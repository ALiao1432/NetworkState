[![](https://jitpack.io/v/ALiao1432/NetworkState.svg)](https://jitpack.io/#ALiao1432/NetworkState)

# NetworkState
NetworkState is a library for android to monitor the network connecting state.

# Add library
1. Add it in your root build.gradle at the end of repositories
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
2. add the dependency
```gradle
dependencies {
  implementation 'com.github.ALiao1432:NetworkState:1.0.0'
}
```

# Getting started from listener or RxJava
Both way need to intiail in onCreate() and your context must be an instance of FragmentActivity
* listener
```java
new NetworkStateUtil(context, new NetworkStateChangeListener() {
	@Override
	public void onConnected(ConnectionType connectionType) {
	              
	}

	@Override
	public void onDisconnected() {

	}
});
```
* RxJava
In your onCreate()
```java
new RxNetworkStateUtil(this).getNetworkStateObservable()
                .doOnNext(connectionType -> Log.d(TAG, "onCreate: connectionType : " + connectionType))
                .doOnError(t -> Log.d(TAG, "onCreate: t : " + t))
                .subscribe();
```
