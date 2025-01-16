package org.acme.core.model;

public abstract class Transfer {

    Typer typer;

    String id;

    Object object;


    public Transfer(String id) {
        this.id = id;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public abstract Transfer transferEntiry(Typer typer);


    public abstract Transfer transferEntiry(Object entityTyper);
}
