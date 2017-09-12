package com.example.weather.web;

import java.time.LocalTime;

class Weather {

	private final int code;
	private final String icon;
	private final double temperature;
	private final LocalTime time;

	public Weather(int code, String icon, double temperature, LocalTime time) {
		super();
		this.code = code;
		this.icon = icon;
		this.temperature = temperature;
		this.time = time;
	}

	public int getCode() {
		return code;
	}
	
	public LocalTime getTime() {
		return time;
	}

	public String getIcon() {
		return icon;
	}

	public String getFahrenheitTemperature() {
		double fahrenheitTemp = (this.temperature * 1.8) - 459.67;
		return String.format("%4.2f", fahrenheitTemp);
	}

	public String getCelsiusTemperature() {
		double celsiusTemp = this.temperature - 273.15;
		return String.format("%4.2f", celsiusTemp);
	}

}
