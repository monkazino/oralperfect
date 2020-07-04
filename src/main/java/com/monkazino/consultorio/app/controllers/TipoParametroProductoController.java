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

import com.monkazino.consultorio.app.general.enums.EstadoActivoInactivoEnum;
import com.monkazino.consultorio.app.general.util.ListaOpcionesEnumeradores;
import com.monkazino.consultorio.app.models.entity.TipoParametroProductoEntity;
import com.monkazino.consultorio.app.models.service.ITipoParametroProductoService;
import com.monkazino.consultorio.app.util.paginator.PageRender;

@Controller
@SessionAttributes("tipoParametroProductoEntity")
public class TipoParametroProductoController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private ITipoParametroProductoService tipoParametroProductoService;
	private Map<String, String> listEstado;

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/parametrizacionSistema/tipoParametroProducto/formTipoParametroProducto")
	public String crearTipoParametroProducto(Map<String, Object> model) {
		TipoParametroProductoEntity tipoParametroProductoEntity = new TipoParametroProductoEntity();
		tipoParametroProductoEntity.setFechaCreacion(new Date());
		tipoParametroProductoEntity.setEstado(EstadoActivoInactivoEnum.ACTIVO.getCodigo());
		inicializarVariablesTipoParametroProducto();
		model.put("tipoParametroProductoEntity", tipoParametroProductoEntity);
		model.put("listEstado", listEstado);
		model.put("lblTituloFormularioTipoParametroProducto", "Tipo Parametro Producto");
		return "parametrizacionSistema/tipoParametroProducto/formTipoParametroProducto";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/parametrizacionSistema/tipoParametroProducto/formTipoParametroProducto/{tipoParametroProducto}")
	public String editarTipoParametroProducto(@PathVariable(value = "tipoParametroProducto") Long tipoParametroProducto, Map<String, Object> model, RedirectAttributes flash) {
		TipoParametroProductoEntity tipoParametroProductoEntity = null;
		if (tipoParametroProducto > 0) {
			tipoParametroProductoEntity = tipoParametroProductoService.findOne(tipoParametroProducto);
			if (tipoParametroProductoEntity == null) {
				flash.addFlashAttribute("error", "El ID del tipo parametro producto no existe en la BBDD!");
				return "redirect:/parametrizacionSistema/tipoParametroProducto/listTipoParametroProducto";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del tipo parametro producto no puede ser cero!");
			return "redirect:/parametrizacionSistema/tipoParametroProducto/listTipoParametroProducto";
		}
		inicializarVariablesTipoParametroProducto();
		model.put("tipoParametroProductoEntity", tipoParametroProductoEntity);
		model.put("listEstado", listEstado);
		model.put("lblTituloFormularioTipoParametroProducto", "Tipo Parametro Producto");
		return "parametrizacionSistema/tipoParametroProducto/formTipoParametroProducto";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/parametrizacionSistema/tipoParametroProducto/formTipoParametroProducto", method = RequestMethod.POST)
	public String guardarTipoParametroProducto(@Valid TipoParametroProductoEntity tipoParametroProductoEntity, BindingResult result, Map<String, Object> model, RedirectAttributes flash, SessionStatus status) {
		int countTipoParametroProductoCodigo = 0;
		if (result.hasErrors()) {
			model.put("listEstado", listEstado);
			return "parametrizacionSistema/tipoParametroProducto/formTipoParametroProducto";
		}
		if (tipoParametroProductoEntity.getTipoParametroProducto() == null) {
			countTipoParametroProductoCodigo = tipoParametroProductoService.consultarCountTipoParametroProductoByCodigo(tipoParametroProductoEntity.getCodigo().toUpperCase());
		} else {
			countTipoParametroProductoCodigo = tipoParametroProductoService.consultarCountTipoParametroProductoByCodigoTipoParametroProducto(tipoParametroProductoEntity.getCodigo().toUpperCase(), tipoParametroProductoEntity.getTipoParametroProducto());
		}
		if (countTipoParametroProductoCodigo == 0) {
			tipoParametroProductoEntity.setCodigo(tipoParametroProductoEntity.getCodigo().toUpperCase());
			tipoParametroProductoEntity.setDescripcion(tipoParametroProductoEntity.getDescripcion().toUpperCase());
			tipoParametroProductoService.save(tipoParametroProductoEntity);
			status.setComplete();
			flash.addFlashAttribute("success", "Registro almacenado correctamente");
			return "redirect:listTipoParametroProducto";
		} else {
			model.put("mensajeErrorTipoParametroProducto", "El código ya se encuentra registrado");
			return "parametrizacionSistema/tipoParametroProducto/formTipoParametroProducto";
		}
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarTipoParametroProducto/{tipoParametroProducto}")
	public String eliminarTipoParametroProducto(@PathVariable(value = "tipoParametroProducto") Long tipoParametroProducto, RedirectAttributes flash) {
		if (tipoParametroProducto > 0) {
			TipoParametroProductoEntity tipoParametroProductoEntity = tipoParametroProductoService.fetchByIdWithParametrosProducto(tipoParametroProducto);
			if (tipoParametroProductoEntity == null) {
				flash.addFlashAttribute("error", "El ID del tipo parametro producto no existe en la BBDD!");
			} else if (tipoParametroProductoEntity.getParametrosProducto().isEmpty() || tipoParametroProductoEntity.getParametrosProducto() == null) {
				tipoParametroProductoService.delete(tipoParametroProducto);
				flash.addFlashAttribute("success", "Tipo Parametro Producto eliminado con éxito");
			} else {
				flash.addFlashAttribute("success", "Tipo Parametro Producto no se puede eliminar, tiene asociado parametros");
			}
		}
		return "redirect:/parametrizacionSistema/tipoParametroProducto/listTipoParametroProducto";
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/parametrizacionSistema/tipoParametroProducto/listParametroProducto/{parametroProducto}")
	public String listParametroProducto(@PathVariable(value = "parametroProducto") Long parametroProducto, Map<String, Object> model, RedirectAttributes flash) {
		TipoParametroProductoEntity tipoParametroProductoEntity = tipoParametroProductoService.fetchByIdWithParametrosProducto(parametroProducto);
		if (tipoParametroProductoEntity == null) {
			flash.addFlashAttribute("error", "El tipo parametro producto no existe en la base de datos");
			return "redirect:/parametrizacionSistema/tipoParametroProducto/listTipoParametroProducto";
		}
		model.put("tipoParametroProductoEntity", tipoParametroProductoEntity);
		model.put("lblTituloDetalleTipoParametroProducto", "Detalle tipo parametro producto: " + tipoParametroProductoEntity.getDescripcion());
		return "parametrizacionSistema/tipoParametroProducto/listParametroProducto";
	}

	@RequestMapping(value = {"/parametrizacionSistema/tipoParametroProducto/listTipoParametroProducto"}, method = RequestMethod.GET)
	public String listTipoParametroProducto(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
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

		Page<TipoParametroProductoEntity> listTipoParametroProductoEntity = tipoParametroProductoService.findAll(pageRequest);

		PageRender<TipoParametroProductoEntity> pageRender = new PageRender<TipoParametroProductoEntity>("/parametrizacionSistema/tipoParametroProducto/listTipoParametroProducto", listTipoParametroProductoEntity);
		model.addAttribute("lblTituloAplicacion", "Monkazino Soft");
		model.addAttribute("lblTituloListadoTipoParametroProducto", "Listado de tipo parametros producto");
		model.addAttribute("listTipoParametroProductoEntity", listTipoParametroProductoEntity);
		model.addAttribute("page", pageRender);
		return "parametrizacionSistema/tipoParametroProducto/listTipoParametroProducto";
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

	public void inicializarVariablesTipoParametroProducto() {
		listEstado= ListaOpcionesEnumeradores.getListEstado();
	}
}
