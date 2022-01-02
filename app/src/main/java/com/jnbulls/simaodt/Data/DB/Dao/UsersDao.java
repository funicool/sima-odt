package com.jnbulls.simaodt.Data.DB.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.jnbulls.simaodt.Data.Entities.Users;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UsersDao {
    @Insert(onConflict = REPLACE)
    void insert(List<Users> usersEntity);
    @Update
    void update(Users usersEntity);
    @Delete
    void delete(Users usersEntity);

    @Query("DELETE FROM users")
    void deleteAllUsers();

    @Query("SELECT * FROM users ORDER BY idUser DESC")
    LiveData<List<Users>> getAllUsers();

    @Query("SELECT * FROM users WHERE idUser = :idUser")
    Users getUserById(int idUser);

    @Query("SELECT * FROM users WHERE email = :username")
    Users getUserByUsername(String username);
}
