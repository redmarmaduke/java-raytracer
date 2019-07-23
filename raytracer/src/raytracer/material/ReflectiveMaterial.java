package raytracer.material;

import raytracer.Vector3f;

/**
 * Generic material that provides diffuse, specular and perfectly specular reflection.
 * 
 * @author Manuel Nunes
 *
 */
public class ReflectiveMaterial extends Material {
	/**
	 * Default constructor.
	 */
	public ReflectiveMaterial() {
		this(new Vector3f(0.5f), new Vector3f(0.5f), new Vector3f(0.5f), 1.0);
	}
	
	/**
	 * Constructor  
	 * @param diffuseColor Sets the percentage [0,1] of the spectral components (r,g,b)
	 *   to reflect diffusely.
	 * @param glossyColor Sets the percentage [0,1] of the spectral components (r,g,b)
	 *   for a glossy/specular reflection.
	 * @param reflectiveColor Sets the percentage [0,1] of the spectral components (r,g,b)
	 *   for perfect specular reflection.
	 * @param e Specular exponent for the glossy BRDF.
	 */
	public ReflectiveMaterial(Vector3f diffuseColor, Vector3f glossyColor, Vector3f reflectiveColor, double e) {
		add(new LambertianBRDF(diffuseColor));
		add(new GlossySpecularBRDF(glossyColor, e));
		add(new PerfectSpecularBRDF(reflectiveColor));
	}
}
