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

    fun onUserClickChangeAvatar() {
        // Change avatar and Update UI
        state.updateState {
            it.copy(
                user = repository.changeAvatar()
            )
        }
    }

    fun onUserUpdateName(name: String) {
        // Update name
        repository.updateName(name)
        // Update UI
        state.updateState {
            it.copy(
                user = repository.getUser(it.user.id)
            )
        }
    }
}