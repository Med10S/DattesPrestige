package com.example.dattespretige;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dattespretige.Adapters.RVadapter;
import com.example.dattespretige.Autre.Constants;
import com.example.dattespretige.Models.commande;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class infoClientActivity extends AppCompatActivity {
    RecyclerView rv;
    ArrayList<commande> commandeList;
    RVadapter adapter;
    String name;
    Date date2;
    Date date1;
    String id_cl;
    String phone;
    private String id_client;
    private TextView Iname_tv, Iaddress_tv, IPrice_tv, IPhone_tv, filterCommandeTV;
    private ImageButton filterOrderBtn, edit_btn, back_btn, print_btn;
    private FloatingActionButton new_com;

    public static String getDate(long time) {
        Date date = (new Date(time));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return sdf.format(date);

    }

    public static String getDate2(long time) {
        Date date = (new Date(time));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(date);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_client);
        id_client = getIntent().getStringExtra("id_client");
        Iname_tv = findViewById(R.id.Iname_tv);
        Iaddress_tv = findViewById(R.id.Iaddress_tv);
        id_cl = getIntent().getStringExtra("id_client");
        IPrice_tv = findViewById(R.id.IPrice_tv);
        print_btn = findViewById(R.id.print_btn);
        filterCommandeTV = findViewById(R.id.filterCommandeTV);
        filterOrderBtn = findViewById(R.id.filterOrderBtn);
        rv = findViewById(R.id.rv1);
        new_com = findViewById(R.id.add_btn_command);
        IPhone_tv = findViewById(R.id.IPhone_tv);
        edit_btn = findViewById(R.id.edit_btn);
        back_btn = findViewById(R.id.back_btn);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
        edit_btn.setOnClickListener(v -> {
            Intent intent = new Intent(infoClientActivity.this, EditeClientDataActivity.class);
            intent.putExtra("id_client", id_client);
            startActivity(intent);
        });
        back_btn.setOnClickListener(v -> super.onBackPressed());

        new_com.setOnClickListener(v -> {
            Intent intent = new Intent(infoClientActivity.this, new_command.class);
            intent.putExtra("id_client", id_client);
            intent.putExtra("name", name);
            startActivity(intent);
        });
        loadclientinfo();
        loadFilterCommande("en cours");

        filterOrderBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(infoClientActivity.this);
            builder.setTitle("Filter Commande")
                    .setItems(Constants.commandeStatus, (dialog, which) -> {
                        String selected = Constants.commandeStatus[which];
                        if (selected.equals("en cours")) {
                            print_btn.setVisibility(View.VISIBLE);
                        } else {
                            print_btn.setVisibility(View.GONE);

                        }
                        filterCommandeTV.setText(selected);
                        if (selected.equals("Showing All Order")) {
                            loadCommandes();
                        } else {
                            loadFilterCommande(selected);
                        }
                    }).show();
        });
        print_btn.setOnClickListener(v -> {
            ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

            AlertDialog.Builder adb = new AlertDialog.Builder(infoClientActivity.this);
            View view = getLayoutInflater().inflate(R.layout.alertdialogepdf, null);
            final TextInputEditText livraison = view.findViewById(R.id.livraison);
            final TextInputEditText hauteur = view.findViewById(R.id.hauteur);
            Button terminer = view.findViewById(R.id.terminer);
            terminer.setOnClickListener(v1 -> {

                String pr_liv_string = livraison.getText().toString().trim();
                String Ha_string = hauteur.getText().toString().trim();
                int liv_int = 0;
                int Hauteur_int = 1000;
                try {
                    if (!pr_liv_string.equals("") && !pr_liv_string.equals("0") && !Ha_string.equals("") && !Ha_string.equals("0")) {
                        liv_int = Integer.parseInt(pr_liv_string);
                        Hauteur_int = Integer.parseInt(Ha_string);
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Veuillez enter des valeurs exact!!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                commandeList = new ArrayList<>();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("commande2");
                int finalHauteur_int = Hauteur_int;
                int finalLiv_int = liv_int;
                ref.child(id_client).child("Orders").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        commandeList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String commandestatus = "" + ds.child("status").getValue();
                            if (commandestatus.equals("en cours")) {
                                commande com = ds.getValue(commande.class);
                                commandeList.add(com);
                                int f = 1800;

                                Log.d("infoPdf10", commandeList.size() + " is the size");

                            }
                        }
                        printPDF(commandeList, finalHauteur_int, finalLiv_int);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            });
            adb.setView(view);
            AlertDialog alertDialog = adb.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();


        });


    }


    private void printPDF(ArrayList<commande> commandeList, int finalHauteur_int, int finalLiv_int) {
        long timstamp1 = System.currentTimeMillis();

        PdfDocument mypdfDocument = new PdfDocument();
        Paint paint = new Paint();

        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(1200, finalHauteur_int, 1).create();
        PdfDocument.Page mypage = mypdfDocument.startPage(myPageInfo);
        Canvas canvas = mypage.getCanvas();
        paint.setTextSize(30f);
        paint.setColor(Color.rgb(167, 144, 111));

        canvas.drawText("DATTES PRESTIGE", 80, 70, paint);
        paint.setColor(Color.BLACK);

        canvas.drawText("Les Jardins de la Rocade", 80, 50 + 55, paint);
        canvas.drawText("Hay Hassani,Casablanca", 80, 50 + 55 + 30, paint);
        canvas.drawText("Mme/Mr  " + name, 600, 70 + 40, paint);
        canvas.drawText("Téléphone: " + phone, 600, 150, paint);
        canvas.drawText("Facture n° : " + 1, 80, 105 + 65, paint);
        canvas.drawText("Le " + getDate(timstamp1), 80, 130 + 80, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawRect(20, 160 + 70, 1170, 200 + 90, paint);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setStyle(Paint.Style.FILL);
        int j = 80;
        canvas.drawText("Com.No. ", 40, 190 + j, paint);
        canvas.drawText("Produit ", 280, 190 + j, paint);
        canvas.drawText("prix ", 700, 190 + j, paint);
        canvas.drawText("Qty. ", 900, 190 + j, paint);
        canvas.drawText("Total. ", 1050, 190 + j, paint);
        canvas.drawLine(180, 160 + j, 180, 195 + j, paint);
        canvas.drawLine(680, 160 + j, 680, 195 + j, paint);
        canvas.drawLine(880, 160 + j, 880, 195 + j, paint);
        canvas.drawLine(990, 160 + j, 990, 195 + j, paint);

        Log.d("infoPdf", commandeList.size() + " is the size");
        int sub_total = 0;
        for (int k = 0; k < commandeList.size(); k++) {
            commande com = commandeList.get(k);
            String duplication = com.getDuplication();
            String price = com.getPrice();
            String selectedproduct = com.getProduct();
            Log.d("infoPdf2", selectedproduct + price + "x" + duplication + " is the size");

            canvas.drawText(" " + (k + 1), 90, 260 + j, paint);
            canvas.drawText(" " + selectedproduct, 240, 260 + j, paint);
            canvas.drawText(price + "DH", 710, 260 + j, paint);
            canvas.drawText("x" + duplication, 920, 260 + j, paint);
            int dup = 1;
            if (!duplication.equals("") && !duplication.equals("0") && !duplication.equals("null")) {

                dup = Integer.parseInt(duplication);
            }
            int pr = 1;
            if (!price.equals("") && !price.equals("null")) {
                pr = Integer.parseInt(price);
            }
            canvas.drawText((pr * dup) + " DH", 1010, 260 + j, paint);

            sub_total += pr * dup;
            j += 80;
        }


        canvas.drawLine(180, 300, 180, 300 + 80 * (commandeList.size()), paint);
        canvas.drawLine(680, 300, 680, 300 + 80 * (commandeList.size()), paint);
        canvas.drawLine(880, 300, 880, 300 + 80 * (commandeList.size()), paint);
        canvas.drawLine(990, 300, 990, 300 + 80 * (commandeList.size()), paint);

        canvas.drawLine(680, 310 + 80 * (commandeList.size()), 1170, 310 + 80 * (commandeList.size()), paint);
        float tot = 1;

        canvas.drawText("sub total :", 710, 350 + 80 * (commandeList.size()), paint);
        canvas.drawText("frais de livraison  :", 710, 400 + 80 * (commandeList.size()), paint);
        canvas.drawText(sub_total + " DH", 1030, 350 + 80 * (commandeList.size()), paint);
        canvas.drawText(finalLiv_int + " DH", 1030, 400 + 80 * (commandeList.size()), paint);
        paint.setColor(Color.rgb(167, 144, 111));
        canvas.drawRect(680, 440 + 80 * (commandeList.size()), 1170, 500 + 80 * (commandeList.size()), paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(40f);
        tot = finalLiv_int + sub_total;
        canvas.drawText("TOTAL :", 710, 480 + 80 * (commandeList.size()), paint);
        canvas.drawText(tot + " DH", 980, 480 + 80 * (commandeList.size()), paint);


        paint.setTextSize(25f);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qrcode);
        Bitmap scaledbmp = Bitmap.createScaledBitmap(bitmap, 300, 300, false);


        canvas.drawBitmap(scaledbmp, 900, finalHauteur_int - 340, paint);
        canvas.drawText("Contact us by phone : 0701589584 or scan the QR code", 300, finalHauteur_int - 40, paint);
        mypdfDocument.finishPage(mypage);

        File file = new File(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), getDate2(timstamp1) + " " + name + ".pdf");
        try {
            mypdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(this, "enregistrement terminer! ", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mypdfDocument.close();
        File outputFile = new File(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), getDate2(timstamp1) + " " + name + ".pdf");
        Uri uri = Uri.fromFile(outputFile);

        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.setType("application/pdf");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        //share.setPackage("com.whatsapp");

        startActivity(Intent.createChooser(share, "partager le PDF du client: " + name));

    }

    private String idval() {
        if (id_cl == null) {
            return id_client;
        } else {
            return id_cl;
        }

    }

    private void loadclientinfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("commande2");
        ref.child(idval()).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = "" + snapshot.child("name").getValue();
                String address = "" + snapshot.child("address").getValue();
                phone = "" + snapshot.child("phone").getValue();
                String total_price = "" + snapshot.child("prix_encours").getValue();
                Iname_tv.setText(name);
                Iaddress_tv.setText(address);
                IPhone_tv.setText(phone);
                IPrice_tv.setText(total_price + " DH");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /***private void chektimeofEnAttente() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("commende2");
        reference.child(id_client).child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String commandestatus = "" + ds.child("status").getValue();
                    String name_client = "" + ds.child("name").getValue();
                    String produit = "" + ds.child("product").getValue();
                    if (commandestatus.equals("en attente")) {
                        String time = "" + ds.child("time").getValue();
                        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                        try {
                            date2 = formatter.parse(time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        long timstimp = System.currentTimeMillis();
                        if (timstimp - date2.getTime() < 43200000*2) {
                            notification(name_client, produit, time);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void notification(String name_client, String produit, String time) {
        String id = "my_id0";
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = notificationManager.getNotificationChannel(id);
            if (channel==null){
                channel =new NotificationChannel(id,"Attention",NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription( "il vous reste moins de 12h pour une commande de "+produit+" pour le client : "+name_client+" et vous n'avez encore commencer à la preparer elle pour le :"+time);
                channel.enableVibration(true);
                channel.enableLights(true);
                channel.setVibrationPattern(new long[]{100,1000,200,340});
                channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                notificationManager.createNotificationChannel(channel);
            }
        }
    }**/

    private void loadFilterCommande(String selected) {
        commandeList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("commande2");
        ref.child(id_client).child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commandeList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String commandestatus = "" + ds.child("status").getValue();
                    if (selected.equals(commandestatus)) {
                        commande com = ds.getValue(commande.class);
                        commandeList.add(com);
                    }
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
                //setup the adapter
                adapter = new RVadapter(infoClientActivity.this, commandeList);
                //set adapter
                rv.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadCommandes() {
        commandeList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("commande2");
        reference.child(id_client).child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commandeList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    commande com = ds.getValue(commande.class);
                    commandeList.add(com);
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
                //setup the adapter
                adapter = new RVadapter(infoClientActivity.this, commandeList);
                //set adapter
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(infoClientActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}