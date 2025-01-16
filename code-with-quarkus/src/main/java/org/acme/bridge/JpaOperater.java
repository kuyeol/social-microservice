package org.acme.bridge;

import java.util.List;
import java.util.stream.Stream;
import org.hibernate.Session;

public class JpaOperater extends AbstractJpaOperations<UngQueryImpl<?>>
{

    public static final JpaOperater INSTANCE = new JpaOperater();


    @Override
    protected UngQueryImpl<?> createPanacheQuery(Session session, String query,
                                                 String originalQuery, String orderBy,
                                                 Object paramsArrayOrMap)
    {

        return new UngQueryImpl<>(session, query, originalQuery, orderBy, paramsArrayOrMap);
    }


    @Override
    public List<?> list(UngQueryImpl<?> query)
    {

        return query.list();
    }


    @Override
    public Stream<?> stream(UngQueryImpl<?> query)
    {

        return query.stream();
    }


}
