package com.monkazino.consultorio.app.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

import com.monkazino.consultorio.app.models.entity.ParametroProductoEntity;
import com.monkazino.consultorio.app.models.entity.ProductoEntity;
import com.monkazino.consultorio.app.models.service.IParametroProductoService;
import com.monkazino.consultorio.app.models.service.IProductoService;
import com.monkazino.consultorio.app.util.general.EstadoActivoInactivoEnum;
import com.monkazino.consultorio.app.util.general.TipoParametroProductoEnum;
import com.monkazino.consultorio.app.util.paginator.PageRender;

@Controller
@SessionAttributes("productoEntity")
public class ProductoController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private IParametroProductoService parametroProductoService;
	
	private List<ParametroProductoEntity> listaParamTipoProducto;
	private List<ParametroProductoEntity> listaParamMarca;
	private List<ParametroProductoEntity> listaParamCategoria;

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/producto/formProducto")
	public String crearProducto(Map<String, Object> model) {
		inicializarParametrosTipoProducto();
		ProductoEntity productoEntity = new ProductoEntity();
		productoEntity.setEstado(EstadoActivoInactivoEnum.ACTIVO.getCodigo());
		model.put("productoEntity", productoEntity);
		model.put("listaParamTipoProducto", listaParamTipoProducto);
		model.put("listaParamMarca", listaParamMarca);
		model.put("listaParamCategoria", listaParamCategoria);
		model.put("lblTituloFormProducto", "Producto");
		return "producto/formProducto";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/producto/formProducto/{producto}")
	public String editarProducto(@PathVariable(value = "producto") Long producto, Map<String, Object> model, RedirectAttributes flash) {
		ProductoEntity productoEntity = null;
		if (producto > 0) {
			productoEntity = productoService.findOne(producto);
			if (productoEntity == null) {
				flash.addFlashAttribute("error", "El ID del producto no existe en la BBDD!");
				return "redirect:/producto/listProducto";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del producto no puede ser cero!");
			return "redirect:/producto/listProducto";
		}
		model.put("productoEntity", productoEntity);
		model.put("listaParamTipoProducto", listaParamTipoProducto);
		model.put("listaParamMarca", listaParamMarca);
		model.put("listaParamCategoria", listaParamCategoria);
		model.put("lblTituloFormProducto", "Producto");
		return "producto/formProducto";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/producto/formProducto", method = RequestMethod.POST)
	public String guardarProducto(@Valid ProductoEntity productoEntity, BindingResult result, Map<String, Object> model, RedirectAttributes flash, SessionStatus status) {
		int countProductoCodigo = 0;
		if (result.hasErrors()) {
			model.put("listaParamTipoProducto", listaParamTipoProducto);
			model.put("listaParamMarca", listaParamMarca);
			model.put("listaParamCategoria", listaParamCategoria);
			return "producto/formProducto";
		}
		if (productoEntity.getProducto() == null) {
			countProductoCodigo = productoService.consultarCountProductoByCodigo(productoEntity.getCodigo().toUpperCase());
		} else {
			countProductoCodigo = productoService.consultarCountProductoByCodigoProducto(productoEntity.getCodigo().toUpperCase(), productoEntity.getProducto());
		}
		if (countProductoCodigo == 0) {
			productoEntity.setCodigo(productoEntity.getCodigo().toUpperCase());
			productoEntity.setDescripcion(productoEntity.getDescripcion().toUpperCase());
			productoService.save(productoEntity);
			status.setComplete();
			flash.addFlashAttribute("success", "Registro almacenado correctamente");
			return "redirect:listProducto";
		} else {
			model.put("mensajeErrorProducto", "El código ya se encuentra registrado");
			return "producto/formProducto";
		}
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarProducto/{producto}")
	public String eliminarProducto(@PathVariable(value = "producto") Long producto, RedirectAttributes flash) {
		ProductoEntity productoEntity = null;
		if (producto > 0) {
			productoEntity = productoService.findOne(producto);
			if (productoEntity != null) {
				productoService.delete(producto);
				flash.addFlashAttribute("success", "Producto eliminado con éxito");
			} else {
				flash.addFlashAttribute("error", "El ID del producto no existe en la BBDD!");
			} 
		}
		return "redirect:/producto/listProducto";
	}

	@RequestMapping(value = {"/producto/listProducto"}, method = RequestMethod.GET)
	public String listProducto(@RequestParam(name = "page", defaultValue = "0") int page, Model model, Authentication authentication, HttpServletRequest request) {

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

		Page<ProductoEntity> productos = productoService.findAll(pageRequest);

		PageRender<ProductoEntity> pageRender = new PageRender<ProductoEntity>("/producto/listProducto", productos);
		model.addAttribute("lblTituloAplicacion", "Monkazino Soft");
		model.addAttribute("lblTituloListadoProducto", "Productos");
		model.addAttribute("productos", productos);
		model.addAttribute("page", pageRender);
		return "producto/listProducto";
	}
	
	public void inicializarParametrosTipoProducto() {
		listaParamTipoProducto= new ArrayList<ParametroProductoEntity>();
		listaParamTipoProducto = parametroProductoService.consultarParametrosProductoTipoParametroProducto(TipoParametroProductoEnum.TIPO_PRODUCTO.getCodigo());
		
		listaParamMarca= new ArrayList<ParametroProductoEntity>();
		listaParamMarca = parametroProductoService.consultarParametrosProductoTipoParametroProducto(TipoParametroProductoEnum.MARCA.getCodigo());
		
		listaParamCategoria= new ArrayList<ParametroProductoEntity>();
		listaParamCategoria = parametroProductoService.consultarParametrosProductoTipoParametroProducto(TipoParametroProductoEnum.CATEGORIA.getCodigo());
	}
	
	public List<ParametroProductoEntity> getListaParamTipoProducto() {
		return listaParamTipoProducto;
	}

	public void setListaParamTipoProducto(List<ParametroProductoEntity> listaParamTipoProducto) {
		this.listaParamTipoProducto = listaParamTipoProducto;
	}

	public List<ParametroProductoEntity> getListaParamMarca() {
		return listaParamMarca;
	}

	public void setListaParamMarca(List<ParametroProductoEntity> listaParamMarca) {
		this.listaParamMarca = listaParamMarca;
	}

	public List<ParametroProductoEntity> getListaParamCategoria() {
		return listaParamCategoria;
	}

	public void setListaParamCategoria(List<ParametroProductoEntity> listaParamCategoria) {
		this.listaParamCategoria = listaParamCategoria;
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
