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

import org.junit.jupiter.params.ParameterizedTest;

public class SourceTest {

    @ParameterizedTest
    @FuzzSource(file = FuzzFile.ATTACK_JSON_JSON_FUZZING)
    public void valueTestSingleParameter(String s1) {
        System.out.println(s1);


    }

    @ParameterizedTest
    @FuzzSources({
            @FuzzSource(file = FuzzFile.ATTACK_JSON_JSON_FUZZING),
            @FuzzSource(file = FuzzFile.ATTACK_HTTP_PROTOCOL_CRLF_INJECTION, paddingValue = "pad")
    })
    public void valueTestDoubleParameter(String s1, String s2) {
        System.out.println(s1 + "-" + s2);
    }
}
