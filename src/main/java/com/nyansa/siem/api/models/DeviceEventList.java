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

import java.util.ArrayList;
import java.util.List;

public class DeviceEventList extends PaginatedResults<DeviceEvent> {
  private List<Device> devices;
  private List<DeviceEvent> deviceEvents;

  public List<Device> getDevices() {
    return devices;
  }

  public void setDevices(List<Device> devices) {
    this.devices = devices;
  }

  @Override
  public List<DeviceEvent> getResults() {
    if (deviceEvents != null) {
      return deviceEvents;
    }
    ArrayList<DeviceEvent> events = new ArrayList<DeviceEvent>();
    for (Device device : devices) {
      for (DeviceEvent event : device.getAttributeChanges()) {
        // de-normalize Device attributes to DeviceEvent
        event.setUuid(device.getUuid());
        event.setMacAddr(device.getMacAddr());
        event.setIpAddress(device.getIpAddress());
        event.setDescription(device.getDescription());
        events.add(event);
      }
    }
    deviceEvents = events;
    return deviceEvents;
  }
}
