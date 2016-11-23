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
 * A class that represents a detected entity feature in the response from a call
 * of the Cloud Vision API as defined by
 * https://cloud.google.com/vision/reference
 * /rest/v1/images/annotate#entityannotation
 * 
 * @author lauriewhite
 * @version 3 October 2016
 * 
 */

public class EntityAnnotation {
	private String mid; // Opaque entity ID. Some IDs might be available in
						// Knowledge Graph(KG). For more details on KG please
						// see: https://developers.google.com/knowledge-graph/
	private String locale; // The language code for the locale in which the
							// entity textual description (next field) is
							// expressed.
	private String description; // Entity textual description, expressed in its
								// locale language.
	private double score; // Overall score of the result. Range [0, 1].
	private double confidence; // The accuracy of the entity detection in an
								// image. For example, for an image containing
								// 'Eiffel Tower,' this field represents the
								// confidence that there is a tower in the query
								// image. Range [0, 1].
	private double topicality; // The relevancy of the ICA (Image Content
								// Annotation) label to the image. For example,
								// the relevancy of 'tower' to an image
								// containing 'Eiffel Tower' is likely higher
								// than an image containing a distant towering
								// building, though the confidence that there is
								// a tower may be the same. Range [0, 1].
	private BoundingPoly boundingPoly; // Image region to which this entity
										// belongs. Not filled currently for
										// LABEL_DETECTION features. For
										// TEXT_DETECTION (OCR), boundingPolys
										// are produced for the entire text
										// detected in an image region, followed
										// by boundingPolys for each word within
										// the detected text.
	private LocationInfo[] locations; // The location information for the
										// detected entity. Multiple
										// LocationInfo elements can be present
										// since one location may indicate the
										// location of the scene in the query
										// image, and another the location of
										// the place where the query image was
										// taken. Location information is
										// usually present for landmarks.
	private Property[] properties; // Some entities can have additional optional
									// Property fields. For example a different
									// kind of score or string that qualifies
									// the entity.

	/**
	 * Return the opaque entity ID. Some IDs might be available in Knowledge
	 * Graph(KG). For more details on KG please see:
	 * https://developers.google.com/knowledge-graph/
	 * 
	 * @return mid
	 */
	private String getMid() {
		return mid;
	}

	/**
	 * Return the language code for the locale in which the entity textual
	 * description (next field) is expressed.
	 * 
	 * @return locale
	 */
	private String getLocale() {
		return locale;
	}

	/**
	 * Entity textual description, expressed in its locale language.
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Overall score of the result. Range [0, 1].
	 * 
	 * @return score
	 */
	private double getScore() {
		return score;
	}

	/**
	 * The accuracy of the entity detection in an image. For example, for an
	 * image containing 'Eiffel Tower,' this field represents the confidence
	 * that there is a tower in the query image. Range [0, 1].
	 * 
	 * @return confidence
	 */
	private double getConfidence() {
		return confidence;
	}

	/**
	 * The relevancy of the ICA (Image Content Annotation) label to the image.
	 * For example, the relevancy of 'tower' to an image containing 'Eiffel
	 * Tower' is likely higher than an image containing a distant towering
	 * building, though the confidence that there is a tower may be the same.
	 * Range [0, 1].
	 * 
	 * @return topicality
	 */
	private double getTopicality() {
		return topicality;
	}

	/**
	 * Image region to which this entity belongs. Not filled currently for
	 * LABEL_DETECTION features. For TEXT_DETECTION (OCR), boundingPolys are
	 * produced for the entire text detected in an image region, followed by
	 * boundingPolys for each word within the detected text.
	 * 
	 * @return boundingPoly
	 */
	private BoundingPoly getBoundingPoly() {
		return boundingPoly;
	}

	/**
	 * The location information for the detected entity. Multiple LocationInfo
	 * elements can be present since one location may indicate the location of
	 * the scene in the query image, and another the location of the place where
	 * the query image was taken. Location information is usually present for
	 * landmarks.
	 * 
	 * @return locations
	 */
	private LocationInfo[] getLocations() {
		return locations;
	}

	/**
	 * Some entities can have additional optional Property fields. For example a
	 * different kind of score or string that qualifies the entity.
	 * 
	 * @return properties
	 */
	private Property[] getProperties() {
		return properties;
	}

	/**  Create a string with all fields with labels.
	 * @return a string representing the Entity
	 */
	public String toString() {
		String result = "Entity Annotation: ";
		if (this.mid != null) {
			result += "mid = " + this.mid + ", ";
		}
		if (this.locale != null) {
			result += "locale = " + this.locale + ", ";
		}
		if (this.description != null) {
			result += "description = " + this.description + ", ";
		}
		result += "score = " + this.score + ", ";
		result += "confidence = " + this.confidence+ ", ";
		result += "topicality = " + this.topicality+ ", ";
		if (this.boundingPoly != null) {
			result += "boundingPoly = " + this.boundingPoly + ", ";
		}
		if (this.locations != null) {
			result += "locations = ";
		    for (LocationInfo loc: locations) {
			   result += loc + ", ";
		    }
		}
		if (this.properties!= null) {
			result += "proerties = " + this.properties + ", ";
		}
		
		if (result.endsWith(", ")) {
			result = result.substring(0, result.length() - 2);
		}
		
		return result;
	}
}
