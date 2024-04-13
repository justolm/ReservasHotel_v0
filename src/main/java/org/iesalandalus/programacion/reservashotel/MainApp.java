package org.iesalandalus.programacion.reservashotel;


import org.iesalandalus.programacion.reservashotel.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.dominio.TipoHabitacion;
import org.iesalandalus.programacion.reservashotel.negocio.Habitaciones;
import org.iesalandalus.programacion.reservashotel.negocio.Huespedes;
import org.iesalandalus.programacion.reservashotel.negocio.Reservas;
import org.iesalandalus.programacion.reservashotel.vista.Consola;
import org.iesalandalus.programacion.reservashotel.vista.Opcion;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

public class MainApp {
    public static final int CAPACIDAD=5;
    private static Habitaciones habitaciones=new Habitaciones(CAPACIDAD);
    private static Reservas reservas=new Reservas(CAPACIDAD);
    private static Huespedes huespedes=new Huespedes(CAPACIDAD);
    private static boolean inicializar=true; //borrar

    public static void main(String[] args) {
        try{
            Opcion opcion = null;
            do{
                if (opcion!=null){
                    System.out.println("Presione ENTER para continuar...");
                    Entrada.cadena();
                }
                Consola.mostrarMenu();
                opcion=Consola.elegirOpcion();
                ejecutarOpcion(opcion);
            }while (opcion!=Opcion.SALIR);
            System.out.println("Hasta luego!!!!");
        } catch (NullPointerException | IllegalArgumentException | DateTimeException | OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void ejecutarOpcion(Opcion opcion) throws IllegalArgumentException, NullPointerException, OperationNotSupportedException {
        switch (opcion)
        {
            case INSERTAR_HUESPED:
                insertarHuesped();
                break;
            case BUSCAR_HUESPED:
                // borrar hasta el siguiente comentario

                if (inicializar){
                    Huesped huesped1 = new Huesped("Justo Lopez", "45596798b", "justo@gmail.com", "666619806", LocalDate.of(1980, 11, 19));
                    Habitacion habitacion1 = new Habitacion(1,0,40,TipoHabitacion.SIMPLE);
                    Habitacion habitacion2 = new Habitacion(2,2,60,TipoHabitacion.DOBLE);
                    Habitacion habitacion3 = new Habitacion(3,13,80,TipoHabitacion.SUITE);
                    Habitacion habitacion4 = new Habitacion(3,12,81,TipoHabitacion.SUITE);
                    try {
                        huespedes.insertar(huesped1);
                        habitaciones.insertar(habitacion1);
                        habitaciones.insertar(habitacion2);
                        habitaciones.insertar(habitacion3);
                        habitaciones.insertar(habitacion4);
                    } catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
                        throw new RuntimeException(e);
                    }
                    inicializar=false;
                }

                // borrar desde el anterior comentario.
                buscarHuesped();
                break;
            case BORRAR_HUESPED:
                borrarHuesped();
                break;
            case MOSTRAR_HUESPEDES:
                mostrarHuespedes();
                break;
            case INSERTAR_HABITACION:
                insertarHabitacion();
                break;
            case BUSCAR_HABITACION:
                buscarHabitacion();
                break;
            case BORRAR_HABITACION:
                borrarHabitacion();
                break;
            case MOSTRAR_HABITACIONES:
                mostrarHabitaciones();
                break;
            case INSERTAR_RESERVA:
                insertarReserva();
                break;
            case LISTAR_RESERVA:
                int numOpcion;
                System.out.println("Introduzca '1' para buscar por huésped o '2' para buscar por tipo de habitación: ");
                numOpcion=Entrada.entero();
                Huesped huesped;
                TipoHabitacion tipoHabitacion1;
                if (numOpcion==1){
                    huesped=Consola.getHuespedPorDni();
                    listarReservas(huesped);
                }
                else if (numOpcion==2) {
                    tipoHabitacion1=Consola.leerTipoHabitacion();
                    listarReservas(tipoHabitacion1);
                }
                else throw new IllegalArgumentException("ERROR: Ha introducido una opción no válida.");
                break;
            case ANULAR_RESERVA:
                anularReserva();
                break;
            case MOSTRAR_RESERVAS:
                mostrarReservas();
                break;
            case CONSULTAR_DISPONIBILIDAD:
                TipoHabitacion tipoHabitacion;
                LocalDate fechaInicioReserva, fechaFinReserva;
                System.out.println("Introduzca el tipo de habitación: ");
                tipoHabitacion = Consola.leerTipoHabitacion();
                System.out.println("Introduzca la fecha de entrada (dd/mm/aa): ");
                fechaInicioReserva = Consola.leerFecha(Entrada.cadena());
                System.out.println("Introduzca la fecha de salida (dd/mm/aa): ");
                fechaFinReserva = Consola.leerFecha(Entrada.cadena());
                Habitacion habitacion1=consultarDisponibilidad(tipoHabitacion, fechaInicioReserva, fechaFinReserva);
                System.out.println(habitacion1);
                break;
            case SALIR:
                break;
        }
    }
    private static void insertarHuesped() {
        Huesped huesped;
        try {
            huesped=Consola.leerHuesped();
            huespedes.insertar(huesped);
            System.out.println("Huésped insertado correctamente.");
        } catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void buscarHuesped() {
        Huesped huesped, huespedEncontrado;
        try {
            huesped=Consola.getHuespedPorDni();
            huespedEncontrado=huespedes.buscar(huesped);
            if (huespedEncontrado!=null){
                System.out.println("Huésped encontrado.");
                System.out.println(huespedEncontrado);
            }
            else {
                System.out.println("El huésped indicado no existe.");
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void borrarHuesped(){
        Huesped huesped;
        try {
            huesped=Consola.getHuespedPorDni();
            huespedes.borrar(huesped);
            System.out.println("Huésped eliminado.");
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void mostrarHuespedes(){
        try {
            if (huespedes.getTamano()>0){
                for (int i=0 ; i< huespedes.getTamano() ; i++){
                    if (huespedes.get()[i]!=null){
                        System.out.println(huespedes.get()[i]);
                    }
                }
            }
            else {
                System.out.println("No hay huéspedes para mostrar.");
            }
        } catch (NullPointerException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
    private static void insertarHabitacion(){
        try {
            Habitacion habitacion;
            habitacion = Consola.leerHabitacion();
            habitaciones.insertar(habitacion);
            System.out.println("Habitación insertada correctamente.");
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }
    }
    private static void buscarHabitacion(){
        Habitacion habitacion, habitacionEncontrada;
        try {
            habitacion = Consola.leerHabitacionPorIdentificador();
            habitacionEncontrada = habitaciones.buscar(habitacion);
            if (habitacionEncontrada!=null){
                System.out.println("Habitación encontrada.");
                System.out.println(habitacionEncontrada);
            }
        } catch (NullPointerException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
    private static void borrarHabitacion(){
        Habitacion habitacion;
        try {
            habitacion = Consola.leerHabitacionPorIdentificador();
            habitaciones.borrar(habitacion);
            System.out.println("Habitación borrada.");
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }
    }
    private static void mostrarHabitaciones(){
        try {
            if(habitaciones.getTamano()>0){
                for (Habitacion habitacion : habitaciones.get()){
                    if (habitacion!=null){
                        System.out.println(habitacion);
                    }
                }
            }
            else {
                System.out.println("No hay habitaciones para mostrar.");
            }
        } catch (NullPointerException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
    private static void insertarReserva() {
        Reserva reserva;
        Habitacion habitacion;
        try {
            reserva=Consola.leerReserva();
            habitacion = consultarDisponibilidad(reserva.getHabitacion().getTipoHabitacion(), reserva.getFechaInicioReserva(), reserva.getFechaFinReserva());
            if (habitacion!=null){
                reservas.insertar(new Reserva(reserva.getHuesped(),habitacion,reserva.getRegimen(),reserva.getFechaInicioReserva(),reserva.getFechaFinReserva(),reserva.getNumeroPersonas()));
                System.out.println("Reserva insertada correctamente.");
            }
            else {
                System.out.println("ERROR: No hay habitaciones disponibles para esa fecha.");
            }
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }
    }
    private static void listarReservas (Huesped huesped){
        if (huesped==null){
            System.out.println("ERROR: No se pueden listar reservas de un huésped nulo.");
        }
        else {
            try {
                if (reservas.getReservas(huesped)!=null){
                    for(Reserva reserva : reservas.getReservas(huesped)){
                        if (reserva!=null){
                            System.out.println(reserva);
                        }
                    }
                }
                else {
                    System.out.println("No existen reservas para el huésped indicado.");
                }
            } catch (NullPointerException | IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }
    private static void listarReservas (TipoHabitacion tipoHabitacion){
        if (tipoHabitacion==null){
            System.out.println("ERROR: No se pueden listar reservas de un tipo de habitación nulo.");
        }
        else {
            try {
                if (reservas.getReservas(tipoHabitacion)!=null){
                    for (Reserva reserva : reservas.getReservas(tipoHabitacion)){
                        if (reserva!=null){
                            System.out.println(reserva);
                        }
                    }
                }
                else {
                    System.out.println("No existen reservas para el tipo de habitación indicado.");
                }
            } catch (NullPointerException | IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }
    private static Reserva[] getReservasAnulables(Reserva[] reservasAAnular) {
        if (reservasAAnular==null){
            return null;
            //throw new NullPointerException("ERROR: El listado de reservas está vacío.");
        }
        Reserva[] reservasAnulables=new Reserva[reservasAAnular.length];
        int reser = 0;
        for (int i=0 ; i<reservasAAnular.length ; i++){
            if (reservasAAnular[i]!=null){
                if (reservasAAnular[i].getFechaInicioReserva().isAfter(LocalDate.now())){
                    reservasAnulables[reser]=new Reserva(reservasAAnular[i]);
                    reser++;
                }
            }
        }
        return reservasAnulables;
    }
    private static void anularReserva() throws NullPointerException, IllegalArgumentException {
        int reservasAnulables = 0;
        String eleccion;
        Huesped huesped = new Huesped(Consola.getHuespedPorDni());
        Reserva[] reservas1 = new Reserva[reservas.getTamano()];
        reservas1 = getReservasAnulables(reservas.getReservas(huesped));
        if (reservas1==null){
            throw new NullPointerException("ERROR: No hay reservas anulables para ese cliente.");
        } else if (getNumElementosNoNulos(reservas1)==1) {
            do {
                System.out.println("¿Confirma que desea eliminar la reserva (S/N): " + reservas1[0].toString() + " ?");
                eleccion = Entrada.cadena();
                if (eleccion.equalsIgnoreCase("S")) {
                    try {
                        reservas.borrar(reservas1[0]);
                    } catch (OperationNotSupportedException | NullPointerException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Reserva eliminada.");
                }
            } while (!eleccion.equalsIgnoreCase("s")&&!eleccion.equalsIgnoreCase("n"));
        } else {
            System.out.println("Se han encontrado varias reservas para el huésped indicado. Elija la que desea eliminar: ");
            for (Reserva reserva : reservas1){
                if (reserva!=null){
                    System.out.println(reservasAnulables + ": " + reserva);
                    reservasAnulables++;
                }
            }
            do {
                eleccion = Entrada.cadena();
            } while (Integer.parseInt(eleccion) > reservasAnulables || Integer.parseInt(eleccion) < 0);
            try {
                reservas.borrar(reservas1[Integer.parseInt(eleccion)]);
            } catch (OperationNotSupportedException | NullPointerException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Reserva eliminada.");
        }
    }
    private static void mostrarReservas() throws NullPointerException {
        Reserva[] reservas1 = new Reserva[reservas.getTamano()];
        reservas1 = reservas.get();
        int numReservas = 0;
        if (reservas==null){
            throw new NullPointerException("ERROR: No hay reservas almacenadas.");
        }
        for (Reserva reserva2 : reservas1){
            if (reserva2!=null){
                System.out.println(reserva2.toString());
                numReservas++;
            }
        }
        if (numReservas==0){
            System.out.println("No hay reservas para mostrar.");
        }
    }
    private static int getNumElementosNoNulos(Reserva[] reserva) throws NullPointerException, IllegalArgumentException {
        int reser = 0;
        if (reserva==null){
            throw new NullPointerException("ERROR: El listado de reservas está vacío.");
        }
        for (Reserva reserva1: reserva){
            if (reserva1!=null){
                reser++;
            }
        }
        /*if (reser==0){
            throw new IllegalArgumentException("ERROR: El listado de reservas no contiene reservas válidas.");
        }*/
        return reser;
    }
    private static Habitacion consultarDisponibilidad(TipoHabitacion tipoHabitacion, LocalDate fechaInicioReserva, LocalDate fechaFinReserva)
    {
        boolean tipoHabitacionEncontrada=false;
        Habitacion habitacionDisponible=null;
        int numElementos=0;

        Habitacion[] habitacionesTipoSolicitado= habitaciones.get(tipoHabitacion);

        if (habitacionesTipoSolicitado==null)
            return habitacionDisponible;

        for (int i=0; i<habitacionesTipoSolicitado.length && !tipoHabitacionEncontrada; i++)
        {

            if (habitacionesTipoSolicitado[i]!=null)
            {
                Reserva[] reservasFuturas = reservas.getReservasFuturas(habitacionesTipoSolicitado[i]);
                numElementos=getNumElementosNoNulos(reservasFuturas);

                if (numElementos == 0)
                {
                    //Si la primera de las habitaciones encontradas del tipo solicitado no tiene reservas en el futuro,
                    // quiere decir que est� disponible.
                    habitacionDisponible=new Habitacion(habitacionesTipoSolicitado[i]);
                    tipoHabitacionEncontrada=true;
                }
                else {

                    //Ordenamos de mayor a menor las reservas futuras encontradas por fecha de fin de la reserva.
                    // Si la fecha de inicio de la reserva es posterior a la mayor de las fechas de fin de las reservas
                    // (la reserva de la posici�n 0), quiere decir que la habitaci�n est� disponible en las fechas indicadas.

                    Arrays.sort(reservasFuturas, 0, numElementos, Comparator.comparing(Reserva::getFechaFinReserva).reversed());

                    /*System.out.println("\n\nMostramos las reservas ordenadas por fecha de inicio de menor a mayor (numelementos="+numElementos+")");
                    mostrar(reservasFuturas);*/

                    if (fechaInicioReserva.isAfter(reservasFuturas[0].getFechaFinReserva())) {
                        habitacionDisponible = new Habitacion(habitacionesTipoSolicitado[i]);
                        tipoHabitacionEncontrada = true;
                    }

                    if (!tipoHabitacionEncontrada)
                    {
                        //Ordenamos de menor a mayor las reservas futuras encontradas por fecha de inicio de la reserva.
                        // Si la fecha de fin de la reserva es anterior a la menor de las fechas de inicio de las reservas
                        // (la reserva de la posici�n 0), quiere decir que la habitaci�n est� disponible en las fechas indicadas.

                        Arrays.sort(reservasFuturas, 0, numElementos, Comparator.comparing(Reserva::getFechaInicioReserva));

                        /*System.out.println("\n\nMostramos las reservas ordenadas por fecha de inicio de menor a mayor (numelementos="+numElementos+")");
                        mostrar(reservasFuturas);*/

                        if (fechaFinReserva.isBefore(reservasFuturas[0].getFechaInicioReserva())) {
                            habitacionDisponible = new Habitacion(habitacionesTipoSolicitado[i]);
                            tipoHabitacionEncontrada = true;
                        }
                    }

                    //Recorremos el array de reservas futuras para ver si las fechas solicitadas est�n alg�n hueco existente entre las fechas reservadas
                    if (!tipoHabitacionEncontrada)
                    {
                        for(int j=1;j<reservasFuturas.length && !tipoHabitacionEncontrada;j++)
                        {
                            if (reservasFuturas[j]!=null && reservasFuturas[j-1]!=null)
                            {
                                if(fechaInicioReserva.isAfter(reservasFuturas[j-1].getFechaFinReserva()) &&
                                        fechaFinReserva.isBefore(reservasFuturas[j].getFechaInicioReserva())) {

                                    habitacionDisponible = new Habitacion(habitacionesTipoSolicitado[i]);
                                    tipoHabitacionEncontrada = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return habitacionDisponible;
    }
}
