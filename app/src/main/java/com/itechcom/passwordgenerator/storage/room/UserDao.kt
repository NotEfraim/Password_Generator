package com.itechcom.passwordgenerator.storage.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table ORDER BY id DESC")
    suspend fun getAllAccounts() : List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserEntity)

    @Query("UPDATE user_table SET accountType =:accountType, accountDescription =:description, accountPassword =:password WHERE id =:id")
    suspend fun update(
        id: Int,
        accountType : String,
        description : String,
        password : String
    )

    @Query("DELETE FROM user_table WHERE id =:id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM user_table WHERE id = :userId")
    suspend fun getUserByID(userId : Int) : UserEntity

}