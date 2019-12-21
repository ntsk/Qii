package com.qii.ntsk.qii.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qii.ntsk.qii.model.entity.Tags
import com.qii.ntsk.qii.model.repository.TagsRepository
import kotlinx.coroutines.launch

class SearchViewModel(app: Application) : AndroidViewModel(app) {

    private val tagsRepository = TagsRepository()

    private val tagsLiveData: MutableLiveData<Tags> = MutableLiveData()

    internal fun fetchTags(): MutableLiveData<Tags> {
        viewModelScope.launch {
            val tagsResponse = tagsRepository.fetch("1", "50", "count")
            val tagsBody = tagsResponse.body()
            if (tagsResponse.isSuccessful && tagsBody != null) {
                tagsLiveData.postValue(Tags(tagsBody))
            }
        }
        return tagsLiveData
    }
}