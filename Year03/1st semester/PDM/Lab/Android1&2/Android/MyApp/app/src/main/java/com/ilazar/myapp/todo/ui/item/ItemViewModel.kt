package com.ilazar.myapp.todo.ui.item

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
import com.ilazar.myapp.todo.data.Item
import com.ilazar.myapp.todo.data.ItemRepository
import kotlinx.coroutines.launch

data class ItemUiState(
    val isLoading: Boolean = false,
    val loadingError: Throwable? = null,
    val itemId: String? = null,
    val item: Item? = null,
    val isSaving: Boolean = false,
    val savingCompleted: Boolean = false,
    val savingError: Throwable? = null,
)

class ItemViewModel(private val itemId: String?, private val itemRepository: ItemRepository) :
    ViewModel() {
    var uiState: ItemUiState by mutableStateOf(ItemUiState(isLoading = true))
        private set

    init {
        Log.d(TAG, "init")
        if (itemId != null) {
            loadItem()
        } else {
            uiState = uiState.copy(item = Item(), isLoading = false)
        }
    }

    fun loadItem() {
        viewModelScope.launch {
            itemRepository.itemStream.collect { items ->
                if (!uiState.isLoading) {
                    return@collect
                }
                val item = items.find { it._id == itemId }
                uiState = uiState.copy(item = item, isLoading = false)
            }
        }
    }

    fun saveOrUpdateItem(celebrationDate: String, received: Boolean, sentimentalValue: Int, text: String, lat:Double, lng:Double) {
        viewModelScope.launch {
            Log.d(TAG, "saveOrUpdateItem...");
            try {
                uiState = uiState.copy(isSaving = true, savingError = null)
                val item = uiState.item?.copy(birthday=celebrationDate, regularStarter = received, age = sentimentalValue, name = text, lat=lat, lng=lng)
                if (itemId == null) {
                    itemRepository.save(item!!)
                } else {
                    itemRepository.update(item!!)
                }
                Log.d(TAG, "saveOrUpdateItem succeeded");
                uiState = uiState.copy(isSaving = false, savingCompleted = true)
            } catch (e: Exception) {
                Log.d(TAG, "saveOrUpdateItem failed");
                uiState = uiState.copy(isSaving = false, savingError = e)
            }
        }
    }

    companion object {
        fun Factory(itemId: String?): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                ItemViewModel(itemId, app.container.itemRepository)
            }
        }
    }
}

