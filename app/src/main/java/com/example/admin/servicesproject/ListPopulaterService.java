package com.example.admin.servicesproject;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class ListPopulaterService extends IntentService {

    private final IBinder mIBinder = new LocalBinder();

    public ListPopulaterService() {
        super("ListPopulaterService");
    }
    Handler handler;

    @Override
    protected void onHandleIntent(Intent intent)
    {

    }

    public List<RandomObject> getObjects()
    {
        List<RandomObject> result = new ArrayList<RandomObject>();
        result.add(new RandomObject(0, 1, 2, 3));
        result.add(new RandomObject(0, 1, 2, 3));
        result.add(new RandomObject(0, 1, 2, 3));
        result.add(new RandomObject(0, 1, 2, 3));
        result.add(new RandomObject(0, 1, 2, 3));
        Message msg = new Message();
        return result;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(handler != null)
        {
            handler = null;
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public IBinder onBind(Intent intent)
    {
        return mIBinder;
    }

    public void setHandler(Handler handler)
    {
        this.handler = handler;
    }

    public class LocalBinder extends Binder
    {
        public ListPopulaterService getInstance()
        {
            return ListPopulaterService.this;
        }
    }
}
