package raytracer;

/**
 * Matrix class
 * 
 * @author Manuel Nunes
 * 
 */

public class Matrix {
	private double m[][] = new double[4][4];
	/** < 4x4 matrix */
	public double kEpsilon = 0.000000001;
	/** < permissible error due to floating point operations */

	/**
	 * Default constructor
	 *
	 * Identity matrix
	 */
	public Matrix() {
		for (int column = 0; column < 4; ++column) {
			for (int row = 0; row < 4; ++row) {
				m[column][row] = 0.0;
			}
		}
		m[0][0] = m[1][1] = m[2][2] = m[3][3] = 1.0;
	}

	/**
	 * Constructor
	 *
	 * @param[in] v Value to assign to all positions in the matrix
	 */
	public Matrix(double v) {
		for (int column = 0; column < 4; ++column) {
			for (int row = 0; row < 4; ++row) {
				m[column][row] = v;
			}
		}
	}

	/**
	 * Copy constructor
	 *
	 * @param[in] v matrix to copy
	 */
	public Matrix(Matrix rhs) {
		Matrix obj = new Matrix();
		for (int column = 0; column < 4; ++column) {
			for (int row = 0; row < 4; ++row) {
				obj.m[column][row] = rhs.m[column][row];
			}
		}
	}

	/**
	 * Constructor
	 *
	 * Translation matrix for a rotation about an axis.
	 *
	 * @param[in] up Axis to rotate the matrix about
	 * @param[in] radians Rotation about the axis in radians.
	 */
	public Matrix(Vector3d up, double radians) {
		Vector3d r = new Vector3d(up);

		r.normalize();

		double cos_theta = Math.cos(radians);
		double sin_theta = Math.sin(radians);
		this.m[0][0] = cos_theta + (1 - cos_theta) * r.get(0) * r.get(0);
		this.m[0][1] = (1.0f - cos_theta) * r.get(0) * r.get(1) + r.get(2) * sin_theta;
		this.m[0][2] = (1.0f - cos_theta) * r.get(0) * r.get(2) - r.get(1) * sin_theta;
		this.m[0][3] = 0.0;
		this.m[1][0] = (1.0f - cos_theta) * r.get(0) * r.get(1) - r.get(2) * sin_theta;
		this.m[1][1] = cos_theta + (1.0f - cos_theta) * r.get(1) * r.get(1);
		this.m[1][2] = (1.0f - cos_theta) * r.get(1) * r.get(2) + r.get(0) * sin_theta;
		this.m[1][3] = 0.0;
		this.m[2][0] = (1.0f - cos_theta) * r.get(0) * r.get(2) + r.get(1) * sin_theta;
		this.m[2][1] = (1.0f - cos_theta) * r.get(1) * r.get(2) - r.get(0) * sin_theta;
		this.m[2][2] = cos_theta + (1.0f - cos_theta) * r.get(2) * r.get(2);
		this.m[2][3] = 0.0;
		this.m[3][0] = 0.0;
		this.m[3][1] = 0.0;
		this.m[3][2] = 0.0;
		this.m[3][3] = 1.0;
	}

	/**
	 * Construct a matrix based on a quaternion rotation.
	 *
	 * @param[in] q Quaternion rotation
	 * @return A new matrix which performs the quaternion rotation.
	 */
	public Matrix(Quaternion q) {
		double s = 2.0 / q.norm();

		m[0][0] = 1.0 - s * (q.q.get(1) * q.q.get(1) + q.q.get(2) * q.q.get(2));
		m[0][1] = s * (q.q.get(0) * q.q.get(1) + q.qw * q.q.get(2));
		m[0][2] = s * (q.q.get(0) * q.q.get(2) - q.qw * q.q.get(1));
		m[0][3] = 0.0;
		m[1][0] = s * (q.q.get(0) * q.q.get(1) - q.qw * q.q.get(2));
		m[1][1] = 1.0 - s * (q.q.get(0) * q.q.get(0) + q.q.get(2) * q.q.get(2));
		m[1][2] = s * (q.q.get(1) * q.q.get(2) + q.qw * q.q.get(0));
		m[1][3] = 0.0;
		m[2][0] = s * (q.q.get(0) * q.q.get(2) + q.qw * q.q.get(1));
		m[2][1] = s * (q.q.get(1) * q.q.get(2) - q.qw * q.q.get(0));
		m[2][2] = 1.0 - s * (q.q.get(0) * q.q.get(0) + q.q.get(1) * q.q.get(1));
		m[2][3] = 0.0;
		m[3][0] = 0.0;
		m[3][1] = 0.0;
		m[3][2] = 0.0;
		m[3][3] = 1.0;
	}

	/**
	 * + operator
	 *
	 * All elements of the argument are added to a copy of the corresponding
	 * elements in this matrix.
	 *
	 * @param[in] rhs Matrix to add to this matrix.
	 * @return A new matrix which is the sum of this matrix and the argument matrix.
	 */
	public Matrix add(Matrix rhs) {
		Matrix result = new Matrix();
		for (int column = 0; column < 4; ++column) {
			for (int row = 0; row < 4; ++row) {
				result.m[column][row] = m[column][row] + rhs.m[column][row];
			}
		}
		return result;
	}

	/**
	 * - operator
	 *
	 * All elements of the argument are subtracted from a copy of the corresponding
	 * elements in this matrix.
	 *
	 * @param[in] rhs Matrix to subtract from this matrix.
	 * @return A new matrix which is the difference of this matrix and the argument
	 *         matrix.
	 */
	public Matrix subtract(Matrix rhs) {
		Matrix result = new Matrix();
		for (int column = 0; column < 4; ++column) {
			for (int row = 0; row < 4; ++row) {
				result.m[column][row] = m[column][row] - rhs.m[column][row];
			}
		}
		return result;
	}

	/**
	 * Rotate the matrix about the x axis.
	 *
	 * @param[in] angle Rotation about the x axis in degrees.
	 */
	public void Rx(double angle) {
		this.m[0][0] = 1.0;

		this.m[1][1] = Math.cos(angle);
		this.m[1][2] = Math.sin(angle);

		this.m[2][1] = -Math.sin(angle);
		this.m[2][2] = Math.cos(angle);

		this.m[3][3] = 1.0;
	}

	/**
	 * Rotate the matrix about the y axis.
	 *
	 * @param[in] angle Rotation about the y axis in degrees.
	 */
	public void Ry(double angle) {
		this.m[0][0] = Math.cos(angle);
		this.m[0][2] = -Math.sin(angle);

		this.m[1][1] = 1.0;

		this.m[2][0] = Math.sin(angle);
		this.m[2][2] = Math.cos(angle);

		this.m[3][3] = 1.0;
	}

	/**
	 * Rotates the matrix about the z axis.
	 *
	 * @param[in] angle Rotation about the z axis in degrees.
	 */
	public void Rz(double angle) {
		this.m[0][0] = Math.cos(angle);
		this.m[0][1] = Math.sin(angle);

		this.m[1][0] = -Math.sin(angle);
		this.m[1][1] = Math.cos(angle);

		this.m[2][2] = 1.0;

		this.m[3][3] = 1.0;
	}

	/**
	 * Rotates the matrix from up vector s to up vector t.
	 * 
	 * Based on :
	 * http://immersivemath.com/forum/question/rotation-matrix-from-one-vector-to-
	 * another/ as a possible solution does not work for u and v pointing in
	 * opposite directions or are identical
	 *
	 * @param[in] s Starting vector for rotation.
	 * @param[in] t Ending vector for rotation.
	 */
	public void R(Vector3d u, Vector3d v) {
		if (u.equals(v.negated())) {
			for (char i = 0; i < 4; ++i) {
				for (char j = 0; j < 4; ++j) {
					if (i == j) {
						this.m[i][j] = -1.0;
					} else {
						this.m[i][j] = 0.0;
					}
				}
			}
			return;
		} else if (u.equals(v)) {
			for (char i = 0; i < 4; ++i) {
				for (char j = 0; j < 4; ++j) {
					if (i == j) {
						this.m[i][j] = 1.0;
					} else {
						this.m[i][j] = 0.0;
					}
				}
			}
			return;
		}

		Vector3d a = u.cross(v);
		a.normalize();

		double alpha = Math.acos(u.dot(v));
		double c = Math.cos(alpha);
		double s = Math.sin(alpha);

		this.m[0][0] = a.get(0) * a.get(0) * (1.0f - c) + c;
		this.m[1][0] = a.get(0) * a.get(1) * (1.0f - c) - s * a.get(2);
		this.m[2][0] = a.get(0) * a.get(2) * (1.0f - c) + s * a.get(1);
		this.m[3][0] = 0.0;

		this.m[0][1] = a.get(0) * a.get(1) * (1.0f - c) + s * a.get(2);
		this.m[1][1] = a.get(1) * a.get(1) * (1.0f - c) + c;
		this.m[2][1] = a.get(1) * a.get(2) * (1.0f - c) - s * a.get(0);
		this.m[3][1] = 0.0;

		this.m[0][2] = a.get(0) * a.get(2) * (1.0f - c) - s * a.get(1);
		this.m[1][2] = a.get(1) * a.get(2) * (1.0f - c) + s * a.get(0);
		this.m[2][2] = a.get(2) * a.get(2) * (1.0f - c) + c;
		this.m[3][2] = 0.0;

		this.m[3][0] = 0.0;
		this.m[3][1] = 0.0;
		this.m[3][2] = 0.0;
		this.m[3][3] = 1.0;
	}


	/**
	 * Multiply two matrices.
	 *
	 * @param[in] rhs Matrix to multiply this matrix by.
	 * 
	 * @return A new matrix which is a product of this matrix and the rhs matrix.
	 */
	public Matrix cross(Matrix rhs) {
		Matrix result = new Matrix();

		for (int column = 0; column < 4; ++column) {
			for (int row = 0; row < 4; ++row) {
				double s = 0.0;

				for (int i = 0; i < 4; ++i) {
					s += m[i][row] * m[column][i];
				}

				result.m[column][row] = s;
			}
		}

		/*
		 * for (int column = 0; column < 4; ++column) { concurrency::parallel_for(0, 4,
		 * 1, [](int row) { double s = 0.0;
		 * 
		 * for (int i = 0; i < 4; ++i) { s += m[i][row] * rhs.m[column][i]; }
		 * result.m[column][row] = s; }); }
		 */
		return result;
	}

	/**
	 * Calculate the adjunct matrix of this matrix.
	 */
	public Matrix adj() {
		Matrix result = new Matrix();
		result.m[0][0] = m[1][1] * m[2][2] * m[3][3] + m[2][1] * m[3][2] * m[1][3] + m[3][1] * m[1][2] * m[2][3]
				- m[1][1] * m[3][2] * m[2][3] - m[2][1] * m[1][2] * m[3][3] - m[3][1] * m[2][2] * m[1][3];

		result.m[1][0] = -(m[0][1] * m[2][2] * m[3][3] + m[2][1] * m[3][2] * m[0][3] + m[3][1] * m[0][2] * m[2][3]
				- m[0][1] * m[3][2] * m[2][3] - m[2][1] * m[0][2] * m[3][3] - m[3][1] * m[2][2] * m[0][3]);

		result.m[2][0] = m[0][1] * m[1][2] * m[3][3] + m[1][1] * m[3][2] * m[0][3] + m[3][1] * m[0][2] * m[1][3]
				- m[0][1] * m[3][2] * m[1][3] - m[1][1] * m[0][2] * m[3][3] - m[3][1] * m[1][2] * m[0][3];

		result.m[3][0] = -(m[0][1] * m[1][2] * m[2][3] + m[1][1] * m[2][2] * m[0][3] + m[2][1] * m[0][2] * m[1][3]
				- m[0][1] * m[2][2] * m[1][3] - m[1][1] * m[0][2] * m[2][3] - m[2][1] * m[1][2] * m[0][3]);

		result.m[0][1] = -(m[1][0] * m[2][2] * m[3][3] + m[2][0] * m[3][2] * m[1][3] + m[3][0] * m[1][2] * m[2][3]
				- m[1][0] * m[3][2] * m[2][3] - m[2][0] * m[1][2] * m[3][3] - m[3][0] * m[2][2] * m[1][3]);

		result.m[1][1] = m[0][0] * m[2][2] * m[3][3] + m[2][0] * m[3][2] * m[0][3] + m[3][0] * m[0][2] * m[2][3]
				- m[0][0] * m[3][2] * m[2][3] - m[2][0] * m[0][2] * m[3][3] - m[3][0] * m[2][2] * m[0][3];

		result.m[2][1] = -(m[0][0] * m[1][2] * m[3][3] + m[1][0] * m[3][2] * m[0][3] + m[3][0] * m[0][2] * m[1][3]
				- m[0][0] * m[3][2] * m[1][3] - m[1][0] * m[0][2] * m[3][3] - m[3][0] * m[1][2] * m[0][3]);

		result.m[3][1] = m[0][0] * m[1][2] * m[2][3] + m[1][0] * m[2][2] * m[0][3] + m[2][0] * m[0][2] * m[1][3]
				- m[0][0] * m[2][2] * m[1][3] - m[1][0] * m[0][2] * m[2][3] - m[2][0] * m[1][2] * m[0][3];

		result.m[0][2] = m[1][0] * m[2][1] * m[3][3] + m[2][0] * m[3][1] * m[1][3] + m[3][0] * m[1][1] * m[2][3]
				- m[1][0] * m[3][1] * m[2][3] - m[2][0] * m[1][1] * m[3][3] - m[3][0] * m[2][1] * m[1][3];

		result.m[1][2] = -(m[0][0] * m[2][1] * m[3][3] + m[2][0] * m[3][1] * m[0][3] + m[3][0] * m[0][1] * m[2][3]
				- m[0][0] * m[3][1] * m[2][3] - m[2][0] * m[0][1] * m[3][3] - m[3][0] * m[2][1] * m[0][3]);

		result.m[2][2] = m[0][0] * m[1][1] * m[3][3] + m[1][0] * m[3][1] * m[0][3] + m[3][0] * m[0][1] * m[1][3]
				- m[0][0] * m[3][1] * m[1][3] - m[1][0] * m[0][1] * m[3][3] - m[3][0] * m[1][1] * m[0][3];

		result.m[3][2] = -(m[0][0] * m[1][1] * m[2][3] + m[1][0] * m[2][1] * m[0][3] + m[2][0] * m[0][1] * m[1][3]
				- m[0][0] * m[2][1] * m[1][3] - m[1][0] * m[0][1] * m[2][3] - m[2][0] * m[1][1] * m[0][3]);

		result.m[0][3] = -(m[1][0] * m[2][1] * m[3][2] + m[2][0] * m[3][1] * m[1][2] + m[3][0] * m[1][1] * m[2][2]
				- m[1][0] * m[3][1] * m[2][2] - m[2][0] * m[1][1] * m[3][2] - m[3][0] * m[2][1] * m[1][2]);

		result.m[1][3] = m[0][0] * m[2][1] * m[3][2] + m[2][0] * m[3][1] * m[0][2] + m[3][0] * m[0][1] * m[2][2]
				- m[0][0] * m[3][1] * m[2][2] - m[2][0] * m[0][1] * m[3][2] - m[3][0] * m[2][1] * m[0][2];

		result.m[2][3] = -(m[0][0] * m[1][1] * m[3][2] + m[1][0] * m[3][1] * m[0][2] + m[3][0] * m[0][1] * m[1][2]
				- m[0][0] * m[3][1] * m[1][2] - m[1][0] * m[0][1] * m[3][2] - m[3][0] * m[1][1] * m[0][2]);

		result.m[3][3] = m[0][0] * m[1][1] * m[2][2] + m[1][0] * m[2][1] * m[0][2] + m[2][0] * m[0][1] * m[1][2]
				- m[0][0] * m[2][1] * m[1][2] - m[1][0] * m[0][1] * m[2][2] - m[2][0] * m[1][1] * m[0][2];

		return (result);
	}

	/**
	 * Calculate the inverse of this matrix.
	 *
	 * @return Matrix inverse of this matrix.
	 */
	public Matrix inverse() {
		return adj().divide(determinant());
	}

	/**
	 * Calculate the determinant of this matrix.
	 *
	 * @return determininant of this matrix
	 */
	public double determinant() {
		double v = m[0][0] * m[1][1] * m[2][2] * m[3][3] + m[1][0] * m[2][1] * m[3][2] * m[0][3]
				+ m[2][0] * m[3][1] * m[0][2] * m[1][3] + m[3][0] * m[0][1] * m[1][2] * m[2][3]
				- m[0][0] * m[3][1] * m[2][2] * m[1][3] - m[1][0] * m[0][1] * m[3][2] * m[2][3]
				- m[2][0] * m[1][1] * m[0][2] * m[3][3] - m[3][0] * m[2][1] * m[1][2] * m[0][3];
		return (v);
	}

	/**
	 * Colculate the transpose of this matrix.
	 *
	 * @return transpose of this matrix
	 */
	public Matrix transpose() {
		Matrix result = new Matrix(this);

		for (int column = 0; column < 4; ++column) {
			for (int row = column + 1; row < 4; ++row) { // skips diagonal with +1
				double swp = result.m[column][row];
				result.m[column][row] = result.m[row][column];
				result.m[row][column] = swp;
			}
		}

		return result;
	}

	/**
	 * * operator
	 * 
	 * Multiply a copy of this matrix by a constant.
	 *
	 * @param[in] a constant to multiply all elements of the matrix by
	 * @return A new matrix which is the product of this matrix and a constant.
	 */
	public Matrix multiply(double a) {
		Matrix result = new Matrix();

		for (int column = 0; column < 4; ++column) {
			for (int row = 0; row < 4; ++row) {
				result.m[column][row] = this.m[column][row] * a;
			}
		}

		return result;
	}

	/**
	 * / operator
	 *
	 * Divide a copy of this matrix by a constant.
	 *
	 * @param[in] a constant to divide all elements of the matrix by
	 * @return A new matrix which is the division of this matrix and a constant.
	 */
	public Matrix divide(double a) {
		Matrix result;

		double invA = 1.0 / a;

		result = this.multiply(invA);

		return result;
	}

	/**
	 * Cross product of a Matrix and a Vector3d.
	 *
	 * Tranforms the Vector3d
	 *
	 * @param[in] rhs Vector3d to multiply by.
	 * 
	 * @return A new Vector3d which is a product of this matrix and the rhs
	 *         Vector3d.
	 */
	public Vector3d cross(Vector3d v) {
		Vector3d result = new Vector3d();
		result.m_Vector[0] = this.m[0][0] * v.get(0) + this.m[1][0] * v.get(1) + this.m[2][0] * v.get(2);
		result.m_Vector[1] = this.m[0][1] * v.get(0) + this.m[1][1] * v.get(1) + this.m[2][1] * v.get(2);
		result.m_Vector[2] = this.m[0][2] * v.get(0) + this.m[1][2] * v.get(1) + this.m[2][2] * v.get(2);

		return result;
	}

	/**
	 * Multiply a Matrix and a Point3f.
	 *
	 * Tranforms the Point3f
	 *
	 * @param[in] rhs Point3f to multiply by.
	 * 
	 * @return A new Point3f which is a product of this matrix and the rhs Point3f.
	 */
	public Vector3f cross(Vector3f p) {
		Vector3f result = new Vector3f();
		result.m_Vector[0] = (float) (this.m[0][0] * p.get(0) + this.m[1][0] * p.get(1) + this.m[2][0] * p.get(2)
				+ this.m[3][0]);
		result.m_Vector[1] = (float) (this.m[0][1] * p.get(0) + this.m[1][1] * p.get(1) + this.m[2][1] * p.get(2)
				+ this.m[3][1]);
		result.m_Vector[2] = (float) (this.m[0][2] * p.get(0) + this.m[1][2] * p.get(1) + this.m[2][2] * p.get(2)
				+ this.m[3][2]);
		return result;
	}
}
