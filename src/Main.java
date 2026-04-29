public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException(
                    "informe dois arquivos de entrada. Ex.: java Main ../dados/arvore1.txt ../dados/arvore2.txt"
            );
        }

        Graph tree1 = new Graph(new In(args[0]));
        Graph tree2 = new Graph(new In(args[1]));

        TreeIsomorphism analysis1 = new TreeIsomorphism(tree1);
        TreeIsomorphism analysis2 = new TreeIsomorphism(tree2);

        printAnalysis("Arvore 1", args[0], tree1, analysis1);
        StdOut.println();
        printAnalysis("Arvore 2", args[1], tree2, analysis2);
        StdOut.println();

        StdOut.println("=== Resultado ===");
        if (!analysis1.isTree() || !analysis2.isTree()) {
            StdOut.println("Comparacao encerrada: pelo menos uma das entradas nao e uma arvore valida.");
            return;
        }
        boolean isomorphic = analysis1.getCanonicalEncoding()
                .equals(analysis2.getCanonicalEncoding());
        if (isomorphic) {
            StdOut.println("As arvores SAO isomorfas.");
        } else {
            StdOut.println("As arvores NAO sao isomorfas.");
        }
    }

    private static void printAnalysis(String titulo, String arquivo, Graph g, TreeIsomorphism a) {
        StdOut.println("=== " + titulo + " (" + arquivo + ") ===");
        StdOut.print("Lista de adjacencia:");
        StdOut.println();
        StdOut.print(g);
        if (a.isTree()) {
            StdOut.println("Valida: true");
            StdOut.println("Centro(s): " + a.getCenters());
            StdOut.println("Codificacao canonica: " + a.getCanonicalEncoding());
        } else {
            StdOut.println("Valida: false");
            StdOut.println("Motivo: " + a.getInvalidReason());
        }
    }
}
