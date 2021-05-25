package com.challenge.bittracker.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.challenge.bittracker.dto.PriceAPIResponseDTO;
import com.challenge.bittracker.dto.PriceInfoResponseDTO;

@Service
public interface PriceInfoService {
	
	public PriceAPIResponseDTO getCurrentCryptoCoinData(String cryptoType, String cryptoCurrency);

	public Optional<PriceInfoResponseDTO> getPriceInformation(String initialTimestamp, String finalTimestamp);

	public Double getPercentageDifference(Double valueOne, Double valueTwo);
}
