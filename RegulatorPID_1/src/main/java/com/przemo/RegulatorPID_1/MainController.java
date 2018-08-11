package com.przemo.RegulatorPID_1;

import java.awt.List;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Random;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class MainController 
{
	@FXML
	private SplitMenuButton connectBTbutton;
	@FXML
	private  Text connectionLabel;
	@FXML
	private  Circle BTactivationCircle;
	@FXML
	private Button buttonNext;
	@FXML
	private ImageView imageNext;
	public static boolean isActiveBT = false;
	
	ArrayList<Thread> threadlist = new ArrayList<Thread>();
	ArrayList<MyThread> mythreadlist = new ArrayList<MyThread>();

	
	public void setBT() throws IOException
	{
		LocalDevice device = LocalDevice.getLocalDevice();
		RemoteDevice[] remotedevice = device.getDiscoveryAgent().retrieveDevices(DiscoveryAgent.PREKNOWN);
		connectBTbutton.getItems().clear();
			int menuItemId = 0;
			for(RemoteDevice d : remotedevice)
			{
				connectBTbutton.getItems().add(new MenuItem(d.getFriendlyName(false)));
				connectBTbutton.getItems().get(menuItemId).setOnAction(e -> {
																			MenuItem menuItem = (MenuItem) e.getSource();
																			try {
																				setBTadress(menuItem,remotedevice);
																			} catch (InterruptedException | IOException e1) {
																				// TODO Auto-generated catch block
																				e1.printStackTrace();
																			}
																			  });
				menuItemId++;
			}
			connectBTbutton.getTypeSelector().length();
	}
	
	public void setBTadress(MenuItem menuItem, RemoteDevice[] remotedevice) throws InterruptedException, IOException
	{
		buttonNext.setVisible(false);
		BTactivationCircle.setFill(new Color(1,0,0,1));
		connectionLabel.setText("You are not connected");
		String nameBT = menuItem.getText();
		String url = "";
		for(RemoteDevice d : remotedevice)		
		{
			if(d.getFriendlyName(false)== nameBT)
			{
				url = d.getBluetoothAddress();
				break;
			}
		}
		Bluetooth.setUrl(url,Bluetooth.getBtChannel());
		
		if(threadlist.size() == 1)
		{
			mythreadlist.get(0).setFinish(true);
			mythreadlist.remove(0);
			threadlist.remove(0);
			Bluetooth.setUrl(url, Bluetooth.getBtChannel());
			Thread.sleep(1000);
		}
		mythreadlist.add(new MyThread());
		Thread watek = new Thread(mythreadlist.get(0));
		threadlist.add(watek);
		threadlist.get(0).start();
		
		/*Thread watek = new Thread(new MyThread());
		threadlist.add(watek);
		threadlist.get(0).start();*/
		Thread.sleep(1500);
		Bluetooth.setUrl(url,Bluetooth.getBtChannel());
		if(isActiveBT == true)
		{
			BTactivationCircle.setFill(new Color(0,1,0,1));
			connectionLabel.setText("You are connected to "+ nameBT);
			buttonNext.setVisible(true);
			//imageNext.setVisible(true);
		}
		isActiveBT = false;
	}


	
}