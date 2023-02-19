package com.example.cure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class MainActivity5 extends AppCompatActivity {

     EditText e1;
     Button s1;
     FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main5);

        e1=findViewById(R.id.femail);
        s1=findViewById(R.id.send);

        fAuth=FirebaseAuth.getInstance();

        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });


    }

    private void resetPassword() {

        String email= e1.getText().toString().trim();

        if(email.isEmpty())
        {
            e1.setError("Email is required");
            e1.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            e1.setError("Please enter valid email");
            e1.requestFocus();
            return;
        }



        fAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity5.this, "Reset link is sent to your email", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity5.this,MainActivity3.class));
                    finish();
                }
                else
                    {
                        Toast.makeText(MainActivity5.this, "Error:"+task.getException(), Toast.LENGTH_SHORT).show();
                    }
            }
        });

    }
}