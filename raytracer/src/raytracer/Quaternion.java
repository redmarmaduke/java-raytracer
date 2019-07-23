package raytracer;

/**
 * Quaternion class
 * 
 * @author Manuel Nunes
 *
 */
public class Quaternion {
	protected Vector3d q;
	protected double qw;

	/**
	 * Default constructor.
	 */
	public Quaternion() {
		q = new Vector3d();
		qw = 1.0;
	}

	/**
	 * Constructor
	 * 
	 * @param q
	 * @param qw
	 */
	public Quaternion(Vector3d q, double qw) {
		this.q = q;
		this.qw = qw;
	}

	/**
	 * Constructor 
	 * 
	 * @param qx
	 * @param qy
	 * @param qz
	 * @param qw
	 */
	public Quaternion(double qx, double qy, double qz, double qw) {
		q = new Vector3d(qx, qy, qz);
		this.qw = qw;
	}

	protected double norm() {
		return (q.dot(q)) + qw * qw;
	}
}
