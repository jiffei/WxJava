package me.chanjar.weixin.mp.api.impl;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpWifiService;
import me.chanjar.weixin.mp.bean.wifi.WxMpWifiShopDataResult;
import me.chanjar.weixin.mp.bean.wifi.WxMpWifiShopListResult;

import static me.chanjar.weixin.mp.enums.WxMpApiUrl.Wifi.BIZWIFI_SHOP_GET;
import static me.chanjar.weixin.mp.enums.WxMpApiUrl.Wifi.BIZWIFI_SHOP_LIST;

/**
 * <pre>
 *  Created by BinaryWang on 2018/6/10.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@RequiredArgsConstructor
public class WxMpWifiServiceImpl implements WxMpWifiService {
  private final WxMpService wxMpService;

  @Override
  public WxMpWifiShopListResult listShop(int pageIndex, int pageSize) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("pageindex", pageIndex);
    json.addProperty("pagesize", pageSize);
    final String result = this.wxMpService.post(BIZWIFI_SHOP_LIST, json.toString());
    return WxMpWifiShopListResult.fromJson(result);
  }

  @Override
  public WxMpWifiShopDataResult getShopWifiInfo(int shopId) throws WxErrorException {
    JsonObject json = new JsonObject();
    json.addProperty("shop_id", shopId);
    return WxMpWifiShopDataResult.fromJson(this.wxMpService.post(BIZWIFI_SHOP_GET, json.toString()));
  }
}
