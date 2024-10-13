package org.acme.security.model.jpa.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "COMPONENT_CONFIG")
public class ComponentConfigEntity {


  @Id
  @Column(name = "ID", length = 36)
  @Access(AccessType.PROPERTY)
  protected String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "COMPONENT_ID")
  protected ComponentEntity component;

  @Column(name = "CONFIG_NAME")
  protected String configName;
  @Nationalized
  @Column(name = "CONFIG_VALUE")
  protected String configValue;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ComponentEntity getComponent() {
    return component;
  }

  public void setComponent(ComponentEntity component) {
    this.component = component;
  }

  public String getConfigName() {
    return configName;
  }

  public void setConfigName(String configName) {
    this.configName = configName;
  }

  public String getConfigValue() {
    return configValue;
  }

  public void setConfigValue(String configValue) {
    this.configValue = configValue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (!(o instanceof ComponentConfigEntity)) {
      return false;
    }

    ComponentConfigEntity that = (ComponentConfigEntity) o;

    if (!id.equals(that.getId())) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
