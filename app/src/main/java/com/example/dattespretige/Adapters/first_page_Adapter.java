package com.example.dattespretige.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dattespretige.Autre.FilterProduct;
import com.example.dattespretige.R;
import com.example.dattespretige.Models.modelClientinfo;
import com.example.dattespretige.infoClientActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class first_page_Adapter extends RecyclerView.Adapter<first_page_Adapter.firstHolder> implements Filterable {
    public ArrayList<modelClientinfo> list, filterList,FilterList_s;
    private final Context context;
    private FilterProduct filterProduct;


    public first_page_Adapter(Context context, ArrayList<modelClientinfo> list) {
        this.context = context;
        this.list = list;
        this.filterList = list;
        this.FilterList_s=list;
    }

    @NonNull
    @Override
    public firstHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_commande_first, parent, false);
        return new firstHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull firstHolder holder, int position) {
        String name, prix_total, id, cour, attente, terminer, anulle,time_proche;
        modelClientinfo com = list.get(position);
        name = com.getName();
        prix_total = com.getPrix_encours();
        id = com.getId_client();
        cour = com.getCours();
        attente = com.getAttente();
        terminer = com.getTerminer();
        anulle = com.getAnnule();
        time_proche=com.getTime_proche();
        if (!(time_proche==null) &&!time_proche.equals("Pas pour Aujourd'hui")){
            holder.prise.setText(prix_total + " DH"+"\n"+time_proche);

        }else {
            holder.prise.setText(prix_total + " DH");

        }


        holder.nametv.setText(name);
       // holder.prise.setText(prix_total + " DH"+"\n"+time_proche);
        holder.cours.setText(cour + " en cours");
        holder.attente.setText(attente + " en attente");
        holder.teminer.setText(terminer + " terminer");
        holder.anuller.setText(anulle + " annuler");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, infoClientActivity.class);
            intent.putExtra("id_client", id);
            context.startActivity(intent);

        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        if (filterProduct==null){
            filterProduct=new FilterProduct(this,FilterList_s);
        }
        return filterProduct;
    }

    static class firstHolder extends RecyclerView.ViewHolder {
        TextView nametv, teminer, anuller, attente, cours, prise, total_product;

        public firstHolder(@NonNull View itemView) {
            super(itemView);
            nametv = itemView.findViewById(R.id.name_tv1);
            cours = itemView.findViewById(R.id.status_cours);
            teminer = itemView.findViewById(R.id.status_terminer);
            anuller = itemView.findViewById(R.id.status_anuller);
            attente = itemView.findViewById(R.id.status_attente);
            prise = itemView.findViewById(R.id.price_tv1);
        }
    }

}
