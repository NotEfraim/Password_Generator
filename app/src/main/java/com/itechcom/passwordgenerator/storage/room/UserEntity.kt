package com.itechcom.passwordgenerator.storage.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity (
    @PrimaryKey(autoGenerate = true) val id : Int? = null,
    @ColumnInfo(name = "accountType") val accountType : String?,
    @ColumnInfo(name = "accountDescription") val description : String?,
    @ColumnInfo(name = "accountPassword") val password : String?
)