package com.monkazino.consultorio.app.controllers;

import java.util.Collection;
import java.util.Date;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.monkazino.consultorio.app.models.entity.TipoParametroPersonaEntity;
import com.monkazino.consultorio.app.models.service.ITipoParametroPersonaService;
import com.monkazino.consultorio.app.util.general.EstadoParametroEnum;
import com.monkazino.consultorio.app.util.paginator.PageRender;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.monkazino.consultorio.app.models.entity.TipoParametroPersonaEntity;
import com.monkazino.consultorio.app.models.service.ITipoParametroPersonaService;
import com.monkazino.consultorio.app.util.general.EstadoPacienteEnum;
import com.monkazino.consultorio.app.util.general.EstadoParametroEnum;
import com.monkazino.consultorio.app.util.paginator.PageRender;

@Controller
@SessionAttributes("tipoParametroPersonaEntity")
public class TipoParametroPersonaController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private ITipoParametroPersonaService tipoParametroPersonaService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/tipoParametroPersona/verTipoParametroPersona/{id}")
	public String verTipoParametroPersona(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		TipoParametroPersonaEntity tipoParametroPersonaEntity = tipoParametroPersonaService.fetchByIdWithParametrosPersona(id);
		if (tipoParametroPersonaEntity == null) {
			flash.addFlashAttribute("error", "El tipo parametro no existe en la base de datos");
			return "redirect:/tipoParametroPersona/listarTipoParametroPersona";
		}

		model.put("tipoParametroPersonaEntity", tipoParametroPersonaEntity);
		model.put("lblTituloDetalleTipoParametroPersona", "Detalle tipo parametro persona: " + tipoParametroPersonaEntity.getDescripcion());
		return "tipoParametroPersona/verTipoParametroPersona";
	}

	@RequestMapping(value = {"/tipoParametroPersona/listarTipoParametroPersona", "/"}, method = RequestMethod.GET)
	public String listarTipoParametroPersona(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
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

		Page<TipoParametroPersonaEntity> tipoParametrosPersona = tipoParametroPersonaService.findAll(pageRequest);

		PageRender<TipoParametroPersonaEntity> pageRender = new PageRender<TipoParametroPersonaEntity>("/tipoParametroPersona/listarTipoParametroPersona", tipoParametrosPersona);
		model.addAttribute("lblTituloAplicacion", "Monkazino Soft");
		model.addAttribute("lblTituloListadoTipoParametroPersona", "Listado de tipo parametros persona");
		model.addAttribute("tipoParametrosPersona", tipoParametrosPersona);
		model.addAttribute("page", pageRender);
		return "tipoParametroPersona/listarTipoParametroPersona";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/tipoParametroPersona/formTipoParametroPersona")
	public String crearTipoParametro(Map<String, Object> model) {
		TipoParametroPersonaEntity tipoParametroPersonaEntity = new TipoParametroPersonaEntity();
		tipoParametroPersonaEntity.setFechaCreacion(new Date());
		tipoParametroPersonaEntity.setEstado(EstadoParametroEnum.ACTIVO.getCodigo());
																																							
		model.put("tipoParametroPersonaEntity", tipoParametroPersonaEntity);
		model.put("lblTituloFormularioTipoParametroPersona", "Tipo Parametro Persona");
		model.put("lblBotonGuardar", "Guardar");
		return "tipoParametroPersona/formTipoParametroPersona";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/tipoParametroPersona/formTipoParametroPersona/{id}")
	public String editarTipoParametro(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		TipoParametroPersonaEntity tipoParametroPersonaEntity = null;

		if (id > 0) {
			tipoParametroPersonaEntity = tipoParametroPersonaService.findOne(id);
			if (tipoParametroPersonaEntity == null) {
				flash.addFlashAttribute("error", "El ID del tipo parametro no existe en la BBDD!");
				return "redirect:/tipoParametroPersona/listarTipoParametroPersona";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del tipo parametro no puede ser cero!");
			return "redirect:/tipoParametroPersona/listarTipoParametroPersona";
		}
		model.put("tipoParametroPersonaEntity", tipoParametroPersonaEntity);
		model.put("lblTituloFormularioTipoParametroPersona", "Tipo Parametro Persona");
		model.put("lblBotonGuardar", "Guardar");
		return "tipoParametroPersona/formTipoParametroPersona";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/tipoParametroPersona/formTipoParametroPersona", method = RequestMethod.POST)
	public String guardarTipoParametro(@Valid TipoParametroPersonaEntity tipoParametroPersonaEntity, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Tipo Parametro");
			model.addAttribute("lblBotonGuardar", "Guardar");
			return "tipoParametroPersona/formTipoParametroPersona";
		}

		String mensajeFlash = (tipoParametroPersonaEntity.getTipoParametro() != null) ? "Tipo Parametro editado con éxito!" : "Tipo Parametro creado con éxito!";

		tipoParametroPersonaService.save(tipoParametroPersonaEntity);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listarTipoParametroPersona";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarTipoParametroPersona/{id}")
	public String eliminarTipoParametro(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			tipoParametroPersonaService.delete(id);
			flash.addFlashAttribute("success", "Tipo Parametro eliminado con éxito!");
		}
		return "redirect:/tipoParametroPersona/listarTipoParametroPersona";
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
