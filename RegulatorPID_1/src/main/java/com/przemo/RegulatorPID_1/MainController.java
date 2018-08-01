package com.przemo.RegulatorPID_1;

import java.io.IOException;
import java.util.Random;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainController 
{
	
	@FXML
	private TextField KpTextField;
	@FXML
	private TextField TiTextField;
	@FXML
	private TextField TdTextField;
	
	public void setPID(ActionEvent event) throws IOException
	{
	
		System.out.println("PID setted");
		LocalDevice device = LocalDevice.getLocalDevice();
		System.out.println("here1");
		RemoteDevice[] remotedevice = device.getDiscoveryAgent().retrieveDevices(DiscoveryAgent.PREKNOWN);
		System.out.println("here2");
		System.out.println(remotedevice.length);
		for(RemoteDevice d :remotedevice)
		{
			System.out.println("Device name :" + d.getFriendlyName(false));
			System.out.println("Bluetooth adress :"+d.getBluetoothAddress() + "/n");
			
		}
		System.out.println(KpTextField.getText());
		System.out.println(TiTextField.getText());
		System.out.println(TdTextField.getText());
		//myMessage.setText(Integer.toString(myRand));
	}
}