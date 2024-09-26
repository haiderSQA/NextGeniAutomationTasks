package Task2Api;

public class Payload {
    public static String addItem() {
        return "{\n" +
                "    \"firstname\": \"Haider\",\n" +
                "    \"lastname\": \"Hasnain\",\n" +
                "    \"totalprice\": 35000,\n" +
                "    \"depositpaid\": true,\n" +
                "    \"bookingdates\": {\n" +
                "        \"checkin\": \"2022-01-01\",\n" +
                "        \"checkout\": \"2024-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\": \"testAdd\"\n" +
                "}";
    }

    public static String addBookingwithemptydata() {
        return "{\n" +
            " \"firstname\" : \"\",\n" +
                    " \"totalprice\" : -1,\n" +
                    " \"depositpaid\" : false,\n" +
                    " \"bookingdates\" : {\n" +
                    " \"checkin\" : \"\",\n" +
                    " \"checkout\" : \"\"\n" +
                    " },\n" +
                    " \"additionalneeds\" : \"\"\n" +
                    "}";
    }
}