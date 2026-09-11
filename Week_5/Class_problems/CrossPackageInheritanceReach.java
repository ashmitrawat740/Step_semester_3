class AccessRuleEngine {

    static String classifyAccess(String fieldModifier,
                                 String accessorContext) {

        if (fieldModifier.equals("private")) {

            if (accessorContext.equals("SAME_CLASS"))
                return "ALLOWED";

            return "DENIED";
        }

        if (fieldModifier.equals("default")) {

            if (accessorContext.equals("SAME_CLASS") ||
                accessorContext.equals("SAME_PACKAGE"))
                return "ALLOWED";

            return "DENIED";
        }

        if (fieldModifier.equals("protected")) {

            if (accessorContext.equals("SAME_CLASS") ||
                accessorContext.equals("SAME_PACKAGE") ||
                accessorContext.equals(
                    "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"))
                return "ALLOWED";

            return "DENIED";
        }

        if (fieldModifier.equals("public"))
            return "ALLOWED";

        return "DENIED";
    }


    static String describeContext(String accessorContext) {

        String[] words = accessorContext.split("_");

        String result = "";

        for (int i = 0; i < words.length; i++) {

            String word = words[i];

            result += word.substring(0, 1).toUpperCase()
                    + word.substring(1).toLowerCase();

            if (i < words.length - 1)
                result += " ";
        }

        return result;
    }
}


public class Main {

    public static void main(String[] args) {

        System.out.println(
            AccessRuleEngine.classifyAccess(
                "protected",
                "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"
            )
        );

        System.out.println(
            AccessRuleEngine.classifyAccess(
                "protected",
                "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"
            )
        );

        System.out.println(
            AccessRuleEngine.describeContext(
                "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"
            )
        );
    }
}