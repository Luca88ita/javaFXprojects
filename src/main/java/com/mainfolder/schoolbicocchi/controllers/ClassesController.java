package com.mainfolder.schoolbicocchi.controllers;

import com.mainfolder.moneymanager.Expense;
import com.mainfolder.schoolbicocchi.dialogs.NewClassDialog;
import com.mainfolder.schoolbicocchi.domain.SchoolClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ClassesController extends DataSourceController {
  @FXML private TableView<SchoolClass> tbClasses;
  @FXML private TableColumn<SchoolClass, String> tcName;
  @FXML private TableColumn<SchoolClass, String> tcRoom;
  @FXML private TableColumn<SchoolClass, String> tcTeacher;
  ObservableList<SchoolClass> classes;

  @FXML
  void onInsert() throws IOException {
    NewClassDialog newClassDialog = new NewClassDialog();
    Optional<SchoolClass> result = newClassDialog.showAndWait();
    result.ifPresent(schoolClass -> {
      classes.add(schoolClass);
    });
  }

  @FXML
  void onRemove(ActionEvent event) {
    SchoolClass selectedClass = tbClasses.getSelectionModel().getSelectedItem();
    if (selectedClass == null) return;
    try (Connection connection = dataSource.getConnection();
         PreparedStatement deleteClass = connection.prepareStatement("DELETE FROM classes WHERE classID=?")) {
      deleteClass.setInt(1, selectedClass.getId());
      deleteClass.executeUpdate();
      classes.remove(selectedClass);
    } catch (IndexOutOfBoundsException | SQLException e) {
      new Alert(Alert.AlertType.ERROR, "SQL Error").showAndWait();
    }
  }

  public void initialize() {
    setupTable();
  }

  void setupTable() {
    tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
    tcName.setCellFactory(TextFieldTableCell.forTableColumn());

    tcName.setOnEditCommit(e -> {
      try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement =
              connection.prepareStatement("UPDATE classes SET name=? WHERE classID=?")) {
          preparedStatement.setString(1, e.getNewValue());
          preparedStatement.setInt(2, e.getRowValue().getId());
          preparedStatement.executeUpdate();
      } catch (SQLException ex) {
          ex.printStackTrace();
          new Alert(Alert.AlertType.ERROR, "Database Error").showAndWait();
      }
    });

    tcRoom.setCellValueFactory(new PropertyValueFactory<>("room"));
    tcRoom.setCellFactory(TextFieldTableCell.forTableColumn());

    tcRoom.setOnEditCommit(e -> {
            try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE classes SET room=? WHERE classID=?")) {
                preparedStatement.setString(1, e.getNewValue());
                preparedStatement.setInt(2, e.getRowValue().getId());
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                new Alert(Alert.AlertType.ERROR, "Database Error").showAndWait();
            }
        });


    tcTeacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
    tcTeacher.setCellFactory(TextFieldTableCell.forTableColumn());

        tcTeacher.setOnEditCommit(e -> {
            try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE classes SET mainTeacher=? WHERE classID=?")) {
                preparedStatement.setString(1, e.getNewValue());
                preparedStatement.setInt(2, e.getRowValue().getId());
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                new Alert(Alert.AlertType.ERROR, "Database Error").showAndWait();
            }
        });

    classes = FXCollections.observableArrayList();
    tbClasses.setItems(classes);
    tbClasses.setEditable(true);
  }

  public void fetchData() {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM classes");
         ResultSet rs = preparedStatement.executeQuery()) {
      classes.clear();
      while (rs.next()) {
        classes.add(new SchoolClass(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("mainTeacher"),
                rs.getString("room")));
      }
    } catch (SQLException e) {
      new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
    }
  }
}
