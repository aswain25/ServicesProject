package com.example.admin.servicesproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity
{
    MediaPlayer mediaPlayer;
    private ListView listView;
    private ArrayAdapter<RandomObject> adapter;
    private List<RandomObject> listObjects = new ArrayList<>();
    private ListPopulaterService mService = null;
    public static RandomObject current;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder)
        {
            mService = ((ListPopulaterService.LocalBinder)iBinder).getInstance();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName)
        {
            mService = null;
        }
    };
    private boolean mIsBound;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = new MediaPlayer();
        EventBus.getDefault().register(this);
        listView = findViewById(R.id.lvRanObjs);
        listObjects.add(new RandomObject(2,2 ,2 ,2 ));
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listObjects);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), AlarmHandler.class);
                RandomObject o = listObjects.get(position);
                intent.putExtra("data", o);
                current = o;
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), UUID.randomUUID().hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pendingIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, ListPopulaterService.class);
        bindIntentService();
    }

    public void btnClicked(View view)
    {
        Intent intent;
        switch (view.getId())
        {
            case R.id.btnMusic:
                playMusic();
                intent = new Intent(MainActivity.this, ForegroundMusicService.class);
                intent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                startService(intent);
                break;
            case R.id.btnList:
                if (mIsBound)
                {
                    listObjects.addAll(mService.getObjects());
                    adapter.notifyDataSetChanged();
                }
                break;
        }

    }

    public void playMusic()
    {
        String url = "http://66.90.93.122/ost/super-mario-galaxy-2/ttwvqlhr/2-16%20Throwback%20Galaxy.mp3"; // your URL here
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try
        {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    @Subscribe
    public void onEvent(String s)
    {
        adapter.notifyDataSetChanged();
        if (s.equals("pause"))
            mediaPlayer.pause();
        else if (s.equals("play"))
            mediaPlayer.start();
    }

    private void bindIntentService()
    {
        // Establish a connection with the service.  We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
        bindService(new Intent(MainActivity.this,
                ListPopulaterService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    private void unbindIntentService()
    {
        if (mIsBound)
        {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindIntentService();
    }
}
