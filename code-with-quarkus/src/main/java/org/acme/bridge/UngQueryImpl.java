package org.acme.bridge;


import io.smallrye.mutiny.Uni;
import jakarta.persistence.LockModeType;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.acme.util.Parameters;
import org.hibernate.Session;
import org.acme.util.Page;


public class UngQueryImpl<UngEntity> implements UngQuery<UngEntity>
{


    private final DelegateQuery<UngEntity> delegateQuery;

    UngQueryImpl(Session em, String query, String originalQuery, String orderBy, Object paramsArrayOrMap) {
        this.delegateQuery = new DelegateQuery<UngEntity>(em, query, originalQuery, orderBy, paramsArrayOrMap);
    }
    protected UngQueryImpl(DelegateQuery<UngEntity> delegateQuery) {
        this.delegateQuery = delegateQuery;
    }


    @Override
    public <T> UngQuery<T> project(Class<T> type) {
        return new UngQueryImpl<>(delegateQuery.project(type));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends UngEntity> UngQuery<T> page(Page page) {
        delegateQuery.page(page);
        return (UngQuery<T>) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends UngEntity> UngQuery<T> page(int pageIndex, int pageSize) {
        delegateQuery.page(pageIndex, pageSize);
        return (UngQuery<T>) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends UngEntity> UngQuery<T> nextPage() {
        delegateQuery.nextPage();
        return (UngQuery<T>) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends UngEntity> UngQuery<T> previousPage() {
        delegateQuery.previousPage();
        return (UngQuery<T>) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends UngEntity> UngQuery<T> firstPage() {
        delegateQuery.firstPage();
        return (UngQuery<T>) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends UngEntity> UngQuery<T> lastPage() {
        delegateQuery.lastPage();
        return (UngQuery<T>) this;
    }

    @Override
    public boolean hasNextPage() {
        return delegateQuery.hasNextPage();
    }

    @Override
    public boolean hasPreviousPage() {
        return delegateQuery.hasPreviousPage();
    }

    @Override
    public int pageCount() {
        return delegateQuery.pageCount();
    }

    @Override
    public Page page() {
        return delegateQuery.page();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends UngEntity> UngQuery<T> range(int startIndex, int lastIndex) {
        delegateQuery.range(startIndex, lastIndex);
        return (UngQuery<T>) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends UngEntity> UngQuery<T> withLock(LockModeType lockModeType) {
        delegateQuery.withLock(lockModeType);
        return (UngQuery<T>) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends UngEntity> UngQuery<T> withHint(String hintName, Object value) {
        delegateQuery.withHint(hintName, value);
        return (UngQuery<T>) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends UngEntity> UngQuery<T> filter(String filterName, Parameters parameters) {
        delegateQuery.filter(filterName, parameters.map());
        return (UngQuery<T>) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends UngEntity> UngQuery<T> filter(String filterName, Map<String, Object> parameters) {
        delegateQuery.filter(filterName, parameters);
        return (UngQuery<T>) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends UngEntity> UngQuery<T> filter(String filterName) {
        delegateQuery.filter(filterName, Collections.emptyMap());
        return (UngQuery<T>) this;
    }

    // Results

    @Override
    public long count() {
        return delegateQuery.count();
    }

    @Override
    public <T extends UngEntity> List<T> list() {
        return delegateQuery.list();
    }

    @Override
    public <T extends UngEntity> Stream<T> stream() {
        return delegateQuery.stream();
    }

    @Override
    public <T extends UngEntity> T firstResult() {
        return delegateQuery.firstResult();
    }

    @Override
    public <T extends UngEntity> Optional<T> firstResultOptional() {
        return delegateQuery.firstResultOptional();
    }

    @Override
    public <T extends UngEntity> T singleResult() {
        return delegateQuery.singleResult();
    }

    @Override
    public <T extends UngEntity> Optional<T> singleResultOptional() {
        return delegateQuery.singleResultOptional();
    }

}
