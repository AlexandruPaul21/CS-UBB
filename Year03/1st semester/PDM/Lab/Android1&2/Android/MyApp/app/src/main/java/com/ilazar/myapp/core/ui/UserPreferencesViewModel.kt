package com.ilazar.myapp.core.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ilazar.myapp.MyApplication
import com.ilazar.myapp.core.TAG
import com.ilazar.myapp.core.data.UserPreferences
import com.ilazar.myapp.core.data.UserPreferencesRepository
import kotlinx.coroutines.launch

class UserPreferencesViewModel(private val userPreferencesRepository: UserPreferencesRepository) :
    ViewModel() {
    var uiState: UserPreferences by mutableStateOf(UserPreferences())
        private set

    init {
        Log.d(TAG, "init")
        load()
    }

    fun load() {
        viewModelScope.launch {
            userPreferencesRepository.userPreferencesStream.collect { userPreferences ->
                uiState = userPreferences
            }
        }
    }

    fun save(userPreferences: UserPreferences) {
        viewModelScope.launch {
            Log.d(TAG, "saveUserPreferences...");
            userPreferencesRepository.save(userPreferences)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                UserPreferencesViewModel(app.container.userPreferencesRepository)
            }
        }
    }
}

