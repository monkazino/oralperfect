package com.monkazino.consultorio.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.monkazino.consultorio.app.models.entity.BarrioEntity;
import com.monkazino.consultorio.app.models.entity.LocalidadEntity;
import com.monkazino.consultorio.app.models.service.IBarrioService;
import com.monkazino.consultorio.app.models.service.ILocalidadService;
import com.monkazino.consultorio.app.util.general.EstadoActivoInactivoEnum;

@Controller
@SessionAttributes("barrioEntity")
public class BarrioController {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IBarrioService barrioService;

	@Autowired
	private ILocalidadService localidadService;

	@GetMapping("/parametrizacionGeografica/formBarrio/localidad/{localidad}")
	public String crear(@PathVariable(value = "localidad") Long localidad, Map<String, Object> model, RedirectAttributes flash) {
		LocalidadEntity localidadEntity = localidadService.findOne(localidad);
		if (localidadEntity == null) {
			flash.addFlashAttribute("error", "La barrio no existe en la base de datos");
			return "redirect:/parametrizacionGeografica/listBarriosLocalidad";
		}
		BarrioEntity barrioEntity= new BarrioEntity();
		barrioEntity.setLocalidadEntity(localidadEntity);
		barrioEntity.setEstado(EstadoActivoInactivoEnum.ACTIVO.getCodigo());
		model.put("barrioEntity", barrioEntity);
		model.put("lblTituloFormBarrio", "Barrio");
		return "parametrizacionGeografica/formBarrio";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/parametrizacionGeografica/formBarrio/barrio/{localidad}")
	public String editarBarrio(@PathVariable(value = "localidad") Long localidad, Map<String, Object> model, RedirectAttributes flash) {
		BarrioEntity barrioEntity = null;
		if (localidad > 0) {
			barrioEntity = barrioService.findOne(localidad);
			if (barrioEntity == null) {
				flash.addFlashAttribute("error", "El ID de la barrio no existe en la BBDD!");
				return "redirect:/parametrizacionGeografica/listBarriosLocalidad";
			}
		} else {
			flash.addFlashAttribute("error", "El ID de la barrio no puede ser cero!");
			return "redirect:/parametrizacionGeografica/listBarriosLocalidad";
		}
		model.put("barrioEntity", barrioEntity);
		model.put("lblTituloFormBarrio", "Barrio");
		return "parametrizacionGeografica/formBarrio";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/parametrizacionGeografica/formBarrio", method = RequestMethod.POST)
	public String guardarBarrio(@Valid BarrioEntity barrioEntity, BindingResult result, Map<String, Object> model, RedirectAttributes flash, SessionStatus status) {
		int countBarrioCodigo = 0;
		if (result.hasErrors()) {
			return "parametrizacionGeografica/formBarrio";
		}
		if (barrioEntity.getBarrio() == null) {
			countBarrioCodigo = barrioService.consultarCountBarrioByCodigoLocalidad(barrioEntity.getCodigo().toUpperCase(), barrioEntity.getLocalidadEntity().getLocalidad());
		} else {
			countBarrioCodigo = barrioService.consultarCountBarrioByCodigoBarrioLocalidad(barrioEntity.getCodigo().toUpperCase(), barrioEntity.getBarrio(), barrioEntity.getLocalidadEntity().getLocalidad());
		}
		if (countBarrioCodigo == 0) {
			barrioEntity.setCodigo(barrioEntity.getCodigo().toUpperCase());
			barrioEntity.setDescripcion(barrioEntity.getDescripcion().toUpperCase());
			barrioService.save(barrioEntity);
			status.setComplete();
			flash.addFlashAttribute("success", "Registro almacenado correctamente");
			return "redirect:/parametrizacionGeografica/listBarriosLocalidad/" + barrioEntity.getLocalidadEntity().getLocalidad();
		} else {
			model.put("mensajeErrorBarrio", "El código ya se encuentra registrado");
			return "parametrizacionGeografica/formBarrio";
		}
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarBarrio/{barrio}")
	public String eliminarBarrio(@PathVariable(value = "barrio") Long barrio, RedirectAttributes flash) {
		BarrioEntity barrioEntity = null;
		Long localidad = 0L;
		if (barrio > 0) {
			barrioEntity = barrioService.findOne(barrio);
			if (barrioEntity != null) {
				localidad = barrioEntity.getLocalidadEntity().getLocalidad();
				barrioService.delete(barrio);
				flash.addFlashAttribute("success", "Barrio eliminado con éxito");
			} else {
				flash.addFlashAttribute("error", "El ID del barrio no existe en la BBDD!");
			} 
		}
		return "redirect:/parametrizacionGeografica/listBarriosLocalidad/" + localidad;
	}
}

