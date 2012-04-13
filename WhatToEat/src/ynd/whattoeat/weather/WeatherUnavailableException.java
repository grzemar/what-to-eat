package ynd.whattoeat.weather;

public class WeatherUnavailableException extends Exception {
	public WeatherUnavailableException() {
	}

	public WeatherUnavailableException(Exception e) {
		super(e);
	}

}
