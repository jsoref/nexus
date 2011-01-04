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
package org.sonatype.nexus.tasks;

import org.codehaus.plexus.component.annotations.Component;
import org.sonatype.nexus.feeds.FeedRecorder;
import org.sonatype.nexus.proxy.ResourceStoreRequest;
import org.sonatype.nexus.proxy.repository.GroupRepository;
import org.sonatype.nexus.proxy.repository.Repository;
import org.sonatype.nexus.scheduling.AbstractNexusRepositoriesPathAwareTask;
import org.sonatype.nexus.tasks.descriptors.ExpireCacheTaskDescriptor;
import org.sonatype.scheduling.SchedulerTask;

/**
 * Clear caches task.
 * 
 * @author cstamas
 */
@Component( role = SchedulerTask.class, hint = ExpireCacheTaskDescriptor.ID, instantiationStrategy = "per-lookup" )
public class ExpireCacheTask
    extends AbstractNexusRepositoriesPathAwareTask<Object>
{
    @Override
    protected String getRepositoryFieldId()
    {
        return ExpireCacheTaskDescriptor.REPO_OR_GROUP_FIELD_ID;
    }

    @Override
    protected String getRepositoryPathFieldId()
    {
        return ExpireCacheTaskDescriptor.RESOURCE_STORE_PATH_FIELD_ID;
    }

    public Object doRun()
        throws Exception
    {
        ResourceStoreRequest req = new ResourceStoreRequest( getResourceStorePath() );

        if ( getRepositoryGroupId() != null )
        {
            getRepositoryRegistry().getRepositoryWithFacet( getRepositoryGroupId(), GroupRepository.class ).expireCaches( req );
        }
        else if ( getRepositoryId() != null )
        {
            getRepositoryRegistry().getRepositoryWithFacet( getRepositoryId(), Repository.class ).expireCaches( req );
        }
        else
        {
            getNexus().expireAllCaches( new ResourceStoreRequest( getResourceStorePath() ) );
        }

        return null;
    }

    protected String getAction()
    {
        return FeedRecorder.SYSTEM_EXPIRE_CACHE_ACTION;
    }

    protected String getMessage()
    {
        if ( getRepositoryGroupId() != null )
        {
            return "Expiring caches for repository group " + getRepositoryGroupName() + " from path "
                + getResourceStorePath() + " and below.";
        }
        else if ( getRepositoryId() != null )
        {
            return "Expiring caches for repository " + getRepositoryName() + " from path " + getResourceStorePath()
                + " and below.";
        }
        else
        {
            return "Expiring caches for all registered repositories from path " + getResourceStorePath()
                + " and below.";
        }
    }

}
