/*
 * Copyright 2006-2013 the original author or authors.
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

package com.consol.citrus.admin.executor;

import java.io.File;

import org.apache.commons.cli.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import com.consol.citrus.admin.launcher.ProcessLauncher;
import com.consol.citrus.admin.launcher.ProcessLauncherImpl;
import com.consol.citrus.admin.launcher.process.ExecuteSingleTest;
import com.consol.citrus.admin.service.ConfigService;
import com.consol.citrus.admin.websocket.LoggingWebSocket;

/**
 * @author Christoph Deppisch
 */
public class ProcessLaunchingTestExecutor extends FileSystemTestExecutor {

    @Autowired
    private ConfigService configService;
    
    @Autowired
    private LoggingWebSocket loggingWebSocket;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(String testName) throws ParseException {
        File file = configService.getProjectHome();
        ProcessBuilder processBuilder = new ExecuteSingleTest(file, testName).getProcessBuilder();
        ProcessLauncher processLauncher = new ProcessLauncherImpl(testName);
        processLauncher.addProcessListener(loggingWebSocket);
        processLauncher.launchAndContinue(processBuilder, 0);
    }
}