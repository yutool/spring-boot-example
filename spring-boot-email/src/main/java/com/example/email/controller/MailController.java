package com.example.email.controller;

import com.example.email.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RestController
public class MailController {
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private MailService mailService;

    @GetMapping("/email/text/{toAddr}")
    public String testSimpleMail(@PathVariable String toAddr) {
        mailService.sendTextMail(toAddr,"测试文本邮箱发送","你好你好！");
        return "success";
    }

    @GetMapping("/email/html/{toAddr}")
    public String testHtmlMail(@PathVariable String toAddr) {
        String content="<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendHtmlMail(toAddr,"test simple mail",content);
        return "success";
    }

    @GetMapping("/email/attachments/{toAddr}")
    public String sendAttachmentsMail(@PathVariable String toAddr) {
        String filePath="C:\\\\Users\\\\Administrator\\\\Desktop\\\\java并发学习.txt";
        mailService.sendAttachmentsMail(toAddr, "主题：带附件的邮件", "有附件，请查收！", filePath);
        return "success";
    }


    @GetMapping("/email/inlineResource/{toAddr}")
    public String sendInlineResourceMail(@PathVariable String toAddr) {
        String rscId = "neo006";
        String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        String imgPath = "C:\\\\Users\\\\Administrator\\\\Desktop\\\\testMail.png";

        mailService.sendInlineResourceMail(toAddr, "主题：这是有图片的邮件", content, imgPath, rscId);
        return "success";
    }

    @GetMapping("/email/template/{toAddr}")
    public String sendTemplateMail(@PathVariable String toAddr) {
        // 创建邮件正文
        Context context = new Context();
        context.setVariable("title", "aaa");
        context.setVariable("content", "abc");

        // 传递 emailTemplate.html 模板需要的值，并将模板转换为 String
        String emailContent = templateEngine.process("emailTemplate", context);

        mailService.sendHtmlMail(toAddr,"主题：这是模板邮件",emailContent);
        return "success";
    }
}
