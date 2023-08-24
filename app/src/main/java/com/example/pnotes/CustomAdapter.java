package com.example.pnotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter  extends ArrayAdapter<Model> {


    CustomAdapter(Context context , ArrayList<Model> list){

        super(context,0,list);


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Model model = getItem(position);


        if(convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.customadapterlayout,parent,false);

        }


        TextView title =  convertView.findViewById(R.id.textTitle);
        TextView content = convertView.findViewById(R.id.textContent);
        TextView date = convertView.findViewById(R.id.dateView);
        title.setText(model.title);
        content.setText(model.content);
        date.setText(model.date);

        return convertView;







    }
}
