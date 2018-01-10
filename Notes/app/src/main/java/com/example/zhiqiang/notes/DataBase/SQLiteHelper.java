package com.example.zhiqiang.notes.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.zhiqiang.notes.Model.Note;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by zhiqiang on 2018/1/8.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME="notes.db";
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        Log.e("SqliteDataBase", "数据库创建");
        String sql = "create table note(id integer Primary Key autoincrement," +
                "time text," +
                "content text," +
                "recording text," +
                "imagePath text," +
                "position text);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("SqliteDataBase", "数据库更新");
    }
    public void createNote(Note note) {
        Log.e("SqliteDataBase", "增加日记");
        SQLiteDatabase db = getWritableDatabase(); // 以读写的形式打开数据库
//        if (queryPerson(note.getName()) != null) return;
        String sql = "insert into note(time, content, recording, imagePath, position) values("
                + String.format("'%s'", note.getTime()) + ","
                + String.format("'%s'", note.getContent()) + ","
                + String.format("'%s'", note.getRecord()) + ","
                + String.format("'%s'", note.getImagePath()) + ","
                + String.format("'%s'", note.getPosition()) + ");";
        db.execSQL(sql);
        db.close();
    }
    public void deleteNote(String time) {
        Log.e("SqliteDataBase", "删除日记");
        SQLiteDatabase db = getWritableDatabase(); // 以读写的形式打开数据库
        String[] where = { time };
        db.delete("note", "time=?", where);
        db.close();
    }

    public void updateNote(Note note) {
        Log.e("SqliteDataBase", "修改日记");
        SQLiteDatabase db = getWritableDatabase(); // 以读写的形式打开数据库
        String sql = "update note set time="
                + String.format("'%s'", note.getTime()) + ",content="
                + String.format("'%s'", note.getContent()) + ",recording="
                + String.format("'%s'", note.getRecord()) + ",imagePath="
                + String.format("'%s'", note.getImagePath()) + ",position="
                + String.format("'%s'", note.getPosition()) + "where time="
                + String.format("'%s'", note.getTime());
        db.execSQL(sql);
        db.close();
    }
    public List<Note> queryAllNote() {
        Log.e("SqliteDataBase", "查询所有日记");
        List<Note> data = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from note;";
        Cursor cursor = db.rawQuery(sql, null);
        Note note = null;
        while (cursor.moveToNext()) {
            note = new Note(cursor.getString(cursor.getColumnIndex("time")),
                    cursor.getString(cursor.getColumnIndex("content")),
                    cursor.getString(cursor.getColumnIndex("recording")),
                    cursor.getString(cursor.getColumnIndex("imagePath")),
                    cursor.getString(cursor.getColumnIndex("position")));
            data.add(note);
        }
        return data;
    }
    public Note queryNote(String time) {
        Log.e("SqliteDataBase", "查询日记");
        Note note = null;
        SQLiteDatabase db = getReadableDatabase(); // 以只读的形式打开数据库
        String[] colums = { "time", "content", "recording", "imagePath", "position"};
        String[] selectionArgs = { time };
        Cursor cursor = db.query("note", colums, "time=?", selectionArgs, null, null, null);
        if (cursor.moveToNext()) {
            note = new Note(cursor.getString(cursor.getColumnIndex("time")),
                    cursor.getString(cursor.getColumnIndex("content")),
                    cursor.getString(cursor.getColumnIndex("recording")),
                    cursor.getString(cursor.getColumnIndex("imagePath")),
                    cursor.getString(cursor.getColumnIndex("position")));
        }
        return note;
    }
}

