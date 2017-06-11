package finalExample;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

public class Example {
	
	public static void runExampale() throws IOException {

		
		File f = createFile("pic1.jpg");
		String url= uploadToS3(f, "sofiamdor","pic1.jpg");
		String[] s = LocationService.myLocation();
		String temperature = WeatherService.getWeather(s[0]);
		SendSMS(url + "\nThe temperature is: " + temperature,"+972544452505");		
	}
	
	static void SendSMS(String message, String phoneNumber) {
		 AmazonSNSClient snsClient = new AmazonSNSClient();
	        Map<String, MessageAttributeValue> smsAttributes = 
	                new HashMap<String, MessageAttributeValue>();
	        //<set SMS attributes>
	        smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
	        .withStringValue("mySenderID")
	        .withDataType("String"));
	        sendSMSMessage(snsClient, message, phoneNumber, smsAttributes);
		
	}
	
	public static void sendSMSMessage(AmazonSNSClient snsClient, String message, 
			String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) {
	        PublishResult result = snsClient.publish(new PublishRequest()
	                        .withMessage(message)
	                        .withPhoneNumber(phoneNumber)
	                        .withMessageAttributes(smsAttributes));
	        System.out.println(result); // Prints the message ID.
	}

	static String uploadToS3(File f, String bucketName, String key) {
		AmazonS3 s3 = new AmazonS3Client();
        Region usWest2 = Region.getRegion(Regions.US_EAST_1);
        s3.setRegion(usWest2);
        
        System.out.println("===========================================");
        System.out.println("Getting Started with Amazon S3");
        System.out.println("===========================================\n");
        
        try {

            System.out.println("Uploading a new object to S3 from a file\n");
            PutObjectRequest putObj = new PutObjectRequest(bucketName, key,f);
            putObj.setCannedAcl(CannedAccessControlList.PublicRead);
            s3.putObject(putObj);
            System.out.println("Finish");
            return s3.getUrl(bucketName, key).toString();
             
         
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
        return s3.getUrl(bucketName, key).toString();
		
	}



	public static File createFile(String path){
		
		File f =  new File(path);		
		return f;
		
	}

}
