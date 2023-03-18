package com.example.sampleproject.ui.home

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.example.sampleproject.domain.model.repository.Repository

class HomeViewModel(
    private val repository: Repository
) : ViewModel(), LifecycleObserver {

}