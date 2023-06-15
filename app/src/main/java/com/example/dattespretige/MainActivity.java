package com.example.dattespretige;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dattespretige.Adapters.first_page_Adapter;
import com.example.dattespretige.Autre.Constants;
import com.example.dattespretige.Models.commande;
import com.example.dattespretige.Models.modelClientinfo;
import com.example.dattespretige.later.Matiere_premier;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /*-----------for navigation vue ---------------------*/
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    /*---------------------------------------------------*/
    RecyclerView recyclerView;
    first_page_Adapter adapter;
    FloatingActionButton new_com;

    String Key = null;
    boolean isLoading = false;
    TextView filterCommandeTV, name_info;
    ImageButton filterOrderBtn, warning;
    EditText search_bar;
    String timstamp = "" + System.currentTimeMillis();
    private ArrayList<modelClientinfo> commandeList, commandeencoursl, comterminerlist, comannullelist, commandeattentelist;
    Date date2;
    Date date1;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*-----------for navigation vue ---------------------*/
        Button crashButton = new Button(this);
//        crashButton.setText("Test Crash");
//        crashButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                throw new RuntimeException("Test Crash"); // Force a crash
//            }
//        });
//
//        addContentView(crashButton, new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT));
//
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        /*--------------------Tool Bar-------------------------*/
        setSupportActionBar(toolbar);
        /*-----------------------Navigation Drawer Menu----------------------------*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        /*-------------------------------------------------------------------*/
        recyclerView = findViewById(R.id.rv);
        new_com = findViewById(R.id.add_btn_command);
        filterOrderBtn = findViewById(R.id.filterOrderBtn);
        filterCommandeTV = findViewById(R.id.filterCommandeTV);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        warning = findViewById(R.id.warning);
        warning.setVisibility(View.GONE);
        search_bar = findViewById(R.id.search_bar);
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    adapter.getFilter().filter(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // loadData();
        loadFilterCommande("en cours");
        warning.setOnClickListener(v -> {
            AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
            adb.setTitle("Attention!!!");
            adb.setMessage("N'oubliez pas que vous avez :\n(" + commandeattentelist.size() + ") client(s) avec une ou plusieur commande(s) mise en attente");
            adb.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = adb.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();

        });

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        boolean isShowDialog = preferences.getBoolean("showDialog", true);
        if (isShowDialog) {
            ShowDialog();
        }
        new_com.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, new_command.class);
            startActivity(intent);
        });
        filterOrderBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Filter Commande")
                    .setItems(Constants.commandeStatus, (dialog, which) -> {
                        String selected = Constants.commandeStatus[which];
                        filterCommandeTV.setText(selected);
                        if (selected.equals("Showing All Order")) {
                            loadData();
                        } else {
                            loadFilterCommande(selected);
                        }
                    }).show();
        });
        statistiqueVerification();
    }

    private void statistiqueVerification() {
        loadprixdumois();
        loadprixdujour();
        loadprixofyear();
    }

    public void loadprixdumois() {
        DatabaseReference reference4 = FirebaseDatabase.getInstance().getReference("stat");
        reference4.child("byyear")
                .child(getDateyear(Long.parseLong(timstamp)))
                .child("bymois")
                .child(getDateMonth(Long.parseLong(timstamp)))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.child("price_M").exists()) {
                            updatestatisticsMonth();
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

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) {
                            updateStatisticsJours();
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
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.child("price_Y").exists()) {
                            updateStatisticsyear();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    private void updateStatisticsyear() {
        //prix du mois
        HashMap<String, Object> hashMap3 = new HashMap<>();
        hashMap3.put("price_Y", "" + (0));
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("stat");
        ref.child("byyear")
                .child(getDateyear(Long.parseLong(timstamp)))
                .updateChildren(hashMap3).addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Bonne journée :)", Toast.LENGTH_LONG).show();
                });
    }

    public void updatestatisticsMonth() {
        //prix du mois
        HashMap<String, Object> hashMap3 = new HashMap<>();
        hashMap3.put("price_M", "" + (0));
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("stat");
        ref.child("byyear")
                .child(getDateyear(Long.parseLong(timstamp)))
                .child("bymois")
                .child(getDateMonth(Long.parseLong(timstamp)))
                .updateChildren(hashMap3).addOnSuccessListener(unused -> {
                    //Toast.makeText(this, "add with suc", Toast.LENGTH_SHORT).show();
                });

    }

    public void updateStatisticsJours() {
        //prix du jours
        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("price_d", "" + (0));
        DatabaseReference reference4 = FirebaseDatabase.getInstance().getReference("stat");
        reference4.child("byyear")
                .child(getDateyear(Long.parseLong(timstamp)))
                .child("bymois")
                .child(getDateMonth(Long.parseLong(timstamp)))
                .child("byday").child(getDateday(Long.parseLong(timstamp))).
                setValue(hashMap2).addOnSuccessListener(unused -> {
                    //Toast.makeText(this, "add with suc", Toast.LENGTH_SHORT).show();
                });


    }


    private void ShowDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        adb.setTitle("Information d'utilisation");
        adb.setMessage("les nouvelle commande sont par defaut mis en cours pour modifier le status : \n->cliquez sur le client \n-> cliquez sur la commande \n->cliquez sur le boutton edite    \n\ncliquez sur Showwing all Order pour afficher tout les commandes");
        adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        adb.setNeutralButton("Don't show again", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                SharedPreferences settings = getSharedPreferences("PREFS", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("showDialog", false);
                // Commit the edits!
                editor.apply();
            }
        });
        AlertDialog alertDialog = adb.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void loadFilterCommande(String selected) {
        commandeList = new ArrayList<>();
        comannullelist = new ArrayList<>();
        commandeattentelist = new ArrayList<>();
        comterminerlist = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("commande2");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commandeList.clear();
                commandeattentelist.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String cours = "" + ds.child("cours").getValue();
                    String attente = "" + ds.child("attente").getValue();
                    String annule = "" + ds.child("annule").getValue();
                    String terminer = "" + ds.child("terminer").getValue();
                    if (!attente.equals("0")) {
                        modelClientinfo com = ds.getValue(modelClientinfo.class);
                        commandeattentelist.add(com);
                    }
                    if (commandeattentelist.size() != 0) {
                        warning.setVisibility(View.VISIBLE);
                        Animation ranim = (Animation) AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate);
                        warning.setAnimation(ranim);
                    } else {
                        warning.setVisibility(View.GONE);

                    }
                    if (selected.equals("en cours") && !cours.equals("0")) {
                        modelClientinfo com = ds.getValue(modelClientinfo.class);
                        commandeList.add(com);
                    }
                    if (selected.equals("attente") && !attente.equals("0")) {
                        modelClientinfo com = ds.getValue(modelClientinfo.class);
                        commandeList.add(com);
                    }
                    if (selected.equals("annule") && !annule.equals("0")) {
                        modelClientinfo com = ds.getValue(modelClientinfo.class);
                        commandeList.add(com);
                    }
                    if (selected.equals("terminer") && !terminer.equals("0")) {
                        modelClientinfo com = ds.getValue(modelClientinfo.class);
                        commandeList.add(com);
                    }

                }
                Collections.sort(commandeList, new Comparator<modelClientinfo>() {
                    @Override
                    public int compare(modelClientinfo o1, modelClientinfo o2) {
                        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                        if (o1.getTime_proche()!=null&&o2.getTime_proche()!=null) {
                            try {

                                date1 = formatter.parse(o1.getTime_proche());
                                date2 = formatter.parse(o2.getTime_proche());

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return Integer.compare((int) date1.getTime(), (int) date2.getTime());
                        }
                        return 0;
                    }
                });
                //setup the adapter
                adapter = new first_page_Adapter(MainActivity.this, commandeList);
                //set adapter
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

    private void loadData() {
        commandeList = new ArrayList<>();
        commandeattentelist = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("commande2");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commandeList.clear();
                commandeattentelist.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String attente = "" + ds.child("attente").getValue();
                    if (!attente.equals("0")) {
                        modelClientinfo com = ds.getValue(modelClientinfo.class);
                        commandeattentelist.add(com);
                    }
                    if (commandeattentelist.size() != 0) {
                        warning.setVisibility(View.VISIBLE);
                        Animation ranim = (Animation) AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate);
                        warning.setAnimation(ranim);
                    } else {
                        warning.setVisibility(View.GONE);
                    }
                    modelClientinfo com = ds.getValue(modelClientinfo.class);
                    assert com != null;
                    commandeList.add(com);
                    String uid = "" + ds.getRef().getKey();
                }
                /**
                 je ne peux trier tous les commandes par ordre decroissant
                 car les commande qui sont terminer reste les plus petite
                 **/

                //setup the adapter
                adapter = new first_page_Adapter(MainActivity.this, commandeList);
                //adapter = new first_page_Adapter(MainActivity.this,commandeList);
                //set adapter
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_new_commande:
                Intent intent = new Intent(MainActivity.this, new_command.class);
                startActivity(intent);
                break;
            case R.id.nav_Historique:
                Intent intent2 = new Intent(MainActivity.this, historique.class);
                startActivity(intent2);
                break;
            case R.id.nav_commancer_boulle:
                Intent intent3 = new Intent(MainActivity.this, boulleActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_M_Premier:
                Intent intent4 = new Intent(MainActivity.this, Matiere_premier.class);
                startActivity(intent4);
                break;
            case R.id.nav_commancer:
                Intent intent5 = new Intent(MainActivity.this, PreparationActivity.class);
                startActivity(intent5);
                break;
            case R.id.nav_Stat:
                Intent intent7 = new Intent(MainActivity.this, statistique.class);
                startActivity(intent7);
                break;
            case R.id.nav_Guide:

                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                adb.setTitle("Guide");
                adb.setMessage("les nouvelle commande sont par defaut mis en cours pour modifier le status : \n->cliquez sur le client \n-> cliquez sur la commande \n->cliquez sur le boutton edite    \n\ncliquez sur Showwing all Order pour afficher tout les commandes");

                adb.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = adb.create();
                alertDialog.show();

                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}