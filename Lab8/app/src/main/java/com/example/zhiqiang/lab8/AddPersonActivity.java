package com.example.zhiqiang.lab8;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPersonActivity extends AppCompatActivity {
    private EditText nameEditText, birthEditText, giftEditText;
    private Button addAction;
    private String name, birth, gift;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        findView();
        setListener();
    }
    public void findView() {
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        birthEditText = (EditText) findViewById(R.id.birthEditText);
        giftEditText = (EditText) findViewById(R.id.giftEditText);
        addAction = (Button) findViewById(R.id.addAction);
    }
    public void setListener() {
        addAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEditText.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(getApplicationContext(),"名字为空，请完善", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (MainActivity.db.queryPerson(name) != null) {
                    Toast.makeText(getApplicationContext(),"名字重复啦，请检查", Toast.LENGTH_SHORT).show();
                    return;
                }
                birth = birthEditText.getText().toString();
                gift = giftEditText.getText().toString();
                Intent intent = new Intent(AddPersonActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                PersonInfo personInfo = new PersonInfo(name, birth, gift);
                bundle.putSerializable("personInfo", personInfo);
                intent.putExtras(bundle);
                setResult(1 ,intent);
                finish();
            }
        });
    }
}
