package com.przemo.RegulatorPID_1.controllers;

import com.przemo.RegulatorPID_1.PID;
import com.przemo.databaseclasses.Params;
import com.przemo.hibernate.service.ParamsService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SaveController 
{
	@FXML
	private Button ok_button;
	@FXML
	private TextField hintText;
	
	public void save()
	{
		if(!hintText.getText().equals(""))
		{
			ParamsService paramsService = new ParamsService();
			
			Params param = new Params();
			param.setKp(PID.kp);
			param.setTd(PID.td);
			param.setTi(PID.ti);
			param.setText(hintText.getText());
			
			paramsService.persist(param);
			
			 // get a handle to the stage
		    Stage stage = (Stage) ok_button.getScene().getWindow();
		    // do what you have to do
		    stage.close();
		}
	}

}
