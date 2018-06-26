import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.Toolkit;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

public class RayTracer implements ActionListener {

	/**
	 * RayTracer Eric McCreath 2009
	 */

	final static Dimension dim = new Dimension(800, 800);

	JFrame jframe;
	JComponent canvas;
	int numframes = 50;
	final BufferedImage frames[];
	int currentframe = 0;
//	checkerboard chb=new checkerboard(new P3D(-20,-20,-5), new P3D(0,1,0), Color.WHITE,0);

	Timer timer;

	@SuppressWarnings("serial")
	public RayTracer() {
		jframe = new JFrame("RayTracer");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frames = new BufferedImage[numframes];
		for (int i = 0; i < frames.length; i++) {
			frames[i] = new BufferedImage(dim.width, dim.height,
					BufferedImage.TYPE_INT_RGB);
		}

		canvas = new JComponent() {
			public void paint(Graphics g) {
				if (frames[currentframe] != null)
					g.drawImage(frames[currentframe], 0, 0, null);
			}
		};
		canvas.setPreferredSize(dim);
		jframe.getContentPane().add(canvas);
		jframe.pack();
		jframe.setVisible(true);

		timer = new Timer(100, this);
	}

	public static void main(String[] args) {
		RayTracer rt = new RayTracer();
		View view = new View(new P3D(4.0, 4.0, -20.0), new P3D(-2.0, -2.0, 10.0),
				new P3D(10.0, 0.0, 2.0), new P3D(0.0, -10.0, 2.0));
		double stage = 0.0;

		// generate frames

		for (int i = 0; i < rt.frames.length; i++) {
			Scene scene = makeScene(stage);
			rt.frames[i] = rt.raytrace(scene, view);
			stage += 1.0 / rt.numframes;
			rt.currentframe = i;
			rt.canvas.repaint();
		}

		// run animation
		rt.timer.start();
	}

	public void animate() {
		currentframe++;
		if (currentframe >= frames.length)
			currentframe = 0;
		Graphics g = canvas.getGraphics();
		canvas.paint(g);
		Toolkit.getDefaultToolkit().sync();
	}

	private BufferedImage raytrace(Scene scene, View view) {
		System.out.println("Start Ray Tracing....");
		BufferedImage res = new BufferedImage(dim.width, dim.height,
				BufferedImage.TYPE_INT_RGB);
		P3D startdir = view.direction.add(view.across.scale(-0.5).add(
				view.down.scale(-0.5)));
		for (int y = 0; y < dim.height; y++) {
			for (int x = 0; x < dim.width; x++) {

				Ray r = new Ray(view.camera, startdir.add(
						view.across.scale(((double) x) / dim.getWidth())).add(
						view.down.scale(((double) y) / dim.getHeight()))
						.normalize());
				Color c = scene.raytrace(r,0);res.setRGB(x, y, c.getRGB());
			}
		}
		return res;
	}

	private static Scene makeScene(double step) {
		Scene res = new Scene();

		// body
		res.add(new Sphere(new P3D(0.0, 1.4, 0.0), 1.9, Color.blue,0));
		res.add(new Sphere(new P3D(0.0, 1.0, 0.0), 2.0, Color.blue,0));
		res.add(new Sphere(new P3D(0.0, -1.0, 0.0), 2.0, Color.red,0));
		res.add(new Sphere(new P3D(0.0, -1.3, 0.0), 2.0, Color.red,0));

		// legs
		for (int i = 0; i < 10; i++) {
			res.add(new Sphere(new P3D(-0.5, -2.6 - (i * 0.3), 0.0),
					1.0 - (i * 0.05), (i % 2 == 0 ? Color.red : Color.green),0));
			res.add(new Sphere(new P3D(0.5, -2.6 - (i * 0.3), 0.0),
					1.0 - (i * 0.05), (i % 2 == 0 ? Color.red : Color.green),0));
		}

		// head
		res.add(new Sphere(new P3D(0.0, 4.3, 0.0), 1.0, Color.white,0));
		res.add(new Sphere(new P3D(0.35, 4.35, -0.9), 0.1, Color.black,0));
		res.add(new Sphere(new P3D(-0.35, 4.35, -0.9), 0.1, Color.black,0));

		// balls

		res.add(new Sphere(ballpos(step), 0.3, Color.orange,0));
		res.add(new Sphere(ballpos(step + 1.0 / 5.0), 0.3, Color.green,0));
		res.add(new Sphere(ballpos(step + 2.0 / 5.0), 0.3, Color.red,0));
		res.add(new Sphere(ballpos(step + 3.0 / 5.0), 0.3, Color.yellow,0));
		res.add(new Sphere(ballpos(step + 4.0 / 5.0), 0.3, Color.white,0));
		
		res.add(new Sphere(new P3D(9.0, 1.35, 3.9), 4.6, Color.LIGHT_GRAY,1));
		res.add(new Sphere(new P3D(-12.35, 1.35, 1.9), 5.6, Color.LIGHT_GRAY,1));
		int x1;int x2;
		for(int j=0;j<60;j+=5){if(j%2==0){x1=-40;x2=-35;}else{x1=-35;x2=-40;}
		for(int i=0;i<30;i+=5){
		res.chb.add(new checkerboard(new P3D(x1+2*i,-20,20+j), new P3D(0,1,0), Color.GREEN,0));
		res.chb.add(new checkerboard(new P3D(x2+2*i,-20,20+j), new P3D(0,-1,0), Color.blue,0));
		}}
		for(int j=0;j<50;j+=5){for(int i=0;i<100;i+=5){
		res.chb.add(new checkerboard(new P3D(-40+i,20,20+j), new P3D(0,-1,0), new Color(85+j,
		156+j,200+j),0));}}
		
		return res;
	}

	private static P3D ballpos(double step) {

		double bhmax = 5.0;
		double bhmin = 0.3;
		double bwmax = 2.0;
		double bwmin = 1.0;

		double phase1 = 0.45;

		while (step > 1.0)
			step -= 1.0;
		if (step < phase1) {
			double phase = step / phase1;
			double x = bwmin - (bwmax + bwmin) * phase;
			double par = (phase - 0.5) * 2.0;
			double y = bhmin + bhmax * (1.0 - par * par);
			return new P3D(x, y, -2.5);
		} else if (step < 0.5) {
			double phase = (step - phase1) / (0.5 - phase1);
			double x = -bwmax + (bwmax - bwmin) * phase;
			double y = bhmin;
			return new P3D(x, y, -2.5);
		} else if (step < 0.5 + phase1) {
			double phase = (step - 0.5) / phase1;
			double x = -bwmin + (bwmax + bwmin) * phase;
			double par = (phase - 0.5) * 2.0;
			double y = bhmin + bhmax * (1.0 - par * par);
			return new P3D(x, y, -2.5);
		} else {
			double phase = (step - 0.5 - phase1) / (0.5 - phase1);
			double x = bwmax - (bwmax - bwmin) * phase;
			double y = bhmin;
			return new P3D(x, y, -2.5);

		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer) {
			animate();
		}
	}

}
