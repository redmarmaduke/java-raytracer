package raytracer.geometry;

import raytracer.Matrix;
import raytracer.Mutable;
import raytracer.Ray;
import raytracer.Vector3d;
import raytracer.Vector3f;

public class Plane implements GeometricObject {
	private Vector3d point; /** < Point on plane */
	private Vector3d normal; /** < Normal of plane */
	public double kEpsilon = 0.000000001; /** < maximum floating point error for equality */
	Matrix m;

	/**
	 * Default constructor
	 */
	public Plane() {
		this(new Vector3f(0), new Vector3f(0.0f, 1.0f, 0.0f));
	}

	
	/**
	 * Constructor
	 * @param point Point on plane.
	 * @param normal Normal of plane.
	 */
	public Plane(Vector3f point, Vector3f normal) {
		this.point = new Vector3d(point);
		this.normal = new Vector3d(normal);

		Vector3d up = new Vector3d(0.0, 1.0, 0.0);

		if (!this.normal.equals(up)) {
			m.R(this.normal, up);
		}
	}

	/**
	 * @see GeometricObject#hit(Ray, Mutable)
	 */
	@Override
	public boolean hit(Ray ray, Mutable<Double> mTmin) {
		double t = (point.subtract(ray.getOrigin())).dot(normal) / ray.getDirection().dot(normal);
		if (t > kEpsilon) {
			mTmin.set(t);
			return (true);
		}
		return (false);
	}

	/**
	 * @see GeometricObject#hit(Ray, Mutable, Mutable)
	 */	
	@Override
	public boolean hit(Ray ray, Mutable<Double> mTmin, Mutable<Vector3d> mNormal) {
		double t = (point.subtract(ray.getOrigin())).dot(normal) / ray.getDirection().dot(normal);
		if (t > kEpsilon) {
			mTmin.set(t);
			mNormal.set(normal);
			return (true);
		}
		return (false);
	}

}
