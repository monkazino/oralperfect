package com.monkazino.consultorio.app.controllers;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.monkazino.consultorio.app.general.enums.EstadoActivoInactivoEnum;
import com.monkazino.consultorio.app.general.util.ListaOpcionesEnumeradores;
import com.monkazino.consultorio.app.models.entity.ParametrizacionSistemaEntity;
import com.monkazino.consultorio.app.models.service.IParametrizacionSistemaService;
import com.monkazino.consultorio.app.util.paginator.PageRender;

@Controller
@SessionAttributes("parametrizacionSistemaEntity")
public class ParametrizacionSistemaController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IParametrizacionSistemaService parametrizacionSistemaService;

	private Map<String, String> listEstado;

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/parametrizacionSistema/formParametrizacionSistema")
	public String crearParametrizacionSistema(Map<String, Object> model) {
		ParametrizacionSistemaEntity parametrizacionSistemaEntity = new ParametrizacionSistemaEntity();
		parametrizacionSistemaEntity.setFechaCreacion(new Date());
		parametrizacionSistemaEntity.setEstado(EstadoActivoInactivoEnum.ACTIVO.getCodigo());
		inicializarVariablesParametrizacionSistema();
		model.put("parametrizacionSistemaEntity", parametrizacionSistemaEntity);
		model.put("listEstado", listEstado);
		model.put("lblTituloFormularioParametrizacionSistema", "Paremetrización Sistema");
		return "parametrizacionSistema/formParametrizacionSistema";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/parametrizacionSistema/formParametrizacionSistema/{parametrizacionSistema}")
	public String editarParametrizacionSistema(@PathVariable(value = "parametrizacionSistema") Long parametrizacionSistema, Map<String, Object> model, RedirectAttributes flash) {
		ParametrizacionSistemaEntity parametrizacionSistemaEntity = null;
		if (parametrizacionSistema > 0) {
			parametrizacionSistemaEntity = parametrizacionSistemaService.findOne(parametrizacionSistema);
			if (parametrizacionSistemaEntity == null) {
				flash.addFlashAttribute("error", "El ID del Paremetrización Sistema no existe en la BBDD!");
				return "redirect:/parametrizacionSistema/listParametrizacionSistema";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del Paremetrización Sistema no puede ser cero!");
			return "redirect:/parametrizacionSistema/listParametrizacionSistema";
		}
		inicializarVariablesParametrizacionSistema();
		model.put("parametrizacionSistemaEntity", parametrizacionSistemaEntity);
		model.put("listEstado", listEstado);
		model.put("lblTituloFormularioParametrizacionSistema", "Paremetrización Sistema");
		return "parametrizacionSistema/formParametrizacionSistema";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/parametrizacionSistema/formParametrizacionSistema", method = RequestMethod.POST)
	public String guardarParametrizacionSistema(@Valid ParametrizacionSistemaEntity parametrizacionSistemaEntity, BindingResult result, Map<String, Object> model, RedirectAttributes flash, SessionStatus status) {
		int countParametrizacionSistemaCodigo = 0;
		if (result.hasErrors()) {
			model.put("listEstado", listEstado);
			return "parametrizacionSistema/formParametrizacionSistema";
		}
		if (parametrizacionSistemaEntity.getParametrizacionSistema() == null) {
			countParametrizacionSistemaCodigo = parametrizacionSistemaService.consultarCountParametrizacionSistemaByCodigo(parametrizacionSistemaEntity.getCodigo().toUpperCase());
		}
		if (countParametrizacionSistemaCodigo == 0) {
			parametrizacionSistemaEntity.setCodigo(parametrizacionSistemaEntity.getCodigo().toUpperCase());
			parametrizacionSistemaEntity.setDescripcion(parametrizacionSistemaEntity.getDescripcion().toUpperCase());
			parametrizacionSistemaService.save(parametrizacionSistemaEntity);
			status.setComplete();
			flash.addFlashAttribute("success", "Registro almacenado correctamente");
			return "redirect:listParametrizacionSistema";
		} else {
			model.put("mensajeErrorParametrizacionSistema", "El código ya se encuentra registrado");
			return "parametrizacionSistema/formParametrizacionSistema";
		}
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarParametrizacionSistema/{parametrizacionSistema}")
	public String eliminarParametrizacionSistema(@PathVariable(value = "parametrizacionSistema") Long parametrizacionSistema, RedirectAttributes flash) {
		if (parametrizacionSistema > 0) {
			ParametrizacionSistemaEntity parametrizacionSistemaEntity = parametrizacionSistemaService.findOne(parametrizacionSistema);
			if (parametrizacionSistemaEntity != null) {
				parametrizacionSistemaService.delete(parametrizacionSistema);
				flash.addFlashAttribute("success", "Paremetrización Sistema eliminado con éxito");
			} else {
				flash.addFlashAttribute("error", "El ID del Paremetrización Sistema no existe en la BBDD!");
			}
		}
		return "redirect:/parametrizacionSistema/listParametrizacionSistema";
	}
	
	@RequestMapping(value = {"/parametrizacionSistema/listParametrizacionSistema"}, method = RequestMethod.GET)
	public String listParametrizacionSistema(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication,
			HttpServletRequest request) {

		if(authentication != null) {
			logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if(auth != null) {
			logger.info("Utilizando forma estática SecurityContextHolder.getContext().getAuthentication(): Usuario autenticado: ".concat(auth.getName()));
		}
		
		if(hasRole("ROLE_ADMIN")) {
			logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}
		
		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "");
		
		if(securityContext.isUserInRole("ROLE_ADMIN")) {
			logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}

		if(request.isUserInRole("ROLE_ADMIN")) {
			logger.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}	
		
		Pageable pageRequest = PageRequest.of(page, 4);

		Page<ParametrizacionSistemaEntity> parametrosSistemaEntity = parametrizacionSistemaService.findAll(pageRequest);

		PageRender<ParametrizacionSistemaEntity> pageRender = new PageRender<ParametrizacionSistemaEntity>("/parametrizacionSistema/listParametrizacionSistema", parametrosSistemaEntity);
		model.addAttribute("lblTituloAplicacion", "Monkazino Soft");
		model.addAttribute("lblTituloListadoParametrizacionSistema", "Listado de paremetrización sistema");
		model.addAttribute("parametrosSistemaEntity", parametrosSistemaEntity);
		model.addAttribute("page", pageRender);
		return "parametrizacionSistema/listParametrizacionSistema";
	}

	public void inicializarVariablesParametrizacionSistema() {
		listEstado = new HashMap<String, String>();
		listEstado= ListaOpcionesEnumeradores.getListEstado();
	}
	
	public Map<String, String> getListEstado() {
		return listEstado;
	}

	public void setListEstado(Map<String, String> listEstado) {
		this.listEstado = listEstado;
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
