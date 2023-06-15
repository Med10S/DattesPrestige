package com.example.dattespretige.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dattespretige.R;

public class produitAdapter extends RecyclerView.Adapter<produitAdapter.holderproduit> {
    @NonNull
    @Override
    public holderproduit onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull holderproduit holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class holderproduit extends RecyclerView.ViewHolder {
        TextView title_tv, price, description;
        ImageView pro_image;

        public holderproduit(@NonNull View itemView) {
            super(itemView);
            title_tv = itemView.findViewById(R.id.title_tv);
            price = itemView.findViewById(R.id.price_tv);
            description = itemView.findViewById(R.id.description_tv);
            pro_image = itemView.findViewById(R.id.photo_tv);

        }
    }
}
