package com.example.dattespretige.Autre;

import android.widget.Filter;

import com.example.dattespretige.Adapters.RVadapter;
import com.example.dattespretige.Models.commande;

import java.util.ArrayList;

public class filterCommande extends Filter {
    private final RVadapter rVadapter;
    private final ArrayList<commande> filterList;

    public filterCommande(RVadapter rVadapter,ArrayList<commande> filterList) {
        this.rVadapter = rVadapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        //vlidate data for searsh query
        if (constraint != null && constraint.length()>0){
            //chage to upper case, to make insensitive
            constraint = constraint.toString().toUpperCase();
            ArrayList<commande> filtermodel = new ArrayList<>();
            for (int i =0 ; i<filterList.size();i++){
                if (filterList.get(i).getTime().toUpperCase().contains(constraint)){
                    filtermodel.add(filterList.get(i));
                }
            }
            results.count = filtermodel.size();
            results.values = filtermodel;
        }else {
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        rVadapter.list = (ArrayList<commande>) results.values;
        rVadapter.notifyDataSetChanged();

    }
}
