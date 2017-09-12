package com.example.weather.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.weather.WeatherAppProperties;
import com.example.weather.integration.ows.WeatherEntry;
import com.example.weather.integration.ows.WeatherForecast;
import com.example.weather.integration.ows.WeatherService;

@RestController
@RequestMapping("/")
public class WeatherSummaryController {

	private final WeatherService weatherService;

	private final WeatherAppProperties properties;

	public WeatherSummaryController(WeatherService weatherService, WeatherAppProperties properties) {
		this.weatherService = weatherService;
		this.properties = properties;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView home() {
		Map<String, Object> model = new LinkedHashMap<>();
		model.put("summary", getWeatherForecastSummaryList(getLocations()));
		return new ModelAndView("summary", model);
	}
	
	@RequestMapping("weekly/{country}/{city}")
	public WeatherForecastSummary summary(@PathVariable String country,
			@PathVariable String city) {
		return getWeatherForecastSummary(new Location(country, city));
	}
	
	private List<WeatherForecastSummary> getWeatherForecastSummaryList(List<Location> locations) {
		final List<WeatherForecastSummary> summary = new ArrayList<>();
		locations.forEach(
			location -> {
				final WeatherForecastSummary wfs = getWeatherForecastSummary(location); 
				summary.add(wfs);
			}
		);
		return summary;
	}

	private WeatherForecastSummary getWeatherForecastSummary(Location location) {
		WeatherForecast weatherForecast = 
				this.weatherService.getWeatherForecast(location.getCountry(), location.getCity());
		final List<WeatherSummary> ws = createWeatherSummary(weatherForecast);
		final WeatherForecastSummary wfs = createWeatherForecastSummary(location, ws);
		return wfs;
	}

	private List<Location> getLocations() {
		List<Location> locations = new ArrayList<>();
		this.properties.getLocations().forEach(
			s -> {
				String[] location = s.split("/");
				String country = location[0];
				String city = location[1];
				locations.add(new Location(country, city));	
			}
		);
		return locations;
	}
	
	private List<WeatherSummary> createWeatherSummary(WeatherForecast weatherForecast) {
		List<WeatherSummary> weatherSummaryList = new ArrayList<>(); 
		List<WeatherEntry> weatherEntryList = weatherForecast.getEntries();
		if (weatherEntryList != null) {
			Map<LocalDate, List<WeatherTime>> weatherSummaryMap = new LinkedHashMap<>();
			for (WeatherEntry weatherEntry: weatherEntryList) {				
				LocalDateTime localDateTime = LocalDateTime.ofInstant(
					weatherEntry.getTimestamp(), 
					ZoneId.systemDefault()
				);
				LocalDate date = localDateTime.toLocalDate();
				List<WeatherTime> weatherList = weatherSummaryMap.get(date);
				if (weatherList == null) {
					weatherList = new ArrayList<>();
					weatherSummaryMap.put(date, weatherList);
				}
				WeatherTime weather = new WeatherTime(
					weatherEntry.getWeatherId(),
					weatherEntry.getWeatherIcon(),
					weatherEntry.getTemperature(),
					localDateTime.toLocalTime()
				);
				weatherList.add(weather);
			}
			weatherSummaryMap.forEach(
				(k, v) -> {
					weatherSummaryList.add(new WeatherSummary(k, v));
				}
			);
		}
		return weatherSummaryList;
	}
	
	private WeatherForecastSummary createWeatherForecastSummary(Location location, List<WeatherSummary> entries) {
		return new WeatherForecastSummary(location, entries);
	}

}
