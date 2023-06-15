package com.example.dattespretige;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.dattespretige.Adapters.RVadapter;
import com.example.dattespretige.Autre.Constants;
import com.example.dattespretige.Models.commande;
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

public class historique extends AppCompatActivity {
    RecyclerView recyclerView;
    RVadapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    boolean isLoading = false;
    Date date1, date2;
    EditText search_bar;
    ImageButton filterOrderBtn;
    private ArrayList<commande> commandeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);
        recyclerView = findViewById(R.id.rv_historique);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        search_bar = findViewById(R.id.search_bar2);
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
        loadData();

        filterOrderBtn=findViewById(R.id.filterOrderBtn2);
        filterOrderBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(historique.this);
            builder.setTitle("Filter Commande")
                    .setItems(Constants.commandeStatus, (dialog, which) -> {
                        String selected = Constants.commandeStatus[which];
                        if (selected.equals("Showing All Order")) {
                            loadData();
                        } else {
                            loadFilterCommande(selected);
                        }
                    }).show();
        });
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItem = linearLayoutManager.getItemCount();
                int lastVisible = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (totalItem < lastVisible + 3) {
                    if (!isLoading) {
                        isLoading = true;
                        loadData();
                    }
                }
            }
        });

    }


    private void loadData() {
        commandeList = new ArrayList<>();
        //swipeRefreshLayout.setRefreshing(true);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("commande2");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commandeList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String client_id = ds.getKey();
                    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("commande2");
                    assert client_id != null;
                    reference2.child(client_id).child("Orders").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds2 : snapshot.getChildren()) {
                                commande com = ds2.getValue(commande.class);
                                String status = "" + ds2.child("status").getValue();
                                Log.d("historique for status", "" + status);
                                commandeList.add(com);
                                Log.d("historique", "commandeList size is " + commandeList.size());
                            }
                            Collections.sort(commandeList, new Comparator<commande>() {
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
                            adapter = new RVadapter(historique.this, commandeList);
                            //set adapter
                            recyclerView.setAdapter(adapter);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
                //setup the adapter

                // swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //swipeRefreshLayout.setRefreshing(false);

            }
        });


    }
    private void loadFilterCommande(String selected){
        commandeList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("commande2");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commandeList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String client_id = ds.getKey();
                    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("commande2");
                    assert client_id != null;
                    reference2.child(client_id).child("Orders").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds2 : snapshot.getChildren()) {
                                String status = "" + ds2.child("status").getValue();
                                if (selected.equals(status)){
                                    commande com = ds2.getValue(commande.class);
                                    commandeList.add(com);
                                }
                                Log.d("historique for status", "" + status);
                                Log.d("historique", "commandeList size is " + commandeList.size());
                            }
                            Collections.sort(commandeList, new Comparator<commande>() {
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
                            adapter = new RVadapter(historique.this, commandeList);
                            //set adapter
                            recyclerView.setAdapter(adapter);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}