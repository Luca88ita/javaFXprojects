package com.mainfolder.schoolbicocchi.domain;

public class SchoolClass {
  int id;
  String name;
  String teacher;
  String room;

  public SchoolClass(int id, String name, String teacher, String room) {
    this.id = id;
    this.name = name;
    this.teacher = teacher;
    this.room = room;
  }

  public SchoolClass(String name, String teacher, String room) {
    this.name = name;
    this.teacher = teacher;
    this.room = room;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTeacher() {
    return teacher;
  }

  public void setTeacher(String teacher) {
    this.teacher = teacher;
  }

  public String getRoom() {
    return room;
  }

  public void setRoom(String room) {
    this.room = room;
  }

  @Override
  public String toString() {
    return "SchoolClass{" + "id=" + id + ", name='" + name + '\'' + ", teacher='" + teacher + '\'' + ", room='" + room + '\'' + '}';
  }
}
