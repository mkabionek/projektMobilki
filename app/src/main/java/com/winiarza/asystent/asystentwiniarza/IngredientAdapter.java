package com.winiarza.asystent.asystentwiniarza;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.winiarza.asystent.asystentwiniarza.Models.Ingredient;
import com.winiarza.asystent.asystentwiniarza.Models.Recipe;

import java.util.List;

public class IngredientAdapter extends ArrayAdapter<Ingredient> {

    TextView tvName;
    TextView tvAmount;
    TextView tvMeasurement;

    public IngredientAdapter(Context context, int resource, List<Ingredient> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Ingredient ingredient = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_item, parent, false);
        }

        tvName = (TextView) convertView.findViewById(R.id.ingredientName);
        tvName.setText(ingredient.getName());

        tvAmount = (TextView) convertView.findViewById(R.id.ingredientAmount);
        tvAmount.setText(String.valueOf(ingredient.getAmount()));

        tvMeasurement = (TextView) convertView.findViewById(R.id.ingredientMeasurement);
        tvMeasurement.setText(ingredient.getMeasurement().getName());

        return convertView;
    }
}
