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

package org.wso2.carbon.identity.rest.api.server.email.template.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.wso2.carbon.identity.rest.api.server.email.template.v1.model.EmailTemplateWithID;
import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;
import javax.validation.Valid;
import javax.xml.bind.annotation.*;

public class EmailTemplateTypeWithTemplates  {
  
    private String displayName;
    private List<EmailTemplateWithID> templates = new ArrayList<>();


    /**
    * Display name of the email template type.
    **/
    public EmailTemplateTypeWithTemplates displayName(String displayName) {

        this.displayName = displayName;
        return this;
    }
    
    @ApiModelProperty(example = "Account Confirmation", required = true, value = "Display name of the email template type.")
    @JsonProperty("displayName")
    @Valid
    @NotNull(message = "Property displayName cannot be null.")

    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
    * Email templates for the template type.
    **/
    public EmailTemplateTypeWithTemplates templates(List<EmailTemplateWithID> templates) {

        this.templates = templates;
        return this;
    }
    
    @ApiModelProperty(required = true, value = "Email templates for the template type.")
    @JsonProperty("templates")
    @Valid
    @NotNull(message = "Property templates cannot be null.")

    public List<EmailTemplateWithID> getTemplates() {
        return templates;
    }
    public void setTemplates(List<EmailTemplateWithID> templates) {
        this.templates = templates;
    }

    public EmailTemplateTypeWithTemplates addTemplatesItem(EmailTemplateWithID templatesItem) {
        this.templates.add(templatesItem);
        return this;
    }

    

    @Override
    public boolean equals(java.lang.Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmailTemplateTypeWithTemplates emailTemplateTypeWithTemplates = (EmailTemplateTypeWithTemplates) o;
        return Objects.equals(this.displayName, emailTemplateTypeWithTemplates.displayName) &&
            Objects.equals(this.templates, emailTemplateTypeWithTemplates.templates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(displayName, templates);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("class EmailTemplateTypeWithTemplates {\n");
        
        sb.append("    displayName: ").append(toIndentedString(displayName)).append("\n");
        sb.append("    templates: ").append(toIndentedString(templates)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
    * Convert the given object to string with each line indented by 4 spaces
    * (except the first line).
    */
    private String toIndentedString(java.lang.Object o) {

        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n");
    }
}

