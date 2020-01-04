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

import org.apiguardian.api.API;
import org.junit.platform.commons.JUnitException;

/**
 * Thrown if an error is encountered while reading the FuzzDB input files.
 *
 * @see FuzzSource
 * @since 5.3
 */
@API(status = EXPERIMENTAL, since = "5.3")
public class FuzzLoadingException extends JUnitException {

  private static final long serialVersionUID = 1L;

  public FuzzLoadingException(String message) {
    super(message);
  }

  public FuzzLoadingException(String message, Throwable cause) {
    super(message, cause);
  }

}
