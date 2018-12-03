package com.winiarza.asystent.asystentwiniarza;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.winiarza.asystent.asystentwiniarza.Models.Recipe;

import java.util.List;

public class RecipeAdapter extends ArrayAdapter<Recipe> {
    TextView tvName;
    TextView tvDescription;

    public RecipeAdapter(Context context, int resource, List<Recipe> objects) {
        super(context, resource, objects);


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Recipe recipe = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_item, parent, false);
        }

        tvName = (TextView) convertView.findViewById(R.id.tvName);
        tvName.setText(recipe.getName());

        tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
        tvDescription.setText(recipe.getDescription());


        return convertView;
    }
}
