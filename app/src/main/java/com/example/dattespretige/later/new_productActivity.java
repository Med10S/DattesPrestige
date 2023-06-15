package com.example.dattespretige.later;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.dattespretige.R;
import com.google.android.material.textfield.TextInputEditText;

public class new_productActivity extends AppCompatActivity {
    ImageButton back_btn;
    ImageView pro_image;
    TextInputEditText title,description,price;
    private static final int CAMERA_REQUEST_CODE= 200;
    private static final int STORAGE_REQUEST_CODE= 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        back_btn=findViewById(R.id.back_btn);
        pro_image=findViewById(R.id.pro_image);
        title=findViewById(R.id.title);
        description=findViewById(R.id.description);
        price=findViewById(R.id.price);
    }

}