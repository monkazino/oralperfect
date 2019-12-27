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

import com.monkazino.consultorio.app.models.entity.LocalidadEntity;
import com.monkazino.consultorio.app.models.entity.CiudadEntity;
import com.monkazino.consultorio.app.models.service.ILocalidadService;
import com.monkazino.consultorio.app.models.service.ICiudadService;

@Controller
@SessionAttributes("localidadEntity")
public class LocalidadController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private ILocalidadService localidadService;

	@Autowired
	private ICiudadService ciudadService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/parametrizacionGeografica/listBarriosLocalidad/{id}")
	public String listBarriosLocalidad(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		LocalidadEntity localidadEntity = localidadService.fetchByIdWithBarrios(id);
		if (localidadEntity == null) {
			flash.addFlashAttribute("error", "La localidad no existe en la base de datos");
			return "redirect:/parametrizacionGeografica/listBarriosLocalidad";
		}

		model.put("localidadEntity", localidadEntity);
		model.put("lblTituloDetalleLocalidad", "Detalle localidad: " + localidadEntity.getDescripcion());
		return "parametrizacionGeografica/listBarriosLocalidad";
	}

	@GetMapping("/parametrizacionGeografica/formLocalidad/ciudad/{ciudad}")
	public String crear(@PathVariable(value = "ciudad") Long ciudad, Map<String, Object> model,
			RedirectAttributes flash) {

		CiudadEntity ciudadEntity = ciudadService.findOne(ciudad);

		if (ciudadEntity == null) {
			flash.addFlashAttribute("error", "La localidad no existe en la base de datos");
			return "redirect:/parametrizacionGeografica/listarCiudad";
		}

		LocalidadEntity localidadEntity= new LocalidadEntity();
		localidadEntity.setCiudadEntity(ciudadEntity);
		
		model.put("localidadEntity", localidadEntity);
		model.put("lblTituloFormLocalidad", "Localidad");
		model.put("lblBotonGuardar", "Guardar");
		return "parametrizacionGeografica/formLocalidad";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/parametrizacionGeografica/formLocalidad/localidad/{id}")
	public String editarLocalidad(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		LocalidadEntity localidadEntity = null;

		if (id > 0) {
			localidadEntity = localidadService.findOne(id);
			if (localidadEntity == null) {
				flash.addFlashAttribute("error", "El ID de la localidad no existe en la BBDD!");
				return "redirect:/parametrizacionGeografica/listBarriosLocalidad";
			}
		} else {
			flash.addFlashAttribute("error", "El ID de la localidad no puede ser cero!");
			return "redirect:/parametrizacionGeografica/listBarriosLocalidad";
		}
		model.put("localidadEntity", localidadEntity);
		model.put("lblTituloFormLocalidad", "Localidad");
		model.put("lblBotonGuardar", "Guardar");
		return "parametrizacionGeografica/formLocalidad";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/parametrizacionGeografica/formLocalidad", method = RequestMethod.POST)
	public String guardarLocalidad(@Valid LocalidadEntity localidadEntity, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Localidad");
			model.addAttribute("lblBotonGuardar", "Guardar");
			return "parametrizacionGeografica/formLocalidad";
		}

		String mensajeFlash = (localidadEntity.getLocalidad() != null) ? "Localidad editado con éxito!" : "Localidad creado con éxito!";

		localidadService.save(localidadEntity);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/parametrizacionGeografica/listLocalidadesCiudad/" + localidadEntity.getCiudadEntity().getCiudad();
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarLocalidad/{id}")
	public String eliminarLocalidad(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		LocalidadEntity localidadEntity = null;
		Long ciudad = 0L; 
		if (id > 0) {			
			localidadEntity = localidadService.findOne(id);
			if (localidadEntity == null) {
				flash.addFlashAttribute("error", "El ID del localidad no existe en la BBDD!");
				return "redirect:/parametrizacionGeografica/listLocalidadesCiudad";
			}
			ciudad = localidadEntity.getCiudadEntity().getCiudad();
			localidadService.delete(id);
			flash.addFlashAttribute("success", "Localidad eliminado con éxito!");
		}
		return "redirect:/parametrizacionGeografica/listLocalidadesCiudad/" + ciudad;
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

