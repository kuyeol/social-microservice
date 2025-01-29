package org.acme.ext.terran.entity;

import jakarta.persistence.EntityManager;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.acme.core.database.DataAccess;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public abstract class AccessController extends DataAccess
{

  private static final ConcurrentMap<Object, Class<?>> entityMap = new ConcurrentHashMap<>();


  protected AccessController(EntityManager em)
  {
    super( em );
    init();
  }


  final void init()
  {
    registerEntity( PrivBarrack.class );
  }


  protected Class getUnitClass(String name)
  {

    return entityMap.get( name );
  }


  AtomicInteger a = new AtomicInteger();


  protected void test()
  {
    PrivBarrack v = new PrivBarrack();

    v.setId( a.getAndIncrement() );
    v.setName( "dasf" );
    save( v );
  }


  protected PrivBarrack newIns()
  {
    PrivBarrack pv = new PrivBarrack();

    return pv;
  }


  protected final void registerEntity(Class<?> cl)
  {
    entityMap.put( cl.getSimpleName().toLowerCase() , cl );
  }


}
