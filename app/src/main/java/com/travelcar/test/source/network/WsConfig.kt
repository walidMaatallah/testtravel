package com.travelcar.test.source.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WsConfig {

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    private fun provideRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
    }

    fun createService(): TravelCarWebService {
        return provideRetrofitClient().create(TravelCarWebService::class.java)
    }

    private const val BASE_URL = "https://gist.githubusercontent.com/"

}