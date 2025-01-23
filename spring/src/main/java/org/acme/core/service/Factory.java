package org.acme.core.service;

import org.acme.core.domain.Maker;
import org.acme.core.domain.NoteBook;

public interface Factory<T extends Maker>
{

    T getBrand();

    NoteBook<T> makeBook();

    NoteBook<T> ma(NoteBook t);


}
