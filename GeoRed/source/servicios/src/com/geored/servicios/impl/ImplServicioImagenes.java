package com.geored.servicios.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import negocios.GestionUsuarios;
import persistencia.Imagen;

import com.geored.servicios.ServicioImagenes;
import com.geored.servicios.impl.auth.GestionTokens;
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
			final HttpServletRequest request) {
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte [] buffer = new byte[1024];
			Collection<Part> parts = request.getParts();
			if (!parts.isEmpty()) {
				InputStream inputStream;
				for (Part part : parts) {
					inputStream = part.getInputStream();
					while (inputStream.read(buffer) > 0) {
						output.write(buffer);
					}
				}
			}
			else {
				ServletInputStream inputStream = request.getInputStream();
				while (inputStream.read(buffer) > 0) {
					output.write(buffer);
				}
			}
			output.close();
			Imagen imagen = new Imagen();
			imagen.setImagen(output.toByteArray());
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
