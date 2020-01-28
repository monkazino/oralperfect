package com.monkazino.consultorio.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.monkazino.consultorio.app.models.entity.TipoParametroPersonaEntity;

public interface ITipoParametroPersonaDao extends PagingAndSortingRepository<TipoParametroPersonaEntity, Long> {
	
	@Query("select tp from TipoParametroPersonaEntity tp left join fetch tp.parametrosPersona f where tp.id=:tipoParametroPersona")
	public TipoParametroPersonaEntity fetchByIdWithParametrosPersona(@Param("tipoParametroPersona") Long tipoParametroPersona);

	@Query("select count(1) from TipoParametroPersonaEntity p where p.codigo = :codigo")
	public int consultarCountTipoParametroPersonaByCodigo(@Param("codigo") String codigo);

	@Query("select count(1) from TipoParametroPersonaEntity p where p.codigo = :codigo and p.tipoParametroPersona <> :tipoParametroPersona")
	public int consultarCountTipoParametroPersonaByCodigoTipoParametroPersona(@Param("codigo") String codigo, @Param("tipoParametroPersona") Long tipoParametroPersona);

}
