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
package io.nuls.v2.rpctools;

import io.nuls.NulsModuleBootstrap;
import io.nuls.api.jsonrpc.controller.ContractController;
import io.nuls.core.core.ioc.SpringLiteContext;
import io.nuls.core.parse.JSONUtils;
import io.nuls.core.rpc.info.HostInfo;
import io.nuls.rpctools.ConsensusTools;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author: PierreLuo
 * @date: 2019-07-23
 */
public class ConsensusToolsTest {

    @BeforeClass
    public static void startUp() throws Exception {
        //NulsModuleBootstrap.main(new String[]{"ws://192.168.1.144:7771"});
        NulsModuleBootstrap.main(null);
    }

    @Test
    public void test() {
        try {
            TimeUnit.SECONDS.sleep(5);
            ConsensusTools tools = SpringLiteContext.getBean(ConsensusTools.class);
            Object agentInfoInContract = tools.getAgentInfoInContract(2, "25326fc2d8ff22ec869baf8c4d55012dfa7f9860c0d1c54dc8569925d15f7e5f",
                    "tNULSeBaN8cW84rugvTDgSrHNUhZEaWEMERAKZ", "tNULSeBaMnrs6JKrCy6TQdzYJZkMZJDng7QAsD");
            System.out.println(JSONUtils.obj2PrettyJson(agentInfoInContract));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
