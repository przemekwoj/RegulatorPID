package com.przemo.RegulatorPID_1;

import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.InputStream;
import java.io.OutputStream;


public class MyThread implements Runnable
{
	private int k;
	private boolean finish = false;
	public void run() 
	{		
		//create the service url
		
		String connectionString = Bluetooth.getServiceURL();
		try 
		{
			String hc05Url = Bluetooth.getUrl();
			StreamConnection streamConnection ;
				OutputStream os;
				InputStream is;
				while(!finish)
					{
					streamConnection = (StreamConnection)Connector.open(hc05Url);
					os = streamConnection.openOutputStream();
					is = streamConnection.openInputStream();
					//layout information about BT
					MainController.isActiveBT = true;
					//
					int x = 20;
					os.write(x);
					os.close();
					//arduino bluetooth send information in byte shape
					//so we have to create vessel main vessel for information , its "supportVessel"
					//its 1024 bytes , its overneed , so we create mainVessele witch contains only
					// inforation bytes , so we create mainVessel with dynamic lenght
					byte[] supportVessel = new byte[1024];	
					Thread.sleep(500);
					is.read(supportVessel);
					int dlugosc = 0;
					//checking lenght of important information
					for(int i=0;i<1024;i++)
					{
						if(supportVessel[i]!=0)
						{
							dlugosc++;
						}
						else
						{
							break;
						}
					}
					///taking olny important bytes
					byte[] mainVessel = new byte[dlugosc];	
					for(int i =0;i<dlugosc;i++)
					{
						mainVessel[i]=supportVessel[i];
					}
					is.close();
					streamConnection.close();
					System.out.println("received " + new String(mainVessel));
					}
				MainController.isActiveBT = false;

			

		} catch (IOException e) 
		{
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean isFinish() {
		return finish;
	}
	public void setFinish(boolean finish) {
		this.finish = finish;
	}

}
