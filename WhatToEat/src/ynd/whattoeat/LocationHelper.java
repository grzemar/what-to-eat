package ynd.whattoeat;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationHelper implements LocationListener {
	private static final int TWO_MINUTES = 1000 * 60 * 2;

	private static LocationHelper instance;

	public static LocationHelper getInstance() {
		if (instance == null)
			throw new RuntimeException("Call init first!");
		return instance;
	}

	private Activity context;
	private Location bestKnownLocation;
	private LocationManager locationManager;
	private boolean updatingLocation = false;
	private List<FoundBetterLocationListener> foundBetterLocationListeners = new LinkedList<FoundBetterLocationListener>();

	private LocationHelper(Activity context) {
		this.context = context;
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		setInitialLocation();
	}

	private void setInitialLocation() {
		reBestLocation(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
		reBestLocation(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
	}

	public void startLocationUpdates() {
		if (!updatingLocation) {
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
			updatingLocation = true;
		}
	}

	public void stopLocationUpdates() {
		if (updatingLocation) {
			locationManager.removeUpdates(this);
			updatingLocation = false;
		}
	}

	public static void init(Activity context) {
		instance = new LocationHelper(context);
		instance.startLocationUpdates();
	}

	private void reBestLocation(Location newLocation) {
		if (isBetterLocation(newLocation, bestKnownLocation)) {
			bestKnownLocation = newLocation;
			fireFoundBetterLocationEvent();
		}
	}

	private void fireFoundBetterLocationEvent() {
		for (FoundBetterLocationListener listener : foundBetterLocationListeners)
			listener.foundBetterLocation(bestKnownLocation);
	}

	public void addFoundBetterLocationListener(FoundBetterLocationListener listener) {
		foundBetterLocationListeners.add(listener);
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

	public Address getCurrentAddress() {
		Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);

		try {
			List<Address> addresses = geocoder.getFromLocation(bestKnownLocation.getLatitude(), bestKnownLocation.getLongitude(), 1);
			if (addresses.size() > 0)
				return addresses.get(0);
		} catch (IOException e) {
		}

		return null;
	}
}
