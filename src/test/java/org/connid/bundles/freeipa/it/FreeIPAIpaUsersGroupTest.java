/**
 * ====================
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2008-2009 Sun Microsystems, Inc. All rights reserved.
 * Copyright 2011-2013 Tirasa. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License("CDDL") (the "License"). You may not use this file
 * except in compliance with the License.
 *
 * You can obtain a copy of the License at https://oss.oracle.com/licenses/CDDL
 * See the License for the specific language governing permissions and limitations
 * under the License.
 *
 * When distributing the Covered Code, include this CDDL Header Notice in each file
 * and include the License file at https://oss.oracle.com/licenses/CDDL.
 * If applicable, add the following below this CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * ====================
 */
package org.connid.bundles.freeipa.it;

import com.unboundid.ldap.sdk.LDAPException;
import java.security.GeneralSecurityException;
import org.connid.bundles.freeipa.FreeIPAConfiguration;
import org.connid.bundles.freeipa.beans.server.FreeIPAIpaUsersGroup;
import org.connid.bundles.freeipa.commons.SampleConfigurationFactory;
import org.junit.Test;

public class FreeIPAIpaUsersGroupTest {
    
    @Test
    public void posixIDsTest() throws GeneralSecurityException, LDAPException, InterruptedException {
        final FreeIPAConfiguration freeIPAConfiguration = SampleConfigurationFactory.
                configurationWithRightUsernameAndPassword();
        final FreeIPAIpaUsersGroup fipaiug = new FreeIPAIpaUsersGroup(freeIPAConfiguration);
        final String testDn = "uid=test.test,cn=users,cn=accounts,dc=tirasa,dc=net";
        fipaiug.addMember(testDn);
        fipaiug.removeMember(testDn);
    }
    
}
