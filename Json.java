import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//Example of json
//javac -cp ".;./jars/gson-2.8.4.jar" Json.java
//java -cp ".;./jars/gson-2.8.4.jar" Json
public class Json{
	public static void main(String[] args){
		Gson gson = new GsonBuilder().create();
        gson.toJson("Hello", System.out);
        gson.toJson(123, System.out);
	}
}