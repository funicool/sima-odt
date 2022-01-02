package com.jnbulls.simaodt.Data.DB.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.jnbulls.simaodt.Data.Entities.Motivos;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MotivosDao {
    @Insert(onConflict = REPLACE)
    void insert(List<Motivos> motivosEntity);
    @Update
    void update(Motivos motivosEntity);
    @Delete
    void delete(Motivos motivosEntity);

    @Query("DELETE FROM motivos")
    void deleteAllMotivos();

    @Query("SELECT * FROM motivos ORDER BY idMotivo DESC")
    LiveData<List<Motivos>> getAllMotivos();

    @Query("SELECT * FROM motivos WHERE idMotivo = :motivoId")
    Motivos getMotivo(int motivoId);
}
