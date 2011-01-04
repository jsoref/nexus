/**
 * Copyright (c) 2008-2011 Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://www.sonatype.com/products/nexus/attributions.
 *
 * This program is free software: you can redistribute it and/or modify it only under the terms of the GNU Affero General
 * Public License Version 3 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License Version 3
 * for more details.
 *
 * You should have received a copy of the GNU Affero General Public License Version 3 along with this program.  If not, see
 * http://www.gnu.org/licenses.
 *
 * Sonatype Nexus (TM) Open Source Version is available from Sonatype, Inc. Sonatype and Sonatype Nexus are trademarks of
 * Sonatype, Inc. Apache Maven is a trademark of the Apache Foundation. M2Eclipse is a trademark of the Eclipse Foundation.
 * All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.integrationtests.nexus133;

import java.io.IOException;

import org.restlet.data.MediaType;
import org.sonatype.nexus.integrationtests.TestContainer;
import org.sonatype.nexus.test.utils.TargetMessageUtil;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * CRUD tests for XML request/response.
 */
public class Nexus133TargetCrudXmlIT
    extends Nexus133TargetCrudJsonIT
{

    @BeforeClass
    public void setSecureTest(){
    	this.messageUtil =
            new TargetMessageUtil( this, this.getXMLXStream(),
                                 MediaType.APPLICATION_XML );
        TestContainer.getInstance().getTestContext().setSecureTest( true );
    }
    
    
    
    @Test
    public void readTest()
        throws IOException
    {
        super.readTest();
    }
    
}
