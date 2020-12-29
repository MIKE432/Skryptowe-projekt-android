package com.apusart.skryptowe_projekt_android.di

import com.apusart.skryptowe_projekt_android.api.local_data_source.GymDatabase
import com.apusart.skryptowe_projekt_android.api.remote_data_source.IGymRemoteService
import com.apusart.skryptowe_projekt_android.tools.Defaults
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    fun provideGson(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {

        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRoomDatabase(): GymDatabase {
        return GymDatabase.db
    }

    @Singleton
    @Provides
    fun provideRetrofitService(
        client: OkHttpClient,
        gsonCorverterFactory: GsonConverterFactory
    ): IGymRemoteService {

        return Retrofit.Builder()
            .baseUrl(Defaults.baseUrl)
            .addConverterFactory(gsonCorverterFactory)
            .client(client)
            .build()
            .create(IGymRemoteService::class.java)
    }
}