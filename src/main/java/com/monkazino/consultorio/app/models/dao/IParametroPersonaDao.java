package com.monkazino.consultorio.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.monkazino.consultorio.app.models.entity.ParametroPersonaEntity;

public interface IParametroPersonaDao extends PagingAndSortingRepository<ParametroPersonaEntity, Long> {
	
	@Query("select p from ParametroPersonaEntity p where p.tipoParametroPersonaEntity.codigo = :codigoTipoParametro and p.estado = :estadoParametro")
	public List<ParametroPersonaEntity> consultarParametrosPersonaTipoParametro(
			@Param("codigoTipoParametro") String codigoTipoParametro,
			@Param("estadoParametro") String estadoParametro);

}