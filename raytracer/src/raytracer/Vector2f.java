package raytracer;

public class Vector2f {
	final private int N = 2;
	/** < number of elements in vector */
	final protected float m_Vector[];
	/** < vector elements */

	/**
	 * Constructor
	 * 
	 * @param values Initializes all elements to 0 on an an empty values array, all
	 *               elements to the the single value of a single element values
	 *               array, or a copy of an N sized values array.
	 * 
	 */
	public Vector2f(float... values) {
		m_Vector = new float[N];

		if (values.length < 2) {
			float value;
			if (values.length == 0) {
				value = 0;
			} else {
				value = values[0];
			}
			for (int idx = 0; idx < N; ++idx) {
				m_Vector[idx] = value;
			}
		} else if (values.length == N) {
			for (int idx = 0; idx < N; ++idx) {
				m_Vector[idx] = values[idx];
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Copy constructor
	 * 
	 * @param another vector to copy.
	 */
	public Vector2f(Vector2f another) {
		m_Vector = another.m_Vector;
	}

	/**
	 * Subtract two vectors.
	 * 
	 * @param another
	 * @return Per element difference of this vector and another vector.
	 */
	public Vector2f subtract(Vector2f another) {
		float result[] = new float[N];
		for (int idx = 0; idx < N; ++idx) {
			result[idx] = m_Vector[idx] - another.m_Vector[idx];
		}

		return new Vector2f(result);
	}

	/**
	 * Vector sum.
	 * 
	 * @param another
	 * @return Per element sum of this vector and another vector.
	 */
	public Vector2f add(Vector2f another) {
		float result[] = new float[N];
		for (int idx = 0; idx < N; ++idx) {
			result[idx] = m_Vector[idx] + another.m_Vector[idx];
		}

		return new Vector2f(result);
	}

	/**
	 * Vector multiply by a float.
	 * 
	 * @param value
	 * @return Per element product of this vector and a float.
	 */
	public Vector2f multiply(float f) {
		float result[] = new float[N];
		for (int idx = 0; idx < N; ++idx) {
			result[idx] = m_Vector[idx] * f;
		}

		return new Vector2f(result);
	}

	/**
	 * Vector multiply.
	 * 
	 * @param another Vector
	 * @return Per element product of this vector and another vector.
	 */
	public Vector2f multiply(Vector2f another) {
		float result[] = new float[N];
		for (int idx = 0; idx < N; ++idx) {
			result[idx] = m_Vector[idx] * another.m_Vector[idx];
		}

		return new Vector2f(result);
	}

	/**
	 * Vector division by a float.
	 * 
	 * @param value
	 * @return Per element division of this vector by a float.
	 */
	public Vector2f divide(float f) {
		float result[] = new float[N];
		for (int idx = 0; idx < N; ++idx) {
			result[idx] = m_Vector[idx] / f;
		}

		return new Vector2f(result);
	}

	/**
	 * Vector division.
	 * 
	 * @param another Vector
	 * @return Per element division of this vector by another vector.
	 */
	public Vector2f divide(Vector2f another) {
		float result[] = new float[N];
		for (int idx = 0; idx < N; ++idx) {
			result[idx] = m_Vector[idx] / another.m_Vector[idx];
		}

		return new Vector2f(result);
	}

	/**
	 * Vector negation.
	 * 
	 * @return Per element negation of this vector.
	 */
	public Vector2f negated() {
		float result[] = new float[N];
		for (int idx = 0; idx < N; ++idx) {
			result[idx] = -m_Vector[idx];
		}

		return new Vector2f(result);
	}

	/**
	 * Dot product
	 * 
	 * @param another
	 * @return Dot product of this vector and another vector.
	 */
	public float dot(Vector2f another) {
		float result = 0;
		for (int idx = 0; idx < N; ++idx) {
			result += m_Vector[idx] * another.m_Vector[idx];
		}

		return result;
	}

	/**
	 * Calculate magnitude squared of this vector.
	 * 
	 * @return Magnitude squared of this vector.
	 */
	public float magnitudeSquared() {
		return this.dot(this);
	}

	/**
	 * Calculate magnitude of this vector.
	 * 
	 * @return Magnitude of this vector.
	 */
	public float magnitude() {
		return (float) Math.sqrt(magnitudeSquared());
	}

	/**
	 * Equals
	 * 
	 * @param another
	 * @return Are all elements of this vector equal to those of another vector?
	 */
	public boolean equals(Vector2f another) {
		for (int idx = 0; idx < N; ++idx) {
			if (m_Vector[idx] != another.m_Vector[idx]) {
				return (false);
			}
		}
		return (true);
	}

	/**
	 * Get float element of vector.
	 * 
	 * @param idx
	 * @return float vector element indexed by idx.
	 */
	public float get(int idx) {
		if (idx < 0 || idx >= N) {
			throw new IllegalArgumentException();
		}
		return m_Vector[idx];
	}

	/**
	 * Normalize this vector.
	 * 
	 * @return A copy of this vector normalized.
	 */
	public Vector2f normalize() {
		return this.divide(magnitude());
	}
}
