package com.przemo.RegulatorPID_1;
import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application
{
	
	
	public static void main(String[] args) throws IOException, InterruptedException
	{
	
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException 
	{		
		Parent root = FXMLLoader.load(getClass().getResource("layout/Main.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("layout/application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
}

