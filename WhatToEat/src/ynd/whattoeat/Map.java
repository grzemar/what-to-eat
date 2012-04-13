package ynd.whattoeat;

import java.util.List;

import ynd.whattoeat.location.LocationHelper;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class Map extends MapActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

		MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.androidmarker);
		Marker itemizedoverlay = new Marker(drawable);

		mapOverlays.add(itemizedoverlay);
		Location bestKnownLocation = LocationHelper.getInstance(this).getBestKnownLocation();
		double lng = bestKnownLocation.getLongitude();
		double lat = bestKnownLocation.getLatitude();
		GeoPoint point = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));
		OverlayItem overlayitem = new OverlayItem(point, "Hola, Mundo!", "I'm in Mexico City!");

		itemizedoverlay.addOverlay(overlayitem);
		int a = 5;
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
