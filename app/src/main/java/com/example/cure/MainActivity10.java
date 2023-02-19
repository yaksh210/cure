package com.example.cure;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity10 extends AppCompatActivity {
    EditText phone,mess_sen;
    Intent intent;
    Button btn;
    TextToSpeech textToSpeech;
    TextView t1,t2,t3,t4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);
        phone=findViewById(R.id.pat_phone);
        mess_sen=findViewById(R.id.message_sent);

        btn=findViewById(R.id.send_m);
        intent=getIntent();

        String done="Prescription :"+"\n"+"\n"+"Symptoms:"+intent.getStringExtra("sym")
                .concat("\n"+"Drugs:"+intent.getStringExtra("drug"))
                .concat("\n"+"Brand:"+intent.getStringExtra("bran"))
                .concat("\n"+"Dosage:"+intent.getStringExtra("dose"));
        TextView t1=(TextView)findViewById(R.id.message_sent);

        t1.setText(done);

        textToSpeech=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String num=phone.getText().toString().trim();

                if(TextUtils.isEmpty(num)){

                    phone.setError("Enter Patient's Mobile_number");
                }

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    if(checkSelfPermission(Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){

                        sendSms();
                    }
                    else {

                        requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                    }

                }



            }
        });




    }


    private void sendSms(){

        String phonenum=phone.getText().toString().trim();
        String sms=mess_sen.getText().toString().trim();


        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phonenum, null, sms, null, null);
            Toast.makeText(this, "message is sent", Toast.LENGTH_SHORT).show();
            textToSpeech.speak("message sent successfully",TextToSpeech.QUEUE_FLUSH,null,null);

            Intent i=new Intent(MainActivity10.this,MainActivity8.class);
            startActivity(i);
            finish();
        }catch (Exception e){

            e.printStackTrace();
            Toast.makeText(this, "failed to send message", Toast.LENGTH_SHORT).show();
            textToSpeech.speak("Failed to send message",TextToSpeech.QUEUE_FLUSH,null,null);

        }
    }




}