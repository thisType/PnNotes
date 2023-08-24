package com.example.pnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DBService extends SQLiteOpenHelper {
    private static final String dbName = "HyperNoteDb";

    private static final String tableName = "userTableIP";

    private static final int dbVersion = 1;


    private static final String firstColumn = "id";
    private static final String secondColumn = "title";
    private static final String thirdColumn = "content";
    private static final String forthColumn = "dateModified";

          DBService(Context context){
              super(context, dbName, null, dbVersion);

          }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTable = String.format("create table %s ( %s integer primary key, %s text, %s text, %s datetime)", tableName
                , firstColumn, secondColumn, thirdColumn, forthColumn);


        sqLiteDatabase.execSQL(createTable);
        Date date = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String dateString = fmt.format(date);
        ContentValues contentValues = new ContentValues();
        contentValues.put(secondColumn, "Welcome to PNote");
        contentValues.put(thirdColumn, "Our design goal is   smart creative  and easy to use.");
        contentValues.put(forthColumn, dateString);
        sqLiteDatabase.insert(tableName, null, contentValues);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void insertNote(String title, String content) {

        Date date = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String dateString = fmt.format(date);

        ContentValues contentValues = new ContentValues();
        contentValues.put(secondColumn, title);
        contentValues.put(thirdColumn, content);
        contentValues.put(forthColumn, dateString);

        SQLiteDatabase db = getWritableDatabase();

        db.insert(tableName, null, contentValues);
    }
    public void update(int contentId, String title, String content) {
        Date date = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        String dateString = fmt.format(date);
        ContentValues contentValues = new ContentValues();
        contentValues.put(secondColumn, title);
        contentValues.put(thirdColumn, content);
        contentValues.put(forthColumn, dateString);

        SQLiteDatabase db = getWritableDatabase();

        db.update(tableName, contentValues, "id = ?", new String[]{String.valueOf(contentId)});


    }
    public ArrayList<Model> populateList() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Model> list = new ArrayList<Model>();
        Cursor cursor = db.query(tableName, new String[]{firstColumn, secondColumn, thirdColumn, forthColumn},
                null, null, null, null, forthColumn + " DESC");

        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {

                Model model = new Model();
                model.setId(cursor.getInt(0));
                model.setTitle(cursor.getString(1));
                model.setContent(cursor.getString(2));
                model.setDate(cursor.getString(3));
                list.add(model);
                cursor.moveToNext();

            }
            cursor.close();
            return list;

        }
        return list;

    }
    public  ArrayList<Model> searchDb(String search){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM userTableIP WHERE title LIKE ?  OR content LIKE ?";
        ArrayList<Model>  searchList = new ArrayList<>();
        String[] arguments = {"%"+search+"%", "%"+search+"%"};

        Cursor cursor = db.rawQuery(query, arguments);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Model model = new Model();
                model.setId(cursor.getInt(0));
                model.setTitle(cursor.getString(1));
                model.setContent(cursor.getString(2));
                model.setDate(cursor.getString(3));
                searchList.add(model);
                cursor.moveToNext();


            }
        }

        cursor.close();
        return  searchList;

    }
    public Model singleContent(int id) {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(tableName, new String[]{firstColumn, secondColumn, thirdColumn, forthColumn}, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        Model model = new Model();
        if (cursor.moveToFirst()) {
            model.setId(cursor.getInt(0));
            model.setTitle(cursor.getString(1));
            model.setContent(cursor.getString(2));
            model.setDate(cursor.getString(3));


            cursor.close();


        }
        return model;


    }
    public  void deleteModel( int id ){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(tableName,firstColumn+" = ?",new String[]{String.valueOf(id)});



    }





}
