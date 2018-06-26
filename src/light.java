import java.awt.Color;

	public class light {
	static P3D lightpos = new P3D(0.0, 5.5,-15.0);static float intens=0.90f;
	public static Color get_color(Ray r,Sphere s, Double d){
	P3D Ipos=r.position.add(r.direction.scale(d));P3D N = s.center.sub(Ipos);
	N=N.normalize();P3D L=lightpos.sub(Ipos);
	L=L.normalize();double temp=(L.dot(N)) * intens;
	if(temp<0){temp=-1*temp;}
	P3D lightposSpec = new P3D(0.9, 1.5, -2.0);P3D LL=Ipos.sub(lightposSpec);
	LL=LL.normalize();P3D V=Ipos.sub(r.position);V=V.normalize();
	P3D R;R=N.scale(2*(LL.dot(N))).sub(LL) ;
	R=R.normalize();int alpha=50;
	Double spec=Math.pow((R.dot(V)),alpha)*intens;
	Double red=s.color.getRed()* (temp + spec);Double green=s.color.getGreen()* 
	(temp + spec);Double blue=s.color.getBlue()* (temp + spec);
	 if(red > 255){red=255.0;}if(green > 255){green=255.0;}
	 if(blue > 255){blue=255.0;}
	return new Color(red.intValue(),green.intValue(),blue.intValue());}}
