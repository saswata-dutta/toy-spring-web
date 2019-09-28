package com.saswata.jobs.service;

import java.util.List;

public interface Job {
  boolean add(int id, int value);

  boolean remove(int id);

  List<Integer> getAll();

  List<Integer> getAllAfter(int startValue);
}
