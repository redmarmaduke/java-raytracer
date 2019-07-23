package raytracer;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import raytracer.camera.ImageSensor;
import raytracer.camera.PinHole;
import raytracer.geometry.Box;
import raytracer.geometry.Cylinder;
import raytracer.geometry.Plane;
import raytracer.geometry.Sphere;
import raytracer.material.MatteMaterial;
import raytracer.material.ReflectiveMaterial;

public class RayTracer extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ImageSensor imageSensor;
	Renderer renderer;
	Scene scene;

	class RenderArea extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5905875225160086819L;

		RenderArea(int width, int height) {
			setPreferredSize(new Dimension(width, height));
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			Rectangle r = new Rectangle();
			g.getClipBounds(r);
			g.setColor(Color.black);
			g.fillRect(r.x, r.y, r.width, r.height);

			long start = System.currentTimeMillis();
			renderer.render(scene);
			long finish = System.currentTimeMillis();
			long timeElapsed = finish - start;
			System.out.println("Render Time : " + timeElapsed);

			for (int y = 0; y < r.height; ++y) {
				for (int x = 0; x < r.width; ++x) {
					Color color = imageSensor.getPixelColor(x, y);
					g.setColor(color);
					g.fillRect(x, r.height - y - 1, 1, 1);
				}
			}
		}
	}

	RenderArea panel;

	public static void main(String[] args) {
		new RayTracer();
	}

	public RayTracer() {
		super("Java Ray Tracer");
		int sceneId = 2;

		if (sceneId == 1) {
			imageSensor = new ImageSensor(400, 400);

			PinHole pinhole = new PinHole(imageSensor);
			Vector3d eye = new Vector3d(0.0, 0.0, 200.0);
			Vector3d up = new Vector3d(0.0, 1.0, 0.0);
			Vector3d lookAt = new Vector3d(0.0);
			pinhole.setLookAt(eye, up, lookAt);
			pinhole.setViewDistance(100.0);

			renderer = new Renderer(pinhole);
			scene = new Scene();

			Sphere sphere = new Sphere(new Vector3f(0.0f, 0.0f, 0.0f), 85.0f);
			MatteMaterial matte = new MatteMaterial(new Vector3f(1.0f, 0.0f, 0.0f));
			scene.add(new Primitive(matte, sphere));
		} else if (sceneId == 2) {
			imageSensor = new ImageSensor(600, 400);
			PinHole pinhole = new PinHole(imageSensor);
			Vector3d eye = new Vector3d(75.0f, 40.0f, 100.0f);
			Vector3d lookAt = new Vector3d(-10.0f, 39.0f, 0.0f);
			Vector3d up = new Vector3d(0.0f, 1.0f, 0.0f);
			pinhole.setLookAt(eye, up, lookAt);
			pinhole.setViewDistance(360.0f);
			renderer = new Renderer(pinhole);

			scene = new Scene();
			scene.add(pinhole);

			Vector3f position = new Vector3f(150.0f, 150.0f, 0.0f);
			Vector3f le = new Vector3f(3.0f);
			boolean castsShadows = true;
			Light light = new Light(position, le, castsShadows);
			scene.add(light);

			// sphere
			Vector3f yellow = new Vector3f(0.75f, 0.75f, 0.0f);
			Vector3f diffuseColor = yellow.multiply(0.5f);
			Vector3f glossyColor = yellow.multiply(0.15f);
			Vector3f white = new Vector3f(1.0f);
			Vector3f reflectiveColor = white.multiply(0.25f);
			ReflectiveMaterial reflective = new ReflectiveMaterial(diffuseColor, glossyColor, reflectiveColor, 100.0);
			position = new Vector3f(38.0f, 23.0f, -25.0f);
			float radius = 23.0f;
			Sphere sphere = new Sphere(position, radius);
			scene.add(new Primitive(reflective, sphere));

			// sphere
			Vector3f orange = new Vector3f(0.75f, 0.25f, 0.0f);
			MatteMaterial matte = new MatteMaterial(orange.multiply(0.75f));
			position = new Vector3f(-7, 10, 42);
			radius = 20.0f;
			sphere = new Sphere(position, radius);
			scene.add(new Primitive(matte, sphere));

			// sphere
			Vector3f black = new Vector3f(0);
			reflective = new ReflectiveMaterial(black, black, white.multiply(0.75f), 0.0);
			position = new Vector3f(-30.0f, 59.0f, 35.0f);
			radius = 20.0f;
			sphere = new Sphere(position, radius);
			scene.add(new Primitive(reflective, sphere));

			// cylinder
			Vector3f cyan = new Vector3f(0.0f, 0.5f, 0.75f);
			reflective = new ReflectiveMaterial(cyan.multiply(0.5f), cyan.multiply(0.2f), white.multiply(0.25f), 100.0);

			position = new Vector3f(0);
			float top = 85;
			float cylinderRadius = 22;
			Cylinder cylinder = new Cylinder(position, cylinderRadius, top);
			scene.add(new Primitive(reflective, cylinder));

			// box
			Vector3f light_green = new Vector3f(0.75f, 1.0f, 0.75f);
			matte = new MatteMaterial(light_green.multiply(0.5f));
			Vector3f corner0 = new Vector3f(-35.0f, 0.0f, -110.0f);
			Vector3f corner1 = new Vector3f(-25.0f, 60.0f, 65.0f);
			Box box = new Box(corner0, corner1);
			scene.add(new Primitive(matte, box));

			// plane
			Vector3f gray = new Vector3f(0.5f);
			matte = new MatteMaterial(gray);
			Vector3f point = new Vector3f(0);
			Vector3f normal = new Vector3f(0.0f, 1.0f, 0.0f);
			Plane plane = new Plane(point, normal);
			scene.add(new Primitive(matte, plane));

		}

		panel = new RenderArea(600, 400);
		add(panel);
		pack();

		setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});
	}
}
