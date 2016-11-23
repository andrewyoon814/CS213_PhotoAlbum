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
 * An object representing a latitude/longitude pair. This is expressed as a pair
 * of doubles representing degrees latitude and degrees longitude. Unless
 * specified otherwise, this must conform to the WGS84 standard. Values must be
 * within normalized ranges. As defined by
 * https://cloud.google.com/vision/reference/rest/v1/images/annotate#latlng
 * 
 * @author lauriewhite
 * @version 3 October 2016
 * 
 */
public class LatLng {
	private double latitude; // The latitude in degrees. It must be in the range
								// [-90.0, +90.0].
	private double longitude; // The longitude in degrees. It must be in the
								// range [-180.0, +180.0].

	/**
	 * The latitude in degrees. It must be in the range [-90.0, +90.0].
	 * 
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * The longitude in degrees. It must be in the range [-180.0, +180.0].
	 * 
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LatLng [latitude=" + latitude + ", longitude=" + longitude
				+ "]";
	}
}
