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

import com.monkazino.consultorio.app.models.entity.ParametroPersonaEntity;
import com.monkazino.consultorio.app.models.entity.TipoParametroPersonaEntity;
import com.monkazino.consultorio.app.models.service.IParametroPersonaService;
import com.monkazino.consultorio.app.models.service.ITipoParametroPersonaService;
import com.monkazino.consultorio.app.util.general.EstadoParametroEnum;
import com.monkazino.consultorio.app.util.paginator.PageRender;
import com.monkazino.consultorio.app.util.general.EstadoPacienteEnum;
import com.monkazino.consultorio.app.util.general.EstadoParametroEnum;
import com.monkazino.consultorio.app.util.paginator.PageRender;

@Controller
@SessionAttributes("parametroPersonaEntity")
public class ParametroPersonaController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IParametroPersonaService parametroPersonaService;

	@Autowired
	private ITipoParametroPersonaService tipoParametroPersonaService;

	@RequestMapping(value = {"/parametroPersona/listarParametroPersona"}, method = RequestMethod.GET)
	public String listarParametroPersona(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
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

		Page<ParametroPersonaEntity> parametrosPersona = parametroPersonaService.findAll(pageRequest);

		PageRender<ParametroPersonaEntity> pageRender = new PageRender<ParametroPersonaEntity>("/parametroPersona/listarParametroPersona", parametrosPersona);
		model.addAttribute("titulo", "Listado de parametrosPersona");
		model.addAttribute("parametrosPersona", parametrosPersona);
		model.addAttribute("page", pageRender);
		return "parametroPersona/listarParametroPersona";
	}
	
	@GetMapping("/tipoParametroPersona/formParametroPersona/tipoParametroPersona/{tipoParametro}")
	public String crear(@PathVariable(value = "tipoParametro") Long tipoParametro, Map<String, Object> model,
			RedirectAttributes flash) {

		TipoParametroPersonaEntity tipoParametroPersonaEntity = tipoParametroPersonaService.findOne(tipoParametro);

		if (tipoParametroPersonaEntity == null) {
			flash.addFlashAttribute("error", "El tipo parametro no existe en la base de datos");
			return "redirect:/tipoParametroPersona/listarTipoParametroPersona";
		}

		ParametroPersonaEntity parametroPersonaEntity= new ParametroPersonaEntity();
		parametroPersonaEntity.setTipoParametroPersonaEntity(tipoParametroPersonaEntity);
		parametroPersonaEntity.setFechaCreacion(new Date());
		parametroPersonaEntity.setEstado(EstadoParametroEnum.ACTIVO.getCodigo());
		
		model.put("parametroPersonaEntity", parametroPersonaEntity);
		model.put("lblTituloFormularioParametroPersona", "Parametro Persona");
		model.put("lblBotonGuardar", "Guardar");
		return "tipoParametroPersona/formParametroPersona";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/tipoParametroPersona/formParametroPersona/parametroPersona/{id}")
	public String editarParametro(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		ParametroPersonaEntity parametroPersonaEntity = null;

		if (id > 0) {
			parametroPersonaEntity = parametroPersonaService.findOne(id);
			if (parametroPersonaEntity == null) {
				flash.addFlashAttribute("error", "El ID del parametro no existe en la BBDD!");
				return "redirectparametroPersona/listarParametroPersona";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del parametro no puede ser cero!");
			return "redirect:/parametroPersona/listarParametroPersona";
		}
		model.put("parametroPersonaEntity", parametroPersonaEntity);
		model.put("lblTituloFormularioParametroPersona", "Parametro Persona");
		model.put("lblBotonGuardar", "Guardar");
		return "tipoParametroPersona/formParametroPersona";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/tipoParametroPersona/formParametroPersona", method = RequestMethod.POST)
	public String guardarParametro(@Valid ParametroPersonaEntity parametroPersonaEntity, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Parametro");
			model.addAttribute("lblBotonGuardar", "Guardar");
			return "tipoParametroPersona/formParametroPersona";
		}

		String mensajeFlash = (parametroPersonaEntity.getParametro() != null) ? "Parametro editado con éxito!" : "Parametro creado con éxito!";

		parametroPersonaService.save(parametroPersonaEntity);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/tipoParametroPersona/verTipoParametroPersona/" + parametroPersonaEntity.getTipoParametroPersonaEntity().getTipoParametro();
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarParametroPersona/{id}")
	public String eliminarParametro(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			
			parametroPersonaService.delete(id);
			flash.addFlashAttribute("success", "Parametro eliminado con éxito!");
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
