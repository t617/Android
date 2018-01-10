package com.example.zhiqiang.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhiqiang.notes.Adapter.CardAdapter;
import com.example.zhiqiang.notes.Adapter.ViewHolder;
import com.example.zhiqiang.notes.DataBase.SQLiteHelper;
import com.example.zhiqiang.notes.Model.Memo;
import com.example.zhiqiang.notes.Model.Note;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView timeDay;
    TextView timeWeekday;
    TextView timeMonth;
    private SQLiteHelper db;
    Button weatherBtn;
    Button newsBtn;
    FloatingActionButton addBtn;
    private RecyclerView recyclerView;
    List<Map<String, Object>> list = new ArrayList<>();
    private CardAdapter cardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new SQLiteHelper(this);
        findViews();
        setBtnClickListener();
        setRecyclerview();
        setTime();
        List<Note> notes = db.queryAllNote();
        for (Note n : notes) {
            Map<String, Object> listItem = new LinkedHashMap<>();
            listItem.put("time", n.getTime());
            listItem.put("content", n.getContent());
            list.add(listItem);;
        }

//        int primary = getResources().getColor(R.color.colorPrimaryDark);
//        int secondary = getResources().getColor(R.color.colorAccent);
//        mBuilder = new SlidrConfig.Builder().primaryColor(primary)
//                .secondaryColor(secondary)
//                .scrimColor(Color.BLACK)
//                .position(SlidrPosition.LEFT)
//                .scrimStartAlpha(0.8f)
//                .scrimEndAlpha(0f)
//                .velocityThreshold(5f)
//                .distanceThreshold(.35f);
//        mSlidrConfig = mBuilder.build();
//        Slidr.attach(this, mSlidrConfig);
    }

    private void findViews() {
        timeDay= (TextView) findViewById(R.id.timeDay);
        timeMonth= (TextView) findViewById(R.id.timeMonth);
        timeWeekday= (TextView) findViewById(R.id.timeWeekday);
        weatherBtn= (Button) findViewById(R.id.weatherBtn);
        newsBtn= (Button) findViewById(R.id.newsBtn);
        addBtn= (FloatingActionButton) findViewById(R.id.floatBtn);
    }

    private void setRecyclerview() {
        recyclerView = (RecyclerView) findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cardAdapter = new CardAdapter<Map<String, Object>>(this, R.layout.cardview, list) {
            @Override
            public void convert(ViewHolder holder, Map<String, Object> map) {
                TextView mTime = holder.getView(R.id.memoTime);
                TextView mContent = holder.getView(R.id.memoContent);
                mTime.setText(map.get("time").toString());
                mContent.setText(map.get("content").toString());
            }
        };

        cardAdapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, NoteContentActivity.class);
                Bundle bundle = new Bundle();
                Note note = db.queryNote(cardAdapter.getTime(position));
                Log.e("click", cardAdapter.getTime(position));
                bundle.putSerializable("note", note);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
            @Override
            public void onLongClick(int position) {
//                Log.e("click", cardAdapter.getTime(position));
                db.deleteNote(cardAdapter.getTime(position));
                cardAdapter.removeItem(position);
                Toast.makeText(MainActivity.this, "移除成功！", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(cardAdapter);
    }
    private void setTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;// Java月份从0开始算
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        timeMonth.setText(String.valueOf(year)+ "/"+ String.valueOf(month));
        timeDay.setText(String.valueOf(day));

        setWeekday(year, month, day);

    }

    private void setWeekday(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);//指定年份
        calendar.set(Calendar.MONTH, month - 1);//指定月份 Java月份从0开始算
        int daysCountOfMonth = calendar.getActualMaximum(Calendar.DATE);//获取指定年份中指定月份有几天

        //获取指定年份月份中指定某天是星期几
        calendar.set(Calendar.DAY_OF_MONTH, day);  //指定日
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1:
                timeWeekday.setText("Sunday");
                break;
            case 2:
                timeWeekday.setText("Monday");
                break;
            case 3:
                timeWeekday.setText("Tuesday");
                break;
            case 4:
                timeWeekday.setText("Wednesday");
                break;
            case 5:
                timeWeekday.setText("Thursday");
                break;
            case 6:
                timeWeekday.setText("Friday");
                break;
            case 7:
                timeWeekday.setText("Saturday");
                break;
        }
    }
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent d) {
        if(requestCode == 0) {
            if(resultCode == 1) {
                Note note = (Note) d.getExtras().get("note");
                if (note != null) {
                    db.createNote(note);
                    Memo memo = new Memo(note.getTime(), note.getContent());
                    cardAdapter.addData(memo);
                }
            }
        }
    }
    private void setBtnClickListener() {
        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mine", "weather click");
                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(intent);
            }
        });

        newsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mine", "news click");
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                startActivity(intent);
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mine", "add click");
                Intent intent = new Intent(MainActivity.this, NotesActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}
