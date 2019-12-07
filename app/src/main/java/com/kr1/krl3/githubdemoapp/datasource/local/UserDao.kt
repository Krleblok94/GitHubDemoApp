package com.kr1.krl3.githubdemoapp.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kr1.krl3.githubdemoapp.datasource.model.db.UserDB

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUser(): UserDB?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserDB)

    @Query("DELETE FROM user")
    fun deleteUser()
}