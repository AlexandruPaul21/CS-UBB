package com.ilazar.myapp.core.data.remote

import android.util.Log
import com.ilazar.myapp.core.TAG
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor constructor() : Interceptor {
  var token: String? = null

  override fun intercept(chain: Interceptor.Chain): Response {
    val original = chain.request()
    val originalUrl = original.url
    if (token == null) {
      Log.d(TAG, "Token is null")
      return chain.proceed(original)
    }
    val requestBuilder = original.newBuilder()
      .addHeader("Authorization", "Bearer $token")
      .url(originalUrl)
    val request = requestBuilder.build()
    Log.d(TAG, "Authorization bearer added")
    return chain.proceed(request)
  }
}