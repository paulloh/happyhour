package com.paulo.android.happyhour.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText email;
    private EditText senha;
    private ProgressDialog dialog;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email1);
        senha = (EditText) findViewById(R.id.password1);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.cadastrar).setOnClickListener(this);
        findViewById(R.id.entrar).setOnClickListener(this);
    }

    private void login(String email, String senha){
        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Usuário ou senha inválidos!", Toast.LENGTH_LONG).show();
                            dialog.cancel();
                        }else{
                            FirebaseUser user = task.getResult().getUser();
                            if (user != null){
                                Intent it = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(it);
                                finish();
                            }
                        }
                    }
                });
    }

    private void realizarCadastro(){
        Intent intent = new Intent(LoginActivity.this, Cadastro.class);
        intent.putExtra("Email", email.getText().toString());
        intent.putExtra("Password", senha.getText().toString());
        startActivity(intent);
    }

    private boolean validateForm() {
        boolean valid = true;

        String mail = email.getText().toString();
        if (TextUtils.isEmpty(mail)) {
            email.setError("O campo E-mail é obrigatório.");
            valid = false;
        } else {
            email.setError(null);
        }

        String password = senha.getText().toString();

        if (TextUtils.isEmpty(password)) {
            senha.setError("O campo senha é obrigatório.");
            valid = false;
        } else {
            senha.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.entrar) {
            login(email.getText().toString(), senha.getText().toString());
            if(validateForm()) {
                dialog = ProgressDialog.show(LoginActivity.this, "Happy Hour", "Realizando login aguarde...", false, true);
                dialog.setCancelable(false);
            }
        }else if(i == R.id.cadastrar){
            realizarCadastro();
        }
    }
}
