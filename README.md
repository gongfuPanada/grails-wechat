[![Build Status](https://travis-ci.org/hhxiao/grails-wechat.svg?branch=master)](https://travis-ci.org/hhxiao/grails-wechat)

grails-wechat
=============

Grails Wechat Integration Plugin, 微信公众平台 Grails 插件 

## Introduction

Services provided:

1. WechatTokenService(Token interface，to retrieve access_token)
2. WechatResponseService(Message response interface，to send response message to client)
3. WechatCustomerService(Customer service interface，to send customer service message to client)

## Installation

To install the plugin add a dependency to BuildConfig.groovy:
~~~~~~~~~~~
compile ":wechat:0.1"
~~~~~~~~~~~

To config wechat appId, appSecret and appToken in Config.groovy:
~~~~~~~~~~~
grails.wechat.app.id='wx..................'
grails.wechat.app.secret='856..................'
grails.wechat.app.token='token..................'
~~~~~~~~~~~

To config URL for server in wechat developer center
~~~~~~~~~~~
http://${hostname}/${appname}/wechat
~~~~~~~~~~~


## Usage

Annotation based or conventional message callback declaration, message callback must accept one Message parameter(could be any one of the subclasses), and return a ResponseMessage result

~~~~~~~~~~~groovy
class SampleService {
    def wechatResponseService

    // Text message only
    ResponseMessage onText(TextMessage message) {
        wechatResponseService.responseText(message, "收到：" + message.content)
    }

    // Image message only
    ResponseMessage onImage(ImageMessage message) {
        wechatResponseService.responseNews(message, new Article([
            title: "标题一", description: '描述一', picUrl: message.picUrl
        ]), new Article(
            title: "标题二", description: '描述二', picUrl: message.picUrl
        ))
    }

    // subscribe and unsubscribe event only
    @MessageHandler(value=MsgType.event, events=[EventType.subscribe, EventType.unsubscribe])
    ResponseMessage onSubscriptionChanged(EventMessage message) {
        wechatResponseService.responseText(message, (message.event == EventType.subscribe) ? '欢迎' : '再见')
    }

    // Location message only, annotation is not necessary
    @MessageHandler(MsgType.location)
    ResponseMessage onLocationReceived(LocationMessage message) {
        wechatResponseService.responseText(message, "收到位置消息: ${message.label}")
    }

    // SCAN event only
    @MessageHandler(value=MsgType.event, events=[EventType.SCAN])
    ResponseMessage onScanned(EventMessage message) {
        wechatResponseService.responseText(message, "扫描了: ${message.eventKey}")
    }

    // CLICK event only
    @MessageHandler(value=MsgType.event, events=[EventType.CLICK])
    ResponseMessage onMenuClicked(EventMessage message) {
        wechatResponseService.responseText(message, "点击了：${message.eventKey}")
    }

    // VIEW event only
    @MessageHandler(value=MsgType.event, events=[EventType.VIEW])
    ResponseMessage onItemViewed(EventMessage message) {
        wechatResponseService.responseText(message, "查看了：${message.eventKey}")
    }

    // LOCATION event only
    @MessageHandler(value=MsgType.event, events=[EventType.LOCATION])
    ResponseMessage onLocationEvent(EventMessage message) {
        wechatResponseService.responseText(message, "收到位置事件: ${message.latitude}:${message.longitude}")
    }

    // Catch all messages
    ResponseMessage onMessage(Message message) {
        // handl the message ...
        System.out.println("Got a ${message.msgType} message")
        return null // return null to use response message from other callback
    }
}

~~~~~~~~~~~

