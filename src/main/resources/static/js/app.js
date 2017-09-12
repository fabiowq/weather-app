$(function() {
	$('#add-weather-btn').click(
		function() {
			var countryInput = $('#country');		
			var cityInput = $('#city');
			
			var country = countryInput.val().trim();		
			var city = cityInput.val().trim();
			if (country != '' && city != '') {
				var url = 'api/weather/weekly/' + country + '/' + city;
				$.getJSON(
					url,
					function(data) { 
						console.log(data);
						countryInput.val('');
						cityInput.val('');
					}
				);			
			}
		}
	);
});