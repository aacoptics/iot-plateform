package com.aac.optics.provider.organization.exception;

import com.aac.optics.common.core.exception.BaseException;

public class RoleNotFoundException extends BaseException {
    public RoleNotFoundException() {
        super(OrganizationErrorType.ROLE_NOT_FOUND);
    }

    public RoleNotFoundException(String message) {
        super(OrganizationErrorType.ROLE_NOT_FOUND, message);
    }
}
