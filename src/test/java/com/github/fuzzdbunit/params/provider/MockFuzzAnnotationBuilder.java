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
  private String nullValue = null;

  private MockFuzzAnnotationBuilder() {
  }

  static MockFuzzSourceBuilder fuzzSource() {
    return new MockFuzzSourceBuilder();
  }

  static MockFuzzSourcesBuilder fuzzSources() {
    return new MockFuzzSourcesBuilder();
  }

  protected abstract B getSelf();

  B emptyValue(String emptyValue) {
    this.emptyValue = emptyValue;
    return getSelf();
  }

  B nullValue(String nullValue) {
    this.nullValue = nullValue;
    return getSelf();
  }

  //abstract A buildSource();
  //abstract A buildSources();

  // -------------------------------------------------------------------------


  static class MockFuzzSourceBuilder extends
      MockFuzzAnnotationBuilder<FuzzSource, MockFuzzSourceBuilder> {

    private FuzzFile file = FuzzFile.ATTACK_JSON_JSON_FUZZING;
    private String paddingValue = null;

    @Override
    protected MockFuzzSourceBuilder getSelf() {
      return this;
    }

    /**
     * .
     */
    MockFuzzSourceBuilder file(FuzzFile file) {
      this.file = file;
      return this;
    }

    MockFuzzSourceBuilder paddingValue(String paddingValue) {
      this.paddingValue = paddingValue;
      return this;
    }

    //@Override
    FuzzSource build() {
      FuzzSource annotation = mock(FuzzSource.class);

      // @FuzzSource
      when(annotation.file()).thenReturn(this.file);
      when(annotation.paddingValue()).thenReturn(this.paddingValue);

      return annotation;
    }
  }


  static class MockFuzzSourcesBuilder extends
          MockFuzzAnnotationBuilder<FuzzSources, MockFuzzSourcesBuilder> {

    private FuzzSource[] fuzzSources = null;

    @Override
    protected MockFuzzSourcesBuilder getSelf() {
      return this;
    }

    /**
     * .
     */
    MockFuzzSourcesBuilder value(FuzzSource[] fuzzSources) {
      this.fuzzSources = fuzzSources;
      return this;
    }

    //@Override
    FuzzSources build() {
      FuzzSources annotation = mock(FuzzSources.class);

      // @FuzzSource
      when(annotation.value()).thenReturn(fuzzSources);

      return annotation;
    }
  }
}
