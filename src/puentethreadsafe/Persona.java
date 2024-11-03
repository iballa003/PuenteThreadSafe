package puentethreadsafe;
/*
    Sistema que controla el paso de personas por un puente:
        - Siempre pasan en la misma direcci�n.
        - No pueden pasar m�s de tres personas a la vez.
        - No puede haber m�s de 200 kg de peso en ning�n momento.
        - El tiempo entre la llegada de dos personas es aleatorio entre 1 y 30 segundos.
        - El tiempo en atravesar el puente es aleatorio, entre 10 y 50 segundos.
        - Las personas tienen un peso aleatorio entre 40 y 120 kg.
*/
import java.util.logging.Level;
import java.util.logging.Logger;

public class Persona implements Runnable {
     // Atributos.
    private final String idPersona;
    private final int tiempoPaso;
    private final int pesoPersona;
    private final Puente puente;
    private final String direccion;

    // Constructor.
    public Persona(String idPersona, int tiempoPaso, int pesoPersona, Puente puente, String direccion) {
        this.idPersona = idPersona;
        this.tiempoPaso = tiempoPaso;
        this.pesoPersona = pesoPersona;
        this.puente = puente;
        this.direccion = direccion;
    }

    // Getters.
    public String getIdPersona() {
        return idPersona;
    }

    public int getPesoPersona() {
        return pesoPersona;
    }

    public String getDireccion() {
        return direccion;
    }

    // Método run.
    @Override
    public void run() {
        System.out.printf(">>> La %s con %d kilos quiere cruzar en %d segundos hacia %s.\n",
                idPersona, pesoPersona, tiempoPaso, direccion);
        // Entrar.
        try {
            puente.entrar(this);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        // Cruzar.
        try {
            Thread.sleep(tiempoPaso * 100);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        // Salir.
        puente.salir(this);
    }

}