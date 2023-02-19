package com.example.cure;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class admin extends AppCompatActivity implements View.OnClickListener {

    CardView ad1, ad2, ad3;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ad1 = findViewById(R.id.adm1);
        ad2 = findViewById(R.id.adm2);
        ad3 = findViewById(R.id.adm3);
        builder = new AlertDialog.Builder(this);


        ad1.setOnClickListener(this);
        ad2.setOnClickListener(this);
        ad3.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {

            case R.id.adm2:
                i=new Intent(this,admin_pat.class);
                startActivity(i);
                break;

            case R.id.adm1:
                i=new Intent(this,admin_doc.class);
                startActivity(i);
                break;

            case R.id.adm3:
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