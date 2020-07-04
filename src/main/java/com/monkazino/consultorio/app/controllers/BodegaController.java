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
import com.monkazino.consultorio.app.models.entity.BodegaEntity;
import com.monkazino.consultorio.app.models.service.IBodegaService;
import com.monkazino.consultorio.app.util.paginator.PageRender;

@Controller
@SessionAttributes("bodegaEntity")
public class BodegaController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IBodegaService bodegaService;
	
	private Map<String, String> listEstado;

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/parametrizacionSistema/bodega/formBodega")
	public String crearBodega(Map<String, Object> model) {
		BodegaEntity bodegaEntity = new BodegaEntity();
		bodegaEntity.setFechaCreacion(new Date());
		bodegaEntity.setEstado(EstadoActivoInactivoEnum.ACTIVO.getCodigo());
		inicializarVariablesBodega();
		model.put("bodegaEntity", bodegaEntity);
		model.put("listEstado", listEstado);
		model.put("lblTituloFormularioBodega", "Bodega");
		return "parametrizacionSistema/bodega/formBodega";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/parametrizacionSistema/bodega/formBodega/{bodega}")
	public String editarBodega(@PathVariable(value = "bodega") Long bodega, Map<String, Object> model, RedirectAttributes flash) {
		BodegaEntity bodegaEntity = null;
		if (bodega > 0) {
			bodegaEntity = bodegaService.findOne(bodega);
			if (bodegaEntity == null) {
				flash.addFlashAttribute("error", "El ID del bodega no existe en la BBDD!");
				return "redirect:/parametrizacionSistema/bodega/listBodega";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del bodega no puede ser cero!");
			return "redirect:/parametrizacionSistema/bodega/listBodega";
		}
		inicializarVariablesBodega();
		model.put("bodegaEntity", bodegaEntity);
		model.put("listEstado", listEstado);
		model.put("lblTituloFormularioBodega", "Bodega");
		return "parametrizacionSistema/bodega/formBodega";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/parametrizacionSistema/bodega/formBodega", method = RequestMethod.POST)
	public String guardarBodega(@Valid BodegaEntity bodegaEntity, BindingResult result, Map<String, Object> model, RedirectAttributes flash, SessionStatus status) {
		Long countBodegaCodigo = 0L;
		if (result.hasErrors()) {
			model.put("listEstado", listEstado);
			return "parametrizacionSistema/bodega/formBodega";
		}
		if (bodegaEntity.getBodega() == null) {
			countBodegaCodigo = bodegaService.consultarCountBodegaByCodigo(bodegaEntity.getCodigo().toUpperCase());
		} else {
			countBodegaCodigo = bodegaService.consultarCountBodegaByCodigoBodega(bodegaEntity.getCodigo().toUpperCase(), bodegaEntity.getBodega());
		}
		if (countBodegaCodigo == 0) {
			bodegaEntity.setCodigo(bodegaEntity.getCodigo().toUpperCase());
			bodegaEntity.setDescripcion(bodegaEntity.getDescripcion().toUpperCase());
			bodegaService.save(bodegaEntity);
			status.setComplete();
			flash.addFlashAttribute("success", "Registro almacenado correctamente");
			return "redirect:listBodega";
		} else {
			model.put("mensajeErrorBodega", "El código ya se encuentra registrado");
			return "parametrizacionSistema/bodega/formBodega";
		}
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarBodega/{bodega}")
	public String eliminarBodega(@PathVariable(value = "bodega") Long bodega, RedirectAttributes flash) {
		if (bodega > 0) {
			/*BodegaEntity bodegaEntity = bodegaService.fetchByIdWithParametrosProducto(bodega);
			if (bodegaEntity == null) {
				flash.addFlashAttribute("error", "El ID del bodega no existe en la BBDD!");
			} else if (bodegaEntity.getParametrosProducto().isEmpty() || bodegaEntity.getParametrosProducto() == null) {
				bodegaService.delete(bodega);
				flash.addFlashAttribute("success", "Bodega eliminado con éxito");
			} else {
				flash.addFlashAttribute("success", "Bodega no se puede eliminar, tiene asociado parametros");
			}*/
		}
		return "redirect:/parametrizacionSistema/bodega/listBodega";
	}
	
	/*@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/parametrizacionSistema/bodega/listParametroProducto/{parametroProducto}")
	public String listParametroProducto(@PathVariable(value = "parametroProducto") Long parametroProducto, Map<String, Object> model, RedirectAttributes flash) {
		BodegaEntity bodegaEntity = bodegaService.fetchByIdWithParametrosProducto(parametroProducto);
		if (bodegaEntity == null) {
			flash.addFlashAttribute("error", "El bodega no existe en la base de datos");
			return "redirect:/parametrizacionSistema/bodega/listBodega";
		}
		model.put("bodegaEntity", bodegaEntity);
		model.put("lblTituloDetalleBodega", "Detalle bodega: " + bodegaEntity.getDescripcion());
		return "parametrizacionSistema/bodega/listParametroProducto";
	}*/

	@RequestMapping(value = {"/parametrizacionSistema/bodega/listBodega"}, method = RequestMethod.GET)
	public String listBodega(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
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

		Page<BodegaEntity> listBodegaEntity = bodegaService.findAll(pageRequest);

		PageRender<BodegaEntity> pageRender = new PageRender<BodegaEntity>("/parametrizacionSistema/bodega/listBodega", listBodegaEntity);
		model.addAttribute("lblTituloAplicacion", "Monkazino Soft");
		model.addAttribute("lblTituloListadoBodega", "Listado de bodega");
		model.addAttribute("listBodegaEntity", listBodegaEntity);
		model.addAttribute("page", pageRender);
		return "parametrizacionSistema/bodega/listBodega";
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
	
	public Map<String, String> getListEstado() {
		return listEstado;
	}

	public void setListEstado(Map<String, String> listEstado) {
		this.listEstado = listEstado;
	}

	public void inicializarVariablesBodega() {
		listEstado = new HashMap<String, String>();
		listEstado= ListaOpcionesEnumeradores.getListEstado();
	}
}
