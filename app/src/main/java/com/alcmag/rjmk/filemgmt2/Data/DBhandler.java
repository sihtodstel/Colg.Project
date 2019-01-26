package com.alcmag.rjmk.filemgmt2.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.alcmag.rjmk.filemgmt2.Model.wishes;
import com.alcmag.rjmk.filemgmt2.Utilities.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DBhandler extends SQLiteOpenHelper {
    private Context ctx;
    public DBhandler(Context context) {
        super(context, Constants.Db_name , null, Constants.DB_version);
        this.ctx= context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Create_table="CREATE TABLE " + Constants.Table_name +"(" +
                Constants.Key_id + " INTEGER PRIMARY KEY," + Constants.Key_wish +" TEXT," +
                Constants.Key_value + " TEXT," + Constants.Key_date + " LONG);";

        db.execSQL(Create_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.Table_name);
        onCreate(db);


    }
    //CRUD OPERATIONSSSS--Create,read,update,delete
    //ADD Wishes
    public void addwish(wishes wishes){
        SQLiteDatabase db=this.getWritableDatabase();
  ContentValues values=new ContentValues();
  values.put(Constants.Key_wish,wishes.getName());
  values.put(Constants.Key_value,wishes.getSecondwish());
  values.put(Constants.Key_date,java.lang.System.currentTimeMillis());
  db.insert(Constants.Table_name,null,values);
        Log.d("saved","saved TO DB");

    }
    //get wish
    public wishes getwishes(int id){
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor cursor=db.query(Constants.Table_name,new String[] {Constants.Key_id,Constants.Key_wish,Constants.Key_value
        ,Constants.Key_date},Constants.Key_id + "=?",new String[]{String.valueOf(id)},null,
                null,null,null);
        if(cursor!=null)
            cursor.moveToFirst();
        wishes wishes=new wishes();
        wishes.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.Key_id))));
        wishes.setName(cursor.getString(cursor.getColumnIndex(Constants.Key_wish)));
        wishes.setSecondwish(cursor.getString(cursor.getColumnIndex(Constants.Key_value)));

        //convert timestamp
        java.text.DateFormat dateFormat=java.text.DateFormat.getDateInstance();
        String formatedDate=dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.Key_date))).getTime());
        wishes.setDate(formatedDate);
        return wishes;
    }
    //get all grocery
    public List<wishes> getallwishes(){
        SQLiteDatabase db= this.getReadableDatabase();
        List<wishes> wishesList=new ArrayList<>();
        Cursor cursor=db.query(Constants.Table_name,new String[] {
                Constants.Key_id,Constants.Key_wish,Constants.Key_value,Constants.Key_date},null,null,null,
                null,Constants.Key_date + " DESC");
        if(cursor.moveToFirst()){
            do {
                 wishes wishes= new wishes();
                 wishes.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.Key_id))));
                wishes.setName(cursor.getString(cursor.getColumnIndex(Constants.Key_wish)));
                wishes.setSecondwish(cursor.getString(cursor.getColumnIndex(Constants.Key_value)));
                java.text.DateFormat dateFormat=java.text.DateFormat.getDateInstance();
                String formatedDate=dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.Key_date))).getTime());

                wishes.setDate(formatedDate);
                 //add to list
                wishesList.add(wishes);
            }while(cursor.moveToNext());
        }
        return wishesList;

    }

    //update wishes
    public int updatewishes(wishes wishes){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(Constants.Key_wish, wishes.getName());
        values.put(Constants.Key_value,wishes.getSecondwish());
        values.put(Constants.Key_date,java.lang.System.currentTimeMillis()); //getting sys time

        return db.update(Constants.Table_name,values,Constants.Key_id + "=?",new String[]
                {String.valueOf(wishes.getId())});


    }

    //delete wishes
    public void deletewish(int id){
        SQLiteDatabase db=this.getWritableDatabase();
           db.delete(Constants.Table_name,Constants.Key_id + "=?",new String[]
                   {String.valueOf(id)});
           db.close();

    }
    //count of wishes
    public int getwishcount(){
        String countquery="SELECT * FROM " + Constants.Table_name;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countquery,null);


        return cursor.getCount();
    }

}
