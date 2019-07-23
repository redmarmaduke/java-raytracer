package raytracer.camera;

import raytracer.Ray;
import raytracer.Vector3d;

public abstract class Camera {
	protected ImageSensor imageSensor; /** < array of pixel elements for image */
	protected Vector3d eye; /** < position of camera */
	protected Vector3d u, v, w; /** < orthonomal basis vectors */

	/**
	 * Constructor
	 * 
	 * @param imageSensor
	 */
	public Camera(ImageSensor imageSensor) {
		this.imageSensor = imageSensor;
		eye = new Vector3d(0.0, 0.0, 0.0);
		u = new Vector3d(-1.0, 0.0, 0.0);
		v = new Vector3d(0.0, 1.0, 0.0);
		w = new Vector3d(0.0, 0.0, -1.0);
	}

	/**
	 * Sample ray for image sensor element at (x,y)
	 * @param x 
	 * @param y
	 * @return Ray sample.
	 */
	abstract public Ray sampleRay(int x, int y);

	/**
	 * Set position and orientation of the camera.
	 * @param eye Position of the camera.
	 * @param up Orientation of the camera.
	 * @param lookAt Point the camera is facing.
	 */
	public void setLookAt(Vector3d eye, Vector3d up, Vector3d lookAt) {
		this.eye = eye;
		w = (eye.subtract(lookAt)).normalize();
		u = up.cross(w).normalize();
		v = w.cross(u);
	}

	/**
	 * Getter
	 * 
	 * @return imageSensor
	 */
	public ImageSensor getImageSensor() {
		return imageSensor;
	}
}
