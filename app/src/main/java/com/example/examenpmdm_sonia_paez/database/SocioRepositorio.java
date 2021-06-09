package com.example.examenpmdm_sonia_paez.database;
/**
 * @author:Sonia Päez C
 * Mi Clase repositorio crea las distintas operacione sde insertar, actualizar, eliminar registros
 */

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SocioRepositorio {
    @SuppressLint("StaticFieldLeak")
    public SocioiDao socioiDao;
    private LiveData<List<Socio>> allSocios;

    public SocioRepositorio(Application aplication) {
        Sociosdatabase sociosdatabase = Sociosdatabase.getInstance(aplication);
        socioiDao = sociosdatabase.reservaDao();
        allSocios = socioiDao.getSocios();


    }
    //Muestra todos los registros de la tabla
    public  LiveData<List<Socio>> getSocios() {
         return allSocios;
    }
    //Muestra un registro determinado  de la tabla
    public LiveData<Socio> getSocio(Integer id) {
        return socioiDao.getSocio(id);
    }
    //Añade un registro
    public void addSocio(Socio socio) {
        new InsertAsynctask(socioiDao).execute(socio);
    }
    //Modifica el  registro
    public void updateSocio(Socio socio) {
        new UpdateAsynctask(socioiDao).execute(socio);
    }
    // Borra el registro determinado
    public void deleteSocio(Socio socio) {
        new DeleteAsynctask(socioiDao).execute(socio);
        ;
    }
// Asyntak para crear registro
    private static class InsertAsynctask extends AsyncTask<Socio, Void, Void> {
        SocioiDao socioiDao;

        public InsertAsynctask(SocioiDao socioiDao) {
            this.socioiDao = socioiDao;
        }

        @Override
        protected Void doInBackground(Socio... socios) {
            socioiDao.addSocio(socios[0]);
            return null;
        }
    }
    // Asyntak para actualizar registro
    private static class UpdateAsynctask extends AsyncTask<Socio, Void, Void> {
        SocioiDao socioiDao;

        public UpdateAsynctask(SocioiDao socioiDao) {
            this.socioiDao = socioiDao;
        }

        @Override
        protected Void doInBackground(Socio... socios) {
            socioiDao.updateSocio(socios[0]);
            return null;
        }
    }
    // Asyntak para borrar registro
    private static class DeleteAsynctask extends AsyncTask<Socio, Void, Void> {
        SocioiDao socioiDao;

        public DeleteAsynctask(SocioiDao socioiDao) {
            this.socioiDao = socioiDao;
        }

        @Override
        protected Void doInBackground(Socio... socios) {
            socioiDao.deleteSocio(socios[0]);
            return null;
        }
    }
}



