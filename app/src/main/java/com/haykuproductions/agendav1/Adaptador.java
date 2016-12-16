package com.haykuproductions.agendav1;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Hayku on 12/12/2016.
 */

public class Adaptador extends BaseAdapter {
    private ArrayList<Elemento> lista;
    private final Activity actividad;


    public Adaptador(Activity a, ArrayList<Elemento> v) {
        super();
        this.lista = v;
        this.actividad = a;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int arg0) {
        return lista.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return lista.get(arg0).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater ly = actividad.getLayoutInflater();
        View view = ly.inflate(R.layout.item, null, true);

        TextView tvTitulo= (TextView) view.findViewById(R.id.tvTitulo);
        tvTitulo.setText((CharSequence) lista.get(position).getNombre());

        TextView tvTelefono= (TextView) view.findViewById(R.id.tvTelefono);
        tvTelefono.setText((CharSequence) lista.get(position).getTelefono());

        TextView tvEmail= (TextView) view.findViewById(R.id.tvEmail);
        tvEmail.setText((CharSequence) lista.get(position).getEmail());

        ImageView im = (ImageView) view.findViewById(R.id.imagen);
        File img = new File(lista.get(position).getFoto());

        if (img.exists()) {
            try{
                im.setImageBitmap(BitmapFactory.decodeFile(img.toString()));
                im.setAdjustViewBounds(true);
            }catch(Exception E){}

        } else {
            im.setImageResource(R.drawable.user);
        }

        return view;
    }

}
