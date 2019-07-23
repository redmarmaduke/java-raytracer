package raytracer.camera;

import raytracer.Ray;
import raytracer.Vector2f;
import raytracer.Vector3d;

/**
 * Class representing the Pin Hole camera.
 * 
 * @author Manuel Nunes
 *
 */
public class PinHole extends Camera {
	private double viewDistance;

	/**
	 * Constructor
	 * 
	 * @param imageSensor
	 */
	public PinHole(ImageSensor imageSensor) {
		super(imageSensor);

		viewDistance = 100.0;
		eye = new Vector3d(0.0, 0.0, 0.0);
		Vector3d up = new Vector3d(0.0, 1.0, 0.0);
		Vector3d lookAt = new Vector3d(0.0, 0.0, 0.0);

		setLookAt(eye, up, lookAt);
	}

	/**
	 * Constructor
	 * @param imageSensor
	 * @param eye position
	 * @param up orientation
	 * @param lookAt facing point
	 * @param viewDistance 
	 */
	public PinHole(ImageSensor imageSensor, Vector3d eye, Vector3d up, Vector3d lookAt, double viewDistance) {
		super(imageSensor);

		this.viewDistance = viewDistance;
		setLookAt(eye, up, lookAt);
	}

	/**
	 * @see Camera#sampleRay(int, int)
	 */
	@Override
	public Ray sampleRay(int x, int y) {
		Vector2f pixelPoint = imageSensor.samplePixelPoint(x, y);

		Vector3d direction = u.multiply(pixelPoint.get(0));
		direction = v.multiply(pixelPoint.get(1)).add(direction);
		direction = (w.multiply(-viewDistance).add(direction)).normalize();

		return new Ray(eye, direction);
	}

	/**
	 * Setter
	 * 
	 * @param viewDistance
	 */
	public void setViewDistance(double viewDistance) {
		this.viewDistance = viewDistance;
	}
}
