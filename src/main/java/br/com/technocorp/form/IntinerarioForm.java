package br.com.technocorp.form;

import java.util.List;

public class IntinerarioForm {

    private Integer idMysql;
    private Integer idLinha;
    private String Nome;
    private String codigo;
    private List<CoordinateForm> listCoordinate;

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

    public List<CoordinateForm> getListCoordinate() {
        return listCoordinate;
    }

    public void setListCoordinate(List<CoordinateForm> listCoordinate) {
        this.listCoordinate = listCoordinate;
    }

    public Integer getIdMysql() {
        return idMysql;
    }

    public void setIdMysql(Integer idMysql) {
        this.idMysql = idMysql;
    }
}
