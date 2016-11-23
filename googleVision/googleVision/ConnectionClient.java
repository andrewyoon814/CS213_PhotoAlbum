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
import java.io.IOException;


public class ConnectionClient {

	
	public static void main(String[] args) throws IOException {
		//  Create a connection with your key
		VisionAPIConnection vision = new VisionAPIConnection("your key here");
		
		//  Set the type of analysis to do and the file to analyze
		vision.addType("LABEL_DETECTION");
		vision.addType("FACE_DETECTION"); // To get sentiment analysis
		vision.setFile("gs://vision-sample-images/man.jpg");
		
		//  Call the API
		VisionAPIResponse json = vision.call();
		//System.out.println(json);
		
		//  Use the result
		if (json == null) {
			return;   //  There was a problem with the retrieval.
		}
		
		AnnotateImageResponse[] imageInfo = json.getResponses();
		
		if (imageInfo == null || imageInfo.length == 0) {
			return;  //  No info was returned
		}
		
		FaceAnnotation[] faceInfo = imageInfo[0].getFaceAnnotations();
		if (faceInfo == null) {
			return;  //  No faces detected or FACE_DETECTION not requested
		}
		
		for (FaceAnnotation oneFace: faceInfo) {
			System.out.println("Is this face surprised? " + oneFace.getSurpriseLikelihood());
			System.out.println("Is this face joyful? " + oneFace.getJoyLikelihood());
			System.out.println("Is this face angry? " + oneFace.getAngerLikelihood());
			System.out.println("Is this face sorrowful? " + oneFace.getSorrowLikelihood());
		}
	}

}
