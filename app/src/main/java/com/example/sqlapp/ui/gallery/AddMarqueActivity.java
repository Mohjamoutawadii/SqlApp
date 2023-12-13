package com.example.sqlapp.ui.gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sqlapp.R;
import com.example.sqlapp.entities.Marque;
import com.example.sqlapp.services.MarqueService;

public class AddMarqueActivity extends AppCompatActivity {

    EditText code_input, libelle_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marque);

        code_input = findViewById(R.id.code_input);
        libelle_input = findViewById(R.id.libelle_input);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Marque marque = new Marque(
                        code_input.getText().toString().trim(),
                        libelle_input.getText().toString().trim()
                );

                MarqueService service = new MarqueService(AddMarqueActivity.this);
                service.create(marque);
                finish();

            }
        });
    }
}
