package com.life.plus.tv.di

import android.content.Context
import androidx.room.Room
import com.life.plus.tv.data.data_source.local.room.UserDatabase
import com.life.plus.tv.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.HttpHeaders
import io.ktor.serialization.gson.gson
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvideModule {

    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, UserDatabase::class.java, Constants.DATABASE_NAME)
            .build()

    @Provides
    @Singleton
    fun provideUserDao(database: UserDatabase) = database.userDao()

    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient{
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okhttpEngine = OkHttp.create {
            addInterceptor(loggingInterceptor)
        }
        return HttpClient(okhttpEngine){
            install(DefaultRequest){
                url(Constants.BASE_URL)
            }
            install(ContentNegotiation){
                gson {
                    setLenient()
                    serializeNulls()
                }
            }
        }
    }
}