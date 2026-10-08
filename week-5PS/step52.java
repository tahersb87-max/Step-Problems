public class step52 {

    static String classifyAccess(String modifier, String context) {
        if (modifier.equals("private"))
            return context.equals("SAME_CLASS") ? "ALLOWED" : "DENIED";

        if (modifier.equals("default"))
            return context.equals("SAME_CLASS") ||
                    context.equals("SAME_PACKAGE") ? "ALLOWED" : "DENIED";

        if (modifier.equals("protected")) {
            if (context.equals("SAME_CLASS") ||
                    context.equals("SAME_PACKAGE") ||
                    context.equals("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"))
                return "ALLOWED";
            return "DENIED";
        }

        if (modifier.equals("public"))
            return "ALLOWED";

        return "DENIED";
    }

    static String describeContext(String context) {
        String[] words = context.split("_");
        String result = "";

        for (String word : words) {
            result += word.substring(0, 1).toUpperCase()
                    + word.substring(1).toLowerCase() + " ";
        }

        return result.trim();
    }

    public static void main(String[] args) {
        System.out.println(
                classifyAccess("protected",
                        "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));

        System.out.println(
                classifyAccess("protected",
                        "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));

        System.out.println(
                describeContext(
                        "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
    }
}