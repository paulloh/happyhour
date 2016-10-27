package com.paulo.android.happyhour;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cadastro extends AppCompatActivity {

    private DatabaseReference mDataBase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        mDataBase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        final EditText email = (EditText) findViewById(R.id.email2);
        final EditText senha = (EditText) findViewById(R.id.password2);

        final String resultEmail = getIntent().getStringExtra("Email");
        final String resultPassword = getIntent().getStringExtra("Password");

        email.setText(resultEmail);
        senha.setText(resultPassword);

        Button cancelar = (Button) findViewById(R.id.cancelar);
        Button cadastrar = (Button) findViewById(R.id.cadastrar);

//        cadastrar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                mAuth.createUserWithEmailAndPassword(resultEmail, resultPassword)
//                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()){
//
//                                }
//                            }
//                        })
//            }
//        });


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
