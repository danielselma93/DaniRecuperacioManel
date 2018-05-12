package com.example.dani.recuperaciodanielselma;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLlistatUsuari extends Fragment {

     ArrayList<Usuario> listaUsuarios;
    private RecyclerView recycler;
    private DatabaseReference dbRef;
    private RecyclerView.LayoutManager rvLM;
    private AdaptadorUsuarios adaptador;

    public FragmentLlistatUsuari() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_fragment_llistat_usuari,container,false);
        listaUsuarios= new ArrayList<Usuario>();
        recycler=(RecyclerView)v.findViewById(R.id.recyclerUsuaris);
        getBocadillos();




        return v;
    }
    private void getBocadillos(){
        //Obtenemos referencia
        dbRef = FirebaseDatabase.getInstance().getReference("usuarios");

        Query q = dbRef.orderByChild("idUser");

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Limpiamos la lista por si tenía elementos
                //listaBocadillos.clear();

                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    //De cada nodo producto, obtenemos un objeto de este
                    Usuario p = datasnapshot.getValue(Usuario.class);


                    Toast.makeText(getActivity(), "Usuario Añadido "+p.getNombre(), Toast.LENGTH_SHORT).show();

                    //Lo añadimos a la lista
                    listaUsuarios.add(p);
                }
                metodoSetArray();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
  public void  metodoSetArray(){
      rvLM = new GridLayoutManager(getActivity().getApplicationContext(), 2);
      recycler.setLayoutManager(rvLM);


      adaptador = new AdaptadorUsuarios(listaUsuarios,getContext());
      recycler.setAdapter(adaptador);

    }
}
