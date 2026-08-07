package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import modelo.Usuario;
import util.Conexion;
import util.Validaciones;

public class UsuarioDAO {

    public boolean agregar(Usuario u) {
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

        String sql = "INSERT INTO Usuario (nombre, apellido, edad, email, password, activo) VALUES (?, ?, ?, ?, ?, 1)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setInt(3, u.getEdad());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getPassword());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                u.setId(rs.getInt(1));
            }
            u.setActivo(true);
            System.out.println("usuario creado correctamente");
            return true;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return false;
        }
    }

    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM Usuario WHERE id = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return armarUsuario(rs);
            }
            return null;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return null;
        }
    }

    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT * FROM Usuario WHERE email = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return armarUsuario(rs);
            }
            return null;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return null;
        }
    }

    public boolean editar(int id, String nombre, String apellido, int edad, String email, String password) {
        Usuario u = buscarPorId(id);
        if (u == null) {
            System.out.println("no existe ese usuario");
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
        if (!Validaciones.validarEdad(edad)) {
            System.out.println("la edad no es valida");
            return false;
        }

        String passwordFinal = password;
        if (Validaciones.esVacio(password)) {
            passwordFinal = u.getPassword();
        }

        String sql = "UPDATE Usuario SET nombre = ?, apellido = ?, edad = ?, email = ?, password = ? WHERE id = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setInt(3, edad);
            ps.setString(4, email);
            ps.setString(5, passwordFinal);
            ps.setInt(6, id);
            ps.executeUpdate();
            System.out.println("usuario actualizado correctamente");
            return true;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "UPDATE Usuario SET activo = 0 WHERE id = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            if (filas == 0) {
                System.out.println("no existe ese usuario");
                return false;
            }
            System.out.println("usuario eliminado");
            return true;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return false;
        }
    }

    public boolean mostrarTodos() {
        String sql = "SELECT * FROM Usuario";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean hayDatos = false;
            while (rs.next()) {
                System.out.println(armarUsuario(rs));
                hayDatos = true;
            }
            if (!hayDatos) {
                System.out.println("no hay usuarios todavia");
            }
            return hayDatos;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return false;
        }
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

    private Usuario armarUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setNombre(rs.getString("nombre"));
        u.setApellido(rs.getString("apellido"));
        u.setEdad(rs.getInt("edad"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setActivo(rs.getBoolean("activo"));
        return u;
    }
}