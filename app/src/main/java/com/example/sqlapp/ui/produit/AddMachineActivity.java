package com.example.sqlapp.ui.produit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sqlapp.R;
import com.example.sqlapp.entities.Machine;
import com.example.sqlapp.entities.Marque;
import com.example.sqlapp.services.MarqueService;
import com.example.sqlapp.services.MachineService;

import java.util.ArrayList;
import java.util.List;

public class AddMachineActivity extends AppCompatActivity {

    private EditText referenceInput, prixInput, dateInput;
    private Spinner marqueSpinner;
    private Button addButton;

    private MachineService machineService;
    private MarqueService marqueService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_machine);

        referenceInput = findViewById(R.id.reference_input);
        prixInput = findViewById(R.id.prix_input);
        dateInput = findViewById(R.id.date_input);
        marqueSpinner = findViewById(R.id.marque_spinner);
        addButton = findViewById(R.id.add_button);

        machineService = new MachineService(this);
        marqueService = new MarqueService(this);


        List<Marque> marques = marqueService.findAll();
        List<String> noms =new ArrayList<>();
        for(Marque marque:marques){
            noms.add(marque.getLibelle());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, noms);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        marqueSpinner.setAdapter(spinnerAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMachine();
            }
        });
    }

    private void addMachine() {
        String reference = referenceInput.getText().toString().trim();
        String prixStr = prixInput.getText().toString().trim();
        String date = dateInput.getText().toString().trim();

        if (reference.isEmpty() || prixStr.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int prix = Integer.parseInt(prixStr);
        String selectedMarque =  marqueSpinner.getSelectedItem().toString();
        String marqueCode = selectedMarque;

        Machine machine = new Machine(reference, prix, date, marqueCode);
        machineService.create(machine);

        Toast.makeText(this, "Machine added successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
