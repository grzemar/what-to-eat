package ynd.whattoeat.weather;

public class WeatherInformation {
	private String condition;
	private String tempC;

	public WeatherInformation(String condition, String tempC) {
		super();
		this.condition = condition;
		this.tempC = tempC;
	}

	@Override
	public String toString() {
		return condition + " " + tempC + "C";
	}

}
