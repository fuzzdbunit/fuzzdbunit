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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FuzzSourceArgumentsProviderTest {

    @SuppressWarnings("unchecked")
    private static <T> T[] array(T... elements) {
        return elements;
    }

    @Test
    void providesArgumentsSingleFile() {
        FuzzSource annotation = MockFuzzAnnotationBuilder.fuzzSource().file(FuzzFile.ATTACK_JSON_JSON_FUZZING).build();

        Stream<Object[]> arguments = provideArguments(new FuzzSourceArgumentsProvider(), annotation);

        assertThat(arguments).containsExactly(array("foo1"), array("foo2"), array("foo3"));
    }

    @Test
    void providesArgumentsOneFileWithPaddingNull() {
        FuzzSource annotation = MockFuzzAnnotationBuilder.fuzzSource().file(FuzzFile.ATTACK_JSON_JSON_FUZZING).paddingValue(null).build();

        Stream<Object[]> arguments = provideArguments(new FuzzSourceArgumentsProvider(), annotation);

        assertThat(arguments)
                .containsExactly(array("foo1"), array("foo2"), array("foo3"));
    }

    private Stream<Object[]> provideArguments(FuzzSourceArgumentsProvider provider,
                                              FuzzSource annotation) {
        provider.accept(annotation);
        ExtensionContext context = mock(ExtensionContext.class);
        when(context.getTestClass()).thenReturn(Optional.of(FuzzSourceArgumentsProviderTest.class));
        doCallRealMethod().when(context).getRequiredTestClass();
        return provider.provideArguments(context).map(Arguments::get);
    }

}