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
package org.sonatype.nexus.proxy.repository.validator;

import java.util.List;

import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.sonatype.nexus.proxy.item.StorageFileItem;
import org.sonatype.nexus.proxy.item.StorageItem;
import org.sonatype.nexus.proxy.repository.validator.FileTypeValidator.FileTypeValidity;

@Component( role = FileTypeValidatorHub.class )
public class DefaultFileTypeValidatorHub
    implements FileTypeValidatorHub
{
    @Requirement( role = FileTypeValidator.class )
    private List<FileTypeValidator> fileTypeValidators;

    @Override
    public boolean isExpectedFileType( final StorageItem item )
    {
        if ( item instanceof StorageFileItem )
        {
            StorageFileItem file = (StorageFileItem) item;

            for ( FileTypeValidator fileTypeValidator : fileTypeValidators )
            {
                FileTypeValidity validity = fileTypeValidator.isExpectedFileType( file );

                if ( FileTypeValidity.INVALID.equals( validity ) )
                {
                    // fail fast
                    return false;
                }
            }

            // return true if not failed for now
            // later we might get this better
            return true;
        }
        else
        {
            // we check files only, so say true here
            return true;
        }
    }
}
