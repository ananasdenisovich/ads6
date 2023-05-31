public class Main {
    public static void main(String[] args) {
        MyGraph<Integer> temp = new MyGraph<>();
        temp.addVertex(1);
        temp.addVertex(3);
        temp.addVertex(2);
        temp.addVertex(4);
        temp.addEdge(1, 3);
        temp.addEdge(1, 2);
        temp.addEdge(2, 3);
        temp.addEdge(3, 4);
        temp.printGraph();
        temp.removeEdge(2,3);
        temp.printGraph();
        System.out.println(temp.hasEdge(1,3));
        System.out.println(temp.hasEdge(1,2));
        System.out.println(temp.getNeighbors(1));
        temp.DFS(1);
    }
}