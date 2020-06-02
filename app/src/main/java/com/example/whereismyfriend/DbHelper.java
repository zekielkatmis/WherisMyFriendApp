package com.example.whereismyfriend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.util.regex.Pattern;


public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "sms";
    public static final int DATABASE_VERSION = 1;

    public static final String PERSON_TABLE_NAME = "Person";
    public static final String PERSON_COLUMN_ID = "PersonId";
    public static final String PERSON_COLUMN_NAME = "Name";
    public static final String PERSON_COLUMN_ADDRESS = "Address";
    public static final String PERSON_COLUMN_PHONE_NUMBER = "PhoneNumber";
    public static final String PERSON_COLUMN_EMAIL = "Email";
    public static final String PERSON_COLUMN_PASSWORD = "Password";

    public static final String CONTACT_TABLE_NAME = "Contact";
    public static final String CONTACT_COLUMN_ID = "ContactId";
    public static final String CONTACT_COLUMN_TOPIC = "Topic";
    public static final String CONTACT_COLUMN_DETAILS = "Details";
    public static final String CONTACT_COLUMN_EMAIL = "Email";

    public static final String USERS_TABLE_NAME = "Users";
    public static final String USER_COLUMN_EMAIL = "UserEmail";
    public static final String USER_COLUMN_NAME = "UserName";
    public static final String USER_COLUMN_STATUS = "Status";
    public static final String USER_COLUMN_H1 = "Hobbies1";
    public static final String USER_COLUMN_H2 = "Hobbies2";
    public static final String USER_COLUMN_H3 = "Hobbies3";
    public static final String USER_COLUMN_IMAGE = "ImageName";
    public static final String USER_COLUMN_ADDRESS = "Address";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String PERSON_CREATE_TABLE = "CREATE TABLE " + PERSON_TABLE_NAME + " ( " + PERSON_COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + PERSON_COLUMN_NAME + " text, " + PERSON_COLUMN_ADDRESS +
                " text, " + PERSON_COLUMN_PHONE_NUMBER + " text, " + PERSON_COLUMN_EMAIL + " text, " +
                PERSON_COLUMN_PASSWORD + " text);";

        String CONTACT_CREATE_TABLE = "CREATE TABLE " + CONTACT_TABLE_NAME + " ( " + CONTACT_COLUMN_ID +
        " INTEGER PRIMARY KEY AUTOINCREMENT, "  + CONTACT_COLUMN_TOPIC + " text, " + CONTACT_COLUMN_DETAILS +
                " text, " + CONTACT_COLUMN_EMAIL + " text);";

        String USERS_CREATE_TABLE = "CREATE TABLE " + USERS_TABLE_NAME + " ( " + USER_COLUMN_EMAIL +
                " TEXT PRIMARY KEY, " + USER_COLUMN_NAME + " TEXT, " + USER_COLUMN_STATUS +
                " TEXT, " + USER_COLUMN_H1 + " TEXT, " + USER_COLUMN_H2 + " TEXT, " + USER_COLUMN_H3 + " TEXT, "
                + USER_COLUMN_IMAGE + " BLOB, " + USER_COLUMN_ADDRESS + " TEXT);";

        db.execSQL(PERSON_CREATE_TABLE);
        db.execSQL(CONTACT_CREATE_TABLE);
        db.execSQL(USERS_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String PERSON_DROP_TABLE = "DROP TABLE IF EXISTS " + PERSON_TABLE_NAME;
        onCreate(db);
    }

    public boolean chkEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from " +
                PERSON_TABLE_NAME + " WHERE " + PERSON_COLUMN_EMAIL +
                " = ?", new String[]{email});
        if(cursor.getCount()>0) return false;
        else return true;
    }

    public boolean LoginSystem(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + PERSON_TABLE_NAME + " Where " +
                PERSON_COLUMN_EMAIL + " = ? AND " +
                PERSON_COLUMN_PASSWORD + " = ?", new String[]{email, password});
        if (cursor.getCount()>0) return true;
        else return false;
    }

    public static boolean isValidPassword(String password) {
        if (!((password.length() >= 8)
                && (password.length() <= 15))) {
            return false;
        }
        if (password.contains(" ")) {
            return false;
        }
        if (true) {
            int count = 0;
            for (int i = 0; i <= 9; i++) {
                String str1 = Integer.toString(i);

                if (password.contains(str1)) {
                    count = 1;
                }
            }
            if (count == 0) {
                return false;
            }
        }
        if (!(password.contains("@") || password.contains("#")
                || password.contains("!") || password.contains("~")
                || password.contains("$") || password.contains("%")
                || password.contains("^") || password.contains("&")
                || password.contains("*") || password.contains("(")
                || password.contains(")") || password.contains("-")
                || password.contains("+") || password.contains("/")
                || password.contains(":") || password.contains(".")
                || password.contains(", ") || password.contains("<")
                || password.contains(">") || password.contains("?")
                || password.contains("|"))) {
            return false;
        }

        if (true) {
            int count = 0;
            for (int i = 65; i <= 90; i++) {
                char c = (char)i;

                String str1 = Character.toString(c);
                if (password.contains(str1)) {
                    count = 1;
                }
            }
            if (count == 0) {
                return false;
            }
        }

        if (true) {
            int count = 0;
            for (int i = 90; i <= 122; i++) {
                char c = (char)i;
                String str1 = Character.toString(c);

                if (password.contains(str1)) {
                    count = 1;
                }
            }
            if (count == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public String PersonAdd(String name, String address, String email, String number, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PERSON_COLUMN_NAME,name);
        values.put(PERSON_COLUMN_ADDRESS, address);
        values.put(PERSON_COLUMN_EMAIL, email);
        values.put(PERSON_COLUMN_PHONE_NUMBER, number);
        values.put(PERSON_COLUMN_PASSWORD, password);

        long result = db.insert(PERSON_TABLE_NAME, null, values);

        String msg = "Not Registered";
        if(result>0){
            msg = "Registered Successfully";
        }
        return msg;
    }

    public String ContactAdd(String topic, String details, String email){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CONTACT_COLUMN_TOPIC,topic);
        values.put(CONTACT_COLUMN_DETAILS, details);
        values.put(CONTACT_COLUMN_EMAIL, email);

        long result = db.insert(CONTACT_TABLE_NAME, null, values);

        String msg = "Not Submitted";
        if(result>0){
            msg = "Submitted Successfully";
        }
        return msg;
    }

    public void UserCreateProfile(String email, String name, byte[] image, String address){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_COLUMN_EMAIL, email);
        values.put(USER_COLUMN_NAME, name);
        values.put(USER_COLUMN_IMAGE, image);
        values.put(USER_COLUMN_ADDRESS, address);


        db.insert(USERS_TABLE_NAME, null, values);
    }

    public String GetName(String email){
        String Name = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + USERS_TABLE_NAME +
                " where "+ USER_COLUMN_EMAIL + " = ?", new String[]{email});
        if (cursor.moveToNext()) {
            Name = cursor.getString(1);
        }
        cursor.close();
        return Name;
    }

    public String getStatus(String email){
        String status = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + USERS_TABLE_NAME +
                " where "+ USER_COLUMN_EMAIL + " = ?", new String[]{email});
        if (cursor.moveToNext()) {
            status = cursor.getString(2);
        }
        cursor.close();
        return status;
    }

    public String geth1(String email){
        String H1 = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + USERS_TABLE_NAME +
                " where "+ USER_COLUMN_EMAIL + " = ?", new String[]{email});
        if (cursor.moveToNext()) {
            H1 = cursor.getString(3);
        }
        cursor.close();
        return H1;
    }

    public String geth2(String email){
        String H2 = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + USERS_TABLE_NAME +
                " where "+ USER_COLUMN_EMAIL + " = ?", new String[]{email});
        if (cursor.moveToNext()) {
            H2 = cursor.getString(4);
        }
        cursor.close();
        return H2;
    }

    public String geth3(String email){
        String H3 = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + USERS_TABLE_NAME +
                " where "+ USER_COLUMN_EMAIL + " = ?", new String[]{email});
        if (cursor.moveToNext()) {
            H3 = cursor.getString(5);
        }
        cursor.close();
        return H3;
    }

    public Bitmap getImage(String email){
        SQLiteDatabase db = this.getWritableDatabase();

        Bitmap bt = null;
        Cursor cursor = db.rawQuery("select * from " + USERS_TABLE_NAME +
                " WHERE " + USER_COLUMN_EMAIL + " = ?", new String[]{email});
        if (cursor.moveToNext()){
            byte[] image = cursor.getBlob(6);
            bt = BitmapFactory.decodeByteArray(image, 0, image.length);
        }
        return bt;
    }

    public boolean UpdateProfile(String email, String name, String hobbies1, String hobbies2,
                                 String hobbies3, String status, byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(USER_COLUMN_EMAIL, email);
        values.put(USER_COLUMN_NAME, name);
        values.put(USER_COLUMN_H1, hobbies1);
        values.put(USER_COLUMN_H2, hobbies2);
        values.put(USER_COLUMN_H3, hobbies3);
        values.put(USER_COLUMN_STATUS, status);
        values.put(USER_COLUMN_IMAGE, image);

        db.update(USERS_TABLE_NAME, values, USER_COLUMN_EMAIL + " = ?", new String[]{email});
        db.close();

        return true;
    }

    public void updatePerson(String email, String name){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put(PERSON_COLUMN_NAME, name);

        db.update(PERSON_TABLE_NAME, v, PERSON_COLUMN_EMAIL + " = ?",
                new String[]{email});

        db.close();
    }

    /*Cursor getFriends(String address){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + USERS_TABLE_NAME +
                " WHERE " + USER_COLUMN_ADDRESS + " LIKE '% " + address + " %'";

        Cursor cursor = null;
        if (db != null){
            cursor = db. rawQuery(query, null);
        }

        return cursor;
    }*/

    Cursor getFriends(){
        String query = "SELECT * FROM " + USERS_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    /*public String getAddress(String email){
        String address1 = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + USERS_TABLE_NAME +
                " where "+ USER_COLUMN_EMAIL + " = ?", new String[]{email});
        if (cursor.moveToNext()) {
            address1 = cursor.getString(7);
        }
        cursor.close();
        return address1;
    }*/
}




