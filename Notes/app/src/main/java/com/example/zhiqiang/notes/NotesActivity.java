package com.example.zhiqiang.notes;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhiqiang.notes.Model.Note;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotesActivity extends ActionBarActivity implements View.OnClickListener {
    private ImageButton location;
    private ImageButton image;
    private ImageButton record;
    private ImageView imageView;
    private EditText content_text;
    private MediaPlayer player;
    private Button play;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private String storagePath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private TextView location_text;
    private Double lat;
    private Double lon;
    private String provider;
    private Location lastKnownLocation;
    private String time;
    private String position;
    private Uri recording;
    private String imagePath;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        bindViews();
        initLocationService();
//        Toast.makeText(this, getTime(), Toast.LENGTH_SHORT).show();
    }

    public void bindViews() {
        position = null;
        recording = null;
        imagePath = null;
        content = null;
        content_text = (EditText) findViewById(R.id.content);
        location_text = (TextView) findViewById(R.id.text);;
        location = (ImageButton) findViewById(R.id.location);
        image = (ImageButton) findViewById(R.id.image);
        record = (ImageButton) findViewById(R.id.recording);
        imageView = (ImageView) findViewById(R.id.image_view);
        play = (Button) findViewById(R.id.play);
        location.setOnClickListener(this);
        image.setOnClickListener(this);
        record.setOnClickListener(this);
        play.setOnClickListener(this);
    }
    public String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());//获取当前时间
        return simpleDateFormat.format(date);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public Note getNote() {
        time = getTime();
        Note note = null;
        String recordings = null;
        content = content_text.getText().toString();
        if (content.equals("")) {
            content = null;
        }
        if (recording != null) {
            recordings = recording.getPath();
        }
        if (position != null || recording != null || imagePath != null || content != null) {
            note = new Note(time, content, recordings, imagePath, position);
        }
        return note;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(NotesActivity.this, MainActivity.class);
                setResult(0, intent);
                finish();
                return true;
            case R.id.action_save:
                Note note = getNote();
                if (note != null) {
                    Bundle bundle = new Bundle();
                    intent = new Intent(NotesActivity.this, MainActivity.class);
                    bundle.putSerializable("note", note);
                    intent.putExtras(bundle);
                    setResult(1, intent);
                    finish();
                } else {
                    Toast.makeText(this, "内容为空", Toast.LENGTH_SHORT).show();
                }
                // User chose the "Settings" item, show the app settings UI...
                return true;
//            case R.id.action_favorite:
//                // User chose the "Favorite" action, mark the current item
//                // as a favorite...
//                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void getLocationList() {
        Geocoder gc = new Geocoder(NotesActivity.this, Locale.getDefault());
        List<Address> locationList = null;
        try {
            locationList = gc.getFromLocation(lat, lon, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address address = locationList.get(0);
        position = address.getAddressLine(0);
        location_text.setText(address.getAddressLine(0));
        Toast.makeText(this, "当前定位 " + address.getAddressLine(0), Toast.LENGTH_SHORT).show();
//        String countryName = address.getCountryName();
//        String locality = address.getLocality();
//        Toast.makeText(this, countryName + locality, Toast.LENGTH_SHORT).show();
        for (int i = 0; address.getAddressLine(i) != null; i++) {
            String addressLine = address.getAddressLine(i);//得到周边信息，包括街道等，i=0，得到街道名称
            Log.e("location", "addressLine = " + addressLine);
        }
    }

    public void initLocationService() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        provider = LocationManager.GPS_PROVIDER;//1.通过GPS定位，较精确，也比较耗电
        provider = LocationManager.NETWORK_PROVIDER;//2.通过网络定位，对定位精度度不高或省点情况可考虑使用
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                lon = location.getLongitude();
//                Log.i(TAG, "时间："+ location.getTime());
//                Log.i(TAG, "经度："+ location.getLongitude());
//                Log.i(TAG, "纬度："+ location.getLatitude());
//                Log.i(TAG, "海拔："+ location.getAltitude());
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override
            public void onProviderEnabled(String provider) {}
            @Override
            public void onProviderDisabled(String provider) {}
        };
//        lastKnownLocation = locationManager.getLastKnownLocation(provider);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        checkPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        checkPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
    }
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.image:
//                //打开手机的图库;
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 0x1);
                break;
            case R.id.camera:
                intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//调用系统的照相机
                startActivityForResult(intent, 0x2);
                break;
            case R.id.location:
                getLocationList();
//                if (lastKnownLocation != null) {
//                    Toast.makeText(this, (int) lastKnownLocation.getLatitude(), Toast.LENGTH_SHORT).show();
//                }
                break;
            case R.id.recording:
                intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                startActivityForResult(intent, 0x3);
                break;
            case R.id.play:
                play.setBackgroundResource(R.drawable.pause_icon);
                playRecord(recording);
        }
    }
//    public byte[] bitMapToByte(Bitmap bitmap) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        return baos.toByteArray();
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x1) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Uri selectedImage = data.getData();
                String [] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null,
                        null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);
                Log.e("路径", imagePath);
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                imageView.setImageBitmap(bitmap);
                cursor.close();
            }
        }
        if (requestCode == 0x2) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Bundle bundle=data.getExtras();
                Bitmap bitmap=(Bitmap)bundle.get("data");
                imageView.setImageBitmap(bitmap);
            }
        }
        if (requestCode == 0x3) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Uri uri = data.getData();
                recording = uri;
                play.setVisibility(View.VISIBLE);
//                playRecord(uri);
            }
        }
    }
    public void playRecord(Uri uri) {
        if (uri != null){
            player = new MediaPlayer();
            try {
                player.reset();
                player.setDataSource(this, uri);
                Log.e("uri", String.valueOf(uri));
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
    private static void checkPermission(Context context, String permission) {
        int perm = context.checkCallingOrSelfPermission(permission);
        String[] permissions = {permission};
        if (perm != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)context, permissions, 1);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String Permissions[], int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "权限获取成功", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "需要获取" + Permissions, Toast.LENGTH_LONG).show();
            System.exit(0);
        }
    }
//    public void init() {
//        if (recorder == null)
//            recorder = new MediaRecorder();
//        recorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 音频输入源
//        recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);//设置输出格式
//        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);    //设置编码格式
//        String fileName = DateFormat.format("yyyy-MM-dd-HH:mm:ss", Calendar.getInstance(Locale.CHINA)) + ".amr";
//        recordFile = new File(storagePath, fileName);;
//        recorder.setOutputFile(recordFile.getAbsolutePath());
//    }
//    public void startRecord() {
//        if( recorder == null )
//            init();
//        if (flag) {
//            try {
//                recorder.prepare();
//                recorder.start();
//                Toast.makeText(getApplicationContext(), "开始录音", Toast.LENGTH_LONG).show();
//            } catch (IllegalStateException e) {
//                Toast.makeText(getApplicationContext(), "录制非法", Toast.LENGTH_LONG).show();
//                e.printStackTrace();
//            } catch (IOException e) {
//                Toast.makeText(getApplicationContext(), "录制音频出现异常", Toast.LENGTH_LONG).show();
//                e.printStackTrace();
//            }
//
//            flag = false;
//        } else {
//            Toast.makeText(getApplicationContext(), "当前正在录制音频", Toast.LENGTH_LONG).show();
//        }
//
//    }
//    public void stopRecord() {
//        if(recorder != null) {
//            try {
//                recorder.stop();
//                recorder.release();
//                recorder = null;
//                Toast.makeText(getApplicationContext(), "已经结束,文件保存在" + recordFile, Toast.LENGTH_LONG).show();
//                flag = true;
//            } catch (RuntimeException e) {
//                recorder.reset();
//                recorder.release();
//                recorder = null;
//                if (recordFile.exists())
//                    recordFile.delete();
//            }
//        }
//    }
}
