package br.com.technocorp.dto;

import java.util.List;

public class LinhaDTO {

    private Integer idLinha;
    private Integer id;
    private String codigo;
    private String nome;

    private List<CoordinateDTO> listCoordinate;

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

    public List<CoordinateDTO> getListCoordinate() {
        return listCoordinate;
    }

    public void setListCoordinate(List<CoordinateDTO> listCoordinate) {
        this.listCoordinate = listCoordinate;
    }
}
