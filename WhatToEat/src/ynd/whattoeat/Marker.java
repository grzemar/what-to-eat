package ynd.whattoeat;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class Marker extends  ItemizedOverlay{

	public Marker(Drawable arg0) {
		  super(boundCenterBottom(arg0));
		// TODO Auto-generated constructor stub
	}

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		 return mOverlays.get(i);
	}

	@Override
	public int size() {
		 return mOverlays.size();
	}
}
