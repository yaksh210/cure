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

public class MainActivity13 extends AppCompatActivity {

    EditText pro_doc;
    Button prob_doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main13);

        pro_doc=findViewById(R.id.problem_doc);
        prob_doc=findViewById(R.id.send_doc);


        prob_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String problem = pro_doc.getText().toString();


                if (TextUtils.isEmpty(problem))
                {
                    pro_doc.setError("Nothing Entered Here");
                    return;
                }
                else
                {
                    Toast.makeText(MainActivity13.this,"Your problem will solve soon ,thanks for your response",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity13.this,MainActivity8.class));
                    finish();

                }



            }
        });





    }
}