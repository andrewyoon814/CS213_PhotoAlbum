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
 * A class that represents the response from a call of the Cloud Vision API as
 * defined by https://cloud.google.com/vision/reference/rest/v1/images/annotate#
 * annotateimageresponse
 * 
 * @version 3 October 2016
 */
public class AnnotateImageResponse {
	private FaceAnnotation[] faceAnnotations; // If present, face detection
												// completed successfully.
	private EntityAnnotation[] landmarkAnnotations; // If present, landmark
													// detection completed
													// successfully.
	private EntityAnnotation[] logoAnnotations; // If present, logo detection
												// completed successfully.
	private EntityAnnotation[] labelAnnotations; // If present, label detection
													// completed successfully.
	private EntityAnnotation[] textAnnotations; // If present, text (OCR)
												// detection completed
												// successfully.
	private SafeSearchAnnotation safeSearchAnnotation; // If present,
														// safe-search
														// annotation completed
														// successfully.
	private ImageProperties imagePropertyAnnotation; // If present, image
														// properties were
														// extracted
														// successfully.
	private Status error; // If set, represents the error message for the
							// operation. Note that filled-in mage annotations
							// are guaranteed to be correct, even when error is
							// non-empty.

	/**
	 * If present, face detection completed successfully.
	 * 
	 * @return the faceAnnotations
	 */
	public FaceAnnotation[] getFaceAnnotations() {
		return faceAnnotations;
	}

	/**
	 * If present, landmark detection completed successfully.
	 * 
	 * @return the landmarkAnnotations
	 */
	public EntityAnnotation[] getLandmarkAnnotations() {
		return landmarkAnnotations;
	}

	/**
	 * If present, logo detection completed successfully.
	 * 
	 * @return the logoAnnotations
	 */
	public EntityAnnotation[] getLogoAnnotations() {
		return logoAnnotations;
	}

	/**
	 * If present, label detection completed successfully.
	 * 
	 * @return the labelAnnotations
	 */
	public EntityAnnotation[] getLabelAnnotations() {
		return labelAnnotations;
	}

	/**
	 * If present, text (OCR) detection completed successfully.
	 * 
	 * @return the textAnnotations
	 */
	public EntityAnnotation[] getTextAnnotations() {
		return textAnnotations;
	}

	/**
	 * If present, safe-search annotation completed successfully.
	 * 
	 * @return the safeSearchAnnotation
	 */
	public SafeSearchAnnotation getSafeSearchAnnotation() {
		return safeSearchAnnotation;
	}

	/**
	 * If present, image properties were extracted successfully.
	 * 
	 * @return the imagePropertyAnnotation
	 */
	public ImageProperties getImagePropertyAnnotation() {
		return imagePropertyAnnotation;
	}

	/**
	 * f set, represents the error message for the operation. Note that
	 * filled-in mage annotations are guaranteed to be correct, even when error
	 * is non-empty.
	 * 
	 * @return the error
	 */
	public Status getError() {
		return error;
	}

	public String toString() {
		String result = "";
		if (this.faceAnnotations != null) {
			result += "Face Annotations: ";
			for (FaceAnnotation fa : this.faceAnnotations) {
				result += fa.toString() + ", ";
			}
		}
		if (landmarkAnnotations != null) {
			result += "Landmark Annotations: ";
			for (EntityAnnotation ea : landmarkAnnotations) {
				result += ea.toString() + ", ";
			}
		}
		if (logoAnnotations != null) {
			result += "Logo Annotations: ";
			for (EntityAnnotation ea : logoAnnotations) {
				result += ea.toString() + ", ";
			}
		}
		if (labelAnnotations != null) {
			result += "Label Annotations: ";
			for (EntityAnnotation ea : labelAnnotations) {
				result += ea.toString() + ", ";
			}
		}
		if (textAnnotations != null) {
			result += "Text Annotations: ";
			for (EntityAnnotation ea : textAnnotations) {
				result += ea.toString() + ", ";
			}
		}
		if (safeSearchAnnotation != null) {
			result += safeSearchAnnotation.toString() + ", ";
		}
		if (imagePropertyAnnotation != null) {
			result += imagePropertyAnnotation.toString() + ", ";
		}
		/*
		 * if (result.endsWith(", ")) { result = result.substring(0,
		 * result.length() -2); } result += ", ";
		 */
		return result;
	}
}
