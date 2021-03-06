package com.nyansa.siem.api.models;

/*-
 * #%L
 * VoyanceSiemSyslogAgent
 * %%
 * Copyright (C) 2019 Nyansa, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.List;

public class IoTGroupStatsList extends PaginatedResults<IoTGroupStats> {
  private List<IoTGroupStats> iotGroupStats;

  public List<IoTGroupStats> getIotGroupStats() {
    return iotGroupStats;
  }

  public void setIotGroupStats(List<IoTGroupStats> iotGroupStats) {
    this.iotGroupStats = iotGroupStats;
  }

  @Override
  public List<IoTGroupStats> getResults() {
    return getIotGroupStats();
  }
}
