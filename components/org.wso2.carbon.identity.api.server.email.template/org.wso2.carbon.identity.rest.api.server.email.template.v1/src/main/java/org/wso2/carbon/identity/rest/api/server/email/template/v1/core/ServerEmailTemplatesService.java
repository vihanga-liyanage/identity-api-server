/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.rest.api.server.email.template.v1.core;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.email.mgt.exceptions.I18nEmailMgtException;
import org.wso2.carbon.email.mgt.model.EmailTemplate;
import org.wso2.carbon.identity.api.server.common.ContextLoader;
import org.wso2.carbon.identity.api.server.common.error.APIError;
import org.wso2.carbon.identity.api.server.common.error.ErrorResponse;
import org.wso2.carbon.identity.api.server.email.template.common.Constants;
import org.wso2.carbon.identity.api.server.email.template.common.EmailTemplatesServiceHolder;
import org.wso2.carbon.identity.rest.api.server.email.template.v1.model.EmailTemplateTypeWithID;
import org.wso2.carbon.identity.rest.api.server.email.template.v1.model.EmailTemplateTypeWithoutTemplates;
import org.wso2.carbon.identity.rest.api.server.email.template.v1.model.EmailTemplateWithID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Response;

import static org.wso2.carbon.identity.api.server.common.Util.base64URLDecode;
import static org.wso2.carbon.identity.api.server.common.Util.base64URLEncode;
import static org.wso2.carbon.identity.api.server.email.template.common.Constants.EMAIL_TEMPLATES_API_BASE_PATH;
import static org.wso2.carbon.identity.api.server.email.template.common.Constants.EMAIL_TEMPLATES_PATH;
import static org.wso2.carbon.identity.api.server.email.template.common.Constants.EMAIL_TEMPLATE_TYPES_PATH;

/**
 * Call internal osgi services to perform email templates related operations.
 */
public class ServerEmailTemplatesService {

    private static final Log log = LogFactory.getLog(ServerEmailTemplatesService.class);
    private static final String PATH_SEPARATOR = "/";
    private static final String LOCALE_CODE_DELIMITER = "_";

    /**
     * Return all email template types in the system with limited information of the templates inside.
     *
     * @param limit  Limit the number of email template types in the response. **Not supported at the moment**
     * @param offset Offset to be used with the limit parameter. **Not supported at the moment**
     * @param sort   Sort the response in ascending order or descending order. **Not supported at the moment**
     * @param sortBy Element to sort the responses. **Not supported at the moment**
     * @return A list of email template types.
     */
    public List<EmailTemplateTypeWithoutTemplates> getAllEmailTemplateTypes(Integer limit, Integer offset,
                                                                            String sort, String sortBy) {

        try {
            List<EmailTemplate> legacyEmailTemplates = EmailTemplatesServiceHolder.getEmailTemplateManager().
                    getAllEmailTemplates(ContextLoader.getTenantDomainFromContext());
            return buildEmailTemplateTypeWithoutTemplatesList(legacyEmailTemplates);
        } catch (I18nEmailMgtException e) {
            throw handleI18nEmailMgtException(e, Constants.ErrorMessage.ERROR_RETRIEVING_EMAIL_TEMPLATE_TYPES);
        }
    }

    /**
     * Return a specific email template type identified by the email template type id.
     *
     * @param templateTypeId Email template type id.
     * @param limit          Limit the number of email template types in the response. **Not supported at the moment**
     * @param offset         Offset to be used with the limit parameter. **Not supported at the moment**
     * @param sort           Sort the response in ascending order or descending order. **Not supported at the moment**
     * @param sortBy         Element to sort the responses. **Not supported at the moment**
     * @return The email template type identified by the given id, 404 if not found.
     */
    public EmailTemplateTypeWithID getEmailTemplateType(String templateTypeId, Integer limit, Integer offset,
                                                        String sort, String sortBy) {

        try {
            List<EmailTemplate> legacyEmailTemplates = EmailTemplatesServiceHolder.getEmailTemplateManager().
                    getAllEmailTemplates(ContextLoader.getTenantDomainFromContext());
            return getMatchingEmailTemplateType(legacyEmailTemplates, templateTypeId);
        } catch (I18nEmailMgtException e) {
            throw handleI18nEmailMgtException(e, Constants.ErrorMessage.ERROR_RETRIEVING_EMAIL_TEMPLATE_TYPES);
        }
    }

    /**
     * Return a list of locations of all available templates in a specific email template type.
     *
     * @param templateTypeId Email template type id.
     * @param limit          Limit the number of email template types in the response. **Not supported at the moment**
     * @param offset         Offset to be used with the limit parameter. **Not supported at the moment**
     * @param sort           Sort the response in ascending order or descending order. **Not supported at the moment**
     * @param sortBy         Element to sort the responses. **Not supported at the moment**
     * @return List of templates in the template type identified by the given id, 404 if not found.
     */
    public List<String> getTemplatesListOfEmailTemplateType(String templateTypeId, Integer limit, Integer offset,
                                                    String sort, String sortBy) {

        try {
            List<EmailTemplate> legacyEmailTemplates = EmailTemplatesServiceHolder.getEmailTemplateManager().
                    getAllEmailTemplates(ContextLoader.getTenantDomainFromContext());
            return getTemplatesListOfEmailTemplateType(legacyEmailTemplates, templateTypeId);
        } catch (I18nEmailMgtException e) {
            throw handleI18nEmailMgtException(e, Constants.ErrorMessage.ERROR_RETRIEVING_EMAIL_TEMPLATE_TYPES);
        }
    }

    /**
     * Iterate through a given legacy email templates list, extract a list of templates in it and build a list of
     * locations of those templates.
     *
     * @param legacyEmailTemplates List of legacy email templates.
     * @param templateTypeId       Email template type to be extracted.
     * @return Extracted locations list in the template type, 404 if not found.
     */
    private  List<String> getTemplatesListOfEmailTemplateType(List<EmailTemplate> legacyEmailTemplates,
                                                         String templateTypeId) {

        List<String> templates = new ArrayList<>();
        String decodedTemplateTypeId = base64URLDecode(templateTypeId);
        for (EmailTemplate legacyTemplate : legacyEmailTemplates) {
            if (decodedTemplateTypeId.equals(legacyTemplate.getTemplateType())) {
                String templateLocation = getTemplateLocation(templateTypeId, legacyTemplate.getLocale());
                templates.add(templateLocation);
            }
        }
        if (templates.isEmpty()) {
            throw handleError(Response.Status.NOT_FOUND, Constants.ErrorMessage.ERROR_EMAIL_TEMPLATE_TYPE_NOT_FOUND);
        }
        return templates;
    }

    /**
     * Create a list EmailTemplateTypeWithoutTemplates objects by reading a legacy EmailTemplate list.
     *
     * @param legacyEmailTemplates List of EmailTemplate objects.
     * @return List of EmailTemplateTypeWithoutTemplates objects.
     */
    private List<EmailTemplateTypeWithoutTemplates> buildEmailTemplateTypeWithoutTemplatesList(
            List<EmailTemplate> legacyEmailTemplates) {

        Map<String, EmailTemplateTypeWithoutTemplates> templateTypeMap = new HashMap<>();
        for (EmailTemplate emailTemplate : legacyEmailTemplates) {
            if (!templateTypeMap.containsKey(emailTemplate.getTemplateType())) {

                EmailTemplateTypeWithoutTemplates emailTemplateType = new EmailTemplateTypeWithoutTemplates();
                // Set display name.
                emailTemplateType.setDisplayName(emailTemplate.getTemplateDisplayName());
                // Set id.
                String templateTypeId = base64URLEncode(emailTemplate.getTemplateType());
                emailTemplateType.setId(templateTypeId);
                // Set location.
                emailTemplateType.setLocation(getTemplateTypeLocation(templateTypeId));

                templateTypeMap.put(emailTemplate.getTemplateType(), emailTemplateType);
            }
        }

        return new ArrayList<>(templateTypeMap.values());
    }

    private String getTemplateTypeLocation(String templateTypeId) {

        String location = EMAIL_TEMPLATES_API_BASE_PATH + EMAIL_TEMPLATE_TYPES_PATH + PATH_SEPARATOR +
                templateTypeId;
        return ContextLoader.buildURIForBody(location).toString();
    }

    private String getTemplateLocation(String templateTypeId, String templateId) {

        String templateLocation = getTemplateTypeLocation(templateTypeId);
        return templateLocation + EMAIL_TEMPLATES_PATH + PATH_SEPARATOR + templateId;
    }

    /**
     * Iterate through a given legacy email templates list and extract a given singe email template type with all
     * of it's templates.
     *
     * @param legacyEmailTemplates List of legacy email templates.
     * @param templateTypeId       Email template type to be extracted.
     * @return Extracted EmailTemplateTypeWithID, 404 if not found.
     */
    private EmailTemplateTypeWithID getMatchingEmailTemplateType(List<EmailTemplate> legacyEmailTemplates,
                                                                 String templateTypeId) {

        EmailTemplateTypeWithID emailTemplateType = new EmailTemplateTypeWithID();
        String decodedTemplateTypeId = base64URLDecode(templateTypeId);
        boolean isFirst = true;
        for (EmailTemplate legacyTemplate : legacyEmailTemplates) {
            if (decodedTemplateTypeId.equals(legacyTemplate.getTemplateType())) {
                if (isFirst) {
                    // Template type details should only be set once.
                    emailTemplateType.setDisplayName(legacyTemplate.getTemplateDisplayName());
                    emailTemplateType.setId(templateTypeId);
                    isFirst = false;
                }
                emailTemplateType.getTemplates().add(buildEmailTemplateWithID(legacyTemplate));
            }
        }
        if (StringUtils.isBlank(emailTemplateType.getId())) {
            throw handleError(Response.Status.NOT_FOUND, Constants.ErrorMessage.ERROR_EMAIL_TEMPLATE_TYPE_NOT_FOUND);
        }
        return emailTemplateType;
    }

    /**
     * Convert a legacy email template to a new Email Template object.
     */
    private EmailTemplateWithID buildEmailTemplateWithID(EmailTemplate legacyTemplate) {

        EmailTemplateWithID templateWithID = new EmailTemplateWithID();
        templateWithID.setId(legacyTemplate.getLocale());
        templateWithID.setContentType(legacyTemplate.getEmailContentType());
        templateWithID.setSubject(legacyTemplate.getSubject());
        templateWithID.setBody(legacyTemplate.getBody());
        templateWithID.setFooter(legacyTemplate.getFooter());
        return templateWithID;
    }

    /**
     * Handle I18nEmailMgtException, i.e. extract error description from the exception and set to the
     * API Error Response, along with an status code to be sent in the response.
     *
     * @param exception Exception thrown
     * @param errorEnum Corresponding error enum
     * @return API Error object.
     */
    private APIError handleI18nEmailMgtException(I18nEmailMgtException exception, Constants.ErrorMessage errorEnum) {

        ErrorResponse errorResponse = getErrorBuilder(errorEnum).build(log, exception, errorEnum.getDescription());

        Response.Status status;

        if (exception != null) {
            errorResponse.setDescription(exception.getMessage());
            status = Response.Status.BAD_REQUEST;
        } else {
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }
        return new APIError(status, errorResponse);
    }

    private APIError handleError(Response.Status status, Constants.ErrorMessage error) {

        return new APIError(status, getErrorBuilder(error).build());
    }

    private ErrorResponse.Builder getErrorBuilder(Constants.ErrorMessage errorMsg) {

        return new ErrorResponse.Builder().withCode(errorMsg.getCode()).
                withMessage(errorMsg.getMessage()).withDescription(errorMsg.getDescription());
    }
}
