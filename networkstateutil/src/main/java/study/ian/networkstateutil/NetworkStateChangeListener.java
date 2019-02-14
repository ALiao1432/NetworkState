package study.ian.networkstateutil;

public interface NetworkStateChangeListener {

    void onConnected(ConnectionType connectionType);

    void onDisconnected();
}
