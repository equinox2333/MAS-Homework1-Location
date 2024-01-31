package com.alex.location.bean;

/**
 * The data requested from the network, after removing the useless fields, the beans needed locally.
 */
public class ApiBean {
    public int id;
    public String uv;
    public String uv_time;
    public String dawn;
    public String dusk;
    public String timestamp;
    public String geoAddress;

    public ApiBean() {
    }

    public ApiBean(int id, String uv, String uv_time, String dawn, String dusk, String timestamp, String geoAddress) {
        this.id = id;
        this.uv = uv;
        this.uv_time = uv_time;
        this.dawn = dawn;
        this.dusk = dusk;
        this.timestamp = timestamp;
        this.geoAddress = geoAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUv() {
        return uv;
    }

    public void setUv(String uv) {
        this.uv = uv;
    }

    public String getUv_time() {
        return uv_time;
    }

    public void setUv_time(String uv_time) {
        this.uv_time = uv_time;
    }

    public String getDawn() {
        return dawn;
    }

    public void setDawn(String dawn) {
        this.dawn = dawn;
    }

    public String getDusk() {
        return dusk;
    }

    public void setDusk(String dusk) {
        this.dusk = dusk;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getGeoAddress() {
        return geoAddress;
    }

    public void setGeoAddress(String geoAddress) {
        this.geoAddress = geoAddress;
    }

    @Override
    public String toString() {
        return "{" + "id=" + id + ", uv='" + uv + '\'' + ", uv_time='" + uv_time + '\'' + ", dawn='" + dawn + '\'' + ", dusk='" + dusk + '\'' + ", timestamp='" + timestamp + '\'' + ", geoAddress='" + geoAddress + '\'' + '}';
    }
}
