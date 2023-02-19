package com.example.cure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageTask;

public class MainActivity extends AppCompatActivity {
        Button sign_up;
        TextView textView;

        TextToSpeech textToSpeech;

        EditText pass,f_namee,l_namee,emaill,pid;
        CheckBox show_pass;
        FirebaseAuth fAuth;

        FirebaseDatabase rootNode;
        DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        pass=findViewById(R.id.pass);
        pid=findViewById(R.id.pid);
        show_pass=findViewById(R.id.show_pass);
        f_namee=findViewById(R.id.f_name);
        l_namee=findViewById(R.id.l_name);
        emaill=findViewById(R.id.email);
        sign_up=findViewById(R.id.sign_up);


        fAuth=FirebaseAuth.getInstance();

        FirebaseUser currentUser = fAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }

        textToSpeech=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

            }
        });


        show_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                    rootNode =FirebaseDatabase.getInstance();
                    reference=rootNode.getReference("Patient");


                    String FirstName=f_namee.getText().toString();
                    String LastName=l_namee.getText().toString();
                    String Email=emaill.getText().toString().trim();
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    String Password=pass.getText().toString().trim();
                    final String P_id=pid.getText().toString().trim();

                    final patientdata patientdata=new patientdata(FirstName,LastName,Password,Email);
                    reference.child(P_id).setValue(patientdata);




                    if(TextUtils.isEmpty(FirstName)) {
                        f_namee.setError("Please enter FirstName");
                        return;
                    }

                    if(!FirstName.matches("[a-zA-Z ]+"))
                    {
                    f_namee.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                    return;
                    }

                    if(TextUtils.isEmpty(LastName)) {
                        l_namee.setError("Please enter LastName");
                        return;
                    }

                    if(!LastName.matches("[a-zA-Z ]+"))
                    {
                        l_namee.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                        return;
                    }

                    if(TextUtils.isEmpty(Email))
                    {
                        emaill.setError("Please enter E-Mail");
                        return;
                    }

                    if(!Email.matches(emailPattern))
                    {
                        emaill.setError("invalid email");
                     }

                    if(TextUtils.isEmpty(Password))
                    {
                        pass.setError("Please enter Password");
                        return;
                     }

                    if(TextUtils.isEmpty(P_id))
                    {
                        pid.setError("Please enter Patient's Id");
                        return;
                    }

                    if(Password.length() < 6)
                    {
                        pass.setError("password must be > 6 characters");
                        return;
                    }

                    fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if(task.isSuccessful())
                            {

                                Toast.makeText(MainActivity.this,"Register Successfully",Toast.LENGTH_SHORT).show();
                                textToSpeech.speak("Register Successfully     ",TextToSpeech.QUEUE_FLUSH,null,null);
                                startActivity(new Intent(getApplicationContext(),MainActivity6.class));
                                FirebaseUser user = fAuth.getCurrentUser();
                                updateUI(user);
                                finish();

                            }

                            else
                            {
                                Toast.makeText(MainActivity.this,"Error!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                textToSpeech.speak("Error!,Failed To Register   ",TextToSpeech.QUEUE_FLUSH,null,null);
                                updateUI(null);
                            }


                        }
                    });


                    }






        });




    }

    private void reload() {


    }

    private void updateUI(FirebaseUser user) {


    }

}