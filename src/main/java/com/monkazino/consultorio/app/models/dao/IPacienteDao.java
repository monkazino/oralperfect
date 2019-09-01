package com.monkazino.consultorio.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.monkazino.consultorio.app.models.entity.PacienteEntity;

public interface IPacienteDao  extends PagingAndSortingRepository<PacienteEntity, Long> {

}
