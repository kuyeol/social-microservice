package org.acme.ext.terran.service;

import jakarta.persistence.EntityManager;
import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.acme.core.database.DataAccess;
import org.acme.ext.terran.entity.Barracks;
import org.acme.ext.terran.entity.CommandCenter;
import org.springframework.stereotype.Component;

@Component
class TerranDataAccess extends DataAccess
{

  private static final ConcurrentMap<Object, Class<?>> entityMap = new ConcurrentHashMap<>();

  TerranDataAccess(EntityManager em)
  {
    super( em );
    init();
  }

  final void init()
  {
    registerEntity( Barracks.class );
    registerEntity( CommandCenter.class );
  }


  public Class getUnitClass(String name)
  {

    return entityMap.get( name );
  }

  protected String getUnitName(String name)
  {
    return entityMap.get( name ).getSimpleName().toLowerCase();
  }


  public final void registerEntity(Class<?> cl)
  {
    entityMap.put( cl.getSimpleName().toLowerCase() , cl );
  }





}
