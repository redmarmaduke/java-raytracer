package raytracer.material;

import raytracer.Mutable;
import raytracer.Vector3d;
import raytracer.Vector3f;

public class Material {
	private int numBxdf;
	final private int NUM_MAX_BXDF = 4;
	private BxDF bxdf[] = new BxDF[NUM_MAX_BXDF];

	/**
	 * Utility function for the number of matches found when querying a type.
	 * @param type
	 * @return number of matching BxDFs
	 */
	int numMatchesType(int type) {
		int num = 0;
		for (int idx = 0; idx < numBxdf; ++idx) {
			if (bxdf[idx].matchesType(type)) {
				++num;
			}
		}
		return (num);
	}

	/**
	 * Composite bidirectional reflectance distribution function.
	 * 
	 * @see BxDF#f(Vector3d, Vector3d, Vector3d)
	 */	
	public Vector3d f(Vector3d normal, Vector3d wo, Vector3d wi, int type) {
		Vector3d f = new Vector3d();
		boolean is_reflected = wi.dot(normal) * wo.dot(normal) > 0;
		for (int idx = 0; idx < numBxdf; ++idx) {
			if (bxdf[idx].matchesType(type) && ((is_reflected && (bxdf[idx].getType() & BxDF.REFLECTIVE) != 0)
					|| (!is_reflected && (bxdf[idx].getType() & BxDF.TRANSMISSIVE) != 0))) {
				f = f.add(bxdf[idx].f(normal, wi, wo));
			}
		}
		return (f);
	}

	/**
	 * Composite sampling bidirectional reflectance distribution function.
	 * 
	 * @see BxDF#fSample(Vector3d, Vector3d, Mutable, Mutable)
	 */		
	public Vector3d fSample(Vector3d normal, Vector3d wo, Vector3f sample, int type, Mutable<Vector3d> wi,
			Mutable<Double> pdf) {
		Vector3d f;

		int numMatches = numMatchesType(type);
		if (numMatches == 0) {
			pdf.set(Double.valueOf(0));
			return new Vector3d(0.0);
		}

		int matchesIdx = 0;
		BxDF matchedBxdf = null;
		int rndIdx = (int) (numMatches * Math.random());
		for (int idx = 0; idx < numBxdf; ++idx) {
			if (bxdf[idx].matchesType(type)) {
				if (matchesIdx == rndIdx) {
					matchedBxdf = bxdf[idx];
					break;
				}
				++matchesIdx;
			}
		}

		f = matchedBxdf.fSample(normal, wo, wi, pdf);

		if (numMatches > 1) {
			if ((matchedBxdf.getType() & BxDF.SPECULAR) != 0) {
				boolean is_reflected = (wi.get()).dot(normal) * wo.dot(normal) > 0;
				f = new Vector3d(0.0);
				for (int idx = 0; idx < numBxdf; ++idx) {
					if (bxdf[idx].matchesType(type)) {
						if (((is_reflected && (bxdf[idx].getType() & BxDF.REFLECTIVE) != 0)
								|| (!is_reflected && (bxdf[idx].getType() & BxDF.TRANSMISSIVE) != 0))) {
							f = f.add(bxdf[idx].f(normal, wi.get(), wo));
						}
						if (bxdf[idx] != matchedBxdf) {
							pdf.set(pdf.get() + bxdf[idx].pdf(normal, wi.get(), wo));
						}
					}
				}
			}
			pdf.set(pdf.get() / numMatches);
		}

		return (f);
	}

	/**
	 * Composite rho function.
	 * 
	 * @see BxDF#rho(Vector3d, Vector3d)
	 */			
	public Vector3d rho(Vector3d normal, Vector3d wo, int type) {
		Vector3d f = new Vector3d();
		for (int idx = 0; idx < numBxdf; ++idx) {
			if (bxdf[idx].matchesType(type) && (bxdf[idx].getType() & BxDF.REFLECTIVE) != 0) {
				f = f.add(bxdf[idx].rho(normal, wo));
			}
		}
		return (f);
	}

	/**
	 * Add a BxDF to the material.
	 * 
	 * @param bxdf
	 */
	void add(BxDF bxdf) {
		assert (numBxdf < NUM_MAX_BXDF);
		this.bxdf[numBxdf++] = bxdf;
	}
}
