package com.monkazino.consultorio.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.monkazino.consultorio.app.models.entity.CiudadEntity;

public interface ICiudadDao extends PagingAndSortingRepository<CiudadEntity, Long> {
	
	@Query("select p from CiudadEntity p where p.departamentoEntity.departamento = :departamento")
	public List<CiudadEntity> consultarCiudadesDepartamento(
			@Param("departamento") Long departamento);
	
	@Query("select p from CiudadEntity p left join fetch p.localidades d where p.id=?1")
	public CiudadEntity fetchByIdWithLocalidades(Long id);

}
