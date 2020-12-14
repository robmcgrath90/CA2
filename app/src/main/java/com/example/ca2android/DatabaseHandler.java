package com.example.ca2android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

//class that handles all of the operations of the database
public class DatabaseHandler extends SQLiteOpenHelper {

    //SETTING CONSTANT VARIABLES
    //reference and example at 45.25 of video https://www.youtube.com/watch?v=hDSVInZ2JCs&fbclid=IwAR2YB6VBte9Peo-XCJhihZV575MH8U5w2NfEOjRVx9I12Ktj90HhFLQC_h4

    public static final String CLIENT_TABLE = "CLIENT_TABLE";
    public static final String COLUMN_CLIENT_NAME = "CLIENT_NAME";
    public static final String COLUMN_ACTIVE_CLIENT = "ACTIVE_CLIENT";
    public static final String COLUMN_ID = "ID";
    private static final String COLUMN_CLIENT_WEIGHT = "CLIENT_WEIGHT";

    //reference https://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper
    //constructor for class DatabaseHandler
    public DatabaseHandler(@Nullable Context context) {
        super(context, "Client.db", null, 1);
    }

    //reference 36.55 of video https://www.youtube.com/watch?v=hDSVInZ2JCs&fbclid=IwAR2YB6VBte9Peo-XCJhihZV575MH8U5w2NfEOjRVx9I12Ktj90HhFLQC_h4
    //this method is called the first time a database is accessed. there should be code to generate a new table
    @Override
    public void onCreate(SQLiteDatabase db) {

        //reference 42.14 video https://www.youtube.com/watch?v=hDSVInZ2JCs&fbclid=IwAR2YB6VBte9Peo-XCJhihZV575MH8U5w2NfEOjRVx9I12Ktj90HhFLQC_h4
        String createTableStatement = "CREATE TABLE " + CLIENT_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CLIENT_NAME + " TEXT, " + COLUMN_CLIENT_WEIGHT + " INT, " + COLUMN_ACTIVE_CLIENT + " BOOL)";
        db.execSQL(createTableStatement);


    }

    // this is called when ever the database version number changes
    //it prevents the user apps from breaking when you change the database design.
    //reference 37.32 of video https://www.youtube.com/watch?v=hDSVInZ2JCs&fbclid=IwAR2YB6VBte9Peo-XCJhihZV575MH8U5w2NfEOjRVx9I12Ktj90HhFLQC_h4
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    //reference 48.05 of video https://www.youtube.com/watch?v=hDSVInZ2JCs&fbclid=IwAR2YB6VBte9Peo-XCJhihZV575MH8U5w2NfEOjRVx9I12Ktj90HhFLQC_h4
    //adding data to database
    public boolean addOne(ClientModel clientModel) {

        //gets the database were going to write to
        SQLiteDatabase db = this.getWritableDatabase();

        //object content values
        //special class that works like a hashmap
        ContentValues  cv = new ContentValues();

        //reference video time 50.00 https://www.youtube.com/watch?v=hDSVInZ2JCs&fbclid=IwAR2YB6VBte9Peo-XCJhihZV575MH8U5w2NfEOjRVx9I12Ktj90HhFLQC_h4
        //associating column name with a value that is a string
        cv.put(COLUMN_CLIENT_NAME, clientModel.getName());
        cv.put(COLUMN_CLIENT_WEIGHT, clientModel.getWeight());
        cv.put(COLUMN_ACTIVE_CLIENT, clientModel.isActive());

        long insert = db.insert(CLIENT_TABLE, null, cv);
        if (insert == -1){
            return false;
        }
        else{
            return true;
        }
    }



    public boolean deleteOne(ClientModel clientModel){
        //find Clientmodel in the db. if it is found, delete it and return true
        //if not found return false
        //reference 1.21.08 of video https://www.youtube.com/watch?v=hDSVInZ2JCs&fbclid=IwAR2YB6VBte9Peo-XCJhihZV575MH8U5w2NfEOjRVx9I12Ktj90HhFLQC_h4

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + CLIENT_TABLE + " WHERE  " + COLUMN_ID + " = " + clientModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }







    //method that will pull all items out of the db
    //reference video time 59:09  https://www.youtube.com/watch?v=hDSVInZ2JCs&fbclid=IwAR2YB6VBte9Peo-XCJhihZV575MH8U5w2NfEOjRVx9I12Ktj90HhFLQC_h4
    public List<ClientModel> getEveryone(){

        List<ClientModel> returnList = new ArrayList<>();

        //get data from the database
        //                                   using constant variable CLIENT_TABLE
        String queryString = "SELECT * FROM " + CLIENT_TABLE;

        //making a reference to the db that is active
        SQLiteDatabase db = this.getReadableDatabase();

        //execute the query
        Cursor cursor = db.rawQuery(queryString, null);

        //move to first result in the result set
        //if true then there were results and we can continue
        if (cursor.moveToFirst()) {
            //loop through the cursor (result set) and create new client objects.put them into the returnList.

            do {
                //reference 103.36 of video https://www.youtube.com/watch?v=hDSVInZ2JCs&fbclid=IwAR2YB6VBte9Peo-XCJhihZV575MH8U5w2NfEOjRVx9I12Ktj90HhFLQC_h4

                    int clientID = cursor.getInt(0);
                    String clientName = cursor.getString(1);
                    int clientWeight = cursor.getInt(2);
                    boolean clientActive = cursor.getInt(3) == 1 ? true: false;

                    ClientModel newClient = new ClientModel(clientID, clientName, clientWeight, clientActive);
                    returnList.add(newClient);


            }  while(cursor.moveToNext());


            }
        else{

            //leaving the else empty, would mean it failed to update
        }

        //closing db and cursor when done and returing the returns list
        cursor.close();
        db.close();
        return returnList;

    }

}
