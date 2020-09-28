package com.damianos.codingchallenge.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;



import java.io.IOException;

@Controller
public class MainController {
  private final Logger logger = LoggerFactory.getLogger(MainController.class);

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String index(Model model) {
    logger.debug("index()");
    return "redirect:/home";
  }

  @RequestMapping("/home")
  public String home() {
    return "home";
  }




}