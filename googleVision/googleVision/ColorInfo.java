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
 * Color information consists of RGB channels, score and fraction of image the color occupies in the image.
 * As defined by https://cloud.google.com/vision/reference/rest/v1/images/annotate#colorinfo.
 * 
 * @author lauriewhite
 * @version 3 October 2016
 *
 */
public class ColorInfo {
	private Color color;           //  RGB components of the color.
	private double score;          //  Image-specific score for this color. Value in range [0, 1].
	private double pixelFraction;  //  Stores the fraction of pixels the color occupies in the image. Value in range [0, 1].

	/**
	 * RGB components of the color.
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Image-specific score for this color. Value in range [0, 1].
	 * @return the score
	 */
	public double getScore() {
		return score;
	}
	
	/**
	 * Stores the fraction of pixels the color occupies in the image. Value in range [0, 1].
	 * @return the pixelFraction
	 */
	public double getPixelFraction() {
		return pixelFraction;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ColorInfo [color=" + color + ", score=" + score
				+ ", pixelFraction=" + pixelFraction + "]";
	}
}
