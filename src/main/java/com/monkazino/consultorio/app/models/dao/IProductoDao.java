package com.monkazino.consultorio.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.monkazino.consultorio.app.models.entity.DocumentoInventarioEntity;
import com.monkazino.consultorio.app.models.entity.ProductoEntity;

public interface IProductoDao extends PagingAndSortingRepository<ProductoEntity, Long> {
	
	@Query("select count(1) from ProductoEntity p where p.codigo = :codigo")
	public int consultarCountProductoByCodigo(@Param("codigo") String codigo);

	@Query("select count(1) from ProductoEntity p where p.codigo = :codigo and p.producto <> :producto")
	public int consultarCountProductoByCodigoProducto(@Param("codigo") String codigo, @Param("producto") Long producto);
	
	@Query("select p from ProductoEntity p where p.descripcion like %?1%")
	public List<ProductoEntity> findByNombre(String term);

	//public List<ProductoEntity> findByNombreLikeIgnoreCase(String term);
	
	@Query("select p from ProductoEntity p join fetch p.paramTipoProducto tp where p.descripcion like %?1%")
	public List<ProductoEntity> fetchByDescripcionLike(String term);

}
