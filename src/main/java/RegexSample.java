import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RegexSample {
    public static void main(String[] args) {
//        System.out.println(Capability.findByList("0,1,1,0,1"));
//        System.out.println(Capability.findByList1("0,1,1,0,1"));

        List<String> list = Arrays.asList("3", "6", "8",
                "14", "15");

        // Using Stream mapToInt(ToIntFunction mapper)
        // and displaying the corresponding IntStream
        list.stream().map(Integer::parseInt)
                .filter(num -> num % 3 == 0)
                .forEach(System.out::println);

        List<String> list1 = Arrays.asList("Geeks", "for", "gfg",
                "GeeksforGeeks", "GeeksQuiz");

        // Using Stream mapToInt(ToIntFunction mapper)
        // and displaying the corresponding IntStream
        // which contains length of each element in
        // given Stream
        list1.stream().map(str -> str.length()).forEach(System.out::println);

        IntStream intStream2 = Stream.of(4, 5, 6).mapToInt(e -> e); //mapToInt method is needed

    }
}

@AllArgsConstructor
enum Capability {
    FALSE("0", false),
    TRUE("1", true);
    @Getter
    private final String umfValue;
    @Getter
    private final Boolean chValue;

    // "0,1,1,0,1"
    public static List<Capability> findByList(final String textArrayStr) {
        if (textArrayStr.isBlank()) {
            return null;
        }
        final String[] textArray = textArrayStr.split(",");
        final List<Capability> result = new ArrayList<>();
        for (final String text : textArray) {
            final String trimmed = text.strip();
            for (final Capability type : Capability.values()) {
                if (trimmed.equalsIgnoreCase(type.getUmfValue())) {
                    result.add(type);
                }
            }
        }
        if (result.isEmpty()) {
            return null;
        }
        return result; // [FALSE, TRUE, TRUE, FALSE, TRUE]
    }

    // "0,1,1,0,1"
    public static List<Capability> findByList1(final String textArrayStr) {
        return Optional.ofNullable(textArrayStr)
                .filter(textArrayStr1 -> !textArrayStr1.isBlank())
                .map(textArrayStr2 -> textArrayStr2.split(","))
                .flatMap(textArray -> Optional.of(textArray)
                        .map(textArray1 -> {
                            final List<Capability> result = new ArrayList<>();
                            Stream.of(textArray1)
                                    .forEach(text -> {
                                        Optional.of(text)
                                                .map(String::strip)
                                                .ifPresent(trimmed -> {
                                                    Stream.of(Capability.values())
                                                            .filter(enumItem -> trimmed.equalsIgnoreCase(enumItem.getUmfValue()))
                                                            .forEach(result::add);
                                                });
                                    });
                            return result;
                        })
                        .filter(result1 -> !result1.isEmpty()))
                .orElse(null); // [FALSE, TRUE, TRUE, FALSE, TRUE]
    }
}
