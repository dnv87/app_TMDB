package com.mttnow.android.app_tmdb.data.apiNetwork

import com.mttnow.android.app_tmdb.data.Const
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object TMDBConnect {
    fun getClient(): TMDBInterface {

        val requestInterceptor = Interceptor { chain ->
            // Перехватчик принимает только один аргумент, который является лямбда-функцией, поэтому круглые скобки можно опустить

            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", Const.API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            //explicitly return a value from whit @ annotation. lambda always returns the value of the last expression implicitly
            //явно вернуть значение из аннотации @. лямбда всегда неявно возвращает значение последнего выражения
            return@Interceptor chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Const.URL_TMDB)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TMDBInterface::class.java)
    }
}