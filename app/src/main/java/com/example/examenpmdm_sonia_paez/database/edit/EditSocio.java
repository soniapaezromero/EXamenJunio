package com.example.examenpmdm_sonia_paez.database.edit;
/**
 * @author:Sonia Päez C
 * Actividad para Editar Registros recoge los datos los pasa a la actividad princiapl paa incluirlos en la base de datos
 */

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.examenpmdm_sonia_paez.MainActivity;
import com.example.examenpmdm_sonia_paez.R;
import com.example.examenpmdm_sonia_paez.database.Socio;
import com.example.examenpmdm_sonia_paez.database.SocioRepositorio;
import com.example.examenpmdm_sonia_paez.databinding.ActivityEditSocioBinding;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class EditSocio extends AppCompatActivity   implements View.OnClickListener{
    private ActivityEditSocioBinding editSocioBinding;
    private SocioRepositorio repositorio;
    private boolean existe=false;
    public final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    private Socio socioModificado;

    public static final String  EXTRA_EDITID="com.example.examenpmdm_sonia_paez.database.edit.EDITID";
    public static final String EXTRA_EDITNOMBRE ="com.example.examenpmdm_sonia_paez.database.edit.EXTRA_EDITNOMBRE";
    public static final String EXTRA_EDITDIRECCION ="com.example.examenpmdm_sonia_paez.database.edit.EXTRA_EDITDIRECCION";
    public static final String EXTRA_EDITCIUDAD ="com.example.examenpmdm_sonia_paez.database.edit.EXTRA_EDITCIUDAD";
    public static final String EXTRA_EDITFECHA ="com.example.examenpmdm_sonia_paez.database.edit.EXTRA_EDITFECHA";
    public static final String EXTRA_EDITIMAGEN ="com.example.examenpmdm_sonia_paez.database.edit.EXTRA_EDITIMAGEN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__socio);
        editSocioBinding = ActivityEditSocioBinding.inflate(getLayoutInflater());
        View view = editSocioBinding.getRoot();
        setContentView(view);
        Intent intent= getIntent();
        if(intent.hasExtra(EXTRA_EDITID)) {
            setTitle("Modificar Socio");
            Log.e("editar", String.valueOf(intent.getIntExtra(EXTRA_EDITID,-1)));
            editSocioBinding.editCodigo.setText(  String.valueOf(intent.getIntExtra(EXTRA_EDITID,-1)));
            editSocioBinding.editnombre.setText(intent.getStringExtra(EXTRA_EDITNOMBRE));
            editSocioBinding.editdireccion.setText(intent.getStringExtra(EXTRA_EDITDIRECCION));
            editSocioBinding.editciudad.setText(intent.getStringExtra(EXTRA_EDITCIUDAD));
            editSocioBinding.editciudad.setText(intent.getStringExtra(EXTRA_EDITCIUDAD));
            editSocioBinding.editciudad.setText(intent.getStringExtra(EXTRA_EDITFECHA));
            editSocioBinding.editImagen.setText(intent.getStringExtra(EXTRA_EDITIMAGEN));
            Log.e("editar", intent.getStringExtra(EXTRA_EDITIMAGEN));
           // Picasso.get().load( intent.getStringExtra(EXTRA_EDITIMAGEN)).placeholder(R.drawable.errorimagen).error(R.drawable.errorimagen).into(editMascotaBinding.imageView);
            Glide.with(this).load(intent.getStringExtra(EXTRA_EDITIMAGEN)).placeholder(R.drawable.errorimagen).error(R.drawable.errorimagen).into(editSocioBinding.imageView);


        }

        //configuramos los clicks
        editSocioBinding.editfecha.setOnClickListener(this);
        editSocioBinding.botonAdd.setOnClickListener(this);
        editSocioBinding.botonBorrar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
       if (v== editSocioBinding.botonAdd){
           modificarSocio();

        }
        if(v== editSocioBinding.botonBorrar){
           finish();
        }
        if(v==editSocioBinding.editfecha){
            mostrarCalendario();
        }


    }
    //Comprobamos que esten todos los datos y pase los datos y lo
    private void modificarSocio() {
        editSocioBinding.editnombre.setError(null);
        editSocioBinding.editciudad.setError(null);
        editSocioBinding.editdireccion.setError(null);
        editSocioBinding.editCodigo.setError(null);
        editSocioBinding.editfecha.setError(null);
        editSocioBinding.editImagen.setError(null);
        String codigo = editSocioBinding.editCodigo.getText().toString();
        String nombre = editSocioBinding.editnombre.getText().toString();
        String direccion = editSocioBinding.editdireccion.getText().toString();
        String ciudad = editSocioBinding.editciudad.getText().toString();
        String fecha = editSocioBinding.editfecha.getText().toString();
        String imagen = editSocioBinding.editImagen.getText().toString();
        Glide.with(this).load(imagen).placeholder(R.drawable.errorimagen).error(R.drawable.errorimagen).into(editSocioBinding.imageView);
        if ("".equals(codigo)) {
            editSocioBinding.editCodigo.setText("Tienes que introducir el dato");
            editSocioBinding.editCodigo.requestFocus();
            return;
        }

        if ("".equals(nombre)) {
            editSocioBinding.editnombre.setText("Tienes que introducir el dato");
            editSocioBinding.editnombre.requestFocus();
            return;
        }
        if ("".equals(direccion)) {
            editSocioBinding.editdireccion.setText("Tienes que introducir el dato");
            editSocioBinding.editdireccion.requestFocus();
            return;
        }
        if ("".equals(ciudad)) {
            editSocioBinding.editciudad.setText("Tienes que introducir el dato");
            editSocioBinding.editciudad.requestFocus();
            return;
        }
        if ("".equals(fecha)) {
            editSocioBinding.editfecha.setText("Tienes que introducir el dato");
            editSocioBinding.editfecha.requestFocus();
            return;
        }
        if ("".equals(imagen)) {
            editSocioBinding.editImagen.setText("Tienes que introducir el dato");
            editSocioBinding.editImagen.requestFocus();
            return;
        }
        socioModificado = new Socio();
        socioModificado.setIdsocio(Integer.valueOf(codigo));
        socioModificado.setNombre(nombre);
        socioModificado.setDireccion(direccion);
        socioModificado.setFecha(fecha);
        socioModificado.setImagen(imagen);

        List<Socio> socioLista = new ArrayList<>();
        socioLista = MainActivity.sociosExistentes;
        for (Socio s : socioLista) {
            if ((s.getIdsocio()== Integer.parseInt(codigo))) {
                Toast.makeText(this, "Socio no se puede añadida,socio existe",
                        Toast.LENGTH_SHORT).show();
                editSocioBinding.editCodigo.requestFocus();
                return;

            } else {
                Toast.makeText(this, "Socio modificada correctamente",
                        Toast.LENGTH_SHORT).show();
                Intent datoEdit = new Intent();
                datoEdit.putExtra(EXTRA_EDITNOMBRE, nombre);
                datoEdit.putExtra(EXTRA_EDITDIRECCION, direccion);
                datoEdit.putExtra(EXTRA_EDITCIUDAD, ciudad);
                datoEdit.putExtra(EXTRA_EDITFECHA, fecha);
                datoEdit.putExtra(EXTRA_EDITIMAGEN, imagen);
                Integer id = getIntent().getIntExtra(EXTRA_EDITID, -1);
                Log.e("DAtos Enviados ID", String.valueOf(id));
                if (id != -1) {
                    datoEdit.putExtra(EXTRA_EDITID, id);
                }
                setResult(RESULT_OK, datoEdit);
                Log.e("DAtos Enviados", "Editar encia datos");
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
                editSocioBinding.editfecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


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