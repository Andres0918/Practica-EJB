package Service;

import java.util.List;

import Controller.UsuarioController;
import exception.usuarioException;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Usuario;

@Path("/usuarios")
public class UsuarioService {
    @Inject
    private UsuarioController usuarioController;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearUsuario(Usuario usuario) {
        try {
            usuarioController.crearUsuario(usuario);
            return Response.status(Response.Status.CREATED).build();
        } catch (usuarioException e) {
            // Devuelve un mensaje de conflicto si ocurre un error genérico
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> getUsuarios() {
        return usuarioController.getUsuarios();
    }
}
