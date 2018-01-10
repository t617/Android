package com.example.zhiqiang.lab2;

import android.content.DialogInterface;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Objects;

import static android.widget.RadioGroup.*;

public class MainActivity extends AppCompatActivity {
    final int fRG = R.id.rG;
    final int fRB1 = R.id.rB1;
    final int fRB2 = R.id.rB2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView mImage = (ImageView) findViewById(R.id.image);
        imageDialog(mImage);

        RadioGroup mRG = (RadioGroup) findViewById(R.id.rG);
        switchRadioButton(mRG);

        Button mB1 = (Button) findViewById(R.id.button1);
        onClickButton1(mB1);

        Button mB2 = (Button) findViewById(R.id.button2);
        onClickButton2(mB2);
    }

    public void onClickButton1(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputLayout mNumberText = (TextInputLayout) findViewById(R.id.editText1);
                TextInputLayout mPassText = (TextInputLayout) findViewById(R.id.editText2);
                String mSid = mNumberText.getEditText().getText().toString().trim();
                String mPwd = mPassText.getEditText().getText().toString().trim();
                if (TextUtils.isEmpty(mSid)) {
                    mNumberText.setErrorEnabled(true);
                    mNumberText.setError("学号不能为空");
                } else {
                    if (TextUtils.isEmpty(mPwd)) {
                        mPassText.setErrorEnabled(true);
                        mPassText.setError("密码不能为空");
                    } else {
                        if (Objects.equals(mSid, "123456") && Objects.equals(mPwd, "6666")) {
                            Snackbar.make(view, "登录成功", Snackbar.LENGTH_SHORT)
                                    .setAction("确定", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Toast.makeText(getApplicationContext(),
                                                    "Snackbar的确定按钮被点击了", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                                    .setDuration(5000)
                                    .show();
                        } else {
                            Snackbar.make(view, "密码或学号错误", Snackbar.LENGTH_SHORT)
                                    .setAction("确定", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Toast.makeText(getApplicationContext(),
                                                    "Snackbar的确定按钮被点击了", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                                    .setDuration(5000)
                                    .show();
                        }
                    }
                }
            }
        });
    }

    public void onClickButton2(final Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioGroup mRG = (RadioGroup) findViewById(fRG);;
                RadioButton mRB = (RadioButton) findViewById(mRG.getCheckedRadioButtonId());
                String mRBV = mRB.getText().toString();
                Snackbar.make(view, mRBV + "注册功能尚未启用", Snackbar.LENGTH_SHORT)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getApplicationContext(),
                                        "Snackbar的确定按钮被点击了", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                        .setDuration(5000)
                        .show();
            }
        });
    }

    public void switchRadioButton(RadioGroup rG) {
        rG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case fRB1:
                        Snackbar.make(radioGroup, "您选择了学生", Snackbar.LENGTH_SHORT)
                                .setAction("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(getApplicationContext(),
                                                "Snackbar的确定按钮被点击了", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                                .setDuration(5000)
                                .show();
                        break;
                    case fRB2:
                        Snackbar.make(radioGroup, "您选择了教职工", Snackbar.LENGTH_SHORT)
                                .setAction("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(getApplicationContext(),
                                                "Snackbar的确定按钮被点击了", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                                .setDuration(5000)
                                .show();
                        break;
                }
            }
        });
    }

    public void imageDialog(final ImageView imageView) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        final String[] items = {"拍摄", "从相册选择"};
        alertDialog.setTitle("上传头像").setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "您选择了[" + items[i] + ']', Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),
                                "你选择了[取消]", Toast.LENGTH_SHORT).show();
                    }
                }).create();

        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.show();
                }
            });
        }
    }
}
