package com.ilazar.myapp.core

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.preferencesDataStore
import com.ilazar.myapp.MyAppDatabase
import com.ilazar.myapp.auth.data.AuthRepository
import com.ilazar.myapp.auth.data.remote.AuthDataSource
import com.ilazar.myapp.core.data.UserPreferencesRepository
import com.ilazar.myapp.core.data.remote.Api
import com.ilazar.myapp.todo.data.ItemRepository
import com.ilazar.myapp.todo.data.remote.ItemService
import com.ilazar.myapp.todo.data.remote.ItemWsClient

val Context.userPreferencesDataStore by preferencesDataStore(
    name = "user_preferences"
)

class AppContainer(val context: Context) {
    init {
        Log.d(TAG, "init")
    }

    private val itemService: ItemService = Api.retrofit.create(ItemService::class.java)
    private val itemWsClient: ItemWsClient = ItemWsClient(Api.okHttpClient)
    private val authDataSource: AuthDataSource = AuthDataSource()

    private val database: MyAppDatabase by lazy { MyAppDatabase.getDatabase(context) }

    val itemRepository: ItemRepository by lazy {
        ItemRepository(itemService, itemWsClient, database.itemDao())
    }

    val authRepository: AuthRepository by lazy {
        AuthRepository(authDataSource)
    }

    val userPreferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepository(context.userPreferencesDataStore)
    }
}
