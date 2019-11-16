package br.com.technocorp.dao;

import br.com.technocorp.bean.Coordinate;
import br.com.technocorp.bean.Linha;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoordinateDAO extends CrudRepository<Coordinate, Integer> {

    @Query("Select c from Coordinate  c join fetch c.linha where c.linha.id = :codigo")
    public List<Coordinate> findByIDW(@Param("codigo") Integer codigo);

    @Query("Select c from Coordinate c  where c.lat = :lat and c.lng =:lng")
    public Coordinate findLat(@Param("lat") Double lat,@Param("lng") Double lng);

    @Query(value = " delete * from coordinate join linha on coordinate.id_linha = linha.id where coordinate.id_linha=?;",nativeQuery = true)
    public void deleteByLinha( Integer idLinha);

}
