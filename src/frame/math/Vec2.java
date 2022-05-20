package frame.math;

public class Vec2
{
	public double x, y;
	public boolean enabled;
	
	public Vec2(double x, double y)
	{set(x, y); enabled = true;}
	
	public Vec2(Vec2 vec)
	{set(vec); enabled = true;}
	
	public Vec2()
	{this(0, 0);}
	
	public void enable()
	{enabled = true;}
	
	public void disable()
	{enabled = false;}
	
	public Vec2 getEnabled() {
		enable();
		return this;
	}
	
	public Vec2 getDisabled() {
		disable();
		return this;
	}
	
	public boolean isEnabled()
	{return enabled;}
	
	public double dot(Vec2 vec)
	{return vec.x*this.x + vec.y*this.y;}
	
	public double lengthSquared()
	{return dot(this);}
	
	public double length()
	{return Math.sqrt(lengthSquared());}
	
	public Vec2 set(double x, double y)
	{this.x = x; this.y = y; return this;}
	
	public Vec2 set(Vec2 vec)
	{Vec2 v = vec.clone(); return set(v.x, v.y);}
	
	public Vec2 add(Vec2 vec)
	{this.x += vec.x; this.y += vec.y; return this;}
	
	public Vec2 sub(Vec2 vec)
	{this.x -= vec.x; this.y -= vec.y; return this;}
	
	public Vec2 mul(double scale)
	{this.x *= scale; this.y *= scale; return this;}
	
	public Vec2 mul(double sx, double sy)
	{this.x *= sx; this.y *= sy; return this;}
	
	public Vec2 mul(Vec2 scs)
	{this.x *= scs.x; this.y *= scs.y; return this;}
	
	public Vec2 div(double div)
	{this.x /= div; this.y /= div; return this;}
	
	public Vec2 half()
	{return new Vec2(this.x/2, this.y/2);}
	
	public Vec2 negate()
	{this.x = -this.x; this.y = -this.y; return this;}
	
	public Vec2 zero()
	{this.x = 0; this.y = 0; return this;}
	
	public Vec2 normalise()
	{double l = length(); if(l != 0) {this.x /= l; this.y /= l;} return this;}
	
	public boolean equals(Vec2 vec)
	{
		if(vec.x != this.x)
			return false;
		if(vec.y != this.y)
			return false;
		return true;
	}
	
	public Vec2 clone()
	{return new Vec2(this.x, this.y);}
	
	public String toString()
	{return "v2[" + x + ", " + y + "]\n";}
}