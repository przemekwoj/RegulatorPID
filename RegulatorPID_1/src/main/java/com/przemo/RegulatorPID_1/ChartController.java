package com.przemo.RegulatorPID_1;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
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
        if(series_u.getData().size() == 50)
        {
        	series_u.getData().clear();
        	series_ud.getData().clear();

        }
        //add new data to series
        series_u.getData().add(new XYChart.Data<String,Number>(probkowanie,PID.u_aktualne));
        series_ud.getData().add(new XYChart.Data<String,Number>(probkowanie,PID.ud));
        // increase sample number
		tp++;
	}
	
	public void saveChart()
	{
		Stage stage = new Stage();
		stage.setTitle("Save Chart");
		FileChooser fileChooser = new FileChooser();
		//change initial Directory
		configureFileChooser(fileChooser);
		File file = fileChooser.showSaveDialog(stage);
	    WritableImage image = linechart.snapshot(new SnapshotParameters(), null);
	    if(file != null)
	    try {
	        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
	    } catch (IOException e) 
	    {
	    	System.out.println("error");
	    }
	    
	    
	}
	//changing initial directory
	public void configureFileChooser( FileChooser fileChooser)
	{                           
       fileChooser.setTitle("View Pictures");
       fileChooser.setInitialDirectory(new File("C:\\Users\\przemoemo\\Desktop"));

	}
	
	public class Chart_Thread implements Runnable
	{
			
		@Override
		public void run()
		{
			linechart.getData().addAll(series_u,series_ud);
			series_u.setName("u actual");
			series_ud.setName("ud");
			while(!isFinish)
			{
				try 
				{
					updateChart();
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
			}
		}

	}


}
