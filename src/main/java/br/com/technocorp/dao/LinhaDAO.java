package br.com.technocorp.dao;

import br.com.technocorp.bean.Linha;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface LinhaDAO extends CrudRepository<Linha, Integer> {

    @Query("Select i from Linha i  where i.codigo = :codigo and i.nome = :nome")
    public Linha findByCode(@Param("codigo") String codigo,@Param("nome")String nome);
    @Query("Select i from Linha i  where i.id = :codigo")
    public Linha findByIDW(@Param("codigo") Integer codigo);

    @Query("Select i from Linha i  where i.id = :codigo")
    public Linha findByIDLast(@Param("codigo") Integer codigo);

    @Query("Select i from Linha i  where i.nome like %:name%")
    public List<Linha> findByName(@Param("name") String name);

    @Query("Select i from Linha i  where i.codigo = :codigo")
    public Linha findByCodigoString(@Param("codigo") String codigo);

    @Query(value = "delete   from Linha l  where l.id= :id")
    public void deleteById(@Param("id")Integer id);

    @Query("Select i from Linha i  where i.nome =:name")
    public List<Linha> findName(@Param("name") String name);

    @Query("Select i from Linha i ")
    public List<Linha> findAllLinha();


}
