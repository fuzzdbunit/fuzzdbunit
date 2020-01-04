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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;

/**
 * @since 5.6
 */
abstract class MockFuzzAnnotationBuilder<A extends Annotation, B extends MockFuzzAnnotationBuilder<A, B>> {

  private String emptyValue = "";

  // -------------------------------------------------------------------------
  private String[] nullValues = new String[0];

  private MockFuzzAnnotationBuilder() {
  }

  static MockFuzzSourceBuilder fuzzSource() {
    return new MockFuzzSourceBuilder();
  }

  protected abstract B getSelf();

  B emptyValue(String emptyValue) {
    this.emptyValue = emptyValue;
    return getSelf();
  }

  B nullValues(String... nullValues) {
    this.nullValues = nullValues;
    return getSelf();
  }

  abstract A build();

  // -------------------------------------------------------------------------


  static class MockFuzzSourceBuilder extends
      MockFuzzAnnotationBuilder<FuzzSource, MockFuzzSourceBuilder> {

    private FuzzFile[] files = {FuzzFile.JSON_JSON_FUZZING};
    private String[] paddingValues = null;

    @Override
    protected MockFuzzSourceBuilder getSelf() {
      return this;
    }

    /**
     * .
     */
    MockFuzzSourceBuilder files(FuzzFile... files) {
      this.files = files;
      return this;
    }

    MockFuzzSourceBuilder paddingValues(String... paddingValues) {
      this.paddingValues = paddingValues;
      return this;
    }

    @Override
    FuzzSource build() {
      FuzzSource annotation = mock(FuzzSource.class);

      // @FuzzSource
      when(annotation.files()).thenReturn(this.files);
      when(annotation.paddingValues()).thenReturn(this.paddingValues);

      return annotation;
    }
  }

}
