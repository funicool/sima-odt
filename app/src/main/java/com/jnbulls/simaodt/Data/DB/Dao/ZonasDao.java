package com.jnbulls.simaodt.Data.DB.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.jnbulls.simaodt.Data.Entities.Zonas;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ZonasDao {
    @Insert(onConflict = REPLACE)
    void insert(List<Zonas> zonasEntity);
    @Update
    void update(Zonas zonasEntity);
    @Delete
    void delete(Zonas zonasEntity);

    @Query("DELETE FROM zonas")
    void deleteAllZonas();

    @Query("SELECT * FROM zonas ORDER BY idZona DESC")
    LiveData<List<Zonas>> getAllZonas();

    @Query("SELECT * FROM zonas WHERE idZona = :zonaId")
    Zonas getZona(int zonaId);
}
