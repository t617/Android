package com.example.zhiqiang.electronicdictionary.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.zhiqiang.electronicdictionary.Data.Present;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhiqiang on 2017/11/16.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME="persons.db";
    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *  @param context to use to open or create the database
     *
     */
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        Log.e("SqliteDataBase", "数据库创建");
        String sql = "create table person(id integer Primary Key autoincrement," +
                "name varchar(20) not null," +
                "gender varchar(20) not null," +
                "date text," +
                "force text," +
                "native text," +
                "image integer," +
                "profile text);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("SqliteDataBase", "数据库更新");
    }
    public void createPerson(Present present) {
        Log.e("SqliteDataBase", "增加人物");
        SQLiteDatabase db = getWritableDatabase(); // 以读写的形式打开数据库
        if (queryPerson(present.getName()) != null) return;
        String sql = "insert into person(name, gender, date, force, native, image, profile) values("
                + String.format("'%s'", present.getName()) + ","
                + String.format("'%s'", present.getSex()) + ","
                + String.format("'%s'", present.getLife()) + ","
                + String.format("'%s'", present.getUnit()) + ","
                + String.format("'%s'", present.getPlace()) + ","
                + present.getImageId() + ","
                + String.format("'%s'", present.getInfo()) + ");";
        db.execSQL(sql);
        db.close();
    }
    public void deletePerson(String name) {
        Log.e("SqliteDataBase", "删除人物");
        SQLiteDatabase db = getWritableDatabase(); // 以读写的形式打开数据库
        String[] where = { name };
        db.delete("person", "name=?", where);
        db.close();
    }

    public void updatePerson(Present present) {
        Log.e("SqliteDataBase", "修改人物");
        SQLiteDatabase db = getWritableDatabase(); // 以读写的形式打开数据库
        String sql = "update person set name="
                + String.format("'%s'", present.getName()) + ",gender="
                + String.format("'%s'", present.getSex()) + ",date="
                + String.format("'%s'", present.getLife()) + ",force="
                + String.format("'%s'", present.getUnit()) + ",native="
                + String.format("'%s'", present.getPlace()) + ",image="
                + present.getImageId() + ",profile="
                + String.format("'%s'", present.getInfo()) + "where name="
                + String.format("'%s'", present.getName());
        db.execSQL(sql);
        db.close();
    }
    public List<Present> queryAllPerson() {
        Log.e("SqliteDataBase", "查询所有人物");
        List<Present> data = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from person;";
        Cursor cursor = db.rawQuery(sql, null);
        Present present = null;
        while (cursor.moveToNext()) {
            present = new Present(cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("gender")),
                    cursor.getString(cursor.getColumnIndex("date")),
                    cursor.getString(cursor.getColumnIndex("force")),
                    cursor.getString(cursor.getColumnIndex("native")),
                    cursor.getInt(cursor.getColumnIndex("image")),
                    cursor.getString(cursor.getColumnIndex("profile")));
            data.add(present);
        }
        return data;
    }
    public Present queryPerson(String name) {
        Log.e("SqliteDataBase", "查询人物");
        Present present = null;
        SQLiteDatabase db = getReadableDatabase(); // 以只读的形式打开数据库
        String[] colums = { "name", "gender", "date", "force", "native", "image", "profile" };
        String[] selectionArgs = {name};
        Cursor cursor = db.query("person", colums, "name=?", selectionArgs, null, null, null);
        if (cursor.moveToNext()) {
            present = new Present(cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("gender")),
                    cursor.getString(cursor.getColumnIndex("date")),
                    cursor.getString(cursor.getColumnIndex("force")),
                    cursor.getString(cursor.getColumnIndex("native")),
                    cursor.getInt(cursor.getColumnIndex("image")),
                    cursor.getString(cursor.getColumnIndex("profile")));
        }
        return present;
    }
}
