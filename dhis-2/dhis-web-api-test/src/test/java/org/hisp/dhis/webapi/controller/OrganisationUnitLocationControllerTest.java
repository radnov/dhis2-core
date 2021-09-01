/*
 * Copyright (c) 2004-2021, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.hisp.dhis.webapi.controller;

import static org.hisp.dhis.webapi.utils.WebClientUtils.assertStatus;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.hisp.dhis.webapi.DhisControllerConvenienceTest;
import org.hisp.dhis.webapi.json.JsonArray;
import org.hisp.dhis.webapi.json.JsonBoolean;
import org.hisp.dhis.webapi.json.JsonDocument.JsonNodeType;
import org.junit.Test;
import org.springframework.http.HttpStatus;

/**
 * Tests the
 * {@link org.hisp.dhis.webapi.controller.organisationunit.OrganisationUnitLocationController}
 * using (mocked) REST requests.
 *
 * @author Jan Bernitt
 */
public class OrganisationUnitLocationControllerTest extends DhisControllerConvenienceTest
{
    @Test
    public void testGetParentByLocation()
    {
        JsonArray parents = GET( "/organisationUnitLocations/orgUnitByLocation?longitude=23.1&latitude=56.2" )
            .content( HttpStatus.OK );
        assertTrue( parents.isArray() );
        assertTrue( parents.isEmpty() );
    }

    @Test
    public void testCheckLocationWithinOrgUnit()
    {
        String ouId = assertStatus( HttpStatus.CREATED,
            POST( "/organisationUnits/", "{'name':'My Unit', 'shortName':'OU1', 'openingDate': '2020-01-01'}" ) );

        JsonBoolean isWithin = GET(
            "/organisationUnitLocations/locationWithinOrgUnitBoundary?longitude=23.1&latitude=56.2&orgUnitUid={ou}",
            ouId ).content( HttpStatus.OK );
        assertEquals( JsonNodeType.BOOLEAN, isWithin.node().getType() );
        assertFalse( isWithin.booleanValue() );
    }
}
