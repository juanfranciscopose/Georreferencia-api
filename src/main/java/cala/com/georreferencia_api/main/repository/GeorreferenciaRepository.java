package cala.com.georreferencia_api.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cala.com.georreferencia_api.main.entity.Georreferencia;

@Repository
public interface GeorreferenciaRepository extends JpaRepository<Georreferencia, Long>, JpaSpecificationExecutor<Georreferencia>{
    List<Georreferencia> findByBarrioId(Long barrioId);
    List<Georreferencia> findByLocalidadId(Long localidadId);
    List<Georreferencia> findByCiudadId(Long ciudadId);
    List<Georreferencia> findByUgId(Long ugId);
    List<Georreferencia> findByProvinciaId(Integer provinciaId);
}
