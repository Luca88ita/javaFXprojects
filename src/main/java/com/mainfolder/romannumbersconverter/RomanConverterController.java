package com.mainfolder.romannumbersconverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RomanConverterController {

  @FXML private ResourceBundle resources;
  @FXML private URL location;
  @FXML private TextField arabNumbertf;
  @FXML private TextField romanNumbertf;
  @FXML private Button btconvert;

  static List<RomanNumber> romanList = new ArrayList<>();
  static String output = "";

  public void initialize(){
    arabNumbertf.setText(null);
    romanNumbertf.setText(null);
    output = "";
    basicRomanNumberListInitialization();
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
      Pattern pattern = Pattern.compile("[0-9]");
      Matcher matcher = pattern.matcher(arabNumbertf.getText());
      boolean matchFound = matcher.find();
      if (matchFound){
        int temp = Integer.parseInt(arabNumbertf.getText());
        romanNumbertf.setText(returnRoman(temp));
      }else romanNumbertf.setText("hai inserito dei caratteri non validi");
    }else {
      if(romanNumbertf.getText()!=null && arabNumbertf.getText()==null){
        String temp = romanNumbertf.getText();
        if(validityCheck2(temp)) arabNumbertf.setText(String.valueOf(returnArab(temp)));
        else arabNumbertf.setText("Formato numero romano non valido");
      } else{
        arabNumbertf.setText(null);
        romanNumbertf.setText(null);
      }
    }
  }


  private static void basicRomanNumberListInitialization(){
    if(romanList.size()==0){
      romanList.add(new RomanNumber(1,0,'I'));
      romanList.add(new RomanNumber(5,0,'V'));
      romanList.add(new RomanNumber(1,1,'X'));
      romanList.add(new RomanNumber(5,1,'L'));
      romanList.add(new RomanNumber(1,2,'C'));
      romanList.add(new RomanNumber(5,2,'D'));
      romanList.add(new RomanNumber(1,3,'M'));
    }
  }

  private static void getRoman(int n, int power){
    for (RomanNumber rn:romanList){
      if (rn.getPotenzaDieci() == power && rn.getValoreAssoluto() == n){
        output = rn.getLetteraRomana()+output;
      }
    }
  }
  private static String returnRoman(int numero){
    int control = numero;
    output = "";
    if (control>3999 || control<=0) {
      return "Inserisci un valore compreso tra 1 e 3999";
    }
    for (int i = 0; control > 0; i++) {
      int temp = control%10;
      switch (temp){
        case 1:{
          getRoman(1,i);
          break;
        }
        case 2:{
          for (int j = 0; j < 2; j++) {
            getRoman(1,i);
          }
          break;
        }
        case 3:{
          for (int j = 0; j < 3; j++) {
            getRoman(1,i);
          }
          break;
        }
        case 4:{
          getRoman(5,i);
          getRoman(1,i);
          break;
        }
        case 5:{
          getRoman(5,i);
          break;
        }
        case 6:{
          getRoman(1,i);
          getRoman(5,i);
          break;
        }
        case 7:{
          for (int j = 0; j < 2; j++) {
            getRoman(1,i);
          }
          getRoman(5,i);
          break;
        }
        case 8:{
          for (int j = 0; j < 3; j++) {
            getRoman(1,i);
          }
          getRoman(5,i);
          break;
        }
        case 9:{
          getRoman(1,i+1);
          getRoman(1,i);
          break;
        }
        default:
      }
      control = control/10;
    }
    return output;
  }

  public static boolean validityCheck2(String s){
    int test = returnArab(s);
    if (s.equals(returnRoman(test))) return true;
    return false;
  }

  public static int returnArab(String s){
    int sum = 0;
    char c, c2;
    for (RomanNumber rn:romanList){
      c = s.charAt(s.length()-1);
      if (c == rn.getLetteraRomana()) sum = (int) (rn.getValoreAssoluto()*(Math.pow(10,rn.getPotenzaDieci())));
    }
    for (int i = 1; i < s.length(); i++) {
      c = s.charAt(s.length()-i-1);
      c2 = s.charAt(s.length()-i);
      int tmp1=0;
      int tmp2=0;
      for (RomanNumber rn:romanList){
        if (c == rn.getLetteraRomana()) tmp1 = (int) (rn.getValoreAssoluto()*(Math.pow(10,rn.getPotenzaDieci())));
        if (c2 == rn.getLetteraRomana()) tmp2 = (int) (rn.getValoreAssoluto()*(Math.pow(10,rn.getPotenzaDieci())));
      }
      sum = (tmp1 < tmp2) ? sum - tmp1: sum + tmp1;
    }
    return sum;
  }

  /* funzione di check da utilizzare in caso si volesse fare la gestione
  degli errori specifica per la conversione da romano ad arabo*/

  public static boolean validityCheck(String s){
    char c1, c2;
    RomanNumber rnempty = new RomanNumber(0,0,' ');
    RomanNumber rn1 = rnempty;
    RomanNumber rn2 = rnempty;
    int counterPrevious1 = 0; // serve per verificare di non avere più di un valore di potenza inferiore precedente
    int counterNext3 = 0; // serve per verificare di non avere più di 3 valori 1 con la stessa potenza di fila
    int counterFives = 0; // serve per verificare di non avere 2 valori 5 con la stessa potenza di fila
    int counterPower = 0; // serve per verificare la potenza di un elemento rn1 pow(x) che abbia un solo elemento superiore di tipo
    int lastPowerindex = 3; // inizializzato a 3 evita falsi negativi (es MMCCCXXIII che è valido, segnato come falso)
    boolean check = false;
    /* qui controllo i numeri romani composti da una sola lettera */
    if (s.length()==1){
      c1 = s.charAt(0);
      for (RomanNumber rn:romanList){
        if (rn.getLetteraRomana() == c1) rn1 = rn;
      }
      return rn1.getLetteraRomana() != ' '? true: false;
    }
    /* qui sotto controllo i numeri romani composti da più lettere */
    /* comincio inizializzando i valori dei 2 elementi che andrò a confrontare */
    for (int i = 1; i < s.length(); i++) {
      check = false; // serve per evitare true in caso di lettere non romane in mezzo (es LXIF)
      c1 = s.charAt(i-1);
      c2 = s.charAt(i);
      rn1 = rnempty; // prevengo che mi rimangano valori residui
      rn2 = rnempty; // prevengo che mi rimangano valori residui
      for (RomanNumber rn:romanList){
        if (rn.getLetteraRomana() == c1) rn1 = rn;
        if (rn.getLetteraRomana() == c2) rn2 = rn;
      }
      /* controllo che entrambi i valori siano validi */
      if (rn1.getLetteraRomana() != ' ' && rn2.getLetteraRomana() != ' '){
        /* prevengo che possa darmi validi valori tipo IXC = 89 */
        if (rn1.getPotenzaDieci()<rn2.getPotenzaDieci() && rn2.getPotenzaDieci()>lastPowerindex) return false;
        /* qui controllo se la potenza del valore più a sinistra è maggiore a quella successiva:
         *  al contempo azzero alcune variabili contatore */
        if (rn1.getPotenzaDieci()>rn2.getPotenzaDieci()){
          counterPrevious1 = 0;
          counterNext3 = 0;
          counterFives = 0;
          check = true;
          /* prevengo che possa darmi validi valori tipo XCIXIII = 102 */
          if (counterPower > 0 && rn2.getPotenzaDieci()>=lastPowerindex) return false;
        }
        /* qui controllo se la potenza del valore più a sinistra è uguale a quella successiva:
         *  inizializzo la variabile check a true per avere la possibilità di avere più numeri
         *  con la stessa potenza uno di seguito all'altro */
        if (rn1.getPotenzaDieci()==rn2.getPotenzaDieci()){
          /* prevengo che possa darmi validi valori tipo IXXXVI = 35 */
          if (rn2.getPotenzaDieci()>lastPowerindex) return false;
          check = true;
          /* prevengo che possa darmi validi valori tipo VV = 10 */
          if(rn1.getValoreAssoluto() == 5 && rn2.getValoreAssoluto() == 5) return false;
          if(rn1.getValoreAssoluto() == 1 && rn2.getValoreAssoluto() == 5) {
            counterPrevious1++;
            counterFives++;
            /* prevengo che possa darmi validi valori tipo VIV = 9 */
            if (counterFives>1 || counterPrevious1>1) return false;
          }
          /* conto quanti 5 della stessa potenza ci sono nel numero */
          if (rn1.getValoreAssoluto() == 5 && rn2.getValoreAssoluto() == 1) counterFives++;

          /* conto quanti 1 della stessa potenza ci sono nel numero per prevenire IIV = 5 o IIII = 4 */
          if (rn1.getValoreAssoluto() == 1 && rn2.getValoreAssoluto() == 1){
            counterPrevious1++;
            counterNext3++;
            if(counterNext3>2) return false;
          }
        }
        /* qui controllo che il primo numero non abbia una differenza di potenza superiore a 1
         * rispetto al secondo (per non avere risultati true in casi tipo IC = 99) */
        if (rn1.getPotenzaDieci()+1 < rn2.getPotenzaDieci()) return false;
        else {
          /* se trovo un rn1 = 5 alla potenza 0+x ed rn2 alla potenza 1+x, devo restituire falso
           *  (per non avere risultati true in casi tipo LC = 50)*/
          if (rn1.getValoreAssoluto() == 5 && rn1.getPotenzaDieci() < rn2.getPotenzaDieci()) return false;
          else{
            lastPowerindex = rn1.getPotenzaDieci();
            counterPower++;
            check = true;
          }
        }
      }
    }
    return check;
  }

  /* funzioni sostituite, funzionanti, ma non più utilizzate */

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

  private String returnRoman2(int numero){
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
      sum = (tmp1 < tmp2) ? sum - tmp1: sum + tmp1;
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
