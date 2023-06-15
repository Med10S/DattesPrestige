package com.example.dattespretige.Adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dattespretige.Autre.filterCommande;
import com.example.dattespretige.EditCommandeActivity2;
import com.example.dattespretige.Autre.FilterProduct;
import com.example.dattespretige.MainActivity;
import com.example.dattespretige.R;
import com.example.dattespretige.Models.commande;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RVadapter extends RecyclerView.Adapter<RVadapter.HolderCommande> implements  Filterable{

    private Context context;
    public ArrayList<commande> list, filterList;
    private filterCommande filterCommande;
    //private FilterProduct filter;


    public RVadapter(Context context, ArrayList<commande> list) {
        this.context = context;
        this.list = list;
        this.filterList = list;
    }

    @NonNull
    @Override
    public HolderCommande onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_command, parent, false);

        return new HolderCommande(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderCommande holder, int position) {
        //get data
        String price, selectedproduct, time, status,duplication,type;
        commande com = list.get(position);
        type=com.getType();

        duplication=com.getDuplication();
        price = com.getPrice();
        selectedproduct = com.getProduct();
        time = com.getTime();
        status = com.getStatus();

        //set data
        int dup = Integer.parseInt(duplication);
        int pr = Integer.parseInt(price);

        holder.product_tv.setText(selectedproduct+" de "+type+"\nQuantité:"+duplication);
        holder.price_tv.setText("Prix:" + pr*dup + " DH");
        holder.status_tv.setText(status);
        holder.time_tv.setText(time);
        String orderStatus = com.getStatus();

        switch (orderStatus) {
            case "en cours":
                holder.status_tv.setTextColor(context.getResources().getColor(R.color.teal_700));
                break;
            case "terminer":
                holder.status_tv.setTextColor(context.getResources().getColor(R.color.green));
                break;
            case "annule":
                holder.status_tv.setTextColor(context.getResources().getColor(R.color.read));
                break;
            case "attente":
                holder.status_tv.setTextColor(context.getResources().getColor(R.color.purple_200));
                break;
        }


        holder.itemView.setOnClickListener(v -> {
            detailsBottomSheet(com);
        });

    }

    @SuppressLint("SetTextI18n")
    private void detailsBottomSheet(commande com) {
        TextView  IDate_tv, IPrice_tv, IStatus_tv, IProduit_tv, Idetail_tv;
        TextView article1,Iname_tv, article2, article3, article4, article5, article6, article7, article8, article9, article10, article11, article12, article13, article14, article15, article17, article16, article18;
        ImageButton back_btn, edit_btn;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.bs_layout_details_commande, null);
        bottomSheetDialog.setContentView(view);

        //init view for bottomsheet
        //initialisation views
        {
            Iname_tv=view.findViewById(R.id.Iname_tv);
            IDate_tv = view.findViewById(R.id.IDate_tv);
            IPrice_tv = view.findViewById(R.id.IPrice_tv);
            IStatus_tv = view.findViewById(R.id.IStatus_tv);
            IProduit_tv = view.findViewById(R.id.IProduit_tv);
            Idetail_tv = view.findViewById(R.id.Idetail_tv);
            article1 = view.findViewById(R.id.article1);
            article2 = view.findViewById(R.id.article2);
            article3 = view.findViewById(R.id.article3);
            article4 = view.findViewById(R.id.article4);
            article5 = view.findViewById(R.id.article5);
            article6 = view.findViewById(R.id.article6);
            article7 = view.findViewById(R.id.article7);
            article8 = view.findViewById(R.id.article8);
            article9 = view.findViewById(R.id.article9);
            article10 = view.findViewById(R.id.article10);
            article11 = view.findViewById(R.id.article11);
            article12 = view.findViewById(R.id.article12);
            article13 = view.findViewById(R.id.article13);
            article14 = view.findViewById(R.id.article14);
            article15 = view.findViewById(R.id.article15);
            article16 = view.findViewById(R.id.article16);
            article17 = view.findViewById(R.id.article17);
            article18 = view.findViewById(R.id.article18);
            edit_btn = view.findViewById(R.id.edit_btn);
            back_btn = view.findViewById(R.id.back_btn);
        }
        //get and set data  in the bottom sheet

        //get data
        String id, details, price, selectedproduct, time, status, id_client,name;
        String café, Truffe, Pistache, Noix_de_coco, Speculos, Caramel_beurre_salé,
                Amande_gout_rose, Amande_gout_orange, Citron, praline,
                Gingember_citron_Vert, framboise, caramel_chocolate,
                amande_Rose, Amande_Orange, Amande_gingembre, Amande_kaab_ghzal, Pistache_beldi;

        id = com.getId();
        id_client = com.getId_client();
        praline = com.getPraliné();
        café = com.getCafé();
        Caramel_beurre_salé = com.getCaramel_beurre_salé();
        Truffe = com.getTruffe();
        Pistache = com.getPistache();
        Noix_de_coco = com.getNoix_de_coco();
        Speculos = com.getSpeculos();
        Amande_gout_rose = com.getAmande_gout_rose();
        Citron = com.getCitron();
        Gingember_citron_Vert = com.getGingember_citron_Vert();
        framboise = com.getFramboise();
        caramel_chocolate = com.getCaramel_chocolate();
        amande_Rose = com.getAmande_Rose();
        Amande_Orange = com.getAmande_Orange();
        Amande_gingembre = com.getAmande_gingembre();
        Amande_kaab_ghzal = com.getAmande_kaab_ghzal();
        Pistache_beldi = com.getPistache_beldi();
        Amande_gout_orange = com.getAmande_gout_orange();
        name=com.getName();

        details = com.getDetails();
        price = com.getPrice();
        selectedproduct = com.getProduct();
        time = com.getTime();
        status = com.getStatus();

//set data
        {
            Iname_tv.setText(name);
            IDate_tv.setText(time);
            Idetail_tv.setText(details);
            IPrice_tv.setText(price + " DH");
            IStatus_tv.setText(status);
            String orderStatus = com.getStatus();
            if (orderStatus.equals("en cours")) {
                IStatus_tv.setTextColor(context.getResources().getColor(R.color.teal_700));
            } else if (orderStatus.equals("termier")) {
                IStatus_tv.setTextColor(context.getResources().getColor(R.color.green));
            } else if (orderStatus.equals("annule")) {
                IStatus_tv.setTextColor(context.getResources().getColor(R.color.read));
            } else if (orderStatus.equals("attente")) {
                IStatus_tv.setTextColor(context.getResources().getColor(R.color.purple_200));
            }
            IProduit_tv.setText(selectedproduct);

            if (!praline.equals("0")) {
                article1.setTextColor(context.getResources().getColor(R.color.read));
            }
            if (!café.equals("0")) {
                article2.setTextColor(context.getResources().getColor(R.color.read));
            }
            if (!Caramel_beurre_salé.equals("0")) {
                article3.setTextColor(context.getResources().getColor(R.color.read));
            }
            if (!Truffe.equals("0")) {
                article4.setTextColor(context.getResources().getColor(R.color.read));
            }
            if (!Pistache.equals("0")) {
                article5.setTextColor(context.getResources().getColor(R.color.read));
            }
            if (!Noix_de_coco.equals("0")) {
                article6.setTextColor(context.getResources().getColor(R.color.read));
            }
            if (!Speculos.equals("0")) {
                article7.setTextColor(context.getResources().getColor(R.color.read));
            }
            if (!Amande_gout_rose.equals("0")) {
                article8.setTextColor(context.getResources().getColor(R.color.read));
            }
            if (!Citron.equals("0")) {
                article9.setTextColor(context.getResources().getColor(R.color.read));
            }
            if (!Gingember_citron_Vert.equals("0")) {
                article10.setTextColor(context.getResources().getColor(R.color.read));
            }
            if (!framboise.equals("0")) {
                article11.setTextColor(context.getResources().getColor(R.color.read));
            }
            if (!caramel_chocolate.equals("0")) {
                article12.setTextColor(context.getResources().getColor(R.color.read));
            }
            if (!amande_Rose.equals("0")) {
                article13.setTextColor(context.getResources().getColor(R.color.read));
            }
            if (!Amande_Orange.equals("0")) {
                article14.setTextColor(context.getResources().getColor(R.color.read));
            }
            if (!Amande_gingembre.equals("0")) {
                article15.setTextColor(context.getResources().getColor(R.color.read));
            }
            if (!Amande_kaab_ghzal.equals("0")) {
                article16.setTextColor(context.getResources().getColor(R.color.read));
            }
            if (!Pistache_beldi.equals("0")) {
                article17.setTextColor(context.getResources().getColor(R.color.read));
            }
            if (!Amande_gout_orange.equals("0")) {
                article18.setTextColor(context.getResources().getColor(R.color.read));
            }

            article1.setText("Praliné : " + praline);
            article2.setText("café : " + café);
            article3.setText("Caramel_beurre_salé : " + Caramel_beurre_salé);
            article4.setText("Truffe : " + Truffe);
            article5.setText("Pistache : " + Pistache);
            article6.setText("Noix_de_coco : " + Noix_de_coco);
            article7.setText("Speculos : " + Speculos);
            article8.setText("Amande_gout_rose : " + Amande_gout_rose);
            article9.setText("Citron : " + Citron);
            article10.setText("Gingember_citron_Vert : " + Gingember_citron_Vert);
            article11.setText("framboise : " + framboise);
            article12.setText("caramel_chocolate : " + caramel_chocolate);
            article13.setText("amande_Rose : " + amande_Rose);
            article14.setText("Amande_Orange : " + Amande_Orange);
            article15.setText("Amande_gingembre : " + Amande_gingembre);
            article16.setText("Amande_kaab_ghzal : " + Amande_kaab_ghzal);
            article17.setText("Pistache_beldi : " + Pistache_beldi);
            article18.setText("Amande_gout_orange : " + Amande_gout_orange);
        }

        bottomSheetDialog.show();
        edit_btn.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditCommandeActivity2.class);
            intent.putExtra("ComId", id);
            Log.d("RVadapter","uid:"+id_client+" comdid"+id);
            intent.putExtra("uid", id_client);
            context.startActivity(intent);
            bottomSheetDialog.dismiss();


        });
        back_btn.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        if (filterCommande==null){
            filterCommande = new filterCommande(this,filterList);
        }
        return filterCommande;
    }

    class HolderCommande extends RecyclerView.ViewHolder {
        /*holder views of rycycle view*/
        private TextView product_tv, price_tv, time_tv, status_tv;

        public HolderCommande(@NonNull View itemView) {
            super(itemView);
            product_tv = itemView.findViewById(R.id.product_tv);
            price_tv = itemView.findViewById(R.id.price_tv);
            time_tv = itemView.findViewById(R.id.time_tv);
            status_tv = itemView.findViewById(R.id.status_tv);

        }


    }

}
