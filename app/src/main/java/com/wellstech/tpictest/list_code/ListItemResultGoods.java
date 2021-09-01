package com.wellstech.tpictest.list_code;

import org.json.JSONException;
import org.json.JSONObject;

public class ListItemResultGoods {
    private JSONObject item;
    private String goodsId;
    private String goodsName;
    private String goodsPrice;
    private String goodsImgUrl;
    private String goodsCategory;
    private String goodsRate;
    private String goodsLike;
    private String goodsReviewCount;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId() {
        try {
            this.goodsId = item.getString("goodsNo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName() {
        try {
            this.goodsName = item.getString("goodsNm");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice() {
        try {
            this.goodsPrice = item.getString("fixedPrice");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsImgUrl() {
        return goodsImgUrl;
    }

    public void setGoodsImgUrl() {
        try {
            this.goodsImgUrl = item.getString("detailImageData");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setGoodsImgUrl(String goodsImgUrl) {
        this.goodsImgUrl = goodsImgUrl;
    }

    public String getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(String goodsCategory) {
        this.goodsCategory = goodsCategory;
    }

    public String getGoodsRate() {
        return goodsRate;
    }

    public void setGoodsRate(String goodsRate) {
        this.goodsRate = goodsRate;
    }

    public String getGoodsLike() {
        return goodsLike;
    }

    public void setGoodsLike(String goodsLike) {
        this.goodsLike = goodsLike;
    }

    public String getGoodsReviewCount() {
        return goodsReviewCount;
    }

    public void setGoodsReviewCount(String goodsReviewCount) {
        this.goodsReviewCount = goodsReviewCount;
    }

    public JSONObject getItem() {
        return item;
    }

    public void setItem(JSONObject item) {
        this.item = item;
    }
}
