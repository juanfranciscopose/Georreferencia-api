package cala.com.georreferencia_api.ug.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cala.com.georreferencia_api.ug.entity.Ug;

@Repository
public interface UgRepository extends JpaRepository<Ug, Long>, JpaSpecificationExecutor<Ug> {
}
