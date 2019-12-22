package com.qii.ntsk.qii.model.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.qii.ntsk.qii.model.entity.Post
import com.qii.ntsk.qii.model.repository.PostsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PopularPostsDataSource(private val scope: CoroutineScope, private val errorObserver: MutableLiveData<Int>) : PageKeyedDataSource<String, Post>() {
    private val repository = PostsRepository()
    private val query = "stocks:>30"

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Post>) {
        scope.launch {
            val response = repository.fetch(DEFAULT_PAGE, params.requestedLoadSize.toString(), query)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                callback.onResult(body.toMutableList(), DEFAULT_PAGE, "2")
            } else {
                errorObserver.postValue(response.code())
            }
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Post>) {

        scope.launch {
            val response = repository.fetch(params.key, params.requestedLoadSize.toString(), query)
            val body = response.body()
            var currentPage = params.key.toInt()

            if (response.isSuccessful && body != null) {
                currentPage++
                callback.onResult(body.toMutableList(), currentPage.toString())
            } else {
                callback.onResult(mutableListOf(), params.key)
            }
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Post>) {
        // Do Nothing
    }

    class Factory(private val scope: CoroutineScope, private val errorObserver: MutableLiveData<Int>) : DataSource.Factory<String, Post>() {
        override fun create(): DataSource<String, Post> {
            return PopularPostsDataSource(scope, errorObserver)
        }
    }

    companion object {
        private const val DEFAULT_PAGE = "1"
    }
}