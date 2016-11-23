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
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A class to simplify the process of using the Cloud Vision API. It will connect to
 * the Cloud Vision API, make calls of the API, and return the result of those
 * calls.
 * 
 * To use this class, an API key must be provided in the constructor call. At
 * least one of the possible types of analysis ("TYPE_UNSPECIFIED",
 * "FACE_DETECTION", "LANDMARK_DETECTION", "LOGO_DETECTION", "LABEL_DETECTION",
 * "TEXT_DETECTION", "SAFE_SEARCH_DETECTION", "IMAGE_PROPERTIES") must be
 * specified using the addType method, and an image (stored in GCP Cloud
 * Storage) must be specified with the setFile method.
 * 
 * For example, the following will perform
 * 
 * <pre>
 * VisionAPIConnection vision = new VisionAPIConnection(
 * 		&quot;your key here&quot;);
 * 
 * // Set the type of analysis to do and the file to analyze
 * vision.addType(&quot;LABEL_DETECTION&quot;);
 * vision.addType(&quot;FACE_DETECTION&quot;); // To get sentiment analysis
 * vision.setFile(&quot;gs://vision-sample-images/man.jpg&quot;);
 * 
 * // Call the API
 * VisionAPIResponse json = vision.call();
 * </pre>
 * 
 * @author lauriewhite
 * @version 3 October 2016
 * 
 */
public class VisionAPIConnection {
	private static final String TARGET_URL = "https://vision.googleapis.com/v1/images:annotate?key=";

	private String apiKey;
	private List<String> types;
	private String imageLocation;
	private List<String> validTypes;

	/**
	 * Create a connection with the given API key. No validation of the key will
	 * occur until the API is called.
	 * 
	 * @param apiKey
	 *            a key created using the credentials of a GCP Project
	 */
	public VisionAPIConnection(String apiKey) {
		this.apiKey = apiKey;
		types = new ArrayList<String>();
		validTypes = new ArrayList<String>(Arrays.asList(new String[] {
				"TYPE_UNSPECIFIED", "FACE_DETECTION", "LANDMARK_DETECTION",
				"LOGO_DETECTION", "LABEL_DETECTION", "TEXT_DETECTION",
				"SAFE_SEARCH_DETECTION", "IMAGE_PROPERTIES" }));
		imageLocation = "";
	}

	/**
	 * Remove all types of analysis from the current connection.
	 */
	public void resetTypes() {
		types.clear();
	}

	/**
	 * Add a type of analysis to the current connection. Only the types
	 * ("TYPE_UNSPECIFIED", "FACE_DETECTION", "LANDMARK_DETECTION",
	 * "LOGO_DETECTION", "LABEL_DETECTION", "TEXT_DETECTION",
	 * "SAFE_SEARCH_DETECTION", "IMAGE_PROPERTIES") ar evalid; any others will
	 * be ignored.
	 * 
	 * @param newType
	 *            the type of analysis to perform
	 */
	public void addType(String newType) {
		if (validTypes.contains(newType)) {
			types.add(newType);
		}
	}

	/**
	 * Provide the name of the file to be analyzed. Files must reside on GCP
	 * storage and their names provided in the form: <tt>"gs://</tt>
	 * <em>bucketname</em><tt>/</tt><em>filename</em>"</tt>
	 * 
	 * @param file
	 */
	public void setFile(String file) {
		imageLocation = file;
	}

	/**
	 * Call the Cloud Vision API with the parameters provided. If all parameters
	 * are not provided or there's a fault in the call, null will be returned.
	 * 
	 * @return the VisionAPIResponse representing the information received.
	 * @throws IOException
	 */
	public VisionAPIResponse call() throws IOException {
		// Check if all necessary fields are filled in
		if (types.size() == 0) {
			return null;
		}

		if (imageLocation == null) {
			return null;
		}

		// Build the HTTP call
		URL serverUrl = new URL(TARGET_URL + apiKey);
		URLConnection urlConnection = serverUrl.openConnection();
		HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;

		httpConnection.setDoOutput(true);
		httpConnection.setRequestMethod("POST");

		httpConnection.setRequestProperty("Content-Type", "application/json");

		BufferedWriter httpRequestBodyWriter = new BufferedWriter(
				new OutputStreamWriter(httpConnection.getOutputStream()));

		httpRequestBodyWriter.write("{\"requests\":  [{ \"features\":  ");

		// Build the list of requested analyses
		httpRequestBodyWriter.write("[ ");
		boolean first = true;
		for (String currType : types) {
			if (first) {
				first = false;
			} else {
				httpRequestBodyWriter.write(", ");
			}
			httpRequestBodyWriter.write("{\"type\": \"" + currType + "\" }");
		}

		httpRequestBodyWriter
				.write("], \"image\": {\"source\": { \"gcsImageUri\": \"");

		// Add the image address
		httpRequestBodyWriter.write(imageLocation);
		httpRequestBodyWriter.write("\"}}}]}");
		httpRequestBodyWriter.close();

		String response = httpConnection.getResponseMessage();
		// We could throw an error here if the response is not okay.
		if (httpConnection.getInputStream() == null) {
			return null;
		}

		// Now that we have a response, parse it.
		Scanner httpResponseScanner = new Scanner(
				httpConnection.getInputStream());
		String resp = "";
		while (httpResponseScanner.hasNext()) {
			String line = httpResponseScanner.nextLine();
			resp += line;
		}
		httpResponseScanner.close();

		// And convert the response to a formatted response
		Gson gson = new GsonBuilder().create();
		VisionAPIResponse json = (VisionAPIResponse) gson.fromJson(resp,
				VisionAPIResponse.class);
		
		return json;
	}

}
