package com.giosoft.base.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGenericBaseRepo<E,ID> extends JpaRepository<E, ID> {

}
