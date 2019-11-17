package com.qii.ntsk.qii.ui.home.newposts

import android.app.Application
import androidx.lifecycle.*
import com.qii.ntsk.qii.model.datasource.repository.PostsRepository
import com.qii.ntsk.qii.model.entity.Post
import kotlinx.coroutines.launch

class NewPostsViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = PostsRepository()
    val newPostsLiveData: MutableLiveData<List<Post>> = MutableLiveData()

    init {
       load()
    }

    private fun load() {
        viewModelScope.launch {
            val response = repository.fetch("1", "20")
            if (response.isSuccessful) {
                newPostsLiveData.postValue(response.body())
            } else {
                newPostsLiveData.postValue(listOf())
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val app: Application) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return NewPostsViewModel(app) as T
        }
    }
}