package me.chanjar.weixin.mp.util.json;

import com.google.gson.*;
import me.chanjar.weixin.common.api.WxConsts.KefuMsgType;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;

public class WxMpKefuMessageGsonAdapter implements JsonSerializer<WxMpKefuMessage> {

  @Override
  public JsonElement serialize(WxMpKefuMessage message, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject messageJson = new JsonObject();
    messageJson.addProperty("touser", message.getToUser());
    messageJson.addProperty("msgtype", message.getMsgType());

    switch (message.getMsgType()) {
      case KefuMsgType.TEXT:
        JsonObject text = new JsonObject();
        text.addProperty("content", message.getContent());
        messageJson.add("text", text);
        break;
      case KefuMsgType.IMAGE:
        JsonObject image = new JsonObject();
        image.addProperty("media_id", message.getMediaId());
        messageJson.add("image", image);
        break;
      case KefuMsgType.VOICE:
        JsonObject voice = new JsonObject();
        voice.addProperty("media_id", message.getMediaId());
        messageJson.add("voice", voice);
        break;
      case KefuMsgType.VIDEO:
        JsonObject video = new JsonObject();
        video.addProperty("media_id", message.getMediaId());
        video.addProperty("thumb_media_id", message.getThumbMediaId());
        video.addProperty("title", message.getTitle());
        video.addProperty("description", message.getDescription());
        messageJson.add("video", video);
        break;
      case KefuMsgType.MUSIC:
        JsonObject music = new JsonObject();
        music.addProperty("title", message.getTitle());
        music.addProperty("description", message.getDescription());
        music.addProperty("thumb_media_id", message.getThumbMediaId());
        music.addProperty("musicurl", message.getMusicUrl());
        music.addProperty("hqmusicurl", message.getHqMusicUrl());
        messageJson.add("music", music);
        break;
      case KefuMsgType.NEWS:
        JsonObject newsJsonObject = new JsonObject();
        JsonArray articleJsonArray = new JsonArray();
        for (WxMpKefuMessage.WxArticle article : message.getArticles()) {
          JsonObject articleJson = new JsonObject();
          articleJson.addProperty("title", article.getTitle());
          articleJson.addProperty("description", article.getDescription());
          articleJson.addProperty("url", article.getUrl());
          articleJson.addProperty("picurl", article.getPicUrl());
          articleJsonArray.add(articleJson);
        }
        newsJsonObject.add("articles", articleJsonArray);
        messageJson.add("news", newsJsonObject);
        break;
      case KefuMsgType.MPNEWS:
        JsonObject json = new JsonObject();
        json.addProperty("media_id", message.getMpNewsMediaId());
        messageJson.add("mpnews", json);
        break;
      case KefuMsgType.WXCARD:
        JsonObject wxcard = new JsonObject();
        wxcard.addProperty("card_id", message.getCardId());
        messageJson.add("wxcard", wxcard);
        break;
      case KefuMsgType.MINIPROGRAMPAGE:
        JsonObject miniProgramPage = new JsonObject();
        miniProgramPage.addProperty("title", message.getTitle());
        miniProgramPage.addProperty("appid", message.getMiniProgramAppId());
        miniProgramPage.addProperty("pagepath", message.getMiniProgramPagePath());
        miniProgramPage.addProperty("thumb_media_id", message.getThumbMediaId());
        messageJson.add("miniprogrampage", miniProgramPage);
        break;
      case KefuMsgType.MSGMENU: {
        JsonObject msgMenu = new JsonObject();
        JsonArray array = new JsonArray();
        for (String s : message.getMsgMenuList()) {
          JsonObject innerJson = new JsonObject();
          final String[] split = s.split(",");
          innerJson.addProperty("id", split[0]);
          innerJson.addProperty("content", split[1]);
          array.add(innerJson);
        }
        msgMenu.addProperty("head_content", message.getHeadContent());
        msgMenu.add("list", array);
        msgMenu.addProperty("tail_content", message.getTailContent());
        messageJson.add("msgmenu", msgMenu);
        break;
      }
      default: {
        throw new RuntimeException("非法消息类型，暂不支持");
      }
    }

    if (StringUtils.isNotBlank(message.getKfAccount())) {
      JsonObject newsJsonObject = new JsonObject();
      newsJsonObject.addProperty("kf_account", message.getKfAccount());
      messageJson.add("customservice", newsJsonObject);
    }

    return messageJson;
  }

}
