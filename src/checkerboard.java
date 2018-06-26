import java.awt.Color;
import java.awt.Polygon;
import java.awt.geom.Point2D;

	public class checkerboard {
	Polygon p;P3D vertex1;P3D vertex2;
	P3D vertex3;P3D vertex4;P3D Normal;
	Color color;P3D temp;int mat;
	public checkerboard(P3D vert1, P3D norm, Color color,int mat) {
		super();this.vertex1 = vert1;temp=vert1.add(new P3D(0,0,5));
		this.vertex2 = temp;temp=vert1.add(new P3D(5,0,5));
		this.vertex3 = temp;temp=vert1.add(new P3D(5,0,0));
		this.vertex4 = temp;this.Normal = norm;this.color = color;
		this.mat=mat;
		int[] x={(int) this.vertex1.x,(int) this.vertex2.x,(int) 
		this.vertex3.x,(int) this.vertex4.x};
		int[] z={(int) this.vertex1.z,(int) this.vertex2.z,(int) 
		this.vertex3.z,(int) this.vertex4.z};int npoints=4;
		p=new Polygon(x,z,npoints);}
	public Double intersect(Ray ray) {
	double d=Normal.dot(vertex1);double num= d-Normal.dot(ray.position);
	double den=Normal.dot(ray.direction);
		if(den!=0 && (num/den)>0){double u=(num/den);
	P3D Ip=ray.position.add(ray.direction.scale(u));
	Point2D np=new Point2D.Double(Ip.x,Ip.z);
	if(p.contains(np)){return (num/den);}
	else{return null;}}else{return null;}}
	public Color color(){Color col=new Color(1.0f,1.0f,1.0f);
	return col;}}
