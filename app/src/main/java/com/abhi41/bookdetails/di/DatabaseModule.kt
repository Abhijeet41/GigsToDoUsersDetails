package com.abhi41.bookdetails.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abhi41.bookdetails.local.UserDao
import com.abhi41.bookdetails.local.UserDatabase
import com.abhi41.bookdetails.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): UserDatabase {
        return Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            Constants.USER_DB
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideDao(database: UserDatabase): UserDao {
        return database.userDao()
    }

}