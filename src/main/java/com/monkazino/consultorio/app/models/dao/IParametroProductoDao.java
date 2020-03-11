package com.monkazino.consultorio.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.monkazino.consultorio.app.models.entity.ParametroProductoEntity;

public interface IParametroProductoDao extends PagingAndSortingRepository<ParametroProductoEntity, Long> {
	
	@Query("select p from ParametroProductoEntity p where p.tipoParametroProductoEntity.codigo = :codigoTipoParametroProducto and p.estado = :estado")
	public List<ParametroProductoEntity> consultarParametrosProductoTipoParametroProducto(@Param("codigoTipoParametroProducto") String codigoTipoParametroProducto, @Param("estado") String estado);

	@Query("select count(1) from ParametroProductoEntity p where p.codigo = :codigo and p.tipoParametroProductoEntity.tipoParametroProducto = :tipoParametroProducto")
	public int consultarCountParametroProductoByCodigoTipoParametroProducto(@Param("codigo") String codigo, @Param("tipoParametroProducto") Long tipoParametroProducto);

	@Query("select count(1) from ParametroProductoEntity p where p.codigo = :codigo and p.parametroProducto <> :parametroProducto and p.tipoParametroProductoEntity.tipoParametroProducto = :tipoParametroProducto")
	public int consultarCountParametroProductoByCodigoParametroProductoTipoParametroProducto(@Param("codigo") String codigo, @Param("parametroProducto") Long parametroProducto, @Param("tipoParametroProducto") Long tipoParametroProducto);

}
