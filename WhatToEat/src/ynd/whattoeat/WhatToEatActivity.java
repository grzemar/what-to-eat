package ynd.whattoeat;

import java.io.IOException;

import org.json.JSONException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdView;

public class WhatToEatActivity extends Activity implements AdListener, FoundBetterLocationListener {
	private ImageView imgFood;
	private TextView locationTxt;
	private TextView whatToEatTxt;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		loadControls();
		loadAd();
		setClickListeners();

		LocationHelper.init(this);
		LocationHelper.getInstance().addFoundBetterLocationListener(this);
	}

	private void setClickListeners() {
		Button b = (Button) findViewById(R.id.buttonWhatToEat);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				whatToEat();
			}
		});
	}

	private void whatToEat() {
		String whatToEat = WhatToEat.whatToEat();
		whatToEatTxt.setText(whatToEat);
		try {
			Bitmap bitmap = Utils.getFirstGoogleImage(whatToEat);
			imgFood.setImageBitmap(bitmap);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void loadControls() {
		imgFood = (ImageView) findViewById(R.id.imageFood);
		locationTxt = (TextView) findViewById(R.id.locationTextView);
		whatToEatTxt = (TextView) findViewById(R.id.textWhatToEat);
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

	@Override
	public void foundBetterLocation(Location newLocation) {
		Address currentAddress = LocationHelper.getInstance().getCurrentAddress();
		String locality = currentAddress == null ? "" : currentAddress.getLocality();
		locationTxt.setText(locality + " " + WeatherHelper.getCurrentWeatherInformation());
	}
}