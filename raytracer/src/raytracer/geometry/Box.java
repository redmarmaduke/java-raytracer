package raytracer.geometry;

import raytracer.Mutable;
import raytracer.Ray;
import raytracer.Vector3d;
import raytracer.Vector3f;

/**
 * Class representing a geometric axis aligned box.
 * 
 * @author Manuel Nunes
 *
 */
public class Box implements GeometricObject {
	private Vector3f p0, p1;
	public double kEpsilon = 0.000000001;

	/**
	 * Default constructor
	 */
	public Box() {
		p0 = new Vector3f(0, 0, 0);
		p1 = new Vector3f(1, 1, 1);
	}

	/**
	 * Constructor
	 * Sorts the coordinates from p0,p1 so that p0 is the lesser point and p1
	 * is the greater point. 
	 * 
	 * @param[in] p0 First corner of box.
	 * @param[in] p1 Second corner of box.
	 */
	public Box(Vector3f p0, Vector3f p1) {
		float p0_x, p0_y, p0_z;
		float p1_x, p1_y, p1_z;

		if (p0.get(0) > p1.get(0)) {
			p0_x = p1.get(0);
			p1_x = p0.get(0);
		} else {
			p0_x = p0.get(0);
			p1_x = p1.get(0);
		}
		if (p0.get(1) > p1.get(1)) {
			p0_y = p1.get(1);
			p1_y = p0.get(1);
		} else {
			p0_y = p0.get(1);
			p1_y = p1.get(1);
		}
		if (p0.get(2) > p1.get(2)) {
			p0_z = p1.get(2);
			p1_z = p0.get(2);
		} else {
			p0_z = p0.get(2);
			p1_z = p1.get(2);
		}

		this.p0 = new Vector3f(p0_x, p0_y, p0_z);
		this.p1 = new Vector3f(p1_x, p1_y, p1_z);
	}

	/**
	 * @see GeometricObject#hit(Ray, Mutable)
	 */
	@Override
	public boolean hit(Ray ray, Mutable<Double> mTmin) {
		double tm = Double.MAX_VALUE;
		Vector3d p0 = new Vector3d(this.p0);
		Vector3d p1 = new Vector3d(this.p1);

		if (ray.getOrigin().get(0) <= p0.get(0) && ray.getDirection().get(0) >= 0
				|| ray.getOrigin().get(0) < p1.get(0) && ray.getDirection().get(0) <= 0) {
			Vector3d norm = new Vector3d(-1, 0, 0);

			double t = (p0.subtract(ray.getOrigin())).dot(norm) / (ray.getDirection().dot(norm));
			if (t < tm && t > kEpsilon) {
				Vector3d hp = ray.getHitPoint(t);
				if (hp.get(1) >= p0.get(1) && hp.get(1) <= p1.get(1) && hp.get(2) >= p0.get(2)
						&& hp.get(2) <= p1.get(2)) {
					tm = t;
				}
			}
		}
		if (ray.getOrigin().get(0) >= p1.get(0) && ray.getDirection().get(0) <= 0
				|| ray.getOrigin().get(0) > p0.get(0) && ray.getDirection().get(0) >= 0) {
			Vector3d norm = new Vector3d(1, 0, 0);

			double t = (p1.subtract(ray.getOrigin())).dot(norm) / (ray.getDirection().dot(norm));
			if (t < tm && t > kEpsilon) {
				Vector3d hp = ray.getHitPoint(t);
				if (hp.get(1) >= p0.get(1) && hp.get(1) <= p1.get(1) && hp.get(2) >= p0.get(2)
						&& hp.get(2) <= p1.get(2)) {
					tm = t;
				}
			}
		}

		if (ray.getOrigin().get(1) <= p0.get(1) && ray.getDirection().get(1) >= 0
				|| ray.getOrigin().get(1) < p1.get(1) && ray.getDirection().get(1) <= 0) {
			Vector3d norm = new Vector3d(0, -1, 0);

			double t = (p0.subtract(ray.getOrigin())).dot(norm) / (ray.getDirection().dot(norm));
			if (t < tm && t > kEpsilon) {
				Vector3d hp = ray.getHitPoint(t);
				if (hp.get(0) >= p0.get(0) && hp.get(0) <= p1.get(0) && hp.get(2) >= p0.get(2)
						&& hp.get(2) <= p1.get(2)) {
					tm = t;
				}
			}
		}
		if (ray.getOrigin().get(1) >= p1.get(1) && ray.getDirection().get(1) <= 0
				|| ray.getOrigin().get(1) > p0.get(1) && ray.getDirection().get(1) >= 0) {
			Vector3d norm = new Vector3d(0, 1, 0);

			double t = (p1.subtract(ray.getOrigin())).dot(norm) / (ray.getDirection().dot(norm));
			if (t < tm && t > kEpsilon) {
				Vector3d hp = ray.getHitPoint(t);
				if (hp.get(0) >= p0.get(0) && hp.get(0) <= p1.get(0) && hp.get(2) >= p0.get(2)
						&& hp.get(2) <= p1.get(2)) {
					tm = t;
				}
			}
		}

		if (ray.getOrigin().get(2) <= p0.get(2) && ray.getDirection().get(2) >= 0
				|| ray.getOrigin().get(2) < p1.get(2) && ray.getDirection().get(2) <= 0) {
			Vector3d norm = new Vector3d(0, 0, -1);

			double t = (p0.subtract(ray.getOrigin())).dot(norm) / (ray.getDirection().dot(norm));
			if (t < tm && t > kEpsilon) {
				Vector3d hp = ray.getHitPoint(t);
				if (hp.get(0) >= p0.get(0) && hp.get(0) <= p1.get(0) && hp.get(1) >= p0.get(1)
						&& hp.get(1) <= p1.get(1)) {
					tm = t;
				}
			}
		}
		if (ray.getOrigin().get(2) >= p1.get(2) && ray.getDirection().get(2) <= 0
				|| ray.getOrigin().get(2) > p0.get(2) && ray.getDirection().get(2) >= 0) {
			Vector3d norm = new Vector3d(0, 0, 1);

			double t = (p1.subtract(ray.getOrigin())).dot(norm) / (ray.getDirection().dot(norm));
			if (t < tm && t > kEpsilon) {
				Vector3d hp = ray.getHitPoint(t);
				if (hp.get(0) >= p0.get(0) && hp.get(0) <= p1.get(0) && hp.get(1) >= p0.get(1)
						&& hp.get(1) <= p1.get(1)) {
					tm = t;
				}
			}
		}

		if (tm != Double.MAX_VALUE) {
			mTmin.set(tm);
			return (true);
		}
		return (false);
	}

	/**
	 * @see GeometricObject#hit(Ray, Mutable, Mutable)
	 */
	@Override
	public boolean hit(Ray ray, Mutable<Double> mTmin, Mutable<Vector3d> mNormal) {
		double tm = Double.MAX_VALUE;
		Vector3d p0 = new Vector3d(this.p0);
		Vector3d p1 = new Vector3d(this.p1);

		if (ray.getOrigin().get(0) <= p0.get(0) && ray.getDirection().get(0) >= 0
				|| ray.getOrigin().get(0) < p1.get(0) && ray.getDirection().get(0) <= 0) {
			Vector3d norm = new Vector3d(-1, 0, 0);

			double t = (p0.subtract(ray.getOrigin())).dot(norm) / (ray.getDirection().dot(norm));
			if (t < tm && t > kEpsilon) {
				Vector3d hp = ray.getOrigin().add(ray.getDirection().multiply(t));
				if (hp.get(1) >= p0.get(1) && hp.get(1) <= p1.get(1) && hp.get(2) >= p0.get(2)
						&& hp.get(2) <= p1.get(2)) {
					tm = t;
					mNormal.set(norm);
				}
			}
		}
		if (ray.getOrigin().get(0) >= p1.get(0) && ray.getDirection().get(0) <= 0
				|| ray.getOrigin().get(0) > p0.get(0) && ray.getDirection().get(0) >= 0) {
			Vector3d norm = new Vector3d(1, 0, 0);

			double t = (p1.subtract(ray.getOrigin())).dot(norm) / (ray.getDirection().dot(norm));
			if (t < tm && t > kEpsilon) {
				Vector3d hp = ray.getOrigin().add(ray.getDirection().multiply(t));
				if (hp.get(1) >= p0.get(1) && hp.get(1) <= p1.get(1) && hp.get(2) >= p0.get(2)
						&& hp.get(2) <= p1.get(2)) {
					tm = t;
					mNormal.set(norm);
				}
			}
		}

		if (ray.getOrigin().get(1) <= p0.get(1) && ray.getDirection().get(1) >= 0
				|| ray.getOrigin().get(1) < p1.get(1) && ray.getDirection().get(1) <= 0) {
			Vector3d norm = new Vector3d(0, -1, 0);

			double t = (p0.subtract(ray.getOrigin())).dot(norm) / (ray.getDirection().dot(norm));
			if (t < tm && t > kEpsilon) {
				Vector3d hp = ray.getHitPoint(t);
				if (hp.get(0) >= p0.get(0) && hp.get(0) <= p1.get(0) && hp.get(2) >= p0.get(2)
						&& hp.get(2) <= p1.get(2)) {
					tm = t;
					mNormal.set(norm);
				}
			}
		}
		if (ray.getOrigin().get(1) >= p1.get(1) && ray.getDirection().get(1) <= 0
				|| ray.getOrigin().get(1) > p0.get(1) && ray.getDirection().get(1) >= 0) {
			Vector3d norm = new Vector3d(0, 1, 0);

			double t = (p1.subtract(ray.getOrigin())).dot(norm) / (ray.getDirection().dot(norm));
			if (t < tm && t > kEpsilon) {
				Vector3d hp = ray.getHitPoint(t);
				if (hp.get(0) >= p0.get(0) && hp.get(0) <= p1.get(0) && hp.get(2) >= p0.get(2)
						&& hp.get(2) <= p1.get(2)) {
					tm = t;
					mNormal.set(norm);
				}
			}
		}

		if (ray.getOrigin().get(2) <= p0.get(2) && ray.getDirection().get(2) >= 0
				|| ray.getOrigin().get(2) < p1.get(2) && ray.getDirection().get(2) <= 0) {
			Vector3d norm = new Vector3d(0, 0, -1);

			double t = (p0.subtract(ray.getOrigin())).dot(norm) / (ray.getDirection().dot(norm));
			if (t < tm && t > kEpsilon) {
				Vector3d hp = ray.getHitPoint(t);
				if (hp.get(0) >= p0.get(0) && hp.get(0) <= p1.get(0) && hp.get(1) >= p0.get(1)
						&& hp.get(1) <= p1.get(1)) {
					tm = t;
					mNormal.set(norm);
				}
			}
		}
		if (ray.getOrigin().get(2) >= p1.get(2) && ray.getDirection().get(2) <= 0
				|| ray.getOrigin().get(2) > p0.get(2) && ray.getDirection().get(2) >= 0) {
			Vector3d norm = new Vector3d(0, 0, 1);

			double t = (p1.subtract(ray.getOrigin())).dot(norm) / (ray.getDirection().dot(norm));
			if (t < tm && t > kEpsilon) {
				Vector3d hp = ray.getHitPoint(t);
				if (hp.get(0) >= p0.get(0) && hp.get(0) <= p1.get(0) && hp.get(1) >= p0.get(1)
						&& hp.get(1) <= p1.get(1)) {
					tm = t;
					mNormal.set(norm);
				}
			}
		}

		if (tm != Double.MAX_VALUE) {
			mTmin.set(tm);
			return (true);
		}
		return (false);
	}

}
