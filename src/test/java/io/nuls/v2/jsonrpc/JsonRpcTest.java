/**
 * MIT License
 * <p>
 * Copyright (c) 2017-2018 nuls.io
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.nuls.v2.jsonrpc;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.nuls.core.parse.JSONUtils;
import io.nuls.core.rpc.model.ApiModel;
import io.nuls.model.form.contract.ContractCall;
import io.nuls.v2.model.dto.CoinFromDto;
import io.nuls.v2.model.dto.ConsensusDto;
import io.nuls.v2.model.dto.RpcResult;
import io.nuls.v2.util.JsonRpcUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author: PierreLuo
 * @date: 2019-07-01
 */
public class JsonRpcTest {

    @Test
    public void test() throws JsonProcessingException {
        List<Object> params = new LinkedList<>();
        params.add(2);
        params.add("tNULSeBaN9n5FJ3EYXENEuYwC2ZmnRE1agJffz");
        params.add("transfer");
        RpcResult result = JsonRpcUtil.request("getContractMethod", params);
        System.out.println(JSONUtils.obj2PrettyJson(result));
    }

    @Test
    public void test1() {
        Class<ContractCall> aClass = ContractCall.class;
        Field[] fields = aClass.getDeclaredFields();
        Field[] fields1 = aClass.getSuperclass().getDeclaredFields();
        System.out.println(Arrays.toString(fields));
        System.out.println(Arrays.toString(fields1));
        Class clzs = aClass;
        List list = new LinkedList();
        while(clzs.getAnnotation(ApiModel.class) != null) {
            list.addAll(Arrays.asList(clzs.getDeclaredFields()));
            clzs = clzs.getSuperclass();
        }
        System.out.println(Arrays.toString(list.toArray()));
    }

    @Test
    public void test2() throws IllegalAccessException, InstantiationException, InvocationTargetException, JsonProcessingException {
        Class cls = ConsensusDto.class;
        Object obj = cls.newInstance();
        Class cls1 = CoinFromDto.class;
        Object obj1 = cls1.newInstance();
        BeanUtils.setProperty(obj, "input", obj1);
        System.out.println(JSONUtils.obj2PrettyJson(obj));
    }

    @Test
    public void test3() {
        System.out.println("0".repeat(2));
    }
}
