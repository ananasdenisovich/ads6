import java.util.*;

public class MyGraph<Vertex> {
    private Map<Vertex, List<Vertex>> list;


    public MyGraph() {
        list = new HashMap<>();
    }

    public void addVertex(Vertex vertex) {
        list.put(vertex, new LinkedList<>());
    }

    public void addEdge(Vertex source, Vertex destination) {
        validateVertex(source);
        validateVertex(destination);
        list.get(source).add(destination);
        list.get(destination).add(source);

    }

    private void validateVertex(Vertex index) {
        if (!list.containsKey(index)) {
            throw new IllegalArgumentException("Vertex " + index + " is out of the range");
        }
    }

    public void printGraph() {
        for (Map.Entry<Vertex, List<Vertex>> entry : list.entrySet()) {
            Vertex vertex = entry.getKey();
            List<Vertex> neighbors = entry.getValue();
            System.out.print("Vertex " + vertex + " is connected to: ");
            for (Vertex neighbor : neighbors) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }

    public void removeEdge(Vertex source, Vertex destination) {
        validateVertex(source);
        validateVertex(destination);
        List<Vertex> neighbors = list.get(source);
        if (neighbors!=null) {
            neighbors.remove(destination);
        }
        list.get(destination).remove(source);
    }

    public boolean hasEdge(Vertex source, Vertex destination) {
        validateVertex(source);
        validateVertex(destination);
        List<Vertex> neighbors = list.get(source);
        return neighbors != null && neighbors.contains(destination);
    }

    public List<Vertex> getNeighbors(Vertex vertex) {
        validateVertex(vertex);
        return list.getOrDefault(vertex, new LinkedList<>());
    }

    public void DFS(Vertex start) {
        validateVertex(start);
        Map<Vertex, Boolean> visited = new HashMap<>();
        for (Vertex vertex:list.keySet()) {
            visited.put(vertex,false);
        }
        DFSHelper(start, visited);

    }
    private void DFSHelper(Vertex vertex, Map<Vertex, Boolean> visited) {
        visited.put(vertex, true);
        System.out.print(vertex + " ");
        for (Vertex neighbor : list.get(vertex)) {
            if (!visited.get(neighbor)) {
                DFSHelper(neighbor, visited);
            }
        }
    }
    public void BFS(Vertex start) {
        validateVertex(start);//checks the vertex

        Map<Vertex, Boolean> visited = new HashMap<>();
        for (Vertex vertex : list.keySet()) { //visits all vertexes in list
            visited.put(vertex, false);
        }

        Queue<Vertex> queue = new LinkedList<>();
        visited.put(start, true);
        queue.add(start);

        while (!queue.isEmpty()) {
            Vertex currentVertex = queue.poll();//poll retrieves and removes start, so that next vertex is start
            System.out.print(currentVertex + " ");

            for (Vertex neighbor : list.get(currentVertex)) { //gets vertexes neighbors first(on 1 level)
                if (!visited.get(neighbor)) {
                    visited.put(neighbor, true);
                    queue.add(neighbor);//adds elements to queue in bfs order
                }
            }
        }
        System.out.println(queue);
    }
    public void search(Vertex start, Vertex destination) {
        Set<Vertex> visited = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();

        visited.add(start);
        queue.add(start);
        Vertex found = null;
        while (!queue.isEmpty()) {
            Vertex current = queue.poll();//checks all vertices until queue is empty or destination is found
            if (current.equals(destination)) {//self-explanatory
                found = current;
            }
            List<Vertex> neighbors = list.getOrDefault(current, Collections.emptyList());
            for (Vertex neighbor : neighbors) {//makes sure that the process ends when destination is found
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        System.out.println(found + " found");
    }
    public Map<Vertex, Double> dijkstra(Vertex start) {
        Map<Vertex, Double> distances = new HashMap<>(); // creates a map to store the distances from the start vertex to each vertex in the graph
        for (Vertex node : list.keySet()) {
            distances.put(node, Double.MAX_VALUE);
        }
        distances.put(start, 0d);

        PriorityQueue<Vertex> queue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));
        queue.add(start);// creates a priority queue to store the vertices based on their distances
        while (!queue.isEmpty()) {
            Vertex currentVertex = queue.poll(); // extracts vertex with the smallest distance from queue
            List<Vertex> neighbors = list.get(currentVertex);// gets neighbors of the current vertex
            if (neighbors == null) {
                continue;
            }
            for (Vertex neighbor : neighbors) {// iterates over  neighbors of  current vertex
                Double currentDistance = distances.get(currentVertex);//gets the current distance from the start vertex to the neighbor
                if (currentDistance == null) {
                    continue;
                }
                double distance = currentDistance + 1;// calculates the new distance from the start vertex to the neighbor
                Double neighborDistance = distances.get(neighbor);
                if (neighborDistance == null || distance < neighborDistance) {// updates the distance if the new distance is smaller than the current recorded distance

                    distances.put(neighbor, distance);
                    queue.add(neighbor);
                }
            }
        }

        return distances;//returns all distances
    }

}