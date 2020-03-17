package br.com.technocorp.form;

import br.com.technocorp.bean.Coordinate;
import br.com.technocorp.bean.Linha;

import java.util.ArrayList;
import java.util.List;

public class LinhaForm {
    private boolean alreadInDatabase= false;
    private Integer id;
    private Integer idLinha;
    private String codigo;
    private String nome;
    private List<CoordinateForm> listCoordenada;

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

    public List<CoordinateForm> getListCoordenada() {
        return listCoordenada;
    }

    public void setListCoordenada(List<CoordinateForm> listCoordenada) {
        this.listCoordenada = listCoordenada;
    }

    public Linha build() {
        Linha i = new Linha();
        i.setId(id);
        i.setCodigo(codigo);
        i.setNome(nome);
        i.setIdLinha(id);
        return i;
    }

    public List<Coordinate> buildList() {
        List<Coordinate> list = new ArrayList<>();
        if (listCoordenada != null) {
            for (CoordinateForm c :
                    listCoordenada) {
                Coordinate coordinate = new Coordinate();
                coordinate.setLng(c.getLng());
                coordinate.setLat(c.getLat());
                list.add(coordinate);
            }
        }
        return list;
    }

    public LinhaForm toLinha(Linha linha) {
        LinhaForm linhaForm = new LinhaForm();
        linhaForm.setCodigo(linha.getCodigo());
        linhaForm.setNome(linha.getNome());
        linhaForm.setId(linha.getId());
        List<CoordinateForm> list = new ArrayList<>();
        for (Coordinate c :
                linha.getListCoordinate()) {
            CoordinateForm cf = new CoordinateForm();
            cf.setLat(c.getLat());
            cf.setLng(c.getLng());
            list.add(cf);
        }
        linhaForm.setListCoordenada(list);
    return linhaForm;
    }

    public boolean isAlreadInDatabase() {
        return alreadInDatabase;
    }

    public void setAlreadInDatabase(boolean alreadInDatabase) {
        this.alreadInDatabase = alreadInDatabase;
    }

    public Integer getIdLinha() {
        return idLinha;
    }

    public void setIdLinha(Integer idLinha) {
        this.idLinha = idLinha;
    }
}

