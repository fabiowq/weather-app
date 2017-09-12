String.prototype.capitalize = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
}

$(function() {
	$('#add-weather-btn').click(
		function() {
			var countryInput = $('#country');		
			var cityInput = $('#city');
			
			var country = countryInput.val().trim().toUpperCase();		
			var city = cityInput.val().trim().capitalize();
			if (country != '' && city != '') {
				var url = 'weekly/' + country + '/' + city;
				$.getJSON(
					url,
					function(data) { 												
						addWeather(data);
						countryInput.val('');
						cityInput.val('');
					}
				);			
			}
		}
	);
});

function addWeather(data) {
	var id = data.locationId;
	var html = '<li id="' + id + '">'
		+ '<h2>' + data.location.city + ', ' + data.location.country + '</h2>';
	
	data.entries.forEach(
		function(dateElment, dateIndex) {
			//console.log('%s, %o', dateIndex, dateElment);
			html += '<div class="date-container">'
				+  '<div class="date">' + dateElment.dayOfWeek + '</div>'
				+ '<table>'
			dateElment.entries.forEach(
				function(timeElement, timeIndex) {
					html += '<tr class="time">'	
						+ '<td>' + timeElement.hour + 'h</td>'
						+ '<td>'
						+		'<i class="owf owf-' + timeElement.code + ' owf-2x" style="opacity: 0.4" ></i>'
						+ '</td>'	
						+ '<td>'
							+ timeElement.celsiusTemperature 
							+ '<sup>°C</sup> / '
							+ timeElement.fahrenheitTemperature
							+ '<sup>°F</sup>'
						+ '</td>'
					+ '</tr>';
				}
			);
			html += '</table>'
				+ '</div>';			
		}
	);
		
	html += '<div style="clear: both;"></div>'
		+ '</li>';
	
	var weatherEl = $('#' + id);
	if (weatherEl && weatherEl.length > 0) {
		weatherEl.replaceWith(html);
	} else {
		$('#weather-conteiner').append(html);
	}
	
}