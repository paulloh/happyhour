package com.paulo.android.happyhour.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.paulo.android.happyhour.Cadastro;
import com.paulo.android.happyhour.R;

public class LoginCadastro extends AppCompatActivity {

    EditText email;
    EditText senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_cadastro);

        email = (EditText) findViewById(R.id.email1);
        senha = (EditText) findViewById(R.id.password1);
        Button cadastrar = (Button) findViewById(R.id.cadastrar);



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
