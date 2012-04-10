package ynd.whattoeat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import android.location.Address;

public class WeatherHelper {
	public static WeatherInformation getCurrentWeatherInformation() {
		Address currentAddress = LocationHelper.getInstance().getCurrentAddress();
		return getWeatherInformation(currentAddress);
	}

	public static WeatherInformation getWeatherInformation(Address address) {
		return getWeatherInformation(address.getLocality());

	}

	public static WeatherInformation getWeatherInformation(String location) {
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
