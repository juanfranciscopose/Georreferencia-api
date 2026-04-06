package cala.com.georreferencia_api.main.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import cala.com.georreferencia_api.main.dto.GeorreferenciaQuery;
import cala.com.georreferencia_api.main.entity.Georreferencia;
import jakarta.persistence.criteria.Predicate;

public class ObtenerGeorreferenciaSpecification {
    public static Specification<Georreferencia> build(GeorreferenciaQuery query) {
        return (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (query.getBarrioId() != null) {
                predicates.add(cb.equal(root.get("barrioId"), query.getBarrioId()));
            }

            if (query.getLocalidadId() != null) {
                predicates.add(cb.equal(root.get("localidadId"), query.getLocalidadId()));
            }

            if (query.getCiudadId() != null) {
                predicates.add(cb.equal(root.get("ciudadId"), query.getCiudadId()));
            }

            if (query.getProvinciaId() != null) {
                predicates.add(cb.equal(root.get("provinciaId"), query.getProvinciaId()));
            }

            if (query.getUgId() != null){
                predicates.add(cb.equal(root.get("ugId"), query.getUgId()));
            }

            if (query.getEstadoEdilicioId() != null) {
                predicates.add(cb.equal(root.get("estadoEdilicioId"), query.getEstadoEdilicioId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
