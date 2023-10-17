package com.yogomatt.iot.devicesamplecatcher.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.yogomatt.iot.devicesamplecatcher.model.DeviceSample;
import com.yogomatt.iot.devicesamplecatcher.model.DeviceSamplePk;

@Repository
public interface DeviceSampleRepository extends CassandraRepository<DeviceSample, DeviceSamplePk> {

}
