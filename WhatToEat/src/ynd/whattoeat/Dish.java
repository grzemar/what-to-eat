package ynd.whattoeat;

import ynd.whattoeat.tags.SizeType;
import ynd.whattoeat.tags.TasteType;
import ynd.whattoeat.tags.VegeType;

public class Dish {
	
	public Dish(String name, TasteType taste, SizeType size, VegeType vege, String info) {
		this.name = name;
		this.info = info;
		this.taste = taste;
		this.size = size;
		this.vege = vege;
	}

	public String name;
	public String info;
	public TasteType taste;
	public SizeType size;
	public VegeType vege;

	
}
