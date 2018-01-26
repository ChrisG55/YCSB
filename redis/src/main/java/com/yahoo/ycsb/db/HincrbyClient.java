/**
 * Copyright (c) 2018 YCSB contributors. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You
 * may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License. See accompanying
 * LICENSE file.
 */

package com.yahoo.ycsb.db;

import com.yahoo.ycsb.ByteIterator;
import com.yahoo.ycsb.Status;
import com.yahoo.ycsb.StringByteIterator;

import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Subclass of {@code RedisClient} to increment hash fields when updating.
 */
public class HincrbyClient extends RedisClient {

  /*
   * NOTE:
   * The Redis command HINCRBY will insert a key, if the key is not yet in the
   * keyspace. If the field of the specified key is not initialized, it will
   * be initialized to zero before being incremented.
   */
  @Override
  public Status update(String table, String key, Map<String, ByteIterator> values) {
    Long zero = new Long(0L);
    Jedis jedis = super.getJedis();
    Status status = Status.OK;
    Set<String> fields = StringByteIterator.getStringMap(values).keySet();
    Iterator<String> fieldIterator = fields.iterator();

    while (fieldIterator.hasNext()) {
      status = ((jedis.hincrBy(key, fieldIterator.next(), 1).compareTo(zero) > 0) &&
          status.isOk()) ? Status.OK : Status.ERROR;
    }
      
    return status;
  }
}
