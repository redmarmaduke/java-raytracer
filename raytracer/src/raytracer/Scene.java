package raytracer;

import raytracer.camera.*;
import raytracer.material.Material;

/**
 * Describes the content of the world to render including:
 * Primitives(models), Lights and Cameras
 * and the interaction between view rays and the world.
 * 
 * @author Manuel Nunes
 *
 */
public class Scene {
	private int nPrimitives;
	private int capacityPrimitives;
	private Primitive primitives[];
	private int nCameras;
	private int capacityCameras;
	private Camera cameras[];
	private int nLights;
	private int capacityLights;
	private Light lights[];

	Vector3d backgroundColor;
	Vector3d Lambient;

	/**
	 * Constructor
	 */
	Scene() {
		capacityPrimitives = 8;
		capacityCameras = 1;
		capacityLights = 1;

		primitives = new Primitive[capacityPrimitives];
		cameras = new Camera[capacityCameras];
		lights = new Light[capacityLights];

		backgroundColor = new Vector3d(0);
		Lambient = new Vector3d(0.5);
	}

	/**
	 * Hit function that returns distance to primitive and primitive information on hit.
	 * Mutable arguments are undefined on a return value of false.
	 * 
	 * @param ray View ray that intersects the scene.
	 * @param mTmin Distance to the closest object intersected (if hit).
	 * @param mNormal Normal of the primitive surface at the point of intersection (if hit).
	 * @param mMaterial Material of the primitive surface at the point of intersection (if hit).
	 * @return Did it hit an object?
	 */
	public boolean hit(Ray ray, Mutable<Double> mTmin, Mutable<Vector3d> mNormal, Mutable<Material> mMaterial) {
		boolean is_hit = false;
		double tmin = Double.MAX_VALUE;

		for (int idx = 0; idx < nPrimitives; ++idx) {
			Mutable<Double> mT = Mutable.valueOf(null);
			Mutable<Vector3d> mN = Mutable.valueOf(null);

			if ((primitives[idx].getGeometricObject().hit(ray, mT, mN)) && mT.get() < tmin) {
				is_hit = true;
				tmin = mT.get();

				mTmin.set(tmin);
				mNormal.set(mN.get());
				mMaterial.set(primitives[idx].getMaterial());
			}
		}
		return (is_hit);
	}

	/**
	 * Simplified hit function that returns distance to primitive on hit.
	 * Mutable arguments are undefined on a return value of false.
	 * 
	 * @param ray View ray that intersects the scene.
	 * @param mTmin Distance to the closest object intersected (if hit). 
	 * @return Did it hit an object?
	 */
	public boolean hit(Ray ray, Mutable<Double> mTmin) {
		boolean is_hit = false;
		double tmin = Double.MAX_VALUE;

		for (int idx = 0; idx < nPrimitives; ++idx) {
			Mutable<Double> mT = Mutable.valueOf(null);

			if ((primitives[idx].getGeometricObject().hit(ray, mT)) && mT.get() < tmin) {
				is_hit = true;
				tmin = mT.get();

				mTmin.set(tmin);
			}
		}
		return (is_hit);
	}

	/**
	 * Getter
	 * 
	 * @return Number of primitives.
	 */
	public int getNumPrimitives() {
		return nPrimitives;
	}

	/**
	 * Getter
	 * @return Number of cameras.
	 */
	public int getNumCameras() {
		return nCameras;
	}

	/**
	 * Getter
	 * 
	 * @return Number of lights.
	 */
	public int getNumLights() {
		return nLights;
	}

	/**
	 * Getter
	 * 
	 * @param idx Primitive index
	 * @return Primitive at index in array.
	 * @throws IllegamArgumentException;
	 */
	public Primitive getPrimitive(int idx) {
		return primitives[idx];
	}

	/**
	 * Getter
	 * 
	 * @param idx Camera index.
	 * @return Camera at index in array.
	 */
	public Camera getCamera(int idx) {
		return cameras[idx];
	}

	/**
	 * Getter
	 * 
	 * @param idx Light index.
	 * @return Light at index in array.
	 */
	public Light getLight(int idx) {
		return lights[idx];
	}

	/**
	 * Adds a primitive to the scene.
	 * 
	 * @param primitive
	 */
	public void add(Primitive primitive) {
		if (nPrimitives < capacityPrimitives) {
			primitives[nPrimitives++] = primitive;
		} else {
			capacityPrimitives *= 2;
			Primitive temp[] = new Primitive[capacityPrimitives];
			for (int idx = 0; idx < nPrimitives; ++idx) {
				temp[idx] = primitives[idx];
			}
			primitives = temp;
		}
	}

	/**
	 * Adds a camera to the scene.
	 * @param camera
	 */
	public void add(Camera camera) {
		if (nCameras < capacityCameras) {
			cameras[nCameras++] = camera;
		} else {
			capacityCameras *= 2;
			Camera temp[] = new Camera[capacityCameras];
			for (int idx = 0; idx < nCameras; ++idx) {
				temp[idx] = cameras[idx];
			}
			cameras = temp;
		}
	}

	/**
	 * Adds a light to the scene.
	 * 
	 * @param light
	 */
	public void add(Light light) {
		if (nLights < capacityLights) {
			lights[nLights++] = light;
		} else {
			capacityLights *= 2;
			Light temp[] = new Light[capacityLights];
			for (int idx = 0; idx < nLights; ++idx) {
				temp[idx] = lights[idx];
			}
			lights = temp;
		}
	}

	/**
	 * Getter
	 * 
	 * @return The radiance associated with the background (outside the scene).
	 */
	Vector3d getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Getter
	 * 
	 * @return The radiance associated with local ambient light.
	 */
	Vector3d getAmbientLight() {
		return Lambient;
	}
}
