package com.example.examenpmdm_sonia_paez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.examenpmdm_sonia_paez.adaptadores.RecyclerTouchListener;
import com.example.examenpmdm_sonia_paez.adaptadores.SociosAdapter;
import com.example.examenpmdm_sonia_paez.database.Socio;
import com.example.examenpmdm_sonia_paez.database.add.AddSocio;
import com.example.examenpmdm_sonia_paez.database.edit.EditSocio;
import com.example.examenpmdm_sonia_paez.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
/**
 * @author:Sonia Päez C
 * Mi Activity principal
 */
public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    public  static final String ENLACE="https://dam.org.es/files/socios.txt";
    public String[] urls;
    private ActivityMainBinding binding;
    private Socio socio;
    private SociosAdapter adapter;
    private MainViewModel mainViewModel;
    public  List<Socio> listSocio;
    public static int ADDSSOCIOREQUEST =1;
    public static int EDITSOCIOREQUEST=2;
    public static List<Socio> sociosExistentes;
    private boolean existe =false;
    public int numeroEnlace=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding =ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        descargaOkHTTP(ENLACE);
        //Cargo el RecyclerView
        adapter= new SociosAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);
        //LLamo al View model
        mainViewModel=new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getAllSocios().observe(this, new Observer<List<Socio>>() {
            @Override
            public void onChanged(List<Socio> socios) {
                for(int i=0;i< socios.size();i++){
                    Log.e("VIEWMODEL",socios.get(i).toString());
                }
                adapter.setSocios(socios);
                setListSocio(socios);
                sociosExistentes =socios;
            }

        });
        refrescarSocios();
        binding.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, binding.recyclerView, new RecyclerTouchListener.ClickListener() {
            //Click Corto modifica el registro
            @Override
            public void onClick(View view, int position) {
                Socio socioEditada = listSocio.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setPositiveButton("Sí, Cambiar", new DialogInterface.OnClickListener() {
                    /**
                     * This method will be invoked when a button in the dialog is clicked.
                     *
                     * @param dialog the dialog that received the click
                     * @param which  the button that was clicked (ex.
                     *               {@link DialogInterface#BUTTON_POSITIVE}) or the position
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, EditSocio.class);
                        intent.putExtra(EditSocio.EXTRA_EDITID, socioEditada.getIdsocio());
                        intent.putExtra(EditSocio.EXTRA_EDITNOMBRE, socioEditada.getNombre());
                        intent.putExtra(EditSocio.EXTRA_EDITDIRECCION, socioEditada.getDireccion());
                        intent.putExtra(EditSocio.EXTRA_EDITFECHA, socioEditada.getFecha().toString());
                        intent.putExtra(EditSocio.EXTRA_EDITIMAGEN, socioEditada.getImagen());
                        startActivityForResult(intent, EDITSOCIOREQUEST);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setTitle("Confirmar");
                builder.setMessage("¿Modificar al socio " + socioEditada.getNombre() + "?");
                AlertDialog alertDialog = builder
                        .create();
                alertDialog.show();
            }

            //Click largo elimina el registro
            @Override
            public void onLongClick(View view, int position) {
                final Socio socioEliminar= listSocio.get(position);;
                AlertDialog dialog1 = new AlertDialog
                        .Builder(MainActivity.this)
                        .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mainViewModel.Delete(socioEliminar);
                                refrescarSocios();
                                Toast.makeText(MainActivity.this, "Socioi eliminado", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Confirmar")
                        .setMessage("¿Eliminar al socio " + socioEliminar.getNombre() + "?")
                        .create();
                dialog1.show();

            }


        }));

        //Actualizamos el recyclerView
        refrescarSocios();

        binding.fab.setOnClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_barra, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.refresh:
                //petición al servidor para descargar de nuevo los sitios
                descargaOkHTTP(ENLACE);
                break;
        }
        return true;
    }

    private void refrescarSocios() {
        if (adapter == null) return;
        List<Socio> socios= new ArrayList<>();
        socios= getListSocio();
        adapter.setSocios(socios);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if(v== binding.fab){
            Intent intento = new Intent(MainActivity.this, AddSocio.class);
            startActivityForResult(intento, ADDSSOCIOREQUEST);
            refrescarSocios();
        }

    }

    public  List<Socio> getListSocio() {
        return listSocio;
    }

    public void setListSocio(List<Socio> listSocio) {
        this.listSocio = listSocio;
    }

    private void descargaOkHTTP(String uri) {
        URL web = null;
        try {
            web = new URL(uri);
        } catch (MalformedURLException e) {
            mostrarError("Fallo: " + e.getMessage());
        }
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(web)
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                mostrarError(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                try (ResponseBody responseBody = response.body()) {
                    if (response.isSuccessful()) {
                        String responseData= responseBody.string();
                        urls=responseData.split("\n");
                        setNumeroEnlace(urls.length);
                        for(int i= 0;i< urls.length;i++){
                            String enlace= urls[i];
                            Log.e("Urls",enlace);
                            Handler uiHandler = new Handler(Looper.getMainLooper());
                            uiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    urls = enlace.split(";");
                                    String codigo = urls[0];
                                    Integer id = Integer.parseInt(codigo);
                                    Log.e("Descarga URl", codigo);
                                    String nombre = urls[1];
                                    String direccion = urls[2];
                                    String ciudad = urls[3];
                                    String fecha = urls[4];
                                    String imagenDEscargada = urls[5];
                                    String[] espacio = imagenDEscargada.split(" ");
                                    String imagen = "https://dam.org.es/" + espacio[1];
                                    Log.e("Descarga URl", imagen);
                                    List<Socio> sociosGuardadas = getListSocio();
                                    socio = new Socio();
                                    socio.setIdsocio(id);
                                    socio.setNombre(nombre);
                                    socio.setDireccion(direccion);
                                    socio.setCiudad(ciudad);
                                    socio.setFecha(fecha);
                                    socio.setImagen(imagen);
                                    if (sociosGuardadas.size() <= 1) {
                                        mainViewModel.Insert(socio);
                                    } else {
                                        if (!Existe(socio)) {
                                            mainViewModel.Insert(socio);
                                        }
                                    }
                                }
                            });

                         }



                    } else {
                        mostrarMensaje(response.toString());

                    }
                }
            }
        });

    }



    private void mostrarError(String s) {

            Looper.prepare();
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            Looper.loop();
    }
    private void mostrarMensaje(String s) {
            Looper.prepare();
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            Looper.loop();;

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Resibimos datos de la Clase ADD
        if (requestCode == ADDSSOCIOREQUEST && resultCode == RESULT_OK) {// Si sale bien el intent recogemos los datos
            String codigo = data.getStringExtra(AddSocio.EXTRA_ADDNOMBRE);
            String nombre = data.getStringExtra(AddSocio.EXTRA_ADDNOMBRE);
            String direccion = data.getStringExtra(AddSocio.EXTRA_ADDDIRECCION);
            String ciudad = data.getStringExtra(AddSocio.EXTRA_ADDCIUDAD);
            String fecha = data.getStringExtra(AddSocio.EXTRA_ADDFECHANAC);
            String imagen = data.getStringExtra(AddSocio.EXTRA_ADDIMAGEN);
            socio = new Socio();
            socio.setIdsocio(Integer.valueOf(codigo));
            socio.setNombre(nombre);
            socio.setDireccion(direccion);
            socio.setCiudad(ciudad);
            socio.setImagen(imagen);
            if (!Existe(socio)) {
                mainViewModel.Insert(socio);

            }

        //Comprobamos los datos y modifciamos el registro
        } else if (requestCode == EDITSOCIOREQUEST && resultCode == RESULT_OK) {
            Integer id = data.getIntExtra(EditSocio.EXTRA_EDITID, -1);
            if (id == -1) {
                Toast.makeText(this, "Socio no se puede modificar",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            id = data.getIntExtra(EditSocio.EXTRA_EDITID, -1);
            String nombreEdit = data.getStringExtra(EditSocio.EXTRA_EDITNOMBRE);
            String direccionEdit = data.getStringExtra(EditSocio.EXTRA_EDITDIRECCION);
            String ciudadEdit = data.getStringExtra(EditSocio.EXTRA_EDITCIUDAD);
            String fechaEdit = data.getStringExtra(EditSocio.EXTRA_EDITFECHA);
            String imagenEdit = data.getStringExtra(EditSocio.EXTRA_EDITIMAGEN);
            Socio socioadd = new Socio();
            socioadd.setIdsocio(id);
            socioadd.setNombre(nombreEdit);
            socioadd.setDireccion(direccionEdit);
            socioadd.setCiudad(ciudadEdit);
            socioadd.setFecha(fechaEdit);
            socioadd.setImagen(imagenEdit);
            if (!Existe(socioadd)) {
                mainViewModel.Update(socioadd);
            }

        }
    }

    /**
     * Metod que comprueba si el registo existe
     * @param socio
     * @return existe
     */
    public boolean Existe(Socio socio){
        List <Socio> socioLista= new ArrayList<>();
        socioLista= getListSocio();

        for(Socio s:socioLista){
            if((s.getIdsocio()==(socio.getIdsocio()))){
                existe=true;
                Log.e( "BOOLEAN","true");
            }
        }
        return existe;
    }

    public int getNumeroEnlace() {
        return numeroEnlace;
    }

    public void setNumeroEnlace(int numeroEnlace) {
        this.numeroEnlace = numeroEnlace;
    }
}
