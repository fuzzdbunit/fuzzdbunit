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

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

import java.lang.annotation.*;

import org.apiguardian.api.API;
import org.junit.jupiter.params.provider.ArgumentsSource;

/**
 * {@code @FuzzSource} is an {@link ArgumentsSource} which is used to load values from one FuzzDB
 * file.
 *
 * <p>The lines of these FuzzDB files will be provided as arguments to the annotated
 * {@code @ParameterizedTest} method.
 *
 * @see org.junit.jupiter.params.provider.ArgumentsSource
 * @see org.junit.jupiter.params.ParameterizedTest
 * @since 5.0
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@API(status = EXPERIMENTAL, since = "5.5")
@ArgumentsSource(FuzzSourceArgumentsProvider.class)
public @interface FuzzSource {

    /**
     * The fuzz data file to be used as the sources of arguments; must not be empty.
     */
    FuzzFile file();

    /**
     * A list of padding values that should be used to pad if the values provided by another {@code FuzzSource}
     * of the list doesn't have the same length.
     *
     * <p>For example, if you specify JSON_JSON_FUZZING (which has 89 lines) as the first source, and
     * SQL_INJECTION_DETECT_GENERICBLIND (31 lines) as the second one, then the arguments passed as
     * the second parameter will be the values defined in SQL_INJECTION_DETECT_GENERICBLIND up to 31,
     * and then {@code padding} from the 32th line.
     *
     * <p>If {@code paddingValues} is left empty, then the padding value is an empty string {@code ""}.
     *
     * @since 5.6
     */
    @API(status = EXPERIMENTAL, since = "5.5")
    String paddingValue() default "";

}