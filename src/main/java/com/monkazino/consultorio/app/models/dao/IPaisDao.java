package com.monkazino.consultorio.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.monkazino.consultorio.app.models.entity.PaisEntity;

public interface IPaisDao extends PagingAndSortingRepository<PaisEntity, Long> {
	
	@Query("select p from PaisEntity p left join fetch p.departamentos d where p.id = :pais")
	public PaisEntity fetchByIdWithDepartamentos(@Param("pais") Long pais);
	
	@Query("select count(1) from PaisEntity p where p.codigo = :codigo")
	public int consultarCountPaisByCodigo(@Param("codigo") String codigo);

	@Query("select count(1) from PaisEntity p where p.codigo = :codigo and p.pais <> :pais")
	public int consultarCountPaisByCodigoPais(@Param("codigo") String codigo, @Param("pais") Long pais);

}
