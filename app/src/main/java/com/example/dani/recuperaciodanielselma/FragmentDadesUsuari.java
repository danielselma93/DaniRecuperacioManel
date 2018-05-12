package com.example.dani.recuperaciodanielselma;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDadesUsuari extends Fragment {

    private EditText nom;
    private EditText cognom;
    private EditText direccion;
    private EditText email;
    private EditText contra;
    private EditText userId;
    public static FirebaseAuth firebaseAuth;
    private DatabaseReference db;
     int estadoBd=0;
     int estadoEmail=0;
    static int parametros=0;
    private static final String TAG = "EmailPassword";
    private Button volver;
    private Button registrar;
    Comunicador comunica;
    public FragmentDadesUsuari() {
        // Required empty public constructor
    }

    public static FragmentDadesUsuari newInstance(String id) {
        FragmentDadesUsuari fragment = new FragmentDadesUsuari();
        if(id==null){
        }else{

            parametros=1;
        }
        //Bundle args = new Bundle();
        //fragment.setArguments(args);    -- No necesitamos parámetros
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_fragment_dades_usuari,container,false);
        nom=(EditText)v.findViewById(R.id.editTextNom);
        userId=(EditText)v.findViewById(R.id.editTextUserid);
        cognom=(EditText)v.findViewById(R.id.editTextCognom);
        db = FirebaseDatabase.getInstance().getReference("usuarios");
        firebaseAuth = FirebaseAuth.getInstance();
        direccion=(EditText)v.findViewById(R.id.editTextAdresa);
        email=(EditText)v.findViewById(R.id.editTextEmail);
        contra=(EditText)v.findViewById(R.id.editTextPassword);
        volver=(Button)v.findViewById(R.id.buttonVolver);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultat = new Intent();

                getActivity().setResult(RESULT_CANCELED, resultat);
                getActivity().finish();
            }
        });
        registrar=(Button)v.findViewById(R.id.buttonOk);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(parametros==0){
                if(userId.getText().toString().equals("")|nom.getText().toString().equals("")|contra.getText().toString().equals("")| cognom.getText().toString().equals("")| direccion.getText().toString().equals("")|email.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Debes de rellenar todos los campos", Toast.LENGTH_SHORT).show();

                }else{
                    String nombre=nom.getText().toString();
                    String id=userId.getText().toString();
                    String apellido=cognom.getText().toString();

                    String calle=direccion.getText().toString();
                    String correo=email.getText().toString();
                    String password=contra.getText().toString();

                    insertarUsuarioBd(nombre,apellido,correo,password,calle,id);


                }

            }else{

            }
            }
        });
        return v;

    }
    public void ejecutarRegistroUsuario( final String nombre, final String apellido, final String email, final String password, final String direccion, final String idUser){
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            estadoEmail=1;
                            comprobacioId(nombre,apellido,email,password,direccion,idUser);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            estadoEmail=0;
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }
    public void insertarUsuarioBd(final String nombre, final String apellido, final String email, final String password, final String direccion, final String idUser){
        Query q = db.orderByChild("idUser").equalTo(idUser);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int cont = 0 ;
                for (DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    cont++;
                }
                if (cont > 0){
                    Toast.makeText(getContext(),"Este Id ya existe", Toast.LENGTH_SHORT).show();

                    estadoBd=0;

                } else {

                    ejecutarRegistroUsuario(nombre,apellido,email,password,direccion,idUser);

                    Usuario u = new Usuario( idUser,  nombre,  apellido,  email,  direccion,  password);

                    db.child(idUser).setValue(u);

                    Toast.makeText(getContext(), "Datos guardados", Toast.LENGTH_SHORT).show();
                    estadoBd=1;



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error : "+databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void comprobacioId(String nombre,String apellido,String correo,String password,String calle,String id){
        if(estadoEmail==1) {
            Intent resultat = new Intent();
            resultat.putExtra("elnom", nombre);
            resultat.putExtra("elId", id);
            resultat.putExtra("elCognom", apellido);

            resultat.putExtra("contra", password);
            resultat.putExtra("elCorreo", correo);
            resultat.putExtra("laCalle", calle);
            Toast.makeText(getContext(), "Antes de cerrar activity", Toast.LENGTH_SHORT).show();
            getActivity().setResult(RESULT_OK, resultat);
            getActivity().finish();
            {

            }
        }else{
            Toast.makeText(getContext(), "userId o email repetidos", Toast.LENGTH_SHORT).show();
        }
    }
    public void metodeCarregaDades(){
        db = FirebaseDatabase.getInstance().getReference("usuarios");

        Query q = db.orderByChild("correo").equalTo(firebaseAuth.getCurrentUser().getEmail());

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Limpiamos la lista por si tenía elementos
                //listaBocadillos.clear();

                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    //De cada nodo producto, obtenemos un objeto de este
                    Usuario p = datasnapshot.getValue(Usuario.class);


                    nom.setText(p.getNombre());
                    cognom.setText(p.getApellidos());
                    userId.setText(p.getIdUser());
                    direccion.setText(p.getDireccion());
                    email.setText(p.getCorreo());
                    contra.setText(p.getContra());
                    contra.setFocusable(false);
                    email.setFocusable(false);
                    userId.setFocusable(false);


                    //Lo añadimos a la lista
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
