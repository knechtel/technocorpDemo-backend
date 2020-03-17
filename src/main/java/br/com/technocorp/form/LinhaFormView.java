package br.com.technocorp.form;

import br.com.technocorp.bean.Linha;

import java.util.Objects;

public class LinhaFormView {
    private Integer id;
    private String codigo;
    private String nome;


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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinhaFormView that = (LinhaFormView) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(codigo, that.codigo) &&
                Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo, nome);
    }

    public LinhaFormView build(Linha linha) {
        LinhaFormView lfv = new LinhaFormView();
        lfv.setNome(linha.getNome());
        lfv.setCodigo(linha.getCodigo());
        return lfv;
    }
}
