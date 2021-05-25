package com.challenge.bittracker.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.challenge.bittracker.domain.Crypto;
import com.challenge.bittracker.dto.PriceInfoResponseDTO;
import com.challenge.bittracker.service.CryptoService;
import com.challenge.bittracker.service.PriceInfoService;

@RestController
@RequestMapping("/api/cryptos")
public class CryptoController {
	
	private final Logger log = LoggerFactory.getLogger(CryptoController.class);

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private PriceInfoService priceInfoService;

    @GetMapping
    public List<Crypto> findAll() {
    	log.debug("REST request to findAll : {}");
        return (List<Crypto>) cryptoService.findAll();
    }

    @GetMapping("/name/{cryptoName}")
    public Crypto findByName(@PathVariable String cryptoName) {
        return cryptoService.findByName(cryptoName).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }
    
    @GetMapping("/timestamp/{cryptoTimestamp}")
    public Crypto findByTimestamp(@PathVariable String cryptoTimestamp) {
    	log.debug("REST request to findByTimestamp : {}", cryptoTimestamp);
        return cryptoService.findByTimestamp(cryptoTimestamp).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No price information available for the given timestamp"));
    }

    @GetMapping("/{id}")
    public Crypto findOne(@PathVariable Long id) {
    	log.debug("REST request to findOne : {}", id);
        return cryptoService.findOne(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/priceinfo")
    public PriceInfoResponseDTO findAveragePriceBetweenTimestamps(@RequestParam String initialTimestamp, @RequestParam String finalTimestamp) {
    	log.debug("REST request to findfindAveragePriceBetweenTimestampsOne : {}");
        return priceInfoService.getPriceInformation(initialTimestamp, finalTimestamp).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No price information available for the given timestamp range"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Crypto create(@RequestBody Crypto crypto) {
    	log.debug("REST request to create : {}", crypto);
        return cryptoService.create(crypto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
    	log.debug("REST request to delete : {}", id);
    	cryptoService.delete(id);
    }

    @PutMapping("/{id}")
    public Crypto update(@RequestBody Crypto crypto, @PathVariable Long id) throws Exception {
    	log.debug("REST request to update : {}", id);
        return cryptoService.update(crypto, id);
    }
}
