package com.example.pnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity implements  AdapterView.OnItemClickListener {
    ListView listViewSearch;

    EditText searchEditText;
    CustomAdapter customAdapter;

    DBService dbService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        dbService= new DBService(this) ;
        listViewSearch = findViewById(R.id.listviewSearch);
        searchEditText   = findViewById(R.id.searchEdit);
        searchEditText.requestFocus();
        listViewSearch.setOnItemClickListener(this);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Fires right as the text is being changed (even supplies the range of text)
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // Fires right before text is changing
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Fires right after the text has changed
                search(s.toString());
            }
        });


    }
    public  void search(String search){

        ArrayList<Model> searchItems = dbService.searchDb(search);
        customAdapter = new CustomAdapter(this,searchItems);
        listViewSearch.setAdapter(customAdapter);



    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, MainActivity2.class);
        Model model = customAdapter.getItem(i);
        intent.putExtra("id",model.id);
        startActivity(intent);
    }
}