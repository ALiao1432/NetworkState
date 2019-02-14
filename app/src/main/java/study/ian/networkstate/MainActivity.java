package study.ian.networkstate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import study.ian.networkstateutil.ConnectionType;
import study.ian.networkstateutil.NetworkStateChangeListener;
import study.ian.networkstateutil.NetworkStateUtil;
import study.ian.networkstateutil.RxNetworkStateUtil;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private TextView textView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text);
        textView.setOnTouchListener((v, event) -> {
            boolean valid = NetworkStateUtil.isNetworkConnectionValid(MainActivity.this);
            textView.setText(valid ? "true" : "false");
            return false;
        });

        new NetworkStateUtil(this, new NetworkStateChangeListener() {
            @Override
            public void onConnected(ConnectionType connectionType) {
                Log.d(TAG, "onConnected: type : " + connectionType.toString());
            }

            @Override
            public void onDisconnected() {
                Log.d(TAG, "onDisconnected: ");
            }
        });

        RxNetworkStateUtil rxNetworkStateUtil = new RxNetworkStateUtil(this);
        rxNetworkStateUtil.getNetworkStateObservable()
                .doOnNext(connectionType -> Log.d(TAG, "onCreate: connectionType : " + connectionType))
                .doOnError(throwable -> Log.d(TAG, "onCreate: error : " + throwable))
                .subscribe();
    }
}
