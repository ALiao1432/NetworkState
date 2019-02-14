package study.ian.networkstateutil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import io.reactivex.Observable;
import io.reactivex.processors.PublishProcessor;

import static study.ian.networkstateutil.NetworkStateUtil.getNetWorkConnectType;
import static study.ian.networkstateutil.NetworkStateUtil.isNetworkConnected;

public class RxNetworkStateUtil {

    private PublishProcessor<ConnectionType> typePublishProcessor = PublishProcessor.create();

    public RxNetworkStateUtil(Context context) {
        new NetworkStateLifecycleObserver(context, new NetworkChangeReceiver(), ((FragmentActivity) context).getLifecycle());
    }

    public Observable<ConnectionType> getNetworkStateObservable() {
        return typePublishProcessor.toObservable();
    }

    class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectionType connectionType = ConnectionType.NO_NETWORK;
            if (isNetworkConnected(context)) {
                connectionType = getNetWorkConnectType(context);
            }
            typePublishProcessor.onNext(connectionType);
        }
    }
}
