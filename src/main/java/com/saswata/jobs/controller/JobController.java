package com.saswata.jobs.controller;

import com.saswata.jobs.service.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class JobController {
  @Autowired private Job jobService;

  @GetMapping("/add")
  public @ResponseBody StatResponse add(
      @RequestParam("integer") int value, @RequestParam("jobid") int id) {
    boolean result = jobService.add(id, value);
    return StatResponse.valueOf(result);
  }

  @PostMapping("/remove")
  public @ResponseBody StatResponse remove(@RequestParam("jobid") int id) {
    boolean result = jobService.remove(id);
    return StatResponse.valueOf(result);
  }

  @GetMapping("/all")
  public @ResponseBody List<Integer> getAll(
      @RequestParam(value = "startInteger") Optional<Integer> startValue) {
    return startValue.map(v -> jobService.getAllAfter(v)).orElseGet(jobService::getAll);
  }
}
