package com.example.textrecognition;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

//    https://developers.google.com/ml-kit/vision/text-recognition/android#java
    private final int PICK_IMAGE = 1;
    TextRecognizer recognizer;
    ImageView img_pickedImg;
    Button btn_imgPick;
    InputImage inputImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img_pickedImg = findViewById(R.id.img_pickedImg);
        btn_imgPick = findViewById(R.id.btn_picImage);

        btn_imgPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
                
            }
        });

        recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            Log.d("MyLog", selectedImage.toString());
            img_pickedImg.setImageURI(selectedImage);

            try {
                inputImage = InputImage.fromFilePath(this, selectedImage);
                Toast.makeText(this, "InputImage set successed", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}