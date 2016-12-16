package com.haykuproductions.agendav1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    static Adaptador a;
    static ArrayList<Elemento> arrayList = new ArrayList();
    ListView listview;
    Intent it, it2;
    Vector<String> arrayUrls = new Vector<String>();
    static public  SharedPreferences prefe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listview = (ListView) findViewById(android.R.id.list);
        arrayList.clear();

        consulta();

        a = new Adaptador(this,arrayList);

        a.notifyDataSetChanged();
        listview.setAdapter(a);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapter, View view, int position, long arg)
            {
                Elemento el = (Elemento) listview.getAdapter().getItem(position);
                it = new Intent(getApplicationContext(), EditarActivity.class);
                it2 = new Intent(getApplicationContext(), nContacto.class);
                it2.putExtra("id",arrayList.get(position).getId());
                it.putExtra("objeto", el);
                it.putExtra("posicion", position);
                it.putExtra("nombre",arrayList.get(position).getNombre());
                it.putExtra("telefono",arrayList.get(position).getTelefono());
                it.putExtra("foto",arrayList.get(position).getFoto());
                it.putExtra("id",arrayList.get(position).getId());
                it.putExtra("email",arrayList.get(position).getEmail());
                it.putExtra("direccion",arrayList.get(position).getDireccion());
                it.putExtra("web",arrayList.get(position).getWeb());

                startActivity(it);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nuevo_contacto) {
            it = new Intent(getApplicationContext(), nContacto.class);
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void consulta(){
        arrayList.clear();
        SQLiteDatabase db = null;
        BDContactos admin = new BDContactos(this);
        db = admin.getReadableDatabase();
        db.beginTransaction();
        arrayUrls.clear();
        try {
            String[]camposContactos = {"idcontacto","nombre","direccion","email","webblog"};
            String[]camposTelefonos = {"idTelefonos", "telefono", "idContacto"};
            String[]camposFotos = {"idFoto", "nomFichero", "idContacto"};
            Cursor cursor = db.query("contactos",camposContactos,null,null,null,null,"nombre",null);

            while (cursor.moveToNext())
            {
                Cursor fotos = db.query("fotos",camposFotos,"idcontacto = "+cursor.getInt(0),null,null,null,null,null);
                Cursor telef = db.query("telefonos",camposTelefonos,"idcontacto = "+cursor.getInt(0),null,null,null,null,null);
                telef.moveToFirst();
                fotos.moveToFirst();
                arrayList.add(new Elemento(cursor.getString(1),cursor.getInt(0),telef.getString(1),fotos.getString(1),cursor.getString(3),cursor.getString(2),cursor.getString(4)));
            }


            db.setTransactionSuccessful();

        } catch (Exception e) {

        } finally {
            db.endTransaction();
            db.close();
        }

    }
    @Override
    protected void onRestart(){
        super.onRestart();
        a=null;
        consulta();
        a=new Adaptador(this,arrayList);
        a.notifyDataSetChanged();
        listview.setAdapter(a);
    }
}
