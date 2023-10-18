package com.yogomatt.iot.devicesamplecatcher.model;

import java.time.LocalDateTime;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class DeviceSamplePk {

    @PrimaryKeyColumn(name = "device_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String deviceId;

    @PrimaryKeyColumn(name = "sample_type", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String sampleType;

    @PrimaryKeyColumn(name = "read_timestamp", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private LocalDateTime readTimestamp;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public LocalDateTime getReadTimestamp() {
        return readTimestamp;
    }

    public void setReadTimestamp(LocalDateTime readTimestamp) {
        this.readTimestamp = readTimestamp;
    }

}
