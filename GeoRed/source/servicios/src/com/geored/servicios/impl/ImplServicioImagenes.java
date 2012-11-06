package com.geored.servicios.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import negocios.GestionUsuarios;
import persistencia.Imagen;

import com.geored.servicios.ServicioImagenes;
import com.geored.servicios.impl.auth.GestionTokens;
import com.geored.servicios.impl.util.DataUploadForm;
import com.geored.servicios.json.ImagenJSON;

@Local
@Stateless
public class ImplServicioImagenes implements ServicioImagenes {

	@EJB
	GestionUsuarios gestionUsuarios;
	
	@EJB
	GestionTokens gestionTokens;
	
	@Override
	public Response bajarImagen(final String userToken, final HttpServletResponse response, final Integer idImagen) {	
		if (gestionTokens.validarToken(userToken)) {
			Imagen imagen = gestionUsuarios.obtenerImagen(idImagen);
			ResponseBuilder responseImage = Response.ok(imagen.getImagen());
			return responseImage.build();
		}
		else {
			response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
		}
		return null;
	}

	@Override
	public ImagenJSON subirImagen(final String userToken, final HttpServletResponse response, 
			final HttpServletRequest request, final DataUploadForm uploadForm) {
		try {
			InputStream input = uploadForm.getPayload();
			ByteArrayOutputStream bufferFinal = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			while (input.read(buffer) > 0) {
				bufferFinal.write(buffer);				
			}
			Imagen imagen = new Imagen();
			imagen.setImagen(bufferFinal.toByteArray());
			Integer idImagen = gestionUsuarios.altaImagen(imagen);
			ImagenJSON imagenJSON = new ImagenJSON();
			imagenJSON.setId(idImagen);
			response.setStatus(Response.Status.OK.getStatusCode());
			return imagenJSON;
		}
		catch (Exception e) {
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
		}
		return null;
	}

}
