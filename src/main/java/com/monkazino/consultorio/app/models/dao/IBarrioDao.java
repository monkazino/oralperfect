package com.monkazino.consultorio.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.monkazino.consultorio.app.models.entity.BarrioEntity;

public interface IBarrioDao extends PagingAndSortingRepository<BarrioEntity, Long> {

	@Query("select p from BarrioEntity p where p.localidadEntity.localidad = :localidad")
	public List<BarrioEntity> consultarBarriosLocalidad(
			@Param("localidad") Long localidad);
}
