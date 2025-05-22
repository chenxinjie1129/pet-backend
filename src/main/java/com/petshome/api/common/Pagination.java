package com.petshome.api.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分页信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "分页信息")
public class Pagination implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数", example = "10", position = 1)
    private Integer totalPage;

    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码", example = "1", position = 2)
    private Integer currentPage;

    /**
     * 每页条数
     */
    @ApiModelProperty(value = "每页条数", example = "20", position = 3)
    private Integer pagePieces;

    /**
     * 总条数
     */
    @ApiModelProperty(value = "总条数", example = "200", position = 4)
    private Long totalPieces;
}