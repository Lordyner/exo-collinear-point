import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    
    private LineSegment[] segments;
    private int nbSegments = 0;
  
    public FastCollinearPoints(Point[] points) {
        // Si le tableau de point est null, on throw une exception
        if (points == null) throw new IllegalArgumentException();
        
        // Si un des points est null, on throw une exception
        for (Point point : points) {
            if (point == null) throw new IllegalArgumentException();
        }
        // On fais une copie du tableau des points
        Point[] sortedPoints = points.clone();
        // On tri les points
        Arrays.sort(sortedPoints);
        // Si deux points sont égaux, on throw une exception
        for (int i = 0; i < sortedPoints.length - 1; i++) {
            if (sortedPoints[i].compareTo(sortedPoints[i + 1]) == 0) throw new IllegalArgumentException();
        }
        
        List<LineSegment> segmentList = new ArrayList<>();
        // On parcourt tout les points, jusqu'à taille du tableau - 3
        for (int first = 0; first < sortedPoints.length - 3; first++) {
            // On tri le tableau des points, à partir de l'index i+1 jusqu'à la fin, en fonction du slope des points à i
            Arrays.sort(sortedPoints, first + 1, sortedPoints.length, sortedPoints[first].slopeOrder());
            int second = first+1;
            
            // On parcourt le tableau de points à partir de i + 2          
            for (int third = second + 1; third < sortedPoints.length; third++) {   
                // Si first, second et third sont pas sur la même ligne
                if (Double.compare(sortedPoints[first].slopeTo(sortedPoints[second]), sortedPoints[first].slopeTo(sortedPoints[third])) != 0) {
                    // Et qu'il y a eu 3 ou plus points sur la même ligne
                    if (third - second >= 3) {
                        // On ajoute un segment
                        addSegment(segmentList, sortedPoints, first, second, third);
                    }
                     
                    second = third;
                }
            }
            // Si on a quitté la première boucle avec 3 ou plus, points collinéaire
            if (sortedPoints.length - second >= 3) {
                // On ajoute un segment
                addSegment(segmentList, sortedPoints, first, second, sortedPoints.length);
            }
        }
        
        segments = segmentList.toArray(new LineSegment[segmentList.size()]);
    }
    

    private void addSegment(List<LineSegment> segmentList, Point[] points, int i, int first, int last) {
        // On tri le tableau de point à partir de first jusqu'à last
        Arrays.sort(points, first, last);
        // Si le point first est plus grand que le point i, alors on return
        if (points[first].compareTo(points[i]) >= 0) {
            return;
        }
        // Sinon on ajoute un segment de i à last - 1
        segmentList.add(new LineSegment(points[i], points[last - 1]));
        nbSegments++;
    }


    
    public int numberOfSegments() {
        // the number of line segments
        return getNumberOfSegments();
    }
    public LineSegment[] segments() {
        return getSegments();
    }
    
    private int getNumberOfSegments() {
        return nbSegments;
    }
    
    private LineSegment[] getSegments() {
        return segments;
    }
    
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        // Points du fichier input8.txt pour débug
//        points[0] = new Point(10000, 0);
//        points[1] = new Point(0, 10000);
//        points[2] = new Point(3000, 7000);
//        points[3] = new Point(7000, 3000);
//        points[4] = new Point(20000, 21000);
//        points[5] = new Point(3000, 4000);
//        points[6] = new Point(14000, 15000);
//        points[7] = new Point(6000, 7000);
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }    
    
}
