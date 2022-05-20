package frame;

import frame.math.Vec3;
import frame.math.Vec4;

public class Ball extends Entity {
	
	public Ball(Vec3 pos, int R, double scale) {
		super(pos, null);		
		int npts = 1 + 2*R + 2*R*(R+1)*(2*R+1)/3;
		Vec4[] points = new Vec4[npts];
		points[0] = new Vec4(pos.x, pos.y, pos.z);
		double hpi = Math.PI/2;
		for(int r = 1; r <= R; r++) {
			for(int n = 0; n <= 2*r; n++) {
				int k = 2*Math.min(n, r) - n;
				if(k == 0)
				{
					points[getIndex(r, n, 0)] = new Vec4(scale*r*Math.sin(n*hpi/r) + pos.x, pos.y, scale*r*Math.cos(n*hpi/r) + pos.z);
				}
				else
					for(int m = 0; m < 4*k; m++) {
						points[getIndex(r, n, m)] = new Vec4(scale*r*Math.sin(n*hpi/r)*Math.cos(m*hpi/k) + pos.x, scale*r*Math.sin(n*hpi/r)*Math.sin(m*hpi/k) + pos.y, scale*r*Math.cos(n*hpi/r) + pos.z);
					}
			}
		}
		int[][] lines = new int[0][2];
		int k = 0, i1 = 0, i2 = 0;
		for(int r = R; r <= R; r++) {
			for(int n = 0; n <= 2*r; n++) {
				k = 2*Math.min(r, n) - n;
				if(r != R) {
					if(n==0) {
						i1 = getIndex(r, 0, 0);
						i2 = getIndex(r+1, 0, 0);
						lines = addLine(lines, i1, i2);
					}
					if(n == 2*r){
						i1 = getIndex(r, n, 0);
						i2 = getIndex(r+1, n+2, 0);
						lines = addLine(lines, i1, i2);
					}
				}
				for(int m = 0; m < 4*k; m++) {
					i1 = getIndex(r, n, m);
					i2 = getIndex(r, n, m == 0 ? 4*k-1 : m-1);
					lines = addLine(lines, i1, i2);
					
					if(n < r) {
						i2 = getIndex(r, n == 0 ? 2*r : n-1, m == 4*k-1 ? 0 : m - Math.floorDiv(m, k));
						lines = addLine(lines, i1, i2);
						i2 = getIndex(r, n == 0 ? 2*r : n-1, m - Math.floorDiv(m + k-1, k));
						lines = addLine(lines, i1, i2);
						i2 = getIndex(r-1, n, m);
						//lines = addLine(lines, i1, i2);
						i2 = getIndex(r-1, n-1, m - Math.floorDiv(m + k-1, k));
						//lines = addLine(lines, i1, i2);
						i2 = getIndex(r-1, n-1, m == 4*k-1 ? 0 : m - Math.floorDiv(m, k));
						//lines = addLine(lines, i1, i2);
					}else if(n == r){
						i2 = getIndex(r, n-1, m == 4*k-1 ? 0 : m - Math.floorDiv(m, k));
						lines = addLine(lines, i1, i2);
						i2 = getIndex(r, n == 0 ? 2*r : n-1, m - Math.floorDiv(m + k-1, k));
						lines = addLine(lines, i1, i2);
						i2 = getIndex(r, n+1, m - Math.floorDiv(m + k - 1, k));
						lines = addLine(lines, i1, i2);
						i2 = getIndex(r, n+1, m == 4*k-1 ? 0 : m - Math.floorDiv(m, k));
						lines = addLine(lines, i1, i2);
						if(r != R) {
							i2 = getIndex(r+1, n+1, m + Math.floorDiv(m, k));
							lines = addLine(lines, i1, i2);
							i2 = getIndex(r+1, n+1, m + Math.floorDiv(m + k-1, k));
							//lines = addLine(lines, i1, i2);
							i2 = getIndex(r+1, n+2, m);
							//lines = addLine(lines, i1, i2);
						}
					}else if(n != 2*r){
						i2 = getIndex(r, n+1, m - Math.floorDiv(m + k - 1, k));
						lines = addLine(lines, i1, i2);
						i2 = getIndex(r, n+1, m == 4*k-1 ? 0 : m - Math.floorDiv(m, k));
						lines = addLine(lines, i1, i2);
						if(r != R) {
							i2 = getIndex(r+1, n+2, m);
							//lines = addLine(lines, i1, i2);
							i2 = getIndex(r+1, n+1, m + Math.floorDiv(m + k-1, k));
							//lines = addLine(lines, i1, i2);
							i2 = getIndex(r+1, n+1, m + Math.floorDiv(m, k));
							//lines = addLine(lines, i1, i2);
						}
					}
				}
			}
		}
		/*for(int i = 0; i < 2*npts; i++) {
			lines[i][0] = i;
			lines[i][1] = i;
		}*/
		
		/*for(int r = 1; r <= R; r++) {
			int ind = 0, tm = 0;
			for(int n = 0; n <= 2*r; n++) {
				int k = 2*Math.min(n, r) - n;
				if(k == 0)
				{
					ind = getIndex(r, n, 0);
					lines[ind][0] = ind;
					lines[ind][1] = ind;
				}
				else
					for(int m = 0; m < 4*k; m++) {
						ind = getIndex(r, n, m);
						lines[ind][0] = ind;
						tm = (m == 0) ? 4*k-1 : m-1;
						lines[ind][1] = getIndex(r, n, tm);
					}
			}
		}*/
		
		/*int x = 0;
		for(int r = 1; r <= R; r++) {
			int ind = 0, tn = 0, tm = 0;
			for(int n = 0; n < r; n++) {
				int k = 2*Math.min(n, r) - n;
				for(int m = 0; m < 4*k; m++) {
					ind = getIndex(r, n, m);
					lines[npts + ind][0] = ind;
					tm = m == 4*k-1 ? m : Math.floorDiv(m, k);
					tn = n-1;
					if(n == 0)
						tn = 2*r;
					lines[npts + ind][1] = getIndex(r, tn, m-tm);
				}
			}
			for(int n = 2*r; n >= r; n--) {
				int k = 2*Math.min(n, r) - n;
				for(int m = 0; m < 4*k; m++) {
					ind = getIndex(r, n, m);
					lines[npts + ind][0] = ind;
					tm = Math.floorDiv(m + k-1, k);
					tn = n+1;
					lines[npts + ind][1] = getIndex(r, tn, m-tm);
				}
			}
			for(int m = 0; m < 4*r; m++) {
				ind = getIndex(r, r, m);
				lines[2*npts+x][0] = ind;
				tm = m == 4*r-1 ? m : Math.floorDiv(m, r);
				tn = r-1;
				lines[2*npts+x][1] = getIndex(r, tn, m-tm);
				x++;
			}
		}*/
		model = new Model3d(points, lines);
	}
	
	public int getIndex(int r, int n, int m) {
		if(n > 2*r)
			return -1;
		
		int k = 2*Math.min(n, r)-n;
		if(k != 0) {
			if(m >= 4*k)
				return -2;
		}
		else if(m > 0)
			return -3;
		
		if(r == 0)
			return 0;
		
		int i = 0;
		if(n <= r) {
			i = 2*r - 1 + 2*r*(r-1)*(2*r-1)/3;
			if(n > 0)
				i++;
			i += 2*k*(k-1);
		}
		else{
			i = 2*r + 2*r*(r+1)*(2*r+1)/3;
			i -= 2*k*(k+1);
		}
		
		return i + m;
	}
	
	public int[][] addLine(int[][] tab, int idx1, int idx2){
		int tabl = tab.length;
		int[][] ret = new int[tabl+1][2];
		for(int i = 0; i < tabl; i++)
			ret[i] = tab[i];
		ret[tabl] = new int[] {idx1, idx2};
		return ret;
	}
}
