package com.monkazino.consultorio.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.monkazino.consultorio.app.models.entity.ParametroEntity;

public interface IParametroDao extends PagingAndSortingRepository<ParametroEntity, Long> {

}
