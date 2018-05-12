package com.example.dani.recuperaciodanielselma;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dani on 08/05/2018.
 */


public class AdaptadorUsuarios extends RecyclerView.Adapter<AdaptadorUsuarios.ViewHolderProductos>{

    //Necesario para recuperar imagen de producto
    //Listado de cursos con los que trabajará el recyclerView
    private List<Usuario> listaUsuarios;
    private RecyclerView.LayoutManager rvLM;
    private Context c;
    private DatabaseReference bbddP;
    String idUser="";
    Comunicador comunica;
    private android.app.FragmentManager fm;
    int a =0;
    private int adapterPosition;
    private android.app.FragmentTransaction ft;

    public AdaptadorUsuarios(ArrayList<Usuario> listaUsuarios, Context c) {
        this.listaUsuarios = listaUsuarios;
        this.c = c;
    }

    public int getAdapterPosition() {
        return adapterPosition;
    }

    private void getFragmentManager(android.app.FragmentManager fm){
        this.fm = fm;
    }


    //Esta clase interna tiene que ser estática y extender de ViewHolder
    public class ViewHolderProductos extends RecyclerView.ViewHolder implements View.OnClickListener{

        //Elementos a mostrar de cada producto
        private TextView textoNombre, textoUserId, textoApellidos;
        private ImageButton borrar,modificar;

        //Constructor
        public ViewHolderProductos(View v) {
            super(v);
            bbddP = FirebaseDatabase.getInstance().getReference("usuarios");
            //Recuperamos elementos del layout de cada elemento (elemento_producto.xml)
            textoNombre = (TextView)v.findViewById(R.id.txtNombre);
            textoUserId = (TextView)v.findViewById(R.id.txtUserId);
            textoApellidos=(TextView)v.findViewById(R.id.txtApellido);
            borrar = (ImageButton)v.findViewById(R.id.imageButtonBorrar);
            modificar=(ImageButton)v.findViewById(R.id.imageButtonEditar);


            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

    }


    @Override
    public AdaptadorUsuarios.ViewHolderProductos onCreateViewHolder(ViewGroup parent, int viewType) {

        //Inflamos el layout de cada elemento
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.apariencia_recycler, parent, false);

        //Devolvemos un objeto del ViewHolder con el View creada de parámetro
        return new AdaptadorUsuarios.ViewHolderProductos(v);
    }


    @Override
    public void onBindViewHolder(final AdaptadorUsuarios.ViewHolderProductos holder, int posicion) {

        //Aquí asignaremos las propiedades correspondientes a los Views correspondientes
        holder.textoNombre.setText(listaUsuarios.get(posicion).getNombre());
        holder.textoApellidos.setText(listaUsuarios.get(posicion).getApellidos());
        holder.textoUserId.setText(listaUsuarios.get(posicion).getIdUser());


         a = this.getAdapterPosition();
        holder.borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarUsuario( holder.textoUserId.getText().toString(), a);
            }
        });
        holder.modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarUsuario(holder.textoUserId.getText().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        //Devolveremos el tamaño de la lista de cursos a mostrar
        return listaUsuarios.size();
    }

    public void modificarUsuario(String userId) {

    }


    private void borrarUsuario(String idUser,final int a){
        Toast.makeText(c, ""+idUser, Toast.LENGTH_SHORT).show();

        Query q= bbddP.orderByChild("idUser").equalTo(idUser);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {

                    String claveEliminar = datasnapshot.getKey();
                    DatabaseReference ref = bbddP.child(claveEliminar);
                    ref.removeValue();

                }
                listaUsuarios.clear();


                notifyItemRangeChanged(a, getItemCount());
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
