package ynd.whattoeat;

import ynd.whattoeat.tags.SizeType;
import ynd.whattoeat.tags.TasteType;
import ynd.whattoeat.tags.VegeType;

public class Dish {
	private String name;
	private String info;
	private TasteType taste;
	private SizeType size;
	private VegeType vege;

	public Dish(String name, TasteType taste, SizeType size, VegeType vege, String info) {
		this.name = name;
		this.info = info;
		this.taste = taste;
		this.size = size;
		this.vege = vege;
	}

	public String getName() {
		return name;
	}

	public String getInfo() {
		return info;
	}

	public TasteType getTaste() {
		return taste;
	}

	public SizeType getSize() {
		return size;
	}

	public VegeType getVege() {
		return vege;
	}
}
