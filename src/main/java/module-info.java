open module com.mainfolder.javafxprojects {
  requires javafx.controls;
  requires javafx.fxml;

  requires org.controlsfx.controls;
  requires com.zaxxer.hikari;
  requires java.sql;

  /*opens com.mainfolder.javafxprojects to javafx.fxml;
  exports com.mainfolder.javafxprojects;
  opens com.mainfolder.romannumbersconverter to javafx.fxml;
  exports com.mainfolder.romannumbersconverter;
  opens com.mainfolder.moneymanager to javafx.fxml;
  exports com.mainfolder.moneymanager;*/
}