package com.example.zhiqiang.lab8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhiqiang on 2017/12/4.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Contacts.db";
    private static final String TABLE_NAME = "Contacts";//不能为Contacts

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " +  TABLE_NAME +
                " (_id integer primary key , " +
                "name text not null," +
                "birth text," +
                "gift text);";
        db.execSQL(sql);
        Log.e("SqliteDataBase", "数据库创建");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //To Do
    }
    public void insert(PersonInfo personInfo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", personInfo.getName());
        values.put("birth", personInfo.getBirthday());
        values.put("gift", personInfo.getGift());
        db.insert(TABLE_NAME, null, values);
        db.close();
        Log.e("SqliteDataBase", "插入");
    }
    public void update(PersonInfo personInfo) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "name = ?"; // 主键列名 = ?
        String[] whereArgs = { personInfo.getName() }; // 主键的值
        ContentValues values = new ContentValues();
        values.put("name", personInfo.getName());
        values.put("birth", personInfo.getBirthday());
        values.put("gift", personInfo.getGift());
        db.update(TABLE_NAME, values, whereClause, whereArgs);
        db.close();
        Log.e("SqliteDataBase", "修改");
    }
    public void delete(String name) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "name = ?"; // 主键列名 = ?
        String[] whereArgs = { name }; // 主键的值
        db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
        Log.e("SqliteDataBase", "删除");
    }

    public PersonInfo queryPerson(String name) {
        PersonInfo personInfo = null;
        String[] colums = { "name", "birth", "gift" };
        String[] selectionArgs = { name };
        SQLiteDatabase db = getReadableDatabase(); // 以只读的形式打开数据库
        Cursor cursor = db.query(TABLE_NAME, colums, "name = ?", selectionArgs, null, null, null);
        if (cursor.moveToNext()) {
            personInfo = new PersonInfo(cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("birth")),
                    cursor.getString(cursor.getColumnIndex("gift")));
        }
        Log.e("SqliteDataBase", "查询");
        return personInfo;
    }

    public List<PersonInfo> queryAllPerson() {
        List<PersonInfo> data = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from " + TABLE_NAME + ";";
        Cursor cursor = db.rawQuery(sql, null);
        PersonInfo personInfo;
        while (cursor.moveToNext()) {
            personInfo = new PersonInfo(cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("birth")),
                            cursor.getString(cursor.getColumnIndex("gift")));
            data.add(personInfo);
        }
        Log.e("SqliteDataBase", "查询所有");
        return data;
    }
}
