package com.mainfolder.romannumbersconverter;

import java.util.Objects;

public class RomanNumber {
    private int valoreAssoluto;
    private int potenzaDieci;
    private char letteraRomana;

    public RomanNumber(int valoreAssoluto, int potenzaDieci, char letteraRomana) {
        this.valoreAssoluto = valoreAssoluto;
        this.potenzaDieci = potenzaDieci;
        this.letteraRomana = letteraRomana;
    }

    public int getValoreAssoluto() {
        return valoreAssoluto;
    }

    public void setValoreAssoluto(int valoreAssoluto) {
        this.valoreAssoluto = valoreAssoluto;
    }

    public int getPotenzaDieci() {
        return potenzaDieci;
    }

    public void setPotenzaDieci(int potenzaDieci) {
        this.potenzaDieci = potenzaDieci;
    }

    public char getLetteraRomana() {
        return letteraRomana;
    }

    public void setLetteraRomana(char letteraRomana) {
        this.letteraRomana = letteraRomana;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RomanNumber that = (RomanNumber) o;
        return valoreAssoluto == that.valoreAssoluto && potenzaDieci == that.potenzaDieci && Objects.equals(letteraRomana, that.letteraRomana);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valoreAssoluto, potenzaDieci, letteraRomana);
    }

    @Override
    public String toString() {
        return "romanNumber{" +
                "valoreAssoluto=" + valoreAssoluto +
                ", potenzaDieci=" + potenzaDieci +
                ", letteraRomana='" + letteraRomana + '\'' +
                '}';
    }
}