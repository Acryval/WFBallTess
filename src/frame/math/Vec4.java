package frame.math;

public class Vec4
{
	public double x, y, z, w;
	public boolean enabled;
	
	public Vec4(double x, double y, double z)
	{set(x, y, z, 1); enabled = true;}
	
	public Vec4(double x, double y, double z, double w)
	{set(x, y, z, w); enabled = true;}
	
	public Vec4(Vec3 vec, double w)
	{set(vec.x, vec.y, vec.z, w); enabled = true;}
	
	public Vec4(Vec4 vec)
	{set(vec); enabled = true;}
	
	public Vec4()
	{this(0, 0, 0, 0);}
	
	public void enable()
	{enabled = true;}
	
	public void disable()
	{enabled = false;}
	
	public boolean isEnabled()
	{return enabled;}
	
	public double dot(Vec4 vec)
	{return vec.x*this.x + vec.y*this.y + vec.z*this.z + vec.w*this.w;}
	
	public double lengthSquared()
	{return dot(this);}
	
	public double length()
	{return Math.sqrt(lengthSquared());}
	
	public Vec4 set(double x, double y, double z, double w)
	{this.x = x; this.y = y; this.z = z; this.w = w; return this;}
	
	public Vec4 set(Vec4 vec)
	{Vec4 v = vec.clone(); return set(v.x, v.y, v.z, v.w);}
	
	public Vec4 set(Vec3 vec, double w)
	{Vec3 v = vec.clone(); return set(v.x, v.y, v.z, w);}
	
	public Vec4 add(Vec4 vec)
	{this.x += vec.x; this.y += vec.y; this.z += vec.z; this.w += vec.w; return this;}
	
	public Vec4 sub(Vec4 vec)
	{this.x -= vec.x; this.y -= vec.y; this.z -= vec.z; this.w -= vec.w; return this;}
	
	public Vec4 mul(double scale)
	{this.x *= scale; this.y *= scale; this.z *= scale; this.w *= scale; return this;}
	
	public Vec4 mul(double sx, double sy, double sz, double sw)
	{this.x *= sx; this.y *= sy; this.z *= sz; this.w *= sw; return this;}
	
	public Vec4 mul(Vec4 scs)
	{this.x *= scs.x; this.y *= scs.y; this.z *= scs.z; this.w *= scs.w; return this;}
	
	public Vec4 mul(Mat4 m)
	{
		Vec4	m0 = new Vec4(m.m00, m.m01, m.m02, m.m03),
				m1 = new Vec4(m.m10, m.m11, m.m12, m.m13),
				m2 = new Vec4(m.m20, m.m21, m.m22, m.m23),
				m3 = new Vec4(m.m30, m.m31, m.m32, m.m33);
		
		Vec4 ret = new Vec4();
		
		ret.x = dot(m0);
		ret.y = dot(m1);
		ret.z = dot(m2);
		ret.w = dot(m3);
		
		return this.set(ret);
	}
	
	public Vec4 div(double div)
	{this.x /= div; this.y /= div; this.z /= div; this.w /= div; return this;}
	
	public Vec4 negate()
	{this.x = -this.x; this.y = -this.y; this.z = -this.z; this.w = -this.w; return this;}
	
	public Vec4 zero()
	{this.x = 0; this.y = 0; this.z = 0; this.w = 0; return this;}
	
	public Vec4 normalize()
	{double l = length(); if(l != 0) {this.x /= l; this.y /= l; this.z /= l; this.w /= l;} return this;}
	
	public boolean equals(Vec4 vec)
	{
		if(vec.x != this.x)
			return false;
		if(vec.y != this.y)
			return false;
		if(vec.z != this.z)
			return false;
		if(vec.w != this.w)
			return false;
		return true;
	}
	
	public Vec4 clone()
	{return new Vec4(this.x, this.y, this.z, this.w);}
	
	public Vec3 xyz()
	{return new Vec3(this.x, this.y, this.z);}
	
	public Vec2 xy()
	{return new Vec2(this.x, this.y);}
	
	public Vec3 Sxyz()
	{return new Vec3(this.x/this.w, this.y/this.w, this.z/this.w);}
	
	public Vec2 Sxy()
	{return new Vec2(this.x/this.w, this.y/this.w);}
	
	public String toString()
	{return "v4[" + x + ", " + y + ", " + z + ", " + w + "]\n";}
}