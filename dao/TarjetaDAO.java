package dao;

import java.util.ArrayList;
import modelo.Tarjeta;
import modelo.Usuario;
import util.GeneradorID;
import util.Validaciones;

public class TarjetaDAO {

    ArrayList<Tarjeta> tarjetas = new ArrayList<>();
    int contador = 1;
    UsuarioDAO usuarioDAO;

    public TarjetaDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public boolean agregar(Tarjeta t) {
        try {

            Usuario dueño = usuarioDAO.buscarPorId(t.getIdUsuario());

            if (dueño == null) {
                System.out.println("no existe ese usuario");
                return false;
            }

            if (!dueño.isActivo()) {
                System.out.println("no se puede crear una tarjeta para un usuario eliminado");
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

            t.setNumero(numero);
            t.setId(contador);
            contador = contador + 1;
            t.setActivo(true);
            tarjetas.add(t);
            System.out.println("tarjeta creada correctamente, numero: " + numero);
            return true;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return false;
        }
    }

    public Tarjeta buscarPorId(int id) {
        for (Tarjeta t : tarjetas) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    public Tarjeta buscarPorNumero(String numero) {
        for (Tarjeta t : tarjetas) {
            if (t.getNumero().equals(numero)) {
                return t;
            }
        }
        return null;
    }

    public boolean editar(int id, String clave, String fechaExp, double saldo, String tipo) {
        try {
            Tarjeta t = buscarPorId(id);
            if (t == null) {
                System.out.println("no existe esa tarjeta");
                return false;
            }

            if (!t.isActivo()) {
                System.out.println("no se puede editar una tarjeta desactivada");
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
            t.setClave(clave);
            t.setFechaExp(fechaExp);
            t.setSaldo(saldo);
            t.setTipo(tipo);
            System.out.println("tarjeta actualizada correctamente");
            return true;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return false;
        }
    }

    public boolean desactivar(int id) {
        Tarjeta t = buscarPorId(id);
        if (t == null) {
            System.out.println("no existe esa tarjeta");
            return false;
        }
        t.setActivo(false);
        System.out.println("tarjeta desactivada");
        return true;
    }

    public boolean mostrarTodas() {
        if (tarjetas.isEmpty()) {
            System.out.println("no hay tarjetas todavia");
            return false;
        }
        for (Tarjeta t : tarjetas) {
            System.out.println(t);
        }
        return true;
    }

    public ArrayList<Tarjeta> listarPorUsuario(int idUsuario) {
        ArrayList<Tarjeta> resultado = new ArrayList<>();
        for (Tarjeta t : tarjetas) {
            if (t.getIdUsuario() == idUsuario) {
                resultado.add(t);
            }
        }
        return resultado;
    }
}
