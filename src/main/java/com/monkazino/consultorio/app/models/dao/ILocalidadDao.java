package com.monkazino.consultorio.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.monkazino.consultorio.app.models.entity.LocalidadEntity;

public interface ILocalidadDao extends PagingAndSortingRepository<LocalidadEntity, Long> {
	
	@Query("select p from LocalidadEntity p where p.ciudadEntity.ciudad = :ciudad")
	public List<LocalidadEntity> consultarLocalidadesCiudad(@Param("ciudad") Long ciudad);
	
	@Query("select p from LocalidadEntity p left join fetch p.barrios d where p.id = :localidad")
	public LocalidadEntity fetchByIdWithBarrios(@Param("localidad") Long localidad);

	@Query("select count(1) from LocalidadEntity p where p.codigo = :codigo and p.ciudadEntity.ciudad = :ciudad")
	public int consultarCountLocalidadByCodigoCiudad(@Param("codigo") String codigo, @Param("ciudad") Long ciudad);

	@Query("select count(1) from LocalidadEntity p where p.codigo = :codigo and p.localidad <> :localidad and p.ciudadEntity.ciudad = :ciudad")
	public int consultarCountLocalidadByCodigoLocalidadCiudad(@Param("codigo") String codigo, @Param("localidad") Long localidad, @Param("ciudad") Long ciudad);

}
