package ynd.whattoeat;

import ynd.whattoeat.location.LocationEventsListener;
import ynd.whattoeat.location.LocationHelper;
import ynd.whattoeat.location.LocationUnknownException;
import ynd.whattoeat.utils.ImageLoadedCallback;
import ynd.whattoeat.utils.UrlUtils;
import ynd.whattoeat.weather.WeatherHelper;
import ynd.whattoeat.weather.WeatherUnavailableException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdView;

public class WhatToEatActivity extends Activity implements AdListener, LocationEventsListener {
	private ImageView foodImg;
	private TextView whatToEatTxt;
	private Button inputDataButton;
	private Button whatToEatButton;
	private Button mapButton;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		loadControls();
		loadAd();
		setClickListeners();

		startLocationHelper();
	}

	private void startLocationHelper() {
		LocationHelper locationHelper = LocationHelper.getInstance(this);
		locationHelper.setBestProviderCriteria(getBestLocationCriteria());
		locationHelper.addLocationEventsListeners(this);
	}

	private Criteria getBestLocationCriteria() {
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		return criteria;
	}

	private void setClickListeners() {
		whatToEatButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				whatToEat();
			}
		});

		mapButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), Map.class);
				startActivityForResult(myIntent, 0);
			}
		});

		inputDataButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog alertDialog;
				alertDialog = new AlertDialog.Builder(WhatToEatActivity.this).create();
				alertDialog.setTitle("Data used");
				alertDialog.setMessage(getUsedData());
				alertDialog.setCanceledOnTouchOutside(true);
				alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				alertDialog.show();
			}
		});
	}

	protected String getUsedData() {
		StringBuilder ret = new StringBuilder();
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
			ret.append("Location: " + LocationHelper.getInstance(this).getCurrentAddress().getLocality());
		} catch (LocationUnknownException e) {
			ret.append("Location not avaialble");
		}
		ret.append("\n");
	}

	private void whatToEat() {
		final ProgressDialog progressBar = ProgressDialog.show(this, "Loading...", "Determining best dish for you basing on:\n" + getUsedData());
		String whatToEat = WhatToEat.whatToEat();
		whatToEatTxt.setText(whatToEat);
		UrlUtils.getFirstGoogleImage(whatToEat, new ImageLoadedCallback() {

			@Override
			public void imageLoaded(Bitmap bitmap) {
				foodImg.setImageBitmap(bitmap);
				progressBar.cancel();
			}
		});
	}

	private void loadControls() {
		foodImg = (ImageView) findViewById(R.id.imageFood);
		whatToEatTxt = (TextView) findViewById(R.id.textWhatToEat);
		inputDataButton = (Button) findViewById(R.id.buttonInputData);
		whatToEatButton = (Button) findViewById(R.id.buttonWhatToEat);
		mapButton = (Button) findViewById(R.id.buttonMap);
	}

	@Override
	protected void onPause() {
		super.onPause();
		LocationHelper.getInstance(this).stopLocationUpdates();
	}

	@Override
	protected void onResume() {
		super.onResume();
		LocationHelper.getInstance(this).startLocationUpdates();
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
		try {
			WeatherHelper.getInstance().updateCachedWeather(LocationHelper.getInstance(this).getCurrentAddress());
		} catch (WeatherUnavailableException e) {
			e.printStackTrace();
		} catch (LocationUnknownException e) {
			e.printStackTrace();
		} finally {
			Toast.makeText(this, "New input data available, expect more accurate results!", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void noLocationProviderAvailable() {
		Toast.makeText(this, "Enable location provider for better results!", Toast.LENGTH_LONG).show();
	}
}