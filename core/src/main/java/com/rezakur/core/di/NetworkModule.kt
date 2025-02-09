package com.rezakur.core.di

import com.rezakur.core.BuildConfig
import com.rezakur.core.data.sources.remote.MovieApi
import com.rezakur.core.data.sources.remote.TvShowApi
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        val hostname = "api.themoviedb.org"
        val certificatePin = CertificatePinner.Builder()
            .add(hostname, BuildConfig.CERTIFICATE_PIN_1)
            .add(hostname, BuildConfig.CERTIFICATE_PIN_2)
            .add(hostname, BuildConfig.CERTIFICATE_PIN_3)
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePin)
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
    single {
        get<Retrofit>().create(MovieApi::class.java)
    }
    single {
        get<Retrofit>().create(TvShowApi::class.java)
    }
}