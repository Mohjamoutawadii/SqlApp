package com.example.sqlapp.ui.produit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.example.sqlapp.services.MachineService;
import com.example.sqlapp.services.MarqueService;
import com.example.sqlapp.ui.gallery.UpdateMarqueActivity;

import java.util.ArrayList;
import java.util.List;

public class UpdateMachineActivity extends AppCompatActivity {

    EditText ref_input, date_input,prix_input;
    Spinner marquespinner;
    Button update_button, delete_button;

    private MarqueService marqueService;
    String id, reference, date,prix,marque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_machine);

        ref_input = findViewById(R.id.reference_input2);
        date_input = findViewById(R.id.date_input2);
        prix_input = findViewById(R.id.prix_input2);
        marquespinner = findViewById(R.id.marque_spinner2);

        update_button = findViewById(R.id.update_button2);
        delete_button = findViewById(R.id.delete_button2);
        marqueService = new MarqueService(this);


        List<Marque> marques = marqueService.findAll();
        List<String> noms =new ArrayList<>();
        for(Marque marque:marques){
            noms.add(marque.getLibelle());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, noms);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        marquespinner.setAdapter(spinnerAdapter);

        getAndSetIntentData();

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MachineService srv = new MachineService(UpdateMachineActivity.this);
                reference = ref_input.getText().toString().trim();
                date = date_input.getText().toString().trim();
                prix = prix_input.getText().toString().trim();
                String selectedMarque =  marquespinner.getSelectedItem().toString();
                String marqueCode = selectedMarque;
                Machine machine = new Machine(reference,Integer.valueOf(prix),date,marqueCode);
                srv.update(machine, Integer.valueOf(id));
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });


    }
    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("Reference") &&
                getIntent().hasExtra("Prix") && getIntent().hasExtra("Date") &&
                getIntent().hasExtra("MarqueCode")) {

            id = getIntent().getStringExtra("id");
            reference = getIntent().getStringExtra("Reference");
            prix = getIntent().getStringExtra("Prix");
            date = getIntent().getStringExtra("Date");
            marque = getIntent().getStringExtra("MarqueCode");
            ref_input.setText(reference);
            prix_input.setText(prix);
            date_input.setText(date);



        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }


    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + reference + " ?");
        builder.setMessage("Are you sure you want to delete " +reference + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MachineService srv = new MachineService(UpdateMachineActivity.this);
                srv.delete(Integer.valueOf(id));
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}