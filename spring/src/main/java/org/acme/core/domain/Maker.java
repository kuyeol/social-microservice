package org.acme.core.domain;

public interface Maker<T>
{

    T get();

    NoteBook createProduct(T m);


}
