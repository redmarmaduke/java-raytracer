package raytracer.geometry;

import java.lang.Math;

import raytracer.Mutable;
import raytracer.Ray;
import raytracer.Vector3d;
import raytracer.Vector3f;

public class Sphere implements GeometricObject {
	private Vector3d center; /** < position of sphere */
	private double radius; /** < radius of sphere */
	public final double kEpsilon = 0.00000001f; /** < maximum permissible error for comparisons */

	/**
	 * Default constructor
	 */
	public Sphere() {
		center = new Vector3d(0.0d);
		radius = 1.0;
	}

	/**
	 * Constructor
	 * @param center Sphere position.
	 * @param radius Sphere radius.
	 */
	public Sphere(Vector3f center, float radius) {
		this.center = new Vector3d(center);
		this.radius = (double) radius;
	}

	/**
	 * @see GeometricObject#hit(Ray, Mutable)
	 */
	@Override
	public boolean hit(Ray ray, Mutable<Double> tmin) {
		double t = 0.0;

		Vector3d temp = ray.getOrigin().subtract(center);
		double a = ray.getDirection().dot(ray.getDirection());
		double b = temp.multiply(2.0).dot(ray.getDirection());
		double c = temp.dot(temp) - radius * radius;
		double disc = (b * b - 4.0 * a * c);

		if (disc < 0.0f) {
			return (false);
		} else {
			double e = Math.pow(disc, 0.5);
			double denom = 2.0 * a;

			t = (-b - e) / denom;
			if (t > kEpsilon) {
				tmin.set(t);
				return (true);
			}

			t = (-b + e) / denom;
			if (t > kEpsilon) {
				tmin.set(t);
				return (true);
			}
		}
		return (false);
	}

	/**
	 * @see GeometricObject#hit(Ray, Mutable, Mutable)
	 */
	@Override
	public boolean hit(Ray ray, Mutable<Double> tmin, Mutable<Vector3d> normal) {
		double t = 0.0f;
		Vector3d temp = ray.getOrigin().subtract(center);
		double a = ray.getDirection().dot(ray.getDirection());
		double b = temp.multiply(2.0f).dot(ray.getDirection());
		double c = temp.dot(temp) - radius * radius;
		double disc = (b * b - 4.0f * a * c);

		if (disc < 0.0) {
			return (false);
		} else {
			double e = Math.pow(disc, 0.5);
			double denom = 2.0f * a;

			t = (-b - e) / denom;
			if (t > kEpsilon) {
				tmin.set(t);
				normal.set(temp.add(ray.getDirection().multiply(t)).divide(radius));
				return (true);
			}

			t = (-b + e) / denom;
			if (t > kEpsilon) {
				tmin.set(t);
				normal.set(temp.add(ray.getDirection().multiply(t)).divide(radius));
				return (true);
			}
		}
		return (false);
	}
}
