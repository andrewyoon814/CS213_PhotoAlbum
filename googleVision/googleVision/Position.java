package googleVision;
/*
	Copyright Google Inc. 2016
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
*/
/**
 * A 3D position in the image, used primarily for Face detection landmarks. A
 * valid Position must have both x and y coordinates. The position coordinates
 * are in the same scale as the original image. As defined by
 * https://cloud.google.com/vision/reference/rest/v1/images/annotate#position
 * 
 * @author lauriewhite
 * @version 3 October 2016
 * 
 */
public class Position {
	private double x; // X coordinate
	private double y; // Y coordinate
	private double z; // Z coordinate (or depth)

	/**
	 * X coordinate
	 * 
	 * @return the x coordinate
	 */
	public double getX() {
		return x;
	}

	/**
	 * Y coordinate
	 * 
	 * @return the y coordinate
	 */
	public double getY() {
		return y;
	}

	/**
	 * Z coordinate (or depth)
	 * 
	 * @return the z coordinate
	 */
	public double getZ() {
		return z;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + ", z=" + z + "]";
	}

}
