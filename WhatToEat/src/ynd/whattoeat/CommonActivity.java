package ynd.whattoeat;

import ynd.whattoeat.location.LocationService;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

public abstract class CommonActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());
		loadControls();
		setClickListeners();
	}

	private ServiceConnection serviceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
		}

		@Override
		public void onServiceDisconnected(ComponentName className) {
		}
	};

	@Override
	protected void onPause() {
		super.onPause();

		unbindService(serviceConnection);
	}

	@Override
	protected void onResume() {
		super.onResume();

		startService(new Intent(this, LocationService.class));
		bindService(new Intent(this, LocationService.class), serviceConnection, Context.BIND_AUTO_CREATE);
	}

	protected abstract int getLayoutId();

	protected abstract void setClickListeners();

	protected abstract void loadControls();

}