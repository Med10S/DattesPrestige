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

import com.example.dattespretige.R;
import com.example.dattespretige.Models.commande;

import java.util.ArrayList;

public class PrAdapter extends RecyclerView.Adapter<PrAdapter.HolderPrepration> {

    public ArrayList<commande> list, filterList;
    private Context context;

    public PrAdapter(Context context, ArrayList<commande> list) {
        this.context = context;
        this.list = list;
        this.filterList = list;
    }

    @NonNull
    @Override
    public HolderPrepration onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hoderpreparation, parent, false);
        return new HolderPrepration(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HolderPrepration holder, int position) {
        //get data
        String name, café, Truffe, Pistache, Noix_de_coco, Speculos, Caramel_beurre_salé, praliné,total,
                Amande_gout_rose, Amande_gout_orange, Citron,
                Gingember_citron_Vert, framboise, caramel_chocolate,
                amande_Rose, Amande_Orange, Amande_gingembre, Amande_kaab_ghzal, Pistache_beldi, duplication;
        commande com = list.get(position);
        duplication = com.getDuplication();
        total= com.getTotal();
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
        Amande_gout_rose = com.getAmande_gout_rose();
        int agr = Integer.parseInt(Amande_gout_rose);
        Amande_gout_orange = com.getAmande_gout_orange();
        int aro = Integer.parseInt(Amande_gout_orange);
        Citron = com.getCitron();
        int ci = Integer.parseInt(Citron);
        Gingember_citron_Vert = com.getGingember_citron_Vert();
        int gcv = Integer.parseInt(Gingember_citron_Vert);
        framboise = com.getFramboise();
        int fran = Integer.parseInt(framboise);
        Caramel_beurre_salé = com.getCaramel_beurre_salé();
        int cbs = Integer.parseInt(Caramel_beurre_salé);
        amande_Rose = com.getAmande_Rose();
        int ar = Integer.parseInt(amande_Rose);
        Amande_Orange = com.getAmande_Orange();
        int ao = Integer.parseInt(Amande_Orange);
        Amande_gingembre = com.getAmande_gingembre();
        int ag = Integer.parseInt(Amande_gingembre);
        Amande_kaab_ghzal = com.getAmande_kaab_ghzal();
        int akg = Integer.parseInt(Amande_kaab_ghzal);
        Pistache_beldi = com.getPistache_beldi();
        int pistache_b = Integer.parseInt(Pistache_beldi);

        //set data
        try {
            if (!praliné.equals("0")) {
                holder.pralinétv.setTextColor(context.getResources().getColor(R.color.principale1));
            }
            if (!café.equals("0")) {
                holder.cafétv.setTextColor(context.getResources().getColor(R.color.principale1));
            }
            if (!Caramel_beurre_salé.equals("0")) {
                holder.Caramel_beurre_salétv.setTextColor(context.getResources().getColor(R.color.principale1));
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
            if (!Amande_gout_rose.equals("0")) {
                holder.Amande_gout_rosetv.setTextColor(context.getResources().getColor(R.color.principale1));
            }
            if (!Citron.equals("0")) {
                holder.Citrontv.setTextColor(context.getResources().getColor(R.color.principale1));
            }
            if (!Gingember_citron_Vert.equals("0")) {
                holder.Gingember_citron_Verttv.setTextColor(context.getResources().getColor(R.color.principale1));
            }
            if (!framboise.equals("0")) {
                holder.framboisetv.setTextColor(context.getResources().getColor(R.color.principale1));
            }
            if (!caramel_chocolate.equals("0")) {
                holder.caramel_chocolatetv.setTextColor(context.getResources().getColor(R.color.principale1));
            }
            if (!amande_Rose.equals("0")) {
                holder.amande_Rosetv.setTextColor(context.getResources().getColor(R.color.principale1));
            }
            if (!Amande_Orange.equals("0")) {
                holder.Amande_Orangetv.setTextColor(context.getResources().getColor(R.color.principale1));
            }
            if (!Amande_gingembre.equals("0")) {
                holder.Amande_gingembretv.setTextColor(context.getResources().getColor(R.color.principale1));
            }
            if (!Amande_kaab_ghzal.equals("0")) {
                holder.Amande_kaab_ghzaltv.setTextColor(context.getResources().getColor(R.color.principale1));
            }
            if (!Pistache_beldi.equals("0")) {
                holder.Pistache_belditv.setTextColor(context.getResources().getColor(R.color.principale1));
            }
            if (!Amande_gout_orange.equals("0")) {
                holder.Amande_gout_orangetv.setTextColor(context.getResources().getColor(R.color.principale1));
            }
            holder.nametv.setText(name + "(" + dup+"x"+total+")");
            holder.cafétv.setText("" + ca * dup);
            holder.Truffetv.setText("" + truf * dup);
            holder.Pistachetv.setText(""+pist * dup);
            holder.Noix_de_cocotv.setText(""+noix * dup);
            holder.Speculostv.setText(""+spe * dup);
            holder.caramel_chocolatetv.setText(""+cracho * dup);
            holder.pralinétv.setText(""+pra * dup);
            holder.Amande_gout_rosetv.setText(""+agr * dup);
            holder.Amande_gout_orangetv.setText(""+aro * dup);
            holder.Citrontv.setText(""+ci * dup);
            holder.Gingember_citron_Verttv.setText(""+gcv * dup);
            holder.framboisetv.setText(""+fran * dup);
            holder.Caramel_beurre_salétv.setText(""+cbs * dup);
            holder.amande_Rosetv.setText(""+ar * dup);
            holder.Amande_Orangetv.setText(""+ao * dup);
            holder.Amande_gingembretv.setText(""+ag * dup);
            holder.Amande_kaab_ghzaltv.setText(""+akg * dup);
            holder.Pistache_belditv.setText(""+pistache_b * dup);
        } catch (Exception e) {
            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class HolderPrepration extends RecyclerView.ViewHolder {
        TextView nametv, cafétv, Truffetv, Pistachetv, Noix_de_cocotv, Speculostv, Caramel_beurre_salétv, pralinétv,
                Amande_gout_rosetv, Amande_gout_orangetv, Citrontv,
                Gingember_citron_Verttv, framboisetv, caramel_chocolatetv,
                amande_Rosetv, Amande_Orangetv, Amande_gingembretv, Amande_kaab_ghzaltv, Pistache_belditv;

        public HolderPrepration(@NonNull View itemView) {
            super(itemView);
            nametv = itemView.findViewById(R.id.textViewCol1);
            cafétv = itemView.findViewById(R.id.textViewCol2);
            Truffetv = itemView.findViewById(R.id.textViewCol3);
            Pistachetv = itemView.findViewById(R.id.textViewCol4);
            Noix_de_cocotv = itemView.findViewById(R.id.textViewCol5);
            Speculostv = itemView.findViewById(R.id.textViewCol6);
            Caramel_beurre_salétv = itemView.findViewById(R.id.textViewCol7);
            pralinétv = itemView.findViewById(R.id.textViewCol8);
            Amande_gout_rosetv = itemView.findViewById(R.id.textViewCol9);
            Amande_gout_orangetv = itemView.findViewById(R.id.textViewCol10);
            Citrontv = itemView.findViewById(R.id.textViewCol11);
            Gingember_citron_Verttv = itemView.findViewById(R.id.textViewCol12);
            framboisetv = itemView.findViewById(R.id.textViewCol13);
            caramel_chocolatetv = itemView.findViewById(R.id.textViewCol14);
            amande_Rosetv = itemView.findViewById(R.id.textViewCol15);
            Amande_Orangetv = itemView.findViewById(R.id.textViewCol16);
            Amande_gingembretv = itemView.findViewById(R.id.textViewCol17);
            Amande_kaab_ghzaltv = itemView.findViewById(R.id.textViewCol18);
            Pistache_belditv = itemView.findViewById(R.id.textViewCol19);


        }
    }
}
