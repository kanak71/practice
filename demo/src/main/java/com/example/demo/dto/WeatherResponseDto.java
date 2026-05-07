package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WeatherResponseDto {

	
	private String city;
	private double temp;
	private String description;
	private String icon;
}
