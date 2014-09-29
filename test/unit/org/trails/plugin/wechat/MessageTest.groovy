package org.trails.plugin.wechat

import org.grails.plugin.wechat.message.EventType
import org.grails.plugin.wechat.message.Message
import org.grails.plugin.wechat.message.MsgType

import static org.junit.Assert.assertEquals

/**
 * Created by hhxiao on 9/29/14.
 */
class MessageTest extends GroovyTestCase {

    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    void testParsingTextMessage() {
        String text = """
<xml>
 <ToUserName><![CDATA[toUser]]></ToUserName>
 <FromUserName><![CDATA[fromUser]]></FromUserName>
 <CreateTime>1348831860</CreateTime>
 <MsgType><![CDATA[text]]></MsgType>
 <Content><![CDATA[this is a test]]></Content>
 <MsgId>1234567890123456</MsgId>
 </xml>"""
        def message = Message.fromXml(text)
        assertEquals(message.msgType, MsgType.text)
        assertEquals(message.content, 'this is a test')
        assertEquals(message.msgId, 1234567890123456L)
        assertEquals(message.createTime, 1348831860L)
        assertEquals(message.toUserName, 'toUser')
        assertEquals(message.fromUserName, 'fromUser')
    }

    void testParsingImageMessage() {
        String text = """
 <xml>
 <ToUserName><![CDATA[toUser]]></ToUserName>
 <FromUserName><![CDATA[fromUser]]></FromUserName>
 <CreateTime>1348831860</CreateTime>
 <MsgType><![CDATA[image]]></MsgType>
 <PicUrl><![CDATA[this is a url]]></PicUrl>
 <MediaId><![CDATA[media_id]]></MediaId>
 <MsgId>1234567890123456</MsgId>
 </xml>
 """
        def message = Message.fromXml(text)
        assertEquals(message.msgType, MsgType.image)
        assertEquals(message.picUrl, 'this is a url')
        assertEquals(message.mediaId, 'media_id')
    }

    void testParsingVoiceMessage() {
        String text = """
<xml>
<ToUserName><![CDATA[toUser]]></ToUserName>
<FromUserName><![CDATA[fromUser]]></FromUserName>
<CreateTime>1357290913</CreateTime>
<MsgType><![CDATA[voice]]></MsgType>
<MediaId><![CDATA[media_id]]></MediaId>
<Format><![CDATA[Format]]></Format>
<MsgId>1234567890123456</MsgId>
</xml>
 """
        def message = Message.fromXml(text)
        assertEquals(message.msgType, MsgType.voice)
        assertEquals(message.format, 'Format')
        assertEquals(message.mediaId, 'media_id')
    }

    void testParsingVideoMessage() {
        String text = """
<xml>
<ToUserName><![CDATA[toUser]]></ToUserName>
<FromUserName><![CDATA[fromUser]]></FromUserName>
<CreateTime>1357290913</CreateTime>
<MsgType><![CDATA[video]]></MsgType>
<MediaId><![CDATA[media_id]]></MediaId>
<ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>
<MsgId>1234567890123456</MsgId>
</xml>
"""
        def message = Message.fromXml(text)
        assertEquals(message.msgType, MsgType.video)
        assertEquals(message.thumbMediaId, 'thumb_media_id')
        assertEquals(message.mediaId, 'media_id')
    }

    void testParsingLocationMessage() {
        String text = """
<xml>
<ToUserName><![CDATA[toUser]]></ToUserName>
<FromUserName><![CDATA[fromUser]]></FromUserName>
<CreateTime>1351776360</CreateTime>
<MsgType><![CDATA[location]]></MsgType>
<Location_X>23.134521</Location_X>
<Location_Y>113.358803</Location_Y>
<Scale>20</Scale>
<Label><![CDATA[位置信息]]></Label>
<MsgId>1234567890123456</MsgId>
</xml>
 """
        def message = Message.fromXml(text)
        assertEquals(message.msgType, MsgType.location)
        assertEquals(message.scale, '20')
        assertEquals(message.label, '位置信息')
        assertEquals(message.location_X, '23.134521')
        assertEquals(message.location_Y, '113.358803')
    }

    void testParsingLinkMessage() {
        String text = """
<xml>
<ToUserName><![CDATA[toUser]]></ToUserName>
<FromUserName><![CDATA[fromUser]]></FromUserName>
<CreateTime>1351776360</CreateTime>
<MsgType><![CDATA[link]]></MsgType>
<Title><![CDATA[公众平台官网链接]]></Title>
<Description><![CDATA[公众平台官网链接]]></Description>
<Url><![CDATA[url]]></Url>
<MsgId>1234567890123456</MsgId>
</xml>
"""
        def message = Message.fromXml(text)
        assertEquals(message.msgType, MsgType.link)
        assertEquals(message.title, '公众平台官网链接')
        assertEquals(message.description, '公众平台官网链接')
        assertEquals(message.url, 'url')
    }

    void testParsingEventMessage() {
        String text = """
<xml>
<ToUserName><![CDATA[toUser]]></ToUserName>
<FromUserName><![CDATA[FromUser]]></FromUserName>
<CreateTime>123456789</CreateTime>
<MsgType><![CDATA[event]]></MsgType>
<Event><![CDATA[SCAN]]></Event>
<EventKey><![CDATA[SCENE_VALUE]]></EventKey>
<Ticket><![CDATA[TICKET]]></Ticket>
</xml>
"""
        def message = Message.fromXml(text)
        assertEquals(message.msgType, MsgType.event)
        assertEquals(message.event, EventType.SCAN)
        assertEquals(message.eventKey, 'SCENE_VALUE')
        assertEquals(message.ticket, 'TICKET')
    }
}
