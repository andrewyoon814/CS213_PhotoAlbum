package googleVision;
/**
 * A class that represents a ColorInfo in the response from a call of the Cloud
 * Vision API as defined by
 * https://cloud.google.com/vision/reference/rest/v1/images/annotate#colorinfo.
 * Color information consists of RGB channels, score and fraction of image the
 * color occupies in the image.
 * 
 * Represents a color in the RGBA color space. This representation is designed
 * for simplicity of conversion to/from color representations in various
 * languages over compactness; for example, the fields of this representation
 * can be trivially provided to the constructor of "java.awt.Color" in Java; it
 * can also be trivially provided to UIColor's "+colorWithRed:green:blue:alpha"
 * method in iOS; and, with just a little work, it can be easily formatted into
 * a CSS "rgba()" string in JavaScript, as well.
 * 
 * @author lauriewhite
 * @version 3 October 2016
 * 
 */
public class Color {
	private double red; // The amount of red in the color as a value in the
						// interval [0, 1].
	private double green; // The amount of green in the color as a value in the
							// interval [0, 1].
	private double blue; // The amount of blue in the color as a value in the
							// interval [0, 1].
	private double alpha; // The fraction of this color that should be applied
							// to the pixel.
							// That is, the final pixel color is defined by the
							// equation:
							// pixel color = alpha * (this color) + (1.0 -
							// alpha) * (background color)
							// This means that a value of 1.0 corresponds to a
							// solid color,
							// whereas a value of 0.0 corresponds to a
							// completely transparent color.
	
	/**
	 * @return the red
	 */
	public double getRed() {
		return red;
	}
	
	/**
	 * @return the green
	 */
	public double getGreen() {
		return green;
	}
	
	/**
	 * @return the blue
	 */
	public double getBlue() {
		return blue;
	}
	
	/**
	 * @return the alpha
	 */
	public double getAlpha() {
		return alpha;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Color [red=" + red + ", green=" + green + ", blue=" + blue
				+ ", alpha=" + alpha + "]";
	}

	
}
