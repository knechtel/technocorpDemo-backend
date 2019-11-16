package br.com.technocorp.form;

import br.com.technocorp.bean.Coordinate;

public class CoordinateForm {

    private Double lat;
    private Double lng;

    private Integer idInt;


    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
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
