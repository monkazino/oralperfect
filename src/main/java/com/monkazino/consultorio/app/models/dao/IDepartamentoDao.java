package com.monkazino.consultorio.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.monkazino.consultorio.app.models.entity.DepartamentoEntity;

public interface IDepartamentoDao extends PagingAndSortingRepository<DepartamentoEntity, Long> {
	
	@Query("select p from DepartamentoEntity p where p.paisEntity.pais = :pais")
	public List<DepartamentoEntity> consultarDepartamentosPais(
			@Param("pais") Long pais);
	
	@Query("select p from DepartamentoEntity p left join fetch p.ciudades d where p.id=?1")
	public DepartamentoEntity fetchByIdWithCiudades(Long id);


}
