package com.paulo.android.happyhour.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paulo.android.happyhour.R;
import com.paulo.android.happyhour.model.Perfil;

import java.io.ByteArrayOutputStream;

public class Cadastro extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EmailPassword";
    final private int GALERY = 1;
    final private int CAPTURE_IMAGE = 2;
    private ImageView imageView;

    private EditText email;
    private EditText senha;
    private EditText senha2;
    private ProgressDialog dialog;
    private Perfil userPerfil;

    private DatabaseReference mDataBase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        mDataBase = FirebaseDatabase.getInstance().getReference();

         email = (EditText) findViewById(R.id.email2);
         senha = (EditText) findViewById(R.id.password2);
         senha2 = (EditText) findViewById(R.id.confirmPass);
         imageView = (ImageView) findViewById(R.id.loadImg);

        findViewById(R.id.cadastrar2).setOnClickListener(this);
        findViewById(R.id.cancelar).setOnClickListener(this);
        findViewById(R.id.loadImg).setOnClickListener(this);

        final String resultEmail = getIntent().getStringExtra("Email");
        final String resultPassword = getIntent().getStringExtra("Password");

        mAuth = FirebaseAuth.getInstance();

        email.setText(resultEmail);
        senha.setText(resultPassword);

    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(Cadastro.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }else{
                            Toast.makeText(Cadastro.this, R.string.sign_up_success,
                                    Toast.LENGTH_SHORT).show();
                            Intent inte = new Intent(Cadastro.this, MainActivity.class);
                            dialog.dismiss();
                            startActivity(inte);
                            createNotification();
                            finish();
                        }

                    }
                });
        // [END create_user_with_email]
        }

    private void selecionarImagem(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Cadastro.this);
        builder.setMessage("Selecionar a imagem do Perfil:")
                .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //camera intent
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePictureIntent, CAPTURE_IMAGE);
                    }
                })
                .setNegativeButton("Galeria", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), GALERY);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE) {
            if (resultCode == RESULT_OK) {
                Glide.with(Cadastro.this)
                        .load(data.getData())
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .centerCrop()
                        .error(R.drawable.img)
                        //.placeholder(R.drawable.default_user_gray)
                        .into(new BitmapImageViewTarget(imageView) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(Cadastro.this.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                imageView.setImageDrawable(circularBitmapDrawable);

                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                resource.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                                byte[] byteArray = byteArrayOutputStream.toByteArray();
                                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                                userPerfil.setPicture(encoded);
                            }


                        });
            }
        }
        if (requestCode == GALERY) {
            if (resultCode == RESULT_OK) {
                Glide.with(Cadastro.this)
                        .load(data.getData())
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .centerCrop()
                        .error(R.drawable.img)
                        //.placeholder(R.drawable.default_user_gray)
                        .into(new BitmapImageViewTarget(imageView) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(Cadastro.this.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                imageView.setImageDrawable(circularBitmapDrawable);

                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                resource.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                                byte[] byteArray = byteArrayOutputStream.toByteArray();
                                String encodedGalery = Base64.encodeToString(byteArray, Base64.DEFAULT);

                            }

                        });
            }
        }
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
        String confirmPass = senha2.getText().toString();
        if (TextUtils.isEmpty(password)) {
            senha.setError("O campo senha é obrigatório.");
            valid = false;
        } else {
            senha.setError(null);
        }
   
        if(TextUtils.isEmpty(confirmPass)){
            senha2.setError("Favor confirmar senha digitada.");
            valid = false;
        }

        if (!TextUtils.equals(password, confirmPass)){
            senha2.setError("As senhas digitadas não coincidem!");
            valid = false;
        }
        return valid;
    }

    public void createNotification(){
        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pIntente = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), i, 0);

        Notification noti = new Notification.Builder(this).setContentTitle("Cadastro realizado com sucesso")
                .setContentText("Bem Vindo ao HAPPY HOUR!")
                .setSmallIcon(R.drawable.happyhourlogo)
                .build();

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(0, noti);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cadastrar2) {
            createAccount(email.getText().toString(), senha.getText().toString());
            if(validateForm()) {
                dialog = ProgressDialog.show(Cadastro.this, "Happy Hour", "Realizando cadastro, aguarde...", false, true);
                dialog.setCancelable(false);
            }
        }else if(i == R.id.cancelar){
            finish();
        }else if (i == R.id.loadImg){
            selecionarImagem();
        }
    }

}
