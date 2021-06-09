package com.example.examenpmdm_sonia_paez.database;
/**
 * @author:Sonia Päez
 * Crea la abase de datos y ejecuta los disitinto hilos a traves de llamadas
 */

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Socio.class}, version = 1)
public abstract class Sociosdatabase extends RoomDatabase {
    private static Sociosdatabase INSTANCE;
    public abstract SocioiDao reservaDao();


    public static Sociosdatabase getInstance(Context context){
          if(INSTANCE== null) {
              synchronized (Sociosdatabase.class) {
                  if (INSTANCE == null) {
                      INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Sociosdatabase.class, "socios")
                              .fallbackToDestructiveMigration()
                              .addCallback(sRoomDatabaseCallback)
                              .build();
                  }
              }
                    }
        return INSTANCE;
    }
    private static Callback sRoomDatabaseCallback = new Callback(){
        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);

            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final SocioiDao socioiDao;
        PopulateDbAsync(Sociosdatabase db){
            socioiDao = db.reservaDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }



}


