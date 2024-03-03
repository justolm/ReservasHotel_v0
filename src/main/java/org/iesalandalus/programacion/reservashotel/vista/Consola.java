package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;

public class Consola {
    private Consola(){}
    public void mostrarMenu(){
        System.out.println(Opcion.values().toString());
    }
    public Opcion elegirOpcion(){
        Opcion elegido;
        int elegidoInt, numOpciones;
        numOpciones = Opcion.values().length-1;
        do{
            System.out.print("Elige la opción deseada (0 - "+numOpciones+"): ");
            elegidoInt=Entrada.entero();
        }while (elegidoInt<0 || elegidoInt>numOpciones);
        elegido=Opcion.valueOf(String.valueOf(elegidoInt));
        return elegido;
    }
    public Huesped leerHuesped(){
        String nombre, dni, correo, telefono;
        LocalDate fechaNacimiento;
        System.out.println("Introduzca los datos del huésped. ");
        System.out.print("Nombre: ");
        nombre=Entrada.cadena();
        System.out.print("DNI: ");
        dni=Entrada.cadena();
        System.out.print("Correo electrónico: ");
        correo=Entrada.cadena();
        System.out.print("Teléfono: ");
        telefono=Entrada.cadena();
        System.out.print("Fecha de nacimiento: ");
        fechaNacimiento= LocalDate.parse(Entrada.cadena());
        return (new Huesped(nombre, dni, correo, telefono, fechaNacimiento));
    }
    public Huesped getHuespedPorDni(){
        String dni;
        System.out.println("Introduzca el DNI del huésped: ");
        dni=Entrada.cadena();
        if (dni==null){
            throw new NullPointerException("ERROR: No se puede buscar un DNI vacío.");
        }
        return new Huesped("nombre",dni,"correo","950000000", LocalDate.of(2000,1,1));
    }
    public LocalDate leerFecha(String mensaje) {
        LocalDate fecha;
        if (mensaje == null) {
            throw new NullPointerException("ERROR: La fecha está vacía.");
        }
        if (mensaje.matches(Huesped.FORMATO_FECHA)) {
            fecha = LocalDate.parse(mensaje);
        } else {
            throw new IllegalArgumentException("ERROR: La fecha no tiene un formato válido.");
        }
        return fecha;
    }
    public Habitacion leerHabitacion(){
        int planta=0, puerta=0, precio=0;
        TipoHabitacion tipoHabitacion=null;
        Habitacion habitacion=null;
        System.out.println("Introduzca los datos de la habitación. ");
        System.out.print("Planta (1-3): ");
        planta=Entrada.entero();
        System.out.print("Puerta (0-14): ");
        puerta=Entrada.entero();
        System.out.print("Introduzca el precio (" + Habitacion.MIN_PRECIO_HABITACION + " - " + Habitacion.MAX_PRECIO_HABITACION + "): ");
        precio=Entrada.entero();
        System.out.print("Elija el tipo de habitación: ");
        System.out.println(TipoHabitacion.values().toString());
        tipoHabitacion=TipoHabitacion.valueOf(Entrada.cadena());
        habitacion=new Habitacion(planta, puerta, precio, tipoHabitacion);
        return habitacion;
    }
    public Habitacion leerHabitacionPorIdentificador(){
        int planta, puerta;
        String identificador;
        Habitacion habitacion=null;
        System.out.println("Introduzca los datos de la habitación. ");
        System.out.print("Planta (" + Habitacion.MIN_NUMERO_PLANTA + " - " + Habitacion.MAX_NUMERO_PLANTA + "): ");
        planta=Entrada.entero();
        if (planta<Habitacion.MIN_NUMERO_PLANTA || planta>Habitacion.MAX_NUMERO_PLANTA){
            throw new IllegalArgumentException("ERROR: El número de planta es incorrecto.");
        }
        System.out.print("Puerta (" + Habitacion.MIN_NUMERO_PUERTA + " - " + Habitacion.MAX_NUMERO_PUERTA + "): ");
        puerta=Entrada.entero();
        if (puerta<Habitacion.MIN_NUMERO_PUERTA || puerta>Habitacion.MAX_NUMERO_PUERTA){
            throw new IllegalArgumentException("ERROR: El número de puerta es incorrecto.");
        }
        identificador=planta+""+puerta;
        habitacion = new Habitacion(planta,puerta,40,TipoHabitacion.SIMPLE);
        return habitacion;
    }
    public TipoHabitacion leerTipoHabitacion(){
        TipoHabitacion tipoHabitacion;
        System.out.print("Elija el tipo de habitación: ");
        System.out.println(TipoHabitacion.values().toString());
        tipoHabitacion=TipoHabitacion.valueOf(Entrada.cadena());
        return tipoHabitacion;
    }
    public Regimen leerRegimen(){
        Regimen regimen;
        System.out.println("Elija el régimen deseado: ");
        System.out.println(Regimen.values().toString());
        regimen=Regimen.valueOf(Entrada.cadena());
        return regimen;
    }
    public Reserva leerReserva(){
        Reserva reserva;
        Huesped huesped;
        Habitacion habitacion;
        Regimen regimen;
        TipoHabitacion tipoHabitacion;
        LocalDate fechaInicioReserva, fechaFinReserva;
        int numeroPersonas,maximoPersonas=5;
        huesped=new Huesped(leerHuesped());
        habitacion=new Habitacion(leerHabitacion());
        regimen= leerRegimen();
        System.out.print("Introduzca la fecha de inicio de reserva (dd/MM/aa): ");
        fechaInicioReserva=leerFecha(Entrada.cadena());
        System.out.print("Introduzca la fecha de fin de reserva (dd/MM/aa): ");
        fechaFinReserva=leerFecha(Entrada.cadena());
        tipoHabitacion= habitacion.getTipoHabitacion();
        maximoPersonas= tipoHabitacion.getNumeroMaximoPersonas();
        do{
            System.out.print("Introduzca el número de personas: ");
            numeroPersonas=Entrada.entero();
        }while (numeroPersonas<0 || numeroPersonas>maximoPersonas);
        reserva=new Reserva(huesped,habitacion,regimen,fechaInicioReserva,fechaFinReserva,numeroPersonas);
        return reserva;
    }
}
