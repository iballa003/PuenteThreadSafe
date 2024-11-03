package puentethreadsafe;

/*
    Objeto compartido entre los hilos.
*/
public class Puente {
  // Constantes.
    private static final int MAXIMO_PERSONAS = 4;
    private static final int MAXIMO_PESO = 300;
    private static final int MAXIMO_PERSONAS_POR_SENTIDO = 3;

    // Variables.
    private int numeroPersonas = 0;
    private int pesoPersonas = 0;
    private int personasNorteSur = 0;
    private int personasSurNorte = 0;

    // Constructor.
    public Puente() {
    }

    // Cruzar.
    public synchronized void entrar(Persona persona) throws InterruptedException {
        while ((numeroPersonas >= MAXIMO_PERSONAS) || (pesoPersonas + persona.getPesoPersona() > MAXIMO_PESO) ||
               (persona.getDireccion().equals("Norte-Sur") && personasNorteSur >= MAXIMO_PERSONAS_POR_SENTIDO) ||
               (persona.getDireccion().equals("Sur-Norte") && personasSurNorte >= MAXIMO_PERSONAS_POR_SENTIDO)) {
            System.out.printf("*** %s debe esperar para cruzar de %s.\n", persona.getIdPersona(), persona.getDireccion());
            this.wait();
        }

        numeroPersonas++;
        pesoPersonas += persona.getPesoPersona();
        if (persona.getDireccion().equals("Norte-Sur")) {
            personasNorteSur++;
        } else {
            personasSurNorte++;
        }
        System.out.printf("*** %s entra. Dirección: %s. Estado del puente: %d personas, %d kilos.\n",
                persona.getIdPersona(), persona.getDireccion(), numeroPersonas, pesoPersonas);
    }

    // Salir.
    public synchronized void salir(Persona persona) {
        numeroPersonas--;
        pesoPersonas -= persona.getPesoPersona();
        if (persona.getDireccion().equals("Norte-Sur")) {
            personasNorteSur--;
        } else {
            personasSurNorte--;
        }
        this.notifyAll();
        System.out.printf("*** %s sale. Dirección: %s. Estado del puente: %d personas, %d kilos.\n",
                persona.getIdPersona(), persona.getDireccion(), numeroPersonas, pesoPersonas);
    }
}
