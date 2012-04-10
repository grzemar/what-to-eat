package ynd.whattoeat;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationHelper implements LocationListener {
	private static final int TWO_MINUTES = 1000 * 60 * 2;

	private static LocationHelper instance;

	private Location bestKnownLocation;

	private LocationHelper(Activity context) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

		reBestLocation(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
		reBestLocation(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
	}

	public static LocationHelper getInstance() {
		if (instance == null)
			throw new RuntimeException("Call init first!");
		return instance;
	}

	public static void init(Activity context) {
		instance = new LocationHelper(context);
	}

	private void reBestLocation(Location newLocation) {
		if (isBetterLocation(newLocation, bestKnownLocation))
			bestKnownLocation = newLocation;
	}

	protected boolean isBetterLocation(Location newLocation, Location oldLocation) {
		if (oldLocation == null) {
			return true;
		}
		if (newLocation == null) {
			return false;
		}

		long timeDelta = newLocation.getTime() - oldLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		if (isSignificantlyNewer) {
			return true;
		} else if (isSignificantlyOlder) {
			return false;
		}

		int accuracyDelta = (int) (newLocation.getAccuracy() - oldLocation.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		boolean isFromSameProvider = isSameProvider(newLocation.getProvider(), oldLocation.getProvider());

		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
			return true;
		}
		return false;
	}

	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	@Override
	public void onLocationChanged(Location location) {
		reBestLocation(location);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	public Location getBestKnownLocation() {
		return bestKnownLocation;
	}
}
