package com.epam.mjc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     * 1. access modifier - optional, followed by space: ' '
     * 2. return type - followed by space: ' '
     * 3. method name
     * 4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     * accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     * private void log(String value)
     * Vector3 distort(int x, int y, int z, float magnitude)
     * public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */

    public MethodSignature parseFunction(String signatureString) {
        String[] parts = signatureString.split("\\(");
        String[] bySpace = parts[0].split(" ");
        String accessMod = null, returnType, methodName;
        List<MethodSignature.Argument> args = new ArrayList<>();
        String[] accessMods = {"private", "public", "protected"};

        StringSplitter stringSplitter = new StringSplitter();

        if (Arrays.asList(accessMods).contains(bySpace[0])) {
            accessMod = bySpace[0];
            returnType = bySpace[1];
            methodName = bySpace[2];
        } else {
            returnType = bySpace[0];
            methodName = bySpace[1];
        }

        if (!parts[1].isEmpty()) {
            String[] argString = stringSplitter.splitByDelimiters(parts[1].substring(0, parts[1].length() - 1), List.of(" ", ",")).toArray(new String[0]);
            for (int i = 0; i < argString.length; i += 2) {
                String type = argString[i], name = argString[i + 1];
                MethodSignature.Argument argument = new MethodSignature.Argument(type, name);
                args.add(argument);
            }
        }

        MethodSignature methodSignature;
        methodSignature = new MethodSignature(methodName, args);
        methodSignature.setAccessModifier(accessMod);
        methodSignature.setReturnType(returnType);

        return methodSignature;
    }
}
