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
 * A face-specific landmark (for example, a face feature). 
 * Landmark positions may fall outside the bounds of the image when the face is near one or more edges of the image. 
 * Therefore it is NOT guaranteed that 0 <= x < width or 0 <= y < height.
 * As defined by https://cloud.google.com/vision/reference/rest/v1/images/annotate#landmark
 * @author lauriewhite
 * @version 3 October 2016
 *
 */
public class Landmark {
	private String type;  // Face landmark type.
	private Position position;  //  
	
	/**
	 * Face landmark type.
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Face landmark position.
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}
	
	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Landmark [type=" + type + ", position=" + position + "]";
	}
}
