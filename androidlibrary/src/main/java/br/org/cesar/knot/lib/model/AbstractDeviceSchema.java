/*
 * Copyright (c) 2017, CESAR.
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD license. See the LICENSE file for details.
 *
 *
 */

package br.org.cesar.knot.lib.model;

import android.support.annotation.NonNull;

/**
 * The device schema.
 */
public class AbstractDeviceSchema {

    // Device Identification
    private int sensor_id;

    // Device Authentication
    private int value_type;

    private int unit;

    private int type_id;

    private String name;


    public int getSensorId() {
        return sensor_id;
    }

    public void setSensorId(int sensor_id) {
        this.sensor_id = sensor_id;
    }

    public int getValueType() {
        return value_type;
    }

    public void setValueType(int value_type) {
        this.value_type = value_type;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getTypeId() {
        return type_id;
    }

    public void setTypeId(int type_id) {
        this.type_id = type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
