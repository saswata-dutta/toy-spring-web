package com.saswata.jobs.service.impl;

import com.saswata.jobs.service.Job;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

@Service
@Slf4j
public class DefaultJob implements Job {

  private Map<Integer, Integer> idIndex;
  private SortedMap<Integer, Set<Integer>> valueIndex;

  @PostConstruct
  private void init() {
    idIndex = new HashMap<>();
    valueIndex = new TreeMap<>();
    log.info("Initialised Storage");
  }

  @Override
  public boolean add(int id, int value) {
    return false;
  }

  @Override
  public boolean remove(int id) {
    return false;
  }

  @Override
  public List<Integer> getAll() {
    return null;
  }

  @Override
  public List<Integer> getAllAfter(int startValue) {
    return null;
  }
}
