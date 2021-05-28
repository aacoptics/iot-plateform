package com.aac.optics.provider.notification.entity.po;

import com.aac.optics.common.web.entity.po.BasePo;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailSend extends BasePo {
    private List<String> to;
    private String emailContent;
    private String subject;
}
