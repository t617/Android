package com.example.zhiqiang.lab7;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class EditTextActivity extends AppCompatActivity {
    private Button save, laod, clear, delete;
    private EditText fileName, fileContent;
    public static String FILE_NAME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        findView();
        buttonListener();
    }
    public void findView() {
        save = (Button) findViewById(R.id.save);
        laod = (Button) findViewById(R.id.laod);
        clear = (Button) findViewById(R.id.clear);
        delete = (Button) findViewById(R.id.delete);
        fileName = (EditText) findViewById(R.id.fileName);
        fileContent = (EditText) findViewById(R.id.fileContent);
    }
    public void buttonListener() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FILE_NAME = fileName.getText().toString() + ".txt";
                String str = fileContent.getText().toString();
                try (FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
                    fileOutputStream.write(str.getBytes());
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    Toast.makeText(getApplicationContext(),
                            "Successfully saved file.", Toast.LENGTH_SHORT).show();
                    Log.i("TAG", "Successfully saved file.");
                } catch (IOException ex) {
                    Log.e("TAG", "Fail to save file.");
                }
            }
        });
        laod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FILE_NAME = fileName.getText().toString() + ".txt";
                try (FileInputStream fileInputStream = openFileInput(FILE_NAME)) {
                    byte[] contents = new byte[fileInputStream.available()];
                    fileInputStream.read(contents);
                    String str = new String(contents);
                    fileContent.setText(str);
                    fileInputStream.close();
                    Log.e("TAG", "Successfully load file.");
                    Toast.makeText(getApplicationContext(),
                            "Successfully load file.", Toast.LENGTH_SHORT).show();
                }
                catch (IOException ex) {
                    Toast.makeText(getApplicationContext(),
                            "Fail to laod file.", Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "Fail to read file.");
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FILE_NAME = fileName.getText().toString() + ".txt";
                String str = "";
                try (FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
                    fileOutputStream.write(str.getBytes());
                    fileOutputStream.close();
                    fileContent.setText(str);
                    Log.i("TAG", "Successfully clear file.");
                } catch (IOException ex) {
                    Log.e("TAG", "Fail to clear file.");
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FILE_NAME = fileName.getText().toString() + ".txt";
                try {
                    deleteFile(FILE_NAME);
                    Log.e("TAG", "delete file.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
