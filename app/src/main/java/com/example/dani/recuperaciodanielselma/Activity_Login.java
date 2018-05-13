package com.example.dani.recuperaciodanielselma;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_Login extends AppCompatActivity {
    private Button acceder;
    private Button registrar;
    private EditText editNomUser;
    public static FirebaseAuth firebaseAuth;
    private static final String TAG = "EmailPassword";
    private EditText editContra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__login);
        acceder=(Button)findViewById(R.id.buttonAcceder);
        firebaseAuth = FirebaseAuth.getInstance();
        registrar=(Button)findViewById(R.id.buttonRegistrar);
        editContra=(EditText)findViewById(R.id.editTextContra);
        editNomUser=(EditText)findViewById(R.id.editTextNomUser);
        acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ejecutarLogIn();
            }
        });
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Login.this, Activity_Registro.class);
                startActivityForResult(intent,1);
                Toast.makeText(Activity_Login.this, "Ara executariem el subactivity", Toast.LENGTH_SHORT).show();
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if (resultCode == RESULT_OK) {

            }else{
                Toast.makeText(this, "SubActivity cancelado", Toast.LENGTH_SHORT).show();
            }
        }
            }

    public void ejecutarLogIn(){
        String unCorreo=editNomUser.getText().toString();
        String lacontra=editContra.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(unCorreo, lacontra)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(Activity_Login.this, ""+task.getException(), Toast.LENGTH_LONG).show();

                            Toast.makeText(Activity_Login.this, "Estarias dentro   ", Toast.LENGTH_LONG).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(Activity_Login.this, MainActivity.class);
                            startActivity(intent);
                            //updateUI(user);
                        } else {
                            Toast.makeText(Activity_Login.this, ""+task.getException(), Toast.LENGTH_LONG).show();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Activity_Login.this, "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });

    }
}
