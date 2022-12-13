package com.abhi41.bookdetails.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhi41.bookdetails.util.Constants.USER_TABLE
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = USER_TABLE)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val userName: String,
    val phoneNumber: String,
    val bookName:String
):Parcelable


