package com.paulo.android.happyhour.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.paulo.android.happyhour.Cadastro;
import com.paulo.android.happyhour.MainActivity;
import com.paulo.android.happyhour.R;

public class LoginCadastro extends AppCompatActivity {

    private EditText email;
    private EditText senha;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_cadastro);

        email = (EditText) findViewById(R.id.email1);
        senha = (EditText) findViewById(R.id.password1);

        mAuth = FirebaseAuth.getInstance();

        Button cadastrar = (Button) findViewById(R.id.cadastrar);
        Button entrar = (Button) findViewById(R.id.entrar);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInWithEmailAndPassword(email.getText().toString(), senha.getText().toString())
                        .addOnCompleteListener(LoginCadastro.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(LoginCadastro.this, "Usuário ou senha inválidos!", Toast.LENGTH_LONG).show();
                                }else{
                                    FirebaseUser user = task.getResult().getUser();
                                    if (user != null){
                                        Intent it = new Intent(LoginCadastro.this, MainActivity.class);
                                        startActivity(it);
                                        finish();
                                    }
                                }
                            }
                        });
            }
        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginCadastro.this, Cadastro.class);
                intent.putExtra("Email", email.getText().toString());
                intent.putExtra("Password", senha.getText().toString());
                startActivity(intent);
            }
        });
    }
}
