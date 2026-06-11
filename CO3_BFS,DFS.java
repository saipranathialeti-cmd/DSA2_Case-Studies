import java.util.*;

public class WasteSenseGraph {

    static Map<String, List<String>> graph = new HashMap<>();

    // Add edge
    static void addEdge(String u, String v) {
        graph.putIfAbsent(u, new ArrayList<>());
        graph.putIfAbsent(v, new ArrayList<>());

        graph.get(u).add(v);
        graph.get(v).add(u);
    }

    // BFS Traversal
    static void BFS(String start) {

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        visited.add(start);
        queue.offer(start);

        System.out.print("BFS Traversal:\n");

        while (!queue.isEmpty()) {

            String node = queue.poll();
            System.out.print(node + " ");

            for (String neighbor : graph.get(node)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }

        System.out.println();
    }

    // DFS Traversal
    static void DFS(String start) {

        Set<String> visited = new HashSet<>();

        System.out.print("DFS Traversal:\n");
        dfsHelper(start, visited);

        System.out.println();
    }

    static void dfsHelper(String node, Set<String> visited) {

        visited.add(node);

        System.out.print(node + " ");

        for (String neighbor : graph.get(node)) {

            if (!visited.contains(neighbor)) {
                dfsHelper(neighbor, visited);
            }
        }
    }

    public static void main(String[] args) {

        // Example from the poster

        addEdge("Collection Center", "Bin A");
        addEdge("Collection Center", "Bin B");
        addEdge("Bin A", "Bin C");
        addEdge("Bin B", "Recycling Plant");

        System.out.println("Locations:");
        System.out.println("Collection Center");
        System.out.println("Bin A");
        System.out.println("Bin B");
        System.out.println("Bin C");
        System.out.println("Recycling Plant");

        System.out.println();

        BFS("Collection Center");

        System.out.println();

        DFS("Collection Center");
    }
}