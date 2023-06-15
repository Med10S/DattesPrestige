package com.example.dattespretige;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditeClientDataActivity extends AppCompatActivity {
    public TextInputEditText nametv, addresstv, phonetv, time;
    String id_client;
    private Button save;
    private String Dname, Daddress, Dphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite_client_data);
        id_client = getIntent().getStringExtra("id_client");
        save = findViewById(R.id.save);

        nametv = findViewById(R.id.textInputName);
        addresstv = findViewById(R.id.textInputAddress);
        phonetv = findViewById(R.id.textInputphone);


        loadclientinfo();
        save.setOnClickListener(v -> {
            updateclientInfo();
            super.onBackPressed();

        });
    }
    private void loadclientinfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("commande2");
        ref.child(id_client).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = "" + snapshot.child("name").getValue();
                String address = "" + snapshot.child("address").getValue();
                String phone = "" + snapshot.child("phone").getValue();
                nametv.setText(name);
                addresstv.setText(address);
                phonetv.setText(phone);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateclientInfo() {
        Dname = nametv.getText().toString().trim();
        Daddress = addresstv.getText().toString().trim();
        Dphone = phonetv.getText().toString().trim();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("commande2");
        ref.child(id_client).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("name", "" + Dname);
                hashMap.put("address", "" + Daddress);
                hashMap.put("phone", "" + Dphone);
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = db.getReference("commande2").child(id_client);
                databaseReference.updateChildren(hashMap).addOnSuccessListener(suc -> {
                    Toast.makeText(EditeClientDataActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                }).addOnFailureListener(e -> {
                    Toast.makeText(EditeClientDataActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}