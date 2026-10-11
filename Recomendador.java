import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recomendador {

    public static class Recomendacao implements Comparable<Recomendacao> {
        public String nomeUsuario;
        public double similaridade;
        public Conjunto<String> emComum;
        public int totalUniao;

        public Recomendacao(String nomeUsuario, double similaridade, Conjunto<String> emComum, int totalUniao) {
            this.nomeUsuario = nomeUsuario;
            this.similaridade = similaridade;
            this.emComum = emComum;
            this.totalUniao = totalUniao;
        }

        @Override
        public int compareTo(Recomendacao outra) {
            return Double.compare(outra.similaridade, this.similaridade);
        }
    }

    public static double calcularJaccard(Conjunto<String> a, Conjunto<String> b) {
        Conjunto<String> uniao = a.uniao(b);

        if (uniao.cardinalidade() == 0) {
            return 0.0;
        }
        Conjunto<String> interseccao = a.interseccao(b);
        return (double) interseccao.cardinalidade() / uniao.cardinalidade();
    }

    public static List<Recomendacao> recomendar(String usuarioAlvo, Map<String, Conjunto<String>> baseUsuarios) {
        List<Recomendacao> resultado = new ArrayList<>();
        Conjunto<String> interessesAlvo = baseUsuarios.get(usuarioAlvo);

        if (interessesAlvo == null) {
            return resultado;
        }

        for (Map.Entry<String, Conjunto<String>> entrada : baseUsuarios.entrySet()) {
            String outroUsuario = entrada.getKey();

            if (!outroUsuario.equalsIgnoreCase(usuarioAlvo)) {
                Conjunto<String> interessesOutro = entrada.getValue();
                
                double jaccard = calcularJaccard(interessesAlvo, interessesOutro);
                Conjunto<String> comuns = interessesAlvo.interseccao(interessesOutro);
                int uniaoTotal = interessesAlvo.uniao(interessesOutro).cardinalidade();

                resultado.add(new Recomendacao(outroUsuario, jaccard, comuns, uniaoTotal));
            }
        }

        Collections.sort(resultado);
        return resultado;
    }

    public static void main(String[] args) {
        System.out.println("=== PARTE 3: Sistema de Recomendação por Interesses (Jaccard) ===");

        Map<String, Conjunto<String>> usuarios = new HashMap<>();
        usuarios.put("Ana", Conjunto.de("rock", "jazz", "cinema", "games"));
        usuarios.put("Bia", Conjunto.de("jazz", "cinema", "series", "viagens"));
        usuarios.put("Caio", Conjunto.de("games", "tecnologia", "cinema"));

    
        String usuarioBase = "Ana";
        System.out.println("\nInteresses de " + usuarioBase + ": " + usuarios.get(usuarioBase));
        System.out.println("Gerando recomendações para " + usuarioBase + ":\n");

        List<Recomendacao> recomendacoes = recomendar(usuarioBase, usuarios);

        for (Recomendacao r : recomendacoes) {
            System.out.printf("- %s: Similaridade = %.2f (Interseção: %s [%d], União: %d)%n",
                    r.nomeUsuario, r.similaridade, r.emComum, r.emComum.cardinalidade(), r.totalUniao);
        }

        if (!recomendacoes.isEmpty()) {
            System.out.println("\n-> Recomendação mais forte para " + usuarioBase + ": " + recomendacoes.get(0).nomeUsuario);
        }
    }
}
