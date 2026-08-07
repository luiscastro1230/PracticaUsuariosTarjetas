package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Tarjeta;
import util.Conexion;
import util.GeneradorID;
import util.Validaciones;

public class TarjetaDAO {

    UsuarioDAO usuarioDAO;

    public TarjetaDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public boolean agregar(Tarjeta t) {
        if (usuarioDAO.buscarPorId(t.getIdUsuario()) == null) {
            System.out.println("no existe ese usuario");
            return false;
        }
        if (!Validaciones.validarTipo(t.getTipo())) {
            System.out.println("el tipo de tarjeta no es valido");
            return false;
        }
        if (t.getSaldo() < 0) {
            System.out.println("el saldo no puede ser negativo");
            return false;
        }

        String numero = GeneradorID.generarNumeroTarjeta();
        while (buscarPorNumero(numero) != null) {
            numero = GeneradorID.generarNumeroTarjeta();
        }

        String sql = "INSERT INTO Tarjeta (clave, numero, fechaExp, saldo, tipo, activo, idUsuario) VALUES (?, ?, ?, ?, ?, 1, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, t.getClave());
            ps.setString(2, numero);
            ps.setString(3, t.getFechaExp());
            ps.setDouble(4, t.getSaldo());
            ps.setString(5, t.getTipo());
            ps.setInt(6, t.getIdUsuario());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                t.setId(rs.getInt(1));
            }
            t.setNumero(numero);
            t.setActivo(true);
            System.out.println("tarjeta creada correctamente, numero: " + numero);
            return true;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return false;
        }
    }

    public Tarjeta buscarPorId(int id) {
        String sql = "SELECT * FROM Tarjeta WHERE id = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return armarTarjeta(rs);
            }
            return null;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return null;
        }
    }

    public Tarjeta buscarPorNumero(String numero) {
        String sql = "SELECT * FROM Tarjeta WHERE numero = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, numero);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return armarTarjeta(rs);
            }
            return null;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return null;
        }
    }

    public boolean editar(int id, String clave, String fechaExp, double saldo, String tipo) {
        Tarjeta t = buscarPorId(id);
        if (t == null) {
            System.out.println("no existe esa tarjeta");
            return false;
        }
        if (!Validaciones.validarTipo(tipo)) {
            System.out.println("el tipo de tarjeta no es valido");
            return false;
        }
        if (saldo < 0) {
            System.out.println("el saldo no puede ser negativo");
            return false;
        }

        String sql = "UPDATE Tarjeta SET clave = ?, fechaExp = ?, saldo = ?, tipo = ? WHERE id = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, clave);
            ps.setString(2, fechaExp);
            ps.setDouble(3, saldo);
            ps.setString(4, tipo);
            ps.setInt(5, id);
            ps.executeUpdate();
            System.out.println("tarjeta actualizada correctamente");
            return true;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return false;
        }
    }

    public boolean desactivar(int id) {
        String sql = "UPDATE Tarjeta SET activo = 0 WHERE id = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            if (filas == 0) {
                System.out.println("no existe esa tarjeta");
                return false;
            }
            System.out.println("tarjeta desactivada");
            return true;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return false;
        }
    }

    public boolean mostrarTodas() {
        String sql = "SELECT * FROM Tarjeta";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean hayDatos = false;
            while (rs.next()) {
                System.out.println(armarTarjeta(rs));
                hayDatos = true;
            }
            if (!hayDatos) {
                System.out.println("no hay tarjetas todavia");
            }
            return hayDatos;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Tarjeta> listarPorUsuario(int idUsuario) {
        ArrayList<Tarjeta> resultado = new ArrayList<>();
        String sql = "SELECT * FROM Tarjeta WHERE idUsuario = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultado.add(armarTarjeta(rs));
            }
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
        return resultado;
    }

    private Tarjeta armarTarjeta(ResultSet rs) throws SQLException {
        Tarjeta t = new Tarjeta();
        t.setId(rs.getInt("id"));
        t.setClave(rs.getString("clave"));
        t.setNumero(rs.getString("numero"));
        t.setFechaExp(rs.getString("fechaExp"));
        t.setSaldo(rs.getDouble("saldo"));
        t.setTipo(rs.getString("tipo"));
        t.setActivo(rs.getBoolean("activo"));
        t.setIdUsuario(rs.getInt("idUsuario"));
        return t;
    }
}