package com.jnbulls.simaodt.Data.DB.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jnbulls.simaodt.Data.Entities.DetallesOdtEntity;

import java.util.List;

@Dao
public interface DetallesOdtDao {
    @Insert
    void insert(DetallesOdtEntity detallesOdtEntity);
    @Update
    void update(DetallesOdtEntity detallesOdtEntity);
    @Delete
    void delete(DetallesOdtEntity detallesOdtEntity);

    @Query("DELETE FROM detalles_odt")
    void deleteAllDetallesOdt();

    @Query("SELECT * FROM detalles_odt ORDER BY idDetalleOdt DESC")
    LiveData<List<DetallesOdtEntity>> getAllDetallesOdt();

    @Query("SELECT * FROM detalles_odt WHERE numeroOdt = :numeroOdt")
    DetallesOdtEntity getDetalleOdt(int numeroOdt);
}
