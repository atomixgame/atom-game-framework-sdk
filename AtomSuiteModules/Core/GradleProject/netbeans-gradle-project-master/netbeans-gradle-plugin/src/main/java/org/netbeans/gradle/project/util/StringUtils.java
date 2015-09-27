package org.netbeans.gradle.project.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import org.jtrim.utils.ExceptionHelper;
import org.openide.filesystems.FileUtil;

public final class StringUtils {
    private static final String HEX_TABLE = "0123456789abcdef";
    private static final String SEPARATORS = ",./?;:'\"\\";

    public static String byteArrayToHex(byte[] array) {
        StringBuilder result = new StringBuilder(array.length * 2);
        for (byte value: array) {
            result.append(HEX_TABLE.charAt(((int)value & 0xF0) >>> 4));
            result.append(HEX_TABLE.charAt((int)value & 0x0F));
        }
        return result.toString();
    }

    public static String emptyForNull(String str) {
        return str != null ? str : "";
    }

    public static String stripSeperatorsFromEnd(String str) {
        for (int i = str.length() - 1; i >= 0; i--) {
            if (SEPARATORS.indexOf(str.charAt(i)) < 0) {
                return str.substring(0, i + 1);
            }
        }
        return "";
    }

    public static String[] splitText(String text, String delimiters) {
        StringTokenizer tokenizer = new StringTokenizer(text, delimiters);
        List<String> result = new LinkedList<>();
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (!token.isEmpty()) {
                result.add(token);
            }
        }

        return result.toArray(new String[result.size()]);
    }

    public static String[] splitLines(String text) {
        return splitText(text, "\n\r");
    }

    public static List<String> toLines(String output) {
        try {
            return toLinesUnsafe(output);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static List<String> toLinesUnsafe(String output) throws IOException {
        ExceptionHelper.checkNotNullArgument(output, "output");

        List<String> lines = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new StringReader(output));
        String line = reader.readLine();
        while (line != null) {
            lines.add(line);
            line = reader.readLine();
        }
        return lines;
    }

    public static String[] splitBySpaces(String text) {
        return splitText(text, " \t\n\r\f");
    }

    public static String getTopMostClassName(String className) {
        ExceptionHelper.checkNotNullArgument(className, "className");

        int nestedClassSeparator = className.indexOf('$');
        return nestedClassSeparator >= 0
                ? className.substring(0, nestedClassSeparator)
                : className;
    }

    public static String getResourceAsString(String resourcePath, Charset encoding) throws IOException {
        ClassLoader classLoader = StringUtils.class.getClassLoader();
        if (classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }

        try (InputStream resource = classLoader.getResourceAsStream(resourcePath);
                Reader reader = new InputStreamReader(resource, encoding)) {

            int bufferSize = 4096;
            StringWriter writer = new StringWriter(bufferSize);

            char[] buffer = new char[bufferSize];
            for (int readCount = reader.read(buffer); readCount > 0; readCount = reader.read(buffer)) {
                writer.write(buffer, 0, readCount);
            }

            return writer.toString();
        }
    }

    public static void writeStringToFile(String content, Charset encoding, File file) throws IOException {
        File parent = file.getParentFile();
        if (parent != null) {
            FileUtil.createFolder(parent);
        }
        try (OutputStream output = new FileOutputStream(file)) {
            output.write(content.getBytes(encoding));
        }
    }

    public static int unescapedIndexOf(String str, int startIndex, char toFind) {
        if (toFind == '\\') {
            throw new IllegalArgumentException("Cannot look for the escape character.");
        }

        int i = startIndex;
        while (i < str.length()) {
            char ch = str.charAt(i);
            if (ch == toFind) {
                return i;
            }

            if (ch == '\\') i += 2;
            else i++;
        }
        return -1;
    }

    public static String unescapeString(String str) {
        StringBuilder result = new StringBuilder(str.length());
        int i = 0;
        while (i < str.length()) {
            char ch = str.charAt(i);
            if (ch == '\\') {
                if (i + 1 < str.length()) {
                    result.append(str.charAt(i + 1));
                    i += 2;
                    continue;
                }
            }

            result.append(ch);
            i++;
        }
        return result.toString();
    }

    public static String[] unescapedSplit(String str, char splitChar) {
        return unescapedSplit(str, splitChar, Integer.MAX_VALUE);
    }

    public static String[] unescapedSplit(String str, char splitChar, int maxSplitCount) {
        if (maxSplitCount <= 0) {
            throw new IllegalArgumentException("Illegal maxSplitCount: " + maxSplitCount);
        }

        if (maxSplitCount == 1) {
            return new String[]{str};
        }

        List<String> result = new LinkedList<>();

        int pos = 0;
        while (true) {
            int splitPos = unescapedIndexOf(str, pos, splitChar);
            if (splitPos < 0) {
                result.add(str.substring(pos, str.length()));
                break;
            }

            result.add(str.substring(pos, splitPos));
            if (result.size() == maxSplitCount - 1) {
                result.add(str.substring(splitPos + 1, str.length()));
                break;
            }

            pos = splitPos + 1;
        }

        return result.toArray(new String[result.size()]);
    }

    public static String capitalizeFirstCharacter(String str) {
        if (str.length() == 0) {
            return str;
        }

        char firstChar = str.charAt(0);
        char capitalized = Character.toUpperCase(firstChar);
        if (capitalized == firstChar) {
            return str;
        }

        StringBuilder result = new StringBuilder(str);
        result.setCharAt(0, capitalized);
        return result.toString();
    }

    private StringUtils() {
        throw new AssertionError();
    }
}
