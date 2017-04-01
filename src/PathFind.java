
/**
 * Created by Hemal Herath on 4/2/2017.
 */

import java.awt.*;
import java.util.*;

public class PathFind {

    Node start;
    Node end;
    Node[][] gridArea;

    //Horizontal and VerticalDistance
    double hVDistance = 1.0;

    //Diagonal Distance
    //Manhattan values.
    public static double Manhattan() {

        double dDistance = 2;
        return dDistance;
    }

    //Euclidean values.
    public static double Euclidean() {
        double dDistance = 1.4;
        return dDistance;
    }

    //Chebyshev values.
    public static double Chebyshev() {
        double dDistance = 1;
        return dDistance;
    }

    /*
     * @param matrix The boolean matrix from the framework given
     * @param sx1 start x value
     * @param sy1 start y value
     * @param ex2 end x value
     * @param ey2 end x value
     * @return The path nodes
     */
    
     public ArrayList<Node> distance(boolean[][] matrix, int sx1, int sy1, int ex2, int ey2,double dDistance,String name) {

        int size = matrix.length;
   
        start = new Node(sx1, sy1);
        end = new Node(ex2, ey2);
        // The grid that is used to store nodes
        gridArea = new Node[size][size];
        // Creating nodes and finding blocked cells in matrix and mapping accordingly to our grid
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                gridArea[i][j] = new Node(i, j);
                if (matrix[i][j] == false) {

                    gridArea[i][j].blocked = true;


                }
            }
        }

        // setting start distance to 0.
        // All other nodes will have infinity distance at the beginning
        start.distance =0;

        // a comparator object to deal with Priority Queue
        Comparator<Node> adjacencyComparator = (left, right) -> {
            if (left.distance > (right.distance)) {
                return 1;//swapping the values

            }
            return -1;
        };

        // Queue to store visiting nodes
        Queue<Node> queue = new PriorityQueue(size, adjacencyComparator);

        queue.add(start);

        while (queue.size() > 0) {
            Node current = queue.remove();
            Node nextNode;//next node

            if (current.x==end.x && current.y==end.y){//when come to end breaking while loop
            	break;
            }
            
            // Top
            if (current.x - 1 >= 0) {

                // Top Top
                nextNode = gridArea[current.x - 1][current.y];
                if (!nextNode.visited && !nextNode.blocked && nextNode.distance > current.distance + hVDistance) {
                    nextNode.distance = current.distance + hVDistance;
                    nextNode.parent = current;
                    queue.add(nextNode);
                }

                // Top Left
                /*if (current.y - 1 > 0) {
                    nextNode = gridArea[current.x - 1][current.y - 1];
                    if (!nextNode.visited && !nextNode.blocked && nextNode.distance > current.distance + dDistance) {
                        nextNode.distance = current.distance + dDistance;
                        nextNode.parent = current;
                        queue.add(nextNode);
                    }
                }*/

                // Top Right
                /*if (current.y + 1 < size) {
                    nextNode = gridArea[current.x - 1][current.y + 1];
                    if (!nextNode.visited && !nextNode.blocked && nextNode.distance > current.distance + dDistance) {
                        nextNode.distance = current.distance + dDistance;
                        nextNode.parent = current;
                        queue.add(nextNode);
                    }
                }*/
            }

            // Left
            if (current.y - 1 > 0) {
                nextNode = gridArea[current.x][current.y - 1];
                if (!nextNode.visited && !nextNode.blocked && nextNode.distance > current.distance + hVDistance) {
                    nextNode.distance = current.distance + hVDistance;
                    nextNode.parent = current;
                    queue.add(nextNode);
                }
            }

            // Right
            if (current.y + 1 < size) {
                nextNode = gridArea[current.x][current.y + 1];
                if (!nextNode.visited && !nextNode.blocked && nextNode.distance > current.distance + hVDistance) {
                    nextNode.distance = current.distance + hVDistance;
                    nextNode.parent = current;
                    queue.add(nextNode);
                }
            }
            
            // Down
            if (current.x + 1 < size) {

                // Down Down
                nextNode = gridArea[current.x + 1][current.y];
                if (!nextNode.visited && !nextNode.blocked && nextNode.distance > current.distance + hVDistance) {
                    nextNode.distance = current.distance + hVDistance;
                    nextNode.parent = current;
                    queue.add(nextNode);
                }

                // Down Left
                /*if (current.y - 1 >= 0) {
                    nextNode = gridArea[current.x + 1][current.y - 1];
                    if (!nextNode.visited && !nextNode.blocked && nextNode.distance > current.distance + dDistance) {
                        nextNode.distance = current.distance + dDistance;
                        nextNode.parent = current;
                        queue.add(nextNode);
                    }
                }*/

                // Down Right
                /*if (current.y + 1 < size) {
                    nextNode = gridArea[current.x + 1][current.y + 1];
                    if (!nextNode.visited && !nextNode.blocked && nextNode.distance > current.distance + dDistance) {
                        nextNode.distance = current.distance + dDistance;
                        nextNode.parent = current;
                        queue.add(nextNode);
                    }
                }*/
            }
            current.visited = true;
        }

        ArrayList<Node> path = new ArrayList<>();

        // Checking if a path exists
        if (!(gridArea[end.x][end.y].distance == Integer.MAX_VALUE || gridArea[start.x][start.y].distance == Integer.MAX_VALUE)) {
            //Trace back the path
            Node current = gridArea[end.x][end.y];
            System.out.println(name+":"+current.distance);
            while (current.parent != null) {
                path.add(current.parent);

                current = current.parent;
            }
        } else System.out.println("NO POSSIBLE PATH");


        return path;
    }


    class Node {//inner class
        int x;
        int y;
        double distance = Integer.MAX_VALUE;
        Node parent = null;
        boolean visited;
        boolean blocked;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    public static void display(boolean[][] a, boolean which) {
        int N = a.length;
        StdDraw.setXscale(-1, N);
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                     StdDraw.square(j, N - i - 1, .5);//show all cells without black cells
                else StdDraw.filledSquare(j, N - i - 1, .5);//show black cells
        
    }

    // draw the N-by-N boolean matrix to standard draw, including the points A (x1, y1) and B (x2,y2) to be marked by a circle
    public static void display(boolean[][] a, boolean which, int x1, int y1, int x2, int y2,ArrayList<Node> path) {
        int N = a.length;
        int s=path.size();
        int count=0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                    if ((i == x1 && j == y1) || (i == x2 && j == y2)) {
                        StdDraw.setPenColor(Color.MAGENTA);
                        StdDraw.filledCircle(j, N - i - 1, .5);

                    }


        for (PathFind.Node node : path) {
            if(s-count==1){
                return;
            }
            count++;

                StdDraw.setPenColor(Color.RED);
                StdDraw.filledCircle(node.y,  N- node.x - 1, .5);
            
        }
    }

    // return a random N-by-N boolean matrix, where each entry is
    // true with probability p
    public static boolean[][] random(int N, double p) {
        boolean[][] a = new boolean[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                a[i][j] = StdRandom.bernoulli(p);
        return a;
    }

    public static void main(String[] args){

        // The following will generate a 10x10 squared grid with relatively few obstacles in it
        // The lower the second parameter, the more obstacles (black cells) are generated
        boolean[][] randomlyGenMatrix = random(10,0.8);

        StdArrayIO.print(randomlyGenMatrix);
        display(randomlyGenMatrix, true);

        // Reading the coordinates for points A and B on the input squared grid.
        Scanner in = new Scanner(System.in);
        
        System.out.println("\nENTER x1 FOR A > ");
        int Ay1 = in.nextInt();
        
        System.out.println("ENTER y1 fOR A > ");
        int Ax1 = in.nextInt();
        
        System.out.println("ENTER x2 FOR B > ");
        int By2 = in.nextInt();
        
        System.out.println("ENTER y2 FOR B > ");
        int Bx2 = in.nextInt();
        
        Stopwatch time = new Stopwatch();
        
        ArrayList<PathFind.Node> path1 = new PathFind().distance(randomlyGenMatrix, Ax1, Ay1, Bx2, By2,Manhattan(),"MANHATTAN");
        System.out.println(time.elapsedTime());
        
        ArrayList<PathFind.Node> path2 = new PathFind().distance(randomlyGenMatrix, Ax1, Ay1, Bx2, By2,Euclidean(),"EUCLIDEAN");
        ArrayList<PathFind.Node> path3 = new PathFind().distance(randomlyGenMatrix, Ax1, Ay1, Bx2, By2,Chebyshev(),"CHEBYSHEV");
        
        display(randomlyGenMatrix, true, Ax1, Ay1, Bx2, By2, path1);

    }
}

