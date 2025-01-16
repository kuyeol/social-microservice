package org.acme.bridge;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.acme.util.Parameters;
import org.acme.util.Page;

public interface UngQuery<UngEntity>
{
    public <T> UngQuery<T> project(Class<T> type);
    public <T extends UngEntity> UngQuery<T> page(Page page);
    public <T extends UngEntity> UngQuery<T> page(int pageIndex, int pageSize);
    public <T extends UngEntity> UngQuery<T> nextPage();
    public <T extends UngEntity> UngQuery<T> previousPage();
    public <T extends UngEntity> UngQuery<T> firstPage();
    public <T extends UngEntity> UngQuery<T> lastPage();
    public boolean hasNextPage();
    public boolean hasPreviousPage();
    public int pageCount();
    public Page page();
    public <T extends UngEntity> UngQuery<T> range(int startIndex, int lastIndex);
    public <T extends UngEntity> UngQuery<T> withLock(LockModeType lockModeType);
    public <T extends UngEntity> UngQuery<T> withHint(String hintName, Object value);
    public <T extends UngEntity> UngQuery<T> filter(String filterName, Parameters parameters);
    public <T extends UngEntity> UngQuery<T> filter(String filterName, Map<String, Object> parameters);
    public <T extends UngEntity> UngQuery<T> filter(String filterName);
    public long count();
    public <T extends UngEntity> List<T> list();
    public <T extends UngEntity> Stream<T> stream();
    public <T extends UngEntity> T firstResult();
    public <T extends UngEntity> Optional<T> firstResultOptional();
    public <T extends UngEntity> T singleResult();
    public <T extends UngEntity> Optional<T> singleResultOptional();



}



