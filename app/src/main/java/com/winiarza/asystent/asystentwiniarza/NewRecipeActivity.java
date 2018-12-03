package com.winiarza.asystent.asystentwiniarza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        final ListView igredientsList = (ListView) findViewById(R.id.IgredientsList);
        final Button btnSave = (Button)findViewById(R.id.SaveRecipeButton);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Przepis został utworzony", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(NewRecipeActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
