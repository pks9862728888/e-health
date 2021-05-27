package com.curesio.ehealth.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public ResponseEntity<Map<String, String>> getResponse() {
        Map<String, String> map = Map.of("status", HttpStatus.OK.getReasonPhrase());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
