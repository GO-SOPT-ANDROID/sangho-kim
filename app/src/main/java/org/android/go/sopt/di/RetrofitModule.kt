package org.android.go.sopt.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.android.go.sopt.BuildConfig
import org.android.go.sopt.data.service.AuthService
import org.android.go.sopt.data.service.FollowerService
import org.android.go.sopt.util.BaseUrlType
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideJsonConverterFactory(): Converter.Factory {
        return Json.asConverterFactory("application/json".toMediaType())
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Retrofit2(BaseUrlType.REQRES)
    fun provideFollowerRetrofit(jsonConverter:Converter.Factory, client: OkHttpClient): FollowerService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.REQRES_BASE_URL)
            .addConverterFactory(jsonConverter)
            .client(client)
            .build()
            .create()
    }

//    @Provides
//    @Singleton
//    @Retrofit2(BaseUrlType.AUTH)
//    fun provideAuthRetrofit(jsonConverter: Converter.Factory, client: OkHttpClient): AuthService {
//        return Retrofit.Builder()
//            .baseUrl(BuildConfig.AUTH_BASE_URL)
//            .addConverterFactory(jsonConverter)
//            .client(client)
//            .build()
//            .create()
//    }

    @Qualifier
    annotation class Retrofit2(val baseUrlType: BaseUrlType)
}