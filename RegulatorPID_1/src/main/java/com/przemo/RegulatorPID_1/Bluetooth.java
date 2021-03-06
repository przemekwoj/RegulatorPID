package com.przemo.RegulatorPID_1;

import javax.bluetooth.UUID;

public class Bluetooth 
{
	static private int btChannel = 1;
	static private String hc05url = "201503027142";
	static private String url = "btspp://" + hc05url + ":"+btChannel+";authenticate=false;encrypt=false;master=false"; //Replace this with your bluetooth URL
	static private UUID uuid = new UUID(0x1101); //create a UUID for SPP
	static private String serviceURL = "btspp://localhost:"+uuid+";name=SampleSPPServer";
	public static String getHc05url() {
		return hc05url;
	}
	public static void setHc05url(String hc05url) {
		Bluetooth.hc05url = hc05url;
	}
	public static UUID getUuid() {
		return uuid;
	}
	public static void setUuid(UUID uuid) {
		Bluetooth.uuid = uuid;
	}
	public static String getServiceURL() {
		return serviceURL;
	}
	public static void setServiceURL(String serviceURL) {
		Bluetooth.serviceURL = serviceURL;
	}
	public static String getUrl() {
		return url;
	}
	public static void setUrl(String url,int btChannel) {
		Bluetooth.url = "btspp://" + url + ":"+btChannel+";authenticate=false;encrypt=false;master=false";;
	}
	public static int getBtChannel() {
		return btChannel;
	}
	public static void setBtChannel(int btChannel) {
		Bluetooth.btChannel = btChannel;
	}
	
}
