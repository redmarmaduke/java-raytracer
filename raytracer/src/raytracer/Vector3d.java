package raytracer;

/**
 * Vector class for three double values.
 * 
 * @author Manuel Nunes
 *
 */
public class Vector3d {
	final private int N = 3; /** < number of elements */
	final protected double m_Vector[]; /** < vector */

	/**
	 * Constructor
	 * 
	 * @param values Initializes all elements to 0 on an an empty values array, all
	 *               elements to the the single value of a single element values
	 *               array, or a copy of an N sized values array.
	 */
	public Vector3d(double ...values) {
		m_Vector = new double[N];
		
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
	public Vector3d(Vector3d another) {
		m_Vector = another.m_Vector;
	}

	/**
	 * Copy constructor for conversion of Vector3f to Vector3d
	 * @param another 
	 */
	public Vector3d(Vector3f another) {
		m_Vector = new double[N];
		for (int idx = 0; idx < N; ++idx) {
			m_Vector[idx] = another.get(idx);
		}
	}

	/**
	 * Vector subtraction.
	 * 
	 * @param another 
	 * @return Per element subtraction of this vector and another vector.
	 */
	public Vector3d subtract(Vector3d another) {
		Vector3d result = new Vector3d();
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
	public Vector3d add(Vector3d another) {
		Vector3d result = new Vector3d();
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
	public Vector3d multiply(double d) {
		Vector3d result = new Vector3d();		
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
	public Vector3d multiply(Vector3d another) {
		Vector3d result = new Vector3d();
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
	public Vector3d divide(double d) {
		Vector3d result = new Vector3d();
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
	public Vector3d divide(Vector3d another) {
		Vector3d result = new Vector3d();
		for (int idx = 0; idx < N; ++idx) {
			result.m_Vector[idx] = m_Vector[idx] / another.m_Vector[idx];
		}
		
		return result;
	}

	/**
	 * Vector negation.
	 * @return Per element negation of this vector.
	 */
	public Vector3d negated() {
		Vector3d result = new Vector3d();
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
	public double dot(Vector3d another) {
		double result = 0;
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
	public Vector3d cross(Vector3d another) {
		Vector3d result = new Vector3d();
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
	public double magnitudeSquared() {
		return this.dot(this);
	}

	/**
	 * Magnitude
	 * 
	 * @return Magnitude this vector.
	 */	
	public double magnitude() {
		return Math.sqrt(magnitudeSquared());
	}

	/**
	 * Equals
	 * 
	 * @param another
	 * @return Are all elements of this vector equal to those of another vector?
	 */
	public boolean equals(Vector3d another) {
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
	public void orthonormalBasis(Mutable<Vector3d> mU, Mutable<Vector3d> mV) {
		Vector3d v = new Vector3d(0.00424, 1.0, 0.00764);
		v = v.cross(this);
		v = v.normalize();
		mV.set(v);
		Vector3d u = v.cross(this);
		mU.set(u);
	}

	/**
	 * Get element of vector.
	 * 
	 * @param idx
	 * @return vector element indexed by idx.
	 */	
	public double get(int idx) {
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
	public Vector3d normalize() {
		return this.divide(magnitude());
	}
}
