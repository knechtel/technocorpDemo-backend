package br.com.technocorp.dto;


import br.com.technocorp.bean.Coordinate;

import java.util.List;
import java.util.Map;

public class IntinerarioDTO {

    private Integer idLinha;
    private String Nome;
    private String codigo;
    private List<Coordinate> listCoordinate;

    public Integer getIdLinha() {
        return idLinha;
    }

    public void setIdLinha(Integer idLinha) {
        this.idLinha = idLinha;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<Coordinate> getListCoordinate() {
        return listCoordinate;
    }

    public void setListCoordinate(List<Coordinate> listCoordinate) {
        this.listCoordinate = listCoordinate;
    }
}
