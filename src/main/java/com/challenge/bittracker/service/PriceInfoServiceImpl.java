package com.challenge.bittracker.service;

import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.math3.util.Precision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.challenge.bittracker.dto.PriceAPIResponseDTO;
import com.challenge.bittracker.dto.PriceInfoResponseDTO;

@Service
public class PriceInfoServiceImpl implements PriceInfoService{

	private final Logger log = LoggerFactory.getLogger(PriceInfoServiceImpl.class);

	@Autowired
    private CryptoService cryptoService;

    @Value("${crypto.price-api.host}")
    private String cryptoHost;

    @Value("${crypto.price-api.uri}")
    private String cryptoUri;

    @Value("${crypto.price-api.useragent}")
    private String cryptoUserAgent;
    
    @Value("${crypto.price-api.contentype}")
    private String cryptoContentType;

    @Value("${crypto.services.priceinfo.config.decimalplaces}")
    private Integer decimalPlaces;

	public PriceAPIResponseDTO getCurrentCryptoCoinData(String cryptoType, String cryptoCurrency){
		log.debug("Getting current bitcoin price information...");
		RestTemplate restTemplate = new RestTemplate();

		//Fix for unsupported mediatype
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		jsonConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "json")));
		restTemplate.getMessageConverters().add(jsonConverter);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", cryptoContentType);
		headers.add("user-agent", cryptoUserAgent);
		
		ResponseEntity<PriceAPIResponseDTO> response = restTemplate.exchange(cryptoHost + cryptoUri + "/" +cryptoType + "/" + cryptoCurrency, HttpMethod.GET, new HttpEntity<Object>(headers), PriceAPIResponseDTO.class);

		return response.getBody();
	}

	public Optional<PriceInfoResponseDTO> getPriceInformation(String initialTimestamp, String finalTimestamp) {
		log.debug("Generating price information...");
		try {
			PriceInfoResponseDTO priceInfoResponseDTO = new PriceInfoResponseDTO();
			
			
			Double avgPrice = cryptoService.findAveragePriceBetweenTimestamps(initialTimestamp, finalTimestamp).getAsDouble();
			Double maxPrice = cryptoService.findMaxPrice();

			priceInfoResponseDTO.setInitialTimestamp(initialTimestamp);
			priceInfoResponseDTO.setFinalTimestamp(finalTimestamp);
			priceInfoResponseDTO.setAvgPriceBetweenTimestamps(Precision.round(avgPrice, decimalPlaces));
			priceInfoResponseDTO.setMaxPrice(Precision.round(maxPrice, decimalPlaces));
			priceInfoResponseDTO.setPercentageDifference(Precision.round(getPercentageDifference(avgPrice, maxPrice), decimalPlaces));

			return Optional.of(priceInfoResponseDTO);
		} catch (Exception e) {
			log.debug(e.getMessage());
			return Optional.empty(); 
		}
	}

	public Double getPercentageDifference(Double valueOne, Double valueTwo) {
		log.debug("Calculating porcentual difference...");
		return ((valueTwo - valueOne) * 100 / valueOne);
	}
}
