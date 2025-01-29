package org.acme.ext.terran.entity;

import jakarta.persistence.EntityManager;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.acme.core.database.DataAccess;
import org.springframework.stereotype.Component;



@Component
public class AccessController extends DataAccess
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


  public Class getUnitClass(String name)
  {

    return entityMap.get( name );
  }

public PrivBarrack newIns(){
    PrivBarrack pv = new PrivBarrack();

    return pv;
}
  public final void registerEntity(Class<?> cl)
  {
    entityMap.put( cl.getSimpleName().toLowerCase() , cl );
  }


}
