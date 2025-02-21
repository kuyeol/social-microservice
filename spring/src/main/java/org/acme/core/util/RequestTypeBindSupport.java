package org.acme.core.util;

import java.beans.PropertyEditorSupport;

public class RequestTypeBindSupport<T extends Enum<T>> extends PropertyEditorSupport
{

  private final Class<T> enumType;


  public RequestTypeBindSupport(Class<T> enumType)
  {
    this.enumType = enumType;
  }


  @Override
  public void setAsText(String text)
  {
    setValue( fromString( text ) );
  }


  public T fromString(String modelString)
  {
    try {
      return Enum.valueOf( enumType , modelString.toUpperCase() );
    } catch ( IllegalArgumentException e ) {
      throw new IllegalArgumentException(
        "Invalid value for " + enumType.getSimpleName() + ": " + modelString );
    }
  }


}
