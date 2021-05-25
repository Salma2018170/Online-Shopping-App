package com.example.shopping.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.shopping.RecyclerViewActivity;

public class Database extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatabase;

    public Database(@Nullable Context context) {
        super(context, Constants.PREFERENCE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + Constants.SignUpTable.TABLE_NAME
                + "(" + Constants.SignUpTable.SIGN_UP_ID + " integer primary key, "
                + Constants.SignUpTable.SIGN_UP_NAME + " text not null, "
                + Constants.SignUpTable.SIGN_UP_PASSWORD + " text , "
                + Constants.SignUpTable.SIGN_UP_EMAIL + " text )");
        sqLiteDatabase.execSQL("CREATE TABLE PRODUCTS(PRODUCT_ID INTEGER," +
                "PRODUCT_NAME TEXT," +
                "PRODUCT_COST INTEGER," +
                "PRODUCT_QUANTITIES INTEGER," +
                "Sign_Up_Id INTEGER," +
                "FOREIGN KEY (Sign_Up_Id) REFERENCES SIGN_UPS(Sign_Up_Id)," +
                "PRIMARY KEY (PRODUCT_ID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + Constants.SignUpTable.TABLE_NAME);
        sqLiteDatabase.execSQL("drop table if exists " + Constants.ProductTable.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void createAcount(SignUp signUp) {
        ContentValues signUpRow = new ContentValues();
        signUpRow.put(Constants.SignUpTable.SIGN_UP_NAME, signUp.getName());
        signUpRow.put(Constants.SignUpTable.SIGN_UP_PASSWORD, signUp.getPassword());
        signUpRow.put(Constants.SignUpTable.SIGN_UP_EMAIL, signUp.getEmail());
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(Constants.SignUpTable.TABLE_NAME, null, signUpRow);
        sqLiteDatabase.close();
    }

    public Cursor showSignUpAccounts() {
        sqLiteDatabase = getReadableDatabase();
        String[] signUpRow = {Constants.SignUpTable.SIGN_UP_ID, Constants.SignUpTable.SIGN_UP_NAME,Constants.SignUpTable.SIGN_UP_PASSWORD,Constants.SignUpTable.SIGN_UP_EMAIL};
        Cursor cursor = sqLiteDatabase.query(Constants.SignUpTable.TABLE_NAME, signUpRow, null, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        sqLiteDatabase.close();
        return cursor;
    }

    public boolean checkMail(String email)
    {
        sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from SIGN_UPS where SIGN_UP_EMAIL = ?", new String[]{email});

        if (cursor.getCount()>0)
            return  true;
        else
            return false;
    }
    public int checkMailAndPassword(String email,String password)
    {
        sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from  SIGN_UPS where SIGN_UP_EMAIL=? and SIGN_UP_PASSWORD=?",new String[]{email,password});
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }

    public void createProduct(Product product){
        ContentValues productRow = new ContentValues();
        productRow.put(Constants.ProductTable.PRODUCT_NAME, product.getName());
        productRow.put(Constants.ProductTable.PRODUCT_COST, product.getCost());
        productRow.put(Constants.ProductTable.PRODUCT_QUANTITIES, product.getQuantities());
        productRow.put(Constants.ProductTable.SIGN_UP_ID, RecyclerViewActivity.id);
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(Constants.ProductTable.TABLE_NAME, null, productRow);
        sqLiteDatabase.close();
    }


    public Cursor getProductsOfUser(){
        sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from "+ Constants.ProductTable.TABLE_NAME + " where " + Constants.ProductTable.SIGN_UP_ID + "=?",new String[]{String.valueOf(RecyclerViewActivity.id)});
        if(cursor.getCount()>0)
            cursor.moveToFirst();
        return cursor;
    }

    public void deleteProduct(int id) {
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(Constants.ProductTable.TABLE_NAME, Constants.ProductTable.PRODUCT_ID + "='" + id + "'", null);
        sqLiteDatabase.close();
    }


    public void updateProduct(Product product) {
        ContentValues productRow = new ContentValues();
        productRow.put(Constants.ProductTable.PRODUCT_NAME, product.getName());
        productRow.put(Constants.ProductTable.PRODUCT_COST, product.getCost());
        productRow.put(Constants.ProductTable.PRODUCT_QUANTITIES, product.getQuantities());
        productRow.put(Constants.ProductTable.SIGN_UP_ID, RecyclerViewActivity.id);
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.update(Constants.ProductTable.TABLE_NAME, productRow, Constants.ProductTable.PRODUCT_ID + "='" + product.getId() + "'", null);
        sqLiteDatabase.close();
    }

    public void updatePassword(String email, String password) {
        sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Constants.SignUpTable.SIGN_UP_PASSWORD,password);
        sqLiteDatabase.update(Constants.SignUpTable.TABLE_NAME,contentValues,Constants.SignUpTable.SIGN_UP_EMAIL + "='" + email + "'",null);
        sqLiteDatabase.close();
    }


}
