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
 * Set of features pertaining to the image, computed by various computer vision
 * methods over safe-search verticals (for example, adult, spoof, medical,
 * violence). As defined by
 * https://cloud.google.com/vision/reference/rest/v1/images/annotate#safesearchannotation
 * 
 * @author lauriewhite
 * @version 3 October 2016
 * 
 */
public class SafeSearchAnnotation {
	private String adult; // Represents the adult contents likelihood for the
							// image.
	private String spoof; // Spoof likelihood. The likelihood that an obvious
							// modification
							// was made to the image's canonical version to make
							// it appear
							// funny or offensive.
	private String medical; // Likelihood this is a medical image.
	private String violence; // Violence likelihood.

	/**
	 * Represents the adult contents likelihood for the image.
	 * 
	 * @return the adult contents likelihood
	 */
	public String getAdult() {
		return adult;
	}

	/**
	 * Spoof likelihood. The likelihood that an obvious modification was made to
	 * the image's canonical version to make it appear funny or offensive.
	 * 
	 * @return the spoof likelihood
	 */
	public String getSpoof() {
		return spoof;
	}

	/**
	 * Likelihood this is a medical image.
	 * 
	 * @return the medical likelihood
	 */
	public String getMedical() {
		return medical;
	}

	/**
	 * Violence likelihood.
	 * 
	 * @return the violence likelihood
	 */
	public String getViolence() {
		return violence;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SafeSearchAnnotation [adult=" + adult + ", spoof=" + spoof
				+ ", medical=" + medical + ", violence=" + violence + "]";
	}

}
