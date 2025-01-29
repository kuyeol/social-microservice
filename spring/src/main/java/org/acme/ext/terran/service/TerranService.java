package org.acme.ext.terran.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.acme.ext.terran.entity.Barracks;
import org.acme.ext.terran.entity.CommandCenter;
import org.acme.ext.terran.model.TerranModel;
import org.springframework.stereotype.Component;

@Component
public class TerranService
{

  //TODO: ENTITY TYPE && DTO IMPLEMENT
  private static TerranDataAccess T;

  private Class<?> enc;

  private final TerranDataAccess terranUnits;

  private static Class clazz;


  TerranService(TerranDataAccess terranUnits)
  {
    this.terranUnits = terranUnits;
  }


  static final AtomicInteger at = new AtomicInteger();


  private int idGet()
  {
    return at.incrementAndGet();
  }


  public Object findNewMethod(String select , int id)
  {

    Class c = terranUnits.getUnitClass( select );

    return terranUnits.setClazz( c ).find( id );
  }


  public Object EntityType(String select , int id)
  {

    try {

      clazz = TerranModel.toStr( select ).getClazz();
    } catch ( RuntimeException e ) {

      throw new IllegalArgumentException( e );
    }
    return terranUnits.setClazz( clazz ).find( id );
  }


  public List<Object> anyList(String select)
  {

    try {
      clazz = TerranModel.toStr( select ).getClazz();
    } catch ( RuntimeException e ) {

      throw new IllegalArgumentException( e );
    }

    return terranUnits.setClazz( clazz ).listAll();
  }


  public void setEntity(String select)
  {
    clazz = TerranModel.toStr( select ).getClazz();

    this.enc = clazz;
  }


  public Object findOfBarracks(int id)
  {
    return terranUnits.find( id , Barracks.class );
  }


  public Object findOfCommand(int id)
  {
    return terranUnits.find( id , CommandCenter.class );
  }


  public Object find(int id)
  {

    return terranUnits.find( id , clazz );
  }


  public <T> Object findById(int id)
  {
    return terranUnits.setClazz( Barracks.class ).find( id );
  }


  public void save(Barracks o)
  {
    create();
    o.setId( idGet() );
    terranUnits.save( o );
  }



  protected String create()
  {

    Barracks      marine = new Barracks();
    Barracks      fire   = new Barracks();

    marine.setId( idGet() );
    marine.setName( "Marine" );
    fire.setId( idGet() );
    fire.setName( "Fire" );

    terranUnits.save( marine );
    terranUnits.save( fire );

    return "";
  }


}
