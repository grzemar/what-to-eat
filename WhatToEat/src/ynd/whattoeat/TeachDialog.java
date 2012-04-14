package ynd.whattoeat;

import ynd.whattoeat.utils.ContentLoaderCallback;
import ynd.whattoeat.utils.UrlUtils;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class TeachDialog extends Dialog implements OnClickListener {

	private ImageView foodImg1;
	private ImageView foodImg2;
	private Button leftButton;
	private Button rightButton;
	private Button skipButton;
	private Dish dish1;
	private Dish dish2;

	public TeachDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teach);

		getDishes();
		setLayout();
		setClickListeners();
	}

	private void setLayout() {
		setTitle("What do you prefer?");
		setCanceledOnTouchOutside(true);
		loadControls();
		setButtonTexts();
		loadImages(dish1, dish2);
	}

	private void setButtonTexts() {
		leftButton.setText(dish1.getName());
		rightButton.setText(dish2.getName());
	}

	private void getDishes() {
		dish1 = WhatToEat.whatToEat();
		while (true) {
			dish2 = WhatToEat.whatToEat();
			if (dish1 != dish2)
				break;
		}
	}

	private void setClickListeners() {
		skipButton.setOnClickListener(this);
		leftButton.setOnClickListener(this);
		rightButton.setOnClickListener(this);
	}

	private void loadControls() {
		foodImg1 = (ImageView) findViewById(R.id.imageFood1);
		foodImg2 = (ImageView) findViewById(R.id.imageFood2);
		leftButton = (Button) findViewById(R.id.teachDialogLeftButton);
		rightButton = (Button) findViewById(R.id.teachDialogRightButton);
		skipButton = (Button) findViewById(R.id.teachDialogSkipButton);
	}

	private void loadImages(Dish whatToEat1, Dish whatToEat2) {
		UrlUtils.getFirstGoogleImage(whatToEat1.getName(), true, new ContentLoaderCallback<Bitmap>() {

			@Override
			public void contentLoaded(Bitmap result) {
				foodImg1.setImageBitmap(result);
			}

			@Override
			public void contentLoadingException(Exception e) {
			}
		});

		UrlUtils.getFirstGoogleImage(whatToEat2.getName(), true, new ContentLoaderCallback<Bitmap>() {

			@Override
			public void contentLoaded(Bitmap result) {
				foodImg2.setImageBitmap(result);
			}

			@Override
			public void contentLoadingException(Exception e) {
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.teachDialogLeftButton:
			Toast.makeText(getContext(), "User likes " + dish1.getName(), Toast.LENGTH_LONG).show();
			break;
		case R.id.teachDialogRightButton:
			Toast.makeText(getContext(), "User likes " + dish2.getName(), Toast.LENGTH_LONG).show();
			break;
		}
		dismiss();
	}
}
