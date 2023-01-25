import java.io.IOException;
import java.net.URI;

public class StringServer implements URLHandler{
    String messages = "";
    public static void main(String[] args){
        try{
            Server.start(Integer.parseInt(args[0]), new StringServer());
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public String handleRequest(URI url) {
        if(url.getPath().equals("/add-message")){
            String[] queryParams = url.getQuery().split("=");
            if(queryParams[0].equals("s")){
                if(!messages.equals("")){
                    messages += "\n";
                }
                messages += queryParams[1];
            }
            return messages;
        }
        return "404 Path Not Found";
    }
}
