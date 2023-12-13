package com.example.sqlapp.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sqlapp.R;
import com.example.sqlapp.adapters.MarqueAdapter;


import android.content.Intent;



import androidx.annotation.Nullable;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlapp.entities.Marque;
import com.example.sqlapp.services.MarqueService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private FloatingActionButton add_button;
    private RecyclerView recyclerView;


    private List<Marque> marques;
    private MarqueAdapter marqueAdapter;
    private MarqueService service;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_gallery_fragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        add_button = view.findViewById(R.id.add_button);


        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddMarqueActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        storeDataInArrays();
        marqueAdapter = new MarqueAdapter(requireActivity(), requireContext(), marques);
        recyclerView.setAdapter(marqueAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }



    private void storeDataInArrays() {
        marques = new ArrayList<>();
        service = new MarqueService(getActivity());
        marques = service.findAll();
    }
}
