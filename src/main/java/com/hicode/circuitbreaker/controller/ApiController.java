package com.hicode.circuitbreaker.controller;

import com.hicode.circuitbreaker.model.Activity;
import com.hicode.circuitbreaker.model.FakeActivity;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/activity")
@AllArgsConstructor
public class ApiController {
    private RestTemplate restTemplate;
    private final String BORED_API = "https://www.boredapi.com/api/activity";
    @GetMapping
    @CircuitBreaker(name = "randomActivity", fallbackMethod = "fallbackRandomActivity")
    public ResponseEntity<?> getActivity(){
        ResponseEntity<Activity> responseEntity = restTemplate.getForEntity(BORED_API, Activity.class);
        Activity activity = responseEntity.getBody();
        FakeActivity activity1 = new FakeActivity(activity.getActivity());
        return ResponseEntity.ok(activity1);
    }

    public  ResponseEntity<?> fallbackRandomActivity(Throwable throwable) {
        return ResponseEntity.ok("Something error!, Try again");
    }
}
