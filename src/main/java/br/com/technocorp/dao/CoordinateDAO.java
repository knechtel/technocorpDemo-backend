package br.com.technocorp.dao;

import br.com.technocorp.bean.Coordinate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinateDAO extends CrudRepository<Coordinate, Integer> {
}
