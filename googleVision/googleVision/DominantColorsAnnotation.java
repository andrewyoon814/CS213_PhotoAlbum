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
import java.util.Arrays;

/**
 * Set of dominant colors and their corresponding scores. As defined by
 * https://cloud.google.com/vision/reference/rest/v1/images/annotate#
 * dominantcolorsannotation
 * 
 * @author lauriewhite
 * @version 3 October 2016
 * 
 */
public class DominantColorsAnnotation {
	private ColorInfo[] colors; // RGB color values, with their score and pixel
								// fraction.

	/**
	 * RGB color values, with their score and pixel fraction.
	 * 
	 * @return the colors
	 */
	public ColorInfo[] getColors() {
		return colors;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DominantColorsAnnotation [colors=" + Arrays.toString(colors)
				+ "]";
	}

}
