package com.learn.githubusercompose.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.learn.githubusercompose.BuildConfig
import com.learn.githubusercompose.data.local.dao.DetailUserDao
import com.learn.githubusercompose.data.local.dao.FavoriteUserDao
import com.learn.githubusercompose.data.local.dao.FollowDao
import com.learn.githubusercompose.data.local.dao.TrendingRepoDao
import com.learn.githubusercompose.data.local.dao.UserDao
import com.learn.githubusercompose.data.local.database.UserDatabase
import com.learn.githubusercompose.data.remote.api.ApiServices
import com.learn.githubusercompose.data.remote.api.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val DATASTORE_NAME = "secure_settings"

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(DATASTORE_NAME)
            }
        )
    }

    @Singleton
    @Provides
    fun provideApiServices(
        retrofit: Retrofit
    ): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {

        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun provideUserDAO(db: UserDatabase): UserDao {
        return db.searchDao()
    }

    @Provides
    fun provideDetailUserDAO(db: UserDatabase): DetailUserDao {
        return db.detailUserDao()
    }

    @Provides
    fun provideFollowDAO(db: UserDatabase): FollowDao {
        return db.followDao()
    }

    @Provides
    fun provideFavoriteUserDAO(db: UserDatabase): FavoriteUserDao {
        return db.favoriteUserDao()
    }


    @Provides
    fun provideTrendingRepoDAO(db: UserDatabase): TrendingRepoDao {
        return db.trendingRepoDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            "github_user.db"
        ).build()
    }
}