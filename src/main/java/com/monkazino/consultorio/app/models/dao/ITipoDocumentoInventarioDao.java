package com.monkazino.consultorio.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.monkazino.consultorio.app.models.entity.TipoDocumentoInventarioEntity;

public interface ITipoDocumentoInventarioDao extends PagingAndSortingRepository<TipoDocumentoInventarioEntity, Long> {
	
	@Query("select count(1) from TipoDocumentoInventarioEntity p where p.codigo = :codigo")
	public int consultarCountTipoDocumentoInventarioByCodigo(@Param("codigo") String codigo);

	@Query("select count(1) from TipoDocumentoInventarioEntity p where p.codigo = :codigo and p.tipoDocumentoInventario <> :tipoDocumentoInventario")
	public int consultarCountTipoDocumentoInventarioByCodigoTipoDocumentoInventario(@Param("codigo") String codigo, @Param("tipoDocumentoInventario") Long tipoDocumentoInventario);

}
