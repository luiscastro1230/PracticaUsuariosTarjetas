package modelo;

public class Tarjeta {

    private int id;
    private String clave;
    private String numero;
    private String fechaExp;
    private double saldo;
    private String tipo;
    private boolean activo;
    private int idUsuario;

    public Tarjeta() {
    }

    public Tarjeta(String clave, String fechaExp, double saldo, String tipo, int idUsuario) {
        this.clave = clave;
        this.fechaExp = fechaExp;
        this.saldo = saldo;
        this.tipo = tipo;
        this.idUsuario = idUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFechaExp() {
        return fechaExp;
    }

    public void setFechaExp(String fechaExp) {
        this.fechaExp = fechaExp;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String toString() {
        return "id: " + id + " numero: " + numero + " tipo: " + tipo
                + " exp: " + fechaExp + " saldo: $" + saldo
                + " activo: " + activo + " idUsuario: " + idUsuario;
    }
}