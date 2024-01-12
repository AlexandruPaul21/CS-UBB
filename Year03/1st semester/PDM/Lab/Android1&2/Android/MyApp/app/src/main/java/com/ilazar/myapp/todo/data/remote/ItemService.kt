package com.ilazar.myapp.todo.data.remote

import com.ilazar.myapp.todo.data.Item
import retrofit2.http.*

interface ItemService {
    @GET("/api/item")
    suspend fun find(): List<Item>

    @GET("/api/item/{id}")
    suspend fun read(@Path("id") itemId: String?): Item;

    @Headers("Content-Type: application/json")
    @POST("/api/item")
    suspend fun create(@Body item: Item): Item

    @Headers("Content-Type: application/json")
    @PUT("/api/item/{id}")
    suspend fun update(@Path("id") itemId: String?, @Body item: Item): Item
}
