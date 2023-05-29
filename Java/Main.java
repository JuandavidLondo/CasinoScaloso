import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.time.Instant;


public class Main {

    private static double balance = 0; // Saldo inicial del jugador
    private static double balanceingresado = 0;
    private static int victoriaRule = 0;
    private static int derroRule = 0;
    private static int victocaba = 0;
    private static int derrocaba = 0;
    private static int victotraga = 0;
    private static int derrotraga = 0;
    private static Instant inicio = Instant.now();
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, Integer> valores = new HashMap<>();



    public static void main(String[] args) throws InterruptedException {
        valores.put("rojo", 2);
        valores.put("negro", 2);
        valores.put("verde", 35);
        int opcion;

        System.out.println("¡Bienvenido al Casino!");

        do {
            mostrarMenu();
            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    ingresarDinero();
                    break;
                case 2:
                    jugarRuleta();
                    break;
                case 3:
                    jugarCaballos();
                    break;
                case 4:
                    jugarTragamonedas();
                    break;
                case 5:
                    estadisticasJug();
                    break;
                case 6:
                    System.out.println("¡Gracias por jugar! Hasta luego.");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
                    break;
            }

            System.out.println();
        } while (opcion != 6);

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("--------------------");
        System.out.println("Saldo actual: $" + balance);
        System.out.println("--------------------");
        System.out.println("1. Ingresar dinero");
        System.out.println("2. Jugar a la ruleta");
        System.out.println("3. Jugar a los caballos");
        System.out.println("4. Jugar a la tragamonedas");
        System.out.println("5. Ver estadisticas");
        System.out.println("6. Salir");
        System.out.print("Ingrese su opción: ");
    }

    private static int leerOpcion() {
        while (true) {
            if (scanner.hasNextInt()) {
                int opcion = scanner.nextInt();
                scanner.nextLine();
                return opcion;
            } else {
                System.out.println("Ingrese un número válido.");
                scanner.nextLine();
            }
        }
    }


    private static void ingresarDinero() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese su targeta: ");
        int tarjeta = leerOpcion();
        double monto;

        System.out.print("Ingrese la cantidad de dinero a ingresar: ");
        monto = scanner.nextDouble();

        if (monto > 0) {
            if (monto > Double.MAX_EXPONENT){
                System.out.println("No se puede ingresar tanto dinero");
            }
            else {
                balance += monto;
                balanceingresado += monto;
                System.out.println("Dinero ingresado exitosamente.");
                System.out.println("Saldo actual: $" + balance);
            }
        } else {
            System.out.println("Ingrese una cantidad válida.");
        }

    }

    private static void jugarRuleta() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n***¡Bienvenido a la ruleta!***");
        System.out.println("Introduce tu apuesta:");

        double apuesta = 0;

        while (true) {
            String input = scanner.nextLine();
            if (input.matches("\\d+(\\.\\d+)?")) {
                apuesta = Double.parseDouble(input);
                if (apuesta > balance) {
                    System.out.println("Lo siento, no tienes suficiente dinero en tu cuenta.");
                    return;
                }
                break;
            } else {
                System.out.println("Debe ingresar un valor numérico.");
            }
        }

        String seleccion = "";
        int numero = -1;

        while (true) {
            System.out.print("Seleccione un número (0-36) o un color (rojo, negro o verde): ");
            seleccion = scanner.nextLine();

            boolean esNumero = true;
            for (int i = 0; i < seleccion.length(); i++) {
                if (!Character.isDigit(seleccion.charAt(i))) {
                    esNumero = false;
                    break;
                }
            }

            if (esNumero) {
                numero = Integer.parseInt(seleccion);
                if (numero < 0 || numero > 36) {
                    System.out.println("Número fuera de la selección.");
                } else {
                    break;
                }
            } else {
                if (!seleccion.equals("rojo") && !seleccion.equals("negro") && !seleccion.equals("verde")) {
                    System.out.println("Seleccione un color correcto.");
                } else {
                    break;
                }
            }
        }

        Random random = new Random();
        int resultado = random.nextInt(37);
        String colorResultado = "";

        if (resultado % 2 == 0) {
            colorResultado = (resultado == 0 || resultado == 00) ? "verde" : "negro";
        } else {
            colorResultado = "rojo";
        }

        System.out.println("La ruleta gira...");
        Thread.sleep(2000);

        System.out.printf("La ruleta ha girado y ha caído en el número %d de color %s.%n", resultado, colorResultado);

        if (resultado == numero || colorResultado.equals(seleccion)) {
            double ganancia = (valores.containsKey(seleccion)) ? apuesta * valores.get(seleccion) : apuesta * 35;
            balance += ganancia - apuesta;
            victoriaRule += 1;
            System.out.printf("¡Felicidades! Ha ganado $%.2f.%n", ganancia);
        } else {
            balance -= apuesta;
            derroRule += 1;
            System.out.printf("Lo siento, ha perdido $%.2f.%n", apuesta);
        }
    }

    private static void jugarCaballos() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n***¡Bienvenido a las carreras de caballos!***");
        System.out.println("Introduce tu apuesta.");

        double apuesta = 0;

        while (true) {
            String input = scanner.nextLine();
            if (input.matches("\\d+(\\.\\d+)?")) {
                apuesta = Double.parseDouble(input);
                if (apuesta > balance) {
                    System.out.println("Lo siento, no tienes suficiente dinero en tu cuenta.");
                    return;
                }
                break;
            } else {
                System.out.println("Debe ingresar un valor numérico.");
            }
        }

        System.out.println("Seleccione un caballo:");
        System.out.println("1. Relámpago");
        System.out.println("2. Trueno");
        System.out.println("3. Juan");
        System.out.println("4. Huracán");

        int seleccion = -1;

        while (true) {
            String input = scanner.nextLine();
            if (input.matches("\\d+")) {
                seleccion = Integer.parseInt(input);
                if (seleccion < 1 || seleccion > 4) {
                    System.out.println("Opción inválida.");
                } else {
                    break;
                }
            } else {
                System.out.println("Debe ingresar un número válido.");
            }
        }

        Random random = new Random();
        int resultado = random.nextInt(4) + 1;

        System.out.println("Los caballos corren...");

        Thread.sleep(4000);
        String caballo = "";
        switch (resultado){
            case 1:
                caballo = "Relámpago";
                break;
            case 2:
                caballo = "Trueno";
                break;
            case 3:
                caballo = "Juan";
                break;
            case 4:
                caballo = "Huracán";
                break;
        }
        System.out.printf("La carrera ha terminado y el caballo %s ha ganado.%n", caballo);

        if (resultado == seleccion) {
            double ganancia = apuesta * 2;
            balance += ganancia - apuesta;
            victocaba++;
            System.out.printf("¡Felicidades! Ha ganado $%.2f.%n", ganancia);
        } else {
            balance -= apuesta;
            derrocaba++;
            System.out.printf("Lo siento, ha perdido $%.2f.%n", apuesta);
        }
    }
    private static void jugarTragamonedas() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n***¡Bienvenido al tragamonedas!***");
        int apuesta = 0;

        while (true) {
            System.out.println("Introduce tu apuesta.");
            String input = scanner.nextLine();
            if (!input.isEmpty() && input.matches("-?\\d+")) {
                apuesta = Integer.parseInt(input);
                if (apuesta > 0) {
                    if (apuesta > balance) {
                        System.out.println("No tienes suficiente dinero para esa apuesta. Inténtalo de nuevo.");
                        return;
                    } else {
                        balance -= apuesta;
                        break;
                    }
                } else {
                    System.out.println("Ingrese una apuesta válida.");
                }
            } else {
                System.out.println("Ingrese un número entero válido.");
            }
        }

        String[] simbolos = {"7", "bar", "cereza", "limón", "gato", "uva"};
        Map<String, Integer> valores = new HashMap<>();
        valores.put("7", 10);
        valores.put("bar", 5);
        valores.put("cereza", 2);
        valores.put("limón", 1);
        valores.put("gato", 1);
        valores.put("uva", 1);

        Random random = new Random();
        String rodillo1 = simbolos[random.nextInt(simbolos.length)];
        String rodillo2 = simbolos[random.nextInt(simbolos.length)];
        String rodillo3 = simbolos[random.nextInt(simbolos.length)];

        System.out.println("Los rodillos giran...");
        Thread.sleep(2000);

        System.out.printf("| %s | %s | %s |%n", rodillo1, rodillo2, rodillo3);

        if (rodillo1.equals(rodillo2) && rodillo2.equals(rodillo3)) {
            int premioMayor = apuesta * valores.get(rodillo1) * 10;
            System.out.printf("¡Ganaste el premio mayor! %d monedas.%n", premioMayor);
            balance += premioMayor;
            victotraga ++;
        } else if (rodillo1.equals(rodillo2) || rodillo1.equals(rodillo3) || rodillo2.equals(rodillo3)) {
            int ganancia = apuesta * valores.get(rodillo1) * 2;
            System.out.printf("¡Has ganado! %d monedas.%n", ganancia);
            balance += ganancia;
            victotraga++;
        } else {
            System.out.println("Lo siento, has perdido.");
            derrotraga++;
        }
    }
    private static void estadisticasJug() {
        System.out.println("Durante esta sesión has: ");
        System.out.println("Dinero ingresado: $" + balanceingresado);
        System.out.println("Ganancias: $" + (balance - balanceingresado));
        System.out.println("Victorias en la ruleta: " + victoriaRule);
        System.out.println("Derrotas en la ruleta: " + derroRule);
        System.out.println("Victorias en las carreras: " + victocaba);
        System.out.println("Derrotas en las carreras: " + derrocaba);
        System.out.println("Victorias en la tragamonedas: " + victotraga);
        System.out.println("Derrotas en la tragamonedas: " + derrotraga);

        Duration duracion = Duration.between(inicio, Instant.now());
        double minutosDecimal = duracion.toMillis() / 60000.0;
        System.out.printf("Y en tan solo %.2f minutos!%n", minutosDecimal);
    }
}