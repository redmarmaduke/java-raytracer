package raytracer.material;

import raytracer.Vector3f;

/**
 * Phong Material
 * 
 * @author Manuel Nunes
 *
 */
public class PhongMaterial extends Material {
	/**
	 * Default Constructor
	 */
	public PhongMaterial() {
		this(new Vector3f(0.5f), new Vector3f(0.5f), 1.0);
	}

	/**
	 * Constructor
	 * @param diffuseColor Sets the percentage [0,1] of the spectral components (r,g,b)
	 *   to reflect diffusely.
	 * @param glossyColor Sets the percentage [0,1] of the spectral components (r,g,b)
	 *   for a glossy reflection.
	 * @param e Specular exponent for the glossy brdf.
	 */
	public PhongMaterial(Vector3f diffuseColor, Vector3f glossyColor, double e) {
		add(new LambertianBRDF(diffuseColor));
		add(new GlossySpecularBRDF(glossyColor, e));
	}
}
