package cala.com.georreferencia_api.institucion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import cala.com.georreferencia_api.institucion.entity.ParametriaId;

@NoRepositoryBean
public interface ParametriaRepository<T> extends JpaRepository<T, ParametriaId>{

    public List<T> findByInstitucionId(Long institucionId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM #{#entityName} e WHERE e.institucionId = :institucionId")
    void deleteByInstitucionId(Long institucionId);
    
}
