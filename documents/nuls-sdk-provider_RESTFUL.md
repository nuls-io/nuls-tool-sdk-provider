# NULS2.0 SDK-Provider

**SDK模块，与核心模块连接，依赖离线SDK包，以HTTP接口作为服务，提供了JSON-RPC和RESTFUL的请求方式，
以此模块作为访问底层钱包数据的桥梁，用于在线和离线的交易组装、查询等功能**


根据hash获取交易，只查已确认交易
==================
Cmd: /api/tx/hash/{hash}
------------------------
### CmdType: RESTFUL
### HttpMethod: GET
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名  |  参数类型  | 参数描述   | 是否非空 |
| ---- |:------:| ------ |:----:|
| hash | string | 交易hash |  是   |

返回值
---
| 字段名                                                      |      字段类型       | 参数描述                                      |
| -------------------------------------------------------- |:---------------:| ----------------------------------------- |
| hash                                                     |     string      | 交易的hash值                                  |
| type                                                     |       int       | 交易类型                                      |
| time                                                     |     string      | 交易时间                                      |
| blockHeight                                              |      long       | 区块高度                                      |
| remark                                                   |     string      | 交易备注                                      |
| transactionSignature                                     |     string      | 交易签名                                      |
| status                                                   |       int       | 交易状态 0:unConfirm(待确认), 1:confirm(已确认)     |
| size                                                     |       int       | 交易大小                                      |
| inBlockIndex                                             |       int       | 在区块中的顺序，存储在rocksDB中是无序的，保存区块时赋值，取出后根据此值排序 |
| form                                                     | list&lt;object> | 输入                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;nonce    |     string      | 账户nonce值的Hex字符串，防止双花交易，取上一笔交易hash的最后8个字节  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;locked   |      byte       | 0普通交易，-1解锁金额交易（退出共识，退出委托）                 |
| to                                                       | list&lt;object> | 输出                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;lockTime |      long       | 解锁时间，-1为永久锁定                              |

注销共识节点
======
Cmd: /api/consensus/agent/stop
------------------------------
### CmdType: RESTFUL
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                      |     参数类型      | 参数描述     | 是否非空 |
| -------------------------------------------------------- |:-------------:| -------- |:----:|
| 注销共识节点                                                   | stopagentform | 注销共识节点表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address  |    string     | 共识节点地址   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password |    string     | 密码       |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述   |
| ----- |:------:| ------ |
| value | string | 交易hash |

Create an agent for consensus! 创建共识(代理)节点
=========================================
Cmd: /api/consensus/agent
-------------------------
### CmdType: RESTFUL
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                            |      参数类型       | 参数描述         | 是否非空 |
| -------------------------------------------------------------- |:---------------:| ------------ |:----:|
| 创建共识(代理)节点                                                     | createagentform | 创建共识(代理)节点表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;agentAddress   |     string      | 节点地址         |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;packingAddress |     string      | 节点出块地址       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;rewardAddress  |     string      | 奖励地址，默认节点地址  |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;commissionRate |       int       | 佣金比例         |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;deposit        |     string      | 抵押金额         |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password       |     string      | 密码           |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述   |
| ----- |:------:| ------ |
| value | string | 交易hash |

deposit nuls to a bank! 申请参与共识
==============================
Cmd: /api/consensus/deposit
---------------------------
### CmdType: RESTFUL
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                       |    参数类型     | 参数描述     | 是否非空 |
| --------------------------------------------------------- |:-----------:| -------- |:----:|
| 申请参与共识                                                    | depositform | 申请参与共识表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address   |   string    | 参与共识账户地址 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;agentHash |   string    | 共识节点hash |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;deposit   |   string    | 参与共识的金额  |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password  |   string    | 密码       |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述   |
| ----- |:------:| ------ |
| value | string | 交易hash |

退出共识
====
Cmd: /api/consensus/withdraw
----------------------------
### CmdType: RESTFUL
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                      |     参数类型     | 参数描述         | 是否非空 |
| -------------------------------------------------------- |:------------:| ------------ |:----:|
| 退出共识                                                     | withdrawform | 退出共识表单       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address  |    string    | 节点地址         |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;txHash   |    string    | 加入共识时的交易hash |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password |    string    | 密码           |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述   |
| ----- |:------:| ------ |
| value | string | 交易hash |

获取账户地址的指定token余额
================
Cmd: /api/contract/balance/token/{contractAddress}/{address}
------------------------------------------------------------
### CmdType: RESTFUL
### HttpMethod: GET
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名             |  参数类型  | 参数描述 | 是否非空 |
| --------------- |:------:| ---- |:----:|
| contractAddress | string | 合约地址 |  是   |
| address         | string | 账户地址 |  是   |

返回值
---
| 字段名             |  字段类型  | 参数描述                    |
| --------------- |:------:| ----------------------- |
| contractAddress | string | 合约地址                    |
| name            | string | token名称                 |
| symbol          | string | token符号                 |
| amount          | string | token数量                 |
| decimals        |  long  | token支持的小数位数            |
| blockHeight     |  long  | 合约创建时的区块高度              |
| status          |  int   | 合约状态(0-不存在, 1-正常, 2-终止) |

单笔转账
====
Cmd: /api/contract/create
-------------------------
### CmdType: RESTFUL
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                          |      参数类型      | 参数描述                 | 是否非空 |
| ------------------------------------------------------------ |:--------------:| -------------------- |:----:|
| 创建合约                                                         | contractcreate | 创建合约表单               |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;contractCode |     string     | 智能合约代码(字节码的Hex编码字符串) |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;alias        |     string     | 合约别名                 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;args         |    object[]    | 参数列表                 |  是   |

返回值
---
| 字段名             |  字段类型  | 参数描述        |
| --------------- |:------:| ----------- |
| txHash          | string | 发布合约的交易hash |
| contractAddress | string | 生成的合约地址     |

单笔转账
====
Cmd: /api/contract/call
-----------------------
### CmdType: RESTFUL
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                             |     参数类型     | 参数描述               | 是否非空 |
| --------------------------------------------------------------- |:------------:| ------------------ |:----:|
| 调用合约                                                            | contractcall | 调用合约表单             |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;contractAddress |    string    | 智能合约地址             |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value           |     long     | 交易附带的货币量           |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;methodName      |    string    | 方法名                |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;methodDesc      |    string    | 方法签名，如果方法名不重复，可以不传 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;args            |   object[]   | 参数列表               |  是   |

返回值
---
| 字段名    |  字段类型  | 参数描述        |
| ------ |:------:| ----------- |
| txHash | string | 调用合约的交易hash |

单笔转账
====
Cmd: /api/contract/delete
-------------------------
### CmdType: RESTFUL
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                             |      参数类型      | 参数描述      | 是否非空 |
| --------------------------------------------------------------- |:--------------:| --------- |:----:|
| 删除合约                                                            | contractdelete | 删除合约表单    |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sender          |     string     | 交易创建者     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;contractAddress |     string     | 智能合约地址    |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password        |     string     | 交易创建者账户密码 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;remark          |     string     | 备注        |  是   |

返回值
---
| 字段名    |  字段类型  | 参数描述        |
| ------ |:------:| ----------- |
| txHash | string | 删除合约的交易hash |

获取智能合约执行结果
==========
Cmd: /api/contract/result/{hash}
--------------------------------
### CmdType: RESTFUL
### HttpMethod: GET
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名  |  参数类型  | 参数描述   | 是否非空 |
| ---- |:------:| ------ |:----:|
| hash | string | 交易hash |  是   |

返回值
---
| 字段名                                                                                                   |      字段类型       | 参数描述                                        |
| ----------------------------------------------------------------------------------------------------- |:---------------:| ------------------------------------------- |
| success                                                                                               |     boolean     | 合约执行是否成功                                    |
| errorMessage                                                                                          |     string      | 执行失败信息                                      |
| contractAddress                                                                                       |     string      | 合约地址                                        |
| result                                                                                                |     string      | 合约执行结果                                      |
| gasLimit                                                                                              |      long       | GAS限制                                       |
| gasUsed                                                                                               |      long       | 已使用GAS                                      |
| price                                                                                                 |      long       | GAS单价                                       |
| totalFee                                                                                              |     string      | 交易总手续费                                      |
| txSizeFee                                                                                             |     string      | 交易大小手续费                                     |
| actualContractFee                                                                                     |     string      | 实际执行合约手续费                                   |
| refundFee                                                                                             |     string      | 合约返回的手续费                                    |
| value                                                                                                 |     string      | 调用者向合约地址转入的主网资产金额，没有此业务时则为0                 |
| stackTrace                                                                                            |     string      | 异常堆栈踪迹                                      |
| transfers                                                                                             | list&lt;object> | 合约转账列表（从合约转出）                               |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;txHash                                                |     string      | 合约生成交易：合约转账交易hash                           |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;from                                                  |     string      | 转出的合约地址                                     |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value                                                 |     string      | 转账金额                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;outputs                                               | list&lt;object> | 转入的地址列表                                     |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;to    |     string      | 转入地址                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value |     string      | 转入金额                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;orginTxHash                                           |     string      | 调用合约交易hash（源交易hash，合约交易由调用合约交易派生而来）         |
| events                                                                                                | list&lt;string> | 合约事件列表                                      |
| tokenTransfers                                                                                        | list&lt;object> | 合约token转账列表                                 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;contractAddress                                       |     string      | 合约地址                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;from                                                  |     string      | 付款方                                         |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;to                                                    |     string      | 收款方                                         |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value                                                 |     string      | 转账金额                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;name                                                  |     string      | token名称                                     |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;symbol                                                |     string      | token符号                                     |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;decimals                                              |      long       | token支持的小数位数                                |
| invokeRegisterCmds                                                                                    | list&lt;object> | 合约调用外部命令的调用记录列表                             |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;cmdName                                               |     string      | 命令名称                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;args                                                  |       map       | 命令参数，参数不固定，依据不同的命令而来，故此处不作描述，结构为 {参数名称=参数值} |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;cmdRegisterMode                                       |     string      | 注册的命令模式（QUERY\_DATA or NEW\_TX）             |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;newTxHash                                             |     string      | 生成的交易hash（当调用的命令模式是 NEW\_TX 时，会生成交易）        |
| contractTxList                                                                                        | list&lt;string> | 合约生成交易的序列化字符串列表                             |
| remark                                                                                                |     string      | 备注                                          |

获取智能合约详细信息
==========
Cmd: /api/contract/info/{address}
---------------------------------
### CmdType: RESTFUL
### HttpMethod: GET
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名     |  参数类型  | 参数描述 | 是否非空 |
| ------- |:------:| ---- |:----:|
| address | string | 合约地址 |  是   |

返回值
---
| 字段名                                                                                                      |      字段类型       | 参数描述                          |
| -------------------------------------------------------------------------------------------------------- |:---------------:| ----------------------------- |
| createTxHash                                                                                             |     string      | 发布合约的交易hash                   |
| address                                                                                                  |     string      | 合约地址                          |
| creater                                                                                                  |     string      | 合约创建者地址                       |
| alias                                                                                                    |     string      | 合约别名                          |
| createTime                                                                                               |      long       | 合约创建时间（单位：秒）                  |
| blockHeight                                                                                              |      long       | 合约创建时的区块高度                    |
| isDirectPayable                                                                                          |     boolean     | 是否接受直接转账                      |
| isNrc20                                                                                                  |     boolean     | 是否是NRC20合约                    |
| nrc20TokenName                                                                                           |     string      | NRC20-token名称                 |
| nrc20TokenSymbol                                                                                         |     string      | NRC20-token符号                 |
| decimals                                                                                                 |      long       | NRC20-token支持的小数位数            |
| totalSupply                                                                                              |     string      | NRC20-token发行总量               |
| status                                                                                                   |     string      | 合约状态（not_found, normal, stop） |
| method                                                                                                   | list&lt;object> | 合约方法列表                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;name                                                     |     string      | 方法名称                          |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;desc                                                     |     string      | 方法描述                          |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;args                                                     | list&lt;object> | 方法参数列表                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;type     |     string      | 参数类型                          |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;name     |     string      | 参数名称                          |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;required |     boolean     | 是否必填                          |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;returnArg                                                |     string      | 返回值类型                         |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;view                                                     |     boolean     | 是否视图方法（调用此方法数据不上链）            |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;event                                                    |     boolean     | 是否是事件                         |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;payable                                                  |     boolean     | 是否是可接受主链资产转账的方法               |

根据私钥导入账户
========
Cmd: /api/account/import/pri
----------------------------
### CmdType: RESTFUL
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                       |           参数类型            | 参数描述                           | 是否非空 |
| --------------------------------------------------------- |:-------------------------:| ------------------------------ |:----:|
| 根据私钥导入账户                                                  | accountprikeypasswordform | 根据私钥导入账户表单                     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;priKey    |          string           | 私钥                             |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password  |          string           | 密码                             |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;overwrite |          boolean          | 是否覆盖账户: false:不覆盖导入, true:覆盖导入 |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述 |
| ----- |:------:| ---- |
| value | string | 账户地址 |

批量创建账户
======
Cmd: /api/account/import/pri
----------------------------
### CmdType: RESTFUL
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                      |       参数类型        | 参数描述     | 是否非空 |
| -------------------------------------------------------- |:-----------------:| -------- |:----:|
| 批量创建账户                                                   | accountcreateform | 批量创建账户表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;count    |        int        | 新建账户数量   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password |      string       | 账户密码     |  是   |

返回值
---
| 字段名  |      字段类型       | 参数描述   |
| ---- |:---------------:| ------ |
| list | list&lt;string> | 交易hash |

根据keystore文件路径导入账户
==================
Cmd: /api/account/import/keystore/path
--------------------------------------
### CmdType: RESTFUL
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                       |           参数类型            | 参数描述                           | 是否非空 |
| --------------------------------------------------------- |:-------------------------:| ------------------------------ |:----:|
| 根据keystore文件路径导入账户                                        | accountkeystoreimportform | 根据keystore文件路径导入账户表单           |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;path      |          string           | 本地keystore文件路径                 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password  |          string           | 密码                             |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;overwrite |          boolean          | 是否覆盖账户: false:不覆盖导入, true:覆盖导入 |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述 |
| ----- |:------:| ---- |
| value | string | 账户地址 |

根据keystore字符串导入账户
=================
Cmd: /api/account/import/keystore/string
----------------------------------------
### CmdType: RESTFUL
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                            |              参数类型               | 参数描述                           | 是否非空 |
| -------------------------------------------------------------- |:-------------------------------:| ------------------------------ |:----:|
| 根据keystore字符串导入账户                                              | accountkeystorestringimportform | 根据keystore字符串导入账户表单            |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;keystoreString |             string              | keystore字符串                    |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password       |             string              | 密码                             |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;overwrite      |             boolean             | 是否覆盖账户: false:不覆盖导入, true:覆盖导入 |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述 |
| ----- |:------:| ---- |
| value | string | 账户地址 |

账户备份，导出AccountKeyStore文件到指定目录
=============================
Cmd: /api/account/export/{address}
----------------------------------
### CmdType: RESTFUL
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                      |         参数类型          | 参数描述           | 是否非空 |
| -------------------------------------------------------- |:---------------------:| -------------- |:----:|
| address                                                  |        string         | 账户地址           |  是   |
| keystone导出信息                                             | accountkeystorebackup | keystone导出信息表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password |        string         | 密码             |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;path     |        string         | 文件路径           |  是   |

返回值
---
| 字段名  |  字段类型  | 参数描述    |
| ---- |:------:| ------- |
| path | string | 导出的文件路径 |

根据AccountKeyStore导入账户
=====================
Cmd: /api/account/import/keystore
---------------------------------
### CmdType: RESTFUL
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                      |    参数类型     | 参数描述       | 是否非空 |
| -------------------------------------------------------- |:-----------:| ---------- |:----:|
| 根据私钥导入账户                                                 | inputstream | 根据私钥导入账户表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;根据私钥导入账户 | inputstream | 根据私钥导入账户表单 |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述 |
| ----- |:------:| ---- |
| value | string | 账户地址 |

[修改密码] 根据原密码修改账户密码
==================
Cmd: /api/account/password/{address}
------------------------------------
### CmdType: RESTFUL
### HttpMethod: PUT
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                         |           参数类型            | 参数描述     | 是否非空 |
| ----------------------------------------------------------- |:-------------------------:| -------- |:----:|
| address                                                     |          string           | 账户地址     |  是   |
| 账户密码信息                                                      | accountupdatepasswordform | 账户密码信息表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password    |          string           | 原始密码     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;newPassword |          string           | 新密码      |  是   |

返回值
---
| 字段名   |  字段类型   | 参数描述   |
| ----- |:-------:| ------ |
| value | boolean | 是否修改成功 |

账户备份，导出账户私钥，只能导出本地创建或导入的账户
==========================
Cmd: /api/account/prikey/{address}
----------------------------------
### CmdType: RESTFUL
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                      |        参数类型         | 参数描述     | 是否非空 |
| -------------------------------------------------------- |:-------------------:| -------- |:----:|
| address                                                  |       string        | 账户地址     |  是   |
| 账户密码信息                                                   | accountpasswordform | 账户密码信息表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password |       string        | 密码       |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述 |
| ----- |:------:| ---- |
| value | string | 私钥   |

根据区块hash查询区块头
=============
Cmd: /api/block/header/hash/{hash}
----------------------------------
### CmdType: RESTFUL
### HttpMethod: GET
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名  |  参数类型  | 参数描述   | 是否非空 |
| ---- |:------:| ------ |:----:|
| hash | string | 区块hash |  是   |

返回值
---
| 字段名                  |  字段类型  | 参数描述                 |
| -------------------- |:------:| -------------------- |
| hash                 | string | 区块的hash值             |
| preHash              | string | 上一个区块的hash值          |
| merkleHash           | string | 梅克尔hash              |
| time                 | string | 区块生成时间               |
| height               |  long  | 区块高度                 |
| txCount              |  int   | 区块打包交易数量             |
| blockSignature       | string | 签名Hex.encode(byte[]) |
| size                 |  int   | 大小                   |
| packingAddress       | string | 打包地址                 |
| roundIndex           |  long  | 共识轮次                 |
| consensusMemberCount |  int   | 参与共识成员数量             |
| roundStartTime       | string | 当前共识轮开始时间            |
| packingIndexOfRound  |  int   | 当前轮次打包出块的名次          |
| mainVersion          | short  | 主网当前生效的版本            |
| blockVersion         | short  | 区块的版本，可以理解为本地钱包的版本   |
| stateRoot            | string | 智能合约世界状态根            |

根据区块高度查询区块头
===========
Cmd: /api/block/header/height/{height}
--------------------------------------
### CmdType: RESTFUL
### HttpMethod: GET
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名    | 参数类型 | 参数描述 | 是否非空 |
| ------ |:----:| ---- |:----:|
| height | long | 区块高度 |  是   |

返回值
---
| 字段名                  |  字段类型  | 参数描述                 |
| -------------------- |:------:| -------------------- |
| hash                 | string | 区块的hash值             |
| preHash              | string | 上一个区块的hash值          |
| merkleHash           | string | 梅克尔hash              |
| time                 | string | 区块生成时间               |
| height               |  long  | 区块高度                 |
| txCount              |  int   | 区块打包交易数量             |
| blockSignature       | string | 签名Hex.encode(byte[]) |
| size                 |  int   | 大小                   |
| packingAddress       | string | 打包地址                 |
| roundIndex           |  long  | 共识轮次                 |
| consensusMemberCount |  int   | 参与共识成员数量             |
| roundStartTime       | string | 当前共识轮开始时间            |
| packingIndexOfRound  |  int   | 当前轮次打包出块的名次          |
| mainVersion          | short  | 主网当前生效的版本            |
| blockVersion         | short  | 区块的版本，可以理解为本地钱包的版本   |
| stateRoot            | string | 智能合约世界状态根            |

根据区块高度查询区块，包含区块打包的所有交易信息，此接口返回数据量较多，谨慎调用
========================================
Cmd: /api/block/height/{height}
-------------------------------
### CmdType: RESTFUL
### HttpMethod: GET
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名    | 参数类型 | 参数描述 | 是否非空 |
| ------ |:----:| ---- |:----:|
| height | long | 区块高度 |  是   |

返回值
---
| 字段名                  |  字段类型  | 参数描述                 |
| -------------------- |:------:| -------------------- |
| hash                 | string | 区块的hash值             |
| preHash              | string | 上一个区块的hash值          |
| merkleHash           | string | 梅克尔hash              |
| time                 | string | 区块生成时间               |
| height               |  long  | 区块高度                 |
| txCount              |  int   | 区块打包交易数量             |
| blockSignature       | string | 签名Hex.encode(byte[]) |
| size                 |  int   | 大小                   |
| packingAddress       | string | 打包地址                 |
| roundIndex           |  long  | 共识轮次                 |
| consensusMemberCount |  int   | 参与共识成员数量             |
| roundStartTime       | string | 当前共识轮开始时间            |
| packingIndexOfRound  |  int   | 当前轮次打包出块的名次          |
| mainVersion          | short  | 主网当前生效的版本            |
| blockVersion         | short  | 区块的版本，可以理解为本地钱包的版本   |
| stateRoot            | string | 智能合约世界状态根            |

根据区块hash查询区块，包含区块打包的所有交易信息，此接口返回数据量较多，谨慎调用
==========================================
Cmd: /api/block/hash/{hash}
---------------------------
### CmdType: RESTFUL
### HttpMethod: GET
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名  |  参数类型  | 参数描述   | 是否非空 |
| ---- |:------:| ------ |:----:|
| hash | string | 区块hash |  是   |

返回值
---
| 字段名                  |  字段类型  | 参数描述                 |
| -------------------- |:------:| -------------------- |
| hash                 | string | 区块的hash值             |
| preHash              | string | 上一个区块的hash值          |
| merkleHash           | string | 梅克尔hash              |
| time                 | string | 区块生成时间               |
| height               |  long  | 区块高度                 |
| txCount              |  int   | 区块打包交易数量             |
| blockSignature       | string | 签名Hex.encode(byte[]) |
| size                 |  int   | 大小                   |
| packingAddress       | string | 打包地址                 |
| roundIndex           |  long  | 共识轮次                 |
| consensusMemberCount |  int   | 参与共识成员数量             |
| roundStartTime       | string | 当前共识轮开始时间            |
| packingIndexOfRound  |  int   | 当前轮次打包出块的名次          |
| mainVersion          | short  | 主网当前生效的版本            |
| blockVersion         | short  | 区块的版本，可以理解为本地钱包的版本   |
| stateRoot            | string | 智能合约世界状态根            |

查询最新区块
======
Cmd: /api/block/newest
----------------------
### CmdType: RESTFUL
### HttpMethod: GET
### ContentType: application/json;charset=UTF-8


参数列表
----
无参数

返回值
---
| 字段名                                                                                                      |      字段类型       | 参数描述                                      |
| -------------------------------------------------------------------------------------------------------- |:---------------:| ----------------------------------------- |
| header                                                                                                   |     object      | 区块头信息, 只返回对应的部分数据                         |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;hash                                                     |     string      | 区块的hash值                                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;preHash                                                  |     string      | 上一个区块的hash值                               |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;merkleHash                                               |     string      | 梅克尔hash                                   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;time                                                     |     string      | 区块生成时间                                    |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;height                                                   |      long       | 区块高度                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;txCount                                                  |       int       | 区块打包交易数量                                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;blockSignature                                           |     string      | 签名Hex.encode(byte[])                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;size                                                     |       int       | 大小                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;packingAddress                                           |     string      | 打包地址                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;roundIndex                                               |      long       | 共识轮次                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;consensusMemberCount                                     |       int       | 参与共识成员数量                                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;roundStartTime                                           |     string      | 当前共识轮开始时间                                 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;packingIndexOfRound                                      |       int       | 当前轮次打包出块的名次                               |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;mainVersion                                              |      short      | 主网当前生效的版本                                 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;blockVersion                                             |      short      | 区块的版本，可以理解为本地钱包的版本                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;stateRoot                                                |     string      | 智能合约世界状态根                                 |
| txs                                                                                                      | list&lt;object> | 交易列表                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;hash                                                     |     string      | 交易的hash值                                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;type                                                     |       int       | 交易类型                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;time                                                     |     string      | 交易时间                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;blockHeight                                              |      long       | 区块高度                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;remark                                                   |     string      | 交易备注                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;transactionSignature                                     |     string      | 交易签名                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;status                                                   |       int       | 交易状态 0:unConfirm(待确认), 1:confirm(已确认)     |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;size                                                     |       int       | 交易大小                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;inBlockIndex                                             |       int       | 在区块中的顺序，存储在rocksDB中是无序的，保存区块时赋值，取出后根据此值排序 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;form                                                     | list&lt;object> | 输入                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;nonce    |     string      | 账户nonce值的Hex字符串，防止双花交易，取上一笔交易hash的最后8个字节  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;locked   |      byte       | 0普通交易，-1解锁金额交易（退出共识，退出委托）                 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;to                                                       | list&lt;object> | 输出                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;lockTime |      long       | 解锁时间，-1为永久锁定                              |

查询最新区块头信息
=========
Cmd: /api/block/header/newest
-----------------------------
### CmdType: RESTFUL
### HttpMethod: GET
### ContentType: application/json;charset=UTF-8


参数列表
----
无参数

返回值
---
| 字段名                  |  字段类型  | 参数描述                 |
| -------------------- |:------:| -------------------- |
| hash                 | string | 区块的hash值             |
| preHash              | string | 上一个区块的hash值          |
| merkleHash           | string | 梅克尔hash              |
| time                 | string | 区块生成时间               |
| height               |  long  | 区块高度                 |
| txCount              |  int   | 区块打包交易数量             |
| blockSignature       | string | 签名Hex.encode(byte[]) |
| size                 |  int   | 大小                   |
| packingAddress       | string | 打包地址                 |
| roundIndex           |  long  | 共识轮次                 |
| consensusMemberCount |  int   | 参与共识成员数量             |
| roundStartTime       | string | 当前共识轮开始时间            |
| packingIndexOfRound  |  int   | 当前轮次打包出块的名次          |
| mainVersion          | short  | 主网当前生效的版本            |
| blockVersion         | short  | 区块的版本，可以理解为本地钱包的版本   |
| stateRoot            | string | 智能合约世界状态根            |

单笔转账
====
Cmd: /api/accountledger/transfer
--------------------------------
### CmdType: RESTFUL
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                       |     参数类型     | 参数描述   | 是否非空 |
| --------------------------------------------------------- |:------------:| ------ |:----:|
| 单笔转账                                                      | transferform | 单笔转账表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address   |    string    | 账户地址   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;toAddress |    string    | 账户地址   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password  |    string    | 账户密码   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount    |  biginteger  | 金额     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;remark    |    string    | 备注     |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述   |
| ----- |:------:| ------ |
| value | string | 交易hash |

查询账户余额
======
Cmd: /api/accountledger/balance/{address}
-----------------------------------------
### CmdType: RESTFUL
### HttpMethod: GET
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名     |  参数类型  | 参数描述 | 是否非空 |
| ------- |:------:| ---- |:----:|
| address | string | 账户地址 |  是   |

返回值
---
| 字段名       |  字段类型  | 参数描述 |
| --------- |:------:| ---- |
| total     | string | 总余额  |
| freeze    | string | 锁定金额 |
| available | string | 可用余额 |

根据hash获取交易，先查未确认，查不到再查已确认
=========================
Cmd: /api/accountledger/tx/{hash}
---------------------------------
### CmdType: RESTFUL
### HttpMethod: GET
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名  |  参数类型  | 参数描述   | 是否非空 |
| ---- |:------:| ------ |:----:|
| hash | string | 交易hash |  是   |

返回值
---
| 字段名                                                      |      字段类型       | 参数描述                                      |
| -------------------------------------------------------- |:---------------:| ----------------------------------------- |
| hash                                                     |     string      | 交易的hash值                                  |
| type                                                     |       int       | 交易类型                                      |
| time                                                     |     string      | 交易时间                                      |
| blockHeight                                              |      long       | 区块高度                                      |
| remark                                                   |     string      | 交易备注                                      |
| transactionSignature                                     |     string      | 交易签名                                      |
| status                                                   |       int       | 交易状态 0:unConfirm(待确认), 1:confirm(已确认)     |
| size                                                     |       int       | 交易大小                                      |
| inBlockIndex                                             |       int       | 在区块中的顺序，存储在rocksDB中是无序的，保存区块时赋值，取出后根据此值排序 |
| form                                                     | list&lt;object> | 输入                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;nonce    |     string      | 账户nonce值的Hex字符串，防止双花交易，取上一笔交易hash的最后8个字节  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;locked   |      byte       | 0普通交易，-1解锁金额交易（退出共识，退出委托）                 |
| to                                                       | list&lt;object> | 输出                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;lockTime |      long       | 解锁时间，-1为永久锁定                              |

