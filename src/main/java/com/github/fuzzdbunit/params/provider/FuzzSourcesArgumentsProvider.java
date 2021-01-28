/**
 * Copyright (C)2020 - Patrick M.J. Roth <red.parrot@bluewin.ch>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.fuzzdbunit.params.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @since 5.0
 */
class FuzzSourcesArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<FuzzSources> {

    private FuzzSources fuzzSources;
    private List<List<String>> lineList;

    FuzzSourcesArgumentsProvider() {

    }

    @Override
    public void accept(FuzzSources annotation) {
        this.fuzzSources = annotation;
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        FuzzFile longerFile = Arrays.stream(fuzzSources.value()).map(FuzzSource::file)
                .max(Comparator.comparing(FuzzFile::size)).get();
        int maxSize = longerFile.size();

        lineList = Arrays.stream(fuzzSources.value()).map(FuzzSource::file).map(FuzzFile::getData)
                .collect(Collectors.toList());

        return IntStream.range(0, maxSize).mapToObj(this::provideObjectArray)
                .map(Arguments::of);
    }

    private String[] provideObjectArray(int i) {
        String[] stringArgs = new String[lineList.size()];
        int j = 0;
        for (List<String> positions : lineList) {
            if (i < positions.size()) {
                stringArgs[j++] = positions.get(i);
            } else {
                stringArgs[j] = fuzzSources.value()[j].paddingValue();
                j++;
            }
        }
        return stringArgs;
    }

}
