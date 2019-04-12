/*
 * Copyright (c) 2019 Intel Corporation
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

package com.intel.rsd.nodecomposer.business.redfish.services.helpers;

import com.intel.rsd.nodecomposer.business.dto.EndpointDto;
import com.intel.rsd.nodecomposer.business.dto.EndpointDto.ConnectedEntityDto;
import com.intel.rsd.nodecomposer.business.dto.EndpointDto.IdentifierDto;
import com.intel.rsd.nodecomposer.business.dto.EndpointDto.Links;
import com.intel.rsd.nodecomposer.business.services.redfish.odataid.ODataId;
import com.intel.rsd.nodecomposer.persistence.dao.PciePortDao;
import com.intel.rsd.nodecomposer.persistence.redfish.ComputerSystem;
import com.intel.rsd.nodecomposer.persistence.redfish.base.DiscoverableEntity;
import com.intel.rsd.nodecomposer.types.DurableNameFormat;
import com.intel.rsd.nodecomposer.types.Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.intel.rsd.nodecomposer.business.redfish.services.helpers.DurableNameProducer.createDurableName;
import static com.intel.rsd.nodecomposer.business.redfish.services.helpers.InitiatorEndpointFinder.PNC_FABRIC_TYPES;
import static com.intel.rsd.nodecomposer.types.DurableNameFormat.findByProtocol;
import static com.intel.rsd.nodecomposer.types.EntityRole.INITIATOR;
import static java.util.stream.Collectors.toSet;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Component
@Scope(SCOPE_SINGLETON)
public class InitiatorEndpointCreateRequestBuilder {
    private final PciePortDao pciePortDao;

    @Autowired
    public InitiatorEndpointCreateRequestBuilder(PciePortDao pciePortDao) {
        this.pciePortDao = pciePortDao;
    }

    public EndpointDto buildInitiatorEndpointCreationRequest(Protocol fabricType, ComputerSystem computerSystem) {
        EndpointDto endpointDto = new EndpointDto();
        endpointDto.addIdentifier(buildIdentifierDto(getDurableNameFormat(fabricType), computerSystem.getUuid()));
        endpointDto.addConnectedEntity(buildConnectedEntityDto());

        if (PNC_FABRIC_TYPES.contains(fabricType)) {
            addUpstreamPorts(computerSystem, endpointDto);
        }

        return endpointDto;
    }

    private void addUpstreamPorts(ComputerSystem computerSystem, EndpointDto endpointDto) {
        Links links = endpointDto.getLinks();
        getUpstreamPortsConnectedWithComputerSystem(computerSystem.getPcieConnectionIds()).forEach(links::addPort);
    }

    private Set<ODataId> getUpstreamPortsConnectedWithComputerSystem(List<String> pcieConnectionIds) {
        return pciePortDao.getUpstreamPortsByCableIds(pcieConnectionIds)
            .stream()
            .map(DiscoverableEntity::getUri)
            .collect(toSet());
    }

    private DurableNameFormat getDurableNameFormat(Protocol fabricType) {
        return findByProtocol(fabricType)
            .orElseThrow(() -> new RuntimeException("Cannot determine durable name format for fabric type: " + fabricType));
    }

    private IdentifierDto buildIdentifierDto(DurableNameFormat durableNameFormat, UUID computerSystemUuid) {
        IdentifierDto identifierDto = new IdentifierDto();
        identifierDto.setDurableNameFormat(durableNameFormat);
        identifierDto.setDurableName(createDurableName(durableNameFormat, computerSystemUuid));

        return identifierDto;
    }

    private ConnectedEntityDto buildConnectedEntityDto() {
        ConnectedEntityDto connectedEntityDto = new ConnectedEntityDto();
        connectedEntityDto.setEntityRole(INITIATOR);

        return connectedEntityDto;
    }
}
