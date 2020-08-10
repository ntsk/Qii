package com.qii.ntsk.qii.datasource.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.qii.ntsk.qii.model.entity.Post
import com.qii.ntsk.qii.datasource.repository.PostsRepository
import com.qii.ntsk.qii.model.state.NetworkState
import com.qii.ntsk.qii.model.state.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostsDataSource(
        private val repository: PostsRepository,
        private val scope: CoroutineScope,
        private val networkStateObserver: MutableLiveData<NetworkState>,
        private val query: String?
) : PageKeyedDataSource<String, Post>() {

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Post>) {
        networkStateObserver.postValue(NetworkState(status = Status.LOADING))
        scope.launch(Dispatchers.IO) {
            val response = repository.fetch(DEFAULT_PAGE, params.requestedLoadSize.toString(), query)
            val body = response.body()
            if (response.isSuccessful && body != null && body.isNotEmpty()) {
                networkStateObserver.postValue(NetworkState(status = Status.SUCCESS, responseCode = response.code()))
                callback.onResult(body.toMutableList(), DEFAULT_PAGE, "2")
            } else {
                networkStateObserver.postValue(NetworkState(status = Status.FAILED, responseCode = response.code()))
            }
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Post>) {
        networkStateObserver.postValue(NetworkState(status = Status.PAGING))
        scope.launch(Dispatchers.IO) {
            val response = repository.fetch(params.key, params.requestedLoadSize.toString(), query)
            val body = response.body()
            var currentPage = params.key.toInt()

            if (response.isSuccessful) {
                if (body != null) {
                    networkStateObserver.postValue(NetworkState(status = Status.SUCCESS, responseCode = response.code()))
                    currentPage++
                    callback.onResult(body.toMutableList(), currentPage.toString())
                } else {
                    networkStateObserver.postValue(NetworkState(status = Status.SUCCESS, responseCode = response.code()))
                    callback.onResult(mutableListOf(), currentPage.toString())
                }
            } else {
                callback.onResult(mutableListOf(), params.key)
            }
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Post>) {
        // Do Nothing
    }

    class Factory(
            private val repository: PostsRepository,
            private val scope: CoroutineScope,
            private val networkStateObserver: MutableLiveData<NetworkState>,
            private val query: String?
    ) : DataSource.Factory<String, Post>() {
        override fun create(): DataSource<String, Post> {
            return PostsDataSource(repository, scope, networkStateObserver, query)
        }
    }

    companion object {
        private const val DEFAULT_PAGE = "1"
    }
}