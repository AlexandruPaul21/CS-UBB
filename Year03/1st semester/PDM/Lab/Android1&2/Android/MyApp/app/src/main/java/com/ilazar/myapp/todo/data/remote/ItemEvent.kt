package com.ilazar.myapp.todo.data.remote

import com.ilazar.myapp.todo.data.Item

data class ItemEvent(val type: String, val payload: Item)
