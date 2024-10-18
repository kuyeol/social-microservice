package org.springframework.samples.petclinic.owner;

import org.springframework.data.repository.Repository;

public interface PetTypeRepository extends Repository<PetType, Integer> {
  void save(String s);
}