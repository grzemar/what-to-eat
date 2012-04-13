package ynd.whattoeat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import android.content.Context;
import android.location.Address;

public class WeatherHelper {

	private static WeatherHelper instance;

	public static WeatherHelper getInstance(Context context) {
		if (instance == null)
			instance = new WeatherHelper(context.getApplicationContext());
		return instance;
	}

	private Context context;

	private WeatherHelper(Context applicationContext) {
		this.context = applicationContext;
	}

	public WeatherInformation getCurrentWeatherInformation() throws LocationUnknownException, AddressUnknownException {
		Address currentAddress = LocationHelper.getInstance(context).getCurrentAddress();
		return getWeatherInformation(currentAddress);
	}

	public WeatherInformation getWeatherInformation(Address address) {
		return getWeatherInformation(address.getLocality());

	}

	public WeatherInformation getWeatherInformation(String location) {
		try {
			String weatherXml = getWeatherXml(location);
			return new WeatherInformation(weatherXml);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getWeatherXml(String location) throws URISyntaxException, IOException {
		URI uri = new URI("http", "www.google.com", "/ig/api", "weather=" + location, null);
		String src = uri.toASCIIString();
		return Utils.getFromURL(src);
	}
}
