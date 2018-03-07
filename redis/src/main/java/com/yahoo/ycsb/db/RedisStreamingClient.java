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

import java.util.Map;
import java.util.UUID;

/**
 * Subclass of {@code RedisClient} to stream operations.
 */
public class RedisStreamingClient extends RedisClient {

  @Override
  public Status insert(String table, String key,
      Map<String, ByteIterator> values) {
    Jedis jedis = super.getJedis();
    if (jedis.stream("HMSET", key, StringByteIterator.getStringMap(values))
        .equals("OK")) {
      jedis.zadd(INDEX_KEY, key.hashCode(), key);
      return Status.OK;
    }
    return Status.ERROR;
  }

  @Override
  public Status insert(String table, String key, UUID uuid,
      Map<String, ByteIterator> values) {
    Jedis jedis = super.getJedis();
    if (jedis.stream("HMSET", key, StringByteIterator.getStringMap(values))
        .equals("OK")) {
      jedis.zadd(INDEX_KEY, key.hashCode(), key);
      return Status.OK;
    }
    return Status.ERROR;
  }

  @Override
  public Status update(String table, String key,
      Map<String, ByteIterator> values) {
    Jedis jedis = super.getJedis();
    return jedis.stream("HMSET", key, StringByteIterator.getStringMap(values))
        .equals("OK") ? Status.OK : Status.ERROR;
  }

  @Override
  public Status update(String table, String key, UUID uuid,
      Map<String, ByteIterator> values) {
    Jedis jedis = super.getJedis();
    return jedis.stream("HMSET", key, StringByteIterator.getStringMap(values))
        .equals("OK") ? Status.OK : Status.ERROR;
  }
}
