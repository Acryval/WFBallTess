package frame.math;

public class Mat4
{
	public double 	m00, m01, m02, m03,
					m10, m11, m12, m13,
					m20, m21, m22, m23,
					m30, m31, m32, m33;
	
	public Mat4()
	{
		m00 = 0; m01 = 0; m02 = 0; m03 = 0;
		m10 = 0; m11 = 0; m12 = 0; m13 = 0;
		m20 = 0; m21 = 0; m22 = 0; m23 = 0;
		m30 = 0; m31 = 0; m32 = 0; m33 = 0;
	}
	
	public Mat4(Mat4 m)
	{set(m);}
	
	public Mat4 set(Mat4 ma)
	{
		Mat4 m = ma.clone();
		
		this.m00 = m.m00;
		this.m01 = m.m01;
		this.m02 = m.m02;
		this.m03 = m.m03;
		
		this.m10 = m.m10;
		this.m11 = m.m11;
		this.m12 = m.m12;
		this.m13 = m.m13;
		
		this.m20 = m.m20;
		this.m21 = m.m21;
		this.m22 = m.m22;
		this.m23 = m.m23;
		
		this.m30 = m.m30;
		this.m31 = m.m31;
		this.m32 = m.m32;
		this.m33 = m.m33;
		
		return this;
	}
	
	public static Mat4 getIdentity()
	{
		Mat4 m = new Mat4();
		
		m.m00 = 1;
		m.m11 = 1;
		m.m22 = 1;
		m.m33 = 1;
		
		return m;
	}
	
	public static Mat4 getUndefined()
	{
		Mat4 m = new Mat4();
		
		m.m00 = Double.NaN;
		
		return m;
	}
	
	public void setUndefined()
	{this.m00 = Double.NaN;}
	
	public boolean isUndefined()
	{return this.m00 == Double.NaN;}
	
	public static Mat4 getRotXY(double angle)
	{
		double ca = Math.cos(angle);
		double sa = Math.sin(angle);
		
		Mat4 m = new Mat4();
		
		m.m00 = ca;
		m.m01 = -sa;
		m.m10 = sa;
		m.m11 = ca;
		m.m22 = 1;
		m.m33 = 1;
		
		return m;
	}
	
	public static Mat4 getRotXZ(double angle)
	{
		double ca = Math.cos(angle);
		double sa = Math.sin(angle);
		
		Mat4 m = new Mat4();
		
		m.m00 = ca;
		m.m02 = -sa;
		m.m11 = 1;
		m.m20 = sa;
		m.m22 = ca;
		m.m33 = 1;
		
		return m;
	}
	
	public static Mat4 getRotXW(double angle)
	{
		double ca = Math.cos(angle);
		double sa = Math.sin(angle);
		
		Mat4 m = new Mat4();
		
		m.m00 = ca;
		m.m13 = -sa;
		m.m11 = 1;
		m.m22 = 1;
		m.m30 = sa;
		m.m33 = ca;
		
		return m;
	}
	
	public static Mat4 getRotYZ(double angle)
	{
		double ca = Math.cos(angle);
		double sa = Math.sin(angle);
		
		Mat4 m = new Mat4();
		
		m.m00 = 1;
		m.m11 = ca;
		m.m12 = -sa;
		m.m21 = sa;
		m.m22 = ca;
		m.m33 = 1;
		
		return m;
	}
	
	public static Mat4 getRotYW(double angle)
	{
		double ca = Math.cos(angle);
		double sa = Math.sin(angle);
		
		Mat4 m = new Mat4();
		
		m.m00 = 1;
		m.m11 = ca;
		m.m13 = -sa;
		m.m22 = 1;
		m.m31 = sa;
		m.m33 = ca;
		
		return m;
	}
	
	public static Mat4 getRotZW(double angle)
	{
		double ca = Math.cos(angle);
		double sa = Math.sin(angle);
		
		Mat4 m = new Mat4();
		
		m.m00 = 1;
		m.m11 = 1;
		m.m22 = ca;
		m.m23 = -sa;
		m.m32 = sa;
		m.m33 = ca;
		
		return m;
	}
	
	public static Mat4 getRot(double rxy, double rxz, double rxw, double ryz, double ryw, double rzw)
	{
		Mat4 m = getIdentity();
		
		if(rxy != 0)
			m.mul(getRotXY(rxy));
		if(rxz != 0)
			m.mul(getRotXZ(rxz));
		if(rxw != 0)
			m.mul(getRotXW(rxw));
		if(ryz != 0)
			m.mul(getRotYZ(ryz));
		if(ryw != 0)
			m.mul(getRotYW(ryw));
		if(rzw != 0)
			m.mul(getRotZW(rzw));
		
		return m;
	}
	
	public static Mat4 getRot3(double rx, double ry, double rz)
	{
		Mat4 m = getIdentity();
		
		if(rx != 0)
			m.mul(getRotYZ(rx));
		if(ry != 0)
			m.mul(getRotXZ(ry));
		if(rz != 0)
			m.mul(getRotXY(rz));
		
		return m;
	}
	
	public static Mat4 getScale(double sx, double sy, double sz, double sw)
	{
		Mat4 m = new Mat4();
		
		m.m00 = sx;
		m.m11 = sy;
		m.m22 = sz;
		m.m33 = sw;
		
		return m;
	}
	
	public static Mat4 getTranslation(double dx, double dy, double dz)
	{
		Mat4 m = getIdentity();
		
		m.m03 = dx;
		m.m13 = dy;
		m.m23 = dz;
		
		return m;
	}
	
	public static Mat4 getProjection(double asc, double fov, double near, double far)
	{
		double cf = 1.0 / Math.tan(fov/2);
		double fr = far - near;
		
		Mat4 m = new Mat4();
		
		m.m00 = cf/asc;
		m.m11 = cf;
		m.m22 = -(far + near)/fr;
		m.m23 = -2*far*near/fr;
		m.m32 = -1.0;
		
		return m;
	}
	
	public Mat4 mul(Mat4 m)
	{
		Vec4 	t0 = new Vec4(this.m00, this.m10, this.m20, this.m30),
				t1 = new Vec4(this.m01, this.m11, this.m21, this.m31),
				t2 = new Vec4(this.m02, this.m12, this.m22, this.m32),
				t3 = new Vec4(this.m03, this.m13, this.m23, this.m33);
		
		Vec4 	m0 = new Vec4(m.m00, m.m01, m.m02, m.m03),
				m1 = new Vec4(m.m10, m.m11, m.m12, m.m13),
				m2 = new Vec4(m.m20, m.m21, m.m22, m.m23),
				m3 = new Vec4(m.m30, m.m31, m.m32, m.m33);
		
		Mat4 ret = new Mat4();
		
		ret.m00 = m0.dot(t0);
		ret.m01 = m0.dot(t1);
		ret.m02 = m0.dot(t2);
		ret.m03 = m0.dot(t3);
		
		ret.m10 = m1.dot(t0);
		ret.m11 = m1.dot(t1);
		ret.m12 = m1.dot(t2);
		ret.m13 = m1.dot(t3);
		
		ret.m20 = m2.dot(t0);
		ret.m21 = m2.dot(t1);
		ret.m22 = m2.dot(t2);
		ret.m23 = m2.dot(t3);
		
		ret.m30 = m3.dot(t0);
		ret.m31 = m3.dot(t1);
		ret.m32 = m3.dot(t2);
		ret.m33 = m3.dot(t3);
		
		return this.set(ret);
	}
	
	public Mat4 clone()
	{
		Mat4 m = new Mat4();
		
		m.m00 = this.m00;
		m.m01 = this.m01;
		m.m02 = this.m02;
		m.m03 = this.m03;
		
		m.m10 = this.m10;
		m.m11 = this.m11;
		m.m12 = this.m12;
		m.m13 = this.m13;
		
		m.m20 = this.m20;
		m.m21 = this.m21;
		m.m22 = this.m22;
		m.m23 = this.m23;
		
		m.m30 = this.m30;
		m.m31 = this.m31;
		m.m32 = this.m32;
		m.m33 = this.m33;
		
		return m;
	}
	
	public Mat4 rmul(Mat4 m)
	{return set(m.clone().mul(this));}
	
	public String toString()
	{
		return 	"m4[" + m00 + ", " + m01 + ", " + m02 + ", " + m03 + "]\n"+
				"  [" + m10 + ", " + m11 + ", " + m12 + ", " + m13 + "]\n"+
				"  [" + m20 + ", " + m21 + ", " + m22 + ", " + m23 + "]\n"+
				"  [" + m30 + ", " + m31 + ", " + m32 + ", " + m33 + "]\n";
	}
}