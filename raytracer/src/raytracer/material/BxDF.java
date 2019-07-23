package raytracer.material;

import raytracer.Mutable;
import raytracer.Vector3d;
import raytracer.Vector3f;

public abstract class BxDF {
	public final static int REFLECTIVE = 1 << 0; /** < reflective type */
	public final static int TRANSMISSIVE = 1 << 1; /** < transmissive type */
	public final static int DIFFUSE = 1 << 2; /** < diffuse type */
	public final static int GLOSSY = 1 << 3; /** < glossy type */
	public final static int SPECULAR = 1 << 4; /** < specular type */
	public final static int ALL_FLAGS = REFLECTIVE | TRANSMISSIVE | DIFFUSE | GLOSSY | SPECULAR; /** < transmissive type */
	
	private int type; /** < composite type */

	/**
	 * Constructor
	 * 
	 * @param type composite type
	 */
	BxDF(int type) {
		if(!isValidType(type)) {
			throw new IllegalArgumentException("Invalid type.");
		}
		this.type = type;
	}
	
	/**
	 * Check if this BxDF contains the types specified.
	 * @param type composite type
	 * @return boolean Does this contain the types specified?
	 */
	public boolean matchesType(int type) {
		return ((this.type & type) == type);
	}

	/**
	 * Check if the type is a valid type.
	 * 
	 * @param type
	 * @return boolean Is this a valid BxDF type?
	 */
	public boolean isValidType(int type) {
		return ((ALL_FLAGS & type) == type);
	}
	
	/**
	 * Getter
	 * @return composite type
	 */
	public int getType() {
		return (type);
	}

	/**
	 * Setter
	 * @param color sets the percentage [0,1] of the spectral components (r,g,b)
	 *   to reflect/transmit.
	 */
	abstract protected void setColor(Vector3f color);

	/**
	 * Setter
	 * @param color sets the percentage [0,1] of the spectral components (r,g,b)
	 *   to reflect/transmit with a scale.
	 */	
	abstract protected void setColor(Vector3f color, float scale);

	/**
	 * bidirection distribution reflectance function
	 * @param normal surface normal
	 * @param wo outgoing light direction
	 * @param wi incoming light direction
	 * @return percentage [0,1] of the spectral components (r,g,b) to reflect/transmit.
	 */
	abstract public Vector3d f(Vector3d normal, Vector3d wo, Vector3d wi);

	/**
	 * sampling bidirection distribution reflectance function
	 * 
	 * @param normal surface normal
	 * @param wo outgoing light direction
	 * @param mWi mutable returning a sampled incoming light direction
	 * @param mPdf mutable returning the value of the probability density function
	 * @return
	 */
	abstract public Vector3d fSample(Vector3d normal, Vector3d wo, Mutable<Vector3d> mWi, Mutable<Double> mPdf);

	/**
	 * Calculate bi-hemispherical reflectance.
	 *   This is used to model ambient illumination for diffuse surfaces.
	 * 
	 * @param normal surface normal
	 * @param wo outgoing light direction
	 * @return percentage [0,1] of the spectral components (r,g,b) to reflect/transmit.
	 */
	abstract public Vector3d rho(Vector3d normal, Vector3d wo);

	/**
	 * Evaluate the probability density function.
	 * 
	 * @param normal surface normal
	 * @param wo outgoing light direction
	 * @param wi incoming light direction
	 * @return value of the probability density function
	 */	
	abstract double pdf(Vector3d normal, Vector3d wo, Vector3d wi);
}
