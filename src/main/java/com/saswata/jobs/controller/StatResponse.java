package com.saswata.jobs.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class StatResponse {
  @NotEmpty private String stat;

  public static final StatResponse OK = new StatResponse("ok");
  public static final StatResponse ERROR = new StatResponse("error");

  public static StatResponse valueOf(boolean status) {
    if (status) return OK;
    return ERROR;
  }
}
