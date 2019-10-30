package br.com.technocorp.dao;

import br.com.technocorp.bean.Linha;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface LinhaDAO extends CrudRepository<Linha, Integer> {

    @Query("Select i from Linha i  where i.codigo = :codigo")
    public Linha findByCode(@Param("codigo") String codigo);
    @Query("Select i from Linha i  where i.id = :codigo")
    public Linha findByIDW(@Param("codigo") Integer codigo);

    @Query("Select i from Linha i  where i.nome like %:name%")
    public List<Linha> findByName(@Param("name") String name);
}
