package Controller;

import java.util.List;

import exception.usuarioException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import model.Usuario;

@Stateless
public class UsuarioController {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void crearUsuario(Usuario usuario) throws usuarioException {
        // Verificar si ya existe un usuario con el mismo ID o email
        Usuario existente = em.find(Usuario.class, usuario.getId());
        if (existente != null) {
            throw new usuarioException("Usuario ya agregado con ID: " + usuario.getId());
        }

        // Si es necesario, también valida por email
        long count = em.createQuery("SELECT COUNT(u) FROM Usuario u WHERE u.email = :email", Long.class)
                       .setParameter("email", usuario.getEmail())
                       .getSingleResult();
        if (count > 0) {
            throw new usuarioException("Usuario ya agregado con email: " + usuario.getEmail());
        }

        // Persistir el usuario si no hay conflictos
        em.merge(usuario);
    }

    public List<Usuario> getUsuarios() {
        return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }
}
