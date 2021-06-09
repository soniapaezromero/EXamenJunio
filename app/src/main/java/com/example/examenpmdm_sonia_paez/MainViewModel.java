package com.example.examenpmdm_sonia_paez;
/**
 * @author:Sonia Päez
 * Clase View Model que se encarga de conectar la activida principal con le clase Respositorio de la base de datos
 */

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.examenpmdm_sonia_paez.database.Socio;
import com.example.examenpmdm_sonia_paez.database.SocioRepositorio;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private SocioRepositorio repositorio;
    private LiveData<List<Socio>> todosSocios;
    public MainViewModel( Application application) {
        super(application);
        repositorio= new SocioRepositorio(application);
        todosSocios = repositorio.getSocios();

    }
    //Inserta registro
    public void Insert(Socio socio){// Inserta una Reserva en la bse de datos
        repositorio.addSocio(socio);
    }
    //Actualiza registro
    public void Update(Socio socio){// Modifica la vbase de datos
        repositorio.updateSocio(socio);
    }
    // Borra el registro
    public void Delete(Socio socio){// Borra registro
        repositorio.deleteSocio(socio);
    }
    public  LiveData<List<Socio>> getAllSocios(){// Muestra todads los socios



        return todosSocios;
    }

}
