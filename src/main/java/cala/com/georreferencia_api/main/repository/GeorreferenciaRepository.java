package cala.com.georreferencia_api.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cala.com.georreferencia_api.main.entity.Georreferencia;

@Repository
public interface GeorreferenciaRepository extends JpaRepository<Georreferencia, Long>, JpaSpecificationExecutor<Georreferencia>{
}
