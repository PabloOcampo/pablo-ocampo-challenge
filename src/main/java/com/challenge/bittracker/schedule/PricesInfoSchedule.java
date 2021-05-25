package com.challenge.bittracker.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.challenge.bittracker.dto.PriceAPIResponseDTO;
import com.challenge.bittracker.mapper.CryptoMapper;
import com.challenge.bittracker.service.CryptoService;
import com.challenge.bittracker.service.PriceInfoService;

@Service
public class PricesInfoSchedule {

	@Autowired
	CryptoService cryptoService;

	@Autowired
	PriceInfoService priceInfoService;

    @Autowired
    private CryptoMapper cryptoMapper;

    @Value("${crypto.price-api.bitcoin-alias}")
    private String cryptoType;

    @Value("${crypto.price-api.currency}")
    private String cryptoCurrency;

	@Scheduled(fixedDelay = 10000, initialDelay = 5000)
	private void getBitcoinPriceSchedule(){
		PriceAPIResponseDTO currentBTCData = priceInfoService.getCurrentCryptoCoinData(cryptoType, cryptoCurrency);
		cryptoService.create(cryptoMapper.priceAPIResponseDTOToCrypto(currentBTCData));
	}
}
