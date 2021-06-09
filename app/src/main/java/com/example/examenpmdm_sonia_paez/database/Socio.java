package com.example.examenpmdm_sonia_paez.database;
/**
 * @author:Sonia Päez
 * Crea la tabla y la entidad
 *
 */

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "socios")
public class Socio {
    @PrimaryKey(autoGenerate =true)
    @ColumnInfo(name = "id")
    private Integer idSocio;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "direccion")
    private String direccion;
    @ColumnInfo(name = "ciudad")
    private String ciudad;
    @ColumnInfo(name = "fecha_nacimiento")
    private String fecha;
    @ColumnInfo(name = "imagen")
    private String imagen;


    public Socio(Integer idmascota) {
        this.idSocio = idmascota;
    }
    public Socio(){

    }

    public Socio(Integer idSocio, String nombre, String direccion, String ciudad, String fecha, String imagen) {
        this.idSocio = idSocio;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.fecha = fecha;
        this.imagen = imagen;
    }

    @NonNull
    public Integer getIdsocio() {
        return idSocio;
    }

    public void setIdsocio(@NonNull Integer idsocio) {
        this.idSocio = idsocio;
    }

    public Integer getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(Integer idSocio) {
        this.idSocio = idSocio;
    }

    @NonNull

    public String getNombre() {
        return nombre;
    }

    public void setNombre( String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Socio{" +
                "idSocio=" + idSocio +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", fecha='" + fecha + '\'' +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}
