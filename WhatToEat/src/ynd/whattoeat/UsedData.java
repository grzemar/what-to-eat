package ynd.whattoeat;

import ynd.whattoeat.location.LocationHelper;
import ynd.whattoeat.location.LocationUnknownException;
import ynd.whattoeat.weather.WeatherHelper;
import ynd.whattoeat.weather.WeatherUnavailableException;
import android.content.Context;

public class UsedData {
	private static UsedData instance;

	public static UsedData getInstance(Context context) {
		if (null == instance) {
			instance = new UsedData(context.getApplicationContext());
		}
		return instance;
	}

	private Context context;

	private UsedData(Context context) {
		this.context = context;
	}

	public String getUsedData() {
		StringBuilder ret = new StringBuilder();
		ret.append("User preferences\n");
		appendLocationInfo(ret);
		appendWeatherInfo(ret);
		return ret.toString();
	}

	private void appendWeatherInfo(StringBuilder ret) {
		try {
			ret.append("Weather: " + WeatherHelper.getInstance().getCachedWeather());
		} catch (WeatherUnavailableException e) {
			ret.append("Weather not available");
		}
		ret.append("\n");
	}

	private void appendLocationInfo(StringBuilder ret) {
		try {
			ret.append("Location: " + LocationHelper.getInstance(context).getCurrentAddress().getLocality());
		} catch (LocationUnknownException e) {
			ret.append("Location not avaialble");
		}
		ret.append("\n");
	}
}
