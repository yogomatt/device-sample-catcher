package com.yogomatt.iot.devicesamplecatcher.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.yogomatt.iot.devicesamplecatcher.controller.dto.DeviceSampleDto;
import com.yogomatt.iot.devicesamplecatcher.controller.dto.DeviceSampleDto.MeasureDto;

/**
 * Excellent reference to understand the Casssandra primary key definition
 * <p>
 * 
 * @see https://www.baeldung.com/cassandra-keys
 *      </p>
 */
@Table(value = "devicesample")
public class DeviceSample {

    @PrimaryKey
    private DeviceSamplePk pk;

    @Column
    @CassandraType(type = CassandraType.Name.MAP, typeArguments = { CassandraType.Name.TEXT,
            CassandraType.Name.DOUBLE })
    private Map<String, Double> measure;

    public static DeviceSample fromDto(DeviceSampleDto dto) {
        DeviceSample sample = new DeviceSample();

        DeviceSamplePk pk = new DeviceSamplePk();
        // OffsetDateTime dt = OffsetDateTime.parse(dto.getSampleDate(),
           //         DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        pk.setReadTimestamp(dto.getSampleDate());
        pk.setSampleType(dto.getSampleType());
        pk.setDeviceId(dto.getDeviceId());

        sample.setPk(pk);
        sample.addMeasure(dto.getMeasure());

        return sample;
    }

    public DeviceSamplePk getPk() {
        return pk;
    }

    public void setPk(DeviceSamplePk pk) {
        this.pk = pk;
    }

    public Map<String, Double> getMeasure() {
        return measure;
    }

    public void setMeasure(Map<String, Double> measure) {
        this.measure = measure;
    }

    public void addMeasure(MeasureDto measureDto) {
        if (measure == null) {
            measure = new HashMap<>();
        }

        measure.put(measureDto.getType(), measureDto.getValue());
    }

}
