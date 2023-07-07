package com.adnstyle.dalnu_gw.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@Setter
@ConstructorBinding
@Slf4j
@ConfigurationProperties(prefix = "spring.file-upload")
@RequiredArgsConstructor
public class AttachYml {
    
    private String uploadPath;
}
