package com.gustoexpedition.common.util;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * packageName    : com.gustoexpedition.common.util
 * fileName       : MessageUtil
 * author         : fddsg
 * date           : 2026-01-15
 * description    :
 */
@Component
public class MessageUtil {
    private final MessageSource messageSource;

    public MessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * methodName : getMessage
     * author : IM HYUN WOO
     * description : get common message by string code
     *
     * @param code
     * @return string
     */
    public String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.getDefault());
    }

    /**
     * methodName : getFormattedMessage
     * author : IM HYUN WOO
     * description : get formatted common message
     *
     * @param code
     * @return string
     */
    public String getFormattedMessage(String code) {
        String message = getMessage(code);
        return String.format("[%s] %s", code, message);
    }
}
