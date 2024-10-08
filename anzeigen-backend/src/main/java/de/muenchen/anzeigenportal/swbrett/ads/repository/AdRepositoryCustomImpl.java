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
import java.util.stream.Collectors;

@Repository
public class AdRepositoryCustomImpl implements AdRepositoryCustom {

    public static final String ORDER_ASC = "asc";
    public static final String ORDER_DESC = "desc";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AdMapper mapper;

    @Override
    public Page<AdTO> searchActiveAds(String userId, String searchTerm, Long categoryId, AdType type, String sortBy, String order, Pageable pageable,
            Long adId) {
        return searchAds(userId, searchTerm, categoryId, type, sortBy, order, pageable, adId, true);
    }

    @Override
    @PreAuthorize("hasAuthority(T(de.muenchen.intranet.sbrett.security.AuthoritiesEnum).BACKEND_READ_THEENTITY.name())")
    public Page<AdTO> searchDeactivatedAds(String userId, String searchTerm, Long categoryId, AdType type, String sortBy, String order, Pageable pageable,
            Long adId) {
        return searchAds(userId, searchTerm, categoryId, type, sortBy, order, pageable, adId, false);
    }

    public Page<AdTO> searchAds(String userId, String searchTerm, Long categoryId, AdType type, String sortBy, String order, Pageable pageable, Long adId,
            boolean isActive) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ad> query = builder.createQuery(Ad.class);

        Root<Ad> root = query.from(Ad.class);
        Path<Long> pathSwbUserId = root.get("swbUser").get("id");
        Path<Long> pathCategoryId = root.get("adCategory").get("id");
        Path<AdType> pathAdType = root.get("adType");
        Path<Boolean> pathActive = root.get("active");
        Path<String> pathTitle = root.get("title");
        Path<String> pathDescription = root.get("description");
        Path<Double> pathAdId = root.get("id");

        /**
         * Search and Filter
         */
        List<Predicate> predicates = new ArrayList<>();

        // active = false ist für den User wie gelöscht
        Predicate filterActive = builder.equal(pathActive, isActive);
        predicates.add(filterActive);

        if (userId != null) {
            Predicate filterUser = builder.equal(pathSwbUserId, userId);
            predicates.add(filterUser);
        }
        if (categoryId != null) {
            Predicate filterCategory = builder.equal(pathCategoryId, categoryId);
            predicates.add(filterCategory);
        }
        if (type != null) {
            Predicate filterType = builder.equal(pathAdType, type);
            predicates.add(filterType);
        }
        if (searchTerm != null) {
            Predicate searchTitle = builder.like(builder.lower(pathTitle), "%" + searchTerm.toLowerCase() + "%");
            Predicate searchDescription = builder.like(builder.lower(pathDescription), "%" + searchTerm.toLowerCase() + "%");
            predicates.add(builder.or(searchTitle, searchDescription));
        }
        if (adId != null) {
            Predicate filterAdId = builder.equal(pathAdId, adId);
            predicates.add(filterAdId);
        }

        Predicate[] finalPredicates = predicates.toArray(new Predicate[predicates.size()]);

        query.select(root);
        query.where(builder.and(finalPredicates));

        /**
         * Sort and Order
         */
        Expression<Object> sortExpression = root.get(sortBy);

        if ("price".contentEquals(sortBy)) {

            // Sonderbehandlung bei Sortierung nach Preis.
            Expression<Integer> se2 = root.get(sortBy);
            sortExpression = (builder.selectCase()
                    .when(builder.equal(sortExpression, 0), -1) // 0:  Zu verschenken
                    .when(builder.greaterThan(se2, Integer.valueOf(0)), sortExpression) // >0: Festpreis
                    .when(builder.lessThan(se2, Integer.valueOf(0)), builder.neg(se2)) // <0: Verhandelbar
            );
        }

        if (order.equals(ORDER_ASC)) {
            query.orderBy(builder.asc(sortExpression));
        } else if (order.equals(ORDER_DESC)) {
            query.orderBy(builder.desc(sortExpression));
        }

        /**
         * Pagination
         */
        TypedQuery<Ad> adsQuery = entityManager.createQuery(query);
        adsQuery.setFirstResult((int) pageable.getOffset());
        adsQuery.setMaxResults(pageable.getPageSize());

        List<AdTO> resultList = adsQuery.getResultList().stream().map(ad -> mapper.toAdTO(ad)).collect(Collectors.toList());

        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        countQuery.where(builder.and(finalPredicates));
        countQuery.select(builder.count(countQuery.from(Ad.class)));

        Long totalRows = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(resultList, pageable, totalRows);
    }
}
