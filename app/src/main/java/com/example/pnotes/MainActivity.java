package com.example.pnotes;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity{



    ListView listView;
    ImageButton createNote;
    DBService dbService;
    CustomAdapter customAdapter;
    ArrayList<Model> list;

    HashSet<Integer> selectedItems = new HashSet<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        dbService = new DBService(this);
        createNote = findViewById(R.id.createButton);
        createNote.setOnClickListener(view -> createNewNote());

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            Model model = customAdapter.getItem(i);
            intent.putExtra("id",model.id);
            startActivity(intent);
        });
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener(){


            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {

                getMenuInflater().inflate(R.menu.listcontextualmode,menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                if(menuItem.getItemId() ==R.id.deleteActionMode){

                    deleteItems();
                    selectedItems.clear();
                    actionMode.finish();
                    return  true;
                } else {
                    return false;
                }


            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

                for(int i :selectedItems){

                    View view = listView.getChildAt(i);
                    ImageView imgView = view.findViewById(R.id.noteStart);
                    imgView.setImageResource(R.drawable.note_display);

                }






                if(!selectedItems.isEmpty()){
                    selectedItems.clear();
                }

            }

            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

                if(b){

                    selectedItems.add(i);
                    View view = listView.getChildAt(i);
                    ImageView imgView = view.findViewById(R.id.noteStart);
                    imgView.setImageResource(R.drawable.checkeditem);
                } else {

                    if(selectedItems.contains(i)){
                        selectedItems.remove(i);
                        View view = listView.getChildAt(i);
                        ImageView imgView = view.findViewById(R.id.noteStart);
                        imgView.setImageResource(R.drawable.note_display);
                    }




                }

            }
        });


    }
    @Override
    public  void onResume(){
        super.onResume();
        list = dbService.populateList();
        customAdapter = new CustomAdapter(this, list);
        listView.setAdapter(customAdapter);

    }



    public void createNewNote(){
        Intent intent = new Intent(this, MainActivity3.class);
        intent.putExtra("id",-45);
        startActivity(intent);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainactivitymenu,menu);

        return  true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() ==R.id.searchItem){

            Intent intent = new Intent(this, MainActivity4.class);
            startActivity(intent);


        }

        return  true;
    }


    public  void deleteItems(){

        for(int i: selectedItems){

            Model model = customAdapter.getItem(i);
            dbService.deleteModel(model.id);
            customAdapter.remove(model);

        }

    }
}




