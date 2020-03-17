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

    @Query("Select c from Coordinate c  WHERE c.linha.id = :id")
    public List<Coordinate> findIdLinha(@Param("id") Integer id);


//    @Query(value = " delete from Coordinate c join fetch c.linha where coordinate.linha.id in (select c.linha.id from Coordinate c  " +
//            " join fetch c.linha where c.linha.id =  :id_Linha)")
//    public void deleteByLinha(@Param("id_Linha") Integer id_Linha);

}
