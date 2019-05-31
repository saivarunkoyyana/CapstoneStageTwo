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

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mauth;
    EditText useremail, userpassword;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mauth = FirebaseAuth.getInstance();
        useremail = findViewById(R.id.login_email);
        userpassword = findViewById(R.id.login_password);
        progressBar = findViewById(R.id.pbar);

        if (mauth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), PosterActivity.class));
        }

    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void userlogin(View view) {
        String loginemail = useremail.getText().toString();
        String loginpassword = userpassword.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(loginemail).matches()) {
            useremail.setError(getString(R.string.valid_email));
            useremail.requestFocus();
            return;

        }
        if (loginemail.isEmpty()) {
            useremail.setError(getString(R.string.email_required));
            useremail.requestFocus();
            return;
        }

        if (loginpassword.isEmpty()) {
            userpassword.setError(getString(R.string.password_required));
            userpassword.requestFocus();
            return;
        }
        if (loginpassword.length() < 6) {
            userpassword.setError(getString(R.string.min_length));
            userpassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);


        mauth.signInWithEmailAndPassword(loginemail, loginpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Intent i = new Intent(MainActivity.this, PosterActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);


                } else {
                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }


}
