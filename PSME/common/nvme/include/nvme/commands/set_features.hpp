/*!
 * @copyright Copyright (c) 2019 Intel Corporation.
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
 * @file nvme/commands/set_features.hpp
 */

#pragma once



#include "nvme/commands/generic_nvme_command.hpp"



namespace nvme {
namespace commands {

class SetFeatures final : public GenericNvmeCommand {
public:

    /*!
     * @brief Constructor
     * @param namespace_id ID of requested namespace
     * @param feature_identifier ID of the requested feature
     * @param feature_value Requested value for feature
     */
    SetFeatures(std::uint32_t namespace_id, std::uint8_t feature_identifier, std::uint32_t feature_value);


    /*! @brief Destructor */
    virtual ~SetFeatures();

};

}
}
