package puentethreadsafe;
import java.util.Random;

public class PuenteThreadSafe {

    public static void main(String[] args) {
        // Constantes.
        final int minimoTiempoLlegada = 1;
        final int maximoTiempoLlegada = 30;
        final int minimoTiempoPaso = 10;
        final int maximoTiempoPaso = 50;
        final int minimoPesoPersona = 40;
        final int maximoPesoPersona = 120;

        // Variables.
        final Puente puente = new Puente();
        int tiempoLlegada;
        int tiempoPaso;
        int pesoPersona;
        String idPersona;
        
        // Bucle infinito creando personas para cruzar el puente.
        int numeroPersona = 0;
        while (true) {
            // Crear persona.
            numeroPersona++;
            idPersona = "Persona " + numeroPersona;
            tiempoLlegada = numeroAleatorio(minimoTiempoLlegada, maximoTiempoLlegada);
            tiempoPaso = numeroAleatorio(minimoTiempoPaso, maximoTiempoPaso);
            pesoPersona = numeroAleatorio(minimoPesoPersona, maximoPesoPersona);
            String direccion = numeroPersona % 2 == 0 ? "Norte-Sur" : "Sur-Norte";
            System.out.printf("La %s llegará en %d segundos, pesa %d kilos y tardará en cruzar %d segundos.\n",
                    idPersona, tiempoLlegada, pesoPersona, tiempoPaso);
            Thread hiloPersona = new Thread(new Persona(idPersona, tiempoPaso, pesoPersona, puente, direccion));
            // Esperar a que llegue.
            try {
                // Esperar un tiempo.
                Thread.sleep(tiempoLlegada * 100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            // Cruzar.
            hiloPersona.start();
        }
    }

    public static int numeroAleatorio(int valorMinimo, int valorMaximo) {
        Random r = new Random();
        return valorMinimo + r.nextInt(valorMaximo - valorMinimo + 1);
    }
}

