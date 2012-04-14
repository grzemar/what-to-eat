package ynd.whattoeat;

import ynd.whattoeat.location.LocationHelper;
import ynd.whattoeat.location.LocationUnknownException;
import ynd.whattoeat.weather.WeatherHelper;
import ynd.whattoeat.weather.WeatherUnavailableException;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdView;

public class WhatToEatActivity extends CommonActivity implements AdListener {
	private Button infoButton;
	private Button whatToEatButton;
	private Button menuButton;
	private Button exitButton;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		loadAd();
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
				MenuDialog menuDialog = new MenuDialog(WhatToEatActivity.this);
				menuDialog.show();
			}
		});

		infoButton.setOnClickListener(new OnClickListener() {

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

		exitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WhatToEatActivity.this.finish();
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

	@Override
	public void loadControls() {
		whatToEatButton = (Button) findViewById(R.id.whatToEatButton);
		infoButton = (Button) findViewById(R.id.infoButton);
		menuButton = (Button) findViewById(R.id.menuButton);
		exitButton = (Button) findViewById(R.id.exitButton);
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
	protected int getLayoutId() {
		return R.layout.main;
	}

}