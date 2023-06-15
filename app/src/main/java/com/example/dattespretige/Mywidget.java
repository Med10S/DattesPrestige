package com.example.dattespretige;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import com.example.dattespretige.Adapters.first_page_Adapter;
import com.example.dattespretige.Models.modelClientinfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class Mywidget extends AppWidgetProvider {
    private static ArrayList<modelClientinfo> commandeList, commandeattentelist;
    private static int com_attent;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.mywidget);
        String val = "";
        if (commandeattentelist.size() != 0) {
            val = "N'oubliez pas que vous avez " + com_attent + " commande mis en attente";
        } else {
            val = "Vous n'avez aucune commande en attente \nC'est bon pour aujourd'hui";
        }
        views.setTextViewText(R.id.appwidget_text, "" + val);
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            commandeList = new ArrayList<>();
            commandeattentelist = new ArrayList<>();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("commande2");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    commandeList.clear();
                    commandeattentelist.clear();
                    com_attent = 0;

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String attente = "" + ds.child("attente").getValue();
                        if (!attente.equals("0") && !attente.equals("null") && attente != null) {
                            modelClientinfo com = ds.getValue(modelClientinfo.class);
                            commandeattentelist.add(com);
                            com_attent += Integer.parseInt(attente);
                        }
                    }
                    updateAppWidget(context, appWidgetManager, appWidgetId);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}