package de.muenchen.anzeigenportal.swbrett.ads.repository;

import de.muenchen.anzeigenportal.swbrett.ads.model.Ad;
import de.muenchen.anzeigenportal.swbrett.ads.model.AdTO;
import de.muenchen.anzeigenportal.swbrett.ads.model.AdType;
import de.muenchen.anzeigenportal.swbrett.ads.service.AdMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@SuppressWarnings({"PMD.CouplingBetweenObjects", "PMD.UselessParentheses"})
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
    @SuppressWarnings({"PMD.UseObjectWithCaseConventions", "PMD.UseObjectForClearerAPI"})
    public Page<AdTO> searchActiveAds(final String userId, final String searchTerm, final Long categoryId, final AdType type, final String sortBy, final String order, final Pageable pageable,
                                      final Long adId) {
        return searchAds(userId, searchTerm, categoryId, type, sortBy, order, pageable, adId, true);
    }

    @Override
    @SuppressWarnings({"PMD.UseObjectWithCaseConventions", "PMD.UseObjectForClearerAPI"})
    @PreAuthorize("hasAuthority(T(de.muenchen.intranet.sbrett.security.AuthoritiesEnum).BACKEND_READ_THEENTITY.name())")
    public Page<AdTO> searchDeactivatedAds(final String userId, final String searchTerm, final Long categoryId, final  AdType type, final String sortBy, final String order, final Pageable pageable,
                                           final Long adId) {
        return searchAds(userId, searchTerm, categoryId, type, sortBy, order, pageable, adId, false);
    }

    @SuppressWarnings("PMD.UseObjectForClearerAPI")
    public Page<AdTO> searchAds(final String userId, final String searchTerm, final Long categoryId, final AdType type, final String sortBy, final String order, final Pageable pageable, final Long adId,
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

        final Predicate[] finalPredicates = predicates.toArray(new Predicate[predicates.size()]);

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
        countQuery.where(builder.and(finalPredicates));
        countQuery.select(builder.count(countQuery.from(Ad.class)));

        final Long totalRows = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(resultList, pageable, totalRows);
    }
}
