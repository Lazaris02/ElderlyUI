package com.example.elderlyui;

public class WeatherInfo {
    /*A data class for all the possible weather condition names, codes , images*/

    private  static final String[] names = {"Καθαρός", "Συννεφιά", "Συννεφιά", "Συννεφιά", "Ομίχλη", "Ομίχλη",
            "Φωτεινός", "Φωτεινός", "Φωτεινός", "Χιονόνερο", "Χιονόνερο", "Βροχή", "Βροχή", "Βροχή"
            , "Χιονόνερο", "Χιονόνερο", "Χιόνι", "Χιόνι", "Χιόνι", "Χιόνι"
            , "Βροχή", "Βροχή", "Βροχή", "Χιόνι", "Χιόνι",
            "Καταιγίδα", "Καταιγίδα με Χαλάζι", "Καταιγίδα με Χαλάζι"};
    private  static final String[] codes = {"0", "1", "2",
            "3", "45", "48", "51", "53",
            "55", "56", "57", "61", "63",
            "65", "66", "67", "71", "73", "75", "77",
            "80", "81", "82", "85", "86", "95", "96", "99"};

    private static final String[] imageIds = {"cloudy","rainy","sunny","snowy","storm","moon","fog"};

    public static String decideWeatherIcon(String name,Integer isDay){

        if(name.equals("Συνεφειά")){return imageIds[0];}
        if(name.equals("Ομίχλη")){return imageIds[6];}
        if(name.equals("Βροχή")){return imageIds[1];}
        if(name.equals("Χιονόνερο") || name.equals("Χιόνι")){return imageIds[3];}
        if(name.equals("Καταιγίδα") || name.equals("Καταιγίδα με Χαλάζι")){return imageIds[4];}
        if(name.equals("Καθαρός") && isDay > 0){return imageIds[2];}
        if(name.equals("Καθαρός")){return  imageIds[5];}

        return imageIds[0];
    }



    public static String[] getNames(){return names;}
    public static String[] getCodes(){return codes;}
    public static String[] getImageIds(){return imageIds;}

}
