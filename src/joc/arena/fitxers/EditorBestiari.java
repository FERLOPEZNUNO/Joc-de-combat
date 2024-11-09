<?xml version="1.0" encoding="UTF-8"?>
<!--
*** GENERATED FROM project.xml - DO NOT EDIT  ***
***         EDIT ../build.xml INSTEAD         ***

For the purpose of easier reading the script
is divided into following sections:

  - initialization
  - compilation
  - jar
  - execution
  - debugging
  - javadoc
  - test compilation
  - test execution
  - test debugging
  - applet
  - cleanup

        -->
<project xmlns:if="ant:if" xmlns:j2seproject1="http://www.netbeans.org/ns/j2se-project/1" xmlns:j2seproject3="http://www.netbeans.org/ns/j2se-project/3" xmlns:jaxrpc="http://www.netbeans.org/ns/j2se-project/jax-rpc" xmlns:unless="ant:unless" basedir=".." default="default" name="LopezNunoFernando-Uf4Pt2-impl">
    <fail message="Please build using Ant 1.8.0 or higher.">
        <condition>
            <not>
                <antversion atleast="1.8.0"/>
            </not>
        </condition>
    </fail>
    <target depends="test,jar,javadoc" description="Build and test whole project." name="default"/>
    <!-- 
                ======================
                INITIALIZATION SECTION 
                ======================
            -->
    <target name="-pre-init">
        <!-- Empty placeholder for easier customization. -->
        <!-- You can override this target in the ../build.xml file. -->
    </target>
    <target depends="-pre-init" name="-init-private">
        <property file="nbproject/private/config.properties"/>
        <property file="nbproject/private/configs/${config}.properties"/>
        <property file="nbproject/private/private.properties"/>
    </target>
    <target depends="-pre-init,-init-private" name="-init-user">
        <property file="${user.properties.file}"/>
        <!-- The two properties below are usually overridden -->
        <!-- by the active platform. Just a fallback. -->
        <property name="default.javac.source" value="1.8"/>
        <property name="default.javac.target" value="1.8"/>
    </target>
    <target depends="-pre-init,-init-private,-init-user" name="-init-project">
        <property file="nbproject/configs/${config}.properties"/>
        <property file="nbproject/project.properties"/>
    </target>
    <target name="-init-modules-supported">
        <condition property="modules.supported.internal" value="true">
            <not>
                <matches pattern="1\.[0-8](\..*)?" string="${javac.source}"/>
            </not>
        </condition>
    </target>
    <target depends="-init-modules-supported" if="modules.supported.internal" name="-init-macrodef-modulename">
        <macrodef name="modulename" uri="http://www.netbeans.org/ns/j2se-project/3">
            <attribute name="property"/>
            <attribute name="sourcepath"/>
            <sequential>
                <loadresource property="@{property}" quiet="true">
                    <javaresource classpath="@{sourcepath}" name="module-info.java" parentFirst="false"/>
                    <filterchain>
                        <stripjavacomments/>
                        <linecontainsregexp>
                            <regexp pattern="module .* \{"/>
                        </linecontainsregexp>
                        <tokenfilter>
                            <linetokenizer/>
                            <replaceregex flags="s" pattern="(\s*module\s+)(\S*)(\s*\{.*)" replace="\2"/>
                        </tokenfilter>
                        <striplinebreaks/>
                    </filterchain>
                </loadresource>
            </sequential>
        </macrodef>
    </target>
    <target depends="-init-modules-supported,-init-macrodef-modulename" if="modules.supported.internal" name="-init-source-module-properties">
        <fail message="Java 9 support requires Ant 1.10.0 or higher.">
            <condition>
                <not>
                    <antversion atleast="1.10.0"/>
                </not>
            </condition>
        </fail>
        <j2seproject3:modulename property="module.name" sourcepath="${src.dir}"/>
        <condition property="named.module.internal">
            <and>
                <isset property="module.name"/>
                <length length="0" string="${module.name}" when="greater"/>
            </and>
        </condition>
        <condition property="unnamed.module.internal">
            <not>
                <isset property="named.module.internal"/>
            </not>
        </condition>
        <property name="javac.modulepath" value=""/>
        <property name="run.modulepath" value="${javac.modulepath}"/>
        <property name="module.build.classes.dir" value="${build.classes.dir}"/>
        <property name="debug.modulepath" value="${run.modulepath}"/>
 