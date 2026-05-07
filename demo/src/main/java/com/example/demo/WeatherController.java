package com.example.demo;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.WeatherResponseDto;
import com.example.demo.service.WeatherService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:5173/")
@RequiredArgsConstructor
@RestController
public class WeatherController {

	private final WeatherService weatherService;
	
	@GetMapping("/api/weather")
	public WeatherResponseDto getWeather(@RequestParam String city) {
		return weatherService.getWeather(city);
	}
	
}
