import java.util.LinkedHashMap;
import java.util.Map;

public class InputService{

    public String[] extract(String raw){
        System.out.println("INPUT: " + raw);

        Map<String,String> kv = new LinkedHashMap<>();
        String[] parts = raw.split(";");
        for (String p : parts) {
            String[] t = p.split("=", 2);
            if (t.length == 2) kv.put(t[0].trim(), t[1].trim());
        }

        String name = kv.getOrDefault("name", "");
        String email = kv.getOrDefault("email", "");
        String phone = kv.getOrDefault("phone", "");
        String program = kv.getOrDefault("program", "");
        String[] ans = {name,email,phone,program};
        return ans;
    }
}