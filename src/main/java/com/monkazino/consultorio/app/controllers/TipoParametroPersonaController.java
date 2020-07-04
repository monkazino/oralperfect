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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.monkazino.consultorio.app.general.enums.EstadoActivoInactivoEnum;
import com.monkazino.consultorio.app.general.util.ListaOpcionesEnumeradores;
import com.monkazino.consultorio.app.models.entity.TipoParametroPersonaEntity;
import com.monkazino.consultorio.app.models.service.ITipoParametroPersonaService;
import com.monkazino.consultorio.app.util.paginator.PageRender;

@Controller
@SessionAttributes("tipoParametroPersonaEntity")
public class TipoParametroPersonaController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private ITipoParametroPersonaService tipoParametroPersonaService;
	
	private Map<String, String> listEstado;

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/parametrizacionSistema/tipoParametroPersona/formTipoParametroPersona")
	public String crearTipoParametroPersona(Map<String, Object> model) {
		TipoParametroPersonaEntity tipoParametroPersonaEntity = new TipoParametroPersonaEntity();
		tipoParametroPersonaEntity.setFechaCreacion(new Date());
		tipoParametroPersonaEntity.setEstado(EstadoActivoInactivoEnum.ACTIVO.getCodigo());
		inicializarVariablesTipoParametroPersona();
		model.put("tipoParametroPersonaEntity", tipoParametroPersonaEntity);
		model.put("listEstado", listEstado);
		model.put("lblTituloFormularioTipoParametroPersona", "Tipo Parametro Persona");
		return "parametrizacionSistema/tipoParametroPersona/formTipoParametroPersona";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/parametrizacionSistema/tipoParametroPersona/formTipoParametroPersona/{tipoParametroPersona}")
	public String editarTipoParametroPersona(@PathVariable(value = "tipoParametroPersona") Long tipoParametroPersona, Map<String, Object> model, RedirectAttributes flash) {
		TipoParametroPersonaEntity tipoParametroPersonaEntity = null;
		if (tipoParametroPersona > 0) {
			tipoParametroPersonaEntity = tipoParametroPersonaService.findOne(tipoParametroPersona);
			if (tipoParametroPersonaEntity == null) {
				flash.addFlashAttribute("error", "El ID del tipo parametro persona no existe en la BBDD!");
				return "redirect:/parametrizacionSistema/tipoParametroPersona/listTipoParametroPersona";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del tipo parametro persona no puede ser cero!");
			return "redirect:/parametrizacionSistema/tipoParametroPersona/listTipoParametroPersona";
		}
		inicializarVariablesTipoParametroPersona();
		model.put("tipoParametroPersonaEntity", tipoParametroPersonaEntity);
		model.put("listEstado", listEstado);
		model.put("lblTituloFormularioTipoParametroPersona", "Tipo Parametro Persona");
		return "parametrizacionSistema/tipoParametroPersona/formTipoParametroPersona";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/parametrizacionSistema/tipoParametroPersona/formTipoParametroPersona", method = RequestMethod.POST)
	public String guardarTipoParametroPersona(@Valid TipoParametroPersonaEntity tipoParametroPersonaEntity, BindingResult result, Map<String, Object> model, RedirectAttributes flash, SessionStatus status) {
		int countTipoParametroPersonaCodigo = 0;
		if (result.hasErrors()) {
			model.put("listEstado", listEstado);
			return "parametrizacionSistema/tipoParametroPersona/formTipoParametroPersona";
		}
		if (tipoParametroPersonaEntity.getTipoParametroPersona() == null) {
			countTipoParametroPersonaCodigo = tipoParametroPersonaService.consultarCountTipoParametroPersonaByCodigo(tipoParametroPersonaEntity.getCodigo().toUpperCase());
		} else {
			countTipoParametroPersonaCodigo = tipoParametroPersonaService.consultarCountTipoParametroPersonaByCodigoTipoParametroPersona(tipoParametroPersonaEntity.getCodigo().toUpperCase(), tipoParametroPersonaEntity.getTipoParametroPersona());
		}
		if (countTipoParametroPersonaCodigo == 0) {
			tipoParametroPersonaEntity.setCodigo(tipoParametroPersonaEntity.getCodigo().toUpperCase());
			tipoParametroPersonaEntity.setDescripcion(tipoParametroPersonaEntity.getDescripcion().toUpperCase());
			tipoParametroPersonaService.save(tipoParametroPersonaEntity);
			status.setComplete();
			flash.addFlashAttribute("success", "Registro almacenado correctamente");
			return "redirect:listTipoParametroPersona";
		} else {
			model.put("mensajeErrorTipoParametroPersona", "El código ya se encuentra registrado");
			return "parametrizacionSistema/tipoParametroPersona/formTipoParametroPersona";
		}
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarTipoParametroPersona/{tipoParametroPersona}")
	public String eliminarTipoParametroPersona(@PathVariable(value = "tipoParametroPersona") Long tipoParametroPersona, RedirectAttributes flash) {
		if (tipoParametroPersona > 0) {
			TipoParametroPersonaEntity tipoParametroPersonaEntity = tipoParametroPersonaService.fetchByIdWithParametrosPersona(tipoParametroPersona);
			if (tipoParametroPersonaEntity == null) {
				flash.addFlashAttribute("error", "El ID del tipo parametro persona no existe en la BBDD!");
			} else if (tipoParametroPersonaEntity.getParametrosPersona().isEmpty() || tipoParametroPersonaEntity.getParametrosPersona() == null) {
				tipoParametroPersonaService.delete(tipoParametroPersona);
				flash.addFlashAttribute("success", "Tipo Parametro Persona eliminado con éxito");
			} else {
				flash.addFlashAttribute("success", "Tipo Parametro Persona no se puede eliminar, tiene asociado parametros");
			}
		}
		return "redirect:/parametrizacionSistema/tipoParametroPersona/listTipoParametroPersona";
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/parametrizacionSistema/tipoParametroPersona/listParametroPersona/{parametroPersona}")
	public String listParametroPersona(@PathVariable(value = "parametroPersona") Long parametroPersona, Map<String, Object> model, RedirectAttributes flash) {
		TipoParametroPersonaEntity tipoParametroPersonaEntity = tipoParametroPersonaService.fetchByIdWithParametrosPersona(parametroPersona);
		if (tipoParametroPersonaEntity == null) {
			flash.addFlashAttribute("error", "El tipo parametro persona no existe en la base de datos");
			return "redirect:/parametrizacionSistema/tipoParametroPersona/listTipoParametroPersona";
		}
		model.put("tipoParametroPersonaEntity", tipoParametroPersonaEntity);
		model.put("lblTituloDetalleTipoParametroPersona", "Detalle tipo parametro persona: " + tipoParametroPersonaEntity.getDescripcion());
		return "parametrizacionSistema/tipoParametroPersona/listParametroPersona";
	}

	@RequestMapping(value = {"/parametrizacionSistema/tipoParametroPersona/listTipoParametroPersona"}, method = RequestMethod.GET)
	public String listTipoParametroPersona(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
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

		Page<TipoParametroPersonaEntity> listTipoParametroPersonaEntity = tipoParametroPersonaService.findAll(pageRequest);

		PageRender<TipoParametroPersonaEntity> pageRender = new PageRender<TipoParametroPersonaEntity>("/parametrizacionSistema/tipoParametroPersona/listTipoParametroPersona", listTipoParametroPersonaEntity);
		model.addAttribute("lblTituloAplicacion", "Monkazino Soft");
		model.addAttribute("lblTituloListadoTipoParametroPersona", "Listado de tipo parametros persona");
		model.addAttribute("listTipoParametroPersonaEntity", listTipoParametroPersonaEntity);
		model.addAttribute("page", pageRender);
		return "parametrizacionSistema/tipoParametroPersona/listTipoParametroPersona";
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
	
	public void inicializarVariablesTipoParametroPersona() {
		listEstado = new HashMap<String, String>();
		listEstado= ListaOpcionesEnumeradores.getListEstado();
	}
	
	public Map<String, String> getListEstado() {
		return listEstado;
	}

	public void setListEstado(Map<String, String> listEstado) {
		this.listEstado = listEstado;
	}
	
}
