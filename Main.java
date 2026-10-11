import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * Menu interativo para operações com conjuntos e recomendação (Partes 1 e 3).
 */
public class Main {

    static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Parte 1: Operações com conjuntos ===");
        Conjunto<Integer> a = null, b = null, u = null;

        while (true) {
            try {
                if (a == null) {
                    System.out.print("Conjunto A (vazio = conjunto vazio; aceita 1..10): ");
                    a = lerConjunto(in.nextLine());
                    System.out.print("Conjunto B: ");
                    b = lerConjunto(in.nextLine());
                    System.out.print("Universo U: ");
                    u = lerConjunto(in.nextLine());
                    System.out.println("A = " + a + "   |A| = " + a.cardinalidade());
                    System.out.println("B = " + b + "   |B| = " + b.cardinalidade());
                    System.out.println("U = " + u);
                }

                System.out.println();
                System.out.println(" 1) A ∪ B        2) A ∩ B        3) A − B");
                System.out.println(" 4) B − A        5) A Δ B        6) x ∈ A");
                System.out.println(" 7) A ⊆ B        8) A ⊂ B        9) |A| e |B|");
                System.out.println("10) Complemento de A em U");
                System.out.println("11) Todas as operações");
                System.out.println("12) Informar novos conjuntos");
                System.out.println("13) Parte 3: Recomendação por interesses (Jaccard)");
                System.out.println(" 0) Sair");
                System.out.print("Operação: ");
                if (!in.hasNextLine()) return;
                String op = in.nextLine().trim();

                if (op.equals("0")) return;
                if (op.equals("12")) { a = null; continue; }
                if (op.equals("13")) {
                    executarParte3();
                    continue;
                }
                executar(op, a, b, u);

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida: use apenas números inteiros.");
                a = null; // volta a pedir os conjuntos
            }
        }
    }

    /** Executa o cenário estrito da Parte 3 conforme enunciado das folhas */
    static void executarParte3() {
        System.out.println("\n--- Parte 3: Recomendação por interesses (Índice de Jaccard) ---");

        Map<String, Conjunto<String>> base = new HashMap<>();
        base.put("Ana", Conjunto.de("rock", "jazz", "cinema", "games"));
        base.put("Bia", Conjunto.de("jazz", "cinema", "series", "viagens"));
        base.put("Caio", Conjunto.de("games", "tecnologia", "cinema"));

        String usuarioAlvo = "Ana";
        List<Recomendador.Recomendacao> recs = Recomendador.recomendar(usuarioAlvo, base);

        System.out.println("Recomendações para " + usuarioAlvo + ":");
        for (Recomendador.Recomendacao r : recs) {
            System.out.printf("- %s: similaridade = %.2f (interseção = %s, união = %d)%n",
                    r.nomeUsuario, r.similaridade, r.emComum, r.totalUniao);
        }
    }

    /** Aceita "1, 2, 2, 3", "1 2 3", "1..10" ou linha vazia (conjunto vazio). */
    static Conjunto<Integer> lerConjunto(String linha) {
        Conjunto<Integer> c = new Conjunto<>();
        linha = linha.trim();
        if (linha.isEmpty()) return c;
        if (linha.contains("..")) {
            String[] p = linha.split("\\.\\.");
            int ini = Integer.parseInt(p[0].trim());
            int fim = Integer.parseInt(p[1].trim());
            for (int i = ini; i <= fim; i++) c.adicionar(i);
            return c;
        }
        for (String t : linha.split("[,; ]+")) {
            if (!t.isEmpty()) c.adicionar(Integer.parseInt(t)); // repetidos são descartados
        }
        return c;
    }

    static void executar(String op, Conjunto<Integer> a, Conjunto<Integer> b, Conjunto<Integer> u) {
        Set<Integer> na = a.paraNativo(), nb = b.paraNativo(), nu = u.paraNativo();
        switch (op) {
            case "1": {
                Set<Integer> n = new HashSet<>(na); n.addAll(nb);
                mostrar("A ∪ B", a.uniao(b), n);
                break;
            }
            case "2": {
                Set<Integer> n = new HashSet<>(na); n.retainAll(nb);
                mostrar("A ∩ B", a.interseccao(b), n);
                break;
            }
            case "3": {
                Set<Integer> n = new HashSet<>(na); n.removeAll(nb);
                mostrar("A − B", a.diferenca(b), n);
                break;
            }
            case "4": {
                Set<Integer> n = new HashSet<>(nb); n.removeAll(na);
                mostrar("B − A", b.diferenca(a), n);
                break;
            }
            case "5": {
                Set<Integer> n = new HashSet<>(na); n.addAll(nb);
                Set<Integer> comum = new HashSet<>(na); comum.retainAll(nb);
                n.removeAll(comum);
                mostrar("A Δ B", a.diferencaSimetrica(b), n);
                break;
            }
            case "6": {
                System.out.print("x = ");
                int x = Integer.parseInt(in.nextLine().trim());
                mostrarBool(x + " ∈ A", a.contem(x), na.contains(x));
                break;
            }
            case "7":
                mostrarBool("A ⊆ B", a.estaContidoEm(b), nb.containsAll(na));
                break;
            case "8":
                mostrarBool("A ⊂ B", a.contidoPropriamente(b), nb.containsAll(na) && !na.equals(nb));
                break;
            case "9":
                System.out.println("|A| = " + a.cardinalidade() + " (nativo: " + na.size() + ")   |B| = "
                        + b.cardinalidade() + " (nativo: " + nb.size() + ")");
                break;
            case "10": {
                Set<Integer> n = new HashSet<>(nu); n.removeAll(na);
                mostrar("Complemento de A em U", a.complemento(u), n);
                break;
            }
            case "11":
                for (int i = 1; i <= 10; i++) {
                    if (i == 6) {
                        // pertinência: testa um elemento de A e um que não está em A
                        mostrarPertinencia(a, na);
                    } else {
                        executar(String.valueOf(i), a, b, u);
                    }
                }
                break;
            default:
                System.out.println("Operação inválida.");
        }
    }

    static void mostrarPertinencia(Conjunto<Integer> a, Set<Integer> na) {
        if (!a.vazio()) {
            int dentro = a.elementos().get(0);
            mostrarBool(dentro + " ∈ A", a.contem(dentro), na.contains(dentro));
        }
        int fora = 0;
        while (a.contem(fora)) fora++;
        mostrarBool(fora + " ∈ A", a.contem(fora), na.contains(fora));
    }

    static void mostrar(String nome, Conjunto<Integer> meu, Set<Integer> nativo) {
        boolean ok = meu.paraNativo().equals(nativo);
        System.out.println(nome + " = " + meu + "   [nativo: " + new TreeSet<>(nativo)
                + " -> " + (ok ? "OK" : "DIVERGE") + "]");
    }

    static void mostrarBool(String nome, boolean meu, boolean nativo) {
        System.out.println(nome + " ? " + (meu ? "Verdadeiro" : "Falso")
                + "   [nativo: " + (nativo ? "Verdadeiro" : "Falso") + " -> "
                + (meu == nativo ? "OK" : "DIVERGE") + "]");
    }
}