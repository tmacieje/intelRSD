/*
 * Copyright (c) 2016-2019 Intel Corporation
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

package com.intel.rsd.nodecomposer.concurrent;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static com.intel.rsd.nodecomposer.utils.beans.JndiNames.ASYNC_TASKS_EXECUTOR;

@Configuration
public class TaskExecutors {
    private static final int TASK_EXECUTOR_CORE_POOL_SIZE = 8;

    @Bean(name = ASYNC_TASKS_EXECUTOR)
    public TaskExecutor getAsyncTasksExecutor() {
        ThreadPoolTaskExecutor asyncTasksExecutor = new ThreadPoolTaskExecutor();
        asyncTasksExecutor.setCorePoolSize(TASK_EXECUTOR_CORE_POOL_SIZE);
        return asyncTasksExecutor;
    }
}
