package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Point {
        public int z;
	public int x;
	public int y;
        
	
	public Point(int z, int x, int y){
	this.z = z;	
            this.x = x;
		this.y = y;
                
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
                result = prime * result + z;
		result = prime * result + x;
		result = prime * result + y;
                
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Point))
			return false;
		Point other = (Point) obj;
                if (z != other.z)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
                
		return true;
	}

	public List<Point> neighbors8(){
		List<Point> points = new ArrayList<Point>();
		
		for (int ox = -1; ox < 2; ox++){
			for (int oy = -1; oy < 2; oy++){
				if (ox == 0 && oy == 0)
					continue;
				
				int nx = x+ox;
				int ny = y+oy;
				
				points.add(new Point(z, nx, ny));
			}
		}

		Collections.shuffle(points);
		return points;
	}
}
