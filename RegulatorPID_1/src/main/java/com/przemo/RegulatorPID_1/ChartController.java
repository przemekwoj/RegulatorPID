package com.przemo.RegulatorPID_1;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.SwingUtilities;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ChartController implements Initializable
{
	// sample numer
	static public int tp = 0;
	static boolean isFinish = false;

	@FXML
	LineChart<String ,Number> linechart;
	
	// process data , it will show up on the chart
	XYChart.Series<String,Number> series_u = new XYChart.Series<String,Number>();
	XYChart.Series<String,Number> series_ud = new XYChart.Series<String,Number>();
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		
		//start showing realtime chart
		Chart_Thread chartThread = new Chart_Thread();
		Thread watek = new Thread(chartThread);
        watek.start();	
	}
	
	public void updateChart() throws InterruptedException
	{
        String probkowanie = Integer.toString(tp);
        //add new data to series
        series_u.getData().add(new XYChart.Data<String,Number>(probkowanie,PID.u_aktualne));
        series_ud.getData().add(new XYChart.Data<String,Number>(probkowanie,PID.ud));
        // increase sample number
		tp++;
	}
	
	public class Chart_Thread implements Runnable
	{
			
		@Override
		public void run()
		{
			linechart.getData().addAll(series_u,series_ud);
			while(!isFinish)
			{
				try 
				{
					updateChart();
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
			}
		}

	}


}
