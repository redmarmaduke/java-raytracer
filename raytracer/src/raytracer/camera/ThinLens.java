package raytracer.camera;

import raytracer.Ray;
import raytracer.Vector2f;
import raytracer.Vector3d;

public class ThinLens extends Camera {
	private double viewDistance;
	private double focalDistance;
	private float lensRadius;

	/**
	 * Constructor
	 * 
	 * @param imageSensor
	 */
	public ThinLens(ImageSensor imageSensor) {
		super(imageSensor);
	}

	/**
	 * Constructor
	 * 
	 * @param imageSensor
	 * @param viewDistance
	 * @param focalDistance
	 * @param lensRadius
	 */
	public ThinLens(ImageSensor imageSensor, double viewDistance, double focalDistance, float lensRadius) {
		super(imageSensor);
		this.viewDistance = viewDistance;
		this.focalDistance = focalDistance;
		this.lensRadius = lensRadius;
	}

	/**
	 * @see Camera#sampleRay(int, int)
	 */
	@Override
	public Ray sampleRay(int x, int y) {
		Vector2f lensPoint = new Vector2f((float) Math.random(), (float) Math.random());

		lensPoint = lensPoint.multiply(lensRadius);
		Vector3d origin = eye;
		origin = origin.add(u.multiply(lensPoint.get(0)));
		origin = origin.add(v.multiply(lensPoint.get(1)));

		Vector2f pixelPoint = imageSensor.samplePixelPoint(x, y);
		pixelPoint = pixelPoint.multiply((float) (focalDistance / viewDistance));

		Vector3d direction = u.multiply(pixelPoint.get(0) - lensPoint.get(0));
		direction = v.multiply(pixelPoint.get(1) - lensPoint.get(1)).add(direction);
		direction = w.multiply(-focalDistance).add(direction);

		Ray ray = new Ray(origin, direction);

		return (ray);
	}

}
