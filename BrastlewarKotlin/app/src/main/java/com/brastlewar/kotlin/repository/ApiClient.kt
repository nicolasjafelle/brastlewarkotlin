package com.brastlewar.kotlin.repository

import com.brastlewar.kotlin.BrastlewarkApplication
import com.brastlewar.kotlin.BuildConfig
import com.brastlewar.kotlin.api.ApiService
import com.jakewharton.picasso.OkHttp3Downloader
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by nicolas on 11/9/17.
 */
class ApiClient private constructor() {

    companion object {

        private val TIMEOUT_MILLIS: Long = 40000

        private val TIMEOUT_UNIT = TimeUnit.MILLISECONDS

        private val DISK_CACHE_SIZE: Long = 10 * 1024 * 1024

        private lateinit var retrofit: Retrofit

//        private var apiService: ApiService? = null

        private lateinit var okHttpDownloader: OkHttp3Downloader

        //        @Inject
//        lateinit var loggingInterceptor: HttpLoggingInterceptor

        val instance: ApiService by lazy {
            retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig::HOST.get())
                    .client(getOkHttpClient())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            retrofit.create(ApiService::class.java)
        }


//        fun getInstance() : ApiService {
//
//            if(apiService == null && retrofit == null) {
//                retrofit = Retrofit.Builder()
//                        .baseUrl(BuildConfig::HOST.get())
//                        .client(getOkHttpClient())
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build()
//
//                apiService = retrofit!!.create(ApiService::class.java)
//            }
//            return apiService!!
//        }

        private fun getOkHttpClient(): OkHttpClient {

            val builder = OkHttpClient.Builder()
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

            builder.retryOnConnectionFailure(true)
            builder.connectTimeout(TIMEOUT_MILLIS, TIMEOUT_UNIT)
            builder.readTimeout(TIMEOUT_MILLIS, TIMEOUT_UNIT)
            builder.writeTimeout(TIMEOUT_MILLIS, TIMEOUT_UNIT)
            builder.addInterceptor(loggingInterceptor)
            builder.cache(getCache())

            val client: OkHttpClient = builder.build()
            okHttpDownloader = OkHttp3Downloader(client)

            return client
        }

        private fun getCache(): Cache {
            val file = File(BrastlewarkApplication.instance.cacheDir, "cache")
            return Cache(file, DISK_CACHE_SIZE)
        }

    }


//    fun populationResponse() = ApiClient.instance.populationResponse()


}