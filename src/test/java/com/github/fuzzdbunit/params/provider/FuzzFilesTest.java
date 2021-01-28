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
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class FuzzFilesTest {

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

    @ParameterizedTest
    @MethodSource("fuzzFileStream")
    void testFuzzFiles(String fuzzEnumName) {
        FuzzFile ff = FuzzFile.valueOf(fuzzEnumName);
        System.out.println(ff.getFilePath());
        assertThat(ff).isNotNull();
        assertThat(ff.getData().size()).isNotEqualTo(0);
    }

}