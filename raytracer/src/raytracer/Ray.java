package raytracer;

/**
 * Ray Class
 * 
 * @author Manuel Nunes
 *
 */
public class Ray {
	final private Vector3d origin;
	final private Vector3d direction;

	/**
	 * Copy Constructor
	 * 
	 * @param ray
	 */
	public Ray(Ray ray) {
		origin = ray.origin;
		direction = ray.direction;
	}

	/**
	 * Constructor
	 * 
	 * @param origin
	 * @param direction
	 */
	public Ray(Vector3d origin, Vector3d direction) {
		this.origin = new Vector3d(origin);
		this.direction = new Vector3d(direction.normalize());
	}

	/**
	 * Getter
	 * 
	 * @return direction
	 */
	public Vector3d getDirection() {
		return direction;
	}

	/**
	 * Getter
	 * 
	 * @return origin
	 */
	public Vector3d getOrigin() {
		return origin;
	}

	/**
	 * Calculate a hit point given a value t
	 * 
	 * @param t distance
	 * @return hit point
	 */
	public Vector3d getHitPoint(double t) {
		return origin.add(direction.multiply(t));
	}

	/**
	 * Calculate a hit point component (x,y,z)->(0,1,2) given a value t
	 * 
	 * @param t   distance
	 * @param idx vector component index where (0,1,2) -> (x,y,z)
	 * @return hit point
	 */
	public double getHitPoint(double t, int idx) {
		return origin.get(idx) + (direction.get(idx) * t);
	}
}
