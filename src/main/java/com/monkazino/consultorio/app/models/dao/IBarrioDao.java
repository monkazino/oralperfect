package com.monkazino.consultorio.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.monkazino.consultorio.app.models.entity.BarrioEntity;

public interface IBarrioDao extends PagingAndSortingRepository<BarrioEntity, Long> {

	@Query("select p from BarrioEntity p where p.localidadEntity.localidad = :localidad")
	public List<BarrioEntity> consultarBarriosLocalidad(@Param("localidad") Long localidad);
	
	@Query("select count(1) from BarrioEntity p where p.codigo = :codigo and p.localidadEntity.localidad = :localidad")
	public int consultarCountBarrioByCodigoLocalidad(@Param("codigo") String codigo, @Param("localidad") Long localidad);

	@Query("select count(1) from BarrioEntity p where p.codigo = :codigo and p.barrio <> :barrio and p.localidadEntity.localidad = :localidad")
	public int consultarCountBarrioByCodigoBarrioLocalidad(@Param("codigo") String codigo, @Param("barrio") Long barrio, @Param("localidad") Long localidad);

}
