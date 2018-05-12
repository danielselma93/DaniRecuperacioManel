package com.example.dani.recuperaciodanielselma;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Activity_Registro extends AppCompatActivity implements Comunicador {
    private String unNom, unId, unCognom, password, correo, calle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__registro);
        cargarFragment(unId);
    }

    @Override
    public void pasaParametres(String userId, String nom, String cognom, String contra, String email, String direccion, int s) {

    }

    public void cargarFragment(String userId) {
        FragmentTransaction transaccion;
        FragmentDadesUsuari fragmentDades = FragmentDadesUsuari.newInstance(userId);//cridar new instance pasantli el userid
        transaccion = getSupportFragmentManager().beginTransaction().replace(R.id.elFrame,fragmentDades);
        transaccion.commit();
    }
}
