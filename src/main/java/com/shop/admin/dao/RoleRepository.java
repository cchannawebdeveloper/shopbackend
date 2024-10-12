package com.shop.admin.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shop.admin.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

}
