package com.mainfolder.moneymanager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.TimeZone;

public class MoneyController {

  public static final String JDBC_Driver_MySQL = "com.mysql.cj.jdbc.Driver";
  public static final String JDBC_URL_MySQL = "jdbc:mysql://localhost:3306/jdbc_schema?user=luca88&password=root&serverTimezone=" + TimeZone.getDefault().getID();

  @FXML private DatePicker dpDate;
  @FXML private TableColumn<Expense, Double> tcAmount;
  @FXML private TableColumn<Expense, LocalDate> tcDate;
  @FXML private TableColumn<Expense, String> tcDescription;
  @FXML private TextField tfAmount;
  @FXML private TextField tfDescription;
  @FXML private TableView<Expense> tvExpenses;
  @FXML private Label lbTotal;
  ObservableList<Expense> expenses;
  private HikariDataSource dataSource;

  public void initialize(){
    setupTable();
    hikariSetup();
    fetchExpenses();
    updateTotalLabel();
  }
  private void hikariSetup() {
    HikariConfig config = new HikariConfig();
    config.setDriverClassName(JDBC_Driver_MySQL);
    config.setJdbcUrl(JDBC_URL_MySQL);
    config.setLeakDetectionThreshold(2000);
    dataSource = new HikariDataSource(config);
  }

  private void fetchExpenses() {
    try (Connection connection = dataSource.getConnection()) {
      expenses.clear();
      try (PreparedStatement getExpenses = connection.prepareStatement("SELECT * FROM expenses")) {
        try (ResultSet rs = getExpenses.executeQuery()) {
          while (rs.next()) {
            expenses.add(new Expense(
                    rs.getInt("idExpense"),
                    convertSQLDateToLocalDate(rs.getDate("Date")),
                    rs.getString("Description"),
                    rs.getDouble("Amount")));
          }
        }
      }
    } catch (SQLException e) {
      new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
    }
  }

  public LocalDate convertSQLDateToLocalDate(Date SQLDate) {
    java.util.Date date = new java.util.Date(SQLDate.getTime());
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }

  void updateTotalLabel(){
    double tempSUM = 0;
    try (Connection connection = dataSource.getConnection()){
      Statement stmt = connection.createStatement(
              ResultSet.TYPE_SCROLL_INSENSITIVE,
              ResultSet.CONCUR_UPDATABLE);
      ResultSet sumAmountrs = stmt.executeQuery("SELECT SUM(amount) FROM expenses");
      if (sumAmountrs.next()) {
        tempSUM = sumAmountrs.getFloat(1);
      }

      lbTotal.setText(String.format("%.2f",tempSUM));
    } catch (SQLException e) {
      new Alert(Alert.AlertType.ERROR, "SQL Error").showAndWait();
    }
  }

  @FXML
  void onAdd(ActionEvent event) {
    Expense expense = new Expense(
            dpDate.getValue(),
            tfDescription.getText(),
            Double.parseDouble(tfAmount.getText()));

    try (Connection connection = dataSource.getConnection();
         PreparedStatement insertExpense = connection.prepareStatement("INSERT INTO expenses (idExpense, date, description, " +
                 "amount) VALUES (?, ?, ?, ?)")) {
      insertExpense.setInt(1, expense.getIdExpense());
      insertExpense.setDate(2, Date.valueOf(expense.getDate()));
      insertExpense.setString(3, expense.getDescription());
      insertExpense.setDouble(4, expense.getAmount());
      insertExpense.executeUpdate();
      expenses.add(expense);
      dpDate.setValue(null);
      tfDescription.setText(null);
      tfAmount.setText(null);
      updateTotalLabel();
    } catch (SQLException e) {
      new Alert(Alert.AlertType.ERROR, "SQL Error").showAndWait();
    }
  }

  @FXML
  void onRemove(ActionEvent event) {
    //expenses.remove(tvExpenses.getSelectionModel().getSelectedItem());
    Expense selectedExpense = tvExpenses.getSelectionModel().getSelectedItem();
    if (selectedExpense == null) return;
    try (Connection connection = dataSource.getConnection();
         PreparedStatement deleteExpense = connection.prepareStatement("DELETE FROM expenses WHERE idExpense=?")) {
      deleteExpense.setInt(1, selectedExpense.getIdExpense());
      deleteExpense.executeUpdate();
      expenses.remove(selectedExpense);
      updateTotalLabel();
    } catch (IndexOutOfBoundsException | SQLException e) {
      new Alert(Alert.AlertType.ERROR, "SQL Error").showAndWait();
    }
  }

  void setupTable() {
    tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    tcDate.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
    tcDate.setOnEditCommit(e -> {
      try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement =
              connection.prepareStatement("UPDATE expenses SET date=? WHERE idExpense=?")) {
        preparedStatement.setDate(1, Date.valueOf(e.getNewValue()));
        preparedStatement.setInt(2, e.getRowValue().getIdExpense());
        preparedStatement.executeUpdate();
      } catch (SQLException ex) {
        ex.printStackTrace();
        new Alert(Alert.AlertType.ERROR, "Database Error").showAndWait();
      }
    });
    tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    tcDescription.setCellFactory(TextFieldTableCell.forTableColumn());
    tcDescription.setOnEditCommit(e -> {
      try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement =
              connection.prepareStatement("UPDATE expenses SET description=? WHERE idExpense=?")) {
        preparedStatement.setString(1, e.getNewValue());
        preparedStatement.setInt(2, e.getRowValue().getIdExpense());
        preparedStatement.executeUpdate();
      } catch (SQLException ex) {
        new Alert(Alert.AlertType.ERROR, "Database Error").showAndWait();
      }
    });
    tcAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    tcAmount.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
    tcAmount.setOnEditCommit(e -> {
      try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE expenses SET amount=? WHERE idExpense=?")) {
        preparedStatement.setDouble(1, e.getNewValue());
        preparedStatement.setInt(2, e.getRowValue().getIdExpense());
        preparedStatement.executeUpdate();
      } catch (SQLException ex) {
        new Alert(Alert.AlertType.ERROR, "Database Error").showAndWait();
      }
    });
    expenses = FXCollections.observableArrayList();
    tvExpenses.setItems(expenses);
    tvExpenses.setEditable(true);
  }

}
