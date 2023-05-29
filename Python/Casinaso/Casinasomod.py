import sys
import random
import time
def simular_ingreso(dinero,dinero_in):
    while True:
      try:
        tarjeta = int(input('Ingrese su número de tarjeta: '))
        cantidad = float(input('Ingrese la cantidad a depositar: '))
        if cantidad> sys.float_info.max:
           print("No se puede ingresar tanto dinero!")
           break
        dinero += cantidad
        dinero_in += cantidad
        print('Se ha depositado ${:.2f} en su cuenta.'.format(cantidad))
        break
      except ValueError:
        print('Numero de la tarjeta o cantidad a depositar invalida.')
    return dinero,dinero_in
    

def jugar_ruleta(dinero,vict_rule,derro_rule,rojo,negro,valores):
    print("\n***¡Bienvenido a la ruleta!***")
    print("Introduce tu apuesta.")
    while True:
        try:
            apuesta = float(input('> '))
            if apuesta > dinero:
                print('Lo siento, no tienes suficiente dinero en tu cuenta.')
                return
            break
        except ValueError:
            print('Debe ingresar un valor numérico.')
    while True:
      seleccion = input('Seleccione un número (0-36) o un color (rojo, negro o verde): ')
      try:
        numero = int(seleccion)
        if numero > 36:
          print("Numero fuera de la seleccion")
        else:
          break
      except ValueError:
        if seleccion != "rojo" and seleccion != "negro" and seleccion != "":
          print("Seleccione un color correcto.")
        else:
          break
    resultado = str(random.randint(0, 36))
    if resultado in rojo:
        color_resultado = "rojo"
    elif resultado in negro:
        color_resultado = "negro"
    else:
        color_resultado = "verde"
    print("La ruleta gira... ")
    time.sleep(2)
    print('La ruleta ha girado y ha caído en el número {} de color {}.'.format(resultado, color_resultado))
    if resultado == seleccion or color_resultado == seleccion:
        if seleccion in valores:
            ganancia = apuesta * valores[seleccion]
        else:
            ganancia = apuesta * 35
        dinero += ganancia - apuesta
        vict_rule += 1
        print('¡Felicidades! Ha ganado ${:.2f}.'.format(ganancia))
    else:
        dinero -= apuesta
        derro_rule += 1
        print('Lo siento, ha perdido ${:.2f}.'.format(apuesta))
    return dinero,vict_rule,derro_rule

def jugar_caballos(dinero,vict_caba,derro_caba,caballos,caballos_stats):
    print("\n***¡Bienvenido a las carreras de caballos!***")
    print("Introduce tu apuesta.")
    while True:
        try:
            apuesta = float(input('>'))
            if apuesta > dinero:
                print('Lo siento, no tienes suficiente dinero en tu cuenta.')
                return
            break
        except ValueError:
            print('Debe ingresar un valor numérico.')
    print('Seleccione un caballo:')
    for i, caballo in enumerate(caballos):
        print(f'{i+1}. {caballo}')
    seleccion = input('> ')
    if not seleccion.isdigit() or int(seleccion) not in range(1, len(caballos)+1):
        print('Opción inválida.')
        return
    seleccion = int(seleccion)
    resultado = random.randint(1, len(caballos))
    if resultado ==1:
      caballos_stats[0] += 1
    elif resultado ==2:
      caballos_stats[1] += 1
    elif resultado ==3:
      caballos_stats[2] += 1
    else:
      caballos_stats[3] += 1
    print("Los caballos corren... ")
    time.sleep(4)
    print('La carrera ha terminado y el {} ha ganado.'.format(caballos[resultado-1]))
    if resultado == seleccion:
        ganancia = apuesta * 2
        dinero += ganancia - apuesta
        vict_caba +=1
        print('¡Felicidades! Ha ganado ${:.2f}.'.format(ganancia))
    else:
        dinero -= apuesta
        derro_caba +=1
        print('Lo siento, ha perdido ${:.2f}.'.format(apuesta))
    return dinero,vict_caba,derro_caba,caballos_stats

def jugar_tragamonedas(dinero,vict_trag,derro_traga):
    print("\n***¡Bienvenido al tragamonedas!***")
    while True:
      try:
        print("Introduce tu apuesta.")
        apuesta = int(input("> "))
        if apuesta > 0:
          if apuesta > dinero:
            print("No tienes suficiente dinero para esa apuesta. Inténtalo de nuevo.")
            return
            break
          else:
            dinero -= apuesta
            break
        else:
            print("ingrese una apuesta valida")
      except ValueError:
          print("Ingrese un número entero válido.")

    simbolos = ['7', 'bar', 'cereza', 'limón', 'gato', 'uva']
    valores = {'7': 10, 'bar': 5, 'cereza': 2, 'limón': 1, 'gato': 1, 'uva': 1}
    rodillo1 = random.choice(simbolos)
    rodillo2 = random.choice(simbolos)
    rodillo3 = random.choice(simbolos)
    print("Los rodillos giran... ")
    time.sleep(2)
    print(f"| {rodillo1} | {rodillo2} | {rodillo3} |")
    if rodillo1 == rodillo2 == rodillo3:
        print("¡Ganaste el premio mayor! ", apuesta * valores[rodillo1] * 10, " monedas.")
        dinero += apuesta * valores[rodillo1] * 10
        
    elif rodillo1 == rodillo2 or rodillo1 == rodillo3 or rodillo2 == rodillo3:
        print("¡Has ganado! ", apuesta * valores[rodillo1] * 2, " monedas.")
        dinero += apuesta * valores[rodillo1] * 2
        vict_trag +=1
    else:
        print("Lo siento, has perdido.")
        derro_traga +=1
    return dinero,vict_trag,derro_traga

def estadisticas(caballos,caballos_stats):
  for i in range(len(caballos)):
    print(caballos[i]," Ha ganado ",caballos_stats[i]," Carreras")
  
def estadisticas_jug(dinero,dinero_in,vict_rule,derro_rule,vict_caba,derro_caba,vict_trag,derro_traga,tiempo):
  print("Durante esta sesion has: ")
  print("Dinero ingresado: $",dinero_in)
  print("Ganancias: $",(dinero-dinero_in))
  print("Victorias en la ruleta: ",vict_rule)
  print("Derrotas en la ruleta: ",derro_rule)
  print("Victorias en las carreras: ",vict_caba)
  print("Derrotas en las carreras: ",derro_caba)
  print("Victorias en la tragamonedas: ",vict_trag)
  print("Derrotas en la tragamonedas: ",derro_traga)
  print(f"Y en tan solo {(((time.time()-tiempo)/60)):.2f} minutos!")