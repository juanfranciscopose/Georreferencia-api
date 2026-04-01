package cala.com.georreferencia_api.ug.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import cala.com.georreferencia_api.ug.dto.UgQuery;
import cala.com.georreferencia_api.ug.entity.Ug;
import jakarta.persistence.criteria.Predicate;

public class ObtenerUgSpecification {
    public static Specification<Ug> build(UgQuery query) {
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

            if (query.getDestacado() != null) {
                predicates.add(cb.equal(root.get("destacado"), query.getDestacado()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
