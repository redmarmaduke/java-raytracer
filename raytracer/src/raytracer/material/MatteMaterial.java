package raytracer.material;

import raytracer.Vector3f;

/**
 * Matte Material
 * @author Manuel Nunes
 *
 */
public class MatteMaterial extends Material {
	/**
	 * Default constructor
	 */
	public MatteMaterial() {
		this(new Vector3f(0));
	}

	/**
	 * Constructor
	 * @param color
	 */
	public MatteMaterial(Vector3f color) {
		add(new LambertianBRDF(color));
	}
}
