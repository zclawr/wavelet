import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;

public class SearchEngine implements URLHandler{
    static SearchEngine singleton;
    static HashSet<String> addedStrings;
    @Override
    public String handleRequest(URI url) {
        if(url.getPath().equals("/")){
            if(addedStrings.size() == 0){
                return "Nothing to search!";
            }
            String all = "";
            for(String s : addedStrings){
                all += s + "\n";
            }
            return all;
        }
        else if(url.getPath().equals("/add") && url.getQuery().split("=")[0].equals("s")){
            String toAdd = url.getQuery().split("=")[1];
            addedStrings.add(toAdd);
            return "Added " + toAdd; 
        }
        else if(url.getPath().equals("/search") && url.getQuery().split("=")[0].equals("s")){
            String search = url.getQuery().split("=")[1];
            String result = "Searched for " + search + ":\n";
            String matches = "";
            int found= 0;
            for(String s : addedStrings){
                if(s.contains(search)){
                    matches += s + "\n";
                    found++;
                }
            }
            if(found == 0)
                result += "No matching strings found";
            else if(found == 1)
                result += "Found " + found + " match:";
            else 
                result += "Found " + found + " matches:";
            return result + "\n\n" + matches;
        }
        return "404 Error: Path not found";
    }
    public SearchEngine(){
        singleton = this;
        addedStrings = new HashSet();
    }
    public static void main(String[] args){
        try{
            Server.start(Integer.parseInt(args[0]), new SearchEngine());
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
