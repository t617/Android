package com.example.zhiqiang.lab8;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button addButton;
    private ListView personList;
    private List<PersonInfo> data;
    private List<Map<String, Object>> listItems = new ArrayList<>();
    private AlertDialog.Builder builder1, builder2;
    private SimpleAdapter simpleAdapter;
    protected static SQLiteHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        db = new SQLiteHelper(this);
        initialList();
        setListener();
    }
    public void findView() {
        addButton = (Button) findViewById(R.id.addButton);
        personList = (ListView) findViewById(R.id.personList);
        addButton.setOnClickListener((View.OnClickListener) this);
        builder1 = new AlertDialog.Builder(MainActivity.this);
        builder2 = new AlertDialog.Builder(MainActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addButton:
                Intent intent = new Intent(MainActivity.this, AddPersonActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1) {
            if (resultCode == 1) {
                PersonInfo personInfo = (PersonInfo) intent.getExtras().get("personInfo");
                Map<String, Object> listItem = new LinkedHashMap<>();
                assert personInfo != null;
                listItem.put("name", personInfo.getName());
                listItem.put("birth", personInfo.getBirthday());
                listItem.put("gift", personInfo.getGift());
                data.add(personInfo);
                db.insert(personInfo);
                listItems.add(listItem);
                simpleAdapter.notifyDataSetChanged();
            }
        }
    }

    public void initialList() {
        data = db.queryAllPerson();
        for (PersonInfo p : data) {
            Map<String, Object> listItem = new LinkedHashMap<>();
            listItem.put("name", p.getName());
            listItem.put("birth", p.getBirthday());
            listItem.put("gift", p.getGift());
            listItems.add(listItem);
        }
        simpleAdapter = new SimpleAdapter(this, listItems, R.layout.person_list,
                new String[]{"name", "birth", "gift"}, new int[]{R.id.name, R.id.birth, R.id.gift});
        personList.setAdapter(simpleAdapter);

    }
    public void setListener() {
        personList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                LayoutInflater factor = LayoutInflater.from(MainActivity.this);
                View view_in = factor.inflate(R.layout.person_info, null);
                /* 获取对话框 */
                TextView nameText = (TextView) view_in.findViewById(R.id.nameText);
                TextView phoneText = (TextView) view_in.findViewById(R.id.phoneText);
                final EditText birthEditText = (EditText) view_in.findViewById(R.id.birthEditText);
                final EditText giftEditText = (EditText) view_in.findViewById(R.id.giftEditText);
                nameText.setText(data.get(position).getName());
                birthEditText.setText(data.get(position).getBirthday());
                giftEditText.setText(data.get(position).getGift());
                /* 使用 ContentProvider 来获取手机通讯录中的电话号码 */
                String[] colums = { ContactsContract.Contacts.HAS_PHONE_NUMBER, ContactsContract.Contacts._ID };
                String[] selectionArgs = { data.get(position).getName() };
                Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, colums,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = ?", selectionArgs, null);
                if (cursor.moveToNext()) {
                    int isHas = Integer. parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER )));
                    if (isHas > 0) {
                        String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        Cursor phone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                        String number = "";
                        while(phone.moveToNext()) {
                            number += phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) + " ";
                        }
                        phoneText.setText(number);
                    }
                }

                builder1.setTitle("ヽ(*￣∀￣)ﾉ～★恭喜发财★～");
                builder1.setView(view_in);
                builder1.setNegativeButton("放弃修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                }).setPositiveButton("保存修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String birth = birthEditText.getText().toString();
                        String gift = giftEditText.getText().toString();
                        PersonInfo personInfo = new PersonInfo(data.get(position).getName(), birth, gift);
                        data.set(position, personInfo);
                        db.update(personInfo);
                        listItems.get(position).put("birth", birth);
                        listItems.get(position).put("gift", gift);
                        simpleAdapter.notifyDataSetChanged();
                    }
                }).create().show();
            }
        });
        personList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                builder2.setMessage("是否删除？");
                builder2.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                }).setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listItems.remove(position);
                        db.delete(data.get(position).getName());
                        data.remove(position);
                        simpleAdapter.notifyDataSetChanged();
                    }
                }).create().show();
                return true;
            }
        });
    }
}
