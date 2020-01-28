package com.monkazino.consultorio.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.monkazino.consultorio.app.models.entity.CiudadEntity;

public interface ICiudadDao extends PagingAndSortingRepository<CiudadEntity, Long> {
	
	@Query("select p from CiudadEntity p where p.departamentoEntity.departamento = :departamento")
	public List<CiudadEntity> consultarCiudadesDepartamento(@Param("departamento") Long departamento);
	
	@Query("select p from CiudadEntity p left join fetch p.localidades d where p.id = :ciudad")
	public CiudadEntity fetchByIdWithLocalidades(@Param("ciudad") Long ciudad);
	
	@Query("select count(1) from CiudadEntity p where p.codigo = :codigo and p.departamentoEntity.departamento = :departamento")
	public int consultarCountCiudadByCodigoDepartamento(@Param("codigo") String codigo, @Param("departamento") Long departamento);

	@Query("select count(1) from CiudadEntity p where p.codigo = :codigo and p.ciudad <> :ciudad and p.departamentoEntity.departamento = :departamento")
	public int consultarCountCiudadByCodigoCiudadDepartamento(@Param("codigo") String codigo, @Param("ciudad") Long ciudad, @Param("departamento") Long departamento);

}
