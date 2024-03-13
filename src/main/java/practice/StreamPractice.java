package practice;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;
import model.Candidate;
import model.Cat;
import model.Person;

public class StreamPractice {
    private static final String ARRAY_SPLIT_REGEX = ",";
    private final CandidateValidator candidateValidator = new CandidateValidator();

    public int findMinEvenNumber(List<String> numbers) {
        return numbers.stream()
                .map(s -> s.split(ARRAY_SPLIT_REGEX))
                .flatMap(Arrays::stream)
                .map(Integer::parseInt)
                .filter(n -> n % 2 == 0)
                .sorted()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't get min value from list: "
                                                                                  + numbers));
    }

    public Double getOddNumsAverage(List<Integer> numbers) {
        return IntStream.range(0, numbers.size())
                .map(i -> {
                    if (i % 2 != 0) {
                        return numbers.get(i) - 1;
                    }
                    return numbers.get(i);
                })
                .filter(n -> n % 2 != 0)
                .average()
                .orElseThrow(NoSuchElementException::new);
    }

    public List<Person> selectMenByAge(List<Person> peopleList, int fromAge, int toAge) {
        return peopleList.stream()
                .filter(p -> p.getSex().equals(Person.Sex.MAN)
                        && p.getAge() >= fromAge && p.getAge() <= toAge)
                .toList();
    }

    public List<Person> getWorkablePeople(int fromAge, int femaleToAge,
                                          int maleToAge, List<Person> peopleList) {
        return peopleList.stream()
                .filter(p -> p.getAge() >= fromAge
                        && (p.getSex().equals(Person.Sex.MAN) && p.getAge() <= maleToAge
                        || p.getSex().equals(Person.Sex.WOMAN) && p.getAge() <= femaleToAge))
                .toList();
    }

    public List<String> getCatsNames(List<Person> peopleList, int femaleAge) {
        return peopleList.stream()
                .filter(p -> p.getSex().equals(Person.Sex.WOMAN)
                            && p.getAge() >= femaleAge)
                .map(Person::getCats)
                .flatMap(Collection::stream)
                .map(Cat::getName)
                .toList();
    }

    public List<String> validateCandidates(List<Candidate> candidates) {
        return candidates.stream()
                .filter(candidateValidator)
                .map(Candidate::getName)
                .sorted()
                .toList();
    }
}
