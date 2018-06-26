import java.awt.Point;
import java.awt.Polygon;
public class poly {
public static void main(String[] args){
int[] x={0,0,2,2};int[] y={0,2,2,0};int npoints=4;
Polygon p=new Polygon(x,y,npoints);
System.out.println(p.contains(new Point(2,2)));}}
