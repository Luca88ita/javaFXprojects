package com.mainfolder.schoolbicocchi.dialogs;

import com.mainfolder.schoolbicocchi.domain.SchoolClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

import java.io.IOException;

public class NewClassDialog extends Dialog<SchoolClass> {

  @FXML private TextField tfCoordinator;
  @FXML private TextField tfName;
  @FXML private TextField tfRoom;

  public NewClassDialog() throws IOException {
    super();
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("newclass-view.fxml"));
    loader.setController(this);
    DialogPane pane = loader.load();
    setDialogPane(pane);
    setTitle("New Class");
    //setHeaderText("Look, a Custom Login Dialog");
    setResizable(false);
    initModality(Modality.APPLICATION_MODAL);
    setResultConverter(buttonType -> {
      if (buttonType == ButtonType.OK) {
        return new SchoolClass(
                0,
                tfName.getText(),
                tfRoom.getText(),
                tfCoordinator.getText());
      }
      return null;
    });
  }
}
