package com.przemo.RegulatorPID_1;

import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

import com.przemo.RegulatorPID_1.controllers.MainController;


import java.io.InputStream;
import java.io.OutputStream;


public class MyThread implements Runnable
{
	//is thread finished his job?
	private boolean finish = false;
	
	public void run() 
	{		
		//create the service url
		
		
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
					PID.u_list.add(PID.u);
					os.write(PID.u);
					os.close();
					//arduino bluetooth send information in byte shape
					//so we have to create vessel main vessel for information , its "supportVessel"
					//its 1024 bytes , its overneed , so we create mainVessele witch contains only
					// inforation bytes , so we create mainVessel with dynamic lenght
					byte[] supportVessel = new byte[1024];	
					Thread.sleep(1000);
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
					String u_akt = new String(mainVessel);
					PID.u_aktualne = Integer.parseInt(u_akt.trim());
					System.out.println("u aktulne z arduino " + PID.u_aktualne);
					System.out.println("u obliczone " + PID.u);
					System.out.println("ud  " + PID.ud);
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
