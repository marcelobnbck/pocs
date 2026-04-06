import java.util.*;

void DfsIterative(Map<Integer, List<Integer>> graph, int startNode) {
    // Use a Set to track visited nodes and an explicit Stack for traversal
    Set<Integer> visited = new HashSet<>();
    Deque<Integer> stack = new ArrayDeque<>();

    stack.push(startNode);

    while (!stack.isEmpty()) {
        int current = stack.pop();

        // Only process if the node hasn't been visited yet
        if (!visited.contains(current)) {
            System.out.print(current + " ");
            visited.add(current);

            // Push all unvisited neighbors to the stack
            List<Integer> neighbors = graph.getOrDefault(current, List.of());

            // To match recursive DFS order (left-to-right), push in reverse
            for (int i = neighbors.size() - 1; i >= 0; i--) {
                int neighbor = neighbors.get(i);
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                }
            }
        }
    }
}

void addEdge(Map<Integer, List<Integer>> graph, int u, int v) {
    graph.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
}

// Java 25 Compact Source File: No class declaration needed for single-file programs
void main() {
    // 1. Represent the graph using an adjacency list
    Map<Integer, List<Integer>> graph = new HashMap<>();
    addEdge(graph, 0, 1);
    addEdge(graph, 0, 2);
    addEdge(graph, 1, 3);
    addEdge(graph, 1, 4);
    addEdge(graph, 2, 5);

    System.out.println("\nIterative DFS starting from node 0:");
    DfsIterative(graph, 0);
}
