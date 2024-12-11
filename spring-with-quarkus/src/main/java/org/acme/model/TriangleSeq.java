package org.acme.model;

public class TriangleSeq implements IntSeq{


    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public int next() {
        return 0;
    }
}
