package com.monkazino.consultorio.app.controllers;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

@Controller
@SessionAttributes("barrioEntity")
public class BarrioController {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IBarrioService barrioService;

	@Autowired
	private ILocalidadService localidadService;

	@GetMapping("/parametrizacionGeografica/formBarrio/localidad/{localidad}")
	public String crear(@PathVariable(value = "localidad") Long localidad, Map<String, Object> model,
			RedirectAttributes flash) {

		LocalidadEntity localidadEntity = localidadService.findOne(localidad);

		if (localidadEntity == null) {
			flash.addFlashAttribute("error", "La barrio no existe en la base de datos");
			return "redirect:/parametrizacionGeografica/listarLocalidad";
		}

		BarrioEntity barrioEntity= new BarrioEntity();
		barrioEntity.setLocalidadEntity(localidadEntity);
		
		model.put("barrioEntity", barrioEntity);
		model.put("lblTituloFormBarrio", "Barrio");
		model.put("lblBotonGuardar", "Guardar");
		return "parametrizacionGeografica/formBarrio";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/parametrizacionGeografica/formBarrio/barrio/{id}")
	public String editarBarrio(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		BarrioEntity barrioEntity = null;

		if (id > 0) {
			barrioEntity = barrioService.findOne(id);
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
		model.put("lblBotonGuardar", "Guardar");
		return "parametrizacionGeografica/formBarrio";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/parametrizacionGeografica/formBarrio", method = RequestMethod.POST)
	public String guardarBarrio(@Valid BarrioEntity barrioEntity, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Barrio");
			model.addAttribute("lblBotonGuardar", "Guardar");
			return "parametrizacionGeografica/formBarrio";
		}

		String mensajeFlash = (barrioEntity.getBarrio() != null) ? "Barrio editado con éxito!" : "Barrio creado con éxito!";

		barrioService.save(barrioEntity);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/parametrizacionGeografica/listBarriosLocalidad/" + barrioEntity.getLocalidadEntity().getLocalidad();
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarBarrio/{id}")
	public String eliminarBarrio(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		BarrioEntity barrioEntity = null;
		Long localidad = 0L; 
		if (id > 0) {			
			barrioEntity = barrioService.findOne(id);
			if (barrioEntity == null) {
				flash.addFlashAttribute("error", "El ID del barrio no existe en la BBDD!");
				return "redirect:/parametrizacionGeografica/listLocalidadesCiudad";
			}
			localidad = barrioEntity.getLocalidadEntity().getLocalidad();
			barrioService.delete(id);
			flash.addFlashAttribute("success", "Barrio eliminado con éxito!");
		}
		return "redirect:/parametrizacionGeografica/listBarriosLocalidad/" + localidad;
	}
	
	private boolean hasRole(String role) {
		
		SecurityContext context = SecurityContextHolder.getContext();
		
		if(context == null) {
			return false;
		}
		
		Authentication auth = context.getAuthentication();
		
		if(auth == null) {
			return false;
		}
		
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		return authorities.contains(new SimpleGrantedAuthority(role));
		
		/*
		 * for(GrantedAuthority authority: authorities) {
			if(role.equals(authority.getAuthority())) {
				logger.info("Hola usuario ".concat(auth.getName()).concat(" tu role es: ".concat(authority.getAuthority())));
				return true;
			}
		}
		
		return false;
		*/
		
	}
}

