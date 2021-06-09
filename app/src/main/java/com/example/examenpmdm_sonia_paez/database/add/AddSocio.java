package com.example.examenpmdm_sonia_paez.database.add;
/**
 * @author:Sonia Päez Creado el:25/04/2021
 * Actividad para añadir Registros recoge los datos los pasa a la ctividad princiapl paa incluirlos en la base de datos
 */

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.examenpmdm_sonia_paez.MainActivity;
import com.example.examenpmdm_sonia_paez.R;
import com.example.examenpmdm_sonia_paez.database.Socio;
import com.example.examenpmdm_sonia_paez.database.SocioRepositorio;
import com.example.examenpmdm_sonia_paez.databinding.ActivityAddSocioBinding;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddSocio extends AppCompatActivity implements View.OnClickListener  {
    private ActivityAddSocioBinding addbinding;
    private SocioRepositorio repositorio;
    private Socio socio;
    private boolean existe=false;
    public final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    public static final String EXTRA_ADDCODIGO ="com.example.examenpmdm_sonia_paez.database.add.EXTRA_ADDCODIGO";
    public static final String EXTRA_ADDNOMBRE ="com.example.examenpmdm_sonia_paez.database.add.EXTRA_ADDNOMBRE";
    public static final String EXTRA_ADDDIRECCION ="com.example.examenpmdm_sonia_paez.database.add.EXTRA_ADDDIRECCION";
    public static final String EXTRA_ADDCIUDAD ="com.example.examenpmdm_sonia_paez.database.add.EXTRA_ADDCIUDAD";
    public static final String EXTRA_ADDFECHANAC ="com.example.examenpmdm_sonia_paez.database.add.EXTRA_ADDFECHANAC";
    public static final String EXTRA_ADDIMAGEN ="com.example.examenpmdm_sonia_paez.database.add.EXTRA_ADDIMAGEN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_socio);
        addbinding = ActivityAddSocioBinding.inflate(getLayoutInflater());
        View view = addbinding.getRoot();
        setContentView(view);
        setTitle("Añadir Socio");

        addbinding.botonAdd.setOnClickListener(this);
        addbinding.botonBorrar.setOnClickListener(this);
        addbinding.addfecha.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v== addbinding.botonAdd){
            crearSocio();
        }
        if(v== addbinding.botonBorrar){
            finish();
        }
        if(v== addbinding.addfecha){
            mostrarCalendario();
        }
    }
  // Método que crea y comprueba que esten todos los campos
    private void crearSocio() {
        addbinding.addCodigo.setError(null);
        addbinding.addnombre.setError(null);
        addbinding.adddireccion.setError(null);
        addbinding.addciudad.setError(null);
        addbinding.addfecha.setError(null);
        addbinding.addImagen.setError(null);
        String id = addbinding.addCodigo.getText().toString();
        String nombre = addbinding.addnombre.getText().toString();
        String direccion = addbinding.adddireccion.getText().toString();
        String ciudad = addbinding.addciudad.getText().toString();
        String imagen = addbinding.addImagen.getText().toString();
        String fecha = addbinding.addfecha.getText().toString();
        Glide.with(this).load(imagen).placeholder(R.drawable.errorimagen).error(R.drawable.errorimagen).into(addbinding.imageView);
        if ("".equals(id)) {
            addbinding.addCodigo.setText("Tienes que introducir el dato");
            addbinding.addCodigo.requestFocus();
            return;
        }

        if ("".equals(nombre)) {
            addbinding.addnombre.setText("Tienes que introducir el dato");
            addbinding.addnombre.requestFocus();
            return;
        }
        if ("".equals(direccion)) {
            addbinding.adddireccion.setText("Tienes que introducir el dato");
            addbinding.adddireccion.requestFocus();
            return;
        }
        if ("".equals(ciudad)) {
            addbinding.adddireccion.setText("Tienes que introducir el dato");
            addbinding.adddireccion.requestFocus();
            return;
        }
        if ("".equals(fecha)) {
            addbinding.addfecha.setText("Tienes que introducir el dato");
            addbinding.addfecha.requestFocus();
            return;
        }
        if ("".equals(imagen)) {
            addbinding.addImagen.setText("Tienes que introducir el dato");
            addbinding.addImagen.requestFocus();
            return;
        }
        socio = new Socio();
        socio.setIdsocio(Integer.valueOf(id));
        socio.setNombre(nombre);
        socio.setDireccion(direccion);
        socio.setCiudad(ciudad);
        socio.setFecha(fecha);
        socio.setImagen(nombre);

        List<Socio> socioLista = new ArrayList<>();
        socioLista = MainActivity.sociosExistentes;
        for (Socio s : socioLista) {
            if ((s.getIdsocio()== Integer.parseInt(id))) {
                Toast.makeText(this, "Socio no se puede añadida,socio existe",
                        Toast.LENGTH_SHORT).show();
                addbinding.addCodigo.requestFocus();
                return;
            } else {
                Toast.makeText(this, "Socio añadid correctamente",
                        Toast.LENGTH_SHORT).show();
                Intent datoAdd = new Intent();
                datoAdd.putExtra(EXTRA_ADDNOMBRE, nombre);
                datoAdd.putExtra(EXTRA_ADDDIRECCION, direccion);
                datoAdd.putExtra(EXTRA_ADDCIUDAD, ciudad);
                datoAdd.putExtra(EXTRA_ADDFECHANAC, fecha);
                datoAdd.putExtra(EXTRA_ADDIMAGEN, imagen);
                setResult(RESULT_OK, datoAdd);
                finish();

            }
        }
    }
   //Muestra el Datepicker y lo pasa  a una editText
    private void mostrarCalendario() {
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            private static final String BARRA = "/";

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = "";
                if (dayOfMonth < 10) {
                    diaFormateado = "0" + String.valueOf(dayOfMonth);
                } else {
                    diaFormateado = String.valueOf(dayOfMonth);
                }
                String mesFormateado = "";
                if (mesActual < 10) {
                    mesFormateado = "0" + String.valueOf(mesActual);
                } else {
                    mesFormateado = String.valueOf(mesActual);
                }
                //Muestro la fecha con el formato deseado
                addbinding.addfecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        }, anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }


}