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
import com.example.sqlapp.entities.Machine;
import com.example.sqlapp.ui.produit.UpdateMachineActivity;

import java.util.List;

public class MachineAdapter extends RecyclerView.Adapter<MachineAdapter.ViewHolder> {

    private Activity activity;
    private Context context;
    private List<Machine> machines;

    public MachineAdapter(Activity activity, Context context, List<Machine> machines) {
        this.activity = activity;
        this.context = context;
        this.machines = machines;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.machine_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Machine machine = machines.get(position);
        holder.id.setText(String.valueOf(machine.getId()));
        holder.reference.setText(machine.getReference());
        holder.prix.setText(String.valueOf(machine.getPrix()));
        holder.date.setText(machine.getDate());
        holder.marqueCode.setText(machine.getMarqueCode());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateMachineActivity.class);
                intent.putExtra("id", String.valueOf(machine.getId()));
                intent.putExtra("Reference", machine.getReference());
                intent.putExtra("Prix", String.valueOf(machine.getPrix()));
                intent.putExtra("Date", machine.getDate());
                intent.putExtra("MarqueCode", machine.getMarqueCode());
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return machines.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        LinearLayout mainLayout;
        TextView reference;
        TextView prix;
        TextView date;
        TextView marqueCode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.machine_id_txt);
            reference = itemView.findViewById(R.id.reference_txt);
            prix = itemView.findViewById(R.id.prix_txt);
            date = itemView.findViewById(R.id.date_txt);
            marqueCode = itemView.findViewById(R.id.marque_txt);
            mainLayout = itemView.findViewById(R.id.machineLayout);
        }
    }
}
