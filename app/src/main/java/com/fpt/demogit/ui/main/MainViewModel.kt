package com.fpt.demogit.ui.main

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val repository = MainRepository()

    fun load(userId: String) {
        // Load user from database
        repository.load(userId)
        // Update UI
        state.updateState {
            it.copy(
                isLoading = false,
                user = repository.getUser(userId)
            )
        }
    }
    // TODO: Implement the ViewModel
}