package com.monkazino.consultorio.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.monkazino.consultorio.app.models.entity.DocumentoInventarioEntity;
import com.monkazino.consultorio.app.models.entity.ProductoBodegaEntity;

public interface IProductoBodegaDao extends PagingAndSortingRepository<ProductoBodegaEntity, Long> {
	
	@Query("select p from ProductoBodegaEntity p where p.productoEntity.producto = :producto and p.bodegaEntity.bodega = :bodega")
	public ProductoBodegaEntity consultarByProductoBodega(@Param("producto") Long producto, @Param("bodega") Long bodega);

}
