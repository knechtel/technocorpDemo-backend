package br.com.technocorp.bean;

import br.com.technocorp.form.LinhaFormView;
import com.thoughtworks.xstream.XStream;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Linha implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
  
    private Integer idLinha;
    private String codigo;
    private String nome;

    @OneToMany(mappedBy = "linha")
    private List<Coordinate> listCoordinate;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Linha linha = (Linha) o;
        return Objects.equals(codigo, linha.codigo) &&
                Objects.equals(nome, linha.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, nome);
    }

    public Integer getIdLinha() {
        return idLinha;
    }

    public void setIdLinha(Integer idLinha) {
        this.idLinha = idLinha;
    }

    public LinhaFormView build(Linha linha){
        LinhaFormView linhaFormView = new LinhaFormView();
        linhaFormView.setNome(linha.getNome());
        linhaFormView.setCodigo(linha.getCodigo());
        linhaFormView.setId(linha.getId());
        return linhaFormView;
    }
}
