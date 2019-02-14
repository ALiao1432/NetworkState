package study.ian.networkstateutil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;

public class NetworkStateUtil {

    private NetworkStateChangeListener networkStateChangeListener;
    private NetworkStateLifecycleObserver networkStateLifecycleObserver;

    public NetworkStateUtil(Context context, NetworkStateChangeListener networkStateChangeListener) {
        this.networkStateChangeListener = networkStateChangeListener;
        networkStateLifecycleObserver =
                new NetworkStateLifecycleObserver(context, new NetworkChangeReceiver(), ((FragmentActivity) context).getLifecycle());
    }

    public static ConnectionType getNetWorkConnectType(Context context) {
        ConnectivityManager connectivityManager = context.getSystemService(ConnectivityManager.class);
        Network network = connectivityManager.getActiveNetwork();

        if (network != null) {
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);

            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return ConnectionType.WIFI;
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return ConnectionType.MOBILE;
            }
        }

        return ConnectionType.NO_NETWORK;
    }

    public static boolean isNetworkConnectionValid(Context context) {
        ConnectivityManager connectivityManager = context.getSystemService(ConnectivityManager.class);
        Network network = connectivityManager.getActiveNetwork();

        if (network != null) {
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
        } else {
            return false;
        }
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = context.getSystemService(ConnectivityManager.class);

        if (connectivityManager != null) {
            NetworkInfo networkInfo= connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        } else {
            return false;
        }
    }

    public void unregisterNetworkChangeReceiver() {
        if (networkStateLifecycleObserver.isRegistered()) {
            networkStateLifecycleObserver.unregisterNetworkChangeReceiver();
        }
    }

    class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (isNetworkConnected(context)) {
                ConnectionType connectionType = getNetWorkConnectType(context);
                if (networkStateChangeListener != null) {
                    networkStateChangeListener.onConnected(connectionType);
                }
            } else {
                if (networkStateChangeListener != null) {
                    networkStateChangeListener.onDisconnected();
                }
            }
        }
    }
}
