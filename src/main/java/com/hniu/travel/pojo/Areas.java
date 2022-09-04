package com.hniu.travel.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Areas {
    @TableId
    private Integer cid;
    private String cName;

}
