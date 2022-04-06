package com.aac.optics.wlg.report.exception;


import com.aac.optics.common.core.exception.BaseException;

public class BusinessException extends BaseException {
    public BusinessException() {
        super(WlgReportErrorType.BUSINESS_EXCEPTION);
    }

    public BusinessException(String message) {
        super(WlgReportErrorType.BUSINESS_EXCEPTION, message);
    }
}
