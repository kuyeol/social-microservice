package org.acme.entity.location;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Collection;
import java.util.LinkedHashSet;
import javax.persistence.Table;
import org.acme.utils.ModelUtils;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table
public class Venue {

    @Id
    @Column(name = "ID", length = 36)
    @Access(AccessType.PROPERTY)
    private String id;


    @Column(name = "VENUENAME")
    private String venueName;

    @Column(name = "size")
    private Long size;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy= "venue")
    @Fetch(FetchMode.SELECT)
    @BatchSize(size=20)
    private Collection<Hall> halls = new LinkedHashSet<>();

    public String getId() {
        return id;
    }

    public void setId() {
        this.id = ModelUtils.generateId();
    }

    public Collection<Hall> getHalls() {
        if (halls == null) {
            halls = new LinkedHashSet<>();
        }
        return halls;
    }

    public void setHalls(Collection<Hall> halls) {
        this.halls = halls;
    }


    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }


    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    @Override
    public String toString() {
        return "Venue{" +
            "venueName='" + venueName + '\'' +
            ", size=" + size +
            ", halls=" + halls +
            '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Venue)) return false;

        Venue that = (Venue) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }



}
