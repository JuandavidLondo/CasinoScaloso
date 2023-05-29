import java.time.Instant
import java.util.Scanner
import scala.collection.mutable.Map
import scala.util.Random
import scala.io.StdIn
import scala.util.control.Breaks.break
import scala.util.control.Breaks._
import java.time.Duration

object Main {
  private var balance = 0.0 // Saldo inicial del jugador
  private var balanceIngresado = 0.0
  private var victoriaRule = 0
  private var derroRule = 0
  private var victocaba = 0
  private var derrocaba = 0
  private var victotraga = 0
  private var derrotraga = 0
  private val inicio: Instant = Instant.now()
  private val scanner = StdIn

  def main(args: Array[String]): Unit = {
    var opcion = 0

    println("¡Bienvenido al Casino!")

    do {
      mostrarMenu()
      opcion = leerOpcion()

      opcion match {
        case 1 =>
          ingresarDinero()
        case 2 =>
          jugarRuleta()
        case 3 =>
          jugarCaballos()
        case 4 =>
          jugarTragamonedas()
        case 5 =>
          estadisticasJug()
        case 6 =>
          println("¡Gracias por jugar! Hasta luego.")
        case _ =>
          println("Opción inválida. Intente nuevamente.")
      }

      println()
    } while (opcion != 6)


  }

  private def mostrarMenu(): Unit = {
    println("--------------------")
    println("Saldo actual: $" + balance)
    println("--------------------")
    println("1. Ingresar dinero")
    println("2. Jugar a la ruleta")
    println("3. Jugar a los caballos")
    println("4. Jugar a la tragamonedas")
    println("5. Ver estadisticas")
    println("6. Salir")
    print("Ingrese su opción: ")
  }

  private def leerOpcion(): Int = {
    var opcion = 0
    var inputValido = false

    while (!inputValido) {
      val input = StdIn.readLine()
      if (input.length >= 10) {
        println("Ingrese un número válido.")
      }
      else if (input.matches("\\d+")) {
        opcion = input.toInt
        inputValido = true
      } else {
        println("Ingrese un número válido.")
      }
    }
    opcion
  }

  private def leertarjeta(): Long = {
    var opcion: Long = 0
    var inputValido = false

    while (!inputValido) {
      val input = StdIn.readLine()
      if(input.length>= 10){
        println("Ingrese un número válido.")
      }
      else if (input.matches("\\d+")) {
        opcion = input.toLong
        inputValido = true
      } else {
        println("Ingrese un número válido.")
      }
    }
    opcion
  }

  private def ingresarDinero(): Unit = {
    print("Ingrese su tarjeta: ")
    val tarjeta = leertarjeta()
    var monto = 0.0
    var inputValido = false

    print("Ingrese la cantidad de dinero a ingresar: ")


    while (!inputValido) {
      val ingresi = StdIn.readLine()
      if (ingresi.length >= 12) {
        println("Ingrese un número válido.")
      }
      else if (ingresi.matches("\\d+")) {
        monto = ingresi.toDouble
        inputValido = true
      } else {
        println("Ingrese un número válido.")
      }
    }

    if (monto > 0) {
      if (monto > Double.MaxValue) {
        println("No se puede ingresar tanto dinero.")
      } else {
        balance += monto
        balanceIngresado += monto
        println("Dinero ingresado exitosamente.")
        println(s"Saldo actual: $$$balance")
      }
    } else {
      println("Ingrese una cantidad válida.")
    }
  }

  private def jugarRuleta(): Unit = {
    println("\n***¡Bienvenido a la ruleta!***")
    println("Introduce tu apuesta:")

    var apuesta = 0.0

    breakable {
      while (true) {
        val input = StdIn.readLine()
        if (input.length >= 10) {
          println("Ingrese un número válido.")
        }
        else if (input.matches("\\d+(\\.\\d+)?")) {
          apuesta = input.toDouble
          if (apuesta > balance) {
            println("Lo siento, no tienes suficiente dinero en tu cuenta.")
            return
          }
          break
        } else {
          println("Debe ingresar un valor numérico.")
        }
      }
    }

    var seleccion = ""
    var numero = -1

    breakable {
      while (true) {
        print("Seleccione un número (0-36) o un color (rojo, negro o verde): ")
        seleccion = StdIn.readLine()

        val esNumero = seleccion.forall(_.isDigit)

        if (esNumero) {
          numero = seleccion.toInt
          if (numero < 0 || numero > 36) {
            println("Número fuera de la selección.")
          } else {
            break
          }
        } else {
          if (!seleccion.equals("rojo") && !seleccion.equals("negro") && !seleccion.equals("verde")) {
            println("Seleccione un color correcto.")
          } else {
            break
          }
        }
      }
    }

    val random = new Random()
    val resultado = random.nextInt(37)
    var colorResultado = ""

    if (resultado % 2 == 0) {
      colorResultado = if (resultado == 0) "verde" else "negro"
    } else {
      colorResultado = "rojo"
    }

    println("La ruleta gira...")
    Thread.sleep(2000)

    printf("La ruleta ha girado y ha caído en el número %d de color %s.%n", resultado, colorResultado)

    val ganancia: Double = seleccion match {
      case "rojo" => apuesta * 2
      case "negro" => apuesta * 2
      case "verde" => apuesta * 35
      case _ => apuesta * 35
    }

    if (resultado == numero || colorResultado == seleccion) {
      balance += ganancia - apuesta
      victoriaRule += 1
      printf("¡Felicidades! Ha ganado $%.2f.%n", ganancia)
    } else {
      balance -= apuesta
      derroRule += 1
      printf("Lo siento, ha perdido $%.2f.%n", apuesta)
    }
  }

  private def jugarCaballos(): Unit = {
    println("\n***¡Bienvenido a las carreras de caballos!***")
    println("Introduce tu apuesta.")

    var apuesta = 0.0

    breakable {
      while (true) {
        val input = StdIn.readLine()
        if (input.matches("\\d+(\\.\\d+)?")) {
          apuesta = input.toDouble
          if (apuesta > balance) {
            println("Lo siento, no tienes suficiente dinero en tu cuenta.")
            return
          }
          break
        } else {
          println("Debe ingresar un valor numérico.")
        }
      }
    }

    println("Seleccione un caballo:")
    println("1. Relámpago")
    println("2. Trueno")
    println("3. Juan")
    println("4. Huracán")

    var seleccion = -1

    breakable {
      while (true) {
        val input = StdIn.readLine()
        if (input.length >= 10) {
          println("Ingrese un número válido.")
        }
        else if (input.matches("\\d+")) {
          seleccion = input.toInt
          if (seleccion >= 1 && seleccion <= 4) {
            break
          } else {
            println("Opción inválida.")
          }
        } else {
          println("Debe ingresar un número válido.")
        }
      }
    }

    val random = new Random()
    val resultado = random.nextInt(4) + 1

    println("Los caballos corren...")

    Thread.sleep(4000)
    val caballo = resultado match {
      case 1 => "Relámpago"
      case 2 => "Trueno"
      case 3 => "Juan"
      case 4 => "Huracán"
    }
    printf("La carrera ha terminado y el caballo %s ha ganado.%n", caballo)

    if (resultado == seleccion) {
      val ganancia = apuesta * 2
      balance += ganancia - apuesta
      victocaba += 1
      printf("¡Felicidades! Ha ganado $%.2f.%n", ganancia)
    } else {
      balance -= apuesta
      derrocaba += 1
      printf("Lo siento, ha perdido $%.2f.%n", apuesta)
    }
  }

  private def jugarTragamonedas(): Unit = {
    val scanner = new Scanner(System.in)
    println("\n***¡Bienvenido al tragamonedas!***")
    var apuesta = 0

    breakable {
      while (true) {
        println("Introduce tu apuesta.")
        val input = scanner.nextLine()
        if (input.length >= 10) {
          println("Ingrese un número válido.")
        }
        else if (!input.isEmpty && input.matches("-?\\d+")) {
          apuesta = input.toInt
          if (apuesta > 0) {
            if (apuesta > balance) {
              println("No tienes suficiente dinero para esa apuesta. Inténtalo de nuevo.")
              return
            } else {
              balance -= apuesta
              break
            }
          } else {
            println("Ingrese una apuesta válida.")
          }
        } else {
          println("Ingrese un número entero válido.")
        }
      }
    }

    val simbolos = Array("7", "bar", "cereza", "limón", "gato", "uva")
    val valores = Map("7" -> 10, "bar" -> 5, "cereza" -> 2, "limón" -> 1, "gato" -> 1, "uva" -> 1)

    val random = new Random()
    val rodillo1 = simbolos(random.nextInt(simbolos.length))
    val rodillo2 = simbolos(random.nextInt(simbolos.length))
    val rodillo3 = simbolos(random.nextInt(simbolos.length))

    println("Los rodillos giran...")
    Thread.sleep(2000)

    printf("| %s | %s | %s |%n", rodillo1, rodillo2, rodillo3)

    if (rodillo1 == rodillo2 && rodillo2 == rodillo3) {
      val premioMayor = apuesta * valores(rodillo1) * 10
      printf("¡Ganaste el premio mayor! %d monedas.%n", premioMayor)
      balance += premioMayor
      victotraga += 1
    } else if (rodillo1 == rodillo2 || rodillo1 == rodillo3 || rodillo2 == rodillo3) {
      val ganancia = apuesta * valores(rodillo1) * 2
      printf("¡Has ganado! %d monedas.%n", ganancia)
      balance += ganancia
      victotraga += 1
    } else {
      println("Lo siento, has perdido.")
      derrotraga += 1
    }
  }

  private def estadisticasJug(): Unit = {
    println("Durante esta sesión has: ")
    println("Dinero ingresado: $" + balanceIngresado)
    println("Ganancias: $" + (balance - balanceIngresado))
    println("Victorias en la ruleta: " + victoriaRule)
    println("Derrotas en la ruleta: " + derroRule)
    println("Victorias en las carreras: " + victocaba)
    println("Derrotas en las carreras: " + derrocaba)
    println("Victorias en la tragamonedas: " + victotraga)
    println("Derrotas en la tragamonedas: " + derrotraga)

    val duracion = Duration.between(inicio, Instant.now())
    val minutosDecimal = duracion.toMillis.toDouble / 60000.0
    printf("Y en tan solo %.2f minutos!%n", minutosDecimal)
  }
}
