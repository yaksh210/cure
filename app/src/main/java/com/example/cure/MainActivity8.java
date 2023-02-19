package com.example.cure;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity8 extends AppCompatActivity implements View.OnClickListener {

        public     CardView doc1,doc3,doc4,doc5;
                    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main8);

        doc1 = findViewById(R.id.doc1);
        doc3 = findViewById(R.id.doc3);
        doc4 = findViewById(R.id.doc4);
        doc5= findViewById(R.id.doc5);
        builder = new AlertDialog.Builder(this);


        doc1.setOnClickListener(this);
        doc3.setOnClickListener(this);
        doc4.setOnClickListener(this);
        doc5.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId())
        {

            case R.id.doc1:
                i=new Intent(this,MainActivity9.class);
                startActivity(i);
                break;



            case R.id.doc4:
                i=new Intent(this,MainActivity13.class);
                startActivity(i);
                break;

            case R.id.doc3:
                i=new Intent(this,MainActivity14.class);
                startActivity(i);
                break;

            case R.id.doc5:

                builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);

                builder.setMessage("Do you want to log-out?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                Toast.makeText(getApplicationContext(), "Logged out",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Log-Out");
                alert.show();
                break;

        }

    }
}