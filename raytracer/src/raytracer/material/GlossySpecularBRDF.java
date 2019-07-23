package raytracer.material;

import raytracer.Mutable;
import raytracer.Vector3d;
import raytracer.Vector3f;

/**
 * Glossy Specular BRDF
 * 
 * @author Manuel Nunes
 *
 */
public class GlossySpecularBRDF extends BxDF {
	private Vector3f color;
	double e;
	
	/**
	 * Default constructor
	 */
	public GlossySpecularBRDF() {
		this(new Vector3f(0), 1.0);
	}

	/**
	 * Constructor
	 * 
	 * @param color Per component percent of light reflected. 
	 * @param e specular exponent
	 */
	public GlossySpecularBRDF(Vector3f color, double e) {
		super(BxDF.REFLECTIVE | BxDF.GLOSSY);
		this.color = color;
		this.e = e;
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
	 * Setter
	 * @param e specular exponent
	 */
	void setE(double e) {
		this.e = e;
	}
	
	/**
	 * @see BxDF#f(Vector3d, Vector3d, Vector3d)
	 */
	@Override
	public Vector3d f(Vector3d normal, Vector3d wo, Vector3d wi) {
		Vector3d L;
		
		// Calculate reflection of wi
		double nDotWi = normal.dot(wi); // was wi.dot(normal)
		Vector3d reflectionOfWi = wi.negated().add(normal.multiply(nDotWi*2.0));

		double rDotWo = reflectionOfWi.dot(wo);
		if (rDotWo > 0.0) {
			Vector3d color = new Vector3d(this.color);
			L = color.multiply(Math.pow(rDotWo, e));
		}
		else {
			L = new Vector3d(0);
		}
		return L;
	}

	/**
	 * @see BxDF#fSample(Vector3d, Vector3d, Mutable, Mutable)
	 */
	@Override
	public Vector3d fSample(Vector3d normal, Vector3d wo, Mutable<Vector3d> mWi, Mutable<Double> mPdf) {
		double nDotWo = normal.dot(wo);

		Vector3d reflectionOfWo = wo.negated().add(normal.multiply(nDotWo*2.0));
		Vector3d w = reflectionOfWo;

		Mutable<Vector3d> mu = Mutable.valueOf(null);
		Mutable<Vector3d> mv = Mutable.valueOf(null);
		w.orthonormalBasis(mu, mv);
		Vector3d u = mu.get();
		Vector3d v = mv.get();

		// hemisphere sample
		double e = 0.0;
		double sampleX = Math.random();
		double sampleY = Math.random();
		double phi = 2.0 * Math.PI * sampleY;
		double cosPhi = Math.cos(phi);
		double sinPhi = Math.sin(phi);
		double cosTheta = Math.pow(sampleX, 1.0 / (e + 1.0f));
		double sinTheta = Math.sqrt(1.0 - cosTheta * cosTheta);
		Vector3d sample = new Vector3d(sinTheta * cosPhi,
			sinTheta * sinPhi,
			cosTheta); // z is up

		Vector3d wi = u.multiply(sample.get(0));
		wi = wi.add(v.multiply(sample.get(1)));
		wi = wi.add(w.multiply(sample.get(2)));

		double nDotWi = normal.dot(wi);
		if (nDotWi < 0.0) {
			wi = u.multiply(-sample.get(0));
			wi = wi.add(v.multiply(-sample.get(1)));
			wi = wi.add(w.multiply(sample.get(2)));
			nDotWi = normal.dot(wi);
		}

		double phongLobe = Math.pow(reflectionOfWo.dot(wi), Math.E);
		mPdf.set(phongLobe * nDotWi);
		
		Vector3d color = new Vector3d(this.color);
		return color.multiply(phongLobe);
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
		double nDotWo = normal.dot(wo);
		Vector3d reflectionOfWo = wo.negated().add(normal.multiply(nDotWo*2.0));
		
		double phongLobe = Math.pow(reflectionOfWo.dot(wi), Math.E);
		double nDotWi = normal.dot(wi);
		
		return phongLobe * nDotWi;
	}
}
