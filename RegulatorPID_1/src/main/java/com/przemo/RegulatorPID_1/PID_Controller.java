package com.przemo.RegulatorPID_1;

import java.awt.Event;
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
 
 public static int numberOfChart = 0;
 
 ArrayList<Thread> threadlist = new ArrayList<Thread>();
 ArrayList<PID_Thread> mythreadlist = new ArrayList<PID_Thread>();


 public void backToPreviousPage() throws IOException
 {
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
	 		 Parent root = FXMLLoader.load(getClass().getResource("Chart.fxml"));
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
}
