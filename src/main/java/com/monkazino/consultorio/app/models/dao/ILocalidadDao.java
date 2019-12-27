package com.monkazino.consultorio.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.monkazino.consultorio.app.models.entity.LocalidadEntity;

public interface ILocalidadDao extends PagingAndSortingRepository<LocalidadEntity, Long> {
	
	@Query("select p from LocalidadEntity p where p.ciudadEntity.ciudad = :ciudad")
	public List<LocalidadEntity> consultarLocalidadesCiudad(
			@Param("ciudad") Long ciudad);
	
	@Query("select p from LocalidadEntity p left join fetch p.barrios d where p.id=?1")
	public LocalidadEntity fetchByIdWithBarrios(Long id);

}
