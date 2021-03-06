/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.wildfly.extension.security.manager.logging;

import static org.jboss.logging.Logger.Level.INFO;

import javax.xml.stream.XMLStreamException;

import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;

/**
 * @author <a href="mailto:sguilhen@redhat.com">Stefan Guilhen</a>
 * @author <a href="mailto:jperkins@redhat.com">James R. Perkins</a>
 */
@MessageLogger(projectCode = "WFLYSM", length = 4)
public interface SecurityManagerLogger extends BasicLogger {

    SecurityManagerLogger ROOT_LOGGER = Logger.getMessageLogger(SecurityManagerLogger.class, "org.wildfly.extension.security.manager");

    @LogMessage(level = INFO)
    @Message(id = 1, value = "Installing the WildFly Security Manager")
    void installingWildFlySecurityManager();

    /**
     * Create {@link javax.xml.stream.XMLStreamException} to indicate an invalid version was found in the permissions element.
     *
     * @param found the version that was found in the element.
     * @param expected the expected version.
     * @return the constructed {@link javax.xml.stream.XMLStreamException}
     */
    @Message(id = 2, value = "Invalid version found in the permissions element. Found %s, expected %s")
    XMLStreamException invalidPermissionsXMLVersion(String found, String expected);

    /**
     * Creates a {@link org.jboss.as.controller.OperationFailedException} to indicate that the security manager subsystem
     * was incorrectly configured. As a rule the minimum-set permissions must be implied by the maximum-set permissions.
     *
     * @param failedPermissions a list of the permissions in the minimum-set that are not implied by the maximum-set.
     * @return the constructed {@link org.jboss.as.controller.OperationFailedException}
     */
    @Message(id = 3, value = "Subsystem configuration error: the following permissions are not implied by the maximum permissions set %s")
    OperationFailedException invalidSubsystemConfiguration(StringBuilder failedPermissions);

    /**
     * Creates a {@link org.jboss.as.server.deployment.DeploymentUnitProcessingException} to indicate that the deployment
     * was incorrectly configured. As a rule the permissions specified in the deployment descriptors (permissions.xml or
     * jboss-permissions.xml) must be implied by the subsystem maximum-set.
     *
     * @param failedPermissions a list of the permissions in deployment descriptors that are not implied by the maximum-set.
     * @return the constructed {@link org.jboss.as.server.deployment.DeploymentUnitProcessingException}
     */
    @Message(id = 4, value = "Deployment configuration error: the following permissions are not implied by the maximum permissions set %s")
    DeploymentUnitProcessingException invalidDeploymentConfiguration(StringBuilder failedPermissions);

    /**
     * Creates a message indicating that empty maximum sets are not understood in the target model version and must thus
     * be rejected.
     *
     * @return the constructed {@link String} message.
     */
    @Message(id = 5, value = "Empty maximum sets are not understood in the target model version and must be rejected")
    String rejectedEmptyMaximumSet();
}
