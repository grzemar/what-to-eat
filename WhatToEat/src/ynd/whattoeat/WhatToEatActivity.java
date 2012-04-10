package ynd.whattoeat;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdView;

public class WhatToEatActivity extends Activity implements AdListener {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		loadAd();

		final ImageView img = (ImageView) findViewById(R.id.imageView1);
		final EditText txt = (EditText) findViewById(R.id.editText1);
		final TextView locationTxt = (TextView) findViewById(R.id.locationTextView);

		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					String googleResult = Utils.getFromURL("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + txt.getText().toString());
					JSONObject googleResultJSON = new JSONObject(googleResult);
					String imageUrl = googleResultJSON.getJSONObject("responseData").getJSONArray("results").getJSONObject(0).getString("tbUrl");
					Bitmap bitmap = Utils.getBitmapFromURL(imageUrl);
					img.setImageBitmap(bitmap);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

		LocationHelper.init(this);
		LocationHelper.getInstance().addFoundBetterLocationListener(new FoundBetterLocationListener() {

			@Override
			public void foundBetterLocation(Location newLocation) {
				Address currentAddress = LocationHelper.getInstance().getCurrentAddress();
				if(currentAddress == null)
				{
					String locality = "NIE MA ADRESU!!!!111!";
				}
				else
				{
					String locality = currentAddress.getLocality();
					locationTxt.setText(locality + " " + WeatherHelper.getCurrentWeatherInformation());
				}
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		LocationHelper.getInstance().stopLocationUpdates();
	}

	@Override
	protected void onResume() {
		super.onResume();
		LocationHelper.getInstance().startLocationUpdates();
	}

	private void loadAd() {
		AdView adView = (AdView) this.findViewById(R.id.adView);
		AdRequest request = new AdRequest();

		request.addTestDevice(AdRequest.TEST_EMULATOR);

		adView.loadAd(request);
		adView.setAdListener(this);
	}

	@Override
	public void onDismissScreen(Ad arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
		// TODO Auto-generated method stub
		System.out.println("onFailedToReceiveAd");
	}

	@Override
	public void onLeaveApplication(Ad arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPresentScreen(Ad arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReceiveAd(Ad arg0) {
		View progressBar = this.findViewById(R.id.adProgressBar);
		progressBar.setVisibility(View.GONE);

		View ad = this.findViewById(R.id.adView);
		ad.setVisibility(View.VISIBLE);

	}
}