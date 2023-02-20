import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegexSampleTest {
    @ParameterizedTest(name = "<REGEX = {0}>    <TEXT = {1}>    <MATCHES = {2}>")
    @CsvSource({
//            "foo, foo, 1", //<MATCHES_STR = foo>
//            "foo, foofoo, 2", //<foo|foo>
//            "foo, 123, 0", //<>
//            "., foo, 3",  //<f|o|o> . match any chars
//            "foo., foofoo, 1", //<foof>
//            "[abc], b, 1",  //<b> -> OR Class -> [...] matches any of the elements in the set (OR)
//            "[abc], cab, 3",  //<c|a|b> it'll match each element separately with no regard to the order,
//            "[bcr]at, bat cat rat, 3", //<bat|cat|rat>
//            "[^abc], g, 1",  //<g> -> NOR Class
//            "[^bcr]at, sat mat eat, 3", //<sat|mat|eat>
//            "[A-Z], Two Uppercase alphabets 34 overall, 2",  //<T|U> -> Range Class
//            "[a-z], Two Uppercase alphabets 34 overall, 26", //<w|o|p|p|e|r|c|a|s|e|a|l|p|h|a|b|e|t|s|o|v|e|r|a|l|l>
//            "[a-zA-Z], Two Uppercase alphabets 34 overall, 28", //<T|w|o|U|p|p|e|r|c|a|s|e|a|l|p|h|a|b|e|t|s|o|v|e|r|a|l|l>
//            "[a-zA-Z ], Two Uppercase alphabets 34 overall, 32", //<T|w|o| |U|p|p|e|r|c|a|s|e| |a|l|p|h|a|b|e|t|s| | |o|v|e|r|a|l|l>
//            "[1-5], Two Uppercase alphabets 34 overall, 2", //<3|4>
//            "3[0-5], Two Uppercase alphabets 34 overall, 1", //<34>
//            "[1-3[7-9]], 123456789, 6", //<1|2|3|7|8|9> -> Union Class
//            "[1-6&&[3-9]], 123456789, 4", //<3|4|5|6> -> Intersection Class
//            "[0-9&&[^2468]], 123456789, 5", //<|1|3|5|7|9> -> Subtraction Class
//            "\\d, 123, 3", //<1|2|3> -> digits: \d = [0-9]
//            "\\D, a6c, 2", //<a|c> -> non-digits: \D = [^0-9]
//            "\\s, a c, 1", //< > -> white space: \s
//            "\\S, a c, 2", //<a|c> -> non-white space: \S
//            "\\w, hi!, 2", //<h|i> -> word character: \w = [a-zA-Z_0-9]
//            "\\W, hi!, 1", //<!> -> non-word character: \W = [^a-zA-Z_0-9]
//            "\\a?, hi, 3", //<||> -> \a? = \a{0,1}
//            "\\a*, hi, 3", //<||> -> \a* = \a{0,}
//            "\\a+, hi, 0", //<> -> \a+ = \a{1,}
//            "a{3}, aaaaaa, 2", //<aaa|aaa>
//            "a{3}, aa, 0",
//            "(\\d\\d), 12, 1", //<12> -> Capturing Groups
//            "(\\d\\d), 1212, 2", //<12|12>
//            "(\\d\\d)\\1, 1212, 1", //<1212> -> back referencing
//            "(\\d\\d)\\1\\1, 121212, 1", //<1212>
//            "(\\d\\d)(\\d\\d), 1212, 1", //<1212> -> repeat the regex
//            "^dog, dogs are friendly, 1", //<dog> -> Boundary Matchers -> required regex is true at the beginning of the text -> ^ at the beginning
//            "^dog, are dogs are friendly?, 0",
//            "dog$, Man's best friend is a dog, 1", //<dog> -> required regex is true at the end of the text -> $ at the end
//            "dog$, is a dog man's best friend?, 0",
//            "\\bdog\\b, a dog is friendly, 1", //<dog> -> required text is found at a word boundary -> text contain "dog"
//            "\\bdog\\b, dog is man's best friend, 1", //<dog> ->The empty string at the beginning of a line is also a word boundary
//            "\\bdog\\b, snoop dogg is a rapper, 0",
            "\\bdog\\B, snoop dogg is a rapper, 1", //<dog> -> Two-word characters appearing in a row doesn't mark a word boundary, but we can make it pass by changing the end of the regex to look for a non-word boundary:
            "(.*), this is a text, 2", //<this is a text|> -> text + end char
    })
    public void test(String regex, String text, int matches) {
        assertEquals(runTest(regex, text), matches);
//        assertEquals(runTest("\\a{0,1}", ""), 1);
//        assertEquals(runTest("\\a{0,}", ""), 1);
//        assertEquals(runTest("a{2,3}", "aaaa"), 1);
//        assertEquals(runTest("dog", "This is a Dog", Pattern.CASE_INSENSITIVE), 1); //<Dog>
//        assertEquals(runTest("dog$  #check end of text","This is a dog", Pattern.COMMENTS), 1); //<Dog>
//        assertEquals(runTest("(?x)dog$  #check end of text","This is a dog"), 1); //<Dog> -> = (?x) = Pattern.COMMENTS

        Pattern pattern = Pattern.compile("(.*)");
        Matcher matcher = pattern.matcher("this is a text" + System.getProperty("line.separator") + " continued on another line");
        matcher.find();
        assertEquals("this is a text", matcher.group(1));

        Pattern pattern1 = Pattern.compile("(.*)", Pattern.DOTALL);// or Pattern.compile("(?s)(.*)");
        Matcher matcher1 = pattern1.matcher("this is a text" + System.getProperty("line.separator") + " continued on another line");
        matcher1.find();
        assertEquals("this is a text" + System.getProperty("line.separator") + " continued on another line", matcher1.group(1));

    }

    public static int runTest(String regex, String text, int flags) {
        int matches = 0;
        String resultPrint = "";
        String matchStr = "";
        resultPrint += "<REGEX = " + regex + ">    <TEXT = " + text + ">    ";

        Pattern pattern = null;
        if (flags != -1) {
            pattern = Pattern.compile(regex, flags);
        } else {
            pattern = Pattern.compile(regex);
        }
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            matchStr += matcher.group() + "|";
            matches++;
        }

        if (matchStr.endsWith("|")) {
            matchStr = matchStr.substring(0, matchStr.lastIndexOf("|"));
        }
        resultPrint += "<MATCHES = " + matches + ">    <MATCHES_STR = " + matchStr + ">";
        System.out.println(resultPrint);

        return matches;
    }
    public static int runTest(String regex, String text) {
        return runTest(regex, text, -1);
    }
    //https://www.baeldung.com/regular-expressions-java
}
