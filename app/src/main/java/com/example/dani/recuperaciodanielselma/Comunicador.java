package com.example.dani.recuperaciodanielselma;

/**
 * Created by Dani on 03/05/2018.
 */

public interface Comunicador {
    void pasaParametres(String userId, String nom, String cognom, String contra, String email, String direccion,int s);
    void cargarFragment(String userId);
}
