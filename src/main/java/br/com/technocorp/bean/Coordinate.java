package br.com.technocorp.bean;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Coordinate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String lat;
    private String lng;

    @ManyToOne()
    @JoinColumn(name = "id_linha", foreignKey = @ForeignKey(name = "id"))
    private Linha linha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Linha getLinha() {
        return linha;
    }

    public void setLinha(Linha linha) {
        this.linha = linha;
    }

    public Coordinate toSingleCoordinate(){
        Coordinate c = new Coordinate();
        c.setLat(lat);
        c.setLng(lng);
        c.setId(id);

        return c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(lat, that.lat) &&
                Objects.equals(lng, that.lng) &&
                Objects.equals(linha, that.linha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lat, lng, linha);
    }
}
