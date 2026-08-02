package menu;

import java.util.ArrayList;
import java.util.Scanner;
import dao.TarjetaDAO;
import dao.UsuarioDAO;
import modelo.Tarjeta;
import modelo.Usuario;

public class Menu {

    static Scanner teclado = new Scanner(System.in);
    static UsuarioDAO usuarioDAO = new UsuarioDAO();
    static TarjetaDAO tarjetaDAO = new TarjetaDAO(usuarioDAO);

    public static void iniciar() {
        int op;
        do {
            System.out.println("\n===== menu principal =====");
            System.out.println("1. usuarios");
            System.out.println("2. tarjetas");
            System.out.println("3. login");
            System.out.println("0. salir");
            op = leerEntero();

            switch (op) {
                case 1:
                    menuUsuarios();
                    break;
                case 2:
                    menuTarjetas();
                    break;
                case 3:
                    login();
                    break;
                case 0:
                    System.out.println("hasta luego");
                    break;
                default:
                    System.out.println("esa opcion no existe");
            }
        } while (op != 0);
    }

    static void menuUsuarios() {
        int op;
        do {
            System.out.println("\n===== usuarios =====");
            System.out.println("1. crear");
            System.out.println("2. editar");
            System.out.println("3. eliminar");
            System.out.println("4. buscar");
            System.out.println("5. listar");
            System.out.println("0. regresar");
            op = leerEntero();

            switch (op) {
                case 1:
                    crearUsuario();
                    break;
                case 2:
                    editarUsuario();
                    break;
                case 3:
                    eliminarUsuario();
                    break;
                case 4:
                    buscarUsuario();
                    break;
                case 5:
                    usuarioDAO.mostrarTodos();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("esa opcion no existe");
            }
        } while (op != 0);
    }

    static void crearUsuario() {
        System.out.println("nombre:");
        String nombre = teclado.nextLine();
        System.out.println("apellido:");
        String apellido = teclado.nextLine();
        System.out.println("edad:");
        int edad = leerEntero();
        System.out.println("email:");
        String email = teclado.nextLine();
        System.out.println("password:");
        String password = teclado.nextLine();

        Usuario u = new Usuario(nombre, apellido, edad, email, password);
        usuarioDAO.agregar(u);
    }

    static void editarUsuario() {
        System.out.println("id del usuario a editar:");
        int id = leerEntero();
        Usuario u = usuarioDAO.buscarPorId(id);
        if (u == null) {
            System.out.println("no existe ese usuario");
            return;
        }
        System.out.println(u);

        System.out.println("nuevo nombre:");
        String nombre = teclado.nextLine();
        System.out.println("nuevo apellido:");
        String apellido = teclado.nextLine();
        System.out.println("nueva edad:");
        int edad = leerEntero();
        System.out.println("nuevo email:");
        String email = teclado.nextLine();
        System.out.println("nueva password (dejar en blanco si no la quieres cambiar):");
        String password = teclado.nextLine();

        usuarioDAO.editar(id, nombre, apellido, edad, email, password);
    }

    static void eliminarUsuario() {
        System.out.println("id del usuario a eliminar:");
        int id = leerEntero();
        usuarioDAO.eliminar(id);
    }

    static void buscarUsuario() {
        System.out.println("1. buscar por id");
        System.out.println("2. buscar por email");
        int op = leerEntero();

        Usuario u = null;
        if (op == 1) {
            System.out.println("id:");
            int id = leerEntero();
            u = usuarioDAO.buscarPorId(id);
        } else if (op == 2) {
            System.out.println("email:");
            String email = teclado.nextLine();
            u = usuarioDAO.buscarPorEmail(email);
        } else {
            System.out.println("esa opcion no existe");
            return;
        }

        if (u != null) {
            System.out.println(u);
        } else {
            System.out.println("no se encontro ese usuario");
        }
    }

    static void menuTarjetas() {
        int op;
        do {
            System.out.println("\n===== tarjetas =====");
            System.out.println("1. crear");
            System.out.println("2. editar");
            System.out.println("3. desactivar");
            System.out.println("4. buscar");
            System.out.println("5. listar todas");
            System.out.println("6. listar tarjetas de un usuario");
            System.out.println("0. regresar");
            op = leerEntero();

            switch (op) {
                case 1:
                    crearTarjeta();
                    break;
                case 2:
                    editarTarjeta();
                    break;
                case 3:
                    desactivarTarjeta();
                    break;
                case 4:
                    buscarTarjeta();
                    break;
                case 5:
                    tarjetaDAO.mostrarTodas();
                    break;
                case 6:
                    listarTarjetasDeUsuario();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("esa opcion no existe");
            }
        } while (op != 0);
    }

    static void crearTarjeta() {
        System.out.println("id del usuario dueño de la tarjeta:");
        int idUsuario = leerEntero();

        System.out.println("clave (numero de 3 o 4 digitos):");
        String clave = teclado.nextLine();
        System.out.println("fecha de expiracion (formato 08/31):");
        String fecha = teclado.nextLine();
        System.out.println("saldo inicial:");
        double saldo = leerDecimal();
        String tipo = leerTipoTarjeta();

        Tarjeta t = new Tarjeta(clave, fecha, saldo, tipo, idUsuario);
        tarjetaDAO.agregar(t);
    }

    static void editarTarjeta() {
        System.out.println("id de la tarjeta a editar:");
        int id = leerEntero();
        Tarjeta t = tarjetaDAO.buscarPorId(id);
        if (t == null) {
            System.out.println("no existe esa tarjeta");
            return;
        }
        System.out.println(t);

        System.out.println("nueva clave:");
        String clave = teclado.nextLine();
        System.out.println("nueva fecha de expiracion:");
        String fecha = teclado.nextLine();
        System.out.println("nuevo saldo:");
        double saldo = leerDecimal();
        String tipo = leerTipoTarjeta();

        tarjetaDAO.editar(id, clave, fecha, saldo, tipo);
    }

    static void desactivarTarjeta() {
        System.out.println("id de la tarjeta a desactivar:");
        int id = leerEntero();
        tarjetaDAO.desactivar(id);
    }

    static void buscarTarjeta() {
        System.out.println("1. buscar por id");
        System.out.println("2. buscar por numero");
        int op = leerEntero();

        Tarjeta t = null;
        if (op == 1) {
            System.out.println("id:");
            int id = leerEntero();
            t = tarjetaDAO.buscarPorId(id);
        } else if (op == 2) {
            System.out.println("numero:");
            String numero = teclado.nextLine();
            t = tarjetaDAO.buscarPorNumero(numero);
        } else {
            System.out.println("esa opcion no existe");
            return;
        }

        if (t != null) {
            System.out.println(t);
        } else {
            System.out.println("no se encontro esa tarjeta");
        }
    }

    static void listarTarjetasDeUsuario() {
        System.out.println("id del usuario:");
        int idUsuario = leerEntero();

        ArrayList<Tarjeta> tarjetas = tarjetaDAO.listarPorUsuario(idUsuario);
        if (tarjetas.isEmpty()) {
            System.out.println("este usuario no tiene tarjetas");
            return;
        }
        for (Tarjeta t : tarjetas) {
            System.out.println(t);
        }
    }
}
