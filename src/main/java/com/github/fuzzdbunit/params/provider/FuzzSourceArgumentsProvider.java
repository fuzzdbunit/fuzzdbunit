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

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

/**
 * @since 5.0
 */
class FuzzSourceArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<FuzzSource> {

    private FuzzSource fuzzSource;

    FuzzSourceArgumentsProvider() {

    }

    @Override
    public void accept(FuzzSource annotation) {
        this.fuzzSource = annotation;
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return fuzzSource.file().getData().stream().map(Arguments::of);
    }

}
