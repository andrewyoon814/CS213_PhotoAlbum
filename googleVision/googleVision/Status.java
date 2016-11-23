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
 * The Status type defines a logical error model that is suitable for different
 * programming environments, including REST APIs and RPC APIs. As defined by
 * https://cloud.google.com/vision/reference/rest/v1/images/annotate#status .
 * 
 * @author lauriewhite
 * @version 3 October 2016
 * 
 */
public class Status {
	private int number; // The status code, which should be an enum value of
						// google.rpc.Code.
	private String message; // A developer-facing error message, which should be
							// in English. Any user-facing error message should
							// be localized and sent in the
							// google.rpc.Status.details field, or localized by
							// the client.

	/**
	 * The status code, which should be an enum value of google.rpc.Code.
	 * 
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * A developer-facing error message, which should be in English. Any
	 * user-facing error message should be localized and sent in the
	 * google.rpc.Status.details field, or localized by the client.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Status [number=" + number + ", message=" + message + "]";
	}

}
