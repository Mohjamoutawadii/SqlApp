package com.example.sqlapp.ui.gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.sqlapp.R;
import com.example.sqlapp.entities.Marque;
import com.example.sqlapp.services.MarqueService;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateMarqueActivity extends AppCompatActivity {

    EditText code_input, libelle_input;
    Button update_button, delete_button;
    String id, code, libelle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_marque);

        code_input = findViewById(R.id.code_input2);
        libelle_input = findViewById(R.id.libelle_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        getAndSetIntentData();

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MarqueService srv = new MarqueService(UpdateMarqueActivity.this);
                code = code_input.getText().toString().trim();
                libelle = libelle_input.getText().toString().trim();
                Marque marque = new Marque(code, libelle);
                srv.update(marque, Integer.valueOf(id));
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
        if (getIntent().hasExtra("id") && getIntent().hasExtra("Code") &&
                getIntent().hasExtra("Libelle")) {

            id = getIntent().getStringExtra("id");
            code = getIntent().getStringExtra("Code");
            libelle = getIntent().getStringExtra("Libelle");
            code_input.setText(code);
            libelle_input.setText(libelle);

        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + code + " ?");
        builder.setMessage("Are you sure you want to delete " + code + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MarqueService srv = new MarqueService(UpdateMarqueActivity.this);
                srv.delete(Integer.valueOf(id));
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do nothing if the user clicks "No"
            }
        });
        builder.create().show();
    }
}