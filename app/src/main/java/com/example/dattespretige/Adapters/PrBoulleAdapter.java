package com.example.dattespretige.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dattespretige.Models.commande;
import com.example.dattespretige.R;

import java.util.ArrayList;

public class PrBoulleAdapter extends RecyclerView.Adapter<PrBoulleAdapter.HolderPrepration> {

    public ArrayList<commande> list, filterList;
    private Context context;

    public PrBoulleAdapter(Context context, ArrayList<commande> list) {
        this.context = context;
        this.list = list;
        this.filterList = list;
    }

    @NonNull
    @Override
    public HolderPrepration onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.holderpreparationboulle, parent, false);
        return new HolderPrepration(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HolderPrepration holder, int position) {
        //get data
        String name, café, Truffe, Pistache, Noix_de_coco, Speculos, praliné,
                framboise, caramel_chocolate, total,
                Amande_gout_orange, duplication;
        commande com = list.get(position);
        duplication = com.getDuplication();
        total = com.getTotal();
        int dup = Integer.parseInt(duplication);
        café = com.getCafé();
        int ca = Integer.parseInt(café);
        name = com.getName();
        Truffe = com.getTruffe();
        int truf = Integer.parseInt(Truffe);
        Pistache = com.getPistache();
        int pist = Integer.parseInt(Pistache);
        Noix_de_coco = com.getNoix_de_coco();
        int noix = Integer.parseInt(Noix_de_coco);
        Speculos = com.getSpeculos();
        int spe = Integer.parseInt(Speculos);
        caramel_chocolate = com.getCaramel_chocolate();
        int cracho = Integer.parseInt(caramel_chocolate);
        praliné = com.getPraliné();
        int pra = Integer.parseInt(praliné);
        framboise = com.getFramboise();
        int fran = Integer.parseInt(framboise);
        Amande_gout_orange = com.getAmande_gout_orange();
        int ao = Integer.parseInt(Amande_gout_orange);
        //set data
        if (!praliné.equals("0")) {
            holder.pralinétv.setTextColor(context.getResources().getColor(R.color.principale1));
        }
        if (!café.equals("0")) {
            holder.cafétv.setTextColor(context.getResources().getColor(R.color.principale1));
        }
        if (!Truffe.equals("0")) {
            holder.Truffetv.setTextColor(context.getResources().getColor(R.color.principale1));
        }
        if (!Pistache.equals("0")) {
            holder.Pistachetv.setTextColor(context.getResources().getColor(R.color.principale1));
        }
        if (!Noix_de_coco.equals("0")) {
            holder.Noix_de_cocotv.setTextColor(context.getResources().getColor(R.color.principale1));
        }
        if (!Speculos.equals("0")) {
            holder.Speculostv.setTextColor(context.getResources().getColor(R.color.principale1));
        }
        if (!framboise.equals("0")) {
            holder.framboisetv.setTextColor(context.getResources().getColor(R.color.principale1));
        }
        if (!caramel_chocolate.equals("0")) {
            holder.caramel_chocolatetv.setTextColor(context.getResources().getColor(R.color.principale1));
        }
        holder.nametv.setText(name + "(" + dup + "x" + total + ")");
        holder.cafétv.setText("" + ca * dup);
        holder.Truffetv.setText("" + truf * dup);
        holder.Pistachetv.setText("" + pist * dup);
        holder.Noix_de_cocotv.setText("" + noix * dup);
        holder.Speculostv.setText("" + spe * dup);
        holder.caramel_chocolatetv.setText("" + cracho * dup);
        holder.pralinétv.setText("" + pra * dup);
        holder.framboisetv.setText("" + fran * dup);
        holder.Amande_Orangetv.setText("" + ao * dup);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class HolderPrepration extends RecyclerView.ViewHolder {
        TextView nametv, cafétv, Truffetv, Pistachetv, Noix_de_cocotv, Speculostv, pralinétv,
                framboisetv, caramel_chocolatetv, Amande_Orangetv;

        public HolderPrepration(@NonNull View itemView) {
            super(itemView);
            nametv = itemView.findViewById(R.id.textViewCol1);
            cafétv = itemView.findViewById(R.id.textViewCol2);
            Truffetv = itemView.findViewById(R.id.textViewCol3);
            Pistachetv = itemView.findViewById(R.id.textViewCol4);
            Noix_de_cocotv = itemView.findViewById(R.id.textViewCol5);
            Speculostv = itemView.findViewById(R.id.textViewCol6);
            pralinétv = itemView.findViewById(R.id.textViewCol7);
            framboisetv = itemView.findViewById(R.id.textViewCol8);
            caramel_chocolatetv = itemView.findViewById(R.id.textViewCol9);
            Amande_Orangetv = itemView.findViewById(R.id.textViewCol10);
        }
    }
}
