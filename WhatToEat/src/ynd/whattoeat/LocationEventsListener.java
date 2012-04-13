package ynd.whattoeat;

import android.location.Location;

public interface LocationEventsListener {
	public void foundBetterLocation(Location newLocation);

	public void noLocationProviderAvailable();
}
