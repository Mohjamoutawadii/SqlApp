package com.example.sqlapp.ui.produit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlapp.R;
import com.example.sqlapp.adapters.MachineAdapter;
import com.example.sqlapp.entities.Machine;
import com.example.sqlapp.services.MachineService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ProduitFragment extends Fragment {

    private FloatingActionButton add_button;
    private RecyclerView recyclerView;
    private ImageView empty_imageview;
    private TextView no_data;

    private List<Machine> machines;
    private MachineAdapter machineAdapter;
    private MachineService machineService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_produit, container, false);

        recyclerView = view.findViewById(R.id.machineRecyclerView);
        add_button = view.findViewById(R.id.add_machine_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddMachineActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Load the data and set up the RecyclerView
        storeDataInArrays();
        machineAdapter = new MachineAdapter(requireActivity(), requireContext(), machines);
        recyclerView.setAdapter(machineAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK) {
            // Machine added successfully, update the list
            storeDataInArrays();
            machineAdapter.notifyDataSetChanged();
        }
    }

    private void storeDataInArrays() {
        machines = new ArrayList<>();
        machineService = new MachineService(getActivity());
        machines = machineService.findAll();
    }
}
