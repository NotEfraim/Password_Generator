package com.itechcom.passwordgenerator.storage.room

import android.content.Context
import androidx.room.Room
import javax.inject.Inject

class RoomDBManager @Inject constructor(context: Context){

    private val dataBase by lazy {
        Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "app_database").build()
    }

    private val userDao = dataBase.userDao()

    suspend fun insertData(userEntity: UserEntity) = userDao.insert(userEntity)

    suspend fun updateData(
        id: Int,
        accountType : String,
        description : String,
        password : String) = userDao.update(
        id,
        accountType,
        description,
        password)

    suspend fun getAllData() = userDao.getAllAccounts()

    suspend fun getDataById(id : Int) = userDao.getUserByID(id)

    suspend fun deleteData(id: Int) = userDao.delete(id)
}