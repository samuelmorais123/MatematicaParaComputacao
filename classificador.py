from fractions import Fraction
from math import isqrt

def classificar_numero(numero):
    if numero.denominator == 1:
        if numero >= 0:
            print("Pertence a: N, Z, Q, R")
            print("Justificativa: é natural, inteiro,")
            print("racional e real.")
        else:
            print("Pertence a: Z, Q, R")
            print("Justificativa: é inteiro negativo,")
            print("portanto também racional e real.")
    else:
        print("Pertence a: Q, R")
        print("Justificativa: pode ser representado")
        print("como uma razão entre dois inteiros.")

def calcular_dizima(entrada):
    import re

    entrada = entrada.strip().replace(",", ".")

    padrao = r"^(-?)(\d+)\.(\d*)\((\d+)\)$"
    resultado = re.fullmatch(padrao, entrada)

    if not resultado:
        raise ValueError("Formato de dízima inválido.")

    sinal, inteiro, nao_periodica, periodo = resultado.groups()

    completo = int(inteiro + nao_periodica + periodo)
    anterior = int(inteiro + nao_periodica)

    denominador = int(
        "9" * len(periodo) + "0" * len(nao_periodica)
    )

    numerador = completo - anterior

    if sinal == "-":
        numerador = -numerador

    return Fraction(numerador, denominador)

def classificar_raiz(entrada):
    valor = entrada.replace("√", "").strip()
    numero = Fraction(valor.replace(",", "."))

    if numero < 0:
        print("A raiz quadrada negativa não pertence aos reais.")
        return

    raiz_numerador = isqrt(numero.numerator)
    raiz_denominador = isqrt(numero.denominator)

    if (raiz_numerador ** 2 == numero.numerator
            and raiz_denominador ** 2 == numero.denominator):

        resultado = Fraction(raiz_numerador, raiz_denominador)

        print(f"Raiz exata: {resultado}")
        classificar_numero(resultado)

    else:
        print("Pertence a: I, R")
        print("Justificativa: a raiz é irracional.")
        print("Não pode ser representada por uma fração exata.")

def encontrar_primos(limite):
    if limite < 2:
        print("Não existem primos nesse intervalo.")
        return

    primos = [True] * (limite + 1)
    primos[0] = primos[1] = False

    for i in range(2, isqrt(limite) + 1):
        if primos[i]:
            for j in range(i * i, limite + 1, i):
                primos[j] = False

    resultado = [i for i in range(2, limite + 1) if primos[i]]
    print("Números primos:", resultado)

def listar_divisores(numero):
    if numero == 0:
        print("O zero possui infinitos divisores não nulos.")
        return

    numero = abs(numero)

    divisores = []

    for i in range(1, numero + 1):
        if numero % i == 0:
            divisores.append(i)

    print("Divisores positivos:", divisores)


while True:
    print("\n===== CLASSIFICADOR NUMÉRICO =====")
    print("1 - Classificar número")
    print("2 - Calcular fração geratriz")
    print("3 - Encontrar números primos")
    print("4 - Listar divisores")
    print("0 - Sair")

    opcao = input("Escolha uma opção: ")

    try:
        if opcao == "1":
            entrada = input("Digite um número: ").strip()

            if entrada.startswith("√"):
                classificar_raiz(entrada)

            elif "(" in entrada:
                numero = calcular_dizima(entrada)
                print(f"Fração geratriz: {numero}")
                classificar_numero(numero)

            else:
                numero = Fraction(entrada.replace(",", "."))
                print(f"Valor simplificado: {numero}")
                classificar_numero(numero)

        elif opcao == "2":
            entrada = input("Digite uma dízima (ex: 0,(3)): ")
            resultado = calcular_dizima(entrada)
            print(f"Fração geratriz: {resultado}")

        elif opcao == "3":
            limite = int(input("Encontrar primos até: "))
            encontrar_primos(limite)

        elif opcao == "4":
            numero = int(input("Digite um número inteiro: "))
            listar_divisores(numero)

        elif opcao == "0":
            print("Encerrando programa...")
            break

        else:
            print("Opção inválida!")

    except (ValueError, ZeroDivisionError, IndexError):
        print("Entrada inválida!")