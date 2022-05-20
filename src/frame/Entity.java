package frame;

import java.awt.Graphics2D;

import frame.math.Mat4;
import frame.math.Vec2;
import frame.math.Vec3;

public class Entity
{
	protected Model3d model;
	private Mat4 rot;
	private Vec3 pos;
	
	public Entity(Vec3 pos, Model3d model)
	{
		this.pos = pos;
		this.rot = Mat4.getIdentity();
		this.model = model;
	}
	
	public void move(Vec3 vec)
	{pos.add(vec);}
	
	public void setPos(Vec3 vec)
	{pos.set(vec);}
	
	public Vec3 getPos()
	{return pos.clone();}

	public void rotate(Vec3 vec)
	{rot.mul(Mat4.getRot3(vec.x, vec.y, vec.z));}
	
	public void rotate(Vec3 axis, double angle)
	{rotate(axis.mul(angle));}
	
	public void setRotation(Vec3 vec)
	{rot.set(Mat4.getRot3(vec.x, vec.y, vec.z));}
	
	public void draw(Graphics2D g, Camera cam)
	{
		Mat4 modelMatrix = rot.clone().mul(Mat4.getTranslation(-pos.x, -pos.y, -pos.z));
		for(int[] ia : model.getLines())
		{
			Vec2 p1 = cam.getOnWindow(model.getMulVertex(ia[0], modelMatrix));
			Vec2 p2 = cam.getOnWindow(model.getMulVertex(ia[1], modelMatrix));
			
			if(p1.isEnabled() || p2.isEnabled())
				g.drawLine((int)p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
		}
	}
}