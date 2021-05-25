package com.challenge.bittracker.dto;

import org.springframework.stereotype.Component;

@Component
public class PriceInfoResponseDTO {

    private String initialTimestamp;

    private String finalTimestamp;

    private Double avgPriceBetweenTimestamps;

    private Double maxPrice;

    private Double percentageDifference;

	public String getInitialTimestamp() {
		return initialTimestamp;
	}

	public void setInitialTimestamp(String initialTimestamp) {
		this.initialTimestamp = initialTimestamp;
	}

	public String getFinalTimestamp() {
		return finalTimestamp;
	}

	public void setFinalTimestamp(String finalTimestamp) {
		this.finalTimestamp = finalTimestamp;
	}

	public Double getAvgPriceBetweenTimestamps() {
		return avgPriceBetweenTimestamps;
	}

	public void setAvgPriceBetweenTimestamps(Double avgPriceBetweenTimestamps) {
		this.avgPriceBetweenTimestamps = avgPriceBetweenTimestamps;
	}

	public Double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Double getPercentageDifference() {
		return percentageDifference;
	}

	public void setPercentageDifference(Double porcentualDifference) {
		this.percentageDifference = porcentualDifference;
	}
}
