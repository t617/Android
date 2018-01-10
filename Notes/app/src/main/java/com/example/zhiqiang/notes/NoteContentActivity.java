package com.example.zhiqiang.notes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhiqiang.notes.Model.Note;

import java.io.IOException;

public class NoteContentActivity extends AppCompatActivity {
    private ImageView noteImage;
    private TextView noteDate;
    private TextView noteposition;
    private TextView noteContent;
    private Button play;
    private MediaPlayer player;
    private String recording;
    private String storagePath = Environment.getExternalStorageDirectory().getAbsolutePath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_content);
        findView();
        initialData();
        buttonListener();
    }
    /**
     * 初始化布局
     */
    private void findView() {
        recording = null;
        noteImage = (ImageView) findViewById(R.id.noteImage);
        noteDate = (TextView) findViewById(R.id.noteDate);
        noteposition = (TextView) findViewById(R.id.noteLocation);
        noteContent = (TextView) findViewById(R.id.noteContent);
        play = (Button) findViewById(R.id.play);
    }

    /**
     * 初始化数据
     */
    public void buttonListener() {
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play.setBackgroundResource(R.drawable.pause_icon);
                playRecord(recording);
            }
        });
    }
    private void initialData() {
        Note note = (Note) getIntent().getExtras().get("note");
        if (note != null) {
            noteDate.setText(note.getTime());
            if (note.getPosition() != null) {
                noteposition.setText(note.getPosition());
            }
            if (note.getContent() != null) {
                noteContent.setText(note.getContent());
            }
            if (note.getImagePath() != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(note.getImagePath());
                noteImage.setImageBitmap(bitmap);
            }
            if (note.getRecord() != null) {
                recording = note.getRecord();
            }
        }
    }
    public void playRecord(String uri) {
        if (uri != null){
            player = new MediaPlayer();
            try {
                player.reset();
                Log.e("play", uri);
                player.setDataSource(this, Uri.parse("content://media" + uri));
                player.prepare();
                player.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this,"播放失败",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onDestroy() {
        if(player != null) {
            if(player.isPlaying()){
                player.stop();
            }
            player.release();
        }
        super.onDestroy();
    }
}
