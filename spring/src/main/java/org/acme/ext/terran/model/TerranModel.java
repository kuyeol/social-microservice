package org.acme.ext.terran.model;

import org.acme.ext.terran.entity.Barracks;
import org.acme.ext.terran.entity.CommandCenter;

public enum TerranModel
{
  BARRACKS( Barracks.class ), COMMANDCENTER( CommandCenter.class );

  private final Class<?> clazz;


  TerranModel(Class<?> clazz)
  {
    this.clazz = clazz;
  }


  public Class<?> getClazz()
  {
    return clazz;
  }


  public String lowToString(TerranModel type)
  {

    return type.toString().toLowerCase();
  }


  public static TerranModel toStr(final String s)
  {
    String toStr = s.toLowerCase();

    switch ( toStr ) {
      case "barracks" -> {
        return TerranModel.BARRACKS;
      }
      case "commandcenter" -> {
        return TerranModel.COMMANDCENTER;
      }
      default -> {
        return null;
      }
    }
  }
}
