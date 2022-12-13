package com.abhi41.bookdetails.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserUser(userEntity: UserEntity)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readUsers(): Flow<List<UserEntity>>

    @Update
    suspend fun updateUser(userEntity: UserEntity)

    @Delete
    suspend fun deleteUsers(userEntity: UserEntity)

}