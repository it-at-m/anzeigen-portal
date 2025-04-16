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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
    @Transactional
    @SuppressWarnings({ "PMD.UseObjectWithCaseConventions", "PMD.UseObjectForClearerAPI" })
    public Page<AdTO> searchActiveAds(final String userId, final String searchTerm, final Long categoryId, final List<AdType> types, final String sortBy,
            final String order, final Pageable pageable,
            final Long adId) {
        return searchAds(userId, searchTerm, categoryId, types, sortBy, order, pageable, adId, true);
    }

    @Override
    @Transactional
    @SuppressWarnings({ "PMD.UseObjectWithCaseConventions", "PMD.UseObjectForClearerAPI" })
    @PreAuthorize("hasAuthority(T(de.muenchen.anzeigenportal.security.AuthoritiesEnum).FACHADMIN.name())")
    public Page<AdTO> searchDeactivatedAds(final String userId, final String searchTerm, final Long categoryId, final List<AdType> types, final String sortBy,
            final String order, final Pageable pageable,
            final Long adId) {
        return searchAds(userId, searchTerm, categoryId, types, sortBy, order, pageable, adId, false);
    }

    @SuppressWarnings({ "PMD.UseObjectForClearerAPI" })
    public Page<AdTO> searchAds(final String userId, final String searchTerm, final Long categoryId, final List<AdType> types, final String sortBy,
            final String order,
            final Pageable pageable, final Long adId, final boolean isActive) {

        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // Main query
        final CriteriaQuery<Ad> cq = cb.createQuery(Ad.class);
        final Root<Ad> ad = cq.from(Ad.class);
        final List<Predicate> predicates = buildPredicates(cb, ad, isActive, userId, searchTerm, categoryId, types, adId);
        cq.where(predicates.toArray(new Predicate[0]));

        // Add sorting and order
        final Order orderCriteria = buildSorter(cb, ad, sortBy, order);
        cq.orderBy(orderCriteria);

        final TypedQuery<Ad> query = entityManager.createQuery(cq);

        // Apply pagination
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        final List<AdTO> ads = query.getResultList()
                .stream()
                .map(mapper::toAdTO)
                .collect(Collectors.toList());

        // Count query for total elements
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        final Root<Ad> countRoot = countQuery.from(Ad.class);
        final List<Predicate> countPredicates = buildPredicates(cb, countRoot, isActive, userId, searchTerm, categoryId, types, adId);
        countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));
        final Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(ads, pageable, total);
    }

    public Order buildSorter(final CriteriaBuilder cb, final Root<Ad> root, final String sortBy, final String order) {
        // Add Sorting
        final Expression<?> sortExpression = PRICE_STRING.equals(sortBy)
                ? cb.selectCase()
                        .when(cb.equal(root.get(sortBy), 0), -1) // 0: Zu verschenken
                        .when(cb.greaterThan(root.get(sortBy), 0), root.get(sortBy)) // >0: Festpreis
                        .otherwise(cb.neg(root.get(sortBy))) // <0: Verhandelbar
                : root.get(sortBy);

        // Add Ordering
        return ORDER_ASC.equals(order) ? cb.asc(sortExpression) : cb.desc(sortExpression);
    }

    private List<Predicate> buildPredicates(final CriteriaBuilder cb, final Root<Ad> root, final boolean isActive, final String userId, final String searchTerm,
            final Long categoryId, final List<AdType> types, final Long adId) {
        final List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("active"), isActive));

        if (userId != null) {
            predicates.add(cb.equal(root.get("swbUser").get("id"), userId));
        }
        if (searchTerm != null) {
            final Predicate titlePredicate = cb.like(cb.lower(root.get("title")), "%" + searchTerm.toLowerCase(Locale.GERMAN) + "%");
            final Predicate descriptionPredicate = cb.like(cb.lower(root.get("description")), "%" + searchTerm.toLowerCase(Locale.GERMAN) + "%");
            predicates.add(cb.or(titlePredicate, descriptionPredicate));
        }
        if (categoryId != null) {
            predicates.add(cb.equal(root.get("adCategory").get("id"), categoryId));
        }
        if (types != null && !types.isEmpty()) {
            CriteriaBuilder.In<AdType> inClause = cb.in(root.get("adType"));
            for (AdType type : types) {
                inClause.value(type);
            }
            predicates.add(inClause);
            // predicates.add(cb.equal(root.get("adType"), types));
        }
        if (adId != null) {
            predicates.add(cb.equal(root.get("id"), adId));
        }

        return predicates;
    }
}
