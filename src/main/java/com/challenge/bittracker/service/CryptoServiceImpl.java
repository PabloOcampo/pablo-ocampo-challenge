package com.challenge.bittracker.service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.bittracker.domain.Crypto;
import com.challenge.bittracker.repository.CryptoRepository;

@Service
public class CryptoServiceImpl implements CryptoService{
	
    @Autowired
    private CryptoRepository cryptoRepository;

    public List<Crypto> findAll() {
        return (List<Crypto>) cryptoRepository.findAll();
    }

    public Optional<Crypto> findByName(String cryptoName) {
        return cryptoRepository.findByName(cryptoName);
    }
    
    public Optional<Crypto> findByTimestamp(String cryptoTimestamp) {
        return cryptoRepository.findByTimestamp(cryptoTimestamp);
    }

    public Optional<Crypto> findOne(Long id) {
        return cryptoRepository.findById(id);
    }

    @Transactional
    public Crypto create(Crypto crypto) {
        return cryptoRepository.save(crypto);
    }

    @Transactional
    public void delete(Long id) {
    	cryptoRepository.findById(id);

    	cryptoRepository.deleteById(id);
    }

    @Transactional
    public Crypto update(Crypto crypto, Long id) throws Exception {
        if (crypto.getId() != id) {
          throw new Exception("The id number from the request url mistmach the one in request body");
        }
        cryptoRepository.findById(id);
        return cryptoRepository.save(crypto);
    }

	public OptionalDouble findAveragePriceBetweenTimestamps(String initialTimestamp, String finalTimestamp) {
		List<Crypto> resultList = cryptoRepository.findPricesBetweenTimestamps(initialTimestamp, finalTimestamp);

		return resultList.stream().mapToDouble(Crypto::getPrice).average();
	}

	public Double findMaxPrice() {
		return cryptoRepository.findMaxPrice();
	}
}
