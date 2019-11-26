package com.qii.ntsk.qii.model.datasource.repository

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.qii.ntsk.qii.model.entity.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PostsDataSource(private val scope: CoroutineScope) : PageKeyedDataSource<String, Post>() {
    private val repository = PostsRepository()

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Post>) {
        scope.launch {
            val response = repository.fetch(DEFAULT_PAGE, params.requestedLoadSize.toString(), null)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                callback.onResult(body.toMutableList(), DEFAULT_PAGE, "2")
            } else {
                callback.onResult(mutableListOf(), DEFAULT_PAGE, "2")
            }
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Post>) {

        scope.launch {
            val response = repository.fetch(params.key, params.requestedLoadSize.toString(), null)
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


    class Factory(private val scope: CoroutineScope) : DataSource.Factory<String, Post>() {
        override fun create(): DataSource<String, Post> {
            return PostsDataSource(scope)
        }
    }

    companion object {
        private const val DEFAULT_PAGE = "1"
    }
}