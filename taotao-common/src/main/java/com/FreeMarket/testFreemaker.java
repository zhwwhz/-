package com.FreeMarket;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class testFreemaker {
    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration(Configuration.getVersion());
        configuration.setDirectoryForTemplateLoading(new File("F:/IdeaProjects/taotao/taotao-common/src/main/resources"));
        configuration.setDefaultEncoding("utf-8");
        Template template = configuration.getTemplate("FreeMaker.ftl");
        Map<String,Object >  map = new HashMap<String,Object>();
        map.put("freemaker1",new Person("张三",15,"男"));
        map.put("freemaker2",new Person("李四",18,"女"));
        map.put("freemaker3",new Person("王五",19,"男"));

        Writer writer = new FileWriter(new File("I:/FreeMaker/FreeMaker.html"));
        template.process(map,writer);
        writer.close();

    }

}
