package frame;

import frame.math.Mat4;
import frame.math.Vec4;

public class Model3d
{
	private Vec4[] model;
	private int[][] lines;
	private int vertexCount;
	
	public Model3d(Vec4[] model, int[][] lines)
	{
		this.model = model;
		this.lines = lines;
		this.vertexCount = model.length;
	}
	
	public Vec4[] getProjected(Mat4 modelMatrix)
	{
		Vec4[] vs = new Vec4[vertexCount];
		
		for(int i = 0; i < vertexCount; i++)
			vs[i] = model[i].clone().mul(modelMatrix);
		
		return vs;
	}
	
	public Vec4 getVertex(int index)
	{return model[index].clone();}
	
	public Vec4 getMulVertex(int index, Mat4 modelMatrix)
	{return model[index].clone().mul(modelMatrix);}
	
	public int[][] getLines()
	{return lines;}
}