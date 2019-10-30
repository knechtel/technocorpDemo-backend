package br.com.technocorp.bean;

import java.util.List;


import com.thoughtworks.xstream.XStream;


public class IntinerarioWrapper {
    private List<IntinerarioForm> listIntinarario;

    public List<IntinerarioForm> getListIntinarario() {
        return listIntinarario;
    }

    public void setListIntinarario(List<IntinerarioForm> listIntinarario) {
        this.listIntinarario = listIntinarario;
    }
    public String toXML() {
        // TODO Auto-generated method stub
        return new XStream().toXML(this);
    }
}
