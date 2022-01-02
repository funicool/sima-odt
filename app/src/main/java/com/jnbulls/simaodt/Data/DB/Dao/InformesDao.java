package com.jnbulls.simaodt.Data.DB.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.jnbulls.simaodt.Data.Entities.Informes;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface InformesDao {
    @Insert(onConflict = REPLACE)
    void insert(List<Informes> informesEntity);
    @Update
    void update(Informes informesEntity);
    @Delete
    void delete(Informes informesEntity);

    @Query("DELETE FROM informes")
    void deleteAllInformes();

    @Query("SELECT * FROM informes ORDER BY idInforme DESC")
    LiveData<List<Informes>> getAllInformes();

    @Query("SELECT * FROM informes WHERE idInforme = :informeId")
    Informes getInforme(int informeId);
}