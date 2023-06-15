package com.example.dattespretige;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dattespretige.Models.commande;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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

public class new_command extends AppCompatActivity {
    private final String status = "attente";
    public Spinner ProductSpinner, spinnertype;
    public ArrayAdapter<CharSequence> ProductAdapter, typeAdapter;
    public TextInputEditText name, address, phone, time;
    //declaration des saveurs
    public EditText detail, price, quantite;
    public EditText Praliné, café, Caramel_beurre_salé, Truffe, Pistache, Noix_de_coco, Speculos, Amande_gout_rose, Amande_gout_orange, Citron, Gingember_citron_Vert, framboise, caramel_chocolate, amande_Rose, Amande_Orange, Amande_gingembre, Amande_kaab_ghzal, Pistache_beldi;
    public TextView total_tv;
    public AppCompatButton terminer_btn, add_btn;
    public Button save;
    public LinearLayout linearLayout, beldi, lineargin, linearcit, line1, linear;
    float prix;
    String timstamp, uid = "", name_ext;
    int total;
    Date date2;
    Date date1;
    private String SelectedProduct, SelectedType;//to hold the value of selected product
    private String Dname, Daddress, Dphone;
    private String Dtime, Ddetail, Dprice, Dstatus, DSelectedProduct, DSelectedType;
    private String Dquantite, DAmande_gout_orange, DPistache_beldi, DAmande_kaab_ghzal, DPraliné, Dcafe, DCaramel_beurre_salé, DAmande_gingembre, DAmande_Orange, Damande_Rose, Dcaramel_chocolate, Dframboise, DGingember_citron_Vert, DCitron, DAmande_gout_rose, DSpeculos, DTruffe, DPistache, DNoix_de_coco;
    private TextInputLayout textInputLayout, textInputLayout2, textInputLayout3;
    private String timstamp1;
    private ArrayList<commande> commandeencoursl, comterminerlist, comannullelist, commandeattentelist, maliste, maliste2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_commande);
//declaration and use of the spinner
        ProductSpinner = findViewById(R.id.spinnerProduct);
        beldi = findViewById(R.id.beldi);

        linearcit = findViewById(R.id.linearcit);
        line1 = findViewById(R.id.line1);
        lineargin = findViewById(R.id.lineargin);
        linear = findViewById(R.id.layout_root);

        spinnertype = findViewById(R.id.spinnertype);
        save = findViewById(R.id.save);
        uid = getIntent().getStringExtra("id_client");
        name_ext = getIntent().getStringExtra("name");
        Log.d("new_command", "id client :" + uid);
        textInputLayout = findViewById(R.id.textInputLayout);
        textInputLayout2 = findViewById(R.id.textInputLayout2);
        textInputLayout3 = findViewById(R.id.textInputLayout3);
        quantite = findViewById(R.id.quantite_text);
        linearLayout = findViewById(R.id.relativelayout);
        linearLayout.setVisibility(View.GONE);

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

        typeAdapter = ArrayAdapter.createFromResource(this, R.array.type_commande, R.layout.spinner_layout);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnertype.setAdapter(typeAdapter);
        spinnertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedType = parent.getItemAtPosition(position).toString();
                try {
                    if (SelectedType.equals("boulles")) {
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
                    Toast.makeText(new_command.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        name = findViewById(R.id.textInputName);
        address = findViewById(R.id.textInputAddress);
        phone = findViewById(R.id.textInputphone);
        time = findViewById(R.id.textInputTime);
        price = findViewById(R.id.price_text);
        time.setInputType(InputType.TYPE_NULL);
        time.setOnClickListener(v -> showDateTimeDialog(time));
        detail = findViewById(R.id.details);
        terminer_btn = findViewById(R.id.submit);
        total_tv = findViewById(R.id.total);


        //initialisation des saveurs
        Praliné = findViewById(R.id.Praliné_val);
        café = findViewById(R.id.Cafe_val);
        Caramel_beurre_salé = findViewById(R.id.Caramel_beurre_salé_val);
        Pistache = findViewById(R.id.Pistache_val);
        Noix_de_coco = findViewById(R.id.Noix_de_coco_val);
        Speculos = findViewById(R.id.Speculos_val);
        Amande_gout_rose = findViewById(R.id.Amande_gout_rose_val);
        Citron = findViewById(R.id.Citron_val);
        Gingember_citron_Vert = findViewById(R.id.Gingember_citron_Vert_val);
        framboise = findViewById(R.id.framboise_val);
        caramel_chocolate = findViewById(R.id.caramel_chocolate_val);
        amande_Rose = findViewById(R.id.amande_Rose_val);
        Amande_Orange = findViewById(R.id.Amande_Orange_val);
        Amande_gingembre = findViewById(R.id.Amande_gingembre_val);
        Amande_kaab_ghzal = findViewById(R.id.Amande_kaab_ghzal_val);
        Pistache_beldi = findViewById(R.id.Pistache_beldi_val);
        Amande_gout_orange = findViewById(R.id.Amande_gout_orange_val);
        Truffe = findViewById(R.id.Truffe_val);
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Caramel_beurre_salé.getText().toString().equals("")) {
                    Caramel_beurre_salé.setText("0");
                }
                if (Praliné.getText().toString().equals("")) {
                    Praliné.setText("0");
                }
                if (café.getText().toString().equals("")) {
                    café.setText("0");
                }
                if (Truffe.getText().toString().equals("")) {
                    Truffe.setText("0");
                }
                if (Pistache.getText().toString().equals("")) {
                    Pistache.setText("0");
                }
                if (Noix_de_coco.getText().toString().equals("")) {
                    Noix_de_coco.setText("0");
                }
                if (Speculos.getText().toString().equals("")) {
                    Speculos.setText("0");
                }
                if (Amande_gout_rose.getText().toString().equals("")) {
                    Amande_gout_rose.setText("0");
                }
                if (Citron.getText().toString().equals("")) {
                    Citron.setText("0");
                }
                if (Gingember_citron_Vert.getText().toString().equals("")) {
                    Gingember_citron_Vert.setText("0");
                }
                if (framboise.getText().toString().equals("")) {
                    framboise.setText("0");
                }
                if (caramel_chocolate.getText().toString().equals("")) {
                    caramel_chocolate.setText("0");
                }
                if (Pistache_beldi.getText().toString().equals("")) {
                    Pistache_beldi.setText("0");
                }
                if (amande_Rose.getText().toString().equals("")) {
                    amande_Rose.setText("0");
                }
                if (Amande_Orange.getText().toString().equals("")) {
                    Amande_Orange.setText("0");
                }
                if (Amande_gingembre.getText().toString().equals("")) {
                    Amande_gingembre.setText("0");
                }
                if (Amande_kaab_ghzal.getText().toString().equals("")) {
                    Amande_kaab_ghzal.setText("0");
                }
                if (Amande_gout_orange.getText().toString().equals("")) {
                    Amande_gout_orange.setText("0");
                }

                int praline = Integer.parseInt(Praliné.getText().toString());
                int caramel_val = Integer.parseInt(Caramel_beurre_salé.getText().toString());
                int cafe_val = Integer.parseInt(café.getText().toString());
                int Truffe_val = Integer.parseInt(Truffe.getText().toString());
                int Pistache_val = Integer.parseInt(Pistache.getText().toString());
                int Noix_de_coco_val = Integer.parseInt(Noix_de_coco.getText().toString());
                int Speculos_val = Integer.parseInt(Speculos.getText().toString());
                int Amande_gout_rose_val = Integer.parseInt(Amande_gout_rose.getText().toString());
                int Citron_val = Integer.parseInt(Citron.getText().toString());
                int Gingember_citron_Vert_val = Integer.parseInt(Gingember_citron_Vert.getText().toString());
                int framboise_val = Integer.parseInt(framboise.getText().toString());
                int caramel_chocolate_val = Integer.parseInt(caramel_chocolate.getText().toString());
                int amande_Rose_val = Integer.parseInt(amande_Rose.getText().toString());
                int Amande_Orange_val = Integer.parseInt(Amande_Orange.getText().toString());
                int Amande_gingembre_val = Integer.parseInt(Amande_gingembre.getText().toString());
                int Amande_kaab_ghzal_val = Integer.parseInt(Amande_kaab_ghzal.getText().toString());
                int Pistache_beldi_val = Integer.parseInt(Pistache_beldi.getText().toString());
                int Amande_gout_orange_val = Integer.parseInt(Amande_gout_orange.getText().toString());
                total = (praline + Amande_gout_orange_val + Pistache_beldi_val + Amande_kaab_ghzal_val + Amande_gingembre_val + Amande_Orange_val + amande_Rose_val + caramel_chocolate_val + framboise_val + Gingember_citron_Vert_val + Citron_val + Amande_gout_rose_val + Speculos_val + cafe_val + caramel_val + Truffe_val + Pistache_val + Noix_de_coco_val);

                total_tv.setText(total + " piéce");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
//do not forget to add a flavor
        Praliné.addTextChangedListener(textWatcher);
        café.addTextChangedListener(textWatcher);
        Caramel_beurre_salé.addTextChangedListener(textWatcher);
        Pistache.addTextChangedListener(textWatcher);
        Noix_de_coco.addTextChangedListener(textWatcher);
        Speculos.addTextChangedListener(textWatcher);
        Amande_gout_rose.addTextChangedListener(textWatcher);
        Citron.addTextChangedListener(textWatcher);
        Gingember_citron_Vert.addTextChangedListener(textWatcher);
        framboise.addTextChangedListener(textWatcher);
        caramel_chocolate.addTextChangedListener(textWatcher);
        amande_Rose.addTextChangedListener(textWatcher);
        Amande_Orange.addTextChangedListener(textWatcher);
        Amande_gingembre.addTextChangedListener(textWatcher);
        Amande_kaab_ghzal.addTextChangedListener(textWatcher);
        Pistache_beldi.addTextChangedListener(textWatcher);
        Amande_gout_orange.addTextChangedListener(textWatcher);
        Truffe.addTextChangedListener(textWatcher);
        if (!(uid == null)) {
            save.setVisibility(View.GONE);
            textInputLayout.setVisibility(View.GONE);
            textInputLayout2.setVisibility(View.GONE);
            textInputLayout3.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }
        save.setOnClickListener(v -> {
            submitinfoclient();
            save.setVisibility(View.GONE);
            textInputLayout.setVisibility(View.GONE);
            textInputLayout2.setVisibility(View.GONE);
            textInputLayout3.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        });
        add_btn = findViewById(R.id.plus);
        add_btn.setOnClickListener(v -> {
            addplusarticle();
        });
        terminer_btn.setOnClickListener(v -> {
            adddernierArticle();
            super.onBackPressed();
        });
    }

    private void gettext() {
        DPraliné = Praliné.getText().toString().trim();
        Dcafe = café.getText().toString().trim();
        DCaramel_beurre_salé = Caramel_beurre_salé.getText().toString().trim();
        DAmande_gout_orange = Amande_gout_orange.getText().toString().trim();
        DAmande_kaab_ghzal = Amande_kaab_ghzal.getText().toString().trim();
        DPistache_beldi = Pistache_beldi.getText().toString().trim();
        DAmande_gingembre = Amande_gingembre.getText().toString().trim();
        DAmande_Orange = Amande_Orange.getText().toString().trim();
        Damande_Rose = amande_Rose.getText().toString().trim();
        Dcaramel_chocolate = caramel_chocolate.getText().toString().trim();
        Dframboise = framboise.getText().toString().trim();
        DGingember_citron_Vert = Gingember_citron_Vert.getText().toString().trim();
        DCitron = Citron.getText().toString().trim();
        DAmande_gout_rose = Amande_gout_rose.getText().toString().trim();
        DSpeculos = Speculos.getText().toString().trim();
        DNoix_de_coco = Noix_de_coco.getText().toString().trim();
        DTruffe = Truffe.getText().toString().trim();
        DPistache = Pistache.getText().toString().trim();
        Dtime = time.getText().toString().trim();
        DSelectedProduct = SelectedProduct.trim();
        DSelectedType = SelectedType.trim();
        Ddetail = detail.getText().toString().trim();
        Dstatus = status.trim();
        Dprice = price.getText().toString().trim();
        Dquantite = quantite.getText().toString().trim();
        if (Dquantite.equals("0") || Dquantite.equals("")) {
            quantite.setText("1");
        }
        if (Dprice.equals("")) {
            price.setText("0");
        }
        Dprice = price.getText().toString().trim();
        Dquantite = quantite.getText().toString().trim();

    }

    private void submitinfoclient() {
        Dname = name.getText().toString().trim();
        Daddress = address.getText().toString().trim();
        Dphone = phone.getText().toString().trim();
        timstamp1 = "" + System.currentTimeMillis();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", "" + Dname);//enter le nom du client
        hashMap.put("address", "" + Daddress);
        hashMap.put("phone", "" + Dphone);
        hashMap.put("id_client", "" + timstamp1);
        hashMap.put("prix_encours", "" + "0");
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = db.getReference("commande2").child(timstamp1);
        databaseReference.setValue(hashMap).addOnSuccessListener(suc -> {
            Toast.makeText(this, "add with success", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private String uidval() {
        if (uid == null) {
            return timstamp1;
        } else {
            save.setVisibility(View.GONE);
            textInputLayout.setVisibility(View.GONE);
            textInputLayout2.setVisibility(View.GONE);
            textInputLayout3.setVisibility(View.GONE);
            return uid;
        }
    }

    private String nameval() {
        if (name_ext == null) {
            return Dname;
        } else {
            return name_ext;
        }

    }

    private void addprixtotal() {
        commandeencoursl = new ArrayList<>();
        comannullelist = new ArrayList<>();
        commandeattentelist = new ArrayList<>();
        comterminerlist = new ArrayList<>();
        maliste = new ArrayList<>();
        maliste2 = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("commande2");
        reference.child(uidval()).child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commandeencoursl.clear();
                comannullelist.clear();
                comterminerlist.clear();
                commandeattentelist.clear();
                maliste.clear();
                maliste2.clear();
                int i = 0;
                prix = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String commandestatus = "" + ds.child("status").getValue();
                    String price_fire = "" + ds.child("price").getValue();
                    String duplication = "" + ds.child("duplication").getValue();
                    //long date_con = Long.parseLong(date);
                    if (!commandestatus.equals("terminer") && !commandestatus.equals("anulle")) {
                        commande com2 = ds.getValue(commande.class);
                        maliste.add(com2);
                    } else {
                        commande com3 = ds.getValue(commande.class);
                        maliste2.add(com3);
                    }
                    int dup = 1;
                    if (!duplication.equals("") && !duplication.equals("0") && !duplication.equals("null")) {

                        dup = Integer.parseInt(duplication);
                    } else {
                        Toast.makeText(new_command.this, "la prochaine fois n'oubli(e) pas la quantité stp:)", Toast.LENGTH_LONG).show();
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
                Collections.sort(maliste, new Comparator<commande>() {
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
                HashMap<String, Object> hashMap = new HashMap<>();
                if (maliste.size() != 0) {
                    hashMap.put("time_proche", "" + maliste.get(0).getTime());
                }else if (maliste2.size()!=0){
                    hashMap.put("time_proche", "" + maliste2.get(maliste2.size()-1).getTime());
                }
                hashMap.put("prix_encours", "" + prix);
                hashMap.put("cours", "" + commandeencoursl.size());
                hashMap.put("attente", "" + commandeattentelist.size());
                hashMap.put("terminer", "" + comterminerlist.size());
                hashMap.put("annule", "" + comannullelist.size());

                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = db.getReference("commande2").child(uidval());
                databaseReference.updateChildren(hashMap).addOnSuccessListener(suc -> {

                }).addOnFailureListener(e -> {
                    Toast.makeText(new_command.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void addplusarticle() {
        addArticle();

        Caramel_beurre_salé.setText("0");
        Praliné.setText("0");
        café.setText("0");
        Truffe.setText("0");
        Pistache.setText("0");
        Noix_de_coco.setText("0");
        Speculos.setText("0");
        Amande_gout_rose.setText("0");
        Citron.setText("0");
        Gingember_citron_Vert.setText("0");
        framboise.setText("0");
        caramel_chocolate.setText("0");
        Pistache_beldi.setText("0");
        amande_Rose.setText("0");
        Amande_Orange.setText("0");
        Amande_gingembre.setText("0");
        Amande_kaab_ghzal.setText("0");
        Amande_gout_orange.setText("0");
        price.setText("0");
        quantite.setText("1");
    }

    private void adddernierArticle() {
        gettext();

        timstamp = "" + System.currentTimeMillis();
        HashMap<String, Object> hashMap = new HashMap<>();
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        hashMap.put("id", "" + timstamp);
        hashMap.put("id_client", "" + uidval());
        hashMap.put("duplication", "" + Dquantite);
        hashMap.put("type", "" + DSelectedType);
        hashMap.put("total", "" + total);
        hashMap.put("timstamp", "" + timstamp);
        hashMap.put("time", "" + Dtime);
        hashMap.put("name", "" + nameval());
        hashMap.put("product", "" + DSelectedProduct);
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

        DatabaseReference databaseReference2 = db.getReference("commande2");
        databaseReference2.child(uidval()).child("Orders").child(timstamp).setValue(hashMap).addOnSuccessListener(suc -> {
            Toast.makeText(this, "add with success", Toast.LENGTH_SHORT).show();

        }).addOnFailureListener(e -> {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        });

        addprixtotal();
        Intent intent = new Intent(new_command.this, MainActivity.class);
        startActivity(intent);
    }

    private void addArticle() {
        gettext();
        String timstamp = "" + System.currentTimeMillis();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", "" + timstamp);
        hashMap.put("duplication", "" + Dquantite);
        hashMap.put("time", "" + Dtime);
        hashMap.put("name", "" + nameval());
        hashMap.put("type", "" + DSelectedType);
        hashMap.put("total", "" + total);
        hashMap.put("timstamp", "" + timstamp);
        hashMap.put("id_client", "" + uidval());
        hashMap.put("product", "" + DSelectedProduct);
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
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference2 = db.getReference("commande2");
        databaseReference2.child(uidval()).child("Orders").child(timstamp).setValue(hashMap).addOnSuccessListener(suc -> {
            Toast.makeText(this, "add with success", Toast.LENGTH_SHORT).show();

        }).addOnFailureListener(e -> {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
        addprixtotal();


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
            new TimePickerDialog(new_command.this,
                    timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), false).show();
        };
        new DatePickerDialog(new_command.this,
                dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


}