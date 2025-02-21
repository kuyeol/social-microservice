package org.acme.ext.terran.entity;

import jakarta.persistence.EntityManager;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.acme.core.database.DataAccess;
import org.springframework.stereotype.Component;

@Component
public abstract class AccessController extends DataAccess
{

  private static final ConcurrentMap<Object, Class<?>> entityMap = new ConcurrentHashMap<>();

  protected static final String PB = PrivBarrack.class.getSimpleName().toLowerCase();

  protected static final String BA = Barracks.class.getSimpleName().toLowerCase();

  AtomicInteger a = new AtomicInteger();


  protected AccessController(EntityManager em)
  {
    super( em );
    init();
  }


  private PrivBarrack P;


  public void intance(String S)
  {
    this.P = new PrivBarrack();
  }

  String e1;
  protected final void registerEntity(Class<?> cl)
  {

     entityMap.put( cl.getSimpleName().toLowerCase() , cl );
  }


  final void init()
  {
    registerEntity( PrivBarrack.class );
    registerEntity( Barracks.class );
  }


  protected Object test(String s) throws InstantiationException, IllegalAccessException
  {

    return getUnitClass( s ).newInstance();
  }


  protected Class getUnitClass(String name)
  {
    name = "barracks";
    return entityMap.get( name );
  }


  protected PrivBarrack newIns()
  {
    PrivBarrack pv = new PrivBarrack();

    return pv;
  }


  public Object getPr()
  {
    return P;
  }


}
