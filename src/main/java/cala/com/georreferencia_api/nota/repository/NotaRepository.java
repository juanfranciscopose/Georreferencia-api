package cala.com.georreferencia_api.nota.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cala.com.georreferencia_api.nota.entity.Nota;

@Repository// TODO: se puede mejorar la busqeda por indices ids
public interface NotaRepository extends JpaRepository<Nota, Long>{
}
