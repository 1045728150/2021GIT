package com.vo;

public class ProductInfoVo {
    private String voName;
    private Integer voTypeId;  //商品类型id
    private Integer lprice;
    private Integer hprice;

    public ProductInfoVo() {
    }

    public ProductInfoVo(String voName, Integer voTypeId, Integer lprice, Integer hprice) {
        this.voName = voName;
        this.voTypeId = voTypeId;
        this.lprice = lprice;
        this.hprice = hprice;
    }

    public String getVoName() {
        return voName;
    }

    public void setVoName(String voName) {
        this.voName = voName;
    }

    public Integer getVoTypeId() {
        return voTypeId;
    }

    public void setVoTypeId(Integer voTypeId) {
        this.voTypeId = voTypeId;
    }

    public Integer getLprice() {
        return lprice;
    }

    public void setLprice(Integer lprice) {
        this.lprice = lprice;
    }

    public Integer getHprice() {
        return hprice;
    }

    public void setHprice(Integer hprice) {
        this.hprice = hprice;
    }
}
