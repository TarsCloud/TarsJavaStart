/**
 * Tencent is pleased to support the open source community by making Tars available.
 *
 * Copyright (C) 2016 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.tencent.tarstools.gensrc;


import com.tencent.tarstools.data.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@Component
public class Generator {

    @Autowired
    private Metadata metadata;

    private static final Logger logger = LoggerFactory.getLogger(Generator.class);

    public void generate() throws FileNotFoundException, UnsupportedEncodingException {
        logger.info("===========================================");
        logger.info("Generating Info");
        logger.info("SprintBoot.version: " + metadata.getSpringbootVersion());
        logger.info("GroupId: " + metadata.getMavenGroup());
        logger.info("ArtifactId: " + metadata.getMavenArtifact());
        logger.info("Name: " + metadata.getMavenName());
        logger.info("PackageName: " + metadata.getPackageName());
        logger.info("Java.version: " + metadata.getJdkVersion());
        logger.info("Type: " + metadata.getType());
        logger.info("===========================================");
        try {
            genProject();
            logger.info("Generating Complete!");
        } catch (Exception e) {
            logger.error("Generating Failed");
            e.printStackTrace();
        }

    }

    private void genProject() throws FileNotFoundException, UnsupportedEncodingException {
        String dirPath = "./" + metadata.getMavenName() + "/";
        String basePath = dirPath + "src/main/";
        String javaPath = basePath + "java/";
        String resourcePath = basePath + "resources/";
        logger.info("Start Generating...");
        new File(basePath).mkdirs();
        logger.info("Generating .gitignore...");
        genGitIgnore(dirPath);
        logger.info("Generating Pom...");
        genPom(dirPath);
        logger.info("Generating Resources...");
        genResources(resourcePath);
        logger.info("Generating Code...");
        genJava(javaPath);
    }

    // generate pom.xml
    private void genPom(String dirPath) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter out = new PrintWriter(dirPath + "pom.xml", "UTF-8");

        // 1. print head
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.println("<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
        out.println("\txsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd\">");
        out.println("\t<modelVersion>4.0.0</modelVersion>");

        // 2. print springboot parent
        out.println("\t<parent>");
        out.println("\t\t<groupId>org.springframework.boot</groupId>");
        out.println("\t\t<artifactId>spring-boot-starter-parent</artifactId>");
        out.println("\t\t<version>" + metadata.getSpringbootVersion() + ".RELEASE</version>");
        out.println("\t</parent>");

        // 3. print project info
        out.println("\t<groupId>" + metadata.getMavenGroup() + "</groupId>");
        out.println("\t<artifactId>" + metadata.getMavenArtifact() + "</artifactId>");
        out.println("\t<version>1.0.0</version>");
        out.println("\t<name>" + metadata.getMavenName() + "</name>");

        // 4. print properties
        out.println();
        out.println("\t<properties>");
        out.println("\t\t<java.version>" + metadata.getJdkVersion() + "</java.version>");
        out.println("\t\t<maven.compiler.source>" + metadata.getJdkVersion() + "</maven.compiler.source>");
        out.println("\t\t<maven.compiler.target>" + metadata.getJdkVersion() + "</maven.compiler.target>");
        out.println("\t</properties>");

        // 5. print dependencies
        out.println();
        out.println("\t<dependencies>");

        out.println("\t\t<dependency>");
        out.println("\t\t\t<groupId>org.springframework.boot</groupId>");
        out.println("\t\t\t<artifactId>spring-boot-starter</artifactId>");
        out.println("\t\t</dependency>");
        out.println();

        out.println("\t\t<dependency>");
        out.println("\t\t\t<groupId>com.tencent.tars</groupId>");
        out.println("\t\t\t<artifactId>tars-spring-boot-starter</artifactId>");
        out.println("\t\t\t<version>1.7.1</version>");
        out.println("\t\t</dependency>");

        out.println("\t</dependencies>");

        // 6. print build plugin
        out.println();
        out.println("\t<build>");
        out.println("\t\t<plugins>");

        // 6.1 print tars maven plugin
        out.println("\t\t\t<!--tars2java plugin-->");
        out.println("\t\t\t<plugin>");
        out.println("\t\t\t\t<groupId>com.tencent.tars</groupId>");
        out.println("\t\t\t\t<artifactId>tars-maven-plugin</artifactId>");
        out.println("\t\t\t\t<version>1.7.1</version>");
        out.println("\t\t\t\t<configuration>");
        out.println("\t\t\t\t\t<tars2JavaConfig>");

        out.println("\t\t\t\t\t\t<!-- tars file location -->");
        out.println("\t\t\t\t\t\t<tarsFiles>");
        out.println("\t\t\t\t\t\t\t<tarsFile>${basedir}/src/main/resources/hello.tars</tarsFile>");
        out.println("\t\t\t\t\t\t</tarsFiles>");

        out.println("\t\t\t\t\t\t<!-- Source file encoding -->");
        out.println("\t\t\t\t\t\t<tarsFileCharset>UTF-8</tarsFileCharset>");

        if ("server".equals(metadata.getType())) {
            out.println("\t\t\t\t\t\t<!-- Generate server code -->");
            out.println("\t\t\t\t\t\t<servant>true</servant>");
        } else {
            out.println("\t\t\t\t\t\t<!-- Generate client code -->");
            out.println("\t\t\t\t\t\t<servant>false</servant>");
        }

        out.println("\t\t\t\t\t\t<!-- Generated source code encoding -->");
        out.println("\t\t\t\t\t\t<charset>UTF-8</charset>");

        out.println("\t\t\t\t\t\t<!-- Generated source code directory -->");
        out.println("\t\t\t\t\t\t<srcPath>${basedir}/src/main/java</srcPath>");

        out.println("\t\t\t\t\t\t<!-- Generated source code package prefix -->");
        out.println("\t\t\t\t\t\t<packagePrefixName>" + metadata.getPackageName() + ".</packagePrefixName>");

        out.println("\t\t\t\t\t</tars2JavaConfig>");
        out.println("\t\t\t\t</configuration>");
        out.println("\t\t\t</plugin>");

        // 6.2 print spring boot maven plugin
        out.println("\t\t\t<!--package plugin-->");
        out.println("\t\t\t<plugin>");
        out.println("\t\t\t\t<groupId>org.springframework.boot</groupId>");
        out.println("\t\t\t\t<artifactId>spring-boot-maven-plugin</artifactId>");
        out.println("\t\t\t\t<configuration>");
        out.println("\t\t\t\t\t<!--set mainClass-->");
        out.println("\t\t\t\t\t<mainClass>" + metadata.getPackageName() + ".Application</mainClass>");
        out.println("\t\t\t\t</configuration>");
        out.println("\t\t\t\t<executions>");
        out.println("\t\t\t\t\t<execution>");
        out.println("\t\t\t\t\t\t<goals>");
        out.println("\t\t\t\t\t\t\t<goal>repackage</goal>");
        out.println("\t\t\t\t\t\t</goals>");
        out.println("\t\t\t\t\t</execution>");
        out.println("\t\t\t\t</executions>");
        out.println("\t\t\t</plugin>");

        out.println("\t\t</plugins>");
        out.println("\t</build>");

        // 7. print end
        out.println();
        out.println("</project>");

        out.close();
    }

    private void genGitIgnore(String dirPath) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter out = new PrintWriter(dirPath + ".gitignore", "UTF-8");

        out.println(".idea/");
        out.println("*.ipr");
        out.println("*.iml");
        out.println("*.iws");
        out.println("target/");
        out.println("*.jar");
        out.println("!.mvn/wrapper/*");
        out.println(".settings/");
        out.println(".project");
        out.println(".classpath");
        out.println("*.dat");

        out.close();
    }

    // generate resources dir
    private void genResources(String resourcePath) throws FileNotFoundException, UnsupportedEncodingException {
        new File(resourcePath).mkdir();
        PrintWriter out = new PrintWriter(resourcePath + "hello.tars", "UTF-8");

        // print tars file
        out.println("module TestServer");
        out.println("{");
        out.println("\tinterface Hello");
        out.println("\t{");
        out.println("\t\tstring hello(int no, string name);");
        out.println("\t};");
        out.println("};");
        out.close();

        if ("client".equals(metadata.getType())) {
            out = new PrintWriter(resourcePath + "client.tars", "UTF-8");
            out.println("module TestClient");
            out.println("{");
            out.println("\tinterface Client");
            out.println("\t{");
            out.println("\t\tstring rpcHello(int no, string name);");
            out.println("\t};");
            out.println("};");
            out.close();
        }
    }

    // generate java code
    private void genJava(String javaPath) throws FileNotFoundException, UnsupportedEncodingException {
        String codePath = javaPath + metadata.getPackageName().replace('.', '/') + "/";
        new File(codePath).mkdirs();
        PrintWriter out = new PrintWriter(codePath + "Application.java", "UTF-8");

        // print package and imports
        out.println("package " + metadata.getPackageName() + ";");
        out.println();

        out.println("import com.qq.tars.spring.annotation.EnableTarsServer;");
        out.println("import org.springframework.boot.SpringApplication;");
        out.println("import org.springframework.boot.autoconfigure.SpringBootApplication;");
        out.println();

        // print main method
        out.println("@SpringBootApplication");
        out.println("@EnableTarsServer");
        out.println("public class Application {");
        out.println("\t public static void main(String[] args) {");
        out.println("\t\tSpringApplication.run(Application.class, args);");
        out.println("\t}");
        out.println("}");
        out.close();
    }

}
