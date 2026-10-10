Laboratório de Conjuntos

Atividade prática de Teoria dos Conjuntos e Conjuntos Numéricos — Desenvolvimento de Software Multiplataforma, Fatec Osasco. Linguagem: Java 9+ (usa BigInteger.sqrt()).

Como rodar
bash
javac -encoding UTF-8 *.java
java -Dfile.encoding=UTF-8 Testes   # casos de teste automáticos
java -Dfile.encoding=UTF-8 Main     # menu interativo (Partes 1, 2 e 3)

No terminal do Windows, rode chcp 65001 antes, para os símbolos (∪, ∩, √) aparecerem corretamente.

Estrutura
Arquivo	Conteúdo
Conjunto.java	Parte 1: conjunto genérico com todas as operações implementadas à mão
Fracao.java	Fração exata (BigInteger), sempre simplificada
Classificador.java	Parte 2: classificador N, Z, Q, I, R + fração geratriz + crivo + divisores
Recomendacao.java	Parte 3 (Opção 1): recomendação por interesses com índice de Jaccard
Main.java	Menu interativo
Testes.java	Casos de teste sem dependências externas
Decisões tomadas
Sem operadores prontos (Parte 1). O Conjunto guarda uma lista sem repetidos. adicionar só insere se contem(x) for falso, então repetidos da entrada são descartados. União, interseção, diferença, diferença simétrica, inclusão e complemento usam apenas for e if. Os HashSet nativos aparecem somente para validar o resultado.
Inclusão própria (⊂). A ⊂ B significa A ⊆ B e |A| < |B|. Para A = B, ⊆ é verdadeiro e ⊂ é falso, como no gabarito.
Complemento. Ā = U − A, reaproveitando a diferença.
Números exatos. A Parte 2 usa BigInteger e frações simplificadas, evitando erros de ponto flutuante (0,1 + 0,2, por exemplo).
N inclui o zero. Constante NATURAIS_INCLUEM_ZERO = true, coerente com a tabela do enunciado (0 pertence a N, Z, Q, R). Basta mudar para false para usar a outra convenção.
Fração geratriz. (todos os dígitos − dígitos antes do período) / (9…9 seguido de 0…0). Exemplo: 1,2(5) → (125 − 12) / 90 = 113/90.
Raízes. √x é racional se numerador e denominador do radicando forem quadrados perfeitos; caso contrário é irracional.
Entradas aceitas na Parte 2: -3, 3/4, 0,75 ou 0.75, 1,2(5), √9, sqrt(2), raiz(2).
Jaccard com união vazia. Se os dois conjuntos são vazios, o índice vale 0 (evita 0/0).
Parte 3. Escolhi a Opção 1 (recomendação) porque ela reutiliza diretamente a classe Conjunto da Parte 1 (interseccao e uniao).
Exemplos de execução
Parte 1 — três execuções (com comparação com a solução nativa)

Execução 1: caso geral (A = 1, 2, 2, 3, 4; B = 3, 4, 5, 6; U = 1..10)

A ∪ B = {1, 2, 3, 4, 5, 6}   [nativo: [1, 2, 3, 4, 5, 6] -> OK]
A ∩ B = {3, 4}   [nativo: [3, 4] -> OK]
A − B = {1, 2}   [nativo: [1, 2] -> OK]
B − A = {5, 6}   [nativo: [5, 6] -> OK]
A Δ B = {1, 2, 5, 6}   [nativo: [1, 2, 5, 6] -> OK]
Complemento de A em U = {5, 6, 7, 8, 9, 10}   [nativo: [5, 6, 7, 8, 9, 10] -> OK]
|A| = 4 (nativo: 4)   |B| = 4 (nativo: 4)

Execução 2: conjunto vazio (A = ∅; B = {1, 2})

A ∪ B = {1, 2}   [nativo: [1, 2] -> OK]
A ∩ B = {}   [nativo: [] -> OK]
A ⊆ B ? Verdadeiro   [nativo: Verdadeiro -> OK]

Execução 3: conjuntos iguais (A = B = {1, 2, 3})

A − B = {}   [nativo: [] -> OK]
A Δ B = {}   [nativo: [] -> OK]
A ⊆ B ? Verdadeiro   [nativo: Verdadeiro -> OK]
A ⊂ B ? Falso   [nativo: Falso -> OK]

Execução 4: conjuntos disjuntos (A = {1, 2}; B = {3, 4})

A ∪ B = {1, 2, 3, 4}   [nativo: [1, 2, 3, 4] -> OK]
A ∩ B = {}   [nativo: [] -> OK]
A Δ B = {1, 2, 3, 4}   [nativo: [1, 2, 3, 4] -> OK]
Parte 2 — classificador (8 entradas ou mais)
Entrada: -3        Tipo: inteiro          Pertence a: Z, Q, R
Entrada: 3/4       Tipo: fração           Pertence a: Q, R
Entrada: -5/2      Tipo: fração           Pertence a: Q, R
Entrada: 0,75      Tipo: decimal exato    Pertence a: Q, R
Entrada: 1,2(5)    Tipo: dízima periódica (parte não periódica: 2; período: 5)
                   Fração geratriz: 113/90    Pertence a: Q, R
Entrada: 0,(9)     Tipo: dízima periódica (parte não periódica: nenhuma; período: 9)
                   Fração geratriz: 9/9 = 1   Pertence a: N, Z, Q, R
Entrada: √9        Tipo: raiz exata       Pertence a: N, Z, Q, R
Entrada: √2        Tipo: raiz não exata   Pertence a: I, R

Primos até 30: [2, 3, 5, 7, 11, 13, 17, 19, 23, 29]
Divisores de 36: [1, 2, 3, 4, 6, 9, 12, 18, 36]
Parte 3 — recomendação por interesses (Jaccard)

Interesses: Ana = {rock, jazz, cinema, games}; Bia = {jazz, cinema, séries, viagens}; Caio = {games, tecnologia, cinema}.

Par	Interseção	União	Jaccard (à mão)	Programa
Ana e Bia	{cinema, jazz} (2)	6	2/6 ≈ 0,33	0.33
Ana e Caio	{cinema, games} (2)	5	2/5 = 0,40	0.40
Bia e Caio	{cinema} (1)	6	1/6 ≈ 0,17	0.17
Recomendações para Ana: Caio (0.40)  Bia (0.33)

Cenários adicionais (em Testes.java): conjuntos iguais → 1,0; conjuntos disjuntos → 0,0; interesses repetidos na entrada (jazz, jazz, jazz) contam uma vez só.

Casos de teste

java Testes executa 66 verificações e termina com Testes OK: 66 | Falhas: 0:

exemplos 1 e 2 do enunciado (caso geral, conjunto vazio, iguais, disjuntos, um contido no outro, repetidos);
500 pares aleatórios comparando cada operação com a solução nativa;
tabela de números da Parte 2, incluindo 0,(9), √9, √2, 1,2(5) e a rejeição de 1/0;
primos até 30 e divisores de 36;
Jaccard: valores do enunciado, iguais, disjuntos, união vazia e repetidos.
