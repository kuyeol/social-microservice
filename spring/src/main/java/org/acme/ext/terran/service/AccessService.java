package org.acme.ext.terran.service;

import jakarta.persistence.EntityManager;
import java.lang.reflect.InvocationTargetException;
import org.acme.ext.terran.entity.AccessController;
import org.acme.ext.terran.entity.Barracks;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AccessService extends AccessController
{

  protected AccessService(EntityManager em)
  {
    super( em );
  }


  static EntityManager ems;


  public static void main(String[] args)
    throws InvocationTargetException, NoSuchMethodException, InstantiationException,
           IllegalAccessException
  {
    AccessService as = new AccessService( ems );

    Class d  = as.getUnitClass( AccessController.PB );
    Class d2 = as.getUnitClass( AccessController.BA );

    as.intance( " SDF" );
    Object dsf = as.getPr();
    System.out.println( dsf + "SDFSDFSDFDSF" );
    System.out.println( as.newIns() + "afsdf" );

    System.out.println( d.getSimpleName() );
    System.out.println( d2.getSimpleName() );
    //as.stage();
    Barracks B = ( Barracks )as.test( AccessController.BA );
    B.setName( "DFDF" );
    System.out.println( B );
    Barracks B1 = ( Barracks )as.test( "barracks" );
    B1.setName( "asdf" );
    B1.setPublicstring( "ASDFSDA" );

    Object FDF = as.test( AccessController.PB );
    System.out.println( FDF + "ASDFASDF" );
  }


  @Transactional
  public void stage()
    throws InvocationTargetException, NoSuchMethodException, InstantiationException,
           IllegalAccessException
  {
    Object S = test( "sss" );
    System.out.println( S.toString() + "dd" );
  }


  @Transactional
  public Object seach()
  {
    Class  c = getUnitClass( "privbarrack" );
    Object o = find( 1 , c );
    return o;
  }


  private void dd()
  {
    getUnitClass( "" );
  }


}
