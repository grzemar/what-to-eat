package ynd.whattoeat.location;

import java.util.Timer;
import java.util.TimerTask;

import ynd.whattoeat.weather.WeatherHelper;
import ynd.whattoeat.weather.WeatherUnavailableException;
import android.app.Service;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class LocationService extends Service implements LocationEventsListener {
	private final IBinder binder = new LocalBinder();

	public class LocalBinder extends Binder {
		public LocationService getService() {
			return LocationService.this;
		}
	}

	private int boundActivitiesCount = 0;

	private Timer timer = new Timer();

	@Override
	public void onCreate() {
		startLocationHelper();
	};

	private void startLocationHelper() {
		LocationHelper locationHelper = LocationHelper.getInstance(this);
		locationHelper.setBestProviderCriteria(getBestLocationCriteria());
		locationHelper.addLocationEventsListeners(this);
	}

	private Criteria getBestLocationCriteria() {
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		return criteria;
	}

	@Override
	public IBinder onBind(Intent intent) {
		userBound();
		return binder;
	}

	@Override
	public void onRebind(Intent intent) {
		userBound();
		super.onRebind(intent);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		boundActivitiesCount--;
		if (boundActivitiesCount == 0)
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					LocationHelper.getInstance(LocationService.this).stopLocationUpdates();
				}
			}, 2000);
		return true;
	}

	private void userBound() {
		if (boundActivitiesCount == 0) {
			timer.cancel();
			timer = new Timer();
			LocationHelper.getInstance(LocationService.this).startLocationUpdates();
		}
		boundActivitiesCount++;
	}

	@Override
	public void foundBetterLocation(Location newLocation) {
		try {
			WeatherHelper.getInstance().updateCachedWeather(LocationHelper.getInstance(this).getCurrentAddress());
		} catch (WeatherUnavailableException e) {
			e.printStackTrace();
		} catch (LocationUnknownException e) {
			e.printStackTrace();
		} finally {
			Toast.makeText(this, "New input data available, expect more accurate results!", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void noLocationProviderAvailable() {
		Toast.makeText(this, "Enable location provider for better results!", Toast.LENGTH_LONG).show();
	}

}
