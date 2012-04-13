package ynd.whattoeat.weather;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ynd.whattoeat.utils.UrlUtils;
import ynd.whattoeat.utils.XMLHelper;
import android.location.Address;

public class WeatherHelper {

	private static WeatherHelper instance;

	public static WeatherHelper getInstance() {
		if (instance == null)
			instance = new WeatherHelper();
		return instance;
	}

	private WeatherInformation cachedWeather;

	private WeatherHelper() {
	}

	public WeatherInformation getCachedWeather() throws WeatherUnavailableException {
		if (cachedWeather == null)
			throw new WeatherUnavailableException();
		return cachedWeather;
	}

	public void updateCachedWeather(Address address) throws WeatherUnavailableException {
		cachedWeather = getWeather(address);
	}

	public WeatherInformation getWeather(String location) throws WeatherUnavailableException {
		try {
			String weatherXml = getWeatherXml(location);
			Document weatherDocument = XMLHelper.loadXMLFromString(weatherXml);
			Element currentConditions = (Element) weatherDocument.getElementsByTagName("current_conditions").item(0);

			String condition = XMLHelper.getTextAttr(currentConditions, "condition", "data");
			String tempC = XMLHelper.getTextAttr(currentConditions, "temp_c", "data");

			return new WeatherInformation(condition, tempC);
		} catch (Exception e) {
			throw new WeatherUnavailableException(e);
		}
	}

	public WeatherInformation getWeather(Address address) throws WeatherUnavailableException {
		return getWeather(address.getLocality());
	}

	private String getWeatherXml(String location) throws URISyntaxException, IOException {
		URI uri = new URI("http", "www.google.com", "/ig/api", "weather=" + location, null);
		String src = uri.toASCIIString();
		return UrlUtils.getFromURL(src);
	}
}
