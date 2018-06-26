import java.awt.Color;
import java.util.ArrayList;

/**
 * Scene Eric McCreath 2009
 */

public class Scene extends ArrayList<Sphere> {

	ArrayList<checkerboard> chb=new ArrayList<checkerboard>(); 
	Color background = Color.black;
	Color c;int m;
	static P3D lightpos = new P3D(0.0, 5.5,-15.0);
	
	    public Color raytrace(Ray r,int limit) {
		Sphere hit = null;
		Double mindis = null;
		checkerboard hit1=null;
		if(limit<2){
		for (Sphere s : this) {
			Double t = s.intersect(r);
			if (t != null) {
				if (mindis == null || t < mindis) {
					mindis = t;
					c= light.get_color(r,s,t);
					hit = s;
				}}}
		
		

		for(checkerboard ch:this.chb){
			Double t1 = ch.intersect(r);
			if (t1 != null) {
			if (mindis == null || t1 < mindis)
			{		mindis = t1;
					c= ch.color;
					hit1 = ch;}}}
		if(hit!=null && hit.mat==1){
			r=ray1(r,hit,mindis);
			c=this.raytrace(r,limit=limit +1);
		}}
		
		if ((hit != null || hit1!=null) && shadow(r,mindis)) {
			return c;}
		else if((hit != null || hit1!=null)){
			return new Color(30,30,30);}
		else {return background;}}
		
	    public static Ray ray1(Ray r,Sphere s,Double d){
		P3D Ipos=r.position.add(r.direction.scale(d));P3D RI=Ipos.normalize();
		P3D N = s.center.sub(Ipos);N=N.normalize();
		P3D RRD=RI.sub((N.scale(2)).scale(RI.dot(N)));return new Ray(Ipos,RRD);}
	
	public boolean shadow(Ray r,Double d){
	P3D Ipos=r.position.add(r.direction.scale(d));P3D L=lightpos.sub(Ipos);L=L.normalize();
	Ray r2=new Ray(Ipos,L);	for (Sphere s : this) {Double t = s.intersect(r2);
	if (t != null) {return false;}}return true;}}
