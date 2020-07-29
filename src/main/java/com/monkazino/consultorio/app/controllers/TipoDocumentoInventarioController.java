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
import com.monkazino.consultorio.app.models.entity.TipoDocumentoInventarioEntity;
import com.monkazino.consultorio.app.models.service.ITipoDocumentoInventarioService;
import com.monkazino.consultorio.app.util.paginator.PageRender;

@Controller
@SessionAttributes("tipoDocumentoInventarioEntity")
public class TipoDocumentoInventarioController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private ITipoDocumentoInventarioService tipoDocumentoInventarioService;
	
	private Map<String, String> listTipoMovimientoInventario;
	
	private Map<String, String> listEstado;

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/parametrizacionSistema/tipoDocumentoInventario/formTipoDocumentoInventario")
	public String crearTipoDocumentoInventario(Map<String, Object> model) {
		TipoDocumentoInventarioEntity tipoDocumentoInventarioEntity = new TipoDocumentoInventarioEntity();
		tipoDocumentoInventarioEntity.setFechaCreacion(new Date());
		tipoDocumentoInventarioEntity.setEstado(EstadoActivoInactivoEnum.ACTIVO.getCodigo());
		inicializarVariablesTipoDocumentoInventario();
		model.put("tipoDocumentoInventarioEntity", tipoDocumentoInventarioEntity);
		model.put("listEstado", listEstado);
		model.put("listTipoMovimientoInventario", listTipoMovimientoInventario);
		model.put("lblTituloFormularioTipoDocumentoInventario", "Tipo Documento Inventario");
		return "parametrizacionSistema/tipoDocumentoInventario/formTipoDocumentoInventario";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/parametrizacionSistema/tipoDocumentoInventario/formTipoDocumentoInventario/{tipoDocumentoInventario}")
	public String editarTipoDocumentoInventario(@PathVariable(value = "tipoDocumentoInventario") Long tipoDocumentoInventario, Map<String, Object> model, RedirectAttributes flash) {
		TipoDocumentoInventarioEntity tipoDocumentoInventarioEntity = null;
		if (tipoDocumentoInventario > 0) {
			tipoDocumentoInventarioEntity = tipoDocumentoInventarioService.findOne(tipoDocumentoInventario);
			if (tipoDocumentoInventarioEntity == null) {
				flash.addFlashAttribute("error", "El ID del tipo documento inventario no existe en la BBDD!");
				return "redirect:/parametrizacionSistema/tipoDocumentoInventario/listTipoDocumentoInventario";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del tipo documento inventario no puede ser cero!");
			return "redirect:/parametrizacionSistema/tipoDocumentoInventario/listTipoDocumentoInventario";
		}
		inicializarVariablesTipoDocumentoInventario();
		model.put("tipoDocumentoInventarioEntity", tipoDocumentoInventarioEntity);
		model.put("listEstado", listEstado);
		model.put("listTipoMovimientoInventario", listTipoMovimientoInventario);
		model.put("lblTituloFormularioTipoDocumentoInventario", "Tipo Documento Inventario");
		return "parametrizacionSistema/tipoDocumentoInventario/formTipoDocumentoInventario";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/parametrizacionSistema/tipoDocumentoInventario/formTipoDocumentoInventario", method = RequestMethod.POST)
	public String guardarTipoDocumentoInventario(@Valid TipoDocumentoInventarioEntity tipoDocumentoInventarioEntity, BindingResult result, Map<String, Object> model, RedirectAttributes flash, SessionStatus status) {
		int countTipoDocumentoInventarioCodigo = 0;
		if (result.hasErrors()) {
			model.put("listEstado", listEstado);
			model.put("listTipoMovimientoInventario", listTipoMovimientoInventario);
			return "parametrizacionSistema/tipoDocumentoInventario/formTipoDocumentoInventario";
		}
		if (tipoDocumentoInventarioEntity.getTipoDocumentoInventario() == null) {
			countTipoDocumentoInventarioCodigo = tipoDocumentoInventarioService.consultarCountTipoDocumentoInventarioByCodigo(tipoDocumentoInventarioEntity.getCodigo().toUpperCase());
		} else {
			countTipoDocumentoInventarioCodigo = tipoDocumentoInventarioService.consultarCountTipoDocumentoInventarioByCodigoTipoDocumentoInventario(tipoDocumentoInventarioEntity.getCodigo().toUpperCase(), tipoDocumentoInventarioEntity.getTipoDocumentoInventario());
		}
		if (countTipoDocumentoInventarioCodigo == 0) {
			tipoDocumentoInventarioEntity.setCodigo(tipoDocumentoInventarioEntity.getCodigo().toUpperCase());
			tipoDocumentoInventarioEntity.setDescripcion(tipoDocumentoInventarioEntity.getDescripcion().toUpperCase());
			tipoDocumentoInventarioService.save(tipoDocumentoInventarioEntity);
			status.setComplete();
			flash.addFlashAttribute("success", "Registro almacenado correctamente");
			return "redirect:listTipoDocumentoInventario";
		} else {
			model.put("mensajeErrorTipoDocumentoInventario", "El código ya se encuentra registrado");
			return "parametrizacionSistema/tipoDocumentoInventario/formTipoDocumentoInventario";
		}
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarTipoDocumentoInventario/{tipoDocumentoInventario}")
	public String eliminarTipoDocumentoInventario(@PathVariable(value = "tipoDocumentoInventario") Long tipoDocumentoInventario, RedirectAttributes flash) {
		if (tipoDocumentoInventario > 0) {
			/*TipoDocumentoInventarioEntity tipoDocumentoInventarioEntity = tipoDocumentoInventarioService.fetchByIdWithParametrosPersona(tipoDocumentoInventario);
			if (tipoDocumentoInventarioEntity == null) {
				flash.addFlashAttribute("error", "El ID del tipo documento inventario no existe en la BBDD!");
			} else if (tipoDocumentoInventarioEntity.getParametrosPersona().isEmpty() || tipoDocumentoInventarioEntity.getParametrosPersona() == null) {
				tipoDocumentoInventarioService.delete(tipoDocumentoInventario);
				flash.addFlashAttribute("success", "Tipo Documento Inventario eliminado con éxito");
			} else {
				flash.addFlashAttribute("success", "Tipo Documento Inventario no se puede eliminar, tiene asociado parametros");
			}*/
		}
		return "redirect:/parametrizacionSistema/tipoDocumentoInventario/listTipoDocumentoInventario";
	}
	
	@RequestMapping(value = {"/parametrizacionSistema/tipoDocumentoInventario/listTipoDocumentoInventario"}, method = RequestMethod.GET)
	public String listTipoDocumentoInventario(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
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

		Page<TipoDocumentoInventarioEntity> listTipoDocumentoInventarioEntity = tipoDocumentoInventarioService.findAll(pageRequest);

		PageRender<TipoDocumentoInventarioEntity> pageRender = new PageRender<TipoDocumentoInventarioEntity>("/parametrizacionSistema/tipoDocumentoInventario/listTipoDocumentoInventario", listTipoDocumentoInventarioEntity);
		model.addAttribute("lblTituloAplicacion", "Monkazino Soft");
		model.addAttribute("lblTituloListadoTipoDocumentoInventario", "Listado de tipos documento inventario");
		model.addAttribute("listTipoDocumentoInventarioEntity", listTipoDocumentoInventarioEntity);
		model.addAttribute("page", pageRender);
		return "parametrizacionSistema/tipoDocumentoInventario/listTipoDocumentoInventario";
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
	
	public void inicializarVariablesTipoDocumentoInventario() {
		listTipoMovimientoInventario = new HashMap<String, String>();
		listTipoMovimientoInventario = ListaOpcionesEnumeradores.getListTipoMovimientoInventario();
		
		listEstado = new HashMap<String, String>();
		listEstado= ListaOpcionesEnumeradores.getListEstado();
	}
	
	public Map<String, String> getListEstado() {
		return listEstado;
	}

	public void setListEstado(Map<String, String> listEstado) {
		this.listEstado = listEstado;
	}

	public Map<String, String> getListTipoMovimientoInventario() {
		return listTipoMovimientoInventario;
	}

	public void setListTipoMovimientoInventario(Map<String, String> listTipoMovimientoInventario) {
		this.listTipoMovimientoInventario = listTipoMovimientoInventario;
	}
	
}
