import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Conjunto<T> {
 
    private final List<T> elementos = new ArrayList<>();
 
    @SafeVarargs
    public static <T> Conjunto<T> de(T... itens) {
        Conjunto<T> c = new Conjunto<>();
        for (T x : itens) c.adicionar(x);
        return c;
    }
 
    public static <T> Conjunto<T> de(List<T> itens) {
        Conjunto<T> c = new Conjunto<>();
        for (T x : itens) c.adicionar(x);
        return c;
    }
 
    /** Só insere se ainda não existir: descarta repetidos da entrada. */
    public boolean adicionar(T x) {
        if (contem(x)) return false;
        elementos.add(x);
        return true;
    }
 
    /** Pertinência: x ∈ A */
    public boolean contem(T x) {
        for (T e : elementos) {
            if (e.equals(x)) return true;
        }
        return false;
    }
 
    /** Cardinalidade: |A| */
    public int cardinalidade() {
        return elementos.size();
    }
 
    public boolean vazio() {
        return elementos.isEmpty();
    }
 
    /** A ∪ B: tudo que está em A ou em B */
    public Conjunto<T> uniao(Conjunto<T> outro) {
        Conjunto<T> r = new Conjunto<>();
        for (T x : this.elementos) r.adicionar(x);
        for (T x : outro.elementos) r.adicionar(x); // repetidos são descartados
        return r;
    }
 
    /** A ∩ B: elementos de A que também estão em B */
    public Conjunto<T> interseccao(Conjunto<T> outro) {
        Conjunto<T> r = new Conjunto<>();
        for (T x : this.elementos) {
            if (outro.contem(x)) r.adicionar(x);
        }
        return r;
    }
 
    /** A − B: elementos de A que NÃO estão em B */
    public Conjunto<T> diferenca(Conjunto<T> outro) {
        Conjunto<T> r = new Conjunto<>();
        for (T x : this.elementos) {
            if (!outro.contem(x)) r.adicionar(x);
        }
        return r;
    }
 
    /** A Δ B = (A − B) ∪ (B − A) */
    public Conjunto<T> diferencaSimetrica(Conjunto<T> outro) {
        return this.diferenca(outro).uniao(outro.diferenca(this));
    }
 
    /** A ⊆ B: todo elemento de A está em B (vale para vazio e para iguais) */
    public boolean estaContidoEm(Conjunto<T> outro) {
        for (T x : this.elementos) {
            if (!outro.contem(x)) return false;
        }
        return true;
    }
 
    /** A ⊂ B (próprio): A ⊆ B e A ≠ B, ou seja, B tem pelo menos 1 elemento a mais */
    public boolean contidoPropriamente(Conjunto<T> outro) {
        return this.estaContidoEm(outro) && this.cardinalidade() < outro.cardinalidade();
    }
 
    /** Complemento de A em relação ao universo U: U − A */
    public Conjunto<T> complemento(Conjunto<T> universo) {
        return universo.diferenca(this);
    }
 
    /** A = B  <=>  A ⊆ B e B ⊆ A */
    public boolean igual(Conjunto<T> outro) {
        return this.estaContidoEm(outro) && outro.estaContidoEm(this);
    }
 
    /** Converte para o HashSet nativo, usado só para validar a implementação. */
    public Set<T> paraNativo() {
        return new HashSet<>(elementos);
    }
 
    public List<T> elementos() {
        return new ArrayList<>(elementos);
    }
 
    @Override
    public String toString() {
        if (elementos.isEmpty()) return "{}";
        List<T> copia = new ArrayList<>(elementos);
        try {
            copia.sort(null); // ordena só para exibir (se os elementos forem comparáveis)
        } catch (ClassCastException ignorada) {
            // mantém a ordem de inserção
        }
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < copia.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(copia.get(i));
        }
        return sb.append("}").toString();
    }
}
 