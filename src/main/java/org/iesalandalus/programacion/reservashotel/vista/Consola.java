package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.dominio.TipoHabitacion;
import org.iesalandalus.programacion.reservashotel.negocio.Huespedes;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;

public class Consola {
    private Consola(){}
    public void mostrarMenu(){
        System.out.println(Opcion.values().toString());
    }
    public int elegirOpcion(){// Opcion elegirOpcion(){
        Opcion elegido;
        int elegidoInt;
        do{
            System.out.print("Elige la opción deseada (0-12): ");
            elegidoInt=Entrada.entero();
        }while (elegidoInt<0 || elegidoInt>12);
        return elegidoInt;
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
        return new Huesped(leerHuesped()); //no es lo que tiene que hacer realmente.
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
        int planta=0, puerta=0;
        String identificador;
        Habitacion habitacion=null;
        System.out.println("Introduzca los datos de la habitación. ");
        System.out.print("Planta (1-3): ");
        planta=Entrada.entero();
        System.out.print("Puerta (0-14): ");
        puerta=Entrada.entero();
        identificador=planta+""+puerta;
        return habitacion;
    }
}
