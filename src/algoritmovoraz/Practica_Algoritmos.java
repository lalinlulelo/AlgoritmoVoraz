package algoritmovoraz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Practica_Algoritmos {

    static List<Actividad> reservas = new ArrayList<>();
    static String entrada = System.getProperty("user.dir")+File.separator+"entrada.txt"+File.separator;
    static String salida = System.getProperty("user.dir")+File.separator+"salida.txt"+File.separator;
    static boolean solucionEncontrada = false;
    static List<Actividad> solucion = new ArrayList<>();

    public static void main(String[] args) {
        /*
            * try { 
            * leerArchivo(entrada); 
            * long startTime = System.nanoTime();
            * otroMetodo(); 
            * long endTime = System.nanoTime();
                   long duration = (endTime - startTime);
                   double millis = duration / 1000000.0;
                   System.out.println("Tiempo de ejecuciﾃｳn: " + millis + "ms.");
            * 
            * } catch (Exception e) { // TODO Auto-generated catch block
            * e.printStackTrace(); 
            * }
        */
        
        try {
            // Se comprueba si hay argumentos
            if (args.length != 0) {
                    switch (args[0]) {
                        case "planifica":
                            if (args.length == 2) {
                                planifica(args[1], false);
                            } else if(args.length == 3){
                                planifica(args[1], args[2], false);
                            } else {
                                System.out.println("Falta fichero de entrada o de salida.");
                            }
                            break;

                        default:
                            System.out.println("Comando o metodo desconocido.");
                            break;
                    }
            }
            // Si no se ejecuta el procedimiento normal
            else {
                boolean salir = false;

                // Por si se quiere introducir datos por teclado
                while (!salir) {
                    System.out.println("Pulsar tecla 'S' para crear manual o aleatoriamente las actividades de entrada.");
                    System.out.println("En caso contrario pulsa 'N' para leer un archivo de entrada creado previamente.");

                    //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                    Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
                    String respuesta = entradaEscaner.nextLine ();

                    switch (respuesta.toLowerCase()) {
                        // Si
                        case "s":
                            salir = true;
                            
                            System.out.println ("Introduce el nombre del fichero de entrada que quieres crear:");                            
                            entrada =  System.getProperty("user.dir")+File.separator+entradaEscaner.nextLine ()+File.separator;
                            
                            System.out.println ("Introduce el nombre del fichero de salida que quieres crear:");
                            salida = System.getProperty("user.dir")+File.separator+entradaEscaner.nextLine ()+File.separator;
                            
                            planifica(entrada, salida, true);
                            break;

                        // No
                        case "n":
                            salir = true;
                            
                            System.out.println ("Introduce el nombre del fichero de entrada que quieres leer:");                            
                            entrada =  System.getProperty("user.dir")+File.separator+entradaEscaner.nextLine ()+File.separator;
                            
                            System.out.println ("Introduce el nombre del fichero de salida que quieres crear:");
                            salida = System.getProperty("user.dir")+File.separator+entradaEscaner.nextLine ()+File.separator;
                            
                            planifica(entrada, salida, false);
                            break;

                        default:
                            System.out.println("Comando desconocido, prueba otra vez.");
                            break;
                    }
                    //br.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metodo base que se encarga de leer los datos y ejecutar el algoritmo
    public static void planifica(String entrada, String salida, boolean teclado) {
        try {
            if (!teclado) {
                leerArchivo(entrada);
                
                // Ponemos un contador para saber cuanto tiempo tarda el algoritmo
                long startTime = System.nanoTime(); 
                voraz();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                double millis = duration / 1000000.0; // conversion a milisegundos.
                
                escribirArchivo(salida);
                System.out.println("Tiempo de ejecucion: " + millis + "ms.");
            } else {
                leerTeclado();
                
                long startTime = System.nanoTime();
                voraz();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                double millis = duration / 1000000.0;
                
                escribirArchivo(salida);
                System.out.println("Tiempo de ejecucion: " + millis + "ms.");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Error al leer o escribir en planifica.");
        }
    }

    ////////////////////////////////
    public static void planifica(String entrada, boolean teclado) {
        try {
            leerArchivo(entrada);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("");
        System.out.println("No se ha recibido parametro para fichero de salida, solo se imprimira por pantalla.");
        System.out.println("");
        
        // saber cuanto tiempo tarda el algoritmo
        long startTime = System.nanoTime(); // ponemos un contador para             
        voraz();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        double millis = duration / 1000000.0; // conversion a milisegundos.

        for (int i = 0; i < solucion.size(); i++) {
            System.out.println(solucion.get(i).toString());
        }

        System.out.println("Tiempo de ejecucion: " + millis + "ms.");
    }
    /////////////////////////////////////////////

    // Lee los datos desde archivo
    public static void leerArchivo(String entrada) throws IOException {
        File archivo = new File(entrada);
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);

        String primeraLinea = br.readLine();

        int actividades = CalcularNumero(primeraLinea);

        int actividadesIncluidas = 0;
        
        System.out.println("Fichero de entrada leido: ");
        
        while (actividadesIncluidas != actividades) {
            String linea = br.readLine();

            int n1 = CalcularNumeroEspacios(linea, true);
            int n2 = CalcularNumeroEspacios(linea, false);
            
            Actividad actividad = new Actividad(actividadesIncluidas, n1, n2);
            System.out.println(actividad.toString());

            reservas.add(actividad);
            actividadesIncluidas++;
        }
        
        System.out.println("Lectura de fichero de entrada terminado.");
        System.out.println();

        br.close();
    }

    // Lee los datos desde teclado
    public static void leerTeclado() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Escribe el numero de actividades a crear:");
        String num = br.readLine();

        int actividades = CalcularNumero(num);

        int actividadesIncluidas = 0;

        int inicio=1;
        int fin=0;

        System.out.println("Pulsa tecla '1' para generar aleatoriamente las actvidades con Inicio y fin de rango entre [0-511].");
        System.out.println("O pulsa 'cualquier otro numero' para crear tu mismo Inicio y Fin de las actvidades.");
        int boolAleatorio = 0;
        Scanner entradate = new Scanner(System.in);
        boolAleatorio = entradate.nextInt();
        boolean comprobar = false;

        do{
            try{
                if(boolAleatorio == 1){
                    while (actividadesIncluidas != actividades) {
                        //El inicio siempre tiene que ser menor que el fin en una actividad
                        while(inicio>=fin){
                            inicio = (int) (Math.random() * 511);
                            fin = (int) (Math.random() * 511);
                            //inicio = (int) (Math.random()*1000) + 1;
                            //fin = (int) (Math.random()*1000) + 1;
                        }
                        Actividad act = new Actividad(actividadesIncluidas,inicio, fin);
                        System.out.println(act.toString());
                        reservas.add(act);

                        inicio=1;
                        fin=0; 

                        actividadesIncluidas++;
                    }

                    System.out.println();
                    System.out.println("Generacion aleatoria terminada.");
                    System.out.println();

                    comprobar=true;
                    br.close();
                }else{         
                    System.out.println("Para crear una actividad debe escribir seguiendo la estrutura sin comillas de: 'Inicio' 'Fin'");
                    while (actividadesIncluidas != actividades) {
                        System.out.println("Escribe la actividad " + actividadesIncluidas);
                        String linea = br.readLine();

                        int n1 = CalcularNumeroEspacios(linea, true);
                        int n2 = CalcularNumeroEspacios(linea, false);

                        reservas.add(new Actividad(actividadesIncluidas, n1, n2));
                        actividadesIncluidas++;
                    }
                    
                    comprobar=true;
                    br.close();
                }
            }catch (InputMismatchException e){
                System.out.println("Error: valor introducido no es numero. ");
            }
        }while(comprobar == false);
        
        //Aqui asignamos el fichero txt que queremos guardar para entrada
        BufferedWriter out = new BufferedWriter(new FileWriter(entrada));
        
        out.write(actividades+"");
        out.write(System.getProperty("line.separator"));

        for(Actividad actividad:Practica_Algoritmos.reservas){               
            //Inicio
            out.write(actividad.getInicio() + " ");           
            //Fin
            out.write(actividad.getFin()+"");       
            //Salto de linea
            out.write(System.getProperty("line.separator"));
        }           
        out.close(); 
    }

    // Algoritmo voraz
    public static void voraz() { 															// O(n^2)
        while (!solucionEncontrada) { 														// O(n^2)
            // Ordena las actividades de orden creciente en fin
            ordenarFin(); 																	// O(n^2)
            // A�ade la primera a la solucion
            solucion.add(reservas.get(0)); 													// O(n)
            // Y actualiza las reservas para que no haya solapamientos
            actualizarReservas(); 															// O(n)

            // Si ya se ha terminado
            if (reservas.size() == 0) { 													// 0(1)
                    solucionEncontrada = true; 													// 0(1)
            }
        }
    }

    // Ordena las reservas de fin menor a mayor
    public static void ordenarFin() { 														// O(n^2)
        for (int i = 0; i < reservas.size(); i++) { 										// O(n^2)
            for (int j = 0; j < reservas.size() - 1; j++) { 								// O(n)
                if (reservas.get(j).getFin() > reservas.get(j + 1).getFin()) { 				// O(1)
                    Actividad aux = reservas.get(j); 										// O(1)
                    reservas.set(j, reservas.get(j + 1)); 									// O(1)
                    reservas.set(j + 1, aux); 												// O(1)
                } else if (reservas.get(j).getFin() == reservas.get(j + 1).getFin()) { 		// O(1)
                    if (reservas.get(j).getInicio() > reservas.get(j + 1).getInicio()) { 	// O(1)
                        Actividad aux = reservas.get(j); 									// O(1)
                        reservas.set(j, reservas.get(j + 1)); 								// O(1)
                        reservas.set(j + 1, aux); 											// O(1)
                    }
                }
            }
        }
    }

    // Actualiza las reservas y quita las que ya no son viables
    public static void actualizarReservas() { 												// O(n)
        Actividad ultima = solucion.get(solucion.size() - 1); 								// O(1)

        int tam = reservas.size(); 															// O(1)

        for (int i = 0; i < tam; i++) { 													// O(n)
            if (reservas.get(i).getInicio() < ultima.getFin()) { 							// O(1)
                reservas.remove(i); 														// O(n)
                tam--; 																		// O(1)
                i--; 																		// O(1)
            }
        }
    }

    public static void otroMetodo() {
        List<Actividad> aux = cogerIniciales();

        for (Actividad a : aux) {
                List<Actividad> sol = new ArrayList<>();
                otroMetodoRecursivo(sol, a.getFin(), cogerListaPosibles(), a);
        }
    }

    public static List<Actividad> cogerIniciales() {
        ordenarFin();
        boolean salir = false;
        List<Actividad> aux = new ArrayList<>();

        int tam = reservas.size();

        for (int i = 1; i < tam && !salir; i++) {
            if (reservas.get(i).getInicio() < reservas.get(0).getFin()) {
                aux.add(reservas.get(i));
                reservas.remove(i);
                i--;
                tam--;
            } else {
                // reservas.remove(0);
                // salir = true;
            }
        }

        return aux;
    }

    public static List<Actividad> cogerListaPosibles() {
        List<Actividad> aux = new ArrayList<>();

        aux.addAll(reservas);

        return aux;
    }

    public static void otroMetodoRecursivo(List<Actividad> solucion, int fin, List<Actividad> posibles, Actividad aniadir) {
        int tam = posibles.size();

        int cuenta = 0;
        List<Actividad> solucionTemporal = new ArrayList<>();
        solucionTemporal.addAll(solucion);
        solucionTemporal.add(aniadir);

        for (int i = 0; i < tam; i++) {
            if (posibles.get(i).getInicio() >= fin) {
                otroMetodoRecursivo(solucionTemporal, posibles.get(i).getFin(),
                        posibles.subList(i, posibles.size()), posibles.get(i));
                cuenta++;
            }
        }

        if (cuenta == 0) {
            System.out.println("Posible solucion: ");
            for (int i = 0; i < solucionTemporal.size(); i++) {
                if (solucionTemporal.get(i) != null)
                    System.out.println(solucionTemporal.get(i).toString());
            }
        }
    }

    // Metodo para escribir los datos de salida en archivo
    public static void escribirArchivo(String salida) throws FileNotFoundException {
        File txt = new File(salida);
        if (txt.exists()){
            System.out.println("Error: Archivo de salida ya existe");
        }else{

            try (PrintWriter out = new PrintWriter(salida)) {
                System.out.println("Imprimiendo datos por pantalla:");
                
                out.println("Numero de actividades solucion: " + solucion.size());
                
                for (int i = 0; i < solucion.size(); i++) {
                    out.println(solucion.get(i).toString());
                    System.out.println(solucion.get(i).toString());
                }
                System.out.println("Numero de actividades solucion: " + solucion.size());
            }

            System.out.println("Datos solucion guardados en el archivo: " + salida);
        }
    }

    // Metodo que calcula el numero inicial, que indica el numero de actividades
    public static int CalcularNumero(String linea) {
        int numero = 0;
        int multiplicador = 0;

        for (int i = linea.length() - 1; i >= 0; i--) {
            numero += Character.getNumericValue(linea.charAt(i)) * Math.pow(10, multiplicador);
            multiplicador++;
        }
        return numero;
    }

    // Metodo que calcula el inicio y fin de cada tarea divididas por espacios en cada linea
    public static int CalcularNumeroEspacios(String linea, boolean antesDeEspacio) {
        int numeroInicio = 0;
        int numeroFin = 0;
        int multiplicador = 0;

        boolean espacio = false;

        if (antesDeEspacio) {
            for (int i = linea.length() - 1; i >= 0; i--) {
                if (linea.charAt(i) == ' ') {
                    espacio = true;
                    multiplicador = 0;
                    continue;
                }

                if (espacio) {
                    numeroInicio += Character.getNumericValue(linea.charAt(i)) * Math.pow(10, multiplicador);
                }
                multiplicador++;
            }
        } else {
            for (int i = linea.length() - 1; i >= 0; i--) {

                if (linea.charAt(i) == ' ') {
                    espacio = true;
                    multiplicador = 0;
                    break;
                }

                if (!espacio) {
                    numeroFin += Character.getNumericValue(linea.charAt(i)) * Math.pow(10, multiplicador);
                }
                multiplicador++;
            }
        }

        if (antesDeEspacio) {
            return numeroInicio;
        } else {
            return numeroFin;
        }
    }
}