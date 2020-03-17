package br.com.technocorp.form;

import br.com.technocorp.bean.Coordinate;

public class CoordinateForm {

    private String lat;
    private String lng;

    private Integer idInt;


    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Integer getIdInt() {
        return idInt;
    }

    public void setIdInt(Integer idInt) {
        this.idInt = idInt;
    }

    public Coordinate build(){
        Coordinate c = new Coordinate();
        c.setLat(lat);
        c.setLng(lng);
        return c;
    }

}
