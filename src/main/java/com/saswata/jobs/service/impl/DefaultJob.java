package com.saswata.jobs.service.impl;

import com.saswata.jobs.service.Job;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@Slf4j
public class DefaultJob implements Job {

  private Map<Integer, Integer> idIndex;
  private SortedMap<Integer, Set<Integer>> valueIndex;

  private ConcurrentMap<Integer, Integer> locks;

  private Object getCacheSyncObject(final int id) {
    locks.computeIfAbsent(id, Integer::valueOf);
    return locks.get(id);
  }

  private void delCacheSyncObject(final int id) {
    locks.remove(id);
  }

  @PostConstruct
  private void init() {
    idIndex = new HashMap<>();
    valueIndex = new TreeMap<>();
    locks = new ConcurrentHashMap<>();
    log.info("Initialised Storage");
  }

  @Override
  public boolean add(int id, int value) {
    synchronized (getCacheSyncObject(id)) {
      if (idIndex.containsKey(id)) return false;

      idIndex.put(id, value);

      if (valueIndex.containsKey(value)) {
        Set<Integer> oldIds = valueIndex.get(value);
        oldIds.add(id);
        valueIndex.put(value, oldIds);
      } else {
        Set<Integer> idSet = new HashSet<>(Collections.singletonList(id));
        valueIndex.put(value, idSet);
      }
      return true;
    }
  }

  @Override
  public boolean remove(int id) {
    boolean response = true;
    synchronized (getCacheSyncObject(id)) {

      if (idIndex.containsKey(id)) {
        int value = idIndex.get(id);
        idIndex.remove(id);
        Set<Integer> oldIds = valueIndex.get(value);
        oldIds.remove(id);
        valueIndex.put(value, oldIds);
      } else {
        response = false;
      }
      delCacheSyncObject(id);
    }
    return response;
  }

  @Override
  public List<Integer> getAll() {
    List<Integer> jobs = new ArrayList<>();
    valueIndex.values().forEach(jobs::addAll);
    return jobs;
  }

  @Override
  public List<Integer> getAllAfter(int startValue) {
    List<Integer> jobs = new ArrayList<>();
    valueIndex.tailMap(startValue).values().forEach(jobs::addAll);
    return jobs;
  }
}
