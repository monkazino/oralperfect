package com.monkazino.consultorio.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.monkazino.consultorio.app.models.entity.ParametroPersonaEntity;

public interface IParametroPersonaDao extends PagingAndSortingRepository<ParametroPersonaEntity, Long> {
	
	@Query("select p from ParametroPersonaEntity p where p.tipoParametroPersonaEntity.codigo = :codigoTipoParametroPersona and p.estado = :estado")
	public List<ParametroPersonaEntity> consultarParametrosPersonaTipoParametroPersona(@Param("codigoTipoParametroPersona") String codigoTipoParametroPersona, @Param("estado") String estado);

	@Query("select count(1) from ParametroPersonaEntity p where p.codigo = :codigo and p.tipoParametroPersonaEntity.tipoParametroPersona = :tipoParametroPersona")
	public int consultarCountParametroPersonaByCodigoTipoParametroPersona(@Param("codigo") String codigo, @Param("tipoParametroPersona") Long tipoParametroPersona);

	@Query("select count(1) from ParametroPersonaEntity p where p.codigo = :codigo and p.parametroPersona <> :parametroPersona and p.tipoParametroPersonaEntity.tipoParametroPersona = :tipoParametroPersona")
	public int consultarCountParametroPersonaByCodigoParametroPersonaTipoParametroPersona(@Param("codigo") String codigo, @Param("parametroPersona") Long parametroPersona, @Param("tipoParametroPersona") Long tipoParametroPersona);

}
