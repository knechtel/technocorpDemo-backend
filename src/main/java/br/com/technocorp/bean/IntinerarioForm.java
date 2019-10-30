package br.com.technocorp.bean;

public class IntinerarioForm {

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

    public Linha build(){
        Linha i = new Linha();
        i.setId(id);
        i.setCodigo(codigo);
        i.setNome(nome);
        return i;
    }
}
