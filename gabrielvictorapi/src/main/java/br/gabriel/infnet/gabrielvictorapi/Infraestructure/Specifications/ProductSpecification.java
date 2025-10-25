package br.gabriel.infnet.gabrielvictorapi.Infraestructure.Specifications;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.Owner;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.Product;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public class ProductSpecification {

    public static Specification<Product> findByCriteria(
            Optional<Integer> id,
            Optional<String> name
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            id.ifPresent(val -> predicates.add(cb.equal(root.get("id"), val)));
            name.ifPresent(val -> predicates.add(cb.like(cb.lower(root.get("name")), "%" + val.toLowerCase() + "%")));
            
            predicates.add(cb.isTrue(root.get("isActive")));
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Product> findById(Integer id) {
        return (root, query, cb) -> {
            
            Predicate idPredicate = cb.equal(root.get("id"), id);
            Predicate activePredicate = cb.isTrue(root.get("isActive"));

            return cb.and(idPredicate, activePredicate);
        };
    }

    public static Specification<Product> findByIdIn(List<Integer> productIds) {
        return (root, query, cb) -> {
            Predicate idPredicate = root.get("id").in(productIds);
            Predicate activePredicate = cb.isTrue(root.get("isActive"));

            return cb.and(idPredicate, activePredicate);
        };
    }

    public static Specification<Product> findByIdWithDeepAssociations(Integer id) {
        return (root, query, cb) -> {

            Fetch<Product, Owner> ownersFetch = root.fetch("owners", JoinType.LEFT);
            ownersFetch.fetch("user", JoinType.LEFT);

            query.distinct(true);

            Predicate idPredicate = cb.equal(root.get("id"), id);
            Predicate activePredicate = cb.isTrue(root.get("isActive"));

            return cb.and(idPredicate, activePredicate);
        };
    }

    public static Specification<Product> findByOwnerIdsWithDeepAssociations(List<Integer> ownerIds) {
        return (root, query, cb) -> {

            Fetch<Product, Owner> ownersFetch = root.fetch("owners", JoinType.LEFT);
            ownersFetch.fetch("user", JoinType.LEFT);

            query.distinct(true);

            Join<Product, Owner> ownersJoin = root.join("owners");
            
            Predicate ownerIdPredicate = ownersJoin.get("id").in(ownerIds);
            Predicate activePredicate = cb.isTrue(root.get("isActive"));

            return cb.and(ownerIdPredicate, activePredicate);
        };
    }
}
