package ynd.whattoeat;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WeatherInformation {
	private String condition;
	private String tempC;

	public WeatherInformation(String weatherXml) {
		try {
			Document weatherDocument = XMLHelper.loadXMLFromString(weatherXml);
			Element currentConditions = (Element) weatherDocument.getElementsByTagName("current_conditions").item(0);
			this.condition = XMLHelper.getTextAttr(currentConditions, "condition", "data");
			this.tempC = XMLHelper.getTextAttr(currentConditions, "temp_c", "data");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return condition + " " + tempC + "C";
	}

}
