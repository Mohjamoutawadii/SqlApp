package com.example.sqlapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlapp.R;
import com.example.sqlapp.entities.Marque;
import com.example.sqlapp.ui.gallery.UpdateMarqueActivity;

import java.util.List;

public class MarqueAdapter extends RecyclerView.Adapter<MarqueAdapter.ViewHolder> {

    private Activity activity;
    private Context context;
    private List<Marque> marques;

    public MarqueAdapter(Activity activity, Context context, List<Marque> marques) {
        this.activity = activity;
        this.context = context;
        this.marques = marques;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use the provided context parameter instead of the context field
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marque_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Marque marque = marques.get(position);
        holder.id.setText(String.valueOf(marque.getId()));
        holder.code.setText(marque.getCode());
        holder.libelle.setText(marque.getLibelle());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateMarqueActivity.class);
                intent.putExtra("id", String.valueOf(marque.getId()));
                intent.putExtra("Code", String.valueOf(marque.getCode()));
                intent.putExtra("Libelle", String.valueOf(marque.getLibelle()));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return marques.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        LinearLayout mainLayout;
        TextView code;
        TextView libelle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.marque_id_txt);
            code = itemView.findViewById(R.id.code_txt);
            libelle = itemView.findViewById(R.id.libelle_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
