package br.com.mcb.galaxyauto.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonS3Config {

	@Value("${aws.accessKeyId}")
	private String accessKeyId;

	@Value("${aws.secretKey}")
	private String secretKey;

	@Value("${aws.region}")
	private String region;

	
    public AWSCredentials credentials() {
    	System.out.println("Teste credentiasl");
    	System.out.println(accessKeyId);
    	System.out.println(secretKey);
    	System.out.println("--------------------------------------");
        AWSCredentials credentials = new BasicAWSCredentials(
        		accessKeyId,
        		secretKey
        );
        return credentials;
    }
    
	@Bean
	AmazonS3 s3Client() {
		return AmazonS3ClientBuilder.standard()
				.withRegion(Regions.US_EAST_1)
				.withCredentials(new AWSStaticCredentialsProvider(credentials()))
				.build();
	}

}