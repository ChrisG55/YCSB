/**
 * Copyright (c) 2018 YCSB contributors. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You
 * may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License. See accompanying
 * LICENSE file.
 */

package com.yahoo.ycsb.generator;

/**
 * A generator that is capable of generating string values.
 *
 */
public abstract class StringGenerator extends Generator<String> {
  private String lastVal;

  /**
   * Set the last value generated. StringGenerator subclasses must use this call
   * to properly set the last value, or the {@link #lastValue()} calls won't work.
   */
  protected void setLastValue(String last) {
    lastVal = last;
  }


  @Override
  public String lastValue() {
    return lastVal;
  }

  /**
   * Return the expected number of characters (mean) of the values this
   * generator will return.
   */
  public abstract double meanLength();

  /**
   * Return the expected number of bytes (mean) of the values this generator
   * will return.
   */
  public abstract double meanBytes();
}
