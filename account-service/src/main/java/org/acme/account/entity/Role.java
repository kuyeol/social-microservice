package org.acme.account.entity;


import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;
import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;


@Entity
@Table(name="roles")
@RegisterForReflection
public class Role extends PanacheEntityBase
{

  @NotNull
  @Id
  @Column(length = 50)
  public String name;

  public Role() {
    //empty
  }

  public Role(String name) {
    //for jsonb
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Role)) {
      return false;
    }
    return Objects.equals(name, ((Role) o).name);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode( name);
  }

  @Override
  public String toString() {
    return "Role{" + "name='" + name + '\'' + "}";
  }


}
