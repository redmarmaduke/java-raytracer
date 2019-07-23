package raytracer;

/**
 * Vector class for three float values.
 * 
 * @author Manuel Nunes
 *
 */
public class Vector3f {
	final private int N = 3; /** < number of elements */
	final protected float m_Vector[]; /** < vector */

	/**
	 * Constructor
	 * 
	 * @param values Initializes all elements to 0 on an an empty values array, all
	 *               elements to the the single value of a single element values
	 *               array, or a copy of an N sized values array.
	 */
	public Vector3f(float ...values) {
		m_Vector = new float[N];
		
		if (values.length == 0) {
			return;
		}
		else if (values.length == 1) {
			for (int idx = 0; idx < N; ++idx) {
				m_Vector[idx] = values[0];
			}
		}
		else if (values.length == N){
			for (int idx = 0; idx < N; ++idx) {
				m_Vector[idx] = values[idx];
			}
		}		
		else {
			throw new IllegalArgumentException();
		}
	}
		
	/**
	 * Copy constructor
	 * @param another
	 */
	public Vector3f(Vector3f another) {
		m_Vector = another.m_Vector;
	}

	/**
	 * Copy constructor for conversion of Vector3d to Vector3f
	 * @param another 
	 */
	public Vector3f(Vector3d another) {
		m_Vector = new float[N];
		for (int idx = 0; idx < N; ++idx) {
			m_Vector[idx] = (float)another.get(idx);
		}
	}
	
	/**
	 * Vector subtraction.
	 * 
	 * @param another 
	 * @return Per element subtraction of this vector and another vector.
	 */
	public Vector3f subtract(Vector3f another) {
		Vector3f result = new Vector3f();
		for (int idx = 0; idx < N; ++idx) {
			result.m_Vector[idx] = m_Vector[idx] - another.m_Vector[idx];
		}
		
		return result;
	}

	/**
	 * Vector addition.
	 * 
	 * @param another 
	 * @return Per element addition of this vector and another vector.
	 */	
	public Vector3f add(Vector3f another) {
		Vector3f result = new Vector3f();
		for (int idx = 0; idx < N; ++idx) {
			result.m_Vector[idx] = m_Vector[idx] + another.m_Vector[idx];
		}
		
		return result;
	}


	/**
	 * Vector multiply by a constant.
	 * 
	 * @param d
	 * @return Per element multiply of this vector and a constant.
	 */			
	public Vector3f multiply(float d) {
		Vector3f result = new Vector3f();		
		for (int idx = 0; idx < N; ++idx) {
			result.m_Vector[idx] = m_Vector[idx] * d;
		}
		
		return result;
	}

	/**
	 * Vector multiply.
	 * 
	 * @param another 
	 * @return Per element multiply of this vector and another vector.
	 */		
	public Vector3f multiply(Vector3f another) {
		Vector3f result = new Vector3f();
		for (int idx = 0; idx < N; ++idx) {
			result.m_Vector[idx] = m_Vector[idx] * another.m_Vector[idx];
		}
		
		return result;
	}

	/**
	 * Vector division by a constant.
	 * 
	 * @param d
	 * @return Per element division of this vector by a constant.
	 */		
	public Vector3f divide(float d) {
		Vector3f result = new Vector3f();
		for (int idx = 0; idx < N; ++idx) {
			result.m_Vector[idx] = m_Vector[idx] / d;
		}
		
		return result;
	}

	/**
	 * Vector division.
	 * 
	 * @param another 
	 * @return Per element division of this vector by another vector.
	 */	
	public Vector3f divide(Vector3f another) {
		Vector3f result = new Vector3f();
		for (int idx = 0; idx < N; ++idx) {
			result.m_Vector[idx] = m_Vector[idx] / another.m_Vector[idx];
		}
		
		return result;
	}

	/**
	 * Vector negation.
	 * @return Per element negation of this vector.
	 */
	public Vector3f negated() {
		Vector3f result = new Vector3f();
		for (int idx = 0; idx < N; ++idx) {
			result.m_Vector[idx] = -m_Vector[idx];
		}
		
		return result;
	}

	/**
	 * Math vector dot product.
	 * @param another
	 * @return Dot product of this vector and another vector.
	 */
	public float dot(Vector3f another) {
		float result = 0;
		for (int idx = 0; idx < N; ++idx) {
			result += m_Vector[idx] * another.m_Vector[idx];
		}
		
		return result;
	}
	
	/**
	 * Math cross product.
	 * @param another
	 * @return Cross product of this vector and another vector.
	 */
	public Vector3f cross(Vector3f another) {
		Vector3f result = new Vector3f();
		result.m_Vector[0] = m_Vector[1] * another.m_Vector[2] - m_Vector[2] * another.m_Vector[1];
		result.m_Vector[1] = m_Vector[2] * another.m_Vector[0] - m_Vector[0] * another.m_Vector[2];
		result.m_Vector[2] = m_Vector[0] * another.m_Vector[1] - m_Vector[1] * another.m_Vector[0];
				
		return result;
	}

	/**
	 * Magnitude squared
	 * 
	 * @return Magnitude squared of this vector.
	 */
	public float magnitudeSquared() {
		return this.dot(this);
	}

	/**
	 * Magnitude
	 * 
	 * @return Magnitude this vector.
	 */	
	public float magnitude() {
		return (float)Math.sqrt(magnitudeSquared());
	}

	/**
	 * Equals
	 * 
	 * @param another
	 * @return Are all elements of this vector equal to those of another vector?
	 */
	public boolean equals(Vector3f another) {
		for (int idx = 0; idx < N; ++idx) {
			if (m_Vector[idx] != another.m_Vector[idx]) {
				return (false);
			}
		}
		return (true);
	}

	/**
	 * Construct 3D orthonormal basis.
	 * @param mU mutable used to return u vector
	 * @param mV mutable used to return v vector
	 */
	public void orthonormalBasis(Mutable<Vector3f> mU, Mutable<Vector3f> mV) {
		Vector3f v = new Vector3f(0.00424f, 1.0f, 0.00764f);
		v = v.cross(this);
		v = v.normalize();
		mV.set(v);
		Vector3f u = v.cross(this);
		mU.set(u);
	}

	/**
	 * Get element of vector.
	 * 
	 * @param idx
	 * @return vector element indexed by idx.
	 */	
	public float get(int idx) {
		if (idx < 0 || idx >= N) {
			throw new IllegalArgumentException();
		}
		return m_Vector[idx];
	}

	/**
	 * Normalize vector.
	 * 
	 * @param idx
	 * @return This vector normalized.
	 */		
	public Vector3f normalize() {
		return this.divide(magnitude());
	}
}
