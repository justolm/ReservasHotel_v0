package org.iesalandalus.programacion.reservashotel.vista;

public enum Opcion {
    SALIR("SALIR"),
    INSERTAR_HUESPED("INSERTAR HUESPED"),
    BUSCAR_HUESPED("BUSCAR HUÉSPED"),
    MOSTRAR_HUESPEDES("MOSTRAR HUÉSPEDES"),
    INSERTAR_HABITACION("INSERTAR HABITACIÓN"),
    BUSCAR_HABITACION("BUSCAR HABITACIÓN"),
    BORRAR_HABITACION("BORRAR HABITACIÓN"),
    MOSTRAR_HABITACIONES("MOSTRAR HABITACIONES"),
    INSERTAR_RESERVA("INSERTAR RESERVA"),
    ANULAR_RESERVA("ANULAR RESERVA"),
    MOSTRAR_RESERVAS("MOSTRAR RESERVAS"),
    CONSULTAR_DISPONIBILIDAD("CONSULTAR DISPONIBILIDAD");

    private final String mensajeAMostrar;
    private Opcion(String mensajeAMostrar){
        this.mensajeAMostrar = mensajeAMostrar;
    }

    @Override
    public String toString() {
        return ordinal() + ".- " + mensajeAMostrar + '\'';
    }
}
