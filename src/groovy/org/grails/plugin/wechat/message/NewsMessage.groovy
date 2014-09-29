package org.grails.plugin.wechat.message

/**
 * Created by hhxiao on 9/29/14.
 */
class NewsMessage extends Message {
    String picUrl   // 图片链接
    String mediaId // 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
    List<Article> articles = []

    String getAdditionalResponseXml() {
        """
<ArticleCount>${articles.size()}</ArticleCount>
<Articles>
${articles.collect{it.toXml()}.join('')}
</Articles>
"""
    }
}
