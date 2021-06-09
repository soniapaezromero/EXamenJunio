package com.example.examenpmdm_sonia_paez.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;
/**
 * @author:Sonia Päez
 * Nos hace las operaciones que queremos hacer con la base de datos
 */


@Dao
public abstract interface SocioiDao {
    @Transaction// Ordena la base por fehas dela mas reciente a la mas antigue
    @Query(value = "SELECT * FROM socios ")
    LiveData<List<Socio>>  getSocios();
    @Transaction
    @Query("SELECT * FROM socios WHERE id LIKE :idsocio")
    LiveData <Socio> getSocio(Integer idsocio);

    @Insert(onConflict = OnConflictStrategy.IGNORE)//Inserta datos en la tabla
    void addSocio(Socio socio);

    @Delete
    void deleteSocio(Socio socio);// Borra un registro de la tabla

    @Update
    void updateSocio(Socio socio);// Actualiza el registro

   }
