package io.nuls.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.nuls.base.api.provider.Provider;
import io.nuls.base.api.provider.ServiceManager;
import io.nuls.core.core.annotation.RpcMethod;
import io.nuls.core.core.config.ConfigurationLoader;
import io.nuls.core.core.ioc.SpringLiteContext;
import io.nuls.core.log.Log;
import io.nuls.core.model.StringUtils;
import io.nuls.core.parse.JSONUtils;
import io.nuls.core.rpc.model.*;
import io.nuls.v2.model.annotation.Api;
import io.nuls.v2.model.annotation.ApiOperation;
import io.nuls.v2.model.annotation.ApiType;
import lombok.Data;
import net.steppschuh.markdowngenerator.table.Table;
import net.steppschuh.markdowngenerator.text.Text;
import net.steppschuh.markdowngenerator.text.heading.Heading;
import org.apache.commons.beanutils.BeanUtils;

import javax.ws.rs.*;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author: zhoulijun
 * @Time: 2019-06-19 14:26
 * @Description: 生成rpc接口文档
 */
public class SdkProviderDocTool {

    static Set<String> exclusion = Set.of("io.nuls.base.protocol.cmd", "io.nuls.core.rpc.cmd.kernel", "io.nuls.core.rpc.modulebootstrap");

    static Set<Class> baseType = new HashSet<>();

    static {
        baseType.add(Integer.class);
        baseType.add(int.class);
        baseType.add(Long.class);
        baseType.add(long.class);
        baseType.add(Float.class);
        baseType.add(float.class);
        baseType.add(Double.class);
        baseType.add(double.class);
        baseType.add(Character.class);
        baseType.add(char.class);
        baseType.add(Short.class);
        baseType.add(short.class);
        baseType.add(Boolean.class);
        baseType.add(boolean.class);
        baseType.add(Byte.class);
        baseType.add(byte.class);
        baseType.add(String.class);
        baseType.add(Object[].class);
        baseType.add(BigInteger.class);
        baseType.add(BigDecimal.class);
    }

    public static class ResultDes implements Serializable {
        String name;
        String des;
        String type;
        List<ResultDes> list;
        boolean canNull;
        String formJsonOfRestful;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<ResultDes> getList() {
            return list;
        }

        public void setList(List<ResultDes> list) {
            this.list = list;
        }

        public boolean isCanNull() {
            return canNull;
        }

        public void setCanNull(boolean canNull) {
            this.canNull = canNull;
        }

        public String getFormJsonOfRestful() {
            return formJsonOfRestful;
        }

        public void setFormJsonOfRestful(String formJsonOfRestful) {
            this.formJsonOfRestful = formJsonOfRestful;
        }

    }

    public static class CmdDes implements Serializable {
        int order;
        String cmdName;
        String cmdType;
        String des;
        String httpMethod;
        String md;
        List<ResultDes> parameters;
        List<ResultDes> result;

        public String getMd() {
            return md;
        }

        public void setMd(String md) {
            this.md = md;
        }

        public String getCmdName() {
            return cmdName;
        }

        public void setCmdName(String cmdName) {
            this.cmdName = cmdName;
        }

        public String getCmdType() {
            return cmdType;
        }

        public void setCmdType(String cmdType) {
            this.cmdType = cmdType;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getHttpMethod() {
            return httpMethod;
        }

        public void setHttpMethod(String httpMethod) {
            this.httpMethod = httpMethod;
        }

        public List<ResultDes> getParameters() {
            return parameters;
        }

        public void setParameters(List<ResultDes> parameters) {
            this.parameters = parameters;
        }

        public List<ResultDes> getResult() {
            return result;
        }

        public void setResult(List<ResultDes> result) {
            this.result = result;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int compareTo(int thatOrder) {
            if(this.order > thatOrder) {
                return 1;
            } else if(this.order < thatOrder) {
                return -1;
            }
            return 0;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("\"order\":")
                    .append(order);
            sb.append(",\"cmdName\":")
                    .append('\"').append(cmdName).append('\"');
            sb.append(",\"des\":")
                    .append('\"').append(des).append('\"');
            sb.append('}');
            return sb.toString();
        }
    }

    public static void main(String[] args) throws IOException {
        ConfigurationLoader configurationLoader = new ConfigurationLoader();
        configurationLoader.load();
        Provider.ProviderType providerType = Provider.ProviderType.valueOf(configurationLoader.getValue("providerType"));
        int defaultChainId = Integer.parseInt(configurationLoader.getValue("chainId"));
        ServiceManager.init(defaultChainId, providerType);
        SpringLiteContext.init("io.nuls");
        Gen.genDoc();
        //Gen.genPostmanJSON();
        System.exit(0);
    }


    public static class Gen {

        private static final String NA = "N/A";

        public static List<CmdDes>[] buildData() {
            Collection<Object> beanList = SpringLiteContext.getAllBeanList();
            List<CmdDes>[] resultArray = new List[2];
            List<CmdDes> restfulCmdDesList = new ArrayList<>();
            List<CmdDes> jsonrpcCmdDesList = new ArrayList<>();
            resultArray[0] = restfulCmdDesList;
            resultArray[1] = jsonrpcCmdDesList;
            beanList.forEach(cmd -> {
                Class<?> clazs = cmd.getClass();
                Api api = clazs.getAnnotation(Api.class);
                if (api == null) {
                    return;
                }
                if (exclusion.contains(clazs.getPackageName())) {
                    Log.info("跳过{}生成文档，所在包{}在排除范围", clazs.getSimpleName(), clazs.getPackageName());
                    return;
                }
                ApiType apiType = api.type();
                String cmdBaseName = "";
                String methodPath = "";
                boolean restful = apiType.equals(ApiType.RESTFUL);
                boolean jsonrpc = apiType.equals(ApiType.JSONRPC);
                if (restful) {
                    Path annotation = clazs.getAnnotation(Path.class);
                    cmdBaseName = annotation.value();
                }
                Method[] methods = clazs.getMethods();
                for (Method method : methods) {
                    Annotation annotation = method.getAnnotation(ApiOperation.class);
                    if (annotation == null) {
                        continue;
                    }
                    ApiOperation cmdAnnotation = (ApiOperation) annotation;
                    CmdDes cmdDes = new CmdDes();
                    cmdDes.order = cmdAnnotation.order();
                    cmdDes.cmdType = apiType.name();
                    if (restful) {
                        Path methodPathAnnotation = method.getAnnotation(Path.class);
                        if (methodPathAnnotation != null) {
                            methodPath = methodPathAnnotation.value();
                        }
                        if (StringUtils.isBlank(methodPath)) {
                            methodPath = "";
                        }
                        String httpMethod = "";
                        do {
                            POST post = method.getAnnotation(POST.class);
                            if (post != null) {
                                httpMethod = post.annotationType().getSimpleName();
                                break;
                            }
                            GET get = method.getAnnotation(GET.class);
                            if (get != null) {
                                httpMethod = get.annotationType().getSimpleName();
                                break;
                            }
                            PUT put = method.getAnnotation(PUT.class);
                            if (put != null) {
                                httpMethod = put.annotationType().getSimpleName();
                                break;
                            }
                            DELETE delete = method.getAnnotation(DELETE.class);
                            if (delete != null) {
                                httpMethod = delete.annotationType().getSimpleName();
                                break;
                            }
                        } while (false);
                        cmdDes.httpMethod = httpMethod;
                    } else if (jsonrpc) {
                        RpcMethod rpcMethod = method.getAnnotation(RpcMethod.class);
                        cmdDes.httpMethod = "POST";
                        methodPath = rpcMethod.value();
                    }

                    cmdDes.cmdName = cmdBaseName + methodPath;
                    cmdDes.des = cmdAnnotation.description();
                    cmdDes.parameters = buildParam(apiType, method);
                    annotation = method.getAnnotation(ResponseData.class);
                    if (annotation != null) {
                        ResponseData responseData = (ResponseData) annotation;
                        cmdDes.result = buildResultDes(responseData.responseType(), responseData.description(), responseData.name());
                    }
                    if (restful) {
                        restfulCmdDesList.add(cmdDes);
                    } else if (jsonrpc) {
                        jsonrpcCmdDesList.add(cmdDes);
                    }
                }
                ;
            });
            return resultArray;
        }

        public static void genDoc() throws IOException {
            List<CmdDes>[] cmdDesList = buildData();
            List<CmdDes> restfulCmdDesList = cmdDesList[0];
            List<CmdDes> jsonrpcCmdDesList = cmdDesList[1];
            restfulCmdDesList.sort((a, b) -> {
                return a.compareTo(b.order);
            });
            jsonrpcCmdDesList.sort((a, b) -> {
                return a.compareTo(b.order);
            });
            //System.out.println("生成RESTFUL文档成功：" + createMarketDownDoc(restfulCmdDesList, ApiType.RESTFUL, "./readme.md"));
            System.out.println("生成JSONRPC文档成功：" + createMarketDownDoc(jsonrpcCmdDesList, ApiType.JSONRPC, "./readme.md"));
//            System.exit(0);
        }

        public static void genJSON() throws IOException {
            List<CmdDes>[] cmdDesList = buildData();
            Log.info("{}", cmdDesList);
            List<CmdDes> restfulCmdDesList = cmdDesList[0];
            List<CmdDes> jsonrpcCmdDesList = cmdDesList[1];
            restfulCmdDesList.sort((a, b) -> {
                return a.compareTo(b.order);
            });
            jsonrpcCmdDesList.sort((a, b) -> {
                return a.compareTo(b.order);
            });
            System.out.println("生成RESTFUL文档成功：" + createJSONConfig(restfulCmdDesList, ApiType.RESTFUL, "./readme.md"));
            System.out.println("生成JSONRPC文档成功：" + createJSONConfig(jsonrpcCmdDesList, ApiType.JSONRPC, "./readme.md"));
//            System.exit(0);
        }

        public static void genPostmanJSON() throws IOException {
            List<CmdDes>[] cmdDesList = buildData();
            List<CmdDes> restfulCmdDesList = cmdDesList[0];
            List<CmdDes> jsonrpcCmdDesList = cmdDesList[1];
            restfulCmdDesList.sort((a, b) -> {
                return a.compareTo(b.order);
            });
            jsonrpcCmdDesList.sort((a, b) -> {
                return a.compareTo(b.order);
            });
            System.out.println("生成Postman-RESTFUL导入文件成功：" + createPostmanJSONConfig(restfulCmdDesList, ApiType.RESTFUL, "./readme.md"));
            System.out.println("生成Postman-JSONRPC导入文件成功：" + createPostmanJSONConfig(jsonrpcCmdDesList, ApiType.JSONRPC, "./readme.md"));
//            System.exit(0);
        }

        public static List<ResultDes> buildParam(ApiType apiType, Method method) {
            Annotation annotation = method.getAnnotation(Parameters.class);
            List<Parameter> parameters;
            if (annotation != null) {
                parameters = Arrays.asList(((Parameters) annotation).value());
            } else {
                Parameter[] parameterAry = method.getAnnotationsByType(Parameter.class);
                parameters = Arrays.asList(parameterAry);
            }
            List<ResultDes> param = new ArrayList<>();
            parameters.stream().forEach(parameter -> {
                ResultDes res = new ResultDes();
                res.type = parameter.parameterType();
                res.name = parameter.parameterName();
                res.des = parameter.parameterDes();
                res.canNull = parameter.canNull();
                Class<?> requestType = parameter.requestType().value();
                if (baseType.contains(requestType)) {
                    param.addAll(buildResultDes(parameter.requestType(), res.des, res.name));
                } else {
                    if(ApiType.RESTFUL.equals(apiType)) {
                        try {
                            res.formJsonOfRestful = JSONUtils.obj2PrettyJson(newInstance(requestType));
                        } catch (Exception e) {
                            System.out.println(String.format("Form named [%s] has no non-args-constructor.", requestType.getSimpleName()));
                        }
                    }
                    res.list = buildResultDes(parameter.requestType(), res.des, res.name);
                    res.type = parameter.requestType().value().getSimpleName().toLowerCase();
                    param.add(res);
                }
            });
            return param;
        }

        private static Object newInstance(Class cls) throws IllegalAccessException, InstantiationException, InvocationTargetException {
            Object o = cls.newInstance();
            Field[] fields = cls.getDeclaredFields();
            for(Field field : fields) {
                if(!baseType.contains(field.getType())) {
                    Object o1 = null;
                    if(field.getType() == List.class) {
                        o1 = new ArrayList<>();
                        List o2 = (List) o1;
                        ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
                        if(apiModelProperty != null) {
                            Class<?> element = apiModelProperty.type().collectionElement();
                            o2.add(newInstance(element));
                        }
                    } else if(field.getType() == Map.class) {
                        o1 = new HashMap<>();
                    } else if(field.getType() == Set.class) {
                        o1 = new HashSet<>();
                    } else {
                        o1 = field.getType().newInstance();
                    }
                    BeanUtils.setProperty(o, field.getName(), o1);
                }
            }
            return o;
        }

        public static List<ResultDes> buildResultDes(TypeDescriptor typeDescriptor, String des, String name) {
            ResultDes resultDes = new ResultDes();
            if (typeDescriptor.value() == Void.class) {
                resultDes.type = "void";
                resultDes.des = des;
                resultDes.name = NA;
                return List.of(resultDes);
            }
            if (baseType.contains(typeDescriptor.value())) {
                resultDes.des = des;
                resultDes.name = name;
                resultDes.type = typeDescriptor.value().getSimpleName().toLowerCase();
                return List.of(resultDes);
            } else if (typeDescriptor.value() == Map.class) {
                return mapToResultDes(typeDescriptor);
            } else if (List.class == typeDescriptor.value()) {
                if (baseType.contains(typeDescriptor.collectionElement())) {
                    resultDes.type = "list&lt;" + typeDescriptor.collectionElement().getSimpleName() + ">";
                    resultDes.des = des;
                    resultDes.name = name;
                    return List.of(resultDes);
                }
                if (typeDescriptor.collectionElement() == Map.class) {
                    return mapToResultDes(typeDescriptor);
                } else {
                    return classToResultDes(typeDescriptor.collectionElement());
                }
            } else if (Object[].class == typeDescriptor.value()) {
                resultDes.des = des;
                resultDes.name = name;
                resultDes.type = "object[]";
                return List.of(resultDes);
            } else {
                Annotation annotation = typeDescriptor.value().getAnnotation(ApiModel.class);
                if (annotation == null) {
                    resultDes.type = typeDescriptor.value().getSimpleName().toLowerCase();
                    resultDes.name = name;
                    resultDes.des = des;
                    return List.of(resultDes);
                }
                return classToResultDes(typeDescriptor.value());
            }
        }

        public static List<ResultDes> mapToResultDes(TypeDescriptor typeDescriptor) {
            Key[] keys = typeDescriptor.mapKeys();
            List<ResultDes> res = new ArrayList<>();
            Arrays.stream(keys).forEach(key -> {
                ResultDes rd = new ResultDes();
                if (baseType.contains(key.valueType())) {
                    rd.type = key.valueType().getSimpleName().toLowerCase();
                    rd.name = key.name();
                    rd.des = key.description();
                } else if (List.class == key.valueType()) {
                    rd.name = key.name();
                    rd.des = key.description();
                    if (baseType.contains(key.valueElement()) || key.valueElement() == Map.class) {
                        rd.type = "list&lt;" + key.valueElement().getSimpleName() + ">";
                    } else {
                        rd.list = classToResultDes(key.valueElement());
                        rd.type = "list&lt;object>";
                    }
                } else if (Map.class == key.valueType()) {
                    rd.type = "map";
                    rd.name = key.name();
                    rd.des = key.description();
                } else {
                    Annotation annotation = key.valueType().getAnnotation(ApiModel.class);
                    if (annotation == null) {
                        rd.type = key.valueType().getSimpleName().toLowerCase();
                        rd.name = key.name();
                        rd.des = key.description();
                    } else {
                        rd.type = "object";
                        rd.name = key.name();
                        rd.des = key.description();
                        rd.list = classToResultDes(key.valueType());
                    }

                }
                res.add(rd);
            });
            return res;
        }

        public static List<ResultDes> classToResultDes(Class<?> clzs) {
            Annotation annotation = clzs.getAnnotation(ApiModel.class);
            if (annotation == null) {
                throw new IllegalArgumentException("返回值是复杂对象时必须声明ApiModule注解 + " + clzs.getSimpleName());
            }
            List<Field> list = new LinkedList();
            list.addAll(Arrays.asList(clzs.getDeclaredFields()));
            Class clzsTemp = clzs.getSuperclass();
            while(clzsTemp.getAnnotation(ApiModel.class) != null) {
                list.addAll(0, Arrays.asList(clzsTemp.getDeclaredFields()));
                clzsTemp = clzsTemp.getSuperclass();
            }
            Field[] fileds = new Field[list.size()];
            list.toArray(fileds);
            List<ResultDes> filedList = new ArrayList<>();
            Arrays.stream(fileds).forEach(filed -> {
                Annotation ann = filed.getAnnotation(ApiModelProperty.class);
                ApiModelProperty apiModelProperty = (ApiModelProperty) ann;
                if (apiModelProperty == null) {
                    Log.warn("发现未配置ApiModelProperty注解的filed:{}", clzs.getSimpleName() + "#" + filed.getName());
                    return;
                }
                ResultDes filedDes = new ResultDes();
                filedDes.des = apiModelProperty.description();
                filedDes.name = filed.getName();
                if (apiModelProperty.type().value() != Void.class) {
                    if (baseType.contains(apiModelProperty.type().collectionElement())) {
                        filedDes.type = "list&lt;" + apiModelProperty.type().collectionElement().getSimpleName() + ">";
                    } else {
                        filedDes.list = buildResultDes(apiModelProperty.type(), filedDes.des, filedDes.name);
                        if (apiModelProperty.type().value() == List.class) {
                            filedDes.type = "list&lt;object>";
                        } else if (apiModelProperty.type().value() == Map.class) {
                            filedDes.type = "map";
                        }
                    }
                } else {
                    if (baseType.contains(filed.getType())) {
                        filedDes.type = filed.getType().getSimpleName().toLowerCase();
                    } else {
                        Annotation filedAnn = filed.getType().getAnnotation(ApiModel.class);
                        if (filedAnn == null) {
                            Log.warn("发现ApiModelProperty注解的filed类型为复杂对象，但对象并未注解ApiModel，filed:{}", clzs.getName() + "#" + filed.getName());
                            filedDes.type = filed.getType().getSimpleName().toLowerCase();
                        } else {
                            if (clzs == filed.getType()) {
                                Log.warn("发现循环引用：{}", clzs);
                                filedDes.type = "object&lt;" + clzs.getSimpleName().toLowerCase() + ">";
                            } else {
                                filedDes.list = classToResultDes(filed.getType());
                                filedDes.type = "object";
                            }
                        }
                    }
                }
                filedList.add(filedDes);
            });
            return filedList;
        }

        public static String createJSONConfig(List<CmdDes> cmdDesList, ApiType apiType, String tempFile) throws IOException {
            ConfigurationLoader configurationLoader = SpringLiteContext.getBean(ConfigurationLoader.class);
            ConfigurationLoader.ConfigItem configItem = configurationLoader.getConfigItem("APP_NAME");
            String appName = configItem.getValue();
            File file = new File(tempFile);
            if (!file.exists()) {
                throw new RuntimeException("模板文件不存在");
            }
            File mdFile = new File(file.getParentFile().getAbsolutePath() + File.separator + "documents" + File.separator + appName + "_" + apiType.name() + ".json");
            if (mdFile.exists()) {
                mdFile.delete();
            }
            mdFile.createNewFile();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(mdFile))) {
                cmdDesList.forEach(cmd -> {
                    try {
                        StringWriter stringWriter = new StringWriter();
                        try (BufferedWriter sbw = new BufferedWriter(stringWriter)) {
                            writeMarkdown(cmd, sbw, null);
                            sbw.flush();
                            cmd.md = stringWriter.toString();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                writer.write(JSONUtils.obj2json(cmdDesList));
            }
            return mdFile.getAbsolutePath();
        }

        public static String createPostmanJSONConfig(List<CmdDes> cmdDesList, ApiType apiType, String tempFile) throws IOException {
            ConfigurationLoader configurationLoader = SpringLiteContext.getBean(ConfigurationLoader.class);
            ConfigurationLoader.ConfigItem configItem = configurationLoader.getConfigItem("APP_NAME");
            String appName = configItem.getValue();
            File file = new File(tempFile);
            if (!file.exists()) {
                throw new RuntimeException("模板文件不存在");
            }
            File mdFile = new File(file.getParentFile().getAbsolutePath() + File.separator + "documents" + File.separator + appName + "_Postman_" + apiType.name() + ".json");
            if (mdFile.exists()) {
                mdFile.delete();
            }
            mdFile.createNewFile();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(mdFile))) {
                String postmanFormatJson = genPostmanFormatJson(cmdDesList, apiType);
                writer.write(postmanFormatJson);
            }
            return mdFile.getAbsolutePath();
        }

        public static String createMarketDownDoc(List<CmdDes> cmdDesList, ApiType apiType, String tempFile) throws IOException {
            ConfigurationLoader configurationLoader = SpringLiteContext.getBean(ConfigurationLoader.class);
            String appName = configurationLoader.getConfigItem("APP_NAME").getValue();
            File file = new File(tempFile);
            if (!file.exists()) {
                throw new RuntimeException("模板文件不存在");
            }

            File mdFile = new File(file.getParentFile().getAbsolutePath() + File.separator + "documents" + File.separator + appName + "_" + apiType.name() + ".md");
            if (mdFile.exists()) {
                mdFile.delete();
            }
            mdFile.createNewFile();
            try (OutputStream outputStream = new FileOutputStream(mdFile); InputStream inputStream = new FileInputStream(file)) {
                inputStream.transferTo(outputStream);
                outputStream.flush();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(mdFile, true))) {
                writer.newLine();
                AtomicInteger i = new AtomicInteger(0);
                cmdDesList.forEach(cmd -> {
                    writeMarkdown(cmd, writer, i);
                });
            }
            return mdFile.getAbsolutePath();
        }

        private static void writeMarkdown(CmdDes cmd, BufferedWriter writer, AtomicInteger i) {
            try {
                String order = "";
                if(i != null) {
                    if(i.get() == 0) {
                        i.set(cmd.order);
                    } else {
                        String currentFirstLetterOfOrder = String.valueOf(i.get()).substring(0, 1);
                        String firstLetterOfOrder = String.valueOf(cmd.order).substring(0, 1);
                        if(!currentFirstLetterOfOrder.equals(firstLetterOfOrder)) {
                            i.set(cmd.order);
                        }
                    }
                    order = String.valueOf(i.getAndIncrement());
                    order = order.substring(0, 1) + "." + Integer.parseInt(order.substring(1)) + " ";
                }
                writer.write(new Heading(order + cmd.des, 1).toString());
                writer.newLine();
                writer.write(new Heading("Cmd: " + cmd.cmdName.replaceAll("_", "\\\\_"), 2).toString());
                writer.newLine();
                writer.write(new Heading("CmdType: " + cmd.cmdType, 3).toString());
                writer.newLine();
                writer.write(new Heading("HttpMethod: " + cmd.httpMethod, 3).toString());
                writer.newLine();
                writer.write(new Heading("ContentType: application/json;charset=UTF-8", 3).toString());
                writer.newLine();
                writer.newLine();
                buildParam(writer, cmd.parameters, ApiType.JSONRPC.name().equals(cmd.cmdType));
                buildResult(writer, cmd.result);
                writer.newLine();
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void buildResult(BufferedWriter writer, List<ResultDes> result) throws IOException {
            writer.newLine();
            writer.newLine();
            writer.write(new Heading("返回值", 2).toString());
            if (result == null) {
                writer.newLine();
                writer.write("无返回值");
                return;
            }
            Table.Builder tableBuilder = new Table.Builder()
                    .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_CENTER, Table.ALIGN_LEFT)
                    .addRow("字段名", "字段类型", "参数描述");
            buildResult(tableBuilder, result, 0);
            writer.newLine();
            writer.write(tableBuilder.build().toString());
        }

        private static void buildResult(Table.Builder tableBuilder, List<ResultDes> result, int depth) {
            result.forEach(r -> {
                tableBuilder.addRow("&nbsp;".repeat(depth * 8) + r.name, r.type.toLowerCase(), r.des);
                if (r.list != null) {
                    buildResult(tableBuilder, r.list, depth + 1);
                }
            });
        }

        private static void buildParam(BufferedWriter writer, List<ResultDes> parameters, boolean jsonrpc) throws IOException {
            parameters.forEach(des -> {
                if(des.formJsonOfRestful != null) {
                    try {
                        writer.newLine();
                        writer.write(new Heading("Form json data: ", 3).toString());
                        writer.newLine();
                        writer.write(new Text("```json").toString());
                        writer.newLine();
                        writer.write(new Text(des.formJsonOfRestful).toString());
                        writer.newLine();
                        writer.write(new Text("```").toString());
                        writer.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            writer.newLine();
            writer.write(new Heading("参数列表", 2).toString());
            if (parameters == null || parameters.isEmpty()) {
                writer.newLine();
                writer.write("无参数");
                return;
            }
            Table.Builder tableBuilder = new Table.Builder()
                    .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_CENTER, Table.ALIGN_LEFT, Table.ALIGN_CENTER)
                    .addRow("参数名", "参数类型", "参数描述", "是否非空");
//            parameters.forEach(p->{
//                tableBuilder.addRow(p.parameterName(),p.parameterType().toLowerCase(),p.parameterDes(),!p.canNull() ? "是" : "否");
//            });
            buildParam(tableBuilder, parameters, 0, jsonrpc);
            writer.newLine();
            writer.write(tableBuilder.build().toString());
        }

        private static void buildParam(Table.Builder tableBuilder, List<ResultDes> result, int depth, boolean jsonrpc) {
            result.forEach(r -> {
                //if(!jsonrpc || (r.list == null || depth > 0)) {
                    tableBuilder.addRow("&nbsp;".repeat(depth * 8) + r.name, r.type.toLowerCase(), r.des, !r.canNull ? "是" : "否");
                //}
                if (r.list != null) {
                    buildParam(tableBuilder, r.list, depth + 1, jsonrpc);
                }
            });
        }

        public static String genPostmanFormatJson(List<CmdDes> cmdDesList, ApiType apiType) throws JsonProcessingException {
            boolean restful = apiType.equals(ApiType.RESTFUL);
            boolean jsonrpc = apiType.equals(ApiType.JSONRPC);
            PostmanFormat format = new PostmanFormat();
            Info info = new Info();
            format.setInfo(info);
            info.setName("SDK-Provider-" + apiType.name());
            info.setSchema("https://schema.getpostman.com/json/collection/v2.1.0/collection.json");
            List<Item> item = new ArrayList<>();
            for(CmdDes des : cmdDesList) {
                Item aItem = new Item();
                aItem.setName(des.des + " - " + des.cmdName);
                Request request = new Request();
                Body body = new Body();
                Url url = new Url();
                request.setMethod(des.httpMethod);
                request.setBody(body);
                if(jsonrpc) {
                    // 排除表单title参数
                    List<String> nameList = des.getParameters().stream().map(p -> {
                        if(p.list == null) {
                            return p.name;
                        } else {
                            String formData = p.list.stream().map(pp -> pp.name).collect(Collectors.toList()).toString();
                            return formData.substring(1, formData.length() - 1);
                        }
                    }).collect(Collectors.toList());
                    body.setRaw(String.format(
                            "{\n\"jsonrpc\":\"2.0\",\n\"method\":\"%s\",\n\"params\":%s,\n\"id\":1234\n}\n",
                            des.cmdName,
                            nameList.toString()
                    ));
                    url = Url.jsonrpcInstance();
                } else if(restful) {
                    Optional<ResultDes> first = des.getParameters().stream().filter(p -> p.formJsonOfRestful != null).findFirst();
                    if(!first.isEmpty()) {
                        body.setRaw(first.get().formJsonOfRestful);
                    }
                    url.path.add(des.cmdName.substring(1));
                    url.raw = String.format("%s://%s:%s/%s", url.protocol, url.host.get(0), url.port, url.path.get(0));
                }
                request.setUrl(url);
                aItem.setRequest(request);
                item.add(aItem);
            }
            format.setItem(item);
            String formatStr = JSONUtils.obj2json(format);
            return formatStr;
        }

        @Data
        private static class PostmanFormat {
            private Info info;
            private List<Item> item;
        }

        @Data
        private static class Info {
            private String _postman_id;
            private String name;
            private String schema;

            public Info() {
                this._postman_id = UUID.randomUUID().toString();
            }
        }

        @Data
        private static class Item {
            private String name;
            private Request request;
            private List<String> response;

            public Item() {
                this.response = new ArrayList<>();
            }
        }

        @Data
        private static class Request {
            private String method;
            private List<Header> header;
            private Body body;
            private Url url;

            public Request() {
                this.header = new ArrayList<>();
                Header header = new Header();
                header.setKey("Content-Type");
                header.setName("Content-Type");
                header.setValue("application/json;charset=UTF-8");
                header.setType("text");
                this.header.add(header);
            }
        }

        @Data
        private static class Header {
            private String key;
            private String name;
            private String value;
            private String type;
        }

        @Data
        private static class Body {
            private String mode;
            private String raw;

            public Body() {
                this.mode = "raw";
            }
        }

        @Data
        private static class Url {
            private String raw;
            private String protocol;
            private List<String> host;
            private String port;
            private List<String> path;

            public Url() {
                this.protocol = "http";
                this.host = new ArrayList<>();
                this.host.add("localhost");
                this.port = "9898";
                this.path = new ArrayList<>();
            }

            public static Url jsonrpcInstance() {
                Url url = new Url();
                url.path.add("jsonrpc");
                url.raw = "http://localhost:9898/jsonrpc";
                return url;
            }
        }
    }



}