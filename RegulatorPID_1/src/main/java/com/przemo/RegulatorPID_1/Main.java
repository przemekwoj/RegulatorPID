package com.przemo.RegulatorPID_1;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class Main extends Application
{
	
	public static void main(String[] args)
	{
		System.out.println("2");
		launch(args);
		System.out.println("3");
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException 
	{
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}