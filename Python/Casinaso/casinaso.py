import random
import sys
import time
from Casinasomod import *
dinero = 0
dinero_in = 0
vict_rule = 0
derro_rule = 0
vict_caba = 0
derro_caba = 0
vict_trag = 0
derro_traga = 0
tiempo = time.time()
caballos = ['Relámpago', 'Trueno', 'Juan', 'Huracán']
caballos_stats = [5,7,4,2]
rojo = ["1", "3", "5", "7", "9", "12", "14", "16", "18", "19", "21", "23", "25", "27", "30", "32", "34", "36"]
negro = ["2", "4", "6", "8", "10", "11", "13", "15", "17", "20", "22", "24", "26", "28", "29", "31", "33", "35"]
verde = ["0", "00"]
valores = {"rojo": 2, "negro": 2, "verde": 35}


    

while True:
    print('------------------------------------------------')
    print('****!Bienvenido al Casino Virtual¡****')
    print('Saldo actual: ${:.2f}'.format(dinero))
    print('------------------------------------------------')
    print('Seleccione una opción:')
    print('1. ingreso de dinero')
    print('2. Jugar a la ruleta')
    print('3. Jugar a los caballos')
    print('4. jugar a tragamonedas')
    print('5. estadisticas de los caballos')
    print('6. estadisticas de la sesion')
    print('7. salir')
    opcion = input()
    if opcion == "1":
        dinero,dinero_in = simular_ingreso(dinero,dinero_in)
    elif opcion == "2":
        dinero,vict_rule,derro_rule = jugar_ruleta(dinero,vict_rule,derro_rule,rojo,negro,valores)
    elif opcion == "3":
        dinero,vict_caba,derro_caba,caballos_stats = jugar_caballos(dinero,vict_caba,derro_caba,caballos,caballos_stats)
    elif opcion == "4":
        dinero,vict_trag,derro_traga = jugar_tragamonedas(dinero,vict_trag,derro_traga)
    elif opcion == "5":
        estadisticas(caballos,caballos_stats)
    elif opcion == "6":
        estadisticas_jug(dinero,dinero_in,vict_rule,derro_rule,vict_caba,derro_caba,vict_trag,derro_traga,tiempo)
    elif opcion == "7":
        print("Gracias por jugar. Su saldo final es ${:.2f}.".format(dinero))
        break
    else:
        print("Opción inválida. Por favor seleccione otra opción.")
    time.sleep(2)
   
