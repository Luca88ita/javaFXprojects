package com.mainfolder.romannumbersconverter;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RomanConverterController {

  @FXML
  private ResourceBundle resources;

  @FXML
  private URL location;

  @FXML
  private TextField arabNumbertf;

  @FXML
  private TextField romanNumbertf;

  public void initialize(){
    arabNumbertf.setText(null);
    romanNumbertf.setText(null);
  }
  @FXML
  void onClickClose(ActionEvent event) {
    System.exit(0);
  }

  @FXML
  void onClickReset(ActionEvent event) {
    arabNumbertf.setText(null);
    romanNumbertf.setText(null);
  }

  @FXML
  void onClickConvert(ActionEvent event) {
    if(arabNumbertf.getText()!=null && romanNumbertf.getText()==null){
      int temp = Integer.parseInt(arabNumbertf.getText());
      romanNumbertf.setText(returnRoman(temp));
    }else {
      if(romanNumbertf.getText()!=null && arabNumbertf.getText()==null){
        String temp = romanNumbertf.getText();
        arabNumbertf.setText(String.valueOf(fromRomanNumber(temp)));
      } else{
        arabNumbertf.setText(null);
        romanNumbertf.setText(null);
      }
    }
  }

  private String arabToRoman(int remains, int counter){
    if (remains == 1) {
      if (counter == 0) return "I";
      if (counter == 1) return "X";
      if (counter == 2) return "C";
      if (counter == 3) return "M";
    }
    if (remains == 2) {
      if (counter == 0) return "II";
      if (counter == 1) return "XX";
      if (counter == 2) return "CC";
      if (counter == 3) return "MM";
    }
    if (remains == 3) {
      if (counter == 0) return "III";
      if (counter == 1) return "XXX";
      if (counter == 2) return "CCC";
      if (counter == 3) return "MMM";
    }
    if (remains == 4) {
      if (counter == 0) return "IV";
      if (counter == 1) return "XL";
      if (counter == 2) return "CD";
    }
    if (remains == 5) {
      if (counter == 0) return "V";
      if (counter == 1) return "L";
      if (counter == 2) return "D";
    }
    if (remains == 6) {
      if (counter == 0) return "VI";
      if (counter == 1) return "LX";
      if (counter == 2) return "DC";
    }
    if (remains == 7) {
      if (counter == 0) return "VII";
      if (counter == 1) return "LXX";
      if (counter == 2) return "DCC";
    }
    if (remains == 8) {
      if (counter == 0) return "VIII";
      if (counter == 1) return "LXXX";
      if (counter == 2) return "DCCC";
    }
    if (remains == 9) {
      if (counter == 0) return "IX";
      if (counter == 1) return "XC";
      if (counter == 2) return "CM";
    }
    return "";
  }

  private String returnRoman(int numero){
    String romano = "";
    int control = numero;
    if (control>3999) return "Hai inserito un numero troppo alto; il valore massimo inseribile è 3999";
    if (control<=0) return "Inserisci un valore positivo tra 1 e 3999";
    for (int i = 0; control > 0; i++) {
      int temp = control%10;
      romano = arabToRoman(temp,i).concat(romano);
      control = control/10;
    }
    return romano;
  }

  public static int fromRomanNumber(String s) {
    int sum = romanToArab(s.charAt(s.length()-1));
    char c, c2;
    for (int i = 1; i < s.length(); i++) {
      c = s.charAt(s.length()-i-1);
      c2 = s.charAt(s.length()-i);
      int tmp1 = romanToArab(c);
      int tmp2 = romanToArab(c2);
      if (tmp1 < tmp2) {
        sum = sum - tmp1;
      }else{
        sum = sum + tmp1;
      }
    }
    return sum;
  }
  public static int romanToArab(char c) {
    if (c == 'I') {
      return 1;
    }
    if (c == 'V') {
      return 5;
    }
    if (c == 'X') {
      return 10;
    }
    if (c == 'L') {
      return 50;
    }
    if (c == 'C') {
      return 100;
    }
    if (c == 'D') {
      return 500;
    }
    if (c == 'M') {
      return 1000;
    }
    return 0;
  }


}
