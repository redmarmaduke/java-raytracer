package raytracer.geometry;

import raytracer.Mutable;
import raytracer.Ray;
import raytracer.Vector3d;

public interface GeometricObject {
	/**
	 * hit function
	 * 
	 * @param ray ray to test against for intersection
	 * @param mTmin mutable used to pass back t or distance to object
	 *   of intersection.
	 * 
	 * @return boolean Did the ray hit the object?
	 */	
	boolean hit(Ray ray, Mutable<Double> mTmin);

	/**
	 * hit function
	 * 
	 * @param ray ray to test against for intersection
	 * @param mTmin mutable used to pass back t or distance to object
	 * @param mNormal mutable used to pass back the surface normal at the point
	 *   of intersection.
	 * 
	 * @return boolean Did the ray hit the object?
	 */	
	boolean hit(Ray ray, Mutable<Double> mTmin, Mutable<Vector3d> mNormal);
}
