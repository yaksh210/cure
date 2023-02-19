package com.example.cure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity9 extends AppCompatActivity {


    Spinner sp1, sp2, sp3, sp4;
    Button conf;
    ImageButton mic;


    SpeechRecognizer mSpeechRecognizer;
    Intent mSpeechRecognizerIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main9);

        sp1 = findViewById(R.id.Sympomts_spin);
        sp2 = findViewById(R.id.Drug_spin);
        sp3 = findViewById(R.id.brand_spin);
        sp4 = findViewById(R.id.dosage_spin);
        conf = findViewById(R.id.confirm);
        mic = findViewById(R.id.btn_mic);

        checkPermission();

        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(MainActivity9.this, MainActivity10.class);
                i.putExtra("sym", sp1.getSelectedItem().toString());

                i.putExtra("drug", sp2.getSelectedItem().toString());
                i.putExtra("bran", sp3.getSelectedItem().toString());
                i.putExtra("dose", sp4.getSelectedItem().toString());

                startActivity(i);
                finish();
            }
        });


        ArrayAdapter adapter = ArrayAdapter.createFromResource(

                this,
                R.array.Symptoms,
                R.layout.color_spinner
        );
        adapter.setDropDownViewResource(R.layout.spinner_drop);
        sp1.setAdapter(adapter);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(

                this,
                R.array.Drugs,
                R.layout.color_spinner
        );
        adapter2.setDropDownViewResource(R.layout.spinner_drop);
        sp2.setAdapter(adapter2);

        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(

                this,
                R.array.Brand,
                R.layout.color_spinner
        );
        adapter3.setDropDownViewResource(R.layout.spinner_drop);
        sp3.setAdapter(adapter3);

        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(

                this,
                R.array.Dosage,
                R.layout.color_spinner
        );
        adapter4.setDropDownViewResource(R.layout.spinner_drop);
        sp4.setAdapter(adapter4);


        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {

            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {



            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });


        findViewById(R.id.btn_mic).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer.stopListening();


                        break;

                    case MotionEvent.ACTION_DOWN:
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        break;
                }


                return false;
            }
        });


    }

    private void checkPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (!(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {

                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }

        }


    }
}
