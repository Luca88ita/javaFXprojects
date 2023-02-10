package com.mainfolder.moneymanager;

import java.util.Objects;

public class Example {

  public static class Car{
    String brand;
    double hp;

    public Car(String brand, double hp) {
      this.brand = brand;
      this.hp = hp;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Car car = (Car) o;
      return Double.compare(car.hp, hp) == 0 && Objects.equals(brand, car.brand);
    }

    @Override
    public int hashCode() {
      return Objects.hash(brand, hp);
    }
  }
  public static void main(String[] args) {
    Car c1 = new Car("BMW", 600.0);
    Car c2 = new Car("Audi", 500.0);
    Car c3 = new Car("Audi", 500.0);
    String s1 = new String("ciao");
    String s2 = new String("ciao");
    System.out.println(s1 == s2);
    System.out.println(s1.equals(s2));
    System.out.println(c1.equals(c2));
    System.out.println(c3.equals(c2));

  }
}
