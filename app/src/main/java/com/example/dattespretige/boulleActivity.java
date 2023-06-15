package com.example.dattespretige;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.dattespretige.Adapters.PrAdapter;
import com.example.dattespretige.Adapters.PrBoulleAdapter;
import com.example.dattespretige.Models.commande;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class boulleActivity extends AppCompatActivity {
    TextView textViewCol2, textViewCol3, textViewCol4, textViewCol5, textViewCol6, textViewCol7, textViewCol8, textViewCol9, textViewCol10, clien_nbr;
    PrBoulleAdapter adapter;
    TextView totaldattes;
    RecyclerView recyclerViewprep;
    private ArrayList<commande> commandesaprepa;
    private int DAmande_gout_orange, DPraliné, Dcafe, Dcaramel_chocolate, Dframboise, DSpeculos, DTruffe, DPistache, DNoix_de_coco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boulle);
        recyclerViewprep = findViewById(R.id.rvadapter);
        totaldattes = findViewById(R.id.totalboulles);
        textViewCol2 = findViewById(R.id.textViewCol2);
        textViewCol3 = findViewById(R.id.textViewCol3);
        textViewCol4 = findViewById(R.id.textViewCol4);
        textViewCol5 = findViewById(R.id.textViewCol5);
        textViewCol6 = findViewById(R.id.textViewCol6);
        textViewCol7 = findViewById(R.id.textViewCol7);
        textViewCol8 = findViewById(R.id.textViewCol8);
        textViewCol9 = findViewById(R.id.textViewCol9);
        textViewCol10 = findViewById(R.id.textViewCol10);
        clien_nbr = findViewById(R.id.nbrclient);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewprep.setLayoutManager(layoutManager);
        recyclerViewprep.setAdapter(adapter);
        loadPrarationdata();
    }
    private void loadPrarationdata() {
        commandesaprepa = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("commande2");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commandesaprepa.clear();
                Log.d("PreparationActivity", "ana hna");

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String client_id = ds.getKey();

                    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("commande2");
                    assert client_id != null;
                    reference2.child(client_id).child("Orders").addValueEventListener(new ValueEventListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds2 : snapshot.getChildren()) {
                                String commandestatus = "" + ds2.child("status").getValue();
                                String commandetype = ""+ds2.child("type").getValue();

                                if (commandestatus.equals("en cours")&&commandetype.equals("boulles")) {
                                    try {
                                        String duplication = "" + ds2.child("duplication").getValue();
                                        int dup = Integer.parseInt(duplication);
                                        Dcafe += Integer.parseInt("" + ds2.child("café").getValue())*dup;
                                        DTruffe = DTruffe + Integer.parseInt("" + ds2.child("Truffe").getValue())*dup;
                                        DAmande_gout_orange += Integer.parseInt("" + ds2.child("Amande_gout_orange").getValue())*dup;
                                        DPraliné += Integer.parseInt("" + ds2.child("praliné").getValue())*dup;
                                        Dcaramel_chocolate += Integer.parseInt("" + ds2.child("caramel_chocolate").getValue())*dup;
                                        Dframboise += Integer.parseInt("" + ds2.child("framboise").getValue())*dup;
                                        DSpeculos += Integer.parseInt("" + ds2.child("Speculos").getValue())*dup;
                                        DNoix_de_coco += Integer.parseInt("" + ds2.child("Noix_de_coco").getValue())*dup;
                                        DPistache += Integer.parseInt("" + ds2.child("Pistache").getValue())*dup;
                                    } catch (Exception e) {
                                        // Toast.makeText(PreparationActivity.this, " vous avez oubliez d'enter les valeur a votre commande " + e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                    commande com = ds2.getValue(commande.class);

                                    commandesaprepa.add(com);
                                    Log.d("PreparationActivity", "size of the list for preparatiokn is :" + commandesaprepa.size());

                                }

                            }
                            int total=Dcafe+DTruffe+DPistache+DNoix_de_coco+DSpeculos+
                                    DPraliné+DAmande_gout_orange+Dframboise+Dcaramel_chocolate;
                            totaldattes.setText(total + "\nboulles");
                            textViewCol2.setText(String.valueOf(Dcafe));
                            textViewCol3.setText(String.valueOf(DTruffe));
                            textViewCol4.setText(String.valueOf(DPistache));
                            textViewCol5.setText(String.valueOf(DNoix_de_coco));
                            textViewCol6.setText(String.valueOf(DSpeculos));
                            textViewCol7.setText(String.valueOf(DPraliné));
                            textViewCol8.setText(String.valueOf(Dframboise));
                            textViewCol9.setText(String.valueOf(Dcaramel_chocolate));
                            textViewCol10.setText(String.valueOf(DAmande_gout_orange));

                            clien_nbr.setText(commandesaprepa.size() + " Commandes(s)");
                            adapter = new PrBoulleAdapter(boulleActivity.this, commandesaprepa);
                            recyclerViewprep.setAdapter(adapter);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
                //apres la dernier for


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //handle databaseError
            }
        });

    }

}