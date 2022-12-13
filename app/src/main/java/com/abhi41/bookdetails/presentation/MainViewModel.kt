package com.abhi41.bookdetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.abhi41.bookdetails.local.UserDao
import com.abhi41.bookdetails.local.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val userDao: UserDao
) : ViewModel() {

    val readUsers: LiveData<List<UserEntity>> = readUser().asLiveData()

    suspend fun insertUser(userEntity: UserEntity) {
        userDao.inserUser(userEntity)
    }

    fun readUser(): Flow<List<UserEntity>> {
        return userDao.readUsers()
    }

    suspend fun updateUser(userEntity: UserEntity) {
        return userDao.updateUser(userEntity)
    }

    fun deleteUser(userEntity: UserEntity) {
        viewModelScope.launch {
            userDao.deleteUsers(userEntity)
        }
    }

}