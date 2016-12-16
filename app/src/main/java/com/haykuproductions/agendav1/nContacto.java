package com.haykuproductions.agendav1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Hayku on 12/12/2016.
 */

public class nContacto extends AppCompatActivity {
    private BDContactos admin;
    SQLiteDatabase bd;
    EditText nombre;
    EditText telefono;
    EditText direccion;
    EditText email;
    EditText web;
    EditText foto;
    private static final int GALERIA = 1;
    private static Uri fotoGaleria;
    private static InputStream is;
    private static BufferedInputStream bis;
    private static Bitmap bm;
    private static OutputStream os;
    private static File path;
    private static File fich_salida;
    Calendar calendario = new GregorianCalendar();
    int hora, minutos, segundos;
    Intent i;
    ImageView im;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ncontacto);

        nombre = (EditText)findViewById(R.id.tfNombreNuevo);
        telefono = (EditText)findViewById(R.id.tfTelefonoNuevo);
        direccion = (EditText)findViewById(R.id.tfDireccionNuevo);
        email = (EditText)findViewById(R.id.tfEmailNuevo);
        web = (EditText)findViewById(R.id.tfWebNuevo);
        foto = (EditText)findViewById(R.id.tfFotoNuevo);
        im = (ImageView)findViewById(R.id.ivNuevo);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.bAlta);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alta(view);
            }
        });
    }

    public long alta(View view){
        long resultado = -1;
        if(nombre.getText().toString().equals("") && telefono.getText().toString().equals("")){
            Toast toast1 = Toast.makeText(getApplicationContext(),getString(R.string.error), Toast.LENGTH_SHORT);
            toast1.show();
        }else{
            admin = new BDContactos(this);
            bd = admin.getWritableDatabase();
            ContentValues valor = new ContentValues();
            bd = admin.getReadableDatabase();

            String[]campos = {"idcontacto","nombre","direccion","email","webblog"};
            Cursor cursor = bd.query("contactos",campos,null,null,null,null,null,null);
            int id;

            if(cursor.moveToLast()){
                id=cursor.getInt(0)+1;
            }else{
                id=1;
            }
            valor.put("idcontacto",id);
            valor.put("nombre", nombre.getText().toString());
            valor.put("direccion", direccion.getText().toString());
            valor.put("email", email.getText().toString());
            valor.put("Webblog", web.getText().toString());
            bd.insert("contactos", null, valor);
            valor.clear();

            valor.put("telefono", telefono.getText().toString());
            valor.put("idcontacto",id);
            bd.insert("telefonos", null, valor);
            valor.clear();
            valor.put("nomFichero", foto.getText().toString());
            valor.put("idcontacto",id);
            bd.insert("fotos", null, valor);
            nombre.setText("");
            direccion.setText("");
            email.setText("");
            web.setText("");
            telefono.setText("");
            foto.setText("");
            Toast toast1 = Toast.makeText(getApplicationContext(),getString(R.string.contactoAñadido), Toast.LENGTH_SHORT);
            toast1.show();


            admin.close();
            bd.close();
        }


        return resultado;

    }

    public void meteFoto(View v){
        i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, GALERIA);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            fotoGaleria = data.getData();
            try {
                is = getContentResolver().openInputStream(fotoGaleria);
                bis = new BufferedInputStream(is);
                bm = BitmapFactory.decodeStream(bis);
                im.setImageBitmap(bm);


            } catch (FileNotFoundException e) {}

            GuardarImg();
        }
    }

    public void GuardarImg(){
        if(im.getDrawable() == null) {
            Toast.makeText(getApplicationContext(),"No hemos seleccionado imagen",
                    Toast.LENGTH_LONG).show();
        } else {
            hora =calendario.get(Calendar.HOUR_OF_DAY);
            minutos = calendario.get(Calendar.MINUTE);
            segundos = calendario.get(Calendar.SECOND);
            path = getExternalFilesDir(null);
            fich_salida= new File(path,hora +""+ minutos+"" + segundos+".jpg");
            try {
                os = new FileOutputStream(fich_salida);
            } catch (FileNotFoundException e) {}
            im.setDrawingCacheEnabled(true);
            bm = im.getDrawingCache();
            try {
                bm.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
            } catch (IOException e) {}
        }
        foto.setText(fich_salida.toString());
    }


}
