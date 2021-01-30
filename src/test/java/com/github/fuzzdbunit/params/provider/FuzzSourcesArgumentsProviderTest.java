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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class FuzzSourcesArgumentsProviderTest {

    static Stream<String> fuzzFileStream() throws IOException {
        System.out.println(Paths.get("build/fuzzDb/attack"));
        return Files.walk(Paths.get("build/fuzzDb/attack"))
                .filter(f -> f.toString().endsWith(".txt"))
                .filter(f -> !f.toString().endsWith(".doc.txt"))
                .map(p -> extractEnumName(p));
    }

    static String extractEnumName(Path p) {
        String filename = p.toString();
        int beginPos = filename.indexOf("attack" + File.separator) + 7;
        int endPos = filename.length() - 4;
        return filename.substring(beginPos, endPos)
                .replace(File.separator, "_")
                .replace('.', '_')
                .replace('-', '_')
                .toUpperCase();
    }

    @SuppressWarnings("unchecked")
    private static <T> T[] array(T... elements) {
        return elements;
    }

    @Test
    void providesArgumentsSingleFile() {
        FuzzSource fuzzSource = MockFuzzAnnotationBuilder.fuzzSource().file(FuzzFile.JSON_JSON_FUZZING).build();
        FuzzSource[] fuzzSources = (FuzzSource[]) Arrays.asList(fuzzSource).toArray(new FuzzSource[1]);
        FuzzSources annotation = MockFuzzAnnotationBuilder.fuzzSources().value(fuzzSources).build();

        Stream<Object[]> arguments = provideArguments(new FuzzSourcesArgumentsProvider(), annotation);

        assertThat(arguments).containsExactly(array("foo1"), array("foo2"), array("foo3"));
    }

    @Test
    void providesArgumentsFileTwice() {
        FuzzSource fuzzSource1 = MockFuzzAnnotationBuilder.fuzzSource().file(FuzzFile.JSON_JSON_FUZZING).build();
        FuzzSource fuzzSource2 = MockFuzzAnnotationBuilder.fuzzSource().file(FuzzFile.JSON_JSON_FUZZING).build();
        FuzzSource[] fuzzSources = (FuzzSource[]) Arrays.asList(fuzzSource1, fuzzSource2).toArray(new FuzzSource[2]);
        FuzzSources annotation = MockFuzzAnnotationBuilder.fuzzSources().value(fuzzSources).build();

        Stream<Object[]> arguments = provideArguments(new FuzzSourcesArgumentsProvider(), annotation);

        assertThat(arguments)
                .containsExactly(array("foo1", "foo1"), array("foo2", "foo2"), array("foo3", "foo3"));
    }

    @Test
    void providesArgumentsTwoFilesLongerFirst() {
        FuzzSource fuzzSource1 = MockFuzzAnnotationBuilder.fuzzSource().file(FuzzFile.JSON_JSON_FUZZING).build();
        FuzzSource fuzzSource2 = MockFuzzAnnotationBuilder.fuzzSource().file(FuzzFile.BUSINESS_LOGIC_COMMONMETHODNAMES).build();
        FuzzSource[] fuzzSources = (FuzzSource[]) Arrays.asList(fuzzSource1, fuzzSource2).toArray(new FuzzSource[2]);
        FuzzSources annotation = MockFuzzAnnotationBuilder.fuzzSources().value(fuzzSources).build();

        Stream<Object[]> arguments = provideArguments(new FuzzSourcesArgumentsProvider(), annotation);

        assertThat(arguments)
                .containsAnyOf(array("foo1", "bar1"), array("foo2", "bar2"),
                        array("foo3", null));
    }

    @Test
    void providesArgumentsTwoFilesShorterFirst() {
        FuzzSource fuzzSource1 = MockFuzzAnnotationBuilder.fuzzSource().file(FuzzFile.BUSINESS_LOGIC_COMMONMETHODNAMES).build();
        FuzzSource fuzzSource2 = MockFuzzAnnotationBuilder.fuzzSource().file(FuzzFile.JSON_JSON_FUZZING).build();
        FuzzSource[] fuzzSources = Arrays.asList(fuzzSource1, fuzzSource2).toArray(new FuzzSource[2]);
        FuzzSources annotation = MockFuzzAnnotationBuilder.fuzzSources().value(fuzzSources).build();

        Stream<Object[]> arguments = provideArguments(new FuzzSourcesArgumentsProvider(), annotation);

        assertThat(arguments)
                .containsExactly(array("bar1", "foo1"), array("bar2", "foo2"),
                        array(null, "foo3"));
    }

    @Test
    void providesArgumentsTwoFilesWithPaddingNull() {
        FuzzSource fuzzSource1 = MockFuzzAnnotationBuilder.fuzzSource().file(FuzzFile.JSON_JSON_FUZZING).paddingValue(null).build();
        FuzzSource fuzzSource2 = MockFuzzAnnotationBuilder.fuzzSource().file(FuzzFile.BUSINESS_LOGIC_COMMONMETHODNAMES).paddingValue(null).build();
        FuzzSource[] fuzzSources = (FuzzSource[]) Arrays.asList(fuzzSource1, fuzzSource2).toArray(new FuzzSource[2]);
        FuzzSources annotation = MockFuzzAnnotationBuilder.fuzzSources().value(fuzzSources).build();

        Stream<Object[]> arguments = provideArguments(new FuzzSourcesArgumentsProvider(), annotation);

        assertThat(arguments)
                .containsExactly(array("foo1", "bar1"), array("foo2", "bar2"),
                        array("foo3", null));
    }

    @Test
    void providesArgumentsTwoFilesWithPaddingEmpty() {
        FuzzSource fuzzSource1 = MockFuzzAnnotationBuilder.fuzzSource().file(FuzzFile.JSON_JSON_FUZZING).paddingValue("").build();
        FuzzSource fuzzSource2 = MockFuzzAnnotationBuilder.fuzzSource().file(FuzzFile.BUSINESS_LOGIC_COMMONMETHODNAMES).paddingValue("").build();
        FuzzSource[] fuzzSources = (FuzzSource[]) Arrays.asList(fuzzSource1, fuzzSource2).toArray(new FuzzSource[2]);
        FuzzSources annotation = MockFuzzAnnotationBuilder.fuzzSources().value(fuzzSources).build();

        Stream<Object[]> arguments = provideArguments(new FuzzSourcesArgumentsProvider(), annotation);

        assertThat(arguments)
                .containsExactly(array("foo1", "bar1"), array("foo2", "bar2"),
                        array("foo3", ""));
    }

    @Test
    void providesArgumentsFourFilesWithPadding() {
        FuzzSource fuzzSource1 = MockFuzzAnnotationBuilder.fuzzSource().file(FuzzFile.BUSINESS_LOGIC_COMMONMETHODNAMES).paddingValue("").build();
        FuzzSource fuzzSource2 = MockFuzzAnnotationBuilder.fuzzSource().file(FuzzFile.JSON_JSON_FUZZING).paddingValue("").build();
        FuzzSource fuzzSource3 = MockFuzzAnnotationBuilder.fuzzSource().file(FuzzFile.HTTP_PROTOCOL_CRLF_INJECTION).paddingValue("").build();
        FuzzSource fuzzSource4 = MockFuzzAnnotationBuilder.fuzzSource().file(FuzzFile.XSS_XSS_OTHER).paddingValue("").build();
        FuzzSource[] fuzzSources = (FuzzSource[]) Arrays.asList(fuzzSource1, fuzzSource2, fuzzSource3, fuzzSource4).toArray(new FuzzSource[2]);
        FuzzSources annotation = MockFuzzAnnotationBuilder.fuzzSources().value(fuzzSources).build();

        Stream<Object[]> arguments = provideArguments(new FuzzSourcesArgumentsProvider(), annotation);

        assertThat(arguments)
                .containsExactly(array( "bar1", "foo1","hello1", "world1"), array("bar2","foo2", "hello2", "world2"),
                        array( "","foo3","", "world3"));
    }

    @Test
    void providesArgumentsWithNormalFileWithPadding() {
        FuzzSource fuzzSource1 = MockFuzzAnnotationBuilder.fuzzSource().file(FuzzFile.JSON_JSON_FUZZING).paddingValue(null).build();
        FuzzSource fuzzSource2 = MockFuzzAnnotationBuilder.fuzzSource().file(FuzzFile.BUSINESS_LOGIC_COMMONDEBUGPARAMNAMES).paddingValue(null).build();
        FuzzSource[] fuzzSources = (FuzzSource[]) Arrays.asList(fuzzSource1, fuzzSource2).toArray(new FuzzSource[2]);
        FuzzSources annotation = MockFuzzAnnotationBuilder.fuzzSources().value(fuzzSources).build();

        Stream<Object[]> arguments = provideArguments(new FuzzSourcesArgumentsProvider(), annotation);

        assertThat(arguments)
                .contains(array("foo1", "7357=1"), array("foo2", "7357=true"),
                        array("foo3", "7357=y"), array(null, "7357=yes"));
    }

    @ParameterizedTest
    @MethodSource("fuzzFileStream")
    void testFuzzFiles(String fuzzEnumName) {
        FuzzFile ff = FuzzFile.valueOf(fuzzEnumName);
        System.out.println(ff.getFilePath());
        assertThat(ff).isNotNull();
        assertThat(ff.getData().size()).isNotEqualTo(0);
    }

//    private Stream<Object[]> provideArguments(FuzzFile file, FuzzSource annotation) {
//        FuzzFile expectedFile = annotation.file();
//
//        FuzzArgumentsProvider provider = new FuzzArgumentsProvider();
//        return provideArguments(provider, annotation);
//    }

    private Stream<Object[]> provideArguments(FuzzSourcesArgumentsProvider provider,
                                              FuzzSources annotation) {
        provider.accept(annotation);
        ExtensionContext context = mock(ExtensionContext.class);
        when(context.getTestClass()).thenReturn(Optional.of(FuzzSourcesArgumentsProviderTest.class));
        doCallRealMethod().when(context).getRequiredTestClass();
        return provider.provideArguments(context).map(Arguments::get);
    }

}
