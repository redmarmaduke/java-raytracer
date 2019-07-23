package raytracer;

import raytracer.geometry.GeometricObject;
import raytracer.material.Material;

/**
 * Class representing the association of a geometric object and it's material.
 * 
 * @author Manuel Nunes
 *
 */
public class Primitive {
	private GeometricObject geometricObject;
	private Material material;

	/**
	 * Constructor
	 * 
	 * @param material
	 * @param obj
	 */
	public Primitive(Material material, GeometricObject geometricObject) {
		this.geometricObject = geometricObject;
		this.material = material;
	}

	/**
	 * Getter
	 * 
	 * @return geometric object
	 */
	GeometricObject getGeometricObject() {
		return (geometricObject);
	}

	/**
	 * Getter
	 * 
	 * @return material
	 */
	Material getMaterial() {
		return (material);
	}
}
