package frame.math;

public class Mat2
{
	public double 	m00, m01,
					m10, m11;
	
	public Mat2()
	{
		this.m00 = 0;
		this.m01 = 0;
		this.m10 = 0;
		this.m11 = 0;
	}
	
	public Mat2(Mat2 m)
	{set(m);}
	
	public Mat2 set(Mat2 ma)
	{
		Mat2 m = ma.clone();
		
		this.m00 = m.m00;
		this.m01 = m.m01;
		
		this.m10 = m.m10;
		this.m11 = m.m11;
		
		return this;
	}
	
	public Mat2 getIdentity()
	{
		Mat2 m = new Mat2();
		
		m.m00 = 1;
		m.m11 = 1;
		
		return m;
	}
	
	public Mat2 getRot(double angle)
	{
		double ca = Math.cos(angle);
		double sa = Math.sin(angle);
		
		Mat2 m = new Mat2();
		
		m.m00 = ca;
		m.m01 = -sa;
		m.m10 = sa;
		m.m11 = ca;
		
		return m;
	}
	
	public Mat2 getScale(double sx, double sy)
	{
		Mat2 m = new Mat2();
		
		m.m00 = sx;
		m.m11 = sy;
		
		return m;
	}
	
	public Mat2 mul(Mat2 m)
	{
		Vec2 	t0 = new Vec2(m.m00, m.m01),
				t1 = new Vec2(m.m10, m.m11);
		
		Vec2 	m0 = new Vec2(this.m00, this.m10),
				m1 = new Vec2(this.m01, this.m11);
		
		Mat2 ret = new Mat2();
		
		ret.m00 = m0.dot(t0);
		ret.m01 = m0.dot(t1);
		
		ret.m10 = m1.dot(t0);
		ret.m11 = m1.dot(t1);
		
		return ret;
	}
	
	public Mat2 clone()
	{
		Mat2 m = new Mat2();
		
		m.m00 = this.m00;
		m.m01 = this.m01;
		
		m.m10 = this.m10;
		m.m11 = this.m11;
		
		return m;
	}
	
	public String toString()
	{
		return 	"m2[" + m00 + ", " + m01 + "]\n"+
				"  [" + m10 + ", " + m11 + "]\n";
	}
}