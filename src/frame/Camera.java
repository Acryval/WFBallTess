package frame;

import frame.math.Mat4;
import frame.math.Vec2;
import frame.math.Vec3;
import frame.math.Vec4;

public class Camera
{
	public static final Vec3 FRONT = new Vec3(0, 0, 1);
	public static final Vec3 UP = new Vec3(0, 1, 0);
	public static final Vec3 RIGHT = new Vec3(1, 0, 0);
	
	private Mat4 prj, rot, dir, posM; // projection matrix
	private Vec3 pos;
	private double aspr, clipd; // aspect ratio | clipping double
	private int width, height;
	
	public Camera(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.prj = Mat4.getIdentity();
		this.rot = Mat4.getIdentity();
		this.dir = Mat4.getIdentity();
		this.posM = Mat4.getUndefined();
		this.pos = new Vec3();
		this.aspr = width / height;
		this.clipd = 0;
	}
	
	public static Camera getProjectionCamera(int width, int height, double fov, double near, double far, double clipd)
	{
		Camera c = new Camera(width, height);
		
		c.prj.set(Mat4.getProjection(c.aspr, fov, near, far));
		c.clipd = clipd;
		
		return c;
	}
	
	public boolean clip(Vec2 v)
	{
		if(v.x > 1 + clipd)
			return true;
		if(v.x < -clipd)
			return true;
		if(v.y > 1 + clipd)
			return true;
		if(v.y < -clipd)
			return true;
		return false;
	}
	
	public void updateProjection(int width, int height, double fov, double near, double far, double clipd)
	{
		this.width = width;
		this.height = height;
		this.aspr = (double)width / height;
		this.prj.set(Mat4.getProjection(aspr, fov, near, far));
		this.clipd = clipd;
	}
	
	public void readyForDrawing()
	{posM.set(Mat4.getTranslation(-pos.x, -pos.y, -pos.z));}
	
	public Vec2 getOnWindow(Vec4 vertex)
	{
		Vec4 v1 = vertex.clone().mul(posM).mul(rot);
		Vec2 v = v1.mul(prj).Sxy().add(new Vec2(0.5, 0.5));
		if(clip(v) || v1.z > 0)
			v.disable();
		return v.mul(width, height);
	}
	
	public Vec3 front()
	{return new Vec3(dir.m02, dir.m12, dir.m22);}
	
	public Vec3 up()
	{return new Vec3(dir.m01, dir.m11, dir.m21);}
	
	public Vec3 right()
	{return new Vec3(dir.m00, dir.m10, dir.m20);}
	
	public void move(Vec3 dpos)
	{pos.add(dpos);}
	
	public void setPos(Vec3 npos)
	{pos.set(npos);}
	
	public Vec3 getPos()
	{return pos.clone();}
	
	public void rotate(Vec3 drot)
	{
		rot.rmul(Mat4.getRot3(drot.x, drot.y, drot.z));
		dir.mul(Mat4.getRot3(-drot.x, -drot.y, -drot.z));
	}
	
	public void rotate(Vec3 axis, double angle)
	{rotate(axis.mul(angle));}
	
	public void setRotation(Vec3 nrot)
	{
		rot.set(Mat4.getRot3(nrot.x, nrot.y, nrot.z));
		dir.set(Mat4.getRot3(-nrot.x, -nrot.y, -nrot.z));
	}
}