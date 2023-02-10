package com.mainfolder.romannumbersconverter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RomanConverterApp extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(com.mainfolder.romannumbersconverter.RomanConverterApp.class.getResource("roman-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 343, 171);
    stage.setTitle("Roman to Arab converter");
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}