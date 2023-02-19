package com.example.cure;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;


public class MainActivity2 extends AppCompatActivity {
    TextToSpeech textToSpeech;
    EditText pass_doc,dfname,dlname,demail,fileup1,dd_id;
    CheckBox show_pass_doc;
    Button b1,sign_up_doc;
    FirebaseAuth dAuth;

    FirebaseDatabase rootNode;
    DatabaseReference dreference;


    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main2);

        pass_doc=findViewById(R.id.pass_doc);
        show_pass_doc=findViewById(R.id.show_pass_doc);
        dfname=findViewById(R.id.df_name);
        dlname=findViewById(R.id.dl_name);
        demail=findViewById(R.id.d_email);
        fileup1=findViewById(R.id.file_up1);
        b1=findViewById(R.id.up1);
        dd_id=findViewById(R.id.d_id);
        sign_up_doc=findViewById(R.id.sign_doc);


        dAuth=FirebaseAuth.getInstance();

        FirebaseUser currentUser = dAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }

        textToSpeech=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

            }
        });



        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        b1.setEnabled(false);
        fileup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectpdf();
            }



        });





        show_pass_doc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if(b)
                {
                    pass_doc.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    pass_doc.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

        sign_up_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                rootNode =FirebaseDatabase.getInstance();
                dreference=rootNode.getReference("Doctor");


                String doctorfname=dfname.getText().toString();
                String doctorlname=dlname.getText().toString();
                String Email=demail.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String Password=pass_doc.getText().toString().trim();
                String file1=fileup1.getText().toString();
                String D_id=dd_id.getText().toString();

                doctordata doctordata=new doctordata(doctorfname,doctorlname,Password,Email,file1,file1);
                dreference.child(D_id).setValue(doctordata);


                if(TextUtils.isEmpty(doctorfname))
                {
                    dfname.setError("please enter firstname");
                }
                if(!doctorfname.matches("[a-zA-Z ]+"))
                {
                    dfname.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                    return;
                }

                if(TextUtils.isEmpty(doctorlname))
                {
                    dlname.setError("please enter lastname");
                }
                if(!doctorlname.matches("[a-zA-Z ]+"))
                {
                    dlname.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                    return;
                }
                if(TextUtils.isEmpty(Email))
                {
                    demail.setError("please enter email");
                }

                if(!Email.matches(emailPattern))
                {
                    demail.setError("invalid email");
                }

                if(TextUtils.isEmpty(Password))
                {
                   pass_doc .setError("Please enter Password");
                    return;
                }
                if(TextUtils.isEmpty(file1))
                {
                    fileup1.setError("please upload your file");
                    return;
                }

                if(TextUtils.isEmpty(D_id))
                {
                    dd_id.setError("please enter Doctor's id");
                    return;
                }

                if(Password.length() < 6)
                {
                    pass_doc.setError("password must be > 6 characters");
                    return;
                }
                if(D_id.equals(dd_id))
                {
                    dd_id.setError("Id is used by another user");
                    return;
                }



                dAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful())
                        {
                            Toast.makeText(MainActivity2.this,"Register Successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity8.class));
                            textToSpeech.speak("Register Successfully   ",TextToSpeech.QUEUE_FLUSH,null,null);

                            FirebaseUser user = dAuth.getCurrentUser();
                            updateUI(user);
                            finish();

                        }

                        else
                        {
                            Toast.makeText(MainActivity2.this,"Error!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            textToSpeech.speak("Error!,Failed To Register    ",TextToSpeech.QUEUE_FLUSH,null,null);

                            updateUI(null);
                        }

                    }
                });




            }
        });


    }

    private void updateUI(FirebaseUser user) {
    }

    private void reload() {
    }


    private void selectpdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "PDF FILE SELECT"), 12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==12 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            b1.setEnabled(true);
            fileup1.setText(data.getDataString()
                    .substring(data.getDataString().lastIndexOf("/")+1));

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadPdfFileFirebase(data.getData());
                }
            });

        }

    }

    private void uploadPdfFileFirebase(Uri data)
    {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("File is Loading...");
        progressDialog.show();

        StorageReference reference=storageReference.child("upload"+System.currentTimeMillis()+".pdf");

        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask= taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                            Uri uri=uriTask.getResult();


                            doctordata doctordata=new doctordata(fileup1.getText().toString(),uri.toString());
                            databaseReference.child(databaseReference.push().getKey()).setValue(doctordata);
                            Toast.makeText(MainActivity2.this,"file upload",Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();


                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                double progress=(100.00 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("file uploaded..."+(int) progress+ "%");

            }
        });

    }

}