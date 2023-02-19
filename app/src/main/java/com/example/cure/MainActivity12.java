package com.example.cure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity12 extends AppCompatActivity {

    EditText pro1;
    Button prob_send;

    FirebaseDatabase root;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main12);

        pro1=findViewById(R.id.problem_patient);
        prob_send=findViewById(R.id.send_prob);

        prob_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String problem = pro1.getText().toString();

                if (TextUtils.isEmpty(problem))
                {
                    pro1.setError("Nothing Entered Here");
                    return;
                }
                else
                {
                    root=FirebaseDatabase.getInstance();
                    db=root.getReference("Patient's problem");

                    patpro p=new patpro(problem);
                    db.setValue(p);


                    Toast.makeText(MainActivity12.this,"Your problem will solve soon ,thanks for your response",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity12.this,MainActivity6.class));
                    finish();

                }

            }
        });

    }
}