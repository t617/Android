package com.example.zhiqiang.lab7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static int MODE = MODE_PRIVATE;
    public static final String PREFERENCE_NAME = "password";
    private Button ok, clear;
    private TextInputLayout nPwd, cPwd;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        signIn();
    }
    public void findView() {
        ok = (Button) findViewById(R.id.ok);
        clear = (Button) findViewById(R.id.clear);
        nPwd = (TextInputLayout) findViewById(R.id.nPwd);
        cPwd = (TextInputLayout) findViewById(R.id.cPwd);
    }
    public void signIn() {
        sharedPreferences = getSharedPreferences (PREFERENCE_NAME, MODE);
        editor = sharedPreferences.edit();
        String pwdStr = sharedPreferences.getString("Password", "");
        if (!pwdStr.equals("")) {
            nPwd.setVisibility(View.GONE);
            cPwd.getEditText().setHint("Password");
            startButtonListener(pwdStr);
        } else {
            initialButtonListener();
        }
    }
    public void startButtonListener(final String pwd) {
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cPwdStr = cPwd.getEditText().getText().toString();
                if (TextUtils.isEmpty(cPwdStr)) {
                    Toast.makeText(getApplicationContext(),
                            "Password cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (cPwdStr.equals(pwd)) {
                        Intent intent = new Intent(MainActivity.this, EditTextActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Password Mismatch", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cPwd.getEditText().setText("");
            }
        });
    }
    public void initialButtonListener() {
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nPwdStr = nPwd.getEditText().getText().toString();
                String cPwdStr = cPwd.getEditText().getText().toString();
                if (TextUtils.isEmpty(nPwdStr)) {
                    Toast.makeText(getApplicationContext(),
                            "Password cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (TextUtils.isEmpty(cPwdStr)) {
                        Toast.makeText(getApplicationContext(),
                                "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    } else {
                        if (cPwdStr.equals(nPwdStr)) {
                            editor.putString("Password", nPwdStr).commit();
                            Intent intent = new Intent(MainActivity.this, EditTextActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Password Mismatch", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nPwd.getEditText().setText("");
                cPwd.getEditText().setText("");
            }
        });
    }
}
