package org.acme.model;

public class BandPass extends Filter {

    double lowCut;
    double highCut;

    public BandPass(double lowCut, double highCut) {
        this.highCut = highCut;
        this.lowCut = lowCut;
    }


}
