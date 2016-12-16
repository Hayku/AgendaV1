package com.haykuproductions.agendav1; /**
 * Created by Hayku on 30/11/2016.
 */
import java.io.Serializable;

public class Elemento implements Serializable{
    private static final long serialVersionUID = 1L;
    private int id;
    String telefono,rFoto, email, direccion,web;
    private String nombre;

    public Elemento(String nombre,int id,String telefono,String rFoto){
        this.nombre = nombre;
        this.id = id;
        this.telefono = telefono;
        this.rFoto = rFoto;
    }
    public Elemento(String nombre,int id,String telefono,String rFoto,String email,String direccion,String web){
        this.nombre = nombre;
        this.id = id;
        this.telefono = telefono;
        this.rFoto = rFoto;
        this.email = email;
        this.direccion = direccion;
        this.web = web;
    }

    public String getNombre(){return nombre;}

    public int getId(){return id;}

    public String getTelefono(){return telefono; }

    public String getEmail(){return email;}

    public String getDireccion(){return direccion;}

    public String getWeb(){return web;}

    public String getFoto(){return rFoto;}


    public void setId(int id){this.id = id;}

    public void setNombre(String nombre){this.nombre = nombre;}

    public void setTelefono(String telefono){this.telefono = telefono;}

    public void setImagen(String url){this.rFoto = url; }

    public void setEmail(String email){this.email = email;}

    public void setDirecicon(String direccion){this.direccion = direccion;}

    public void setWeb(String web){this.web = web;}
}
