package com.monkazino.consultorio.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.monkazino.consultorio.app.models.entity.TipoParametroEntity;

public interface ITipoParametroDao extends PagingAndSortingRepository<TipoParametroEntity, Long> {
	
	@Query("select tp from TipoParametroEntity tp left join fetch tp.parametros f where tp.id=?1")
	public TipoParametroEntity fetchByIdWithParametros(Long id);

}
