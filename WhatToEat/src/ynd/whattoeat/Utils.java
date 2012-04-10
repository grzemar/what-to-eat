package ynd.whattoeat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

public class Utils {
	public static String getFromURL(String src) throws IOException {
		InputStream input = openStream(src);
		BufferedReader in = new BufferedReader(new InputStreamReader(input));

		String all = "";
		String s;
		while ((s = in.readLine()) != null)
			all += s + "\n";

		return all;
	}

	public static Bitmap getBitmapFromURL(String src) {
		try {
			InputStream input = openStream(src);
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
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