package raytracer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import raytracer.geometry.Sphere;

class SphereTest {

	@Test
	void test() {
		Sphere sphere = new Sphere();
		Ray r = new Ray(new Vector3d(0.0, 0.0, -101.0), new Vector3d(0, 0, 1));
		Mutable<Double> tmin = new Mutable<Double>(new Double(0.0));

		boolean is_hit = sphere.hit(r, tmin);
		assertTrue(is_hit);
		assertEquals(100.0d, tmin.get(), sphere.kEpsilon);

		Mutable<Vector3d> normal = new Mutable<Vector3d>(null);
		is_hit = sphere.hit(r, tmin, normal);
		assertTrue(is_hit);
		assertEquals(100.0d, tmin.get(), sphere.kEpsilon);
		if (is_hit) {
			assertEquals(0.0d, normal.get().get(0), sphere.kEpsilon);
			assertEquals(0.0d, normal.get().get(1), sphere.kEpsilon);
			assertEquals(-1.0d, normal.get().get(2), sphere.kEpsilon);
		}
		assertTrue(is_hit);
	}
}
