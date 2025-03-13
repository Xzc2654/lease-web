package com.xzc.lease.web.app.vo.apartment;


import com.xzc.lease.model.entity.ApartmentInfo;
import com.xzc.lease.model.entity.GraphInfo;
import com.xzc.lease.model.entity.LabelInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "App端公寓信息")
public class ApartmentItemVo extends ApartmentInfo {

    private List<LabelInfo> labelInfoList;

    private List<GraphInfo> graphVoList;

    private BigDecimal minRent;
}
