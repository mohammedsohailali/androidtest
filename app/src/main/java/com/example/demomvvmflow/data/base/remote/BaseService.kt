package com.example.demomvvmflow.data.base.remote

import com.example.demomvvmflow.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class BaseService {
    private var retrofitBaseApi: Retrofit? = null

    fun getBaseApi(): BaseApi? {
        return createRetrofitBase().create(BaseApi::class.java)
    }

    private fun createRetrofitBase(): Retrofit {
        if (retrofitBaseApi == null) {
            retrofitBaseApi = Retrofit.Builder()
                .baseUrl("https://www.balldontlie.io/")
                .addConverterFactory(
                    GsonConverterFactory.create(
//                        GsonBuilder()
//                            .excludeFieldsWithoutExposeAnnotation().create()
                    )
                )
                .client(getOkHttpClient())
                .build()
        }
        return retrofitBaseApi as Retrofit
    }

    private fun getOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        httpClient.connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
        val logging = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) logging.level = HttpLoggingInterceptor.Level.BODY

        httpClient.addInterceptor(logging)
        httpClient.addInterceptor { chain ->
            val originalRequest: Request = chain.request()


            val requestBuilder = originalRequest.newBuilder()

            val request = requestBuilder.build()
            chain.proceed(request)
        }
        return httpClient.build()
    }
}