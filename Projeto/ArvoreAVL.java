public class ArvoreAVL {

    class No {
        int valor, altura;
        No esquerda, direita;

        No(int v) {
            valor = v;
            altura = 1;
        }
    }

    No raiz;

    int altura(No no) {
        if (no == null) return 0;
        return no.altura;
    }

    int fatorBalanceamento(No no) {
        if (no == null) return 0;
        return altura(no.esquerda) - altura(no.direita);
    }

    No rotacaoDireita(No y) {
        No x = y.esquerda;
        No t2 = x.direita;

        x.direita = y;
        y.esquerda = t2;

        y.altura = 1 + Math.max(altura(y.esquerda), altura(y.direita));
        x.altura = 1 + Math.max(altura(x.esquerda), altura(x.direita));

        return x;
    }

    No rotacaoEsquerda(No x) {
        No y = x.direita;
        No t2 = y.esquerda;

        y.esquerda = x;
        x.direita = t2;

        x.altura = 1 + Math.max(altura(x.esquerda), altura(x.direita));
        y.altura = 1 + Math.max(altura(y.esquerda), altura(y.direita));

        return y;
    }

    No inserir(No no, int valor) {
        if (no == null) return new No(valor);

        if (valor < no.valor) {
            no.esquerda = inserir(no.esquerda, valor);  // subárvore esquerda
        } else if (valor > no.valor) {
            no.direita = inserir(no.direita, valor);    // subárvore direita
        } else {
            return no;
        }

        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));
        int fb = fatorBalanceamento(no);

        if (fb > 1 && valor < no.esquerda.valor) return rotacaoDireita(no);
        if (fb < -1 && valor > no.direita.valor) return rotacaoEsquerda(no);
        if (fb > 1 && valor > no.esquerda.valor) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }
        if (fb < -1 && valor < no.direita.valor) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    public void inserir(int valor) {
        raiz = inserir(raiz, valor);
    }

    
    void imprimirPorNiveis() {
        java.util.Queue<No> fila = new java.util.LinkedList<>();
        fila.add(raiz);
        int nivel = 0;

        while (!fila.isEmpty()) {
            int tamanho = fila.size();
            System.out.print("Nível " + nivel + ": ");
            for (int i = 0; i < tamanho; i++) {
                No atual = fila.poll();
                System.out.print(atual.valor + " ");
                if (atual.esquerda != null) fila.add(atual.esquerda);
                if (atual.direita != null) fila.add(atual.direita);
            }
            System.out.println();
            nivel++;
        }
    }

    // Lista de nós com altura e fator de balanceamento
    void imprimirAlturaFB(No no) {
        if (no != null) {
            imprimirAlturaFB(no.esquerda);
            System.out.println("Nó " + no.valor + " -> Altura: " + no.altura + ", FB: " + fatorBalanceamento(no));
            imprimirAlturaFB(no.direita);
        }
    }

    public static void main(String[] args) {
        ArvoreAVL avl = new ArvoreAVL();

        int[] teste1 = {40, 20, 60, 10, 30, 25};
        for (int v : teste1) avl.inserir(v);
        System.out.println("Árvore 1:");
        avl.imprimirPorNiveis();
        avl.imprimirAlturaFB(avl.raiz);

        avl = new ArvoreAVL();
        int[] teste2 = {60, 40, 80, 35, 50, 90, 20, 38, 37};
        for (int v : teste2) avl.inserir(v);
        System.out.println("\nÁrvore 2:");
        avl.imprimirPorNiveis();
        avl.imprimirAlturaFB(avl.raiz);

        avl = new ArvoreAVL();
        int[] teste3 = {30, 20, 10, 25, 40, 50, 5, 35, 45};
        for (int v : teste3) avl.inserir(v);
        System.out.println("\nÁrvore 3:");
        avl.imprimirPorNiveis();
        avl.imprimirAlturaFB(avl.raiz);
    }
}
