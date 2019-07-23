package raytracer.material;

import raytracer.Mutable;
import raytracer.Vector3d;
import raytracer.Vector3f;

public class PerfectSpecularBRDF extends BxDF {
	private Vector3f color;

	/**
	 * Default constructor
	 */
	public PerfectSpecularBRDF() {
		this(new Vector3f(0.5f));
	}

	/**
	 * Constructor
	 * @param color Per component percent of light reflected. 
	 */
	public PerfectSpecularBRDF(Vector3f color) {
		super(BxDF.REFLECTIVE | BxDF.SPECULAR);
		this.color = color;
	}

	/**
	 * @see BxDF#setColor(Vector3f)
	 */
	protected void setColor(Vector3f color) {
		this.color = color;
	}

	/**
	 * @see BxDF#setColor(Vector3f, float)
	 */	
	protected void setColor(Vector3f color, float scale) {
		this.color = color.multiply(scale);
	}

	/**
	 * @see BxDF#f(Vector3d, Vector3d, Vector3d)
	 */		
	@Override
	public Vector3d f(Vector3d normal, Vector3d wo, Vector3d wi) {
		return new Vector3d(0);
	}

	/**
	 * @see BxDF#fSample(Vector3d, Vector3d, Mutable, Mutable)
	 */		
	@Override
	public Vector3d fSample(Vector3d normal, Vector3d wo, Mutable<Vector3d> mWi, Mutable<Double> mPdf) {
		double nDotWo = normal.dot(wo);
		Vector3d wi = wo.negated().add(normal.multiply(nDotWo * 2.0));

		mWi.set(wi);
		mPdf.set(1.0);

		Vector3d color = new Vector3d(this.color);
		return color.multiply(normal.dot(wi));
	}

	/**
	 * @see BxDF#rho(Vector3d, Vector3d)
	 */		
	@Override
	public Vector3d rho(Vector3d normal, Vector3d wo) {
		return new Vector3d(0);
	}

	/**
	 * @see BxDF#pdf(Vector3d, Vector3d, Vector3d)
	 */		
	@Override
	double pdf(Vector3d normal, Vector3d wo, Vector3d wi) {
		return 1.0;
	}
}
