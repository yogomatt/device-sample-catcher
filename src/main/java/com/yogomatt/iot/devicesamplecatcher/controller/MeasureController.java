package com.yogomatt.iot.devicesamplecatcher.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yogomatt.iot.devicesamplecatcher.controller.dto.DeviceSampleDto;
import com.yogomatt.iot.devicesamplecatcher.model.DeviceSample;
import com.yogomatt.iot.devicesamplecatcher.repository.DeviceSampleRepository;

@RestController
@RequestMapping("/api")
public class MeasureController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    DeviceSampleRepository repository;

    @GetMapping("/measure")
    public String inquireMeasure() {
        return "Hi";
    }

    @PostMapping("/measure")
    public void recordMeasure(@RequestBody DeviceSampleDto deviceSample) {
        log.info("Saving measure sample... {}", deviceSample.toString());

        repository.save(DeviceSample.fromDto(deviceSample));

        log.info("Measure saved: {}", deviceSample.toString());
    }
    
}