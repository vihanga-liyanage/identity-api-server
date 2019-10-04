/*
* Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.wso2.carbon.identity.rest.api.server.email.template.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.wso2.carbon.identity.rest.api.server.email.template.v1.model.EmailTemplateTypeWithID;
import org.wso2.carbon.identity.rest.api.server.email.template.v1.model.EmailTemplateTypeWithTemplates;
import org.wso2.carbon.identity.rest.api.server.email.template.v1.model.EmailTemplateTypeWithoutTemplates;
import org.wso2.carbon.identity.rest.api.server.email.template.v1.model.EmailTemplateWithID;
import org.wso2.carbon.identity.rest.api.server.email.template.v1.model.Error;
import org.wso2.carbon.identity.rest.api.server.email.template.v1.model.InlineResponse201;
import java.util.List;
import org.wso2.carbon.identity.rest.api.server.email.template.v1.EmailApiService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import io.swagger.annotations.*;

import javax.validation.constraints.*;

@Path("/email")
@Api(description = "The email API")

public class EmailApi  {

    @Autowired
    private EmailApiService delegate;

    @Valid
    @POST
    @Path("/template-types/{template-type-id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Add a new email template to an existing email template type.", notes = "Adds a new email template an existing email template type in the system. The locale of the new email template should not already exists in the respective email template type.  <b>Permission required:</b> * /permission/admin/manage/? ", response = InlineResponse201.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Email Templates", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Item Created", response = InlineResponse201.class),
        @ApiResponse(code = 400, message = "Invalid input request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
        @ApiResponse(code = 409, message = "Item Already Exists", response = Error.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    public Response addEmailTemplate(@ApiParam(value = "Email Template Type ID",required=true) @PathParam("template-type-id") String templateTypeId, @ApiParam(value = "Email template to be added." ) @Valid EmailTemplateWithID emailTemplateWithID) {

        return delegate.addEmailTemplate(templateTypeId,  emailTemplateWithID );
    }

    @Valid
    @POST
    @Path("/template-types")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Add a new email template type.", notes = "Adds a new email template type to the system. An email template type can have any number of email templates.  * Attribute _**displayName**_ of the template type should be unique.  <b>Permission required:</b> * /permission/admin/manage/? ", response = EmailTemplateTypeWithoutTemplates.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Email Template Types", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Item Created", response = EmailTemplateTypeWithoutTemplates.class),
        @ApiResponse(code = 400, message = "Invalid input request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
        @ApiResponse(code = 409, message = "Item Already Exists", response = Error.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    public Response addEmailTemplateType(@ApiParam(value = "Email template type to be added." ) @Valid EmailTemplateTypeWithTemplates emailTemplateTypeWithTemplates) {

        return delegate.addEmailTemplateType(emailTemplateTypeWithTemplates );
    }

    @Valid
    @DELETE
    @Path("/template-types/{template-type-id}/templates/{template-id}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Removes an email template.", notes = "Removes an email template identified by the template-type-id and the template-id.  <b>Permission required:</b>   * /permission/admin/manage/identity/? ", response = Void.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Email Templates", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Item Deleted", response = Void.class),
        @ApiResponse(code = 400, message = "Invalid input request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
        @ApiResponse(code = 404, message = "The specified resource is not found", response = Error.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    public Response deleteEmailTemplate(@ApiParam(value = "Email Template Type ID",required=true) @PathParam("template-type-id") String templateTypeId, @ApiParam(value = "Email template ID. This should be a valid locale.",required=true) @PathParam("template-id") String templateId) {

        return delegate.deleteEmailTemplate(templateTypeId,  templateId );
    }

    @Valid
    @DELETE
    @Path("/template-types/{template-type-id}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Removes an email template type.", notes = "Removes an existing email template type with all it's email templates from the system.  <b>Permission required:</b> * /permission/admin/manage/identity/challenge/delete ", response = Void.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Email Template Types", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Item Deleted", response = Void.class),
        @ApiResponse(code = 400, message = "Invalid input request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
        @ApiResponse(code = 404, message = "The specified resource is not found", response = Error.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    public Response deleteEmailTemplateType(@ApiParam(value = "Email Template Type ID",required=true) @PathParam("template-type-id") String templateTypeId) {

        return delegate.deleteEmailTemplateType(templateTypeId );
    }

    @Valid
    @GET
    @Path("/template-types")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve all the Email Template Types.", notes = "Retrieve all the Email Template Types in the system, with limited details of the email templates.  <b>Permission required:</b> * /permission/admin/manage/? ", response = EmailTemplateTypeWithoutTemplates.class, responseContainer = "List", authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Email Template Types", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Search results matching the given criteria.", response = EmailTemplateTypeWithoutTemplates.class, responseContainer = "List"),
        @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    public Response getAllEmailTemplateTypes(    @Valid @Min(0)@ApiParam(value = "Maximum number of records to return. _<b>This option is not yet supported.<b>_")  @QueryParam("limit") Integer limit,     @Valid @Min(0)@ApiParam(value = "Number of records to skip for pagination. _<b>This option is not yet supported.<b>_")  @QueryParam("offset") Integer offset,     @Valid@ApiParam(value = "Define the order how the retrieved records should be sorted. _<b>This option is not yet supported.<b>_", allowableValues="asc, desc")  @QueryParam("sort") String sort,     @Valid@ApiParam(value = "Attribute by which the retrieved records should be sorted. _<b>This option is not yet supported.<b>_")  @QueryParam("sortBy") String sortBy) {

        return delegate.getAllEmailTemplateTypes(limit,  offset,  sort,  sortBy );
    }

    @Valid
    @GET
    @Path("/template-types/{template-type-id}/templates/{template-id}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieves a single email template.", notes = "Retrieves the email template that matches to the template-type-id and the template-id  <b>Permission required:</b>   * /permission/admin/manage/identity/? ", response = EmailTemplateWithID.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Email Templates", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Search results matching the given criteria.", response = EmailTemplateWithID.class),
        @ApiResponse(code = 400, message = "Invalid input request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
        @ApiResponse(code = 404, message = "The specified resource is not found", response = Error.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    public Response getEmailTemplate(@ApiParam(value = "Email Template Type ID",required=true) @PathParam("template-type-id") String templateTypeId, @ApiParam(value = "Email template ID. This should be a valid locale.",required=true) @PathParam("template-id") String templateId,     @Valid @Min(0)@ApiParam(value = "Maximum number of records to return. _<b>This option is not yet supported.<b>_")  @QueryParam("limit") Integer limit,     @Valid @Min(0)@ApiParam(value = "Number of records to skip for pagination. _<b>This option is not yet supported.<b>_")  @QueryParam("offset") Integer offset,     @Valid@ApiParam(value = "Define the order how the retrieved records should be sorted. _<b>This option is not yet supported.<b>_", allowableValues="asc, desc")  @QueryParam("sort") String sort,     @Valid@ApiParam(value = "Attribute by which the retrieved records should be sorted. _<b>This option is not yet supported.<b>_")  @QueryParam("sortBy") String sortBy) {

        return delegate.getEmailTemplate(templateTypeId,  templateId,  limit,  offset,  sort,  sortBy );
    }

    @Valid
    @GET
    @Path("/template-types/{template-type-id}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve the email template type corresponds to the template type id.", notes = "Retrieve the email template type in the system identified by the template-type-id.  <b>Permission required:</b> * /permission/admin/manage/identity/challenge/view ", response = EmailTemplateTypeWithID.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Email Template Types", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Search results matching the given criteria.", response = EmailTemplateTypeWithID.class),
        @ApiResponse(code = 400, message = "Invalid input request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
        @ApiResponse(code = 404, message = "The specified resource is not found", response = Error.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    public Response getEmailTemplateType(@ApiParam(value = "Email Template Type ID",required=true) @PathParam("template-type-id") String templateTypeId,     @Valid @Min(0)@ApiParam(value = "Maximum number of records to return. _<b>This option is not yet supported.<b>_")  @QueryParam("limit") Integer limit,     @Valid @Min(0)@ApiParam(value = "Number of records to skip for pagination. _<b>This option is not yet supported.<b>_")  @QueryParam("offset") Integer offset,     @Valid@ApiParam(value = "Define the order how the retrieved records should be sorted. _<b>This option is not yet supported.<b>_", allowableValues="asc, desc")  @QueryParam("sort") String sort,     @Valid@ApiParam(value = "Attribute by which the retrieved records should be sorted. _<b>This option is not yet supported.<b>_")  @QueryParam("sortBy") String sortBy) {

        return delegate.getEmailTemplateType(templateTypeId,  limit,  offset,  sort,  sortBy );
    }

    @Valid
    @GET
    @Path("/template-types/{template-type-id}/templates")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve the list of email templates in the template type id.", notes = "Retrieve the set of locale objects corresponds to the template type id.  <b>Permission required:</b>   * /permission/admin/manage/identity/challenge/view ", response = String.class, responseContainer = "List", authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Email Templates", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Search results matching the given criteria.", response = String.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid input request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
        @ApiResponse(code = 404, message = "The specified resource is not found", response = Error.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    public Response getTemplatesListOfEmailTemplateType(@ApiParam(value = "Email Template Type ID",required=true) @PathParam("template-type-id") String templateTypeId,     @Valid @Min(0)@ApiParam(value = "Maximum number of records to return. _<b>This option is not yet supported.<b>_")  @QueryParam("limit") Integer limit,     @Valid @Min(0)@ApiParam(value = "Number of records to skip for pagination. _<b>This option is not yet supported.<b>_")  @QueryParam("offset") Integer offset,     @Valid@ApiParam(value = "Define the order how the retrieved records should be sorted. _<b>This option is not yet supported.<b>_", allowableValues="asc, desc")  @QueryParam("sort") String sort,     @Valid@ApiParam(value = "Attribute by which the retrieved records should be sorted. _<b>This option is not yet supported.<b>_")  @QueryParam("sortBy") String sortBy) {

        return delegate.getTemplatesListOfEmailTemplateType(templateTypeId,  limit,  offset,  sort,  sortBy );
    }

    @Valid
    @PUT
    @Path("/template-types/{template-type-id}/templates/{template-id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Replace an existing email template.", notes = "Replace the email template identified by the template-type-id and the template-id.  <b>Permission required:</b>   * /permission/admin/manage/? ", response = Void.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Email Templates", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Item Updated", response = Void.class),
        @ApiResponse(code = 400, message = "Invalid input request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
        @ApiResponse(code = 404, message = "The specified resource is not found", response = Error.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    public Response updateEmailTemplate(@ApiParam(value = "Email Template Type ID",required=true) @PathParam("template-type-id") String templateTypeId, @ApiParam(value = "Email template ID. This should be a valid locale.",required=true) @PathParam("template-id") String templateId, @ApiParam(value = "Email templates for the template type" ) @Valid EmailTemplateWithID emailTemplateWithID) {

        return delegate.updateEmailTemplate(templateTypeId,  templateId,  emailTemplateWithID );
    }

    @Valid
    @PUT
    @Path("/template-types/{template-type-id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Replace all email templates of the respective email template type", notes = "Replace all email templates of the respective email template type with the newly provided email templates.  <b>Permission required:</b> * /permission/admin/manage/? ", response = Void.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Email Template Types" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Item Updated", response = Void.class),
        @ApiResponse(code = 400, message = "Invalid input request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
        @ApiResponse(code = 404, message = "The specified resource is not found", response = Error.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    public Response updateEmailTemplateType(@ApiParam(value = "Email Template Type ID",required=true) @PathParam("template-type-id") String templateTypeId, @ApiParam(value = "Email templates for the template type" ) @Valid List<EmailTemplateWithID> emailTemplateWithID) {

        return delegate.updateEmailTemplateType(templateTypeId,  emailTemplateWithID );
    }

}
