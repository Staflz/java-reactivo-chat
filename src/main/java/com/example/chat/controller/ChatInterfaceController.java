package com.example.chat.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import reactor.core.publisher.Mono;

/**
 * Controller for serving the chat interface.
 * Provides the main HTML interface for the reactive chat application.
 */
@Controller
public class ChatInterfaceController {

    /**
     * Serves the main chat interface.
     *
     * @return the chat HTML page
     */
    @GetMapping("/")
    public Mono<String> index() {
        return Mono.just("redirect:/chat");
    }

    /**
     * Serves the chat HTML page.
     *
     * @return the chat HTML content
     */
    @GetMapping(value = "/chat", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public Mono<String> chat() {
        return loadChatHtml();
    }

    /**
     * Loads the chat HTML from static resources.
     *
     * @return the HTML content
     */
    private Mono<String> loadChatHtml() {
        return Mono.fromSupplier(() -> {
            try {
                Resource resource = new ClassPathResource("static/chat.html");
                return resource.getContentAsString(java.nio.charset.StandardCharsets.UTF_8);
            } catch (Exception e) {
                return "<h1>Error loading chat interface</h1><p>Please check if chat.html exists in static resources.</p>";
            }
        });
    }
}