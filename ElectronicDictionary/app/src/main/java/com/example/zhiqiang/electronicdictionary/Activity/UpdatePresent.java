package com.example.zhiqiang.electronicdictionary.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.zhiqiang.electronicdictionary.Data.Present;
import com.example.zhiqiang.electronicdictionary.R;

public class UpdatePresent extends AppCompatActivity {
//    private TextView name, life, unit, place, info;
    private EditText name_edit, pinyin_edit, life_edit, unit_edit, place_edit, info_edit;
    private RadioGroup rg;
    private RadioButton gender;
    private Button cancel, confirm;
    private String genderString ,pinyinString, nameString, lifeString, unitString, placeString, infoString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_present);
        findView();
        updatePresent();
    }
    public void findView() {

        name_edit = (EditText) findViewById(R.id.name_edit);
        pinyin_edit = (EditText) findViewById(R.id.pinyin_edit);
        life_edit = (EditText) findViewById(R.id.life_edit);
        unit_edit = (EditText) findViewById(R.id.unit_edit);
        place_edit = (EditText) findViewById(R.id.place_edit);
        info_edit = (EditText) findViewById(R.id.info_edit);
        rg = (RadioGroup) findViewById(R.id.radioGroup);
        cancel = (Button) findViewById(R.id.cancel);
        confirm = (Button) findViewById(R.id.confirm);
    }
    public void updatePresent() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
                genderString = gender.getText().toString();
                nameString = name_edit.getText().toString();
                pinyinString = pinyin_edit.getText().toString();
                lifeString = life_edit.getText().toString();
                unitString = unit_edit.getText().toString();
                placeString = place_edit.getText().toString();
                infoString = info_edit.getText().toString();
                Resources res = getResources();
                int i = res.getIdentifier(pinyinString, "mipmap", getPackageName());
                Log.e("image", String.valueOf(i));
                Log.e("img", String.valueOf(R.mipmap.diaochan));
                Intent intent = new Intent(UpdatePresent.this, PresentList.class);
                Bundle bundle = new Bundle();
                Present present = new Present(nameString, genderString, lifeString, unitString, placeString, i, infoString);
                bundle.putSerializable("present", present);
                intent.putExtras(bundle);
                setResult(0, intent);
                finish();
            }
        });
    }
}
