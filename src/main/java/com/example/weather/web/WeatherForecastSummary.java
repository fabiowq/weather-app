package com.example.weather.web;

import java.util.List;

public class WeatherForecastSummary {

	private final Location location;
	private final List<WeatherSummary> entries;

	public WeatherForecastSummary(Location location, List<WeatherSummary> entries) {
		super();
		this.location = location;
		this.entries = entries;
	}

	public Location getLocation() {
		return location;
	}
	
	public String getLocationId() {
		return location.getCountry().concat("_").concat(location.getCity()).toUpperCase().replaceAll("\\s", "_");
	}

	public List<WeatherSummary> getEntries() {
		return entries;
	}

}
