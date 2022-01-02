package com.jnbulls.simaodt.Data.DB.Dao;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jnbulls.simaodt.Data.Entities.Informes;
import com.jnbulls.simaodt.Data.Entities.OdtEntity;

import java.util.List;

@Dao
public interface OdtDao {
    @Insert
    void insert(OdtEntity odtEntity);
    @Update
    void update(OdtEntity odtEntity);
    @Delete
    void delete(OdtEntity odtEntity);
    @Query("DELETE FROM odt_entity")
    void deleteAllOdt();

    @Query("SELECT * FROM odt_entity ORDER BY id DESC")
    LiveData<List<OdtEntity>> getAllOdt();

    @Query("SELECT * FROM odt_entity WHERE numeroOdt = :odtNumber AND estado = :estado")
    OdtEntity getOdt(int odtNumber, String estado);
}
