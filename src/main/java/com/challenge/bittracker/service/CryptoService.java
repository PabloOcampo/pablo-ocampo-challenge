package com.challenge.bittracker.service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

import org.springframework.stereotype.Service;

import com.challenge.bittracker.domain.Crypto;

@Service
public interface CryptoService {

		public List<Crypto> findAll();

	    public Optional<Crypto> findByName(String cryptoName);
	    
	    public Optional<Crypto> findByTimestamp(String cryptoTimestamp);

	    public Optional<Crypto> findOne(Long id);

	    public Crypto create(Crypto crypto);

	    public void delete(Long id);

	    public Crypto update(Crypto crypto, Long id) throws Exception;

		public OptionalDouble findAveragePriceBetweenTimestamps(String initialTimestamp, String finalTimestamp);

		public Double findMaxPrice();
}
