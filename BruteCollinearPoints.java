import java.util.Arrays;

public class BruteCollinearPoints {
    
    private LineSegment[] segments;
    private LineSegment[] aux;
    private int nbSegments = 0;
    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        if (points == null) throw new IllegalArgumentException();
        Point[] pointsAux = new Point[points.length];
        
        for (int i = 0; i < points.length; i++) {
            pointsAux[i] = points[i];
        }
        
        Arrays.sort(pointsAux);
        segments = new LineSegment[0];
        for (int i = 0; i < pointsAux.length; i++) {
            for (int j=i+1; j < pointsAux.length; j++) {
                for (int k=j+1; k < pointsAux.length; k++) {
                    for (int l=k+1; l < pointsAux.length; l++) {
                       if (pointsAux[i] == null || pointsAux[j] == null || pointsAux[k] == null || pointsAux[l]== null) throw new IllegalArgumentException();
                       if (pointsAux[i].compareTo(pointsAux[j]) == 0 || pointsAux[i].compareTo(pointsAux[k]) == 0 || pointsAux[i].compareTo(pointsAux[l]) == 0 || pointsAux[j].compareTo(pointsAux[k]) == 0 || pointsAux[j].compareTo(pointsAux[l]) == 0 || pointsAux[k].compareTo(pointsAux[l]) == 0) throw new IllegalArgumentException();
                       if (pointsAux[i].slopeTo(pointsAux[j]) == pointsAux[i].slopeTo(pointsAux[k]) && pointsAux[i].slopeTo(pointsAux[l]) == pointsAux[i].slopeTo(pointsAux[j])) {                      
                           aux = Arrays.copyOf(segments, segments.length+1);                              
                           segments = aux; 
                           segments[segments.length-1] = new LineSegment(pointsAux[i], pointsAux[l]);
                           nbSegments++; 
                       }
                    }
                }
            }
        }
    }
    
    
    
    public int numberOfSegments() {
        return getNumberOfSegments();
    }
    public LineSegment[] segments() {
        // the line segments
        return getSegments();
    }
    
    private int getNumberOfSegments() {
        return nbSegments;
    }
    
    private LineSegment[] getSegments() {
        return segments;
    }
}
