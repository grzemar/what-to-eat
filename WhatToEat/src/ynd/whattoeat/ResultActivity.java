package ynd.whattoeat;

import ynd.whattoeat.utils.ContentLoaderCallback;
import ynd.whattoeat.utils.UrlUtils;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends CommonActivity {
	protected TextView dishName;
	protected ImageView dishImage;
	protected Button backButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		whatToEat();
	}

	@Override
	protected void loadControls() {
		dishImage = (ImageView) findViewById(R.id.dishImage);
		dishName = (TextView) findViewById(R.id.dishName);
		backButton = (Button) findViewById(R.id.backButton);
	}

	@Override
	protected void setClickListeners() {
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ResultActivity.this.finish();
			}
		});
	}

	private void whatToEat() {
		final ProgressDialog progressBar = ProgressDialog.show(this, "Loading...", "Determining best dish for you basing on:\n" + UsedData.getInstance(this).getUsedData());
		final Dish whatToEat = WhatToEat.whatToEat();
		dishName.setText(whatToEat.getName());
		
		UrlUtils.getFirstGoogleImage(whatToEat.getName(), false, new ContentLoaderCallback<Bitmap>() {

			@Override
			public void contentLoaded(Bitmap result) {
				dishImage.setImageBitmap(result);
				progressBar.cancel();
			}

			@Override
			public void contentLoadingException(Exception e) {
				progressBar.cancel();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(ResultActivity.this, "Could not download image, sorry for problems", Toast.LENGTH_LONG).show();
					}
				});
			}
		});
	}

	@Override
	protected int getLayoutId() {
		return R.layout.result;
	}
}
