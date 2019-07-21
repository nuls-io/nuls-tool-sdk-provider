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
package io.nuls.v2.depth;

import io.nuls.model.dto.*;
import io.nuls.utils.Utils;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: PierreLuo
 * @date: 2019-07-13
 */
public class DepthTest {

    @Test
    public void test() {
        System.out.println(Utils.getDepth(Depth2.class));
    }

    static class Depth5 {
        Depth4 depth4;
    }

    static class Depth4 {
        Depth3 testDepth;
        List<Depth3> testDepths;
        Map<Depth3, Depth3> testDepthMap;
    }

    static class Depth3 {
        private List<ContractMergedTransferDto> transfers;
        private List<String> events;
        private List<String> events1;
        private List<ContractTokenTransferDto> tokenTransfers;
        private List<ContractInvokeRegisterCmdDto> invokeRegisterCmds;
        private List<String> contractTxList;
        private List<ProgramMethod> methods;
        private Map<String, ProgramMethod> map;
        private Set<ProgramMethodArg> set;
        private String password;
        private BigInteger amount;
        private Integer a;
        private int b;
    }

    static class Depth2 {
        private Set<ProgramMethodArg> set;
        private String password;
        private BigInteger amount;
        private Integer a;
        private int b;
    }

}
