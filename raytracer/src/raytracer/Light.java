package raytracer;

/**
 * Point Light
 * 
 * @author Manuel Nunes
 *
 */

public class Light {
	private boolean castsShadows;
	/** < true if shadow processing is enabled otherwise false */
	private Vector3d color;
	/** < radiance of the light */
	private Vector3d position;
	/** < position of the light */

	/**
	 * Default Constructor
	 */
	public Light() {
		this(new Vector3f(0, 1000, 0), new Vector3f(1), false);
	}

	/**
	 * Constructor taking position, color and castsShadows boolean
	 * 
	 * @param position     Position of the light.
	 * @param color        Radiance of the light.
	 * @param castsShadows Does the light cast shadows?
	 */
	public Light(Vector3f position, Vector3f color, boolean castsShadows) {
		this.position = new Vector3d(position);
		this.color = new Vector3d(color);
		this.castsShadows = castsShadows;
	}

	/**
	 * Get function.
	 * 
	 * @return Does the light cast shadows?
	 */
	public boolean castsShadows() {
		return castsShadows;
	}

	/**
	 * Get function.
	 * 
	 * @return Position of the light.
	 */
	public Vector3d getPosition() {
		return position;
	}

	/**
	 * Get function.
	 * 
	 * @return Radiance of the light.
	 */
	public Vector3d Le() {
		return color;
	}

	/**
	 * Calculates a normalized direction vector from the light position to a point.
	 * 
	 * @param point
	 * @return normalized direction vector
	 */
	public Vector3d getDirectionTo(Vector3f point) {
		Vector3d position = new Vector3d(this.position);
		Vector3d location = new Vector3d(point);

		return location.subtract(position).normalize();
	}
}
