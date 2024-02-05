package org.iesalandalus.programacion.reservashotel.dominio;

public enum TipoHabitacion {
    SUITE("SUITE",4),
    SIMPLE("SIMPLE",1),
    DOBLE("DOBLE",2),
    TRIPLE("TRIPLE",3);

    public final String cadenaAMostrar;
    public final int numeroMaximoPersonas;

    @Override
    public String toString() {
        return "TipoHabitacion{" +
                "cadenaAMostrar='" + cadenaAMostrar + '\'' +
                ", numeroMaximoPersonas=" + numeroMaximoPersonas +
                '}';
    }

    public int getNumeroMaximoPersonas() {
        return numeroMaximoPersonas;
    }

    TipoHabitacion(String cadenaAMostrar, int numeroMaximoPersonas){
        this.cadenaAMostrar = cadenaAMostrar;
        this.numeroMaximoPersonas = numeroMaximoPersonas;
    }

}
