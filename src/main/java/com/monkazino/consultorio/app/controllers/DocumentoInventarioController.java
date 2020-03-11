package com.monkazino.consultorio.app.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.monkazino.consultorio.app.models.entity.DocumentoInventarioEntity;
import com.monkazino.consultorio.app.models.entity.ItemInventarioEntity;
import com.monkazino.consultorio.app.models.entity.PaisEntity;
import com.monkazino.consultorio.app.models.entity.ProveedorEntity;
import com.monkazino.consultorio.app.models.entity.ProductoEntity;
import com.monkazino.consultorio.app.models.service.IDocumentoInventarioService;
import com.monkazino.consultorio.app.models.service.IProductoService;
import com.monkazino.consultorio.app.models.service.IProveedorService;
import com.monkazino.consultorio.app.util.general.EstadoActivoInactivoEnum;

@Controller
@RequestMapping("/inventario")
@SessionAttributes("documentoInventarioEntity")
public class DocumentoInventarioController {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IDocumentoInventarioService documentoInventarioService;
	
	@Autowired
	private IProveedorService proveedorService;
	
	@Autowired
	private IProductoService productoService;
	
	@GetMapping("/verInformacionDocumentoInventario/{documentoInventario}")
	public String verInformacionDocumentoInventario(@PathVariable(value="documentoInventario") Long documentoInventario, Model model, RedirectAttributes flash) {
		DocumentoInventarioEntity documentoInventarioEntity = documentoInventarioService.fetchByIdWithProveedorEntityWhithItemDocumentoInventarioEntityWithProductoEntity(documentoInventario); //proveedorService.findDocumentoInventarioById(documentoInventario);
		
		if(documentoInventarioEntity == null) {
			flash.addFlashAttribute("error", "El Documento Inventario no existe en la base de datos!");
			return "redirect:/proveedor/listProveedor";
		}
		
		model.addAttribute("documentoInventarioEntity", documentoInventarioEntity);
		model.addAttribute("titulo", "Documento Inventario: ".concat(documentoInventarioEntity.getObservacion()));
		
		return "inventario/verInformacionDocumentoInventario";
	}

	
	@GetMapping("/formDocumentoInventario/{proveedor}")
	public String crearDocumentoInventario(@PathVariable(value = "proveedor") Long proveedor, Map<String, Object> model, RedirectAttributes flash) {
		ProveedorEntity proveedorEntity = proveedorService.findOne(proveedor);
		if (proveedorEntity == null) {
			flash.addFlashAttribute("error", "El proveedor no existe en la base de datos");
			return "redirect:/proveedor/listProveedor";
		}
		DocumentoInventarioEntity documentoInventarioEntity = new DocumentoInventarioEntity();
		documentoInventarioEntity.setProveedorEntity(proveedorEntity);
		model.put("documentoInventarioEntity", documentoInventarioEntity);
		model.put("lblTituloFormDocumentoInventario", "Documento Inventario");
		model.put("titulo", "Crear Documento Inventario");
		return "inventario/formDocumentoInventario";
	}
	
	@PostMapping("/formDocumentoInventario")
	public String guardarDocumentoInventario(@Valid DocumentoInventarioEntity documentoInventarioEntity, 
			BindingResult result, Model model,
			@RequestParam(name = "item_producto[]", required = false) Long[] itemProducto,
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidad, 
			RedirectAttributes flash,
			SessionStatus status) {
		
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Documento Inventario");
			return "inventario/formDocumentoInventario";
		}

		if (itemProducto == null || itemProducto.length == 0) {
			model.addAttribute("titulo", "Crear Documento Inventario");
			model.addAttribute("error", "Error: La Documento Inventario debe tener líneas!");
			return "inventario/formDocumentoInventario";
		}
		
		for (int i = 0; i < itemProducto.length; i++) {
			ProductoEntity productoEntity = productoService.findOne(itemProducto[i]);

			ItemInventarioEntity linea = new ItemInventarioEntity();
			linea.setCantidad(cantidad[i]);
			linea.setProductoEntity(productoEntity);
			documentoInventarioEntity.addItemFactura(linea);

			log.info("PRODUCTO: " + itemProducto[i].toString() + ", cantidad: " + cantidad[i].toString());
		}

		documentoInventarioService.save(documentoInventarioEntity);
		status.setComplete();

		flash.addFlashAttribute("success", "Documento Inventario creado con éxito!");

		return "redirect:/proveedor/verInformacionProveedor/" + documentoInventarioEntity.getProveedorEntity().getProveedor();
	}
	
	@GetMapping("/eliminarDocumentoInventario/{documentoInventario}")
	public String eliminarDocumentoInventario(@PathVariable(value="documentoInventario") Long documentoInventario, RedirectAttributes flash) {
		DocumentoInventarioEntity documentoInventarioEntity = documentoInventarioService.findOne(documentoInventario);
		if(documentoInventarioEntity != null) {
			documentoInventarioService.delete(documentoInventario);
			flash.addFlashAttribute("success", "Documento Inventario eliminado con éxito!");
			return "redirect:/proveedor/verInformacionProveedor/" + documentoInventarioEntity.getProveedorEntity().getProveedor();
		}
		flash.addFlashAttribute("error", "El Documento Inventario no existe en la base de datos, no se pudo eliminar!");
		
		return "redirect:/proveedor/listProveedor";
	}
	
	@GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
	public @ResponseBody List<ProductoEntity> cargarProductos(@PathVariable String term) {
		return productoService.fetchByDescripcionLike(term);
	}

}
