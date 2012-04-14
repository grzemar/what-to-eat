package ynd.whattoeat;

import android.app.Activity;
import android.os.Bundle;

public abstract class CommonActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());
		loadControls();
		setClickListeners();
	}

	protected abstract int getLayoutId();

	protected abstract void setClickListeners();

	protected abstract void loadControls();

}