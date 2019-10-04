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

/**
 * Claim Management constant class.
 */
public class Constants {

    public static final String EMAIL_TEMPLATES_ERROR_CODE_PREFIX = "ETM-";
    public static final String EMAIL_TEMPLATES_API_BASE_PATH = "/email";
    public static final String EMAIL_TEMPLATE_TYPES_PATH = "/template-types";
    public static final String EMAIL_TEMPLATES_PATH = "/templates";

    /**
     * Enum for error messages.
     */
    public enum ErrorMessage {

        ERROR_RETRIEVING_EMAIL_TEMPLATE_TYPES("50002",
                "Unable to retrieve email template types.",
                "Server encountered an error while retrieving email template types."),
        ERROR_RETRIEVING_EMAIL_TEMPLATE_TYPE("50003",
                "Unable to retrieve email template type matching the given .",
                "Server encountered an error while retrieving email template type."),
        ERROR_EMAIL_TEMPLATE_TYPE_NOT_FOUND("500010",
                "Email Template Type does not exists.",
                "Specified email template type does not exist in the system, hence unable to proceed.");

        private final String code;
        private final String message;
        private final String description;

        ErrorMessage(String code, String message, String description) {

            this.code = code;
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

        @Override
        public String toString() {

            return code + " | " + message;
        }
    }
}
