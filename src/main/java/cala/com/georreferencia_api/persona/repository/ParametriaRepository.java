package cala.com.georreferencia_api.persona.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import cala.com.georreferencia_api.persona.entity.ParametriaId;

@NoRepositoryBean
public interface ParametriaRepository<T> extends JpaRepository<T, ParametriaId>{

    public List<T> findByPersonaId(Long personaId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM #{#entityName} e WHERE e.personaId = :personaId")
    void deleteByPersonaId(Long personaId);
    
}
