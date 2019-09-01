package com.monkazino.consultorio.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.monkazino.consultorio.app.models.entity.TipoParametroPersonaEntity;

public interface ITipoParametroPersonaDao extends PagingAndSortingRepository<TipoParametroPersonaEntity, Long> {
	
	@Query("select tp from TipoParametroPersonaEntity tp left join fetch tp.parametrosPersona f where tp.id=?1")
	public TipoParametroPersonaEntity fetchByIdWithParametrosPersona(Long id);

}
