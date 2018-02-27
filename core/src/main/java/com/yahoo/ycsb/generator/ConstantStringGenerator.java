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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.yahoo.ycsb.Utils;

/**
 * A trivial string generator that always returns the same value.
 *
 */
public class ConstantStringGenerator extends StringGenerator {

  private final String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incidi" +
      "dunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi" +
      " ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dol" +
      "ore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deseru" +
      "nt mollit anim id est laborum.";
  private final String s;

  public ConstantStringGenerator() {
    this.s = lorem;
  }

  /**
   * @param i The length of the string in bytes this generator will always
   * return.
   */
  public ConstantStringGenerator(int i) {
    StringBuilder sb = new StringBuilder(i + 1);
    int byteCount = 0;
    List<String> dictionary = readDictionary("lorem.txt");
    int dictSize = dictionary.size();
    while (sb.length() < i) {
      String word = dictionary.get(Utils.random().nextInt(dictSize));
      sb.append(word);
      sb.append(" ");
    }
    sb.setLength(i);
    this.s = sb.toString();
    setLastValue(this.s);
  }

  /**
   * @param s The string that this generator will always return.
   */
  public ConstantStringGenerator(String s) {
    this.s = s;
    setLastValue(this.s);
  }

  @Override
  public String nextValue() {
    return s;
  }

  @Override
  public double meanLength() {
    return s.length();
  }

  @Override
  public double meanBytes() {
    int l = 0;
    try {
      l = s.getBytes("UTF-8").length;
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return l;
  }

  private List<String> readDictionary(String file) {
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
