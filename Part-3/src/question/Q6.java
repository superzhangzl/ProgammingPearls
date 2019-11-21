package question;

/**
 * @author zzl
 */
public class Q6 {

    public static final String TEMPLATE = "" +
            "Dear %s: \n" +
            "How ar you today~ \n" +
            "There is something that %s\n" +
            "\n" +
            "                               %s";

    public static void main(String[] args) {
        System.out.println(getEmailFormTemplate("hd", "i miss you", "zzl"));
    }

    public static String getEmailFormTemplate(String receiver, String content, String sender) {
        return String.format(TEMPLATE, receiver, content, sender);

    }
}
