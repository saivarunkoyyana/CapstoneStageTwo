package com.example.acer.capstonestagetwo.UI;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.acer.capstonestagetwo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegisterActivity extends AppCompatActivity {
    EditText email, password;
    private FirebaseAuth mauth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        mauth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);

    }

    public void signin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void registeruser(View view) {
        String useremail = email.getText().toString();
        String userpassword = password.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(useremail).matches()) {
            email.setError(getString(R.string.enter_valid_email));
            email.requestFocus();
            return;

        }
        if (useremail.isEmpty()) {
            email.setError(getString(R.string.email_required));
            email.requestFocus();
            return;
        }

        if (userpassword.isEmpty()) {
            password.setError(getString(R.string.password_required));
            password.requestFocus();
            return;
        }
        if (userpassword.length() < 6) {
            password.setError(getString(R.string.min_length));
            password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mauth.createUserWithEmailAndPassword(useremail, userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);

                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, R.string.user_register_successfull, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegisterActivity.this, PosterActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(RegisterActivity.this, R.string.already_registered, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
    }
}
