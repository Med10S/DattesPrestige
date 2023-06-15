package com.example.dattespretige.Autre;

import android.widget.Filter;

import com.example.dattespretige.Adapters.RVadapter;
import com.example.dattespretige.Adapters.first_page_Adapter;
import com.example.dattespretige.Models.commande;
import com.example.dattespretige.Models.modelClientinfo;

import java.util.ArrayList;


public class FilterProduct extends Filter {
    private final first_page_Adapter rVadapter;
    private final ArrayList<modelClientinfo> filterList;

    public FilterProduct( first_page_Adapter rVadapter, ArrayList<modelClientinfo> filterList) {
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
            ArrayList<modelClientinfo> filtermodel = new ArrayList<>();
            for (int i =0 ; i<filterList.size();i++){
                if (filterList.get(i).getName().toUpperCase().contains(constraint)){
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
        rVadapter.list = (ArrayList<modelClientinfo>) results.values;
        rVadapter.notifyDataSetChanged();

    }
}
