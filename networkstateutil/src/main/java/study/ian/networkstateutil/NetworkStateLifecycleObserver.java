package study.ian.networkstateutil;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

public class NetworkStateLifecycleObserver implements DefaultLifecycleObserver {

    private BroadcastReceiver networkChangeReceiver;
    private Context context;
    private boolean isRegistered = false;

    NetworkStateLifecycleObserver(Context context, BroadcastReceiver broadcastReceiver, Lifecycle lifecycle) {
        this.context = context;
        networkChangeReceiver = broadcastReceiver;
        lifecycle.addObserver(this);
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        if (!isRegistered) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            context.registerReceiver(networkChangeReceiver, intentFilter);
            isRegistered = true;
        }
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        if (isRegistered) {
            context.unregisterReceiver(networkChangeReceiver);
            isRegistered = false;
        }
    }

    boolean isRegistered() {
        return isRegistered;
    }

    void unregisterNetworkChangeReceiver() {
        if (isRegistered) {
            context.unregisterReceiver(networkChangeReceiver);
        }
    }
}
