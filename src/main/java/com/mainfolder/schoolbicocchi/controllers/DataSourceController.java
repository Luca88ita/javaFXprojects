package com.mainfolder.schoolbicocchi.controllers;

import com.zaxxer.hikari.HikariDataSource;

public abstract class DataSourceController {
  HikariDataSource dataSource;

  public HikariDataSource getDataSource() {
    return dataSource;
  }

  public void setDataSource(HikariDataSource dataSource) {
    this.dataSource = dataSource;
  }

  public abstract void fetchData();

}
