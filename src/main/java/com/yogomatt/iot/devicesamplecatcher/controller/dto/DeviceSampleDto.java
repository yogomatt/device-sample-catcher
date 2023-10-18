package com.yogomatt.iot.devicesamplecatcher.controller.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class DeviceSampleDto {

    @DateTimeFormat(pattern = "yyyy-mm-dd'T'HH:MM:SS.SSS") // sample: 2018-04-26 05:59:38.226
    private LocalDateTime sampleDate;
    private String sampleType;
    private String deviceId;
    private MeasureDto measure;

    public LocalDateTime getSampleDate() {
        return sampleDate;
    }

    public void setSampleDate(LocalDateTime sampleDate) {
        this.sampleDate = sampleDate;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public MeasureDto getMeasure() {
        return measure;
    }

    public void setMeasure(MeasureDto measure) {
        this.measure = measure;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Device Sample:");
        sb.append(System.lineSeparator());
        sb.append("Device ID: ");
        sb.append(deviceId);
        sb.append(System.lineSeparator());
        sb.append("Sample type: ");
        sb.append(sampleType);
        sb.append(System.lineSeparator());
        sb.append("Sample date: ");
        sb.append(sampleDate);
        sb.append(System.lineSeparator());
        sb.append("Measure type: ");
        sb.append(getMeasure().getType());
        sb.append(System.lineSeparator());
        sb.append("Measure value: ");
        sb.append(getMeasure().getValue());

        return sb.toString();
    }

    public class MeasureDto {

        private String type;
        private Double value;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

    }

}
