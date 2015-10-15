package com.hkpking.life.autodiary.sqlite;

import java.io.*;

public class LocationData implements Comparable<LocationData>, Serializable {

	Double latitude;
	Double longitude;
	int count;

	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int compareTo(LocationData another) {
		
		return another.getCount() - this.getCount();
	}
	
	
	
}
