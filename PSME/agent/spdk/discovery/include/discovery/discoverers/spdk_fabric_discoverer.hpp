/*!
 * @brief SPDK Fabric discoverer interface.
 *
 * @copyright Copyright (c) 2018-2019 Intel Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @file spdk_fabric_discoverer.hpp
 */
#pragma once



#include "agent-framework/discovery/discoverers/fabric_discoverer.hpp"
#include "spdk_agent_context.hpp"



namespace agent {
namespace spdk {
namespace discovery {

class SpdkFabricDiscoverer : public agent_framework::discovery::FabricDiscoverer {
public:

    /*! Destructor */
    virtual ~SpdkFabricDiscoverer() {}


    /*!
     * @brief Perform discovery of Fabric object.
     * @param parent_uuid Parent UUID.
     * @return Fabric object filled with discovered data.
     * */
    agent_framework::model::Fabric discover(const Uuid& parent_uuid) override;
};
}
}
}

