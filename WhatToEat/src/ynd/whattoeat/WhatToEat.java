package ynd.whattoeat;

import java.util.Random;

import ynd.whattoeat.tags.SizeType;
import ynd.whattoeat.tags.TasteType;
import ynd.whattoeat.tags.VegeType;

public class WhatToEat {

	private static Dish[] dishes = new Dish[] { 
		new Dish( "Pizza",				TasteType.Mixed,	SizeType.BigMeal,	VegeType.All,			"Description"), 
		new Dish( "Ice creams",			TasteType.Sweet,	SizeType.Snack,		VegeType.Vegetarian,	"Description"), 
		new Dish( "Chocolate",			TasteType.Sweet,	SizeType.Snack,		VegeType.Vegetarian,	"Description"), 
		new Dish( "Apple Pie",			TasteType.Sweet,	SizeType.Snack,		VegeType.Vegan,			"Description"), 
		new Dish( "Spaghetti Bolonese",	TasteType.Salty,	SizeType.BigMeal,	VegeType.WithMeat,		"Description"), 
		new Dish( "Fruit Salad",		TasteType.Sweet,	SizeType.SmallFood,	VegeType.Vegan,			"Description"), 
		new Dish( "cereals with milk",	TasteType.Sweet,	SizeType.SmallFood,	VegeType.Vegan,			"Description"), 
		new Dish( "Cookie",				TasteType.Sweet,	SizeType.Snack,		VegeType.Vegan,			"Description"), 
		new Dish( "Nuts",				TasteType.Salty,	SizeType.Snack,		VegeType.Vegan,			"Description"), 
	};

	public static Dish whatToEat() {
		return dishes[new Random().nextInt(dishes.length)];
	}
}
