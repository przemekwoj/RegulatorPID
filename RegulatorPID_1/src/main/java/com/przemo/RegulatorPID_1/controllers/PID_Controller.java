package com.przemo.RegulatorPID_1.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.bluetooth.RemoteDevice;
import com.przemo.RegulatorPID_1.PID_Thread;
import com.przemo.databaseclasses.Params;
import com.przemo.hibernate.service.ParamsService;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PID_Controller
{
 @FXML
 private Button backButton;
 @FXML
 private TextField kp_textfield;
 @FXML
 private TextField ti_textfield;
 @FXML
 private TextField td_textfield;
 @FXML
 private TextField ud_textfield;
 @FXML
 private Button button_arrow_up_kp;
 @FXML
 private Button button_arrow_up_ti;
 @FXML
 private Button button_arrow_up_td;
 @FXML
 private Button button_arrow_down_kp;
 @FXML
 private Button button_arrow_down_ti;
 @FXML
 private Button button_arrow_down_td;
 @FXML
 private Button button_set_value_pid;
 @FXML
 private Button save;
 @FXML
 private SplitMenuButton loadparamsbutton;
 
 public static int numberOfChart = 0;
 
 ArrayList<Thread> threadlist = new ArrayList<Thread>();
 ArrayList<PID_Thread> mythreadlist = new ArrayList<PID_Thread>();


 public void backToPreviousPage() throws IOException
 {
	 	URL url = new File("src/main/java/com/przemo/RegulatorPID_1/layout/Main.fxml").toURL();
	 	Parent root = FXMLLoader.load(url);
		Scene scene = new Scene(root);
		String s = "src/main/java/com/przemo/RegulatorPID_1/layout/application.css.fxml";
		scene.getStylesheets().add(s);
	    Stage stage = (Stage) backButton.getScene().getWindow();
		stage.setScene(scene);
		stage.show(); 
 }
 
 public void setPID() throws InterruptedException
 {
	double kp = Double.parseDouble(kp_textfield.getText().toString());
	double ti =	Double.parseDouble(ti_textfield.getText().toString());
	double td = Double.parseDouble(td_textfield.getText().toString());
	int ud = Integer.parseInt(ud_textfield.getText().toString());
	
	mythreadlist.add(new PID_Thread());
	if(threadlist.size() == 1)
	{
		mythreadlist.get(0).setFinished(true);
		mythreadlist.remove(0);
		threadlist.remove(0);
		Thread.sleep(1000);
	}
	//mythreadlist.get(0).setValue(kp, ti, td, ud);
	mythreadlist.get(0).setValue(kp, ti, td,ud);
	Thread watek = new Thread(mythreadlist.get(0));
	threadlist.add(watek);
	threadlist.get(0).start();
	showChart();
 }
 
 public void changePIDparams(ActionEvent e)
 {
	 // we have 7 button and one function for them
	 //to see which button was clieked we get his id name
	 String buttonName2 = ((Node) e.getSource()).getId();
	
	 if("button_arrow_up_kp".equalsIgnoreCase(buttonName2))
	 {
		 double val = (Double.parseDouble(kp_textfield.getText().toString()) + 0.1);
		 val = roundVal(val);
		 kp_textfield.setText(String.valueOf(val));
	 }
	 if("button_arrow_up_ti".equalsIgnoreCase(buttonName2))
	 {
		 double val = (Double.parseDouble(ti_textfield.getText().toString()) + 0.1);
		 val = roundVal(val);
		 ti_textfield.setText(String.valueOf(val));
	 }
	 if("button_arrow_up_td".equalsIgnoreCase(buttonName2))
	 {
		 double val = (Double.parseDouble(td_textfield.getText().toString()) + 0.1);
		 val = roundVal(val);
		 td_textfield.setText(String.valueOf(val));
	 }
	 if("button_arrow_down_kp".equalsIgnoreCase(buttonName2))
	 {
		 double val = (Double.parseDouble(kp_textfield.getText().toString()) - 0.1);
		 val = roundVal(val);
		 kp_textfield.setText(String.valueOf(val));
	 }
	 if("button_arrow_down_ti".equalsIgnoreCase(buttonName2))
	 {
		 double val = (Double.parseDouble(ti_textfield.getText().toString()) - 0.1);
		 val = roundVal(val);
		 ti_textfield.setText(String.valueOf(val));
	 }
	 if("button_arrow_down_td".equalsIgnoreCase(buttonName2))
	 {
		 double val = (Double.parseDouble(td_textfield.getText().toString())- 0.1);
		 val = roundVal(val);
		 td_textfield.setText(String.valueOf(val));
	 }
 }
 
 double roundVal(double val)
 {
	 val = val*100;
	 val = Math.round(val);
	 val = val/100;
	 return val;
 }
 
 public void showChart()
 {
	 numberOfChart++;
	 if(numberOfChart==1)
	 {
	     try {
	    	 //this allow to pot chart
	    	 ChartController.isFinish=false;
	    	 URL url = new File("src/main/java/com/przemo/RegulatorPID_1/layout/Chart.fxml").toURL();
	 		 Parent root = FXMLLoader.load(url);
	         Stage stage = new Stage();
	         stage.setTitle("PID");
	         stage.setScene(new Scene(root, 600, 600));
	         stage.show();
	         stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				
				@Override
				public void handle(WindowEvent event) {
					// this is finisht a chart thread
					ChartController.isFinish=true;
					numberOfChart = 0;
					
				}
			});
	     }
	     catch (IOException e) {
	         e.printStackTrace();
	     }
	 }
 }
 
 public void showSave() throws IOException
 {
	 URL url = new File("src/main/java/com/przemo/RegulatorPID_1/layout/Save.fxml").toURL();
	 Parent root = FXMLLoader.load(url);
     Stage stage = new Stage();
     stage.setTitle("SAVE");
     stage.setScene(new Scene(root));
     // focus only on one stage(window)
     stage.initModality(Modality.APPLICATION_MODAL);
     stage.show();
 }
 ///setting load pid parameters
 public void loadParams()
 {
	 loadparamsbutton.getItems().clear();
	 
	 ParamsService paramsService = new ParamsService();
	 List<Params> paramslist = paramsService.findAll();
	 int menuItemId = 0;
		for(Params p : paramslist)
		{
			//we are adding menuitem to SplitMenuButton
			loadparamsbutton.getItems().add(new MenuItem(p.getText()));
			// we are setting function of this menuitem
			loadparamsbutton.getItems().get(menuItemId).setOnAction
			(e ->{
				kp_textfield.setText(Double.toString(p.getKp()));
				ti_textfield.setText(Double.toString(p.getTi()));
				td_textfield.setText(Double.toString(p.getTd()));
				 });
			menuItemId++;
		}
	 
 }
}
