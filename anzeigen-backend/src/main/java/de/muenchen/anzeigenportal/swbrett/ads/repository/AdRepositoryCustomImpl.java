package de.muenchen.anzeigenportal.swbrett.ads.repository;

import de.muenchen.anzeigenportal.swbrett.ads.model.Ad;
import de.muenchen.anzeigenportal.swbrett.ads.model.AdTO;
import de.muenchen.anzeigenportal.swbrett.ads.model.AdType;
import de.muenchen.anzeigenportal.swbrett.ads.service.AdMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@SuppressWarnings({ "PMD.CouplingBetweenObjects", "PMD.UselessParentheses" })
@Repository
public class AdRepositoryCustomImpl implements AdRepositoryCustom {

    public static final String ORDER_ASC = "asc";
    public static final String ORDER_DESC = "desc";

    public static final String PRICE_STRING = "price";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AdMapper mapper;

    @Override
    @SuppressWarnings({ "PMD.UseObjectWithCaseConventions", "PMD.UseObjectForClearerAPI" })
    public Page<AdTO> searchActiveAds(final String userId, final String searchTerm, final Long categoryId, final AdType type, final String sortBy,
            final String order, final Pageable pageable,
            final Long adId) {
        return searchAds(userId, searchTerm, categoryId, type, sortBy, order, pageable, adId, true);
    }

    @Override
    @SuppressWarnings({ "PMD.UseObjectWithCaseConventions", "PMD.UseObjectForClearerAPI" })
    @PreAuthorize("hasAuthority(T(de.muenchen.anzeigenportal.security.AuthoritiesEnum).REFARCH_BACKEND_READ_THEENTITY.name())")
    public Page<AdTO> searchDeactivatedAds(final String userId, final String searchTerm, final Long categoryId, final AdType type, final String sortBy,
            final String order, final Pageable pageable,
            final Long adId) {
        return searchAds(userId, searchTerm, categoryId, type, sortBy, order, pageable, adId, false);
    }

    public Page<AdTO> searchAds(final String userId, final String searchTerm, final Long categoryId, final AdType type, final String sortBy, final String order,
            final Pageable pageable, final Long adId, final boolean isActive) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // Main query
        CriteriaQuery<Ad> cq = cb.createQuery(Ad.class);
        Root<Ad> ad = cq.from(Ad.class);
        List<Predicate> predicates = buildPredicates(cb, ad, isActive, userId, searchTerm, categoryId, type, adId);
        cq.where(predicates.toArray(new Predicate[0]));

        // Add sorting and order
        Order orderCriteria = buildSorter(cb, ad, sortBy, order);
        cq.orderBy(orderCriteria);

        TypedQuery<Ad> query = entityManager.createQuery(cq);

        // Apply pagination
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<AdTO> ads = query.getResultList()
                .stream()
                .map(mapper::toAdTO)
                .collect(Collectors.toList());

        // Count query for total elements
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Ad> countRoot = countQuery.from(Ad.class);
        List<Predicate> countPredicates = buildPredicates(cb, countRoot, isActive, userId, searchTerm, categoryId, type, adId);
        countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(ads, pageable, total);
    }

    public Order buildSorter(CriteriaBuilder cb, Root<Ad> root, final String sortBy, final String order) {
        // Add Sorting
        Expression<?> sortExpression = PRICE_STRING.equals(sortBy)
                ? cb.selectCase()
                        .when(cb.equal(root.get(sortBy), 0), -1) // 0: Zu verschenken
                        .when(cb.greaterThan(root.get(sortBy), 0), root.get(sortBy)) // >0: Festpreis
                        .otherwise(cb.neg(root.get(sortBy))) // <0: Verhandelbar
                : root.get(sortBy);

        // Add Ordering
        return ORDER_ASC.equals(order) ? cb.asc(sortExpression) : cb.desc(sortExpression);
    }

    private List<Predicate> buildPredicates(CriteriaBuilder cb, Root<Ad> root, boolean isActive, String userId, String searchTerm,
            Long categoryId, AdType type, Long adId) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("active"), isActive));

        if (userId != null) {
            predicates.add(cb.equal(root.get("swbUser").get("id"), userId));
        }
        if (searchTerm != null) {
            Predicate titlePredicate = cb.like(cb.lower(root.get("title")), "%" + searchTerm.toLowerCase() + "%");
            Predicate descriptionPredicate = cb.like(cb.lower(root.get("description")), "%" + searchTerm.toLowerCase() + "%");
            predicates.add(cb.or(titlePredicate, descriptionPredicate));
        }
        if (categoryId != null) {
            predicates.add(cb.equal(root.get("adCategory").get("id"), categoryId));
        }
        if (type != null) {
            predicates.add(cb.equal(root.get("adType"), type));
        }
        if (adId != null) {
            predicates.add(cb.equal(root.get("id"), adId));
        }

        return predicates;
    }
}
