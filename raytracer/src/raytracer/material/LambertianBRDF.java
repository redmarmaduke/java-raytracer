package raytracer.material;

import raytracer.Mutable;
import raytracer.Vector3d;
import raytracer.Vector3f;

/**
 * Lambertian BRDF - Perfectly matte BRDF
 * 
 * @author Manuel Nunes
 *
 */
public class LambertianBRDF extends BxDF {
	private Vector3f color;

	/**
	 * Default constructor
	 */
	public LambertianBRDF() {
		this(new Vector3f(0));
	}

	/**
	 * Constructor
	 * @param color Per component percent of light reflected. 
	 */
	public LambertianBRDF(Vector3f color) {
		super(BxDF.REFLECTIVE | BxDF.DIFFUSE);
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
		Vector3d c = new Vector3d(color);
		
		return c.multiply(1.0/Math.PI);
	}

	/**
	 * @see BxDF#fSample(Vector3d, Vector3d, Mutable, Mutable)
	 */
	@Override
	public Vector3d fSample(Vector3d normal, Vector3d wo, Mutable<Vector3d> mWi, Mutable<Double> mPdf) {
		Vector3d w = normal;
		
		Mutable<Vector3d> mu = Mutable.valueOf(null);
		Mutable<Vector3d> mv = Mutable.valueOf(null);
		w.orthonormalBasis(mu, mv);
		Vector3d u = mu.get();
		Vector3d v = mv.get();

		// hemisphere sample
		double e = 0.0;
		double sample_x = Math.random();
		double sample_y = Math.random();
		double phi = 2.0 * Math.PI * sample_y;
		double cos_phi = Math.cos(phi);
		double sin_phi = Math.sin(phi);
		double cos_theta = Math.pow(sample_x, 1.0 / (e + 1.0f));
		double sin_theta = Math.sqrt(1.0 - cos_theta * cos_theta);
		Vector3d sample = new Vector3d(sin_theta * cos_phi,
			sin_theta * sin_phi,
			cos_theta); // z is up
		
		Vector3d wi = u.multiply(sample.get(0));
		wi = wi.add(v.multiply(sample.get(1)));
		wi = wi.add(w.multiply(sample.get(2)));
		wi = wi.normalize();
		
		//wi = (u * sample.x) + (v * sample.y) + (w * sample.z);
		double pdf = wi.dot(normal) / Math.PI;

		mWi.set(wi);
		mPdf.set(pdf);
		
		Vector3d c = new Vector3d(color);
		c = c.multiply(1.0/Math.PI);
		
		return (c);
	}

	/**
	 * @see BxDF#rho(Vector3d, Vector3d)
	 */
	@Override
	public Vector3d rho(Vector3d normal, Vector3d wo) {
		Vector3d c = new Vector3d(color);
		
		return (c);
	}

	/**
	 * @see BxDF#pdf(Vector3d, Vector3d, Vector3d)
	 */
	@Override
	double pdf(Vector3d normal, Vector3d wo, Vector3d wi) {
		double pdf = wi.dot(normal) / Math.PI;
		
		return (pdf);
	}

}
