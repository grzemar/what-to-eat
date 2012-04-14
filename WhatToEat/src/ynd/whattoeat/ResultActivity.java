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
		UrlUtils.getFirstGoogleImage(whatToEat.getName(), new ContentLoaderCallback<Bitmap>() {

			@Override
			public void contentLoaded(Bitmap result) {
				dishName.setText(whatToEat.getName());
				dishImage.setImageBitmap(result);
				progressBar.cancel();
			}

			@Override
			public void contentLoadingException(Exception e) {
				progressBar.cancel();
				Toast.makeText(ResultActivity.this, "Couldn't determine best dish for you, try again later.", Toast.LENGTH_LONG).show();
				ResultActivity.this.finish();
			}
		});
	}

	@Override
	protected int getLayoutId() {
		return R.layout.result;
	}
}
