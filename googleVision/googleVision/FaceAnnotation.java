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
 * A face annotation object contains the results of face detection. As defined
 * by https://cloud.google.com/vision/reference/rest/v1/images/annotate#faceannotation
 * 
 * @author lauriewhite
 * @version 3 October 2016
 * 
 */
public class FaceAnnotation {
	private BoundingPoly boundingPoly; // The bounding polygon around the face.
										// The coordinates of the bounding box
										// are in the original image's scale, as
										// returned in ImageParams. The bounding
										// box is computed to "frame" the face
										// in accordance with human
										// expectations. It is based on the
										// landmarker results. Note that one or
										// more x and/or y coordinates may not
										// be generated in the BoundingPoly (the
										// polygon will be unbounded) if only a
										// partial face appears in the image to
										// be annotated.
	private BoundingPoly fdBoundingPoly;// This bounding polygon is tighter than
										// the previous boundingPoly, and
										// encloses only the skin part of the
										// face. Typically, it is used to
										// eliminate the face from any image
										// analysis that detects the
										// "amount of skin" visible in an image.
										// It is not based on the landmarker
										// results, only on the initial face
										// detection, hence the fd (face
										// detection) prefix.
	private Landmark[] landmarks; // Detected face landmarks.
	private double rollAngle; // Roll angle. Indicates the amount of
								// clockwise/anti-clockwise rotation of the face
								// relative to the image vertical, about the
								// axis perpendicular to the face. Range
								// [-180,180].
	private double panAngle; // Yaw angle. Indicates the leftward/rightward
								// angle that the face is pointing, relative to
								// the vertical plane perpendicular to the
								// image. Range [-180,180].
	private double tiltAngle; // Pitch angle. Indicates the upwards/downwards
								// angle that the face is pointing relative to
								// the image's horizontal plane. Range
								// [-180,180].
	private double detectionConfidence; // Detection confidence. Range [0, 1].
	private double landmarkingConfidence;// Face landmarking confidence. Range
											// [0, 1].

	private String joyLikelihood; // Joy likelihood.
	private String sorrowLikelihood; // Sorrow likelihood.
	private String angerLikelihood; // Anger likelihood.
	private String surpriseLikelihood; // Surprise likelihood.
	private String underExposedLikelihood; // Under-exposed likelihood.
	private String blurredLikelihood; // Blurred likelihood.
	private String headwearLikelihood; // Headwear likelihood.

	/**
	 * Return the bounding polygon around the face. The coordinates of the
	 * bounding box are in the original image's scale, as returned in
	 * ImageParams. The bounding box is computed to "frame" the face in
	 * accordance with human expectations. It is based on the landmarker
	 * results. Note that one or more x and/or y coordinates may not be
	 * generated in the BoundingPoly (the polygon will be unbounded) if only a
	 * partial face appears in the image to be annotated.
	 * 
	 * @return The bounding polygon if there is one, null otherwise
	 */
	public BoundingPoly getBoundingPoly() {
		return boundingPoly;
	}

	/**
	 * Return the face detection bounding polygon. This bounding polygon is
	 * tighter than the previous boundingPoly, and encloses only the skin part
	 * of the face. Typically, it is used to eliminate the face from any image
	 * analysis that detects the "amount of skin" visible in an image. It is not
	 * based on the landmarker results, only on the initial face detection, hence
	 * the fd (face detection) prefix.
	 * 
	 * @return the face detection bounding polygon.
	 */
	public BoundingPoly getfdBoundingPoly() {
		return fdBoundingPoly;
	}

	/**
	 * Return detected face landmarks.
	 * 
	 * @return Detected face landmarks.
	 */
	public Landmark[] getLandmarks() {
		return landmarks;
	}

	/**
	 * Return the roll angle. Indicates the amount of clockwise/anti-clockwise
	 * rotation of the face relative to the image vertical, about the axis
	 * perpendicular to the face. Range [-180,180].
	 * 
	 * @return the roll angle.
	 */
	public double getRollAngle() {
		return rollAngle;
	}

	/**
	 * Return the yaw angle. Indicates the leftward/rightward angle that the
	 * face is pointing, relative to the vertical plane perpendicular to the
	 * image. Range [-180,180].
	 * 
	 * @return Yaw angle.
	 */
	public double getPanAngle() {
		return panAngle;
	}

	/**
	 * Return the pitch angle. Indicates the upwards/downwards angle that the
	 * face is pointing relative to the image's horizontal plane. Range
	 * [-180,180].
	 * 
	 * @return Pitch angle.
	 */
	public double getTiltAngle() {
		return tiltAngle;
	}

	/**
	 * Return the detection confidence. Range [0, 1].
	 * 
	 * @return detectionConfidence.
	 */
	public double getDetectionConfidence() {
		return detectionConfidence;
	}

	/**
	 * Return the face landmarking confidence. Range [0, 1].
	 * 
	 * @return landmarkingConfidence.
	 */
	public double landmarkingConfidence() {
		return landmarkingConfidence;
	}

	/**
	 * Return the joy likelihood.
	 * 
	 * @return joy likelihood
	 */
	public String getJoyLikelihood() {
		return joyLikelihood;
	}

	/**
	 * Return the sorrow likelihood.
	 * 
	 * @return sorrow likelihood.
	 */
	public String getSorrowLikelihood() {
		return sorrowLikelihood;
	}

	/**
	 * Return the anger likelihood.
	 * 
	 * @return anger likelihood.
	 */
	public String getAngerLikelihood() {
		return angerLikelihood;
	}

	/**
	 * Return the surprise likelihood.
	 * 
	 * @return surprise likelihood.
	 */
	public String getSurpriseLikelihood() {
		return surpriseLikelihood;
	}

	/**
	 * Return the under-exposed likelihood.
	 * 
	 * @return under-exposed likelihood.
	 */
	public String getUnderExposedLikelihood() {
		return underExposedLikelihood;
	}

	/**
	 * Return the blurred likelihood.
	 * 
	 * @return blurred likelihood.
	 */
	public String getBlurredLikelihood() {
		return blurredLikelihood;
	}

	/**
	 * Return the headwear likelihood.
	 * 
	 * @return headwear likelihood.
	 */
	public String getHeadwearLikelihood() {
		return headwearLikelihood;
	}

	// ToString
	public String toString() {
		String result = "";

		if (boundingPoly != null) {
			result += boundingPoly.toString();
		}

		if (fdBoundingPoly != null) {
			result += fdBoundingPoly.toString();
		}

		if (landmarks != null) {
			result += "Landmarks: ";
			for (Landmark l : landmarks) {
				result += l.toString() + " ";
			}
		}

		result += "Roll Angle: " + rollAngle;
		result += "Pan Angle: " + panAngle;
		result += "Tilt Angle: " + tiltAngle;
		result += "Detection confidence: " + detectionConfidence;
		result += "Landmarking confidence: " + landmarkingConfidence;

		if (joyLikelihood != null) {
			result += "Joy likelihood: " + joyLikelihood;
		}
		if (sorrowLikelihood != null) {
			result += "Sorrow likelihood: " + sorrowLikelihood;
		}
		if (angerLikelihood != null) {
			result += "Anger likelihood: " + angerLikelihood;
		}
		if (surpriseLikelihood != null) {
			result += "Surprise likelihood: " + surpriseLikelihood;
		}
		if (underExposedLikelihood != null) {
			result += "Under-exposed likelihood: " + underExposedLikelihood;
		}
		if (blurredLikelihood != null) {
			result += "Blurred likelihood: " + blurredLikelihood;
		}
		if (headwearLikelihood != null) {
			result += "Headwear likelihood: " + headwearLikelihood;
		}

		return result;

	}
}
