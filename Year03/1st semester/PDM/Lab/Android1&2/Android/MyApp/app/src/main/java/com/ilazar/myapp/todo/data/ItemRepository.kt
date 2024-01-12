package com.ilazar.myapp.todo.data

import android.util.Log
import com.ilazar.myapp.core.TAG
import com.ilazar.myapp.todo.data.local.ItemDao
import com.ilazar.myapp.todo.data.remote.ItemEvent
import com.ilazar.myapp.todo.data.remote.ItemService
import com.ilazar.myapp.todo.data.remote.ItemWsClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.withContext

class ItemRepository(
    private val itemService: ItemService,
    private val itemWsClient: ItemWsClient,
    private val itemDao: ItemDao
) {
    val itemStream by lazy { itemDao.getAll() }
    var count = 0;

    init {
        Log.d(TAG, "init")
    }

    suspend fun getAll(): Flow<List<Item>> {
        return itemDao.getAll();
    }

    suspend fun refresh() {
        Log.d(TAG, "refresh started")
        try {
            val items = itemService.find()
            itemDao.deleteAll()
            items.forEach { itemDao.insert(it) }
            Log.d(TAG, "refresh succeeded")
        } catch (e: Exception) {
            Log.w(TAG, "refresh failed", e)
        }
    }

    suspend fun openWsClient() {
        Log.d(TAG, "openWsClient")
        withContext(Dispatchers.IO) {
            getItemEvents().collect {
                Log.d(TAG, "Item event collected $it")
                if (it.isSuccess) {
                    val itemEvent = it.getOrNull();
                    when (itemEvent?.type) {
                        "created" -> handleItemCreated(itemEvent.payload)
                        "updated" -> handleItemUpdated(itemEvent.payload)
                        "deleted" -> handleItemDeleted(itemEvent.payload)
                    }
                }
            }
        }
    }

    suspend fun closeWsClient() {
        Log.d(TAG, "closeWsClient")
        withContext(Dispatchers.IO) {
            itemWsClient.closeSocket()
        }
    }

    suspend fun getItemEvents(): Flow<Result<ItemEvent>> = callbackFlow {
        Log.d(TAG, "getItemEvents started")
        itemWsClient.openSocket(
            onEvent = {
                Log.d(TAG, "onEvent $it")
                if (it != null) {
                    trySend(Result.success(it))
                }
            },
            onClosed = { close() },
            onFailure = { close() });
        awaitClose { itemWsClient.closeSocket() }
    }

    suspend fun update(item: Item): Item {
        Log.d(TAG, "update $item...")
        var updatedItem: Item
        try {
            updatedItem = itemService.update(item._id, item)
        } catch (e: Exception) {
            updatedItem = item
            Log.d(TAG, "exception")
        }
        Log.d(TAG, "update $item succeeded")
        handleItemUpdated(updatedItem)
        return updatedItem
    }

    suspend fun save(item: Item): Item {
        Log.d(TAG, "save $item...")
        var createdItem: Item
        try {
            createdItem = itemService.create(item)
        } catch (e: Exception) {
            createdItem = item
            createdItem._id = "failed" + count;
            count++;
            Log.d(TAG, "exception")
        }
        Log.d(TAG, "save $item succeeded")
        handleItemCreated(createdItem)
        return createdItem
    }

    private suspend fun handleItemDeleted(item: Item) {
        Log.d(TAG, "handleItemDeleted - todo $item")
    }

    private suspend fun handleItemUpdated(item: Item) {
        Log.d(TAG, "handleItemUpdated...")
        itemDao.update(item)
    }

    private suspend fun handleItemCreated(item: Item) {
        Log.d(TAG, "handleItemCreated...")
        itemDao.insert(item)
    }

    suspend fun deleteAll() {
        itemDao.deleteAll()
    }

    fun setToken(token: String) {
        itemWsClient.authorize(token)
    }

    suspend fun sync() {
        val items = getAll().collect {
            it.forEach {
                if (it._id.startsWith("failed")) {
                    it._id = ""
                    itemService.create(it)
                } else {
                    itemService.update(it._id, it)
                }
            }
            //refresh()
        }
    }
}