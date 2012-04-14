package ynd.whattoeat;

import ynd.whattoeat.location.LocationEventsListener;
import ynd.whattoeat.location.LocationHelper;
import ynd.whattoeat.location.LocationUnknownException;
import ynd.whattoeat.utils.ContentLoaderCallback;
import ynd.whattoeat.utils.UrlUtils;
import ynd.whattoeat.weather.WeatherHelper;
import ynd.whattoeat.weather.WeatherUnavailableException;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdView;

public class WhatToEatActivity extends CommonActivity implements AdListener, LocationEventsListener {
	private Button inputDataButton;
	private Button whatToEatButton;
	private Button menuButton;
	private Button teachButton;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		loadAd();
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

	@Override
	public void setClickListeners() {
		whatToEatButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				whatToEat();
			}
		});

		menuButton.setOnClickListener(new OnClickListener() {

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

		teachButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				teachUs();
			}
		});

	}

	protected String getUsedData() {
		StringBuilder ret = new StringBuilder();
		ret.append("We will look for best food for you basing on:\n");
		ret.append(" - Your preferences by far\n");
		ret.append(" - ");
		appendLocationInfo(ret);
		ret.append(" - ");
		appendWeatherInfo(ret);
		return ret.toString();
	}

	private void appendWeatherInfo(StringBuilder ret) {
		try {
			ret.append("Weather: " + WeatherHelper.getInstance().getCachedWeather());
		} catch (WeatherUnavailableException e) {
			ret.append("Weather: Not available");
		}
		ret.append("\n");
	}

	private void appendLocationInfo(StringBuilder ret) {
		try {
			ret.append("Location: " + LocationHelper.getInstance(this).getCurrentAddress().getLocality());
		} catch (LocationUnknownException e) {
			ret.append("Location: Not avaialble");
		}
		ret.append("\n");
	}

	private void whatToEat() {
		Intent intent = new Intent(this, ResultActivity.class);
		startActivity(intent);
	}

	private void teachUs() {

		Dish whatToEat1 = WhatToEat.whatToEat();
		Dish whatToEat2;

		while(true)
		{
			whatToEat2 = WhatToEat.whatToEat();
			if (whatToEat1 != whatToEat2)
				break;
		}

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View dialogView = inflater.inflate(R.layout.teach, null);

		final ImageView foodImg1 = (ImageView) dialogView.findViewById(R.id.imageFood1);
		final ImageView foodImg2 = (ImageView) dialogView.findViewById(R.id.imageFood2);
		
		AlertDialog alertDialog;
		alertDialog = new AlertDialog.Builder(WhatToEatActivity.this).setTitle("What do you prefer?").setView(dialogView).create();

		alertDialog.setCanceledOnTouchOutside(true);

		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, whatToEat1.getName(), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Skip", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, whatToEat2.getName(), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		alertDialog.show();
		
		UrlUtils.getFirstGoogleImage(whatToEat1.getName(), new ContentLoaderCallback<Bitmap>() {

			@Override
			public void contentLoaded(Bitmap result) {
				foodImg1.setImageBitmap(result);
			}

			@Override
			public void contentLoadingException(Exception e) {}
		});
		
		UrlUtils.getFirstGoogleImage(whatToEat2.getName(), new ContentLoaderCallback<Bitmap>() {

			@Override
			public void contentLoaded(Bitmap result) {
				foodImg2.setImageBitmap(result);
			}

			@Override
			public void contentLoadingException(Exception e) {}
		});
		

	}

	@Override
	public void loadControls() {
		whatToEatButton = (Button) findViewById(R.id.whatToEatButton);
		inputDataButton = (Button) findViewById(R.id.inputDataButton);
		teachButton = (Button) findViewById(R.id.teachButton);
		menuButton = (Button) findViewById(R.id.menuButton);
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

	@Override
	protected int getLayoutId() {
		return R.layout.main;
	}

}