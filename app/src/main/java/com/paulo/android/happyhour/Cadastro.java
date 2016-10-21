package com.paulo.android.happyhour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Cadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        EditText email = (EditText) findViewById(R.id.email2);
        EditText senha = (EditText) findViewById(R.id.password2);

        String resultEmail = getIntent().getStringExtra("Email");
        String resultPassword = getIntent().getStringExtra("Password");

        email.setText(resultEmail);
        senha.setText(resultPassword);

        Button cancelar = (Button) findViewById(R.id.cancelar);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
