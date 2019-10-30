package br.com.technocorp.bean;

import com.thoughtworks.xstream.XStream;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Linha implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idLinha;
    private Integer id;
    private String codigo;
    private String nome;

    @OneToMany
    private List<Coordinate> listCoordinate;

    public Integer getIdLinha() {
        return idLinha;
    }

    public void setIdLinha(Integer idLinha) {
        this.idLinha = idLinha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Coordinate> getListCoordinate() {
        return listCoordinate;
    }

    public void setListCoordinate(List<Coordinate> listCoordinate) {
        this.listCoordinate = listCoordinate;
    }
    public String toXML() {
        // TODO Auto-generated method stub
        return new XStream().toXML(this);
    }
}
