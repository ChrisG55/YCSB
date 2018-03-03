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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.yahoo.ycsb.Utils;

/**
 * A trivial string generator that randomly returns a value.
 *
 */
public class RandomStringGenerator extends StringGenerator {

  private int len;

  private final Random rng = Utils.random();

  private static final int DEFAULT_LENGTH = 140;
  private static final List<String> DICTIONARY = readDictionary("lorem.txt");
  private static final int DICTIONARY_SIZE = DICTIONARY.size();

  /**
   * Instance of a generator generating random strings of default length.
   */ 
  public RandomStringGenerator() {
    this.len = DEFAULT_LENGTH;
  }

  /**
   * @param len The length of the string in bytes this generator will randomly
   * generate.
   */
  public RandomStringGenerator(int len) {
    this.len = len;
  }

  /**
   * @param seed The seed that this generator will use to generate a random value.
   */
  public RandomStringGenerator(long seed) {
    this.len = DEFAULT_LENGTH;
    rng.setSeed(seed);
  }

  /**
   * @param len The length of the string in bytes this generator will randomly
   * generate.
   * @param seed The seed that this generator will use to generate a random value.
   */
  public RandomStringGenerator(int len, long seed) {
    this.len = len;
    rng.setSeed(seed);
  }

  @Override
  public String nextValue() {
    String ret;
    StringBuilder sb = new StringBuilder(len);
    while (sb.length() < len) {
      String word = DICTIONARY.get(rng.nextInt(DICTIONARY_SIZE));
      sb.append(word);
      sb.append(" ");
    }
    sb.setLength(len - 1);
    ret = sb.toString();
    setLastValue(ret);

    return ret;
  }

  public String nextValue(UUID uuid) {
    String ret;
    StringBuilder sb = new StringBuilder(len);
    if (len < 36) {
      System.out.println("WARNING: field length is too short to store UUID. Field length is set to 36 characters.");
      this.len = 36;
    }
    sb.append(uuid.toString());
    sb.append(" ");
    while (sb.length() < len) {
      String word = DICTIONARY.get(rng.nextInt(DICTIONARY_SIZE));
      sb.append(word);
      sb.append(" ");
    }
    sb.setLength(len - 1);
    ret = sb.toString();
    setLastValue(ret);

    return ret;
  }

  @Override
  public double meanLength() {
    return len;
  }

  @Override
  public double meanBytes() {
    return len;
  }

  private static List<String> readDictionary(String file) {
    List<String> dictionary = new ArrayList<String>();
    BufferedReader br = null;

    try {
      br = new BufferedReader(new InputStreamReader(ConstantStringGenerator.class.getClassLoader()
        .getResourceAsStream(file), "UTF-8"));
      String word;
      while ((word = br.readLine()) != null) {
        dictionary.add(word.trim());
      }
    } catch(IOException e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch(IOException e) {
          e.printStackTrace();
        }
      }
    }
    return dictionary;
  }
}
