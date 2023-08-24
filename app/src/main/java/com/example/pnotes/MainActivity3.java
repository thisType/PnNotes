package com.example.pnotes;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {
    DBService dbService;

    EditText editTextTitle;
    EditText editTextContent;
    boolean saved = false;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

    Intent intent = getIntent();
    dbService = new DBService(this);

    id = intent.getIntExtra("id",-1);
    editTextTitle = findViewById(R.id.editTitle);
    editTextContent = findViewById(R.id.editContent);
    populateEditText(id);

    }
    public  void  populateEditText(int id){

        if(id>=0){

            Model model = dbService.singleContent(id);

            editTextTitle.setText(model.getTitle());
            editTextContent.setText(model.getContent());


        }

        editTextContent.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.mainactivity3menu,menu);
        return  true;

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        checkUpdateSave();
        this.finish();
        return  true;


    }
    @Override
    public  void onPause(){
        if(!saved) {
            checkUpdateSave();


        }
        super.onPause();
    }
    public  void checkUpdateSave() {

        String editTitle = editTextTitle.getText().toString();
        String editContent = editTextContent.getText().toString();

        if (editTitle.equals("") & editContent.equals("")) {

            return;
        } else {
            if (this.id >= 0) {


                doUpdate();
            } else {


                doSave();
            }


        }
    }

    public void doSave(){
        String editTitle = editTextTitle.getText().toString();
        String editContent = editTextContent.getText().toString();
        String generatedTitle;
        if(editTitle.equals("")){
            if(editContent.contains(" ")){
                int indexOfLine = editContent.indexOf(" ");
                generatedTitle= editContent.substring(0,indexOfLine);

            } else {


                generatedTitle = editContent;


            }



            dbService.insertNote(generatedTitle,editContent);




        } else {

            dbService.insertNote(editTitle,editContent);



        }
        saved = true;
        Toast.makeText(this, "Successful saved", Toast.LENGTH_SHORT).show();


    }
    public void doUpdate(){
        String editTitle = editTextTitle.getText().toString();
        String editContent = editTextContent.getText().toString();
        String generatedTitle;
        if(editTitle.equals("")){
            if(editContent.contains(" ")){
                int indexOfLine = editContent.indexOf(" ");
                generatedTitle= editContent.substring(0,indexOfLine);

            } else {


                generatedTitle = editContent;


            }
            dbService.update(this.id,generatedTitle,editContent);



        } else {

            dbService.update(this.id,editTitle,editContent);





        }
        saved = true;



    }
}