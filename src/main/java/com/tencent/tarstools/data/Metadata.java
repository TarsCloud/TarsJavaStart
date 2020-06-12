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
package com.tencent.tarstools.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Metadata {

    @Value("${springboot.version}")
    private String springbootVersion;

    @Value("${maven.group}")
    private String mavenGroup;

    @Value("${maven.artifact}")
    private String mavenArtifact;

    @Value("${maven.name}")
    private String mavenName;

    @Value("${packagename}")
    private String packageName;

    @Value("${jdk.version}")
    private String jdkVersion;

    @Value("${type}")
    private String type;

    public String getSpringbootVersion() {
        return springbootVersion;
    }

    public void setSpringbootVersion(String springbootVersion) {
        this.springbootVersion = springbootVersion;
    }

    public String getMavenGroup() {
        return mavenGroup;
    }

    public void setMavenGroup(String mavenGroup) {
        this.mavenGroup = mavenGroup;
    }

    public String getMavenArtifact() {
        return mavenArtifact;
    }

    public void setMavenArtifact(String mavenArtifact) {
        this.mavenArtifact = mavenArtifact;
    }

    public String getMavenName() {
        return mavenName;
    }

    public void setMavenName(String mavenName) {
        this.mavenName = mavenName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getJdkVersion() {
        return jdkVersion;
    }

    public void setJdkVersion(String jdkVersion) {
        this.jdkVersion = jdkVersion;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
