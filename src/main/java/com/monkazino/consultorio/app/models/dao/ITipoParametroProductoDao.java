package com.monkazino.consultorio.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.monkazino.consultorio.app.models.entity.TipoParametroProductoEntity;

public interface ITipoParametroProductoDao extends PagingAndSortingRepository<TipoParametroProductoEntity, Long> {
	
	@Query("select tp from TipoParametroProductoEntity tp left join fetch tp.parametrosProducto f where tp.id=:tipoParametroProducto")
	public TipoParametroProductoEntity fetchByIdWithParametrosProducto(@Param("tipoParametroProducto") Long tipoParametroProducto);

	@Query("select count(1) from TipoParametroProductoEntity p where p.codigo = :codigo")
	public int consultarCountTipoParametroProductoByCodigo(@Param("codigo") String codigo);

	@Query("select count(1) from TipoParametroProductoEntity p where p.codigo = :codigo and p.tipoParametroProducto <> :tipoParametroProducto")
	public int consultarCountTipoParametroProductoByCodigoTipoParametroProducto(@Param("codigo") String codigo, @Param("tipoParametroProducto") Long tipoParametroProducto);

}
