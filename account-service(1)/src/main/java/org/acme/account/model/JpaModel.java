package org.acme.account.model;


public interface JpaModel<T> {
  T getEntity();
}