import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class TreeIsomorphism {
    private final Graph graph;
    private final boolean isTree;
    private final String invalidReason;
    private final List<Integer> centers;
    private final String canonicalEncoding;

    public TreeIsomorphism(Graph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("graph nao pode ser nulo");
        }
        this.graph = graph;

        String reason = validateTree(graph);
        if (reason != null) {
            this.isTree = false;
            this.invalidReason = reason;
            this.centers = null;
            this.canonicalEncoding = null;
        } else {
            this.isTree = true;
            this.invalidReason = null;
            this.centers = findCenters(graph);
            this.canonicalEncoding = computeCanonicalEncoding(graph, this.centers);
        }
    }

    public Graph getGraph() {
        return graph;
    }

    public boolean isTree() {
        return isTree;
    }

    public String getInvalidReason() {
        return invalidReason;
    }

    public String getValidationMessage() {
        if (isTree) {
            return "Valida: true";
        }
        return "Valida: false; Motivo: " + invalidReason;
    }

    public List<Integer> getCenters() {
        return centers;
    }

    public String getCanonicalEncoding() {
        return canonicalEncoding;
    }

    private static String validateTree(Graph g) {
        int V = g.V();
        int E = g.E();
        if (V <= 0) {
            return "grafo vazio (V = " + V + ")";
        }
        for (int v = 0; v < V; v++) {
            Set<Integer> seen = new HashSet<>();
            for (int w : g.adj(v)) {
                if (w == v) {
                    return "o grafo contem laco no vertice " + v;
                }
                if (!seen.add(w)) {
                    return "o grafo contem aresta paralela entre " + v + " e " + w;
                }
            }
        }
        if (E != V - 1) {
            if (E >= V) {
                return "o grafo contem ciclo (E = " + E + " >= V = " + V + ")";
            }
            return "o grafo e desconexo (E = " + E + " < V - 1 = " + (V - 1) + ")";
        }
        boolean[] visited = new boolean[V];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        visited[0] = true;
        int count = 1;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int w : g.adj(u)) {
                if (!visited[w]) {
                    visited[w] = true;
                    count++;
                    queue.add(w);
                }
            }
        }
        if (count != V) {
            return "o grafo e desconexo (BFS alcanca apenas " + count + " de " + V + " vertices)";
        }
        return null;
    }

    private static List<Integer> findCenters(Graph g) {
        int V = g.V();
        List<Integer> result = new ArrayList<>();
        if (V == 1) {
            result.add(0);
            return result;
        }
        int[] degree = new int[V];
        for (int v = 0; v < V; v++) {
            degree[v] = g.degree(v);
        }
        List<Integer> leaves = new ArrayList<>();
        for (int v = 0; v < V; v++) {
            if (degree[v] <= 1) {
                leaves.add(v);
            }
        }
        int processed = leaves.size();
        while (processed < V) {
            List<Integer> newLeaves = new ArrayList<>();
            for (int u : leaves) {
                for (int w : g.adj(u)) {
                    degree[w]--;
                    if (degree[w] == 1) {
                        newLeaves.add(w);
                    }
                }
            }
            processed += newLeaves.size();
            leaves = newLeaves;
        }
        return leaves;
    }

    private static String computeCanonicalEncoding(Graph g, List<Integer> centers) {
        if (centers.size() == 1) {
            return encode(g, centers.get(0), -1);
        }
        String code1 = encode(g, centers.get(0), -1);
        String code2 = encode(g, centers.get(1), -1);
        return code1.compareTo(code2) <= 0 ? code1 : code2;
    }

    private static String encode(Graph g, int v, int parent) {
        List<String> childCodes = new ArrayList<>();
        for (int w : g.adj(v)) {
            if (w != parent) {
                childCodes.add(encode(g, w, v));
            }
        }
        Collections.sort(childCodes);
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        for (String c : childCodes) {
            sb.append(c);
        }
        sb.append(')');
        return sb.toString();
    }
}
