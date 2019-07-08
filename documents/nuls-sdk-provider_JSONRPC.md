# NULS2.0 SDK-Provider

**SDK模块，与核心模块连接，依赖离线SDK包，以HTTP接口作为服务，提供了JSON-RPC和RESTFUL的请求方式，
以此模块作为访问底层钱包数据的桥梁，用于在线和离线的交易组装、查询等功能**


批量创建账户
======
Cmd: createAccount
------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名      |  参数类型  | 参数描述 | 是否非空 |
| -------- |:------:| ---- |:----:|
| chainId  |  int   | 链ID  |  是   |
| count    |  int   | 创建数量 |  是   |
| password | string | 密码   |  是   |

返回值
---
| 字段名 |      字段类型       | 参数描述     |
| --- |:---------------:| -------- |
| 返回值 | list&lt;string> | 返回账户地址集合 |

重置账密码
=====
Cmd: updatePassword
-------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名         |  参数类型  | 参数描述 | 是否非空 |
| ----------- |:------:| ---- |:----:|
| chainId     |  int   | 链ID  |  是   |
| address     | string | 账户地址 |  是   |
| oldPassword | string | 账户密码 |  是   |
| newPassword | string | 新密码  |  是   |

返回值
---
| 字段名   |  字段类型   | 参数描述   |
| ----- |:-------:| ------ |
| value | boolean | 是否修改成功 |

根据keystore导入账户
==============
Cmd: importKeystore
-------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名          |  参数类型  | 参数描述        | 是否非空 |
| ------------ |:------:| ----------- |:----:|
| chainId      |  int   | 链ID         |  是   |
| keyStoreJson | string | keyStore字符串 |  是   |
| password     | string | keystore密码  |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述 |
| ----- |:------:| ---- |
| value | string | 账户地址 |

根据keystore导入账户
==============
Cmd: exportKeystore
-------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名      |  参数类型  | 参数描述 | 是否非空 |
| -------- |:------:| ---- |:----:|
| chainId  |  int   | 链ID  |  是   |
| address  | string | 账户地址 |  是   |
| password | string | 账户密码 |  是   |

返回值
---
| 字段名    |  字段类型  | 参数描述     |
| ------ |:------:| -------- |
| result | string | keystore |

获取账户余额
======
Cmd: getAccountBalance
----------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名      |  参数类型  | 参数描述 | 是否非空 |
| -------- |:------:| ---- |:----:|
| chainId  |  int   | 链ID  |  是   |
| address  | string | 账户地址 |  是   |
| password | string | 账户密码 |  是   |

返回值
---
| 字段名       |  字段类型  | 参数描述 |
| --------- |:------:| ---- |
| total     | string | 总余额  |
| freeze    | string | 锁定金额 |
| available | string | 可用余额 |

离线 - 批量创建账户
===========
Cmd: createAccountOffline
-------------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名      |  参数类型  | 参数描述 | 是否非空 |
| -------- |:------:| ---- |:----:|
| chainId  |  int   | 链ID  |  是   |
| count    |  int   | 创建数量 |  是   |
| password | string | 密码   |  是   |

返回值
---
| 字段名                 |  字段类型  | 参数描述   |
| ------------------- |:------:| ------ |
| address             | string | 账户地址   |
| pubKey              | string | 公钥     |
| prikey              | string | 明文私钥   |
| encryptedPrivateKey | string | 加密后的私钥 |

根据私钥导入账户
========
Cmd: importPriKey
-----------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名      |  参数类型  | 参数描述   | 是否非空 |
| -------- |:------:| ------ |:----:|
| chainId  |  int   | 链ID    |  是   |
| priKey   | string | 账户明文私钥 |  是   |
| password | string | 新密码    |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述 |
| ----- |:------:| ---- |
| value | string | 账户地址 |

Create an agent for consensus! 创建共识(代理)节点
=========================================
Cmd: createAgent
----------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                            |      参数类型       | 参数描述         | 是否非空 |
| -------------------------------------------------------------- |:---------------:| ------------ |:----:|
| chainId                                                        |       int       | 链ID          |  是   |
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

注销共识节点
======
Cmd: stopAgent
--------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                      |     参数类型      | 参数描述     | 是否非空 |
| -------------------------------------------------------- |:-------------:| -------- |:----:|
| chainId                                                  |      int      | 链ID      |  是   |
| 注销共识节点                                                   | stopagentform | 注销共识节点表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address  |    string     | 共识节点地址   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password |    string     | 密码       |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述   |
| ----- |:------:| ------ |
| value | string | 交易hash |

deposit nuls to a bank! 申请参与共识
==============================
Cmd: depositToAgent
-------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                       |    参数类型     | 参数描述     | 是否非空 |
| --------------------------------------------------------- |:-----------:| -------- |:----:|
| chainId                                                   |     int     | 链ID      |  是   |
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
Cmd: withdraw
-------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                      |     参数类型     | 参数描述         | 是否非空 |
| -------------------------------------------------------- |:------------:| ------------ |:----:|
| chainId                                                  |     int      | 链ID          |  是   |
| 退出共识                                                     | withdrawform | 退出共识表单       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address  |    string    | 节点地址         |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;txHash   |    string    | 加入共识时的交易hash |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password |    string    | 密码           |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述   |
| ----- |:------:| ------ |
| value | string | 交易hash |

离线组装 - 创建共识(代理)节点
=================
Cmd: createAgentOffline
-----------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                                                                     |     参数类型     | 参数描述           | 是否非空 |
| ------------------------------------------------------------------------------------------------------- |:------------:| -------------- |:----:|
| chainId                                                                                                 |     int      | 链ID            |  是   |
| 离线创建共识(代理)节点                                                                                            | consensusdto | 离线创建共识(代理)节点表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;agentAddress                                            |    string    | 节点创建地址         |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;packingAddress                                          |    string    | 节点出块地址         |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;rewardAddress                                           |    string    | 获取共识奖励地址       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;commissionRate                                          |     int      | 节点佣金比例         |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;deposit                                                 |  biginteger  | 创建节点保证金        |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;input                                                   |    object    | 交易输入信息         |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address |    string    | 账户地址           |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;chainId |     int      | 资产的链id         |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetId |     int      | 资产id           |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount  |  biginteger  | 资产金额           |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;nonce   |    string    | 资产nonce值       |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述     |
| ----- |:------:| -------- |
| hash  | string | 交易hash   |
| txHex | string | 交易序列化字符串 |

离线组装 - 注销共识节点
=============
Cmd: stopAgentOffline
---------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                                                                                                                     |       参数类型       | 参数描述        | 是否非空 |
| ------------------------------------------------------------------------------------------------------------------------------------------------------- |:----------------:| ----------- |:----:|
| chainId                                                                                                                                                 |       int        | 链ID         |  是   |
| 离线注销共识节点                                                                                                                                                | stopconsensusdto | 离线注销共识节点表单  |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;agentHash                                                                                               |      string      | 创建节点的交易hash |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;agentAddress                                                                                            |      string      | 节点地址        |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;deposit                                                                                                 |    biginteger    | 创建节点的保证金    |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;price                                                                                                   |    biginteger    | 手续费单价       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;depositList                                                                                             | list&lt;object>  | 停止委托列表      |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;depositHash                                             |      string      | 委托共识的交易hash |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;input                                                   |      object      | 交易输入信息      |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address |      string      | 账户地址        |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;chainId |       int        | 资产的链id      |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetId |       int        | 资产id        |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount  |    biginteger    | 资产金额        |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;nonce   |      string      | 资产nonce值    |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述     |
| ----- |:------:| -------- |
| hash  | string | 交易hash   |
| txHex | string | 交易序列化字符串 |

离线组装 - 申请参与共识
=============
Cmd: depositToAgentOffline
--------------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                                                                     |    参数类型    | 参数描述       | 是否非空 |
| ------------------------------------------------------------------------------------------------------- |:----------:| ---------- |:----:|
| chainId                                                                                                 |    int     | 链ID        |  是   |
| 离线申请参与共识                                                                                                | depositdto | 离线申请参与共识表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address                                                 |   string   | 账户地址       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;deposit                                                 | biginteger | 委托金额       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;agentHash                                               |   string   | 共识节点hash   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;input                                                   |   object   | 交易输入信息     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address |   string   | 账户地址       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;chainId |    int     | 资产的链id     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetId |    int     | 资产id       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount  | biginteger | 资产金额       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;nonce   |   string   | 资产nonce值   |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述     |
| ----- |:------:| -------- |
| hash  | string | 交易hash   |
| txHex | string | 交易序列化字符串 |

离线组装 - 退出共识
===========
Cmd: withdrawOffline
--------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                                                                     |    参数类型     | 参数描述        | 是否非空 |
| ------------------------------------------------------------------------------------------------------- |:-----------:| ----------- |:----:|
| chainId                                                                                                 |     int     | 链ID         |  是   |
| 离线退出共识                                                                                                  | withdrawdto | 离线退出共识表单    |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address                                                 |   string    | 地址          |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;depositHash                                             |   string    | 委托共识交易的hash |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;price                                                   | biginteger  | 手续费单价       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;input                                                   |   object    | 交易输入信息      |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address |   string    | 账户地址        |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;chainId |     int     | 资产的链id      |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetId |     int     | 资产id        |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount  | biginteger  | 资产金额        |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;nonce   |   string    | 资产nonce值    |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述     |
| ----- |:------:| -------- |
| hash  | string | 交易hash   |
| txHex | string | 交易序列化字符串 |

根据区块高度查询区块，包含区块打包的所有交易信息，此接口返回数据量较多，谨慎调用
========================================
Cmd: getBlockByHeight
---------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名     | 参数类型 | 参数描述 | 是否非空 |
| ------- |:----:| ---- |:----:|
| chainId | int  | 链ID  |  是   |
| height  | long | 区块高度 |  是   |

返回值
---
| 字段名                                                                                                           |      字段类型       | 参数描述                                      |
| ------------------------------------------------------------------------------------------------------------- |:---------------:| ----------------------------------------- |
| header                                                                                                        |     object      | 区块头信息, 只返回对应的部分数据                         |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;hash                                                          |     string      | 区块的hash值                                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;preHash                                                       |     string      | 上一个区块的hash值                               |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;merkleHash                                                    |     string      | 梅克尔hash                                   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;time                                                          |     string      | 区块生成时间                                    |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;height                                                        |      long       | 区块高度                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;txCount                                                       |       int       | 区块打包交易数量                                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;blockSignature                                                |     string      | 签名Hex.encode(byte[])                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;size                                                          |       int       | 大小                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;packingAddress                                                |     string      | 打包地址                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;roundIndex                                                    |      long       | 共识轮次                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;consensusMemberCount                                          |       int       | 参与共识成员数量                                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;roundStartTime                                                |     string      | 当前共识轮开始时间                                 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;packingIndexOfRound                                           |       int       | 当前轮次打包出块的名次                               |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;mainVersion                                                   |      short      | 主网当前生效的版本                                 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;blockVersion                                                  |      short      | 区块的版本，可以理解为本地钱包的版本                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;stateRoot                                                     |     string      | 智能合约世界状态根                                 |
| txs                                                                                                           | list&lt;object> | 交易列表                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;hash                                                          |     string      | 交易的hash值                                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;type                                                          |       int       | 交易类型                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;time                                                          |     string      | 交易时间                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;blockHeight                                                   |      long       | 区块高度                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;remark                                                        |     string      | 交易备注                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;transactionSignature                                          |     string      | 交易签名                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;status                                                        |       int       | 交易状态 0:unConfirm(待确认), 1:confirm(已确认)     |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;size                                                          |       int       | 交易大小                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;inBlockIndex                                                  |       int       | 在区块中的顺序，存储在rocksDB中是无序的，保存区块时赋值，取出后根据此值排序 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;form                                                          | list&lt;object> | 输入                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address       |     string      | 账户地址                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetsChainId |       int       | 资产发行链的id                                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetsId      |       int       | 资产id                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount        |     string      | 数量                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;nonce         |     string      | 账户nonce值的Hex字符串，防止双花交易，取上一笔交易hash的最后8个字节  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;locked        |      byte       | 0普通交易，-1解锁金额交易（退出共识，退出委托）                 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;to                                                            | list&lt;object> | 输出                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address       |     string      | 账户地址                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetsChainId |       int       | 资产发行链的id                                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetsId      |       int       | 资产id                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount        |     string      | 数量                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;lockTime      |      long       | 解锁时间，-1为永久锁定                              |

根据区块hash查询区块，包含区块打包的所有交易信息，此接口返回数据量较多，谨慎调用
==========================================
Cmd: getBlockByHash
-------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名     |  参数类型  | 参数描述   | 是否非空 |
| ------- |:------:| ------ |:----:|
| chainId |  int   | 链ID    |  是   |
| hash    | string | 区块hash |  是   |

返回值
---
| 字段名                                                                                                           |      字段类型       | 参数描述                                      |
| ------------------------------------------------------------------------------------------------------------- |:---------------:| ----------------------------------------- |
| header                                                                                                        |     object      | 区块头信息, 只返回对应的部分数据                         |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;hash                                                          |     string      | 区块的hash值                                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;preHash                                                       |     string      | 上一个区块的hash值                               |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;merkleHash                                                    |     string      | 梅克尔hash                                   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;time                                                          |     string      | 区块生成时间                                    |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;height                                                        |      long       | 区块高度                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;txCount                                                       |       int       | 区块打包交易数量                                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;blockSignature                                                |     string      | 签名Hex.encode(byte[])                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;size                                                          |       int       | 大小                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;packingAddress                                                |     string      | 打包地址                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;roundIndex                                                    |      long       | 共识轮次                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;consensusMemberCount                                          |       int       | 参与共识成员数量                                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;roundStartTime                                                |     string      | 当前共识轮开始时间                                 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;packingIndexOfRound                                           |       int       | 当前轮次打包出块的名次                               |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;mainVersion                                                   |      short      | 主网当前生效的版本                                 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;blockVersion                                                  |      short      | 区块的版本，可以理解为本地钱包的版本                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;stateRoot                                                     |     string      | 智能合约世界状态根                                 |
| txs                                                                                                           | list&lt;object> | 交易列表                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;hash                                                          |     string      | 交易的hash值                                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;type                                                          |       int       | 交易类型                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;time                                                          |     string      | 交易时间                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;blockHeight                                                   |      long       | 区块高度                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;remark                                                        |     string      | 交易备注                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;transactionSignature                                          |     string      | 交易签名                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;status                                                        |       int       | 交易状态 0:unConfirm(待确认), 1:confirm(已确认)     |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;size                                                          |       int       | 交易大小                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;inBlockIndex                                                  |       int       | 在区块中的顺序，存储在rocksDB中是无序的，保存区块时赋值，取出后根据此值排序 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;form                                                          | list&lt;object> | 输入                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address       |     string      | 账户地址                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetsChainId |       int       | 资产发行链的id                                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetsId      |       int       | 资产id                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount        |     string      | 数量                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;nonce         |     string      | 账户nonce值的Hex字符串，防止双花交易，取上一笔交易hash的最后8个字节  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;locked        |      byte       | 0普通交易，-1解锁金额交易（退出共识，退出委托）                 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;to                                                            | list&lt;object> | 输出                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address       |     string      | 账户地址                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetsChainId |       int       | 资产发行链的id                                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetsId      |       int       | 资产id                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount        |     string      | 数量                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;lockTime      |      long       | 解锁时间，-1为永久锁定                              |

查询最新区块，包含区块打包的所有交易信息，此接口返回数据量较多，谨慎调用
====================================
Cmd: getBestBlock
-----------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名     | 参数类型 | 参数描述 | 是否非空 |
| ------- |:----:| ---- |:----:|
| chainId | int  | 链ID  |  是   |

返回值
---
| 字段名                                                                                                           |      字段类型       | 参数描述                                      |
| ------------------------------------------------------------------------------------------------------------- |:---------------:| ----------------------------------------- |
| header                                                                                                        |     object      | 区块头信息, 只返回对应的部分数据                         |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;hash                                                          |     string      | 区块的hash值                                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;preHash                                                       |     string      | 上一个区块的hash值                               |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;merkleHash                                                    |     string      | 梅克尔hash                                   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;time                                                          |     string      | 区块生成时间                                    |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;height                                                        |      long       | 区块高度                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;txCount                                                       |       int       | 区块打包交易数量                                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;blockSignature                                                |     string      | 签名Hex.encode(byte[])                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;size                                                          |       int       | 大小                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;packingAddress                                                |     string      | 打包地址                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;roundIndex                                                    |      long       | 共识轮次                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;consensusMemberCount                                          |       int       | 参与共识成员数量                                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;roundStartTime                                                |     string      | 当前共识轮开始时间                                 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;packingIndexOfRound                                           |       int       | 当前轮次打包出块的名次                               |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;mainVersion                                                   |      short      | 主网当前生效的版本                                 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;blockVersion                                                  |      short      | 区块的版本，可以理解为本地钱包的版本                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;stateRoot                                                     |     string      | 智能合约世界状态根                                 |
| txs                                                                                                           | list&lt;object> | 交易列表                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;hash                                                          |     string      | 交易的hash值                                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;type                                                          |       int       | 交易类型                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;time                                                          |     string      | 交易时间                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;blockHeight                                                   |      long       | 区块高度                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;remark                                                        |     string      | 交易备注                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;transactionSignature                                          |     string      | 交易签名                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;status                                                        |       int       | 交易状态 0:unConfirm(待确认), 1:confirm(已确认)     |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;size                                                          |       int       | 交易大小                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;inBlockIndex                                                  |       int       | 在区块中的顺序，存储在rocksDB中是无序的，保存区块时赋值，取出后根据此值排序 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;form                                                          | list&lt;object> | 输入                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address       |     string      | 账户地址                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetsChainId |       int       | 资产发行链的id                                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetsId      |       int       | 资产id                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount        |     string      | 数量                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;nonce         |     string      | 账户nonce值的Hex字符串，防止双花交易，取上一笔交易hash的最后8个字节  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;locked        |      byte       | 0普通交易，-1解锁金额交易（退出共识，退出委托）                 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;to                                                            | list&lt;object> | 输出                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address       |     string      | 账户地址                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetsChainId |       int       | 资产发行链的id                                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetsId      |       int       | 资产id                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount        |     string      | 数量                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;lockTime      |      long       | 解锁时间，-1为永久锁定                              |

查询最新区块头信息
=========
Cmd: getBestBlockHeader
-----------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名     | 参数类型 | 参数描述 | 是否非空 |
| ------- |:----:| ---- |:----:|
| chainId | int  | 链ID  |  是   |

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
Cmd: getHeaderByHeight
----------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名     | 参数类型 | 参数描述 | 是否非空 |
| ------- |:----:| ---- |:----:|
| chainId | int  | 链ID  |  是   |
| height  | long | 区块高度 |  是   |

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

根据区块hash查询区块头
=============
Cmd: getHeaderByHash
--------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名     |  参数类型  | 参数描述   | 是否非空 |
| ------- |:------:| ------ |:----:|
| chainId |  int   | 链ID    |  是   |
| hash    | string | 区块hash |  是   |

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

获取智能合约执行结果
==========
Cmd: getContractTxResult
------------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名     |  参数类型  | 参数描述   | 是否非空 |
| ------- |:------:| ------ |:----:|
| chainId |  int   | 链ID    |  是   |
| hash    | string | 交易hash |  是   |

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

获取账户地址的指定token余额
================
Cmd: getTokenBalance
--------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名             |  参数类型  | 参数描述 | 是否非空 |
| --------------- |:------:| ---- |:----:|
| chainId         |  int   | 链id  |  是   |
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

获取合约代码构造函数
==========
Cmd: getContractConstructor
---------------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名          |  参数类型  | 参数描述                 | 是否非空 |
| ------------ |:------:| -------------------- |:----:|
| chainId      |  int   | 链ID                  |  是   |
| contractCode | string | 智能合约代码(字节码的Hex编码字符串) |  是   |

返回值
---
| 字段名                                                                                                      |      字段类型       | 参数描述               |
| -------------------------------------------------------------------------------------------------------- |:---------------:| ------------------ |
| constructor                                                                                              |     object      | 合约构造函数详情           |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;name                                                     |     string      | 方法名称               |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;desc                                                     |     string      | 方法描述               |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;args                                                     | list&lt;object> | 方法参数列表             |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;type     |     string      | 参数类型               |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;name     |     string      | 参数名称               |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;required |     boolean     | 是否必填               |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;returnArg                                                |     string      | 返回值类型              |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;view                                                     |     boolean     | 是否视图方法（调用此方法数据不上链） |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;event                                                    |     boolean     | 是否是事件              |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;payable                                                  |     boolean     | 是否是可接受主链资产转账的方法    |
| isNrc20                                                                                                  |     boolean     | 是否是NRC20合约         |

验证发布合约
======
Cmd: validateContractCreate
---------------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名          |   参数类型   | 参数描述                 | 是否非空 |
| ------------ |:--------:| -------------------- |:----:|
| chainId      |   int    | 链id                  |  是   |
| sender       |  string  | 交易创建者账户地址            |  是   |
| gasLimit     |   long   | GAS限制                |  是   |
| price        |   long   | GAS单价                |  是   |
| contractCode |  string  | 智能合约代码(字节码的Hex编码字符串) |  是   |
| args         | object[] | 参数列表                 |  是   |

返回值
---
| 字段名     |  字段类型   | 参数描述      |
| ------- |:-------:| --------- |
| success | boolean | 验证成功与否    |
| code    | string  | 验证失败的错误码  |
| msg     | string  | 验证失败的错误信息 |

验证调用合约
======
Cmd: validateContractCall
-------------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名             |    参数类型    | 参数描述                                     | 是否非空 |
| --------------- |:----------:| ---------------------------------------- |:----:|
| chainId         |    int     | 链id                                      |  是   |
| sender          |   string   | 交易创建者账户地址                                |  是   |
| value           | biginteger | 调用者向合约地址转入的主网资产金额，没有此业务时填BigInteger.ZERO |  是   |
| gasLimit        |    long    | GAS限制                                    |  是   |
| price           |    long    | GAS单价                                    |  是   |
| contractAddress |   string   | 合约地址                                     |  是   |
| methodName      |   string   | 合约方法                                     |  是   |
| methodDesc      |   string   | 合约方法描述，若合约内方法没有重载，则此参数可以为空               |  是   |
| args            |  object[]  | 参数列表                                     |  是   |

返回值
---
| 字段名     |  字段类型   | 参数描述      |
| ------- |:-------:| --------- |
| success | boolean | 验证成功与否    |
| code    | string  | 验证失败的错误码  |
| msg     | string  | 验证失败的错误信息 |

验证删除合约
======
Cmd: validateContractDelete
---------------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名             |  参数类型  | 参数描述      | 是否非空 |
| --------------- |:------:| --------- |:----:|
| chainId         |  int   | 链id       |  是   |
| sender          | string | 交易创建者账户地址 |  是   |
| contractAddress | string | 合约地址      |  是   |

返回值
---
| 字段名     |  字段类型   | 参数描述      |
| ------- |:-------:| --------- |
| success | boolean | 验证成功与否    |
| code    | string  | 验证失败的错误码  |
| msg     | string  | 验证失败的错误信息 |

估算发布合约交易的GAS
============
Cmd: imputedContractCreateGas
-----------------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名          |   参数类型   | 参数描述                 | 是否非空 |
| ------------ |:--------:| -------------------- |:----:|
| chainId      |   int    | 链id                  |  是   |
| sender       |  string  | 交易创建者账户地址            |  是   |
| contractCode |  string  | 智能合约代码(字节码的Hex编码字符串) |  是   |
| args         | object[] | 参数列表                 |  是   |

返回值
---
| 字段名      | 字段类型 | 参数描述              |
| -------- |:----:| ----------------- |
| gasLimit | long | 消耗的gas值，执行失败返回数值1 |

估算调用合约交易的GAS
============
Cmd: imputedContractCallGas
---------------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名             |    参数类型    | 参数描述                                     | 是否非空 |
| --------------- |:----------:| ---------------------------------------- |:----:|
| chainId         |    int     | 链id                                      |  是   |
| sender          |   string   | 交易创建者账户地址                                |  是   |
| value           | biginteger | 调用者向合约地址转入的主网资产金额，没有此业务时填BigInteger.ZERO |  是   |
| contractAddress |   string   | 合约地址                                     |  是   |
| methodName      |   string   | 合约方法                                     |  是   |
| methodDesc      |   string   | 合约方法描述，若合约内方法没有重载，则此参数可以为空               |  是   |
| args            |  object[]  | 参数列表                                     |  是   |

返回值
---
| 字段名      | 字段类型 | 参数描述              |
| -------- |:----:| ----------------- |
| gasLimit | long | 消耗的gas值，执行失败返回数值1 |

调用合约不上链方法
=========
Cmd: invokeView
---------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名             |   参数类型   | 参数描述                       | 是否非空 |
| --------------- |:--------:| -------------------------- |:----:|
| chainId         |   int    | 链id                        |  是   |
| contractAddress |  string  | 合约地址                       |  是   |
| methodName      |  string  | 合约方法                       |  是   |
| methodDesc      |  string  | 合约方法描述，若合约内方法没有重载，则此参数可以为空 |  是   |
| args            | object[] | 参数列表                       |  是   |

返回值
---
| 字段名    |  字段类型  | 参数描述      |
| ------ |:------:| --------- |
| result | string | 视图方法的调用结果 |

token转账
=======
Cmd: tokentransfer
------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名             |    参数类型    | 参数描述                                     | 是否非空 |
| --------------- |:----------:| ---------------------------------------- |:----:|
| chainId         |    int     | 链id                                      |  是   |
| fromAddress     |   string   | 交易创建者账户地址                                |  是   |
| password        |   string   | 调用者账户密码                                  |  是   |
| toAddress       |   string   | 调用者向合约地址转入的主网资产金额，没有此业务时填BigInteger.ZERO |  是   |
| contractAddress |   string   | 合约地址                                     |  是   |
| amount          | biginteger | 合约方法                                     |  是   |
| remark          |   string   | 交易备注                                     |  是   |

返回值
---
| 字段名    |  字段类型  | 参数描述   |
| ------ |:------:| ------ |
| txHash | string | 交易hash |

获取合约方法信息
========
Cmd: getContractMethod
----------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名             |  参数类型  | 参数描述 | 是否非空 |
| --------------- |:------:| ---- |:----:|
| chainId         |  int   | 链ID  |  是   |
| contractAddress | string | 合约地址 |  是   |
| methodName      | string | 方法名称 |  是   |
| methodDesc      | string | 方法描述 |  是   |

返回值
---
| 字段名                                                      |      字段类型       | 参数描述               |
| -------------------------------------------------------- |:---------------:| ------------------ |
| name                                                     |     string      | 方法名称               |
| desc                                                     |     string      | 方法描述               |
| args                                                     | list&lt;object> | 方法参数列表             |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;type     |     string      | 参数类型               |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;name     |     string      | 参数名称               |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;required |     boolean     | 是否必填               |
| returnArg                                                |     string      | 返回值类型              |
| view                                                     |     boolean     | 是否视图方法（调用此方法数据不上链） |
| event                                                    |     boolean     | 是否是事件              |
| payable                                                  |     boolean     | 是否是可接受主链资产转账的方法    |

获取合约方法参数类型
==========
Cmd: getContractMethodArgsTypes
-------------------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名             |  参数类型  | 参数描述 | 是否非空 |
| --------------- |:------:| ---- |:----:|
| chainId         |  int   | 链ID  |  是   |
| contractAddress | string | 合约地址 |  是   |
| methodName      | string | 方法名称 |  是   |
| methodDesc      | string | 方法描述 |  是   |

返回值
---
| 字段名 |      字段类型       | 参数描述 |
| --- |:---------------:| ---- |
| 返回值 | list&lt;string> |      |

发布合约
====
Cmd: contractCreate
-------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名          |   参数类型   | 参数描述                 | 是否非空 |
| ------------ |:--------:| -------------------- |:----:|
| chainId      |   int    | 链id                  |  是   |
| sender       |  string  | 交易创建者账户地址            |  是   |
| password     |  string  | 账户密码                 |  是   |
| alias        |  string  | 合约别名                 |  是   |
| gasLimit     |   long   | GAS限制                |  是   |
| price        |   long   | GAS单价                |  是   |
| contractCode |  string  | 智能合约代码(字节码的Hex编码字符串) |  是   |
| args         | object[] | 参数列表                 |  是   |
| remark       |  string  | 交易备注                 |  是   |

返回值
---
| 字段名             |  字段类型  | 参数描述        |
| --------------- |:------:| ----------- |
| txHash          | string | 发布合约的交易hash |
| contractAddress | string | 生成的合约地址     |

调用合约
====
Cmd: contractCall
-----------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名             |    参数类型    | 参数描述                                     | 是否非空 |
| --------------- |:----------:| ---------------------------------------- |:----:|
| chainId         |    int     | 链id                                      |  是   |
| sender          |   string   | 交易创建者账户地址                                |  是   |
| password        |   string   | 调用者账户密码                                  |  是   |
| value           | biginteger | 调用者向合约地址转入的主网资产金额，没有此业务时填BigInteger.ZERO |  是   |
| gasLimit        |    long    | GAS限制                                    |  是   |
| price           |    long    | GAS单价                                    |  是   |
| contractAddress |   string   | 合约地址                                     |  是   |
| methodName      |   string   | 合约方法                                     |  是   |
| methodDesc      |   string   | 合约方法描述，若合约内方法没有重载，则此参数可以为空               |  是   |
| args            |  object[]  | 参数列表                                     |  是   |
| remark          |   string   | 交易备注                                     |  是   |

返回值
---
| 字段名    |  字段类型  | 参数描述        |
| ------ |:------:| ----------- |
| txHash | string | 调用合约的交易hash |

删除合约
====
Cmd: contractDelete
-------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名             |  参数类型  | 参数描述      | 是否非空 |
| --------------- |:------:| --------- |:----:|
| chainId         |  int   | 链id       |  是   |
| sender          | string | 交易创建者账户地址 |  是   |
| password        | string | 交易账户密码    |  是   |
| contractAddress | string | 合约地址      |  是   |
| remark          | string | 交易备注      |  是   |

返回值
---
| 字段名    |  字段类型  | 参数描述        |
| ------ |:------:| ----------- |
| txHash | string | 删除合约的交易hash |

从账户地址向合约地址转账(主链资产)的合约交易
=======================
Cmd: transfer2contract
----------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名         |    参数类型    | 参数描述                                     | 是否非空 |
| ----------- |:----------:| ---------------------------------------- |:----:|
| chainId     |    int     | 链id                                      |  是   |
| fromAddress |   string   | 交易创建者账户地址                                |  是   |
| password    |   string   | 调用者账户密码                                  |  是   |
| toAddress   |   string   | 调用者向合约地址转入的主网资产金额，没有此业务时填BigInteger.ZERO |  是   |
| amount      | biginteger | 合约方法                                     |  是   |
| remark      |   string   | 交易备注                                     |  是   |

返回值
---
| 字段名    |  字段类型  | 参数描述   |
| ------ |:------:| ------ |
| txHash | string | 交易hash |

获取智能合约详细信息
==========
Cmd: getContract
----------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名             |  参数类型  | 参数描述 | 是否非空 |
| --------------- |:------:| ---- |:----:|
| chainId         |  int   | 链ID  |  是   |
| contractAddress | string | 合约地址 |  是   |

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

离线 - 发布合约交易
===========
Cmd: contractCreateOffline
--------------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名          |   参数类型   | 参数描述                 | 是否非空 |
| ------------ |:--------:| -------------------- |:----:|
| chainId      |   int    | 链id                  |  是   |
| sender       |  string  | 交易创建者账户地址            |  是   |
| alias        |  string  | 合约别名                 |  是   |
| contractCode |  string  | 智能合约代码(字节码的Hex编码字符串) |  是   |
| args         | object[] | 参数列表                 |  是   |
| remark       |  string  | 交易备注                 |  是   |

返回值
---
| 字段名             |  字段类型  | 参数描述     |
| --------------- |:------:| -------- |
| hash            | string | 交易hash   |
| txHex           | string | 交易序列化字符串 |
| contractAddress | string | 生成的合约地址  |

离线 - 调用合约
=========
Cmd: contractCallOffline
------------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名             |    参数类型    | 参数描述                                     | 是否非空 |
| --------------- |:----------:| ---------------------------------------- |:----:|
| chainId         |    int     | 链id                                      |  是   |
| sender          |   string   | 交易创建者账户地址                                |  是   |
| value           | biginteger | 调用者向合约地址转入的主网资产金额，没有此业务时填BigInteger.ZERO |  是   |
| contractAddress |   string   | 合约地址                                     |  是   |
| methodName      |   string   | 合约方法                                     |  是   |
| methodDesc      |   string   | 合约方法描述，若合约内方法没有重载，则此参数可以为空               |  是   |
| args            |  object[]  | 参数列表                                     |  是   |
| remark          |   string   | 交易备注                                     |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述     |
| ----- |:------:| -------- |
| hash  | string | 交易hash   |
| txHex | string | 交易序列化字符串 |

离线 - 删除合约
=========
Cmd: contractDeleteOffline
--------------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名             |  参数类型  | 参数描述      | 是否非空 |
| --------------- |:------:| --------- |:----:|
| chainId         |  int   | 链id       |  是   |
| sender          | string | 交易创建者账户地址 |  是   |
| contractAddress | string | 合约地址      |  是   |
| remark          | string | 交易备注      |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述     |
| ----- |:------:| -------- |
| hash  | string | 交易hash   |
| txHex | string | 交易序列化字符串 |

离线 - token转账
============
Cmd: tokentransferOffline
-------------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名             |    参数类型    | 参数描述                                     | 是否非空 |
| --------------- |:----------:| ---------------------------------------- |:----:|
| chainId         |    int     | 链id                                      |  是   |
| fromAddress     |   string   | 交易创建者账户地址                                |  是   |
| toAddress       |   string   | 调用者向合约地址转入的主网资产金额，没有此业务时填BigInteger.ZERO |  是   |
| contractAddress |   string   | 合约地址                                     |  是   |
| amount          | biginteger | 合约方法                                     |  是   |
| remark          |   string   | 交易备注                                     |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述     |
| ----- |:------:| -------- |
| hash  | string | 交易hash   |
| txHex | string | 交易序列化字符串 |

离线 - 从账户地址向合约地址转账(主链资产)的合约交易
============================
Cmd: transfer2contractOffline
-----------------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名         |    参数类型    | 参数描述                                     | 是否非空 |
| ----------- |:----------:| ---------------------------------------- |:----:|
| chainId     |    int     | 链id                                      |  是   |
| fromAddress |   string   | 交易创建者账户地址                                |  是   |
| toAddress   |   string   | 调用者向合约地址转入的主网资产金额，没有此业务时填BigInteger.ZERO |  是   |
| amount      | biginteger | 合约方法                                     |  是   |
| remark      |   string   | 交易备注                                     |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述     |
| ----- |:------:| -------- |
| hash  | string | 交易hash   |
| txHex | string | 交易序列化字符串 |

单笔转账
====
Cmd: transfer
-------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名       |  参数类型  | 参数描述   | 是否非空 |
| --------- |:------:| ------ |:----:|
| chainId   |  int   | 链id    |  是   |
| assetId   |  int   | 资产id   |  是   |
| address   | string | 转出账户地址 |  是   |
| toAddress | string | 转入账户地址 |  是   |
| password  | string | 转出账户密码 |  是   |
| amount    | string | 转出金额   |  是   |
| remark    | string | 备注     |  是   |

返回值
---
| 字段名  |  字段类型  | 参数描述   |
| ---- |:------:| ------ |
| hash | string | 交易hash |

离线组装转账交易
========
Cmd: createTransferTxOffline
----------------------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名                                                                                                      |      参数类型       | 参数描述     | 是否非空 |
| -------------------------------------------------------------------------------------------------------- |:---------------:| -------- |:----:|
| transferDto                                                                                              |   transferdto   | 转账交易表单   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;inputs                                                   | list&lt;object> | 转账交易输入列表 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address  |     string      | 账户地址     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;chainId  |       int       | 资产的链id   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetId  |       int       | 资产id     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount   |   biginteger    | 资产金额     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;nonce    |     string      | 资产nonce值 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;outputs                                                  | list&lt;object> | 转账交易输出列表 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address  |     string      | 账户地址     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;chainId  |       int       | 资产的链id   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetId  |       int       | 资产id     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount   |   biginteger    | 资产金额     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;lockTime |      long       | 锁定时间     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;remark                                                   |     string      | 交易备注     |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述         |
| ----- |:------:| ------------ |
| hash  | string | 交易hash       |
| txHex | string | 交易序列化16进制字符串 |

验证交易
====
Cmd: validateTx
---------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名     |  参数类型  | 参数描述     | 是否非空 |
| ------- |:------:| -------- |:----:|
| chainId |  int   | 链id      |  是   |
| tx      | string | 交易序列化字符串 |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述   |
| ----- |:------:| ------ |
| value | string | 交易hash |

广播交易
====
Cmd: broadcastTx
----------------
### CmdType: JSONRPC
### HttpMethod: POST
### ContentType: application/json;charset=UTF-8


参数列表
----
| 参数名     |  参数类型  | 参数描述     | 是否非空 |
| ------- |:------:| -------- |:----:|
| chainId |  int   | 链id      |  是   |
| tx      | string | 交易序列化字符串 |  是   |

返回值
---
| 字段名   |  字段类型   | 参数描述   |
| ----- |:-------:| ------ |
| value | boolean | 是否成功   |
| hash  | string  | 交易hash |

