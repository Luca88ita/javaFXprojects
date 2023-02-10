package com.mainfolder.moneymanager;

import java.time.LocalDate;
import java.util.Objects;

public class Expense {
  int idExpense;
  LocalDate date;
  String description;
  double amount;

  public Expense(LocalDate date, String description, double amount) {
    this.date = date;
    this.description = description;
    this.amount = amount;
  }

  public Expense(int idExpense, LocalDate date, String description, double amount) {
    this.idExpense = idExpense;
    this.date = date;
    this.description = description;
    this.amount = amount;
  }

  public int getIdExpense() {
    return idExpense;
  }

  public void setIdExpense(int idExpense) {
    this.idExpense = idExpense;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Expense expense = (Expense) o;
    return Double.compare(expense.amount, amount) == 0 && Objects.equals(date, expense.date) && Objects.equals(description, expense.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, description, amount);
  }

  @Override
  public String toString() {
    return "Expense{" +
            "idExpense=" + idExpense +
            "date=" + date +
            ", description='" + description + '\'' +
            ", amount=" + amount +
            '}';
  }
}
