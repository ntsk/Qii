package com.qii.ntsk.qii.datasource.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.qii.ntsk.qii.model.entity.Post
import com.qii.ntsk.qii.datasource.repository.UserRepository
import com.qii.ntsk.qii.model.state.NetworkState
import com.qii.ntsk.qii.model.state.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StocksDataSource(
        private val repository: UserRepository,
        private val scope: CoroutineScope,
        private val networkStateObserver: MutableLiveData<NetworkState>,
        private val userId: String
) : PageKeyedDataSource<Int, Post>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Post>) {
        networkStateObserver.postValue(NetworkState(status = Status.LOADING))
        scope.launch(Dispatchers.IO) {
            val response = repository.getStocks(userId, DEFAULT_PAGE, params.requestedLoadSize)
            val body = response.body()
            if (response.isSuccessful && body != null && body.isNotEmpty()) {
                networkStateObserver.postValue(NetworkState(status = Status.SUCCESS, responseCode = response.code()))
                callback.onResult(body.toMutableList(), DEFAULT_PAGE, 2)
            } else {
                networkStateObserver.postValue(NetworkState(status = Status.FAILED, responseCode = response.code()))
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Post>) {
        networkStateObserver.postValue(NetworkState(status = Status.PAGING))
        scope.launch(Dispatchers.IO) {
            val response = repository.getStocks(userId, params.key, params.requestedLoadSize)
            val body = response.body()
            var currentPage = params.key

            if (response.isSuccessful && body != null) {
                networkStateObserver.postValue(NetworkState(status = Status.SUCCESS, responseCode = response.code()))
                currentPage++
                callback.onResult(body.toMutableList(), currentPage)
            } else {
                callback.onResult(mutableListOf(), params.key)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Post>) {
        // Do Nothing
    }

    class Factory(
            private val repository: UserRepository,
            private val scope: CoroutineScope,
            private val networkStateObserver: MutableLiveData<NetworkState>,
            private val userId: String
    ) : DataSource.Factory<Int, Post>() {
        override fun create(): DataSource<Int, Post> {
            return StocksDataSource(repository, scope, networkStateObserver, userId)
        }
    }

    companion object {
        private const val DEFAULT_PAGE = 1
    }
}
