package frame.math;

public class Vec3
{
	public double x, y, z;
	public boolean enabled;
	
	public Vec3(double x, double y, double z)
	{set(x, y, z); enabled = true;}
	
	public Vec3(Vec2 vec, double z)
	{set(vec.x, vec.y, z); enabled = true;}
	
	public Vec3(double x, Vec2 vec)
	{set(x, vec.x, vec.y); enabled = true;}
	
	public Vec3(Vec2 vec)
	{this(vec, 0);}
	
	public Vec3(Vec3 vec)
	{set(vec); enabled = true;}
	
	public Vec3()
	{this(0, 0, 0);}
	
	public void enable()
	{enabled = true;}
	
	public void disable()
	{enabled = false;}
	
	public boolean isEnabled()
	{return enabled;}
	
	public double dot(Vec3 vec)
	{return vec.x*this.x + vec.y*this.y + vec.z*this.z;}
	
	public double lengthSquared()
	{return dot(this);}
	
	public double length()
	{return Math.sqrt(lengthSquared());}
	
	public Vec3 set(double x, double y, double z)
	{this.x = x; this.y = y; this.z = z; return this;}
	
	public Vec3 set(Vec3 vec)
	{Vec3 v = vec.clone(); return set(v.x, v.y, v.z);}
	
	public Vec3 add(Vec3 vec)
	{this.x += vec.x; this.y += vec.y; this.z += vec.z; return this;}
	
	public Vec3 sub(Vec3 vec)
	{this.x -= vec.x; this.y -= vec.y; this.z -= vec.z; return this;}
	
	public Vec3 mul(double scale)
	{this.x *= scale; this.y *= scale; this.z *= scale; return this;}
	
	public Vec3 mul(double sx, double sy, double sz)
	{this.x *= sx; this.y *= sy; this.z *= sz; return this;}
	
	public Vec3 mul(Vec3 scs)
	{this.x *= scs.x; this.y *= scs.y; this.z *= scs.z; return this;}
	
	public Vec3 div(double div)
	{this.x /= div; this.y /= div; this.z /= div; return this;}
	
	public Vec3 negate()
	{this.x = -this.x; this.y = -this.y; this.z = -this.z; return this;}
	
	public Vec3 zero()
	{this.x = 0; this.y = 0; this.z = 0; return this;}
	
	public Vec3 normalize()
	{double l = length(); if(l != 0) {this.x /= l; this.y /= l; this.z /= l;} return this;}
	
	public boolean equals(Vec3 vec)
	{
		if(vec.x != this.x)
			return false;
		if(vec.y != this.y)
			return false;
		if(vec.z != this.z)
			return false;
		return true;
	}
	
	public Vec3 clone()
	{return new Vec3(this.x, this.y, this.z);}
	
	public Vec2 xy()
	{return new Vec2(x, y);}
	
	public String toString()
	{return "v3[" + x + ", " + y + ", " + z + "]\n";}
}