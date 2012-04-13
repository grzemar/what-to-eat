package ynd.whattoeat.location;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationHelper implements LocationListener {
	private static final int TWO_MINUTES = 1000 * 60 * 2;

	private static LocationHelper instance;

	public static LocationHelper getInstance(Context context) {
		if (instance == null)
			instance = new LocationHelper(context.getApplicationContext());
		return instance;
	}

	private Context context;
	private Location bestKnownLocation;
	private LocationManager locationManager;
	private Criteria bestProviderCriteria = new Criteria();

	private List<LocationEventsListener> locationEventsListeners = new LinkedList<LocationEventsListener>();

	private LocationHelper(Context context) {
		this.context = context;
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		setInitialLocation();
	}

	private void setInitialLocation() {
		for (String provider : locationManager.getAllProviders())
			reBestLocation(locationManager.getLastKnownLocation(provider));
	}

	public void startLocationUpdates() {
		listenFromBestProvider();
	}

	public void stopLocationUpdates() {
		locationManager.removeUpdates(this);
	}

	private void listenFromBestProvider() {
		String bestProvider = getBestProvider();
		if (bestProvider == null)
			fireNoLocationProviderAvailable();
		else
			locationManager.requestLocationUpdates(bestProvider, 0, 0, this);
	}

	private String getBestProvider() {
		return locationManager.getBestProvider(getBestProviderCriteria(), true);
	}

	@Override
	public void onLocationChanged(Location location) {
		reBestLocation(location);
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	private void reBestLocation(Location newLocation) {
		if (isBetterLocation(newLocation, bestKnownLocation)) {
			bestKnownLocation = newLocation;
			fireFoundBetterLocationEvent();
		}
	}

	public Address getCurrentAddress() throws LocationUnknownException {
		if (bestKnownLocation == null)
			throw new LocationUnknownException();
		Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);

		List<Address> addresses = new LinkedList<Address>();
		try {
			addresses = geocoder.getFromLocation(bestKnownLocation.getLatitude(), bestKnownLocation.getLongitude(), 1);
		} catch (IOException e) {
		} finally {
			if (addresses.isEmpty())
				throw new LocationUnknownException();
		}
		return addresses.get(0);
	}

	public void addLocationEventsListeners(LocationEventsListener listener) {
		locationEventsListeners.add(listener);
	}

	private void fireFoundBetterLocationEvent() {
		for (LocationEventsListener listener : locationEventsListeners)
			listener.foundBetterLocation(bestKnownLocation);
	}

	private void fireNoLocationProviderAvailable() {
		for (LocationEventsListener listener : locationEventsListeners)
			listener.noLocationProviderAvailable();
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

	public Location getBestKnownLocation() {
		return bestKnownLocation;
	}

	private Criteria getBestProviderCriteria() {
		return bestProviderCriteria;
	}

	public void setBestProviderCriteria(Criteria bestProviderCriteria) {
		this.bestProviderCriteria = bestProviderCriteria;
	}

}
