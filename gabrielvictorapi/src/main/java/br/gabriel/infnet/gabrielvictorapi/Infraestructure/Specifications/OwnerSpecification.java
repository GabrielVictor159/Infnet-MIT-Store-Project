package br.gabriel.infnet.gabrielvictorapi.Infraestructure.Specifications;

import org.springframework.data.jpa.domain.Specification;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.Owner;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.User;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OwnerSpecification {

   public static Specification<Owner> findByCriteria(
            Optional<Integer> id,
            Optional<String> name,
            Optional<String> cnpj,
            Optional<String> address,
            Optional<String> cep,
            Optional<String> email
    ) {
        return (root, query, cb) -> {
            

            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                 root.fetch("user", JoinType.LEFT);
                 query.distinct(true); 
            }

            List<Predicate> predicates = new ArrayList<>();
            
            id.ifPresent(val -> predicates.add(cb.equal(root.get("id"), val)));
            name.ifPresent(val -> predicates.add(cb.like(cb.lower(root.get("name")), "%" + val.toLowerCase() + "%")));
            cnpj.ifPresent(val -> predicates.add(cb.equal(root.get("cnpj"), val)));
            address.ifPresent(val -> predicates.add(cb.like(cb.lower(root.get("address")), "%" + val.toLowerCase() + "%")));
            cep.ifPresent(val -> predicates.add(cb.equal(root.get("cep"), val)));
            email.ifPresent(val -> predicates.add(cb.like(cb.lower(root.get("contactEmail")), "%" + val.toLowerCase() + "%")));
            
            predicates.add(cb.isTrue(root.get("isActive")));
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Owner> findActiveOwnersWithProducts(User user) {
        return (root, query, cb) -> {
            
            root.fetch("products", JoinType.LEFT);
            
            query.distinct(true);

            Predicate userPredicate = cb.equal(root.get("user"), user);
            Predicate activePredicate = cb.isTrue(root.get("isActive"));
            
            return cb.and(userPredicate, activePredicate);
        };
    }

    public static Specification<Owner> findByIdsWithUser(List<Integer> ids) {
        return (root, query, cb) -> {
            
            root.fetch("user", JoinType.LEFT);
            query.distinct(true);

            Predicate idPredicate = root.get("id").in(ids);
            Predicate activePredicate = cb.isTrue(root.get("isActive"));

            return cb.and(idPredicate, activePredicate);
        };
    }

    public static Specification<Owner> findByIdWithUser(Integer id) {
        return (root, query, cb) -> {
            
            root.fetch("user", JoinType.LEFT);
            
            Predicate idPredicate = cb.equal(root.get("id"), id);
            Predicate activePredicate = cb.isTrue(root.get("isActive"));

            return cb.and(idPredicate, activePredicate);
        };
    }
}
