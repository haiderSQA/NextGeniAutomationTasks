package Task2Api;

public class payload {

    public static String addDevicePayload()
    {
        return"{\n" +
                "    \"name\": \"Apple Max Pro 2TB\",\n" +
                "    \"data\": {\n" +
                "        \"year\": 2023,\n" +
                "        \"price\": 7999.99,\n" +
                "        \"CPU model\": \"Apple ARM A7\",\n" +
                "        \"Hard disk size\": \"1 TB\"\n" +
                "    }\n" +
                "}";
    }

}
