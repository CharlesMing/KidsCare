package com.lanbiao.youxiaoyunfamily.loadimg;

public class FoodImageAndText {
	private String foodUrl;
	private String name;
	private String details;

	public FoodImageAndText(String foodUrl) {
		super();
		this.foodUrl = foodUrl;
	}

	public FoodImageAndText(String foodUrl, String name) {
		super();
		this.foodUrl = foodUrl;
		this.name = name;
	}

	public FoodImageAndText(String foodUrl, String name, String details) {
		super();
		this.foodUrl = foodUrl;
		this.name = name;
		this.details = details;
	}

	public String getFoodUrl() {
		return foodUrl;
	}

	public void setFoodUrl(String foodUrl) {
		this.foodUrl = foodUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}