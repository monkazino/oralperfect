package com.monkazino.consultorio.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.monkazino.consultorio.app.models.entity.ProveedorEntity;

public interface IProveedorDao  extends PagingAndSortingRepository<ProveedorEntity, Long> {

}
