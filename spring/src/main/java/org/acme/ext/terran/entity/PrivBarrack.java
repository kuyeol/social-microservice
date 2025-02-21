package org.acme.ext.terran.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.acme.EntityID;

@Entity
public class PrivBarrack implements EntityID
{

  @Id
  @Access( value = AccessType.PROPERTY )
  private int id;

  private String name;


  protected PrivBarrack()
  {

  }


  //
  @Override
  public String toString()
  {
    return " PRBARARR{" + "id=" + id + ", name='" + name + '\'' + ", publicstring='" +
           publicstring + '\'' + ", secret='" + secret + '\'' + ", age=" + age + '}';
  }


  public String getPublicstring()
  {
    return publicstring;
  }


  public void setPublicstring(String publicstring)
  {
    this.publicstring = publicstring;
  }


  public String getSecret()
  {
    return secret;
  }


  public void setSecret(String secret)
  {
    this.secret = secret;
  }


  public String publicstring;

  private String secret;


  public int getAge()
  {
    return age;
  }


  public void setAge(int age)
  {
    this.age = age;
  }


  private int age;


  public void setName(String name)
  {
    this.name = name;
  }


  @Override
  public int getId()
  {
    return id;
  }


  public String getName()
  {
    return name;
  }


  public void setId(int id)
  {
    this.id = id;
  }

  //@Override
  //public String toString()
  //{
  //    return "SpawningPool{" + "id=" + id + ", name='" + name + '\'' + '}';
  //}
}
