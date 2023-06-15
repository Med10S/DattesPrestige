package com.example.dattespretige;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dattespretige.Adapters.PrAdapter;
import com.example.dattespretige.Models.commande;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PreparationActivity extends AppCompatActivity {
    TextView textViewCol2, textViewCol3, textViewCol4, textViewCol5, textViewCol6, textViewCol7, textViewCol8, textViewCol9, textViewCol10, textViewCol11, textViewCol12, textViewCol13, textViewCol14, textViewCol15, textViewCol17, textViewCol16, textViewCol18, textViewCol19, clien_nbr;
    PrAdapter adapter;
    TextView totaldattes;
    RecyclerView recyclerViewprep;
    private ArrayList<commande> commandesaprepa;
    private int DAmande_gout_orange, DPistache_beldi, DAmande_kaab_ghzal, DPraliné, Dcafe, DCaramel_beurre_salé, DAmande_gingembre, DAmande_Orange, Damande_Rose, Dcaramel_chocolate, Dframboise, DGingember_citron_Vert, DCitron, DAmande_gout_rose, DSpeculos, DTruffe, DPistache, DNoix_de_coco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparation);
        recyclerViewprep = findViewById(R.id.rvadapter);
        textViewCol2 = findViewById(R.id.textViewCol2);
        textViewCol3 = findViewById(R.id.textViewCol3);
        textViewCol4 = findViewById(R.id.textViewCol4);
        textViewCol5 = findViewById(R.id.textViewCol5);
        textViewCol6 = findViewById(R.id.textViewCol6);
        textViewCol7 = findViewById(R.id.textViewCol7);
        textViewCol8 = findViewById(R.id.textViewCol8);
        textViewCol9 = findViewById(R.id.textViewCol9);
        textViewCol10 = findViewById(R.id.textViewCol10);
        textViewCol11 = findViewById(R.id.textViewCol11);
        textViewCol12 = findViewById(R.id.textViewCol12);
        textViewCol13 = findViewById(R.id.textViewCol13);
        textViewCol14 = findViewById(R.id.textViewCol14);
        textViewCol15 = findViewById(R.id.textViewCol15);
        textViewCol16 = findViewById(R.id.textViewCol16);
        textViewCol17 = findViewById(R.id.textViewCol17);
        textViewCol18 = findViewById(R.id.textViewCol18);
        textViewCol19 = findViewById(R.id.textViewCol19);
        totaldattes = findViewById(R.id.totaldattes);
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
                                String type ="" + ds2.child("type").getValue();

                                if (commandestatus.equals("en cours")&&type.equals("dattes")) {
                                    try {
                                        String duplication = "" + ds2.child("duplication").getValue();
                                        int dup = Integer.parseInt(duplication);
                                        Dcafe += Integer.parseInt("" + ds2.child("café").getValue())*dup;
                                        DTruffe = DTruffe + Integer.parseInt("" + ds2.child("Truffe").getValue())*dup;
                                        DAmande_gout_orange += Integer.parseInt("" + ds2.child("Amande_gout_orange").getValue())*dup;
                                        DPistache_beldi += Integer.parseInt("" + ds2.child("Pistache_beldi").getValue())*dup;
                                        DAmande_kaab_ghzal += Integer.parseInt("" + ds2.child("Amande_kaab_ghzal").getValue())*dup;
                                        DPraliné += Integer.parseInt("" + ds2.child("praliné").getValue())*dup;
                                        DCaramel_beurre_salé += Integer.parseInt("" + ds2.child("Caramel_beurre_salé").getValue())*dup;
                                        DAmande_gingembre += Integer.parseInt("" + ds2.child("Amande_gingembre").getValue())*dup;
                                        DAmande_Orange += Integer.parseInt("" + ds2.child("Amande_Orange").getValue())*dup;
                                        Damande_Rose += Integer.parseInt("" + ds2.child("amande_Rose").getValue())*dup;
                                        Dcaramel_chocolate += Integer.parseInt("" + ds2.child("caramel_chocolate").getValue())*dup;
                                        Dframboise += Integer.parseInt("" + ds2.child("framboise").getValue())*dup;
                                        DGingember_citron_Vert += Integer.parseInt("" + ds2.child("Gingember_citron_Vert").getValue())*dup;
                                        DCitron += Integer.parseInt("" + ds2.child("Citron").getValue())*dup;
                                        DAmande_gout_rose += Integer.parseInt("" + ds2.child("Amande_gout_rose").getValue())*dup;
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
                                    DCaramel_beurre_salé+DPraliné+DAmande_gout_rose+DAmande_gout_orange+DCitron+
                                    DGingember_citron_Vert+Dframboise+Dcaramel_chocolate+Damande_Rose+DAmande_Orange+
                                    DAmande_gingembre+ DAmande_kaab_ghzal+DPistache_beldi;
                            totaldattes.setText(total + "\ndattes");

                            textViewCol2.setText(String.valueOf(Dcafe));
                            textViewCol3.setText(String.valueOf(DTruffe));
                            textViewCol4.setText(String.valueOf(DPistache));
                            textViewCol5.setText(String.valueOf(DNoix_de_coco));
                            textViewCol6.setText(String.valueOf(DSpeculos));
                            textViewCol7.setText(String.valueOf(DCaramel_beurre_salé));
                            textViewCol8.setText(String.valueOf(DPraliné));
                            textViewCol9.setText(String.valueOf(DAmande_gout_rose));
                            textViewCol10.setText(String.valueOf(DAmande_gout_orange));
                            textViewCol11.setText(String.valueOf(DCitron));
                            textViewCol12.setText(String.valueOf(DGingember_citron_Vert));
                            textViewCol13.setText(String.valueOf(Dframboise));
                            textViewCol14.setText(String.valueOf(Dcaramel_chocolate));
                            textViewCol15.setText(String.valueOf(Damande_Rose));
                            textViewCol16.setText(String.valueOf(DAmande_Orange));
                            textViewCol17.setText(String.valueOf(DAmande_gingembre));
                            textViewCol18.setText(String.valueOf(DAmande_kaab_ghzal));
                            textViewCol19.setText(String.valueOf(DPistache_beldi));
                            clien_nbr.setText(commandesaprepa.size() + " Commandes(s)");
                            adapter = new PrAdapter(PreparationActivity.this, commandesaprepa);
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