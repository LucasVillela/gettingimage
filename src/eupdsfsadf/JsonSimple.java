package eupdsfsadf;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JsonSimple {

	
	public static void main(String[] args) {
    		
	
		HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 

	    try {
	        HttpPost request = new HttpPost("http://localhost:8080/returnImage/");
	        StringEntity params =new StringEntity("{\"url\":\"https://www.theguardian.com/world/2016/sep/05/philippines-president-rodrigo-duterte-barack-obama-son-whore\"} ");
	        request.addHeader("content-type", "application/json");
	        
	        request.setEntity(params);
	        //System.out.println(request.toString());
	        HttpResponse response = httpClient.execute(request);
	        String json = EntityUtils.toString(response.getEntity());
	        JsonParser jsonParser = new JsonParser();
	        JsonElement element = jsonParser.parse(json);
	        JsonElement aux = element.getAsJsonObject().get("img_url");
	        
	        //System.out.println(aux);
	        
	        
	        
	        BufferedImage img = ImageIO.read(new URL(aux.getAsString()));
	        
	        int type = img.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : img.getType();
	        
	        BufferedImage img2 = resizeImage(img, type, 500, 200);
	        //img.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
	        File outputfile = new File("/home/lucas/image.jpg");
	        ImageIO.write(img2, "jpg", outputfile);

	        // handle response here...
	    }catch (Exception ex) {
	        // handle exception here
	    } finally {
	        httpClient.getConnectionManager().shutdown(); //Deprecated
	    }
    	
    	
    }
	
	private static BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT) {
	    BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
	    Graphics2D g = resizedImage.createGraphics();
	    g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
	    g.dispose();

	    return resizedImage;
	}
}

