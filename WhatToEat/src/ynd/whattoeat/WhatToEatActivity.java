package ynd.whattoeat;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class WhatToEatActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

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
		
		
		Button b2  = (Button) findViewById(R.id.button2);
		b2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				 Intent myIntent = new Intent(arg0.getContext(), Map.class);
                 startActivityForResult(myIntent, 0);
			}
			
		});
		LocationHelper.init(this);
		LocationHelper.getInstance().addFoundBetterLocationListener(new FoundBetterLocationListener() {

			@Override
			public void foundBetterLocation(Location newLocation) {
				
				locationTxt.setText(LocationHelper.getInstance().getCurrentAddress().toString());
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
}