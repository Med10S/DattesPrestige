package com.example.dattespretige;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.dattespretige.Models.commande;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class EditCommandeActivity2 extends AppCompatActivity {
    public Spinner ProductSpinner, statuset;
    public ArrayAdapter<CharSequence> ProductAdapter, statusAdapter;
    public TextInputEditText timeet;
    //declaration des saveurs
    public EditText detailet, priceet, quantite;
    public EditText Pralinéet, caféet, Caramel_beurre_saléet, Truffeet, Pistacheet, Noix_de_cocoet, Speculoset, Amande_gout_roseet, Amande_gout_orangeet, Citronet, Gingember_citron_Vertet, framboiseet, caramel_chocolateet, amande_Roseet, Amande_Orangeet, Amande_gingembreet, Amande_kaab_ghzalet, Pistache_beldiet;
    public TextView total_tvet;
    public AppCompatButton editbtn;
    public LinearLayout beldi, lineargin, linearcit, line1, linear;
    ProgressDialog progressDialog;
    int total;
    String timstamp;
    int prI = 0;
    String gettimefromfonction;
    String date;
    String proche_commande_date;
    Date date2, date1, dateinclient;
    private String ComId, uid;
    private String SelectedProduct;//to hold the value of selected product
    private String status, status2;
    private ArrayList<commande> maliste2,commandeencoursl, comterminerlist, comannullelist, commandeattentelist;
    private ArrayList<Long> time;
    private String Dtime, Ddetail, Dquantite, Dprice, Dstatus, DSelectedProduct;
    private String DAmande_gout_orange, DPistache_beldi, DAmande_kaab_ghzal, DPraliné, Dcafe, DCaramel_beurre_salé, DAmande_gingembre, DAmande_Orange, Damande_Rose, Dcaramel_chocolate, Dframboise, DGingember_citron_Vert, DCitron, DAmande_gout_rose, DSpeculos, DTruffe, DPistache, DNoix_de_coco;
    private ImageButton delete_btn;

    public static String getDateyear(long time) {
        Date date = (new Date(time));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.getDefault());
        return sdf.format(date);

    }

    public static String getDateMonth(long time) {
        Date date = (new Date(time));
        SimpleDateFormat sdf = new SimpleDateFormat("MM", Locale.getDefault());
        return sdf.format(date);

    }

    public static String getDateday(long time) {
        Date date = (new Date(time));
        SimpleDateFormat sdf = new SimpleDateFormat("dd", Locale.getDefault());
        return sdf.format(date);

    }

    public static String getDate(long time) {
        Date date = (new Date(time));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.getDefault());
        return sdf.format(date);

    }

    //String timstamptest ="1674430784574"; pour tester sur un autre jours

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_commande2);
        ComId = getIntent().getStringExtra("ComId");
        uid = getIntent().getStringExtra("uid");
        //spinner status
        statuset = findViewById(R.id.statuset);
        quantite = findViewById(R.id.quantite_text);
        beldi = findViewById(R.id.beldi);
        linearcit = findViewById(R.id.linearcit);
        line1 = findViewById(R.id.line1);
        lineargin = findViewById(R.id.lineargin);
        linear = findViewById(R.id.layout_root);
        statusAdapter = ArrayAdapter.createFromResource(this, R.array.status_commande, R.layout.spinner_layout);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statuset.setPrompt("Etat de la commande : ");
        statuset.setAdapter(statusAdapter);
        statuset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//product spinner
        ProductSpinner = findViewById(R.id.spinnerProduct);
        ProductAdapter = ArrayAdapter.createFromResource(this, R.array.array_Product, R.layout.spinner_layout);
        ProductAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ProductSpinner.setAdapter(ProductAdapter);
        ProductSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedProduct = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        timeet = findViewById(R.id.textInputTime);
        priceet = findViewById(R.id.price_text);
        timeet.setInputType(InputType.TYPE_NULL);
        timeet.setOnClickListener(v -> {
            showDateTimeDialog(timeet);
        });
        detailet = findViewById(R.id.details);
        editbtn = findViewById(R.id.updatebtn);
        total_tvet = findViewById(R.id.total);
        //initialisation des saveurs
        Pralinéet = findViewById(R.id.Praliné_val);
        caféet = findViewById(R.id.Cafe_val);
        Caramel_beurre_saléet = findViewById(R.id.Caramel_beurre_salé_val);
        Pistacheet = findViewById(R.id.Pistache_val);
        Noix_de_cocoet = findViewById(R.id.Noix_de_coco_val);
        Speculoset = findViewById(R.id.Speculos_val);
        Amande_gout_roseet = findViewById(R.id.Amande_gout_rose_val);
        Citronet = findViewById(R.id.Citron_val);
        Gingember_citron_Vertet = findViewById(R.id.Gingember_citron_Vert_val);
        framboiseet = findViewById(R.id.framboise_val);
        caramel_chocolateet = findViewById(R.id.caramel_chocolate_val);
        amande_Roseet = findViewById(R.id.amande_Rose_val);
        Amande_Orangeet = findViewById(R.id.Amande_Orange_val);
        Amande_gingembreet = findViewById(R.id.Amande_gingembre_val);
        Amande_kaab_ghzalet = findViewById(R.id.Amande_kaab_ghzal_val);
        Pistache_beldiet = findViewById(R.id.Pistache_beldi_val);
        Amande_gout_orangeet = findViewById(R.id.Amande_gout_orange_val);
        Truffeet = findViewById(R.id.Truffe_val);

        loadCommandeDetails();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Caramel_beurre_saléet.getText().toString().equals("")) {
                    Caramel_beurre_saléet.setText("0");
                }
                if (Pralinéet.getText().toString().equals("")) {
                    Pralinéet.setText("0");
                }
                if (caféet.getText().toString().equals("")) {
                    caféet.setText("0");
                }
                if (Truffeet.getText().toString().equals("")) {
                    Truffeet.setText("0");
                }
                if (Pistacheet.getText().toString().equals("")) {
                    Pistacheet.setText("0");
                }
                if (Noix_de_cocoet.getText().toString().equals("")) {
                    Noix_de_cocoet.setText("0");
                }
                if (Speculoset.getText().toString().equals("")) {
                    Speculoset.setText("0");
                }
                if (Amande_gout_roseet.getText().toString().equals("")) {
                    Amande_gout_roseet.setText("0");
                }
                if (Citronet.getText().toString().equals("")) {
                    Citronet.setText("0");
                }
                if (Gingember_citron_Vertet.getText().toString().equals("")) {
                    Gingember_citron_Vertet.setText("0");
                }
                if (framboiseet.getText().toString().equals("")) {
                    framboiseet.setText("0");
                }
                if (caramel_chocolateet.getText().toString().equals("")) {
                    caramel_chocolateet.setText("0");
                }
                if (Pistache_beldiet.getText().toString().equals("")) {
                    Pistache_beldiet.setText("0");
                }
                if (amande_Roseet.getText().toString().equals("")) {
                    amande_Roseet.setText("0");
                }
                if (Amande_Orangeet.getText().toString().equals("")) {
                    Amande_Orangeet.setText("0");
                }
                if (Amande_gingembreet.getText().toString().equals("")) {
                    Amande_gingembreet.setText("0");
                }
                if (Amande_kaab_ghzalet.getText().toString().equals("")) {
                    Amande_kaab_ghzalet.setText("0");
                }
                if (Amande_gout_orangeet.getText().toString().equals("")) {
                    Amande_gout_orangeet.setText("0");
                }
                try {
                    int praline = Integer.parseInt(Pralinéet.getText().toString());
                    int caramel_val = Integer.parseInt(Caramel_beurre_saléet.getText().toString());
                    int cafe_val = Integer.parseInt(caféet.getText().toString());
                    int Truffe_val = Integer.parseInt(Truffeet.getText().toString());
                    int Pistache_val = Integer.parseInt(Pistacheet.getText().toString());
                    int Noix_de_coco_val = Integer.parseInt(Noix_de_cocoet.getText().toString());
                    int Speculos_val = Integer.parseInt(Speculoset.getText().toString());
                    int Amande_gout_rose_val = Integer.parseInt(Amande_gout_roseet.getText().toString());
                    int Citron_val = Integer.parseInt(Citronet.getText().toString());
                    int Gingember_citron_Vert_val = Integer.parseInt(Gingember_citron_Vertet.getText().toString());
                    int framboise_val = Integer.parseInt(framboiseet.getText().toString());
                    int caramel_chocolate_val = Integer.parseInt(caramel_chocolateet.getText().toString());
                    int amande_Rose_val = Integer.parseInt(amande_Roseet.getText().toString());
                    int Amande_Orange_val = Integer.parseInt(Amande_Orangeet.getText().toString());
                    int Amande_gingembre_val = Integer.parseInt(Amande_gingembreet.getText().toString());
                    int Amande_kaab_ghzal_val = Integer.parseInt(Amande_kaab_ghzalet.getText().toString());
                    int Pistache_beldi_val = Integer.parseInt(Pistache_beldiet.getText().toString());
                    int Amande_gout_orange_val = Integer.parseInt(Amande_gout_orangeet.getText().toString());
                    total = (praline + Amande_gout_orange_val + Pistache_beldi_val + Amande_kaab_ghzal_val + Amande_gingembre_val + Amande_Orange_val + amande_Rose_val + caramel_chocolate_val + framboise_val + Gingember_citron_Vert_val + Citron_val + Amande_gout_rose_val + Speculos_val + cafe_val + caramel_val + Truffe_val + Pistache_val + Noix_de_coco_val);

                    total_tvet.setText(total + " piéce");
                } catch (Exception e) {
                    Toast.makeText(EditCommandeActivity2.this, "edite" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
//do not forget to add a flavor
        Pralinéet.addTextChangedListener(textWatcher);
        caféet.addTextChangedListener(textWatcher);
        Caramel_beurre_saléet.addTextChangedListener(textWatcher);
        Pistacheet.addTextChangedListener(textWatcher);
        Noix_de_cocoet.addTextChangedListener(textWatcher);
        Speculoset.addTextChangedListener(textWatcher);
        Amande_gout_roseet.addTextChangedListener(textWatcher);
        Citronet.addTextChangedListener(textWatcher);
        Gingember_citron_Vertet.addTextChangedListener(textWatcher);
        framboiseet.addTextChangedListener(textWatcher);
        caramel_chocolateet.addTextChangedListener(textWatcher);
        amande_Roseet.addTextChangedListener(textWatcher);
        Amande_Orangeet.addTextChangedListener(textWatcher);
        Amande_gingembreet.addTextChangedListener(textWatcher);
        Amande_kaab_ghzalet.addTextChangedListener(textWatcher);
        Pistache_beldiet.addTextChangedListener(textWatcher);
        Amande_gout_orangeet.addTextChangedListener(textWatcher);
        Truffeet.addTextChangedListener(textWatcher);

        editbtn.setOnClickListener(v -> {
            inputData();
            super.onBackPressed();
        });
        delete_btn = findViewById(R.id.delete_btn);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("commande2");
        reference.child(uid).child("Orders").child(ComId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String status = "" + snapshot.child("status").getValue();
                if (status.equals("en cours") || status.equals("terminer")) {
                    delete_btn.setVisibility(View.GONE);
                } else {
                    delete_btn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        delete_btn.setOnClickListener(v -> {
            AlertDialog.Builder adb = new AlertDialog.Builder(EditCommandeActivity2.this);
            adb.setTitle("Attention !");
            adb.setIcon(R.drawable.ic_warning_24);
            adb.setMessage("êtes-vous sur de vouloir supprimer cette commande");
            adb.setPositiveButton("oui ", (dialog, which) -> {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("commande2");
                ref.child(uid).child("Orders").child(ComId).removeValue()
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(EditCommandeActivity2.this, "commande supprimer...", Toast.LENGTH_SHORT).show();
                            updateprixstatclient();
                            outtime();
                            EditCommandeActivity2.super.onBackPressed();

                        }).addOnFailureListener(e -> {
                            Toast.makeText(EditCommandeActivity2.this, "probleme de suppression !!! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            });
            adb.setNeutralButton("Non anuller", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = adb.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        });

    }

    private void loadCommandeDetails() {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("commande2");
        reference1.child(uid).child("Orders").child(ComId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String id = "" + snapshot.child("id").getValue();
                String duplication = "" + snapshot.child("duplication").getValue();
                String time = "" + snapshot.child("time").getValue();
                String product = "" + snapshot.child("product").getValue();
                String details = "" + snapshot.child("details").getValue();
                String status = "" + snapshot.child("status").getValue();
                String price = "" + snapshot.child("price").getValue();
                String café = "" + snapshot.child("café").getValue();
                String praliné = "" + snapshot.child("praliné").getValue();
                String Caramel_beurre_salé = "" + snapshot.child("Caramel_beurre_salé").getValue();
                String Pistache = "" + snapshot.child("Pistache").getValue();
                String Noix_de_coco = "" + snapshot.child("Noix_de_coco").getValue();
                String Speculos = "" + snapshot.child("Speculos").getValue();
                String Amande_gout_rose = "" + snapshot.child("Amande_gout_rose").getValue();
                String Citron = "" + snapshot.child("Citron").getValue();
                String Gingember_citron_Vert = "" + snapshot.child("Gingember_citron_Vert").getValue();
                String framboise = "" + snapshot.child("framboise").getValue();
                String caramel_chocolate = "" + snapshot.child("caramel_chocolate").getValue();
                String amande_Rose = "" + snapshot.child("amande_Rose").getValue();
                String Amande_Orange = "" + snapshot.child("Amande_Orange").getValue();
                String Amande_gingembre = "" + snapshot.child("Amande_gingembre").getValue();
                String Amande_kaab_ghzal = "" + snapshot.child("Amande_kaab_ghzal").getValue();
                String Pistache_beldi = "" + snapshot.child("Pistache_beldi").getValue();
                String Amande_gout_orange = "" + snapshot.child("Amande_gout_orange").getValue();
                String Truffe = "" + snapshot.child("Truffe").getValue();
                String typecommande = "" + snapshot.child("type").getValue();

                try {
                    if (typecommande.equals("boulles")) {
                        linearcit.setVisibility(View.GONE);
                        line1.setVisibility(View.GONE);
                        lineargin.setVisibility(View.GONE);
                        linear.setVisibility(View.GONE);
                        beldi.setVisibility(View.GONE);
                    } else {
                        linearcit.setVisibility(View.VISIBLE);
                        line1.setVisibility(View.VISIBLE);
                        lineargin.setVisibility(View.VISIBLE);
                        linear.setVisibility(View.VISIBLE);
                        beldi.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {

                }

                timeet.setText(time);
                int spinnerPosition = ProductAdapter.getPosition(product);
                ProductSpinner.setSelection(spinnerPosition);
                int spinnerPosition2 = statusAdapter.getPosition(status);
                statuset.setSelection(spinnerPosition2);
                quantite.setText(duplication);
                detailet.setText(details);
                //statuset.setText(status);
                priceet.setText(price);
                caféet.setText(café);
                Caramel_beurre_saléet.setText(Caramel_beurre_salé);
                Pistacheet.setText(Pistache);
                Noix_de_cocoet.setText(Noix_de_coco);
                Speculoset.setText(Speculos);
                Amande_gout_roseet.setText(Amande_gout_rose);
                Pralinéet.setText(praliné);
                Citronet.setText(Citron);
                Gingember_citron_Vertet.setText(Gingember_citron_Vert);
                framboiseet.setText(framboise);
                caramel_chocolateet.setText(caramel_chocolate);
                amande_Roseet.setText(amande_Rose);
                Amande_Orangeet.setText(Amande_Orange);
                Amande_gingembreet.setText(Amande_gingembre);
                Amande_kaab_ghzalet.setText(Amande_kaab_ghzal);
                Pistache_beldiet.setText(Pistache_beldi);
                Amande_gout_orangeet.setText(Amande_gout_orange);
                Truffeet.setText(Truffe);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void inputData() {
        DPraliné = Pralinéet.getText().toString().trim();
        Dcafe = caféet.getText().toString().trim();
        DCaramel_beurre_salé = Caramel_beurre_saléet.getText().toString().trim();
        DAmande_gout_orange = Amande_gout_orangeet.getText().toString().trim();
        DAmande_kaab_ghzal = Amande_kaab_ghzalet.getText().toString().trim();
        DPistache_beldi = Pistache_beldiet.getText().toString().trim();
        DAmande_gingembre = Amande_gingembreet.getText().toString().trim();
        DAmande_Orange = Amande_Orangeet.getText().toString().trim();
        Damande_Rose = amande_Roseet.getText().toString().trim();
        Dcaramel_chocolate = caramel_chocolateet.getText().toString().trim();
        Dframboise = framboiseet.getText().toString().trim();
        DGingember_citron_Vert = Gingember_citron_Vertet.getText().toString().trim();
        DCitron = Citronet.getText().toString().trim();
        DAmande_gout_rose = Amande_gout_roseet.getText().toString().trim();
        DSpeculos = Speculoset.getText().toString().trim();
        DNoix_de_coco = Noix_de_cocoet.getText().toString().trim();
        DTruffe = Truffeet.getText().toString().trim();
        DPistache = Pistacheet.getText().toString().trim();

        Dtime = timeet.getText().toString().trim();
        DSelectedProduct = SelectedProduct.trim();
        Ddetail = detailet.getText().toString().trim();
        Dstatus = status;
        Dprice = priceet.getText().toString().trim();
        Dquantite = quantite.getText().toString().trim();
        if (Dquantite.equals("0") || Dquantite.equals("")) {
            quantite.setText("1");
        }
        Dquantite = quantite.getText().toString().trim();
        if (Dprice.equals("")) {
            priceet.setText("0");
        }
        Dprice = priceet.getText().toString().trim();
        updateCommande();

    }

    public void updateCommande() {
        timstamp = "" + System.currentTimeMillis();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("time", "" + Dtime);
        hashMap.put("product", "" + DSelectedProduct);
        hashMap.put("total", "" + total);
        hashMap.put("timstamp", "" + timstamp);
        hashMap.put("duplication", "" + Dquantite);
        hashMap.put("details", "" + Ddetail);
        hashMap.put("status", "" + Dstatus);
        hashMap.put("price", "" + Dprice);
        hashMap.put("café", "" + Dcafe);
        hashMap.put("praliné", "" + DPraliné);
        hashMap.put("Caramel_beurre_salé", "" + DCaramel_beurre_salé);
        hashMap.put("Pistache", "" + DPistache);
        hashMap.put("Noix_de_coco", "" + DNoix_de_coco);
        hashMap.put("Speculos", "" + DSpeculos);
        hashMap.put("Amande_gout_rose", "" + DAmande_gout_rose);
        hashMap.put("Citron", "" + DCitron);
        hashMap.put("Gingember_citron_Vert", "" + DGingember_citron_Vert);
        hashMap.put("framboise", "" + Dframboise);
        hashMap.put("caramel_chocolate", "" + Dcaramel_chocolate);
        hashMap.put("amande_Rose", "" + Damande_Rose);
        hashMap.put("Amande_Orange", "" + DAmande_Orange);
        hashMap.put("Amande_gingembre", "" + DAmande_gingembre);
        hashMap.put("Amande_kaab_ghzal", "" + DAmande_kaab_ghzal);
        hashMap.put("Pistache_beldi", "" + DPistache_beldi);
        hashMap.put("Amande_gout_orange", "" + DAmande_gout_orange);
        hashMap.put("Truffe", "" + DTruffe);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("commande2");
        reference.child(uid).child("Orders").child(ComId).updateChildren(hashMap).addOnSuccessListener(unused -> {
            Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

        });
        outtime();
        updateprixstatclient();
        loadprixdujour();
        loadprixdumois();
        loadprixofyear();
    }

    /**
     * cette partie cert a enregister le prix des commandes terminer
     * en premier lieu si les donnes exite j'extracte l'information
     * en deuxieme lieux j'ajoute cette valeur a la valeur du prix de la commande
     * si la donner n'existe pas le prix alors j'ajoute cette valeur avec un zero initialiser au debut
     * mais le probleme c'est que si le client fait dans le jour 1 terminer alors c'est bien
     * dans le deuxieme jours na rien fait donc j'aurais pas cette donné
     * donc je doit faire une boucle si snapchot.child(getdatejour(timestimp).exist) ne fait rien si non met un zero
     * je dois implementer ce code dans une partie toujour active (MainActivity!!) oui c'a a marche :=)
     * fonction name :statistiqueVerification()
     **/

    public void loadprixdumois() {
        DatabaseReference reference4 = FirebaseDatabase.getInstance().getReference("stat");
        reference4.child("byyear")
                .child(getDateyear(Long.parseLong(timstamp)))
                .child("bymois")
                .child(getDateMonth(Long.parseLong(timstamp)))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    int taman_mois = 0;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child("price_M").exists()) {
                            String price = "" + snapshot.child("price_M").getValue();
                            taman_mois = Integer.parseInt(price);
                            updatestatisticsMonth(taman_mois);

                            Log.d("prix du mois", "" + taman_mois);
                        } else {
                            updatestatisticsMonth(taman_mois);

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    public void loadprixdujour() {
        DatabaseReference reference4 = FirebaseDatabase.getInstance().getReference("stat");
        reference4.child("byyear")
                .child(getDateyear(Long.parseLong(timstamp)))
                .child("bymois")
                .child(getDateMonth(Long.parseLong(timstamp)))
                .child("byday").child(getDateday(Long.parseLong(timstamp)))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    int taman_jrs = 0;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String price = "" + snapshot.child("price_d").getValue();
                            taman_jrs = Integer.parseInt(price);
                            Log.d("prix du mois", "" + prI);
                            updateStatisticsJours(taman_jrs);
                        } else {
                            updateStatisticsJours(taman_jrs);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    public void loadprixofyear() {
        DatabaseReference reference4 = FirebaseDatabase.getInstance().getReference("stat");
        reference4.child("byyear")
                .child(getDateyear(Long.parseLong(timstamp)))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    int taman_3am = 0;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child("price_Y").exists()) {
                            String price = "" + snapshot.child("price_Y").getValue();
                            taman_3am = Integer.parseInt(price);
                            Log.d("prix du mois", "" + prI);
                            updateStatisticsyear(taman_3am);
                        } else {
                            updateStatisticsyear(taman_3am);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    private void updateStatisticsyear(int taman_3am) {
        //prix du mois
        if (Dstatus.equals("terminer")) {
            int prI = Integer.parseInt(Dprice);
            int Qty = Integer.parseInt(Dquantite);
            int tot_j = ((prI * Qty) + taman_3am);
            HashMap<String, Object> hashMap3 = new HashMap<>();
            hashMap3.put("price_Y", "" + (tot_j));
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("stat");
            ref.child("byyear")
                    .child(getDateyear(Long.parseLong(timstamp)))
                    .updateChildren(hashMap3).addOnSuccessListener(unused -> {
                        Toast.makeText(this, "add with suc", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    public void updatestatisticsMonth(int taman_mois) {
        //prix du mois
        if (Dstatus.equals("terminer")) {
            int prI = Integer.parseInt(Dprice);
            int Qty = Integer.parseInt(Dquantite);
            int tot_j = ((prI * Qty) + taman_mois);
            HashMap<String, Object> hashMap3 = new HashMap<>();
            hashMap3.put("price_M", "" + (tot_j));
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("stat");
            ref.child("byyear")
                    .child(getDateyear(Long.parseLong(timstamp)))
                    .child("bymois")
                    .child(getDateMonth(Long.parseLong(timstamp)))
                    .updateChildren(hashMap3).addOnSuccessListener(unused -> {
                        Toast.makeText(this, "add with suc", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    public void updateStatisticsJours(int prix_du_j) {
        Log.d("prix j ", "prixdu jours" + prix_du_j);
        if (Dstatus.equals("terminer")) {
            //prix du jours
            int prI = Integer.parseInt(Dprice);
            int Qty = Integer.parseInt(Dquantite);
            int tot_j = ((prI * Qty) + prix_du_j);
            HashMap<String, Object> hashMap2 = new HashMap<>();
            hashMap2.put("price_d", "" + (tot_j));
            DatabaseReference reference4 = FirebaseDatabase.getInstance().getReference("stat");
            reference4.child("byyear")
                    .child(getDateyear(Long.parseLong(timstamp)))
                    .child("bymois")
                    .child(getDateMonth(Long.parseLong(timstamp)))
                    .child("byday").child(getDateday(Long.parseLong(timstamp))).
                    setValue(hashMap2).addOnSuccessListener(unused -> {
                        Toast.makeText(this, "add with suc", Toast.LENGTH_SHORT).show();
                    });


        }
    }

    private void outtime() {
        commandeencoursl = new ArrayList<>();
        maliste2 = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("commande2");
        reference.child(uid).child("Orders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commandeencoursl.clear();
                maliste2.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String commandestatus = "" + ds.child("status").getValue();
                    date = "" + ds.child("time").getValue();
                    if (!commandestatus.equals("terminer")&&!commandestatus.equals("anulle")){
                        commande com = ds.getValue(commande.class);
                        commandeencoursl.add(com);
                    } else {
                        commande com3 = ds.getValue(commande.class);
                        maliste2.add(com3);
                    }
                }
                Collections.sort(commandeencoursl, new Comparator<commande>() {
                    @Override
                    public int compare(commande o1, commande o2) {
                        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                        try {
                            date1 = formatter.parse(o1.getTime());
                            date2 = formatter.parse(o2.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return Integer.compare((int) date1.getTime(), (int) date2.getTime());
                    }
                });
                Collections.sort(maliste2, new Comparator<commande>() {
                    @Override
                    public int compare(commande o1, commande o2) {
                        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                        try {
                            date1 = formatter.parse(o1.getTime());
                            date2 = formatter.parse(o2.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return Integer.compare((int) date1.getTime(), (int) date2.getTime());
                    }
                });
               // Log.d("le temps le plus proche est :", "" + commandeencoursl.get(0).getTime());
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = db.getReference("commande2").child(uid);
                HashMap<String, Object> hashMap = new HashMap<>();
                if (maliste2.size()==0){

                }
                if(commandeencoursl.size()!=0){
                    hashMap.put("time_proche", "" + commandeencoursl.get(0).getTime());
                }else if (maliste2.size()!=0){
                    hashMap.put("time_proche", "" + maliste2.get(maliste2.size()-1).getTime());
                }else if (maliste2.size()==0){
                    databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(EditCommandeActivity2.this, "client supprimer", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditCommandeActivity2.this, "erreur dans la suppression du client", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

                databaseReference.updateChildren(hashMap).addOnSuccessListener(suc -> {

                }).addOnFailureListener(e -> {
                    Toast.makeText(EditCommandeActivity2.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void updateprixstatclient() {
        commandeencoursl = new ArrayList<>();
        comannullelist = new ArrayList<>();
        commandeattentelist = new ArrayList<>();
        comterminerlist = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("commande2");
        reference.child(uid).child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    commandeencoursl.clear();
                    comannullelist.clear();
                    comterminerlist.clear();
                    commandeattentelist.clear();
                    //String selected = "en cours";
                    int i = 0;
                    int prix = 0;
                    int prix2 = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String commandestatus = "" + ds.child("status").getValue();
                        String price_fire = "" + ds.child("price").getValue();
                        String duplication = "" + ds.child("duplication").getValue();
                        int dup = 1;
                        if (!duplication.equals("") && !duplication.equals("0") && !duplication.equals("null")) {

                            dup = Integer.parseInt(duplication);
                        } else {
                            Toast.makeText(EditCommandeActivity2.this, "la prochaine fois n'oubli(e) pas la quantité stp:)", Toast.LENGTH_LONG).show();
                        }
                        if (commandestatus.equals("en cours")) {
                            i++;
                            if (!price_fire.equals("") && !price_fire.equals("null")) {
                                int pr = Integer.parseInt(price_fire);
                                prix += pr * dup;
                            }


                            commande com = ds.getValue(commande.class);
                            commandeencoursl.add(com);
                        }
                        if (commandestatus.equals("attente")) {
                            commande com = ds.getValue(commande.class);
                            commandeattentelist.add(com);
                        }
                        if (commandestatus.equals("terminer")) {
                            commande com = ds.getValue(commande.class);
                            comterminerlist.add(com);
                        }
                        if (commandestatus.equals("annule")) {
                            commande com = ds.getValue(commande.class);
                            comannullelist.add(com);
                        }
                    }

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("prix_encours", "" + prix);
                    hashMap.put("cours", "" + commandeencoursl.size());
                    hashMap.put("attente", "" + commandeattentelist.size());
                    hashMap.put("terminer", "" + comterminerlist.size());
                    hashMap.put("annule", "" + comannullelist.size());

                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = db.getReference("commande2").child(uid);
                    databaseReference.updateChildren(hashMap).addOnSuccessListener(suc -> {

                    }).addOnFailureListener(e -> {
                        Toast.makeText(EditCommandeActivity2.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }

    private void showDateTimeDialog(TextInputEditText time) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    time.setText(simpleDateFormat.format(calendar.getTime()));
                }
            };
            new TimePickerDialog(EditCommandeActivity2.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
        };
        new DatePickerDialog(EditCommandeActivity2.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}