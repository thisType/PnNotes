package com.example.pnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity implements  View.OnClickListener {

    DBService dbService;


    TextView readTitle;
    TextView  readContent;
    TextView readDate;
    Intent intent;
    Model model;
int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        dbService =  new DBService(this);
        intent = getIntent();
        readTitle = findViewById(R.id.readTitle);
        readContent = findViewById(R.id.readContent);
        readDate = findViewById(R.id.readDate);
        id =  intent.getIntExtra("id",-1);
        readContent.setOnClickListener(this);
        readTitle.setOnClickListener(this);
    }
    public  void  updateText(){
        this.model =  dbService.singleContent(id);
        readTitle.setText(model.getTitle());
        readContent.setText(model.getContent());
        readDate.setText(model.date);

    }
    @Override
    public void onResume(){
        super.onResume();
        updateText();


    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MainActivity3.class);
        intent.putExtra("id",this.id);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainactivity2menu,menu);


        return  true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.copyTextRead){
            this.copyToClipBoard();
            return  true;
        } else if(item.getItemId()== R.id.deleteTextRead) {

            this.deleteText();
            this.finish();

        }


        return  false;
    }

    public void copyToClipBoard(){

        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", model.content);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_SHORT).show();





    }
    public void deleteText(){

        dbService.deleteModel(model.id);
        Toast.makeText(this,"delete successful",Toast.LENGTH_SHORT).show();



    }
}
