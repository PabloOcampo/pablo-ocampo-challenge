package com.challenge.bittracker.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.challenge.bittracker.domain.Crypto;
import com.challenge.bittracker.dto.CryptoDTO;
import com.challenge.bittracker.dto.PriceAPIResponseDTO;

@Service
public class CryptoMapper {

    public Crypto cryptoDTOToCrypto(CryptoDTO cryptoDTO) {
        if (cryptoDTO == null) {
            return null;
        } else {
        	Crypto crypto = new Crypto();
        	crypto.setName(cryptoDTO.getName());
        	crypto.setPrice(cryptoDTO.getPrice());
        	crypto.setCurrency(cryptoDTO.getCurrency());
        	crypto.setTimestamp(cryptoDTO.getTimestamp());

            return crypto;
        }
    }

    public CryptoDTO cryptoToCryptoDTO(Crypto crypto) {
        if (crypto == null) {
            return null;
        } else {
        	CryptoDTO cryptoDTO = new CryptoDTO();
        	cryptoDTO.setName(crypto.getName());
        	cryptoDTO.setPrice(crypto.getPrice());
        	cryptoDTO.setCurrency(crypto.getCurrency());
        	cryptoDTO.setTimestamp(crypto.getTimestamp());

            return cryptoDTO;
        }
    }

    public Crypto priceAPIResponseDTOToCrypto(PriceAPIResponseDTO priceAPIResponseDTO) {
        if (priceAPIResponseDTO == null) {
            return null;
        } else {
        	Crypto crypto = new Crypto();
        	crypto.setName(priceAPIResponseDTO.getCurr1());
        	crypto.setPrice(Double.parseDouble(priceAPIResponseDTO.getLprice()));
        	crypto.setCurrency(priceAPIResponseDTO.getCurr2());
        	crypto.setTimestamp((LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))));

            return crypto;
        }
    }
}
