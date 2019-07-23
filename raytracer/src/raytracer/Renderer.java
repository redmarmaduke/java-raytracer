package raytracer;

import raytracer.camera.Camera;
import raytracer.material.BxDF;
import raytracer.material.Material;

/**
 * Renderer Class
 * @author Manuel Nunes
 *
 */
public class Renderer {
	private Camera camera;

	/**
	 * Constructor
	 * @param camera
	 */
	Renderer(Camera camera) {
		this.camera = camera;
	}

	/**
	 * Renders the scene
	 * @param scene 
	 */
	void render(Scene scene) {
		int numSamples = 16;
		int width = camera.getImageSensor().getWidth();
		int height = camera.getImageSensor().getHeight();
		Vector3d La = scene.getAmbientLight();

		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				Vector3d Lo = new Vector3d(0.0f, 0.0f, 0.0f);
				for (int sample = 0; sample < numSamples; sample++) {
					Mutable<Double> mTmin = Mutable.valueOf(null);
					Mutable<Vector3d> mNormal = Mutable.valueOf(null);
					Mutable<Material> mMaterial = Mutable.valueOf(null);

					Ray ray = camera.sampleRay(x, y);
					if (scene.hit(ray, mTmin, mNormal, mMaterial)) {
						Vector3d wo = ray.getDirection().negated();
						Vector3d rho = mMaterial.get().rho(mNormal.get(), wo, BxDF.REFLECTIVE);
						Lo = Lo.add(La.multiply(rho));

						Vector3d hitPoint = ray.getHitPoint(mTmin.get());
						int numLights = scene.getNumLights();
						for (int i = 0; i < numLights; i++) {
							Vector3d surfaceToLightVec = (scene.getLight(i).getPosition()).subtract(hitPoint);

							Vector3d wi = surfaceToLightVec.normalize(); // normalized vec
							double nDotWi = mNormal.get().dot(wi);

							if (nDotWi > 0.0) {
								boolean inShadow = false;

								if (scene.getLight(i).castsShadows()) {
									Ray shadowRay = new Ray(hitPoint, wi);
									double d = surfaceToLightVec.magnitude();
									Mutable<Double> mTmin_ = Mutable.valueOf(Double.valueOf(Double.MAX_VALUE));
									if (scene.hit(shadowRay, mTmin_) && mTmin_.get() < d) {
										inShadow = true;
									}
								}

								if (!inShadow) {
									Vector3d Li = scene.getLight(i).Le();
									Lo = Lo.add(mMaterial.get().f(mNormal.get(), wo, wi, BxDF.REFLECTIVE)
											.multiply(Li).multiply(nDotWi));
								}
							}
						}
					} else {
						Lo.add(scene.getBackgroundColor());
					}
				}

				Vector3f pixel = new Vector3f(Lo.divide((double) numSamples));
				camera.getImageSensor().setPixel(x, y, pixel);
			}
		}
	}
}
