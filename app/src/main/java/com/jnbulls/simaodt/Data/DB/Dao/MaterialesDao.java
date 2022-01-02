package com.jnbulls.simaodt.Data.DB.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.jnbulls.simaodt.Data.Entities.Materiales;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MaterialesDao {
    @Insert(onConflict = REPLACE)
    void insert(List<Materiales> materialesEntity);
    @Update
    void update(Materiales materialesEntity);
    @Delete
    void delete(Materiales materialesEntity);

    @Query("DELETE FROM materiales")
    void deleteAllMateriales();

    @Query("SELECT * FROM materiales ORDER BY idMaterial DESC")
    LiveData<List<Materiales>> getAllMateriales();

    @Query("SELECT * FROM materiales WHERE idMaterial = :materialId")
    Materiales getMaterial(int materialId);
}
