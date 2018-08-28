package com.przemo.RegulatorPID_1.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;

import com.przemo.RegulatorPID_1.Bluetooth;
import com.przemo.RegulatorPID_1.MyThread;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
		//getting all BT pairs devices
		LocalDevice device = LocalDevice.getLocalDevice();
		RemoteDevice[] remotedevice = device.getDiscoveryAgent().retrieveDevices(DiscoveryAgent.PREKNOWN);
		//we have to clear it, cos we dont want do append devices to prevoius ones
		connectBTbutton.getItems().clear();
			int menuItemId = 0;
			for(RemoteDevice d : remotedevice)
			{
				//we are adding menuitem to SplitMenuButton
				connectBTbutton.getItems().add(new MenuItem(d.getFriendlyName(false)));
				// we are setting function of this menuitem
				connectBTbutton.getItems().get(menuItemId).setOnAction
				(e ->{
					MenuItem menuItem = (MenuItem) e.getSource();
					try {
						setBTadress(menuItem,remotedevice);
					} catch (InterruptedException | IOException e1) {
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
		//we are getting particular device
		for(RemoteDevice d : remotedevice)		
		{
			if(d.getFriendlyName(false)== nameBT)
			{
				url = d.getBluetoothAddress();
				break;
			}
		}
		Bluetooth.setUrl(url,Bluetooth.getBtChannel());
		// we want only one BT connection , se we are checking if we have existing connection
		//we are finishing previous thread with BT and start new one
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
		
		Thread.sleep(1500);
		Bluetooth.setUrl(url,Bluetooth.getBtChannel());
		if(isActiveBT == true)
		{
			BTactivationCircle.setFill(new Color(0,1,0,1));
			connectionLabel.setText("You are connected to "+ nameBT);
			buttonNext.setVisible(true);
		}
		isActiveBT = false;
	}

	
	public void nextPage() throws IOException
	{
		URL url = new File("src/main/java/com/przemo/RegulatorPID_1/layout/PID.fxml").toURL();
		Parent root = FXMLLoader.load(url);
		Scene scene2 = new Scene(root);
		String s = "src/main/java/com/przemo/RegulatorPID_1/layout/application.css.fxml";
		scene2.getStylesheets().add(s);
	    Stage stage = (Stage) buttonNext.getScene().getWindow();
		stage.setScene(scene2);
		stage.show();
	}

	
}