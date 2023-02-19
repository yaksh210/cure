package com.example.cure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity3 extends AppCompatActivity {

    TextView t;
    EditText mail,pass;
    Button log;
    FirebaseAuth fAuth;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main3);

        t=findViewById(R.id.forgetpass);
        mail=findViewById(R.id.log_email);
        pass=findViewById(R.id.log_pass);
        log=findViewById(R.id.log_in);

        fAuth=FirebaseAuth.getInstance();

        textToSpeech=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

            }
        });


        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity3.this,MainActivity5.class);
                startActivity(i);
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String Email=mail.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                final String Password=pass.getText().toString().trim();

                if(TextUtils.isEmpty(Email))
                {
                    mail.setError("Please enter E-Mail");
                    return;
                }

                if(!Email.matches(emailPattern))
                {
                    mail.setError("invalid email");
                }

                if(TextUtils.isEmpty(Password))
                {
                    pass.setError("Please enter Password");
                    return;
                }
                if(Password.length() < 6)
                {
                    pass.setError("password must be > 6 characters");
                    return;
                }



                fAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            if(Email.equals(FirebaseDatabase.getInstance().getReference("Doctor").child("demail")))
                            {
                                Toast.makeText(MainActivity3.this, "login Successfully", Toast.LENGTH_SHORT).show();
                                textToSpeech.speak("Logged-in Successfully", TextToSpeech.QUEUE_FLUSH,null,null);

                                startActivity(new Intent(getApplicationContext(), MainActivity8.class));
                                FirebaseUser user = fAuth.getCurrentUser();
                                updateUI(user);
                                finish();
                            }

                            else{

                                Toast.makeText(MainActivity3.this, "login Successfully", Toast.LENGTH_SHORT).show();
                                textToSpeech.speak("Logged-in Successfully", TextToSpeech.QUEUE_FLUSH,null,null);

                                startActivity(new Intent(getApplicationContext(), MainActivity6.class));
                                FirebaseUser user = fAuth.getCurrentUser();
                                updateUI(user);
                                finish();
                            }


                            }

                        else if (Email.equals("cureplea0702@gmail.com") && Password.equals("admin123")) {
                            Toast.makeText(MainActivity3.this, "login Successfully", Toast.LENGTH_SHORT).show();
                            textToSpeech.speak("Hello Admin !", TextToSpeech.QUEUE_FLUSH,null,null);

                            startActivity(new Intent(MainActivity3.this, admin.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(MainActivity3.this,"Error!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            textToSpeech.speak("Error!,Failed To logged-in",TextToSpeech.QUEUE_FLUSH,null,null);
                            updateUI(null);
                        }

                    }

                    private void updateUI(FirebaseUser user) {
                    }
                });


            }
        });

    }

}