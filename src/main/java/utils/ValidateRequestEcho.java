package utils;

import java.util.Arrays;

public class ValidateRequestEcho {
    private static final String STX = "\u0002"; // Código ASCII para STX
    private static final String ETX = "\u0003";
    private static final String[] validActions = {"96", "97", "98", "99"};
    public static boolean isValidRequest(String request) {
        if(request== null || request.isBlank() || request.length() < 26){
            return false;
        }
        
        if(!request.substring(0, 1).equals(STX) || !request.substring(request.length() - 1).equals(ETX)){
            return false;
        }
        
        String action = request.substring(1, 3);
        
        if( Arrays.stream(validActions).noneMatch(action::equals)){
            System.out.println("Invalid action");
            return false;
        }
        
        String id = request.substring(3, 11);
        String startId = id.substring(0, 2);
        if(!startId.equals("TL") && !startId.equals("DR")){
            System.out.println("Invalid id");
            return false;
        }
        
        String date = request.substring(11, 19);
        if(!date.matches("\\d{8}")){
            System.out.println("Invalid date");
            return false;
        }
        String hour = request.substring(19, 25);
        return hour.matches("\\d{6}");
    }
}
