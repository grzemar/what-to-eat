package ynd.whattoeat.location;

import android.location.Location;

public interface LocationEventsListener {
	public void foundBetterLocation(Location newLocation);

	public void noLocationProviderAvailable();
}
