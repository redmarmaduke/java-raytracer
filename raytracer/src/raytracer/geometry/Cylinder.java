package raytracer.geometry;

import raytracer.Mutable;
import raytracer.Ray;
import raytracer.Vector3d;
import raytracer.Vector3f;

public class Cylinder implements GeometricObject {
	public double kEpsilon = 0.000000001; /** < maximum floating point error for equality */
	protected float radius; 
	protected float height;
	protected Vector3f center; /** < position of the center of the base */

	/**
	 * Default constructor
	 */
	public Cylinder() {
		center = new Vector3f(0, 0, 0);
		radius = 1.0f;
		height = 1.0f;
	}

	/**
	 * Constructor
	 * 
	 * @param center position of the center of the base
	 * @param radius
	 * @param height
	 */
	public Cylinder(Vector3f center, float radius, float height) {
		this.center = center;
		this.radius = radius;
		this.height = height;
	}

	@Override
	public boolean hit(Ray ray, Mutable<Double> mTmin) {
		double a = ray.getDirection().get(0) * ray.getDirection().get(0)
				+ ray.getDirection().get(2) * ray.getDirection().get(2);
		double b = 2 * (ray.getOrigin().get(0) * ray.getDirection().get(0)
				+ ray.getOrigin().get(2) * ray.getDirection().get(2));
		double c = ray.getOrigin().get(0) * ray.getOrigin().get(0) + ray.getOrigin().get(2) * ray.getOrigin().get(2)
				- radius * radius;
		double disc = b * b - 4.0 * a * c;

		if (disc < 0) {
			return (false);
		} else {
			double e = Math.pow(disc, 0.5);
			double denom = 2.0 * a;

			Vector3d center = new Vector3d(this.center);
			double radius = this.radius;
			double height = this.height;
			;
			// Lesser t for infinite cylinder, check intersection and if within height
			double t = (-b - e) / denom;
			if (t > kEpsilon) {
				double y = ray.getOrigin().get(1) + (ray.getDirection().get(1) * t);

				if (y >= (center.get(1) - kEpsilon) && y <= (center.get(1) + height + kEpsilon)) {
					mTmin.set(t);
					return (true);
				}
			}

			// Greater t for infinite cylinder, check intersection and if within height
			t = (-b + e) / denom;
			if (t > kEpsilon) {
				double y = ray.getOrigin().get(1) + (ray.getDirection().get(1) * t);
				if (y >= (center.get(1) - kEpsilon) && y <= (center.get(1) + height + kEpsilon)) {
					mTmin.set(t);
					return (true);
				}
			}

			// Plane intersection test for plane at base of cylinder w/ check if within
			// radius
			Vector3d normal = new Vector3d(0.0, -1.0, 0.0);
			t = (center.subtract(ray.getOrigin())).dot(normal) / ray.getDirection().dot(normal);
			if (t > kEpsilon) {
				Vector3d hit_point = ray.getOrigin().add(ray.getDirection().multiply(t));
				if (hit_point.get(0) * hit_point.get(0) + hit_point.get(2) * hit_point.get(2) < radius * radius) {
					mTmin.set(t);
					return (true);
				}
			}

			// Plane intersection test for plane at top of cylinder w/ check if within
			// radius
			normal = new Vector3d(0.0, 1.0, 0.0);
			Vector3d top_center = new Vector3d(center.get(0), center.get(1) + height, center.get(2));
			t = (top_center.subtract(ray.getOrigin())).dot(normal) / ray.getDirection().dot(normal);
			if (t > kEpsilon) {
				Vector3d hit_point = ray.getOrigin().add(ray.getDirection().multiply(t));
				if (hit_point.get(0) * hit_point.get(0) + hit_point.get(2) * hit_point.get(2) < radius * radius) {
					mTmin.set(t);
					return (true);
				}
			}
		}
		return (false);
	}

	@Override
	public boolean hit(Ray ray, Mutable<Double> mTmin, Mutable<Vector3d> mNormal) {
		double a = ray.getDirection().get(0) * ray.getDirection().get(0)
				+ ray.getDirection().get(2) * ray.getDirection().get(2);
		double b = 2 * (ray.getOrigin().get(0) * ray.getDirection().get(0)
				+ ray.getOrigin().get(2) * ray.getDirection().get(2));
		double c = ray.getOrigin().get(0) * ray.getOrigin().get(0) + ray.getOrigin().get(2) * ray.getOrigin().get(2)
				- radius * radius;
		double disc = b * b - 4.0 * a * c;

		if (disc < 0) {
			return (false);
		} else {
			double e = Math.pow(disc, 0.5);
			double denom = 2.0 * a;

			Vector3d center = new Vector3d(this.center);
			double radius = this.radius;
			double height = this.height;
			;
			// Lesser t for infinite cylinder, check intersection and if within height
			double t = (-b - e) / denom;
			if (t > kEpsilon) {
				double y = ray.getHitPoint(t, 1);

				if (y >= (center.get(1) - kEpsilon) && y <= (center.get(1) + height + kEpsilon)) {
					mTmin.set(t);
					Vector3d hit_point = ray.getHitPoint(t);
					Vector3d normal = new Vector3d(hit_point.get(0) / radius, 0, hit_point.get(2) / radius);
					mNormal.set(normal);
					return (true);
				}
			}

			// Greater t for infinite cylinder, check intersection and if within height
			t = (-b + e) / denom;
			if (t > kEpsilon) {
				double y = ray.getHitPoint(t, 1);
				if (y >= (center.get(1) - kEpsilon) && y <= (center.get(1) + height + kEpsilon)) {
					mTmin.set(t);
					Vector3d hit_point = ray.getHitPoint(t);
					Vector3d normal = new Vector3d(hit_point.get(0) / radius, 0, hit_point.get(2) / radius);
					mNormal.set(normal);
					return (true);
				}
			}

			// Plane intersection test for plane at base of cylinder w/ check if within
			// radius
			Vector3d normal = new Vector3d(0.0, -1.0, 0.0);
			t = (center.subtract(ray.getOrigin())).dot(normal) / ray.getDirection().dot(normal);
			if (t > kEpsilon) {
				Vector3d hit_point = ray.getHitPoint(t);
				if (hit_point.get(0) * hit_point.get(0) + hit_point.get(2) * hit_point.get(2) < radius * radius) {
					mTmin.set(t);
					mNormal.set(normal);
					return (true);
				}
			}

			// Plane intersection test for plane at top of cylinder w/ check if within
			// radius
			normal = new Vector3d(0.0, 1.0, 0.0);
			Vector3d top_center = new Vector3d(center.get(0), center.get(1) + height, center.get(2));
			t = (top_center.subtract(ray.getOrigin())).dot(normal) / ray.getDirection().dot(normal);
			if (t > kEpsilon) {
				Vector3d hit_point = ray.getHitPoint(t);
				if (hit_point.get(0) * hit_point.get(0) + hit_point.get(2) * hit_point.get(2) < radius * radius) {
					mTmin.set(t);
					mNormal.set(normal);
					return (true);
				}
			}
		}
		return (false);
	}
}
