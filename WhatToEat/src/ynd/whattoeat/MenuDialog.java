package ynd.whattoeat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuDialog extends Dialog implements android.view.View.OnClickListener {

	private Button aboutButton;
	private Button howToButton;
	private Button teachButton;

	public MenuDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

		setLayout();
		setClickListeners();
	}

	private void setLayout() {
		setTitle("Menu");
		setCanceledOnTouchOutside(true);
		loadControls();
	}

	private void setClickListeners() {
		aboutButton.setOnClickListener(this);
		howToButton.setOnClickListener(this);
		teachButton.setOnClickListener(this);
	}

	private void loadControls() {
		aboutButton = (Button) findViewById(R.id.menuDialogAboutButton);
		howToButton = (Button) findViewById(R.id.menuDialogHowToButton);
		teachButton = (Button) findViewById(R.id.menuDialogTeachButton);
	}

	private void teachUs() {
		TeachDialog teachDialog = new TeachDialog(getContext());
		teachDialog.show();
	}

	private void displayDialog(String title, String msg) {
		final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setTitle(title).setMessage(msg).create();
		alertDialog.setCanceledOnTouchOutside(true);
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				alertDialog.dismiss();
			}
		});
		alertDialog.show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menuDialogAboutButton:
			displayDialog("About", "blablabla");
			break;
		case R.id.menuDialogHowToButton:
			displayDialog("How to use", "blablabla");
			break;
		case R.id.menuDialogTeachButton:
			teachUs();
			dismiss();
			break;
		}

	}

}
