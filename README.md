# Laboratório de Conjuntos

Atividade prática de **Teoria dos Conjuntos e Conjuntos Numéricos** — Desenvolvimento de Software Multiplataforma, Fatec Osasco.
Linguagem: **Java 9+** (usa `BigInteger.sqrt()`).

## Como rodar

```bash
javac -encoding UTF-8 *.java
java -Dfile.encoding=UTF-8 Testes   # casos de teste automáticos
java -Dfile.encoding=UTF-8 Main     # menu interativo (Partes 1, 2 e 3)
```

No terminal do Windows, rode `chcp 65001` antes, para os símbolos (∪, ∩, √) aparecerem corretamente.
## Decisões tomadas

- **Sem operadores prontos (Parte 1).** O `Conjunto` guarda uma lista sem repetidos. `adicionar` só insere se `contem(x)` for falso, então repetidos da entrada são descartados. União, interseção, diferença, diferença simétrica, inclusão e complemento usam apenas `for` e `if`. Os `HashSet` nativos aparecem somente para **validar** o resultado.
- **Inclusão própria (⊂).** `A ⊂ B` significa `A ⊆ B` e `|A| < |B|`. Para `A = B`, `⊆` é verdadeiro e `⊂` é falso, como no gabarito.
- **Complemento.** `Ā = U − A`, reaproveitando a diferença.
- **Números exatos.** A Parte 2 usa `BigInteger` e frações simplificadas, evitando erros de ponto flutuante (`0,1 + 0,2`, por exemplo).
- **N inclui o zero.** Constante `NATURAIS_INCLUEM_ZERO = true`, coerente com a tabela do enunciado (`0` pertence a N, Z, Q, R). Basta mudar para `false` para usar a outra convenção.
- **Fração geratriz.** `(todos os dígitos − dígitos antes do período) / (9…9 seguido de 0…0)`. Exemplo: `1,2(5)` → (125 − 12) / 90 = 113/90.
- **Raízes.** `√x` é racional se numerador e denominador do radicando forem quadrados perfeitos; caso contrário é irracional.
- **Entradas aceitas na Parte 2:** `-3`, `3/4`, `0,75` ou `0.75`, `1,2(5)`, `√9`, `sqrt(2)`, `raiz(2)`.
- **Jaccard com união vazia.** Se os dois conjuntos são vazios, o índice vale 0 (evita 0/0).
- **Parte 3.** Escolhi a Opção 1 (recomendação) porque ela reutiliza diretamente a classe `Conjunto` da Parte 1 (`interseccao` e `uniao`).

## Exemplos de execução

### Parte 1 — três execuções (com comparação com a solução nativa)

**Execução 1: caso geral** (`A = 1, 2, 2, 3, 4`; `B = 3, 4, 5, 6`; `U = 1..10`)

```
A ∪ B = {1, 2, 3, 4, 5, 6}   [nativo: [1, 2, 3, 4, 5, 6] -> OK]
A ∩ B = {3, 4}   [nativo: [3, 4] -> OK]
A − B = {1, 2}   [nativo: [1, 2] -> OK]
B − A = {5, 6}   [nativo: [5, 6] -> OK]
A Δ B = {1, 2, 5, 6}   [nativo: [1, 2, 5, 6] -> OK]
Complemento de A em U = {5, 6, 7, 8, 9, 10}   [nativo: [5, 6, 7, 8, 9, 10] -> OK]
|A| = 4 (nativo: 4)   |B| = 4 (nativo: 4)
```

**Execução 2: conjunto vazio** (`A = ∅`; `B = {1, 2}`)

```
A ∪ B = {1, 2}   [nativo: [1, 2] -> OK]
A ∩ B = {}   [nativo: [] -> OK]
A ⊆ B ? Verdadeiro   [nativo: Verdadeiro -> OK]
```

**Execução 3: conjuntos iguais** (`A = B = {1, 2, 3}`)

```
A − B = {}   [nativo: [] -> OK]
A Δ B = {}   [nativo: [] -> OK]
A ⊆ B ? Verdadeiro   [nativo: Verdadeiro -> OK]
A ⊂ B ? Falso   [nativo: Falso -> OK]
```

**Execução 4: conjuntos disjuntos** (`A = {1, 2}`; `B = {3, 4}`)

```
A ∪ B = {1, 2, 3, 4}   [nativo: [1, 2, 3, 4] -> OK]
A ∩ B = {}   [nativo: [] -> OK]
A Δ B = {1, 2, 3, 4}   [nativo: [1, 2, 3, 4] -> OK]
```


# ||||| Parte 2 — Classificador de Conjuntos Numéricos |||||

## Objetivo

Desenvolver um programa em Python capaz de identificar a quais conjuntos numéricos um número pertence, apresentando sua classificação e justificativa matemática.

## Conjuntos utilizados

- **N — Naturais:** números inteiros não negativos.
- **Z — Inteiros:** números positivos, negativos e zero, sem parte fracionária.
- **Q — Racionais:** números que podem ser representados por uma fração de dois inteiros, com denominador diferente de zero.
- **I — Irracionais:** números reais que não podem ser representados por uma fração de inteiros.
- **R — Reais:** conjunto que inclui os números racionais e irracionais.

## Funcionalidades

1. Classificação de números naturais, inteiros, racionais e irracionais.
2. Simplificação de frações.
3. Conversão de dízimas periódicas em frações geratrizes.
4. Identificação de raízes quadradas racionais e irracionais.
5. Exibição de justificativas matemáticas.
6. Identificação de números primos pelo Crivo de Eratóstenes.
7. Listagem de divisores positivos de números inteiros.

## Tecnologias utilizadas

- Python 3
- Biblioteca `fractions`
- Biblioteca `math`
- Biblioteca `re`

## Como executar

Abra o terminal na pasta do projeto e execute:

```bash
python classificador.py
```

O programa exibirá um menu com as funcionalidades disponíveis.

## Exemplos de classificação

| Entrada | Conjuntos |
|---|---|
| 5 | N, Z, Q, R |
| -8 | Z, Q, R |
| 3/4 | Q, R |
| 6/3 | N, Z, Q, R |
| 0,(3) | Q, R |
| -1,2(5) | Q, R |
| √2 | I, R |
| √16 | N, Z, Q, R |

Os resultados acima são os resultados esperados para os testes.

## testes

![Testes realizados na parte 2 - classificador de números](prints/testes_etapa_2.png)

## Observações

O programa considera o zero pertencente ao conjunto dos naturais.

As frações são simplificadas automaticamente pela biblioteca `fractions`. As dízimas periódicas são convertidas por meio do cálculo de sua fração geratriz.

As raízes quadradas são classificadas por meio da verificação de quadrados perfeitos no numerador e denominador da fração simplificada.


### Parte 3 — recomendação por interesses (Jaccard)

Interesses: Ana = {rock, jazz, cinema, games}; Bia = {jazz, cinema, séries, viagens}; Caio = {games, tecnologia, cinema}.

#### Cenários de uso obrigatórios (Cálculo à mão vs. Saída do programa)

1. **Cenário 1 — Ana e Caio:**
   - Interseção à mão: $\{games, cinema\} \rightarrow 2$ elementos.
   - União à mão: $\{rock, jazz, cinema, games, tecnologia\} \rightarrow 5$ elementos.
   - Jaccard calculado à mão: $2/5 = 0,40$.
   - Saída do programa: `similaridade = 0.40`.

2. **Cenário 2 — Ana e Bia:**
   - Interseção à mão: $\{jazz, cinema\} \rightarrow 2$ elementos.
   - União à mão: $\{rock, jazz, cinema, games, series, viagens\} \rightarrow 6$ elementos.
   - Jaccard calculado à mão: $2/6 \approx 0,33$.
   - Saída do programa: `similaridade = 0.33`.

3. **Cenário 3 — Bia e Caio:**
   - Interseção à mão: $\{cinema\} \rightarrow 1$ elemento.
   - União à mão: $\{jazz, cinema, series, viagens, games, tecnologia\} \rightarrow 6$ elementos.
   - Jaccard calculado à mão: $1/6 \approx 0,17$.
   - Saída do programa: `similaridade = 0.17`.

| Par | Interseção | União | Jaccard (à mão) | Programa |
|---|---|---|---|---|
| Ana e Bia | {cinema, jazz} (2) | 6 | 2/6 ≈ 0,33 | 0.33 |
| Ana e Caio | {cinema, games} (2) | 5 | 2/5 = 0,40 | 0.40 |
| Bia e Caio | {cinema} (1) | 6 | 1/6 ≈ 0,17 | 0.17 |

```text
Recomendações para Ana:
- Caio: similaridade = 0.40 (interseção = {cinema, games}, união = 5)
- Bia: similaridade = 0.33 (interseção = {cinema, jazz}, união = 6)
```

Resultado esperado: para a Ana, a recomendação mais forte é o Caio, por possuir o maior índice de afinidade (0,40 > 0,33).

#### Casos de teste de borda da Parte 3

- **Conjuntos vazios:** Se ambos os usuários não têm interesses cadastrados ($\emptyset$ e $\emptyset$), a união é 0 e o programa retorna afinidade `0.0`, prevenindo divisão por zero.
- **Conjuntos iguais:** Dois perfis com exatamente os mesmos gostos resultam em $J = 1,00$ (afinidade máxima).
- **Conjuntos disjuntos:** Perfis sem nenhum interesse em comum (interseção $\emptyset$) resultam em $J = 0,00$.
- **Elementos repetidos na entrada:** Entradas com duplicatas (ex.: `jazz, jazz, jazz`) têm suas repetições descartadas pela classe `Conjunto`, computando a cardinalidade correta.

## Casos de teste

`java Testes` executa 66 verificações e termina com `Testes OK: 66 | Falhas: 0`:

- exemplos 1 e 2 do enunciado (caso geral, conjunto vazio, iguais, disjuntos, um contido no outro, repetidos);
- **500 pares aleatórios** comparando cada operação com a solução nativa;
- tabela de números da Parte 2, incluindo `0,(9)`, `√9`, `√2`, `1,2(5)` e a rejeição de `1/0`;
- primos até 30 e divisores de 36;
- Jaccard: valores do enunciado, iguais, disjuntos, união vazia e repetidos.