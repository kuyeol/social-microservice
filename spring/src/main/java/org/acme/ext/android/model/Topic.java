package org.acme.ext.android.model;



public record Topic(String id,
                           String name,
                           String shortDescription,
                           String longDescription,
                           String url,
                           String imageUrl,
                           boolean followed) {
}
