package dao;

import java.util.ArrayList;
import modelo.Usuario;
import util.Validaciones;

public class UsuarioDAO {

    ArrayList<Usuario> usuarios = new ArrayList<>();
    int contador = 1;

    public boolean agregar(Usuario u) {
        try {
            if (Validaciones.esVacio(u.getNombre()) || Validaciones.esVacio(u.getApellido()) || Validaciones.esVacio(u.getEmail())) {
                System.out.println("hay datos vacios");
                return false;
            }
            if (!Validaciones.validarEmail(u.getEmail())) {
                System.out.println("el correo no es valido");
                return false;
            }
            if (!Validaciones.validarEdad(u.getEdad())) {
                System.out.println("la edad no es valida");
                return false;
            }
            if (buscarPorEmail(u.getEmail()) != null) {
                System.out.println("ese correo ya esta registrado");
                return false;
            }
            u.setId(contador);
            contador = contador + 1;
            u.setActivo(true);
            usuarios.add(u);
            System.out.println("usuario creado correctamente");
            return true;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return false;
        }
    }

    public Usuario buscarPorId(int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public Usuario buscarPorEmail(String email) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        return null;
    }

    public boolean editar(int id, String nombre, String apellido, int edad, String email, String password) {
        try {
            Usuario u = buscarPorId(id);
            if (u == null) {
                System.out.println("no existe ese usuario");
                return false;
            }

            if (!u.isActivo()) {
                System.out.println("no se puede editar un usuario eliminado");
                return false;
            }

            if (Validaciones.esVacio(nombre) || Validaciones.esVacio(apellido) || Validaciones.esVacio(email)) {
                System.out.println("hay datos vacios");
                return false;
            }
            if (!Validaciones.validarEmail(email)) {
                System.out.println("el correo no es valido");
                return false;
            }

            Usuario existente = buscarPorEmail(email);
            if (existente != null && existente.getId() != id) {
                System.out.println("ese correo ya esta registrado por otro usuario");
                return false;
            }

            if (!Validaciones.validarEdad(edad)) {
                System.out.println("la edad no es valida");
                return false;
            }
            u.setNombre(nombre);
            u.setApellido(apellido);
            u.setEdad(edad);
            u.setEmail(email);
            if (!Validaciones.esVacio(password)) {
                u.setPassword(password);
            }
            System.out.println("usuario actualizado correctamente");
            return true;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        Usuario u = buscarPorId(id);
        if (u == null) {
            System.out.println("no existe ese usuario");
            return false;
        }
        u.setActivo(false);
        System.out.println("usuario eliminado");
        return true;
    }

    public boolean mostrarTodos() {
        if (usuarios.isEmpty()) {
            System.out.println("no hay usuarios todavia");
            return false;
        }
        for (Usuario u : usuarios) {
            System.out.println(u);
        }
        return true;
    }

    public Usuario login(String email, String password) {
        Usuario u = buscarPorEmail(email);
        if (u == null) {
            return null;
        }
        if (!u.isActivo()) {
            return null;
        }
        if (u.getPassword().equals(password)) {
            return u;
        }
        return null;
    }
}
