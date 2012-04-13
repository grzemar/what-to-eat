package ynd.whattoeat.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.Toast;

public class UrlUtils {

	private static Map<String, Bitmap> cachedImages = new HashMap<String, Bitmap>();

	public static String getFromURL(String src) throws IOException {
		InputStream input = openStream(src);
		BufferedReader in = new BufferedReader(new InputStreamReader(input));

		String all = "";
		String s;
		while ((s = in.readLine()) != null)
			all += s + "\n";

		return all;
	}

	public static Bitmap getBitmapFromURL(String src) throws MalformedURLException, IOException {
		if (cachedImages.containsKey(src))
			return cachedImages.get(src);

		InputStream input = openStream(src);
		Bitmap bitmap = BitmapFactory.decodeStream(input);
		cachedImages.put(src, bitmap);
		return bitmap;
	}

	public static Bitmap getFirstGoogleImage(String query) throws IOException, JSONException {
		String googleResult = UrlUtils.getFromURL(String.format("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=%s", Uri.encode(query + " tasty")));
		JSONObject googleResultJSON = new JSONObject(googleResult);
		String imageUrl = googleResultJSON.getJSONObject("responseData").getJSONArray("results").getJSONObject(0).getString("url");
		Bitmap bitmap = UrlUtils.getBitmapFromURL(imageUrl);
		return bitmap;
	}

	public static void connectionErrorToast(Context context) {
		Toast.makeText(context, "Couldn't connect to the server.\nTry again later", Toast.LENGTH_LONG).show();
	}

	private static InputStream openStream(String src) throws MalformedURLException, IOException {
		URL url = new URL(src);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoInput(true);
		connection.connect();
		InputStream input = connection.getInputStream();
		return input;
	}
}