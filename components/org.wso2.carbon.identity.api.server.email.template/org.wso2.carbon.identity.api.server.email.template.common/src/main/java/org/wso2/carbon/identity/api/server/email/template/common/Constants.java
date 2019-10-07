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

package org.wso2.carbon.identity.api.server.email.template.common;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.Response.Status;

import static org.wso2.carbon.email.mgt.constants.I18nMgtConstants.ErrorCodes.EMAIL_TEMPLATE_TYPE_NODE_FOUND;

/**
 * Claim Management constant class.
 */
public class Constants {

    public static final String EMAIL_TEMPLATES_ERROR_CODE_PREFIX = "ETM-";
    public static final String EMAIL_TEMPLATES_API_BASE_PATH = "/email";
    public static final String EMAIL_TEMPLATE_TYPES_PATH = "/template-types";
    public static final String EMAIL_TEMPLATES_PATH = "/templates";

    private static final Map<String, ErrorMessage> ERROR_CODE_MAP = new HashMap<>();

    /**
     * Enum for error messages.
     */
    public enum ErrorMessage {

        ERROR_RETRIEVING_EMAIL_TEMPLATE_TYPES("50002", Status.INTERNAL_SERVER_ERROR,
                "Unable to retrieve email template types.",
                "Server encountered an error while retrieving email template types."),
        ERROR_RETRIEVING_EMAIL_TEMPLATE_TYPE("50003", Status.INTERNAL_SERVER_ERROR,
                "Unable to retrieve the email template type.",
                "Server encountered an error while retrieving the email template " +
                        "type identified by the given template-type-id."),
        ERROR_RETRIEVING_EMAIL_TEMPLATE("50004", Status.INTERNAL_SERVER_ERROR,
                "Unable to retrieve the email template.",
                "Server encountered an error while retrieving the email template " +
                        "identified by the given template-type-id and the template-id"),
        ERROR_INVALID_TEMPLATE_TYPE_ID("50005", Status.BAD_REQUEST,
                "Provided email template type is invalid.",
                "Server encountered an error while processing the given template-type-id."),
        ERROR_EMAIL_TEMPLATE_TYPE_NOT_FOUND("500010", Status.NOT_FOUND,
                "Email Template Type does not exists.",
                "Specified email template type does not exist in the system, hence unable to proceed."),
        ERROR_EMAIL_TEMPLATE_NOT_FOUND("500011", Status.NOT_FOUND,
                "Email Template does not exists.",
                "Specified email template does not exist in the system, hence unable to proceed.");

        private final String message;
        private final Status httpStatus;
        private final String code;
        private final String description;

        ErrorMessage(String code, Status httpStatus, String message, String description) {

            this.code = code;
            this.httpStatus = httpStatus;
            this.message = message;
            this.description = description;
        }

        public String getCode() {

            return EMAIL_TEMPLATES_ERROR_CODE_PREFIX + code;
        }

        public String getMessage() {

            return message;
        }

        public String getDescription() {

            return description;
        }

        public Status getHttpStatus() {

            return httpStatus;
        }

        @Override
        public String toString() {

            return code + " | " + message;
        }
    }

    static {
        ERROR_CODE_MAP.put(EMAIL_TEMPLATE_TYPE_NODE_FOUND, ErrorMessage.ERROR_EMAIL_TEMPLATE_TYPE_NOT_FOUND);
    }

    public static ErrorMessage getMappedErrorMessage(String errorCode) {

        return ERROR_CODE_MAP.get(errorCode);
    }
}
