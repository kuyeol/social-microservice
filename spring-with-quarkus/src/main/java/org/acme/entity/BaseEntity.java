
package org.acme.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.acme.utils.ModelUtils;


@MappedSuperclass
public class BaseEntity implements Serializable {


	@Id
	@Column(name="ID", length = 36)
	@Access(AccessType.PROPERTY)
	protected String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = ModelUtils.generateId();
	}





}
