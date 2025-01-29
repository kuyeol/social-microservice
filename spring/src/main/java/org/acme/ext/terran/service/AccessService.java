package org.acme.ext.terran.service;

import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.acme.ext.terran.entity.AccessController;
import org.acme.ext.terran.entity.PrivBarrack;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AccessService extends AccessController
{


  protected AccessService(EntityManager em)
  {
    super( em );
  }

AtomicInteger a = new AtomicInteger();
  @Transactional
  public void test()
  {
    PrivBarrack v = newIns();

    v.setId(a.getAndIncrement() );
    v.setName( "dasf" );
    save( v );
  }


}
