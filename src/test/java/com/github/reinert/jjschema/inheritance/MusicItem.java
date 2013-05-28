package com.github.reinert.jjschema.inheritance;

import java.math.BigDecimal;

public class MusicItem extends BaseItem {

	private BigDecimal price;
	private String artistName;
	private String releaseYear;

	public String getArtistName() {
		return artistName;
	}
	
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	
	public String getReleaseYear() {
		return releaseYear;
	}
	
	public void setReleaseYear(String releaseYear) {
		this.releaseYear = releaseYear;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
