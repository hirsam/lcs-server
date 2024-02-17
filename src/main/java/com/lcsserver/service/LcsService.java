package com.lcsserver.service;

import com.lcsserver.dto.ValueDto;
import com.lcsserver.exception.LcsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.lcsserver.util.ErrorMessages.EMPTY_VALUES_MESSAGE;
import static com.lcsserver.util.ErrorMessages.VALUES_MUST_BE_A_SET_MESSAGE;

@Service
public class LcsService {

    private final Logger logger = LoggerFactory.getLogger(LcsService.class);


    public List<ValueDto> getAllLcs(List<ValueDto> strings) {
        logger.info("Processing request to find longest common substrings for input: {}", strings);
        validateStrings(strings);
        List<ValueDto> result = getSortedLongestCommonSubstrings(strings);
        logger.info("Found longest common substrings: {}", result);
        return result;
    }

    private List<ValueDto> getSortedLongestCommonSubstrings(List<ValueDto> strings) {
        int n = strings.size();

        String s = strings.get(0).getValue();
        int length = s.length();

        List<ValueDto> commonSubstrings = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j <= length; j++) {
                String substring = s.substring(i, j);
                int k;
                for (k = 1; k < n; k++) {
                    if (!strings.get(k).getValue().contains(substring)) {
                        break;
                    }
                }

                if (k == n) {
                    commonSubstrings.add(new ValueDto(substring));
                }
            }
        }

        int maxSubstringLength = getMaxLength(commonSubstrings);

        return commonSubstrings.stream()
                .filter(substring -> maxSubstringLength == substring.getValue().length())
                .sorted(Comparator.comparing(ValueDto::getValue))
                .toList();
    }

    private int getMaxLength(List<ValueDto> strings) {
        return strings.stream()
                .max(Comparator.comparing((i) -> i.getValue().length()))
                .orElse(new ValueDto("")).getValue().length();
    }

    private void validateStrings(List<ValueDto> strings) {
        if (strings == null || strings.isEmpty() || strings.stream().allMatch(s -> s.getValue().trim().isEmpty())) {
            throw new LcsException(EMPTY_VALUES_MESSAGE);
        }

        Set<String> uniqueValues = strings.stream()
                .map(ValueDto::getValue)
                .collect(Collectors.toSet());
        if (uniqueValues.size() != strings.size()) {
            throw new LcsException(VALUES_MUST_BE_A_SET_MESSAGE);
        }
    }
}
