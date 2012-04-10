package ynd.whattoeat;

import java.util.Random;

public class WhatToEat {
	private static String[] dishes = new String[] { "Pizza", "Ice creams" };

	public static String whatToEat() {
		return dishes[new Random().nextInt(dishes.length)];
	}
}
