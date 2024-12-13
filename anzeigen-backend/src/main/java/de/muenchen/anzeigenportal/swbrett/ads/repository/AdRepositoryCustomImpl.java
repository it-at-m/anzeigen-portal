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

    public Page<AdTO> searchAds(final String userId, final String searchTerm, final Long categoryId, final AdType type, final String sortBy, final String order, final Pageable pageable, final Long adId, final boolean isActive) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // Main query
        CriteriaQuery<Ad> cq = cb.createQuery(Ad.class);
        Root<Ad> ad = cq.from(Ad.class);
        List<Predicate> predicates = buildPredicates(cb, ad, isActive, userId, searchTerm, categoryId, type, adId);
        cq.where(predicates.toArray(new Predicate[0]));

        // Add sorting
        if (sortBy != null) {
            Path<?> sortPath = ad.get(sortBy);
            if ("desc".equalsIgnoreCase(order)) {
                cq.orderBy(cb.desc(sortPath));
            } else {
                cq.orderBy(cb.asc(sortPath));
            }
        }

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

    private List<Predicate> buildPredicates(CriteriaBuilder cb, Root<Ad> root, boolean isActive, String userId, String searchTerm,
                                            Long categoryId, AdType type, Long adId) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("active"), isActive));

        if (userId != null) {
            predicates.add(cb.equal(cb.lower(root.get("swbUser").get("id")), userId.toLowerCase()));
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

    @SuppressWarnings("PMD.UseObjectForClearerAPI")
    public Page<AdTO> searchAdsOld(final String userId, final String searchTerm, final Long categoryId, final AdType type, final String sortBy, final String order,
            final Pageable pageable, final Long adId,
            final boolean isActive) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Ad> query = builder.createQuery(Ad.class);

        final Root<Ad> root = query.from(Ad.class);
        final Path<Long> pathSwbUserId = root.get("swbUser").get("id");
        final Path<Long> pathCategoryId = root.get("adCategory").get("id");
        final Path<AdType> pathAdType = root.get("adType");
        final Path<Boolean> pathActive = root.get("active");
        final Path<String> pathTitle = root.get("title");
        final Path<String> pathDescription = root.get("description");
        final Path<Double> pathAdId = root.get("id");

        /**
         * Search and Filter
         */
        final List<Predicate> predicates = new ArrayList<>();

        // active = false ist für den User wie gelöscht
        final Predicate filterActive = builder.equal(pathActive, isActive);
        predicates.add(filterActive);

        if (userId != null) {
            final Predicate filterUser = builder.equal(pathSwbUserId, userId);
            predicates.add(filterUser);
        }
        if (categoryId != null) {
            final Predicate filterCategory = builder.equal(pathCategoryId, categoryId);
            predicates.add(filterCategory);
        }
        if (type != null) {
            final Predicate filterType = builder.equal(pathAdType, type);
            predicates.add(filterType);
        }
        if (searchTerm != null) {
            final Predicate searchTitle = builder.like(builder.lower(pathTitle), "%" + searchTerm.toLowerCase(Locale.GERMAN) + "%");
            final Predicate searchDescription = builder.like(builder.lower(pathDescription), "%" + searchTerm.toLowerCase(Locale.GERMAN) + "%");
            predicates.add(builder.or(searchTitle, searchDescription));
        }
        if (adId != null) {
            final Predicate filterAdId = builder.equal(pathAdId, adId);
            predicates.add(filterAdId);
        }

        final Predicate[] finalPredicates = predicates.toArray(new Predicate[0]);

        query.select(root);
        query.where(builder.and(finalPredicates));

        /**
         * Sort and Order
         */
        Expression<Object> sortExpression = root.get(sortBy);

        if (PRICE_STRING.equals(sortBy)) {

            // Sonderbehandlung bei Sortierung nach Preis.
            final Expression<Integer> se2 = root.get(sortBy);
            sortExpression = (builder.selectCase()
                    .when(builder.equal(sortExpression, 0), -1) // 0:  Zu verschenken
                    .when(builder.greaterThan(se2, 0), sortExpression) // >0: Festpreis
                    .when(builder.lessThan(se2, 0), builder.neg(se2)) // <0: Verhandelbar
            );
        }

        if (ORDER_ASC.equals(order)) {
            query.orderBy(builder.asc(sortExpression));
        } else if (ORDER_DESC.equals(order)) {
            query.orderBy(builder.desc(sortExpression));
        }

        /**
         * Pagination
         */
        final TypedQuery<Ad> adsQuery = entityManager.createQuery(query);
        adsQuery.setFirstResult((int) pageable.getOffset());
        adsQuery.setMaxResults(pageable.getPageSize());

        final List<AdTO> resultList = adsQuery.getResultList().stream().map(mapper::toAdTO).collect(Collectors.toList());

        final CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<Ad> countRoot = query.from(Ad.class);
        countQuery.select(builder.count(countRoot));
        countQuery.where(builder.and(finalPredicates));

        //inal Long totalRows = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(resultList, pageable, 10);
    }
}
