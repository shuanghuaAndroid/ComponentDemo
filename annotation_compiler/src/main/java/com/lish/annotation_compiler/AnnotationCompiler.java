package com.lish.annotation_compiler;

import com.google.auto.service.AutoService;
import com.lish.annotation.BindPath;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import static com.lish.annotation.Constant.PACKAGE_NAME;

/**
 * description: 自定义 注解处理器   自动生成类
 * author: lish
 * date: 2019/7/14
 * update:
 * version: 1.0.0
 */
@AutoService(Processor.class)
public class AnnotationCompiler extends AbstractProcessor {

    //文件生成对象
    Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    /**
     * 声明注解处理器处理哪些注解
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(BindPath.class.getCanonicalName());
        return types;
    }

    /**
     * 声明 注解处理器支持的java  sdk版本
     *
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    /**
     * 注解处理器核心方法   写文件在这里进行
     *
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //通过api获取所有用到bindPath注解的节点
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(BindPath.class);
        //初始化数据
        Map<String, String> map = new HashMap<>();
        for (Element element : elementsAnnotatedWith) {
            TypeElement typeElement = (TypeElement) element;
            //获取key  BindPath中的value
            String key = typeElement.getAnnotation(BindPath.class).value();
            //获取value BindView注解的类
            String value = typeElement.getQualifiedName().toString();
            map.put(key, value);
        }

        //开始对数据进行写文件
        if (map.size() > 0) {
            Writer writer = null;
            //创建类名
            String utilName = "ArouterUtil" + System.currentTimeMillis();
            try {
                JavaFileObject sourceFile = filer.createSourceFile(PACKAGE_NAME + "." + utilName);
                writer = sourceFile.openWriter();
                writer.write("package " +
                        PACKAGE_NAME +
                        ";\n" +
                        "\n" +
                        "import com.lish.comminlib.Arouter;\n" +
                        "import com.lish.comminlib.IArouter;\n" +
                        "\n" +
                        "public class " +
                        utilName +
                        " implements IArouter {\n" +
                        "    @Override\n" +
                        "    public void putActivity() {");
                Iterator<String> iterator = map.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    String value = map.get(key);
                    writer.write("Arouter.getInstance().putActivity(\"" +
                            key +
                            "\"," +
                            value +
                            ".class);\n");
                }

                writer.write("}\n}");

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }
}
