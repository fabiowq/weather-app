package com.example.weather.web;

import java.util.ArrayList;
import java.util.List;

public class WeatherForecastSummary {

	private Location location;
	private List<WeatherSummary> entries = new ArrayList<>();
	
	public WeatherForecastSummary() {
		super();
	}

	public WeatherForecastSummary(Location location, List<WeatherSummary> entries) {
		this();
		this.location = location;
		this.entries = entries;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<WeatherSummary> getEntries() {
		return entries;
	}

	public void setEntries(List<WeatherSummary> entries) {
		this.entries = entries;
	}

}
