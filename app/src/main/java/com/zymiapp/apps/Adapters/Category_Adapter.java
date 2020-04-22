package com.zymiapp.apps.Adapters;

import android.app.Activity;
import android.content.Context;
import com.zymiapp.apps.Model.Categories;
import com.zymiapp.apps.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class Category_Adapter extends ArrayAdapter<Categories> {


    public Category_Adapter(Activity context, List<Categories> categoriesList) {
        super(context,0, categoriesList);
    }

    public Category_Adapter(Context applicationContext, List<Categories> categories) {
        super(applicationContext,0,categories);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.category_item_design,null);
        }

        Categories curentItem = getItem(position);

        TextView textView = (TextView) listItemView.findViewById(R.id.category_Name);
        textView.setText(curentItem.getCat_name());
        return listItemView;
    }

}
