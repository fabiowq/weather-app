package com.example.weather.web;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

class WeatherSummary {

	private final LocalDate date;
	private final List<Weather> entries;

	public WeatherSummary(LocalDate date, List<Weather> entries) {
		super();
		this.date = date;
		this.entries = entries;
	}

	public LocalDate getDate() {
		return date;
	}
	
	public String getDayOfWeek() {
		return date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.getDefault());
	}

	public List<Weather> getEntries() {
		return entries;
	}
	
}
