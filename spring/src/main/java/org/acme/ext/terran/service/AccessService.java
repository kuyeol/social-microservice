package org.acme.ext.terran.service;

import jakarta.persistence.EntityManager;
import org.acme.ext.terran.entity.AccessController;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AccessService extends AccessController
{

  protected AccessService(EntityManager em)
  {
    super( em );
  }


  @Transactional
  public void stage()
  {
    test();
  }

  @Transactional
  public Object seach(){
    Class c= getUnitClass( "privbarrack" );
   Object o =  find( 1, c );
     return o;
  }


}
