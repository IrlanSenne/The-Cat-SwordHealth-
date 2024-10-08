package com.swordhealth.thecat.di

import com.swordhealth.thecat.BuildConfig
import com.swordhealth.thecat.MainViewModel
import com.swordhealth.thecat.data.api.CatsApi
import com.swordhealth.thecat.data.repository.CatRepository
import com.swordhealth.thecat.data.repository.CatRepositoryImpl
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("x-api-key", BuildConfig.API_KEY)
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatsApi::class.java)
    }

    single<CatRepository> { CatRepositoryImpl(get()) }

    viewModel { MainViewModel(get()) }
}

const val BASE_URL = "https://api.thecatapi.com/v1/"