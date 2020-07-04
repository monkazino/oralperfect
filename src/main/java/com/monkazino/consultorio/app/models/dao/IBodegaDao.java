package com.monkazino.consultorio.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.monkazino.consultorio.app.models.entity.BodegaEntity;

public interface IBodegaDao extends PagingAndSortingRepository<BodegaEntity, Long> {
	
	@Query("select count(1) from BodegaEntity p where p.codigo = :codigo")
	public Long consultarCountBodegaByCodigo(@Param("codigo") String codigo);

	@Query("select count(1) from BodegaEntity p where p.codigo = :codigo and p.bodega <> :bodega")
	public Long consultarCountBodegaByCodigoBodega(@Param("codigo") String codigo, @Param("bodega") Long bodega);

}
