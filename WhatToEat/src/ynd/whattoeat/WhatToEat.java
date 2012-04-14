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
		new Dish( "Cereals with milk",	TasteType.Sweet,	SizeType.SmallFood,	VegeType.Vegan,			"Description"), 
		new Dish( "Cookies",			TasteType.Sweet,	SizeType.Snack,		VegeType.Vegan,			"Description"), 
		new Dish( "Nuts",				TasteType.Salty,	SizeType.Snack,		VegeType.Vegan,			"Description"), 
		new Dish( "Pretzels ",			TasteType.Salty,	SizeType.Snack,		VegeType.Vegan,			"Description"), 
		new Dish( "Popcorn ",			TasteType.Salty,	SizeType.Snack,		VegeType.Vegan,			"Description"), 
		new Dish( "Chicken Wings",		TasteType.Mixed,	SizeType.SmallFood,	VegeType.WithMeat,		"Description"), 
		new Dish( "Hamburger",			TasteType.Mixed,	SizeType.BigMeal,	VegeType.WithMeat,		"Description"), 
		new Dish( "French Fries",		TasteType.Salty,	SizeType.SmallFood,	VegeType.Vegan,			"Description"), 
		new Dish( "Pancakes",			TasteType.Sweet,	SizeType.SmallFood,	VegeType.Vegan,			"Description"), 
		new Dish( "Scrambled Eggs",		TasteType.Salty,	SizeType.BigMeal,	VegeType.Vegetarian,	"Description"), 
		new Dish( "Potatoes",			TasteType.Salty,	SizeType.SmallFood,	VegeType.Vegan,			"Description"), 
		new Dish( "Salmon",				TasteType.Salty,	SizeType.BigMeal,	VegeType.Vegetarian,	"Description"),
		new Dish( "Soup",				TasteType.Mixed,	SizeType.BigMeal,	VegeType.All,			"Description"),
		new Dish( "Fruits",				TasteType.Sweet,	SizeType.Snack,		VegeType.Vegan,			"Description"), 
		new Dish( "Noodles",			TasteType.Mixed,	SizeType.BigMeal,	VegeType.All,			"Description"),
		new Dish( "Beef",				TasteType.Mixed,	SizeType.BigMeal,	VegeType.WithMeat,		"Description"),
		new Dish( "Steak",				TasteType.Mixed,	SizeType.BigMeal,	VegeType.WithMeat,		"Description"),
		new Dish( "Burrito",			TasteType.Mixed,	SizeType.BigMeal,	VegeType.WithMeat,		"Description"),
		new Dish( "Kebab",				TasteType.Mixed,	SizeType.BigMeal,	VegeType.WithMeat,		"Description"),
		new Dish( "Pasta",				TasteType.Mixed,	SizeType.BigMeal,	VegeType.Vegan,			"Description"),
		new Dish( "Yogurt",				TasteType.Sweet,	SizeType.SmallFood,	VegeType.Vegetarian,	"Description"),
		new Dish( "Grilled sandwich",	TasteType.Mixed,	SizeType.SmallFood,	VegeType.All,			"Description"),
		new Dish( "Cheese ",			TasteType.Mixed,	SizeType.SmallFood,	VegeType.Vegetarian,	"Description"),
		new Dish( "Sweetroll",			TasteType.Sweet,	SizeType.Snack,		VegeType.Vegetarian,	"Description"),
		new Dish( "Peanuts",			TasteType.Salty,	SizeType.Snack,		VegeType.Vegan,			"Description"),
		new Dish( "Biscuits",			TasteType.Sweet,	SizeType.Snack,		VegeType.Vegetarian,	"Description"),
		new Dish( "Sweet Corn",			TasteType.Mixed,	SizeType.SmallFood,	VegeType.Vegan,			"Description"),
		new Dish( "Rice",				TasteType.Salty,	SizeType.SmallFood,	VegeType.Vegan,			"Description"),
		new Dish( "Vegetables",			TasteType.Mixed,	SizeType.SmallFood,	VegeType.Vegan,			"Description"),
		new Dish( "Barbecue",			TasteType.Mixed,	SizeType.BigMeal,	VegeType.WithMeat,		"Description"),
		new Dish( "Casserole",			TasteType.Mixed,	SizeType.BigMeal,	VegeType.WithMeat,		"Description"),
		new Dish( "Nachos",				TasteType.Salty,	SizeType.Snack,		VegeType.Vegan,			"Description"),
		new Dish( "Donut",				TasteType.Sweet,	SizeType.BigMeal,	VegeType.Vegetarian,	"Description"),
		new Dish( "Strawberries",		TasteType.Sweet,	SizeType.Snack,		VegeType.Vegan,			"Description"),
		new Dish( "Jelly ",				TasteType.Sweet,	SizeType.SmallFood,	VegeType.Vegetarian,	"Description"),
		new Dish( "Fish and chips",		TasteType.Mixed,	SizeType.BigMeal,	VegeType.Vegetarian,		"Description"),
	};

	public static Dish whatToEat() {
		return dishes[new Random().nextInt(dishes.length)];
	}
}


















