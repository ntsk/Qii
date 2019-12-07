package com.qii.ntsk.qii.ui.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qii.ntsk.qii.model.entity.Tags
import com.qii.ntsk.qii.model.repository.TagsRepository
import kotlinx.coroutines.launch

class SearchViewModel(app: Application) : AndroidViewModel(app) {

    private val tagsRepository = TagsRepository()

    val tagsLiveData: MutableLiveData<Tags> = MutableLiveData()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            val tagsResponse = tagsRepository.fetch("1", "50", "count")
            val tagsBody = tagsResponse.body()
            if (tagsResponse.isSuccessful && tagsBody != null) {
                tagsLiveData.postValue(Tags(tagsBody))
            }

        }
    }
}