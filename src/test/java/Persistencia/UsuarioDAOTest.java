package Persistencia;

import Dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioDAOTest {

    private UsuarioDAO usuarioDAO;

    @BeforeEach
    void setUp(){

        usuarioDAO = new UsuarioDAO();

    }

    private Usuario crear(Usuario usuario) throws SQLException{


        Usuario resultado = usuarioDAO.crear(usuario);



        assertNotNull(
                resultado,
                "El usuario creado no debería ser nulo."
        );


        assertEquals(
                usuario.getNombre(),
                resultado.getNombre(),
                "El nombre debe coincidir."
        );


        assertEquals(
                usuario.getApellido(),
                resultado.getApellido(),
                "El apellido debe coincidir."
        );


        assertEquals(
                usuario.getLogin(),
                resultado.getLogin(),
                "El login debe coincidir."
        );


        assertEquals(
                usuario.getEstatus(),
                resultado.getEstatus(),
                "El estatus debe coincidir."
        );


        return resultado;

    }

    private void actualizar(Usuario usuario) throws SQLException{


        usuario.setNombre(
                usuario.getNombre() + "_actualizado"
        );


        usuario.setApellido(
                usuario.getApellido() + "_actualizado"
        );


        usuario.setEstatus(
                (byte)1
        );



        boolean resultado =
                usuarioDAO.actualizar(usuario);



        assertTrue(
                resultado,
                "La actualización debería ser exitosa."
        );



        obtenerPorId(usuario);

    }

    private void obtenerPorId(Usuario usuario) throws SQLException{


        Usuario resultado =
                usuarioDAO.obtenerPorId(
                        usuario.getIdUsuario()
                );



        assertNotNull(
                resultado,
                "El usuario obtenido no debería ser nulo."
        );



        assertEquals(
                usuario.getIdUsuario(),
                resultado.getIdUsuario()
        );


        assertEquals(
                usuario.getNombre(),
                resultado.getNombre()
        );


        assertEquals(
                usuario.getApellido(),
                resultado.getApellido()
        );

    }

    private void eliminar(Usuario usuario) throws SQLException{


        boolean resultado =
                usuarioDAO.eliminar(
                        usuario.getIdUsuario()
                );



        assertTrue(
                resultado,
                "El usuario debería eliminarse correctamente."
        );


    }

    @Test
    public void pruebaCRUDUsuario() throws SQLException{


        Usuario usuario = new Usuario();



        usuario.setNombre("Majo");

        usuario.setApellido("Rosa");

        usuario.setLogin("aldana");

        usuario.setPassword("12345");

        usuario.setEstatus((byte)1);




        Usuario creado =
                crear(usuario);



        actualizar(creado);



        obtenerPorId(creado);



        eliminar(creado);


    }

    @Test
    public void pruebaListarUsuarios() throws SQLException{


        ArrayList<Usuario> lista =
                usuarioDAO.obtenerTodos();



        assertNotNull(
                lista,
                "La lista no debería ser nula."
        );



        System.out.println(
                "Cantidad de usuarios: "
                        + lista.size()
        );
    }
}
