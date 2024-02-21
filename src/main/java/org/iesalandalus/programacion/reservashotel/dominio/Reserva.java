package org.iesalandalus.programacion.reservashotel.dominio;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Reserva {
    public static final int MAX_NUMERO_MESES_RESERVA = 6;
    private static final int MAX_HORAS_POSTERIOR_CHECKOUT = 12;
    public static final String FORMATO_FECHA_RESERVA = "dd/MM/yy";
    public static final String FORMATO_FECHA_HORA_RESERVA = "dd/MM/yy HH:mm";
    private Huesped huesped;
    private Habitacion habitacion;
    private Regimen regimen;
    private LocalDate fechaInicioReserva;
    private LocalDate fechaFinReserva;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private double precio;
    private int numeroPersonas;

    public Reserva(Huesped huesped, Habitacion habitacion, Regimen regimen, LocalDate fechaInicioReserva, LocalDate fechaFinReserva, int numeroPersonas) {
        if (huesped==null)
            throw new NullPointerException("ERROR: El huésped de una reserva no puede ser nulo.");
        if(habitacion==null)
            throw new NullPointerException("ERROR: La habitación de una reserva no puede ser nula.");
        if(regimen==null)
            throw new NullPointerException("ERROR: El régimen de una reserva no puede ser nulo.");
        if(fechaInicioReserva==null)
            throw new NullPointerException("ERROR: La fecha de inicio de una reserva no puede ser nula.");
        if(fechaFinReserva==null)
            throw new NullPointerException("ERROR: La fecha de fin de una reserva no puede ser nula.");
        if(numeroPersonas<1)
            throw new IllegalArgumentException("ERROR: El número de personas de una reserva no puede ser menor o igual a 0.");
        setHuesped(huesped);
        setHabitacion(habitacion);
        setRegimen(regimen);
        setFechaInicioReserva(fechaInicioReserva);
        setFechaFinReserva(fechaFinReserva);
        setNumeroPersonas(numeroPersonas);
        setPrecio();
        precio=getPrecio();
    }

    public Reserva(Reserva reserva) {
        if (reserva==null)
            throw new NullPointerException("ERROR: No es posible copiar una reserva nula.");
        this.huesped=reserva.huesped;
        this.habitacion=reserva.habitacion;
        this.regimen=reserva.regimen;
        this.fechaInicioReserva=reserva.fechaInicioReserva;
        this.fechaFinReserva=reserva.fechaFinReserva;
        this.numeroPersonas=reserva.numeroPersonas;
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public void setHuesped(Huesped huesped) {
        if(huesped==null){
            throw new NullPointerException("Huésped nulo.");
        }
        this.huesped = huesped;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        if (habitacion==null){
            throw new NullPointerException("Habitación nula.");
        }
        this.habitacion = habitacion;
    }

    public Regimen getRegimen() {
        return regimen;
    }

    public void setRegimen(Regimen regimen) {
        if (regimen==null){
            throw new NullPointerException("Régimen nulo.");
        }
        this.regimen = regimen;
    }

    public LocalDate getFechaInicioReserva() {
        return fechaInicioReserva;
    }

    public void setFechaInicioReserva(LocalDate fechaInicioReserva) {
        if (fechaInicioReserva==null)
            throw new NullPointerException("ERROR: La fecha de inicio de una reserva no puede ser nula.");
        if (fechaInicioReserva.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("ERROR: La fecha de inicio de la reserva no puede ser anterior al día de hoy.");
        }
        if (fechaInicioReserva.isAfter(LocalDate.now().plusMonths(MAX_NUMERO_MESES_RESERVA))){
            throw new IllegalArgumentException("ERROR: La fecha de inicio de la reserva no puede ser posterior a seis meses.");
        }
        this.fechaInicioReserva = fechaInicioReserva;
    }

    public LocalDate getFechaFinReserva() {
        return fechaFinReserva;
    }

    public void setFechaFinReserva(LocalDate fechaFinReserva) {
        if (fechaFinReserva==null)
            throw new NullPointerException("ERROR: La fecha de fin de una reserva no puede ser nula.");
        if (fechaFinReserva.isBefore(fechaInicioReserva)||fechaFinReserva.isEqual(fechaInicioReserva)){ // se podría haber puesto '!isAfter'
            throw new IllegalArgumentException("ERROR: La fecha de fin de la reserva debe ser posterior a la de inicio.");
        }
        this.fechaFinReserva = fechaFinReserva;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        if (checkIn==null)
            throw new NullPointerException("ERROR: El checkin de una reserva no puede ser nulo.");
        fechaInicioReserva=getFechaInicioReserva();
        if (checkIn.isBefore(ChronoLocalDateTime.from(fechaInicioReserva.atStartOfDay()))){
            throw new IllegalArgumentException("ERROR: El checkin de una reserva no puede ser anterior a la fecha de inicio de la reserva.");
        }
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        if (checkOut==null)
            throw new NullPointerException("ERROR: El checkout de una reserva no puede ser nulo.");
        if (checkOut.isBefore(checkIn)){
            throw new IllegalArgumentException("ERROR: El checkout de una reserva no puede ser anterior al checkin.");
        }
        if (checkOut.isAfter(fechaFinReserva.atStartOfDay().plusHours(MAX_HORAS_POSTERIOR_CHECKOUT))){
            throw new IllegalArgumentException("ERROR: El checkout de una reserva puede ser como máximo 12 horas después de la fecha de fin de la reserva.");
        }
        this.checkOut = checkOut;
    }

    public double getPrecio() {
      //  this.precio=Habitacion.getPrecio();
        return precio;
    }

    private void setPrecio() {
        double precioHabitacion=getHabitacion().getPrecio();
        double precioRegimen=getRegimen().getIncrementoPrecio()* getNumeroPersonas();
        Period estancia = Period.between(getFechaInicioReserva(), getFechaFinReserva());
        this.precio=(precioHabitacion+precioRegimen)*estancia.getDays();
    }

    public int getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(int numeroPersonas) {
        int maximoPersonas = getHabitacion().getTipoHabitacion().getNumeroMaximoPersonas();
        if (numeroPersonas<1){
            throw new IllegalArgumentException("ERROR: El número de personas de una reserva no puede ser menor o igual a 0.");
        }
        if (numeroPersonas>maximoPersonas){
            throw new IllegalArgumentException("ERROR: El número de personas de una reserva no puede superar al máximo de personas establecidas para el tipo de habitación reservada.");
        }
        this.numeroPersonas = numeroPersonas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reserva reserva)) return false;
        return Objects.equals(getHabitacion(), reserva.getHabitacion()) && Objects.equals(getFechaInicioReserva(), reserva.getFechaInicioReserva());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHabitacion(), getFechaInicioReserva());
    }

    @Override
    public String toString() {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(FORMATO_FECHA_RESERVA);
        String fechaIniRes = formatoFecha.format(fechaInicioReserva);
        String fechaFinRes = formatoFecha.format(fechaFinReserva);
        String entrada="", salida ="";
        DateTimeFormatter formatoFechaHora = DateTimeFormatter.ofPattern(FORMATO_FECHA_HORA_RESERVA);
        if (checkIn==null)
            entrada = "No registrado";
        else entrada= formatoFechaHora.format(checkIn);
        if (checkOut==null)
            salida="No registrado";
        else salida=formatoFechaHora.format(checkOut);

        DecimalFormat formatoImporte = new DecimalFormat("0.00");
        String importe = formatoImporte.format(precio);
        return "Huesped: " + huesped.getNombre() + ' ' + huesped.getDni() +
                " Habitación:" + habitacion.getIdentificador() + " - " + habitacion.getTipoHabitacion() +
                " Fecha Inicio Reserva: " + fechaIniRes +
                " Fecha Fin Reserva: " + fechaFinRes +
                " Checkin: " + entrada +
                " Checkout: " + salida +
                " Precio: " + importe +
                " Personas: " + numeroPersonas;
    }
}
