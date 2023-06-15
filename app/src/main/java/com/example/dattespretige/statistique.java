package com.example.dattespretige;

import static com.example.dattespretige.MainActivity.getDateyear;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.androidplot.xy.XYPlot;
import com.example.dattespretige.Models.commande;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class statistique extends AppCompatActivity {
    LineChart lineChart;
    Button mois_btn, anne_btn;
    LinearLayout linear_mois, linear_annee;
    ArrayList barArraylist;
    BarChart barchart_year;
    BarChart barchart_Mois;
    BarDataSet barDataSet;
    ImageButton setyear,back_btn;
    String month_id;
    private XYPlot plot;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistique);
        barchart_year = findViewById(R.id.barchart_year);
        barchart_Mois = findViewById(R.id.barchart_Mois);
        mois_btn = findViewById(R.id.mois_btn);
        anne_btn = findViewById(R.id.anne_btn);
        back_btn = findViewById(R.id.back_btn);
        setyear = findViewById(R.id.setyear);
        linear_mois = findViewById(R.id.linear_mois);
        linear_annee = findViewById(R.id.linear_annee);
        mois_btn.setBackground(getResources().getDrawable(R.drawable.shape_rect_gris));
        long timpstimp = System.currentTimeMillis();
        String year = getDateyear(timpstimp);
        getdatamois(year);
        setyear.setOnClickListener(v -> {
            AlertDialog.Builder adb = new AlertDialog.Builder(statistique.this);
            View view = getLayoutInflater().inflate(R.layout.alertdialogstatistics2, null);
            final TextInputEditText annes = view.findViewById(R.id.annes);
            Button terminer = view.findViewById(R.id.terminer_anne);
            adb.setView(view);
            AlertDialog alertDialog = adb.create();
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.show();
            terminer.setOnClickListener(v1 -> {
                String date = annes.getText().toString().trim();
                try {
                    getdatamois(date);
                    alertDialog.dismiss();
                } catch (Exception e) {
                    Toast.makeText(this, "Veuillez choisir une datte valide !!!", Toast.LENGTH_SHORT).show();

                    getdatamois(year);
                }
            });

        });
        mois_btn.setOnClickListener(v -> {
            setyear.setVisibility(View.VISIBLE);
            linear_mois.setVisibility(View.VISIBLE);
            linear_annee.setVisibility(View.GONE);
            mois_btn.setBackground(getResources().getDrawable(R.drawable.shape_rect_gris));
            anne_btn.setBackground(getResources().getDrawable(R.drawable.chape_rect1));

            Log.d("date of this year", "" + year);
            getdatamois(year);

        });
        anne_btn.setOnClickListener(v -> {
            setyear.setVisibility(View.GONE);
            linear_annee.setVisibility(View.VISIBLE);
            linear_mois.setVisibility(View.GONE);
            mois_btn.setBackground(getResources().getDrawable(R.drawable.chape_rect1));
            anne_btn.setBackground(getResources().getDrawable(R.drawable.shape_rect_gris));
            getdata();

        });
        back_btn.setOnClickListener(v -> super.onBackPressed());

    }

    private void getdatamois(String year) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("stat");
        ref.child("byyear").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String year_id = dataSnapshot.getKey();
                    barArraylist = new ArrayList();
                    //Log.d("years",""+year_id);
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("stat");
                    reference.child("byyear").child(year).child("bymois").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int i = 1;
                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                month_id = dataSnapshot1.getKey();
                                DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("stat");
                                int finalI = i;
                                ref2.child("byyear").child(year).child("bymois").child(month_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            String month_string_val = "" + snapshot.child("price_M").getValue();
                                            float month_f_val = Integer.parseInt(month_string_val);
                                            barchart_Mois.animateY(1500);
                                            barArraylist.add(new BarEntry(finalI, month_f_val));

                                            ValueFormatter xAxisFormatter = new DayAxisValueFormatter(barchart_Mois);
                                            XAxis xAxis = barchart_Mois.getXAxis();
                                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                            xAxis.setDrawGridLines(false);
                                            xAxis.setGranularity(1f); // only intervals of 1 day
                                            xAxis.setLabelCount(7);
                                            xAxis.setValueFormatter(xAxisFormatter);
                                            barDataSet = new BarDataSet(barArraylist, "Revenue en DH (" + year + ")");
                                            Log.d("barArraylist size ", "" + barArraylist.size());
                                            BarData barData = new BarData(barDataSet);
                                            barchart_Mois.setMaxVisibleValueCount(15);
                                            barchart_Mois.setFitBars(true);
                                            barchart_Mois.getDescription().setEnabled(false);
                                            barchart_Mois.setData(barData);
                                            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                                            barDataSet.setValueTextSize(16f);
                                            barchart_Mois.invalidate();
                                            barchart_Mois.notifyDataSetChanged();

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                i++;

                            }
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

    private void getdata() {
        DatabaseReference reference4 = FirebaseDatabase.getInstance().getReference("stat");
        reference4.child("byyear")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        barArraylist = new ArrayList();
                        int i = 2023;
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String year_id = ds.getKey();
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("stat");
                            int finalI = i;
                            assert year_id != null;
                            ref.child("byyear").child(year_id).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        String year_string_val = "" + snapshot.child("price_Y").getValue();
                                        float year_f_val = Integer.parseInt(year_string_val);
                                        barchart_year.animateY(1500);
                                        barArraylist.add(new BarEntry(finalI, year_f_val));
                                        Log.d("barArraylist size 2", "" + barArraylist.size());
                                        XAxis xAxis = barchart_year.getXAxis();
                                        xAxis.setGranularity(1f);
                                        barDataSet = new BarDataSet(barArraylist, "Revenue en DH");
                                        Log.d("barArraylist size ", "" + barArraylist.size());
                                        BarData barData = new BarData(barDataSet);

                                        barchart_year.setFitBars(true);
                                        barchart_year.setData(barData);
                                        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                                        barDataSet.setValueTextSize(16f);
                                        barchart_year.getDescription().setEnabled(false);
                                        barchart_year.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                            i++;
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    public static class DayAxisValueFormatter extends ValueFormatter {
        private final BarLineChartBase<?> barchart_Mois;

        public DayAxisValueFormatter(BarLineChartBase<?> barchart_Mois) {
            this.barchart_Mois = barchart_Mois;
        }

        @Override
        public String getFormattedValue(float value) {
            return "Mois " + (int) value;
        }
    }


}