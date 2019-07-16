# NULS2.0 SDK-Provider

**SDK模块，与核心模块连接，依赖离线SDK包，以HTTP接口作为服务，提供了`JSON-RPC`和`RESTFUL`的请求方式，
以此模块作为访问底层钱包数据的桥梁，用于在线和离线的交易组装、查询等功能**


- **`JSON-RPC`访问方式**

     添加请求头 Content-Type: application/json;charset=UTF-8
     
     HttpMethod: POST
     
     请求数据格式: 
     
     ```json
     {
       "jsonrpc":"2.0",
       "method":"methodCMD", //接口名称
       "params":[],          //所有接口的参数，都以数组方式传递，且参数顺序不能变，若参数是非必填，也必须填入null占位
       "id":1234
     }
     ```

- **`RESTFUL`访问方式**

     添加请求头 Content-Type: application/json;charset=UTF-8
     
     其余请参考 [RESTFUL 接口文档](https://github.com/nuls-io/nuls-sdk-provider/blob/master/documents/nuls-sdk-provider_RESTFUL.md)


## 接口文档

我们提供了两种请求方式`JSON-RPC`和`RESTFUL`的文档

[JSON-RPC 接口文档](https://github.com/nuls-io/nuls-sdk-provider/blob/master/documents/nuls-sdk-provider_JSONRPC.md)

[RESTFUL 接口文档](https://github.com/nuls-io/nuls-sdk-provider/blob/master/documents/nuls-sdk-provider_RESTFUL.md)

## 接口调试

我们提供了`Postman`接口调式工具的导入文件(`JSON-RPC`和`RESTFUL`)，导入后，即可调试接口

[JSON-PRC 接口调试-POSTMAN导入文件](https://github.com/nuls-io/nuls-sdk-provider/blob/master/documents/nuls-sdk-provider_Postman_JSONRPC.json)

[RESTFUL 接口调试-POSTMAN导入文件](https://github.com/nuls-io/nuls-sdk-provider/blob/master/documents/nuls-sdk-provider_Postman_RESTFUL.json)



0.1 获取本链相关信息
============
Cmd: /api/info
--------------
_**详细描述: 获取本链相关信息**_
### HttpMethod: GET

参数列表
----
无参数

返回值
---
| 字段名             |  字段类型  | 参数描述         |
| --------------- |:------:| ------------ |
| chainId         | string | 本链的ID        |
| assetId         | string | 本链默认主资产的ID   |
| inflationAmount | string | 本链默认主资产的初始数量 |
| agentChainId    | string | 本链共识资产的链ID   |
| agentAssetId    | string | 本链共识资产的ID    |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

1.1 批量创建账户
==========
Cmd: /api/account
-----------------
_**详细描述: 创建的账户存在于本地钱包内**_
### HttpMethod: POST

### Form json data: 
```json
{
  "count" : 0,
  "password" : null
}
```

参数列表
----
| 参数名                                                      |       参数类型        | 参数描述     | 是否必填 |
| -------------------------------------------------------- |:-----------------:| -------- |:----:|
| 批量创建账户                                                   | accountcreateform | 批量创建账户表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;count    |        int        | 新建账户数量   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password |      string       | 账户密码     |  是   |

返回值
---
| 字段名  |      字段类型       | 参数描述 |
| ---- |:---------------:| ---- |
| list | list&lt;string> | 账户地址 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

1.2 修改账户密码
==========
Cmd: /api/account/password/{address}
------------------------------------
_**详细描述: 修改账户密码**_
### HttpMethod: PUT

### Form json data: 
```json
{
  "password" : null,
  "newPassword" : null
}
```

参数列表
----
| 参数名                                                         |           参数类型            | 参数描述     | 是否必填 |
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
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

1.3 导出账户私钥
==========
Cmd: /api/account/prikey/{address}
----------------------------------
_**详细描述: 只能导出本地钱包已存在账户的私钥**_
### HttpMethod: POST

### Form json data: 
```json
{
  "password" : null
}
```

参数列表
----
| 参数名                                                      |        参数类型         | 参数描述     | 是否必填 |
| -------------------------------------------------------- |:-------------------:| -------- |:----:|
| address                                                  |       string        | 账户地址     |  是   |
| 账户密码信息                                                   | accountpasswordform | 账户密码信息表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password |       string        | 密码       |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述 |
| ----- |:------:| ---- |
| value | string | 私钥   |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

1.4 根据私钥导入账户
============
Cmd: /api/account/import/pri
----------------------------
_**详细描述: 导入私钥时，需要输入密码给明文私钥加密**_
### HttpMethod: POST

### Form json data: 
```json
{
  "priKey" : null,
  "password" : null,
  "overwrite" : false
}
```

参数列表
----
| 参数名                                                       |           参数类型            | 参数描述                           | 是否必填 |
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
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

1.5 根据keyStore导入账户
==================
Cmd: /api/account/import/keystore
---------------------------------
_**详细描述: 根据keyStore导入账户**_
### HttpMethod: POST

参数列表
----
| 参数名                                                      |    参数类型     | 参数描述       | 是否必填 |
| -------------------------------------------------------- |:-----------:| ---------- |:----:|
| 根据私钥导入账户                                                 | inputstream | 根据私钥导入账户表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;根据私钥导入账户 | inputstream | 根据私钥导入账户表单 |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述 |
| ----- |:------:| ---- |
| value | string | 账户地址 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

1.6 根据keystore文件路径导入账户
======================
Cmd: /api/account/import/keystore/path
--------------------------------------
_**详细描述: 根据keystore文件路径导入账户**_
### HttpMethod: POST

### Form json data: 
```json
{
  "path" : null,
  "password" : null,
  "overwrite" : false
}
```

参数列表
----
| 参数名                                                       |           参数类型            | 参数描述                           | 是否必填 |
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
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

1.7 根据keystore字符串导入账户
=====================
Cmd: /api/account/import/keystore/string
----------------------------------------
_**详细描述: 根据keystore字符串导入账户**_
### HttpMethod: POST

### Form json data: 
```json
{
  "keystoreString" : null,
  "password" : null,
  "overwrite" : false
}
```

参数列表
----
| 参数名                                                            |              参数类型               | 参数描述                           | 是否必填 |
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
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

1.8 账户备份，导出AccountKeyStore文件到指定目录
=================================
Cmd: /api/account/export/{address}
----------------------------------
_**详细描述: 账户备份，导出AccountKeyStore文件到指定目录**_
### HttpMethod: POST

### Form json data: 
```json
{
  "password" : null,
  "path" : null
}
```

参数列表
----
| 参数名                                                      |         参数类型          | 参数描述           | 是否必填 |
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
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

1.9 查询账户余额
==========
Cmd: /api/accountledger/balance/{address}
-----------------------------------------
_**详细描述: 根据资产链ID和资产ID，查询本链账户对应资产的余额与nonce值**_
### HttpMethod: POST

### Form json data: 
```json
{
  "assetChainId" : 0,
  "assetId" : 0
}
```

参数列表
----
| 参数名                                                          |    参数类型     | 参数描述   | 是否必填 |
| ------------------------------------------------------------ |:-----------:| ------ |:----:|
| balanceDto                                                   | balanceform | 账户余额表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetChainId |     int     | 资产的链ID |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetId      |     int     | 资产ID   |  是   |

返回值
---
| 字段名           |  字段类型  | 参数描述                      |
| ------------- |:------:| ------------------------- |
| total         | string | 总余额                       |
| freeze        | string | 锁定金额                      |
| available     | string | 可用余额                      |
| timeLock      | string | 时间锁定金额                    |
| consensusLock | string |  共识锁定金额                   |
| nonce         | string | 账户资产nonce值                |
| nonceType     |  int   | 1：已确认的nonce值,0：未确认的nonce值 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

1.10 离线 - 批量创建账户
================
Cmd: /api/account/offline
-------------------------
_**详细描述: 创建的账户不会保存到钱包中,接口直接返回账户的keystore信息**_
### HttpMethod: POST

### Form json data: 
```json
{
  "count" : 0,
  "password" : null
}
```

参数列表
----
| 参数名                                                      |       参数类型        | 参数描述       | 是否必填 |
| -------------------------------------------------------- |:-----------------:| ---------- |:----:|
| 离线批量创建账户                                                 | accountcreateform | 离线批量创建账户表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;count    |        int        | 新建账户数量     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password |      string       | 账户密码       |  是   |

返回值
---
| 字段名                                                                 |      字段类型       | 参数描述         |
| ------------------------------------------------------------------- |:---------------:| ------------ |
| list                                                                | list&lt;object> | 账户keystore列表 |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address             |     string      | 账户地址         |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;pubKey              |     string      | 公钥           |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;prikey              |     string      | 明文私钥         |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;encryptedPrivateKey |     string      | 加密后的私钥       |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

1.11 离线获取账户明文私钥
===============
Cmd: /api/account/priKey/offline
--------------------------------
_**详细描述: 离线获取账户明文私钥**_
### HttpMethod: POST

### Form json data: 
```json
{
  "address" : null,
  "encryptedPriKey" : null,
  "password" : null
}
```

参数列表
----
| 参数名                                                             |     参数类型      | 参数描述         | 是否必填 |
| --------------------------------------------------------------- |:-------------:| ------------ |:----:|
| 离线获取账户明文私钥                                                      | getprikeyform | 离线获取账户明文私钥表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address         |    string     | 账户地址         |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;encryptedPriKey |    string     | 账户密文私钥       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password        |    string     | 账户密码         |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述 |
| ----- |:------:| ---- |
| value | string | 明文私钥 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

1.12 离线修改账户密码
=============
Cmd: /api/account/password/offline/{address}
--------------------------------------------
_**详细描述: 离线修改账户密码**_
### HttpMethod: PUT

### Form json data: 
```json
{
  "address" : null,
  "encryptedPriKey" : null,
  "oldPassword" : null,
  "newPassword" : null
}
```

参数列表
----
| 参数名                                                             |       参数类型        | 参数描述       | 是否必填 |
| --------------------------------------------------------------- |:-----------------:| ---------- |:----:|
| 离线修改账户密码                                                        | resetpasswordform | 离线修改账户密码表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address         |      string       | 账户地址       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;encryptedPriKey |      string       | 账户密文私钥     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;oldPassword     |      string       | 账户原密码      |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;newPassword     |      string       | 账户新密码      |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述       |
| ----- |:------:| ---------- |
| value | string | 重置密码后的加密私钥 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

1.13 多账户摘要签名
============
Cmd: /api/account/multi/sign
----------------------------
_**详细描述: 用于签名离线组装的多账户转账交易，调用接口时，参数可以传地址和私钥，或者传地址和加密私钥和加密密码**_
### HttpMethod: POST

### Form json data: 
```json
{
  "dtoList" : [ {
    "address" : null,
    "priKey" : null,
    "encryptedPrivateKey" : null,
    "password" : null
  } ],
  "txHex" : null
}
```

参数列表
----
| 参数名                                                                                                                 |      参数类型       | 参数描述        | 是否必填 |
| ------------------------------------------------------------------------------------------------------------------- |:---------------:| ----------- |:----:|
| 多账户摘要签名                                                                                                             |  multisignform  | 多账户摘要签名表单   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;dtoList                                                             | list&lt;object> | keystore集合  |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address             |     string      | 地址          |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;priKey              |     string      | 明文私钥        |  否   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;encryptedPrivateKey |     string      | 加密私钥        |  否   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password            |     string      | 密码          |  否   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;txHex                                                               |     string      | 交易序列化Hex字符串 |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述          |
| ----- |:------:| ------------- |
| hash  | string | 交易hash        |
| txHex | string | 签名后的交易16进制字符串 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

1.14 明文私钥摘要签名
=============
Cmd: /api/account/priKey/sign
-----------------------------
_**详细描述: 明文私钥摘要签名**_
### HttpMethod: POST

### Form json data: 
```json
{
  "txHex" : null,
  "address" : null,
  "priKey" : null
}
```

参数列表
----
| 参数名                                                     |      参数类型      | 参数描述        | 是否必填 |
| ------------------------------------------------------- |:--------------:| ----------- |:----:|
| 明文私钥摘要签名                                                | prikeysignform | 明文私钥摘要签名表单  |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;txHex   |     string     | 交易序列化Hex字符串 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address |     string     | 账户地址        |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;priKey  |     string     | 账户明文私钥      |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述          |
| ----- |:------:| ------------- |
| hash  | string | 交易hash        |
| txHex | string | 签名后的交易16进制字符串 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

1.15 密文私钥摘要签名
=============
Cmd: /api/account/encryptedPriKey/sign
--------------------------------------
_**详细描述: 密文私钥摘要签名**_
### HttpMethod: POST

### Form json data: 
```json
{
  "txHex" : null,
  "address" : null,
  "encryptedPriKey" : null,
  "password" : null
}
```

参数列表
----
| 参数名                                                             |          参数类型           | 参数描述        | 是否必填 |
| --------------------------------------------------------------- |:-----------------------:| ----------- |:----:|
| 密文私钥摘要签名                                                        | encryptedprikeysignform | 密文私钥摘要签名表单  |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;txHex           |         string          | 交易序列化Hex字符串 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address         |         string          | 账户地址        |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;encryptedPriKey |         string          | 账户密文私钥      |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password        |         string          | 账户密码        |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述          |
| ----- |:------:| ------------- |
| hash  | string | 交易hash        |
| txHex | string | 签名后的交易16进制字符串 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

2.1 根据区块高度查询区块头
===============
Cmd: /api/block/header/height/{height}
--------------------------------------
_**详细描述: 根据区块高度查询区块头**_
### HttpMethod: GET

参数列表
----
| 参数名    | 参数类型 | 参数描述 | 是否必填 |
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
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

2.2 根据区块hash查询区块头
=================
Cmd: /api/block/header/hash/{hash}
----------------------------------
_**详细描述: 根据区块hash查询区块头**_
### HttpMethod: GET

参数列表
----
| 参数名  |  参数类型  | 参数描述   | 是否必填 |
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
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

2.3 查询最新区块头信息
=============
Cmd: /api/block/header/newest
-----------------------------
_**详细描述: 查询最新区块头信息**_
### HttpMethod: GET

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
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

2.4 查询最新区块
==========
Cmd: /api/block/newest
----------------------
_**详细描述: 包含区块打包的所有交易信息，此接口返回数据量较多，谨慎调用**_
### HttpMethod: GET

参数列表
----
无参数

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
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

2.5 根据区块高度查询区块
==============
Cmd: /api/block/height/{height}
-------------------------------
_**详细描述: 包含区块打包的所有交易信息，此接口返回数据量较多，谨慎调用**_
### HttpMethod: GET

参数列表
----
| 参数名    | 参数类型 | 参数描述 | 是否必填 |
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
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

2.6 根据区块hash查询区块
================
Cmd: /api/block/hash/{hash}
---------------------------
_**详细描述: 包含区块打包的所有交易信息，此接口返回数据量较多，谨慎调用**_
### HttpMethod: GET

参数列表
----
| 参数名  |  参数类型  | 参数描述   | 是否必填 |
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
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

3.1 根据hash获取交易
==============
Cmd: /api/tx/tx/{hash}
----------------------
_**详细描述: 根据hash获取交易**_
### HttpMethod: GET

参数列表
----
| 参数名  |  参数类型  | 参数描述   | 是否必填 |
| ---- |:------:| ------ |:----:|
| hash | string | 交易hash |  是   |

返回值
---
| 字段名                                                           |      字段类型       | 参数描述                                      |
| ------------------------------------------------------------- |:---------------:| ----------------------------------------- |
| hash                                                          |     string      | 交易的hash值                                  |
| type                                                          |       int       | 交易类型                                      |
| time                                                          |     string      | 交易时间                                      |
| blockHeight                                                   |      long       | 区块高度                                      |
| remark                                                        |     string      | 交易备注                                      |
| transactionSignature                                          |     string      | 交易签名                                      |
| status                                                        |       int       | 交易状态 0:unConfirm(待确认), 1:confirm(已确认)     |
| size                                                          |       int       | 交易大小                                      |
| inBlockIndex                                                  |       int       | 在区块中的顺序，存储在rocksDB中是无序的，保存区块时赋值，取出后根据此值排序 |
| form                                                          | list&lt;object> | 输入                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address       |     string      | 账户地址                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetsChainId |       int       | 资产发行链的id                                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetsId      |       int       | 资产id                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount        |     string      | 数量                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;nonce         |     string      | 账户nonce值的Hex字符串，防止双花交易，取上一笔交易hash的最后8个字节  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;locked        |      byte       | 0普通交易，-1解锁金额交易（退出共识，退出委托）                 |
| to                                                            | list&lt;object> | 输出                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address       |     string      | 账户地址                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetsChainId |       int       | 资产发行链的id                                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetsId      |       int       | 资产id                                      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount        |     string      | 数量                                        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;lockTime      |      long       | 解锁时间，-1为永久锁定                              |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

3.2 验证交易
========
Cmd: /api/accountledger/transaction/validate
--------------------------------------------
_**详细描述: 验证离线组装的交易,验证成功返回交易hash值,失败返回错误提示信息**_
### HttpMethod: POST

### Form json data: 
```json
{
  "txHex" : null
}
```

参数列表
----
| 参数名                                                   |  参数类型  | 参数描述         | 是否必填 |
| ----------------------------------------------------- |:------:| ------------ |:----:|
| 验证交易是否正确                                              | txform | 验证交易是否正确表单   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;txHex | string | 交易序列化16进制字符串 |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述   |
| ----- |:------:| ------ |
| value | string | 交易hash |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

3.3 广播交易
========
Cmd: /api/accountledger/transaction/broadcast
---------------------------------------------
_**详细描述: 广播离线组装的交易,成功返回true,失败返回错误提示信息**_
### HttpMethod: POST

### Form json data: 
```json
{
  "txHex" : null
}
```

参数列表
----
| 参数名                                                   |  参数类型  | 参数描述         | 是否必填 |
| ----------------------------------------------------- |:------:| ------------ |:----:|
| 广播交易                                                  | txform | 广播交易表单       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;txHex | string | 交易序列化16进制字符串 |  是   |

返回值
---
| 字段名   |  字段类型   | 参数描述   |
| ----- |:-------:| ------ |
| value | boolean | 是否成功   |
| hash  | string  | 交易hash |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

3.4 单笔转账
========
Cmd: /api/accountledger/transfer
--------------------------------
_**详细描述: 发起单账户单资产的转账交易**_
### HttpMethod: POST

### Form json data: 
```json
{
  "address" : null,
  "toAddress" : null,
  "password" : null,
  "amount" : null,
  "remark" : null
}
```

参数列表
----
| 参数名                                                       |     参数类型     | 参数描述   | 是否必填 |
| --------------------------------------------------------- |:------------:| ------ |:----:|
| 单笔转账                                                      | transferform | 单笔转账表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address   |    string    | 账户地址   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;toAddress |    string    | 账户地址   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password  |    string    | 账户密码   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount    |  biginteger  | 金额     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;remark    |    string    | 备注     |  否   |

返回值
---
| 字段名   |  字段类型  | 参数描述   |
| ----- |:------:| ------ |
| value | string | 交易hash |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

3.5 离线组装转账交易
============
Cmd: /api/accountledger/createTransferTxOffline
-----------------------------------------------
_**详细描述: 根据inputs和outputs离线组装转账交易，用于单账户或多账户的转账交易。交易手续费为inputs里本链主资产金额总和，减去outputs里本链主资产总和**_
### HttpMethod: POST

### Form json data: 
```json
{
  "inputs" : [ {
    "address" : null,
    "assetChainId" : 0,
    "assetId" : 0,
    "amount" : null,
    "nonce" : null
  } ],
  "outputs" : [ {
    "address" : null,
    "assetChainId" : 0,
    "assetId" : 0,
    "amount" : null,
    "lockTime" : 0
  } ],
  "remark" : null
}
```

参数列表
----
| 参数名                                                                                                          |      参数类型       | 参数描述     | 是否必填 |
| ------------------------------------------------------------------------------------------------------------ |:---------------:| -------- |:----:|
| transferDto                                                                                                  |   transferdto   | 转账交易表单   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;inputs                                                       | list&lt;object> | 转账交易输入列表 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address      |     string      | 账户地址     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetChainId |       int       | 资产的链id   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetId      |       int       | 资产id     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount       |   biginteger    | 资产金额     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;nonce        |     string      | 资产nonce值 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;outputs                                                      | list&lt;object> | 转账交易输出列表 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address      |     string      | 账户地址     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetChainId |       int       | 资产的链id   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetId      |       int       | 资产id     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount       |   biginteger    | 资产金额     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;lockTime     |      long       | 锁定时间     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;remark                                                       |     string      | 交易备注     |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述         |
| ----- |:------:| ------------ |
| hash  | string | 交易hash       |
| txHex | string | 交易序列化16进制字符串 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

3.6 计算离线创建转账交易所需手续费
===================
Cmd: /api/accountledger/calcTransferTxFee
-----------------------------------------
_**详细描述: 计算离线创建转账交易所需手续费**_
### HttpMethod: POST

### Form json data: 
```json
{
  "addressCount" : 0,
  "fromLength" : 0,
  "toLength" : 0,
  "remark" : null,
  "price" : null
}
```

参数列表
----
| 参数名                                                          |       参数类型       | 参数描述    | 是否必填 |
| ------------------------------------------------------------ |:----------------:| ------- |:----:|
| TransferTxFeeDto                                             | transfertxfeedto | 转账交易手续费 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;addressCount |       int        | 转账地址数量  |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;fromLength   |       int        | 转账输入长度  |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;toLength     |       int        | 转账输出长度  |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;remark       |      string      | 交易备注    |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;price        |    biginteger    | 手续费单价   |  否   |

返回值
---
| 字段名   |  字段类型  | 参数描述  |
| ----- |:------:| ----- |
| value | string | 交易手续费 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

4.1 发布合约
========
Cmd: /api/contract/create
-------------------------
_**详细描述: 发布合约**_
### HttpMethod: POST

### Form json data: 
```json
{
  "sender" : null,
  "gasLimit" : 0,
  "price" : 0,
  "password" : null,
  "remark" : null,
  "contractCode" : null,
  "alias" : null,
  "args" : null
}
```

参数列表
----
| 参数名                                                          |      参数类型      | 参数描述                 | 是否必填 |
| ------------------------------------------------------------ |:--------------:| -------------------- |:----:|
| 发布合约                                                         | contractcreate | 发布合约表单               |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sender       |     string     | 交易创建者                |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;gasLimit     |      long      | 最大gas消耗              |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;price        |      long      | 执行合约单价               |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password     |     string     | 交易创建者账户密码            |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;remark       |     string     | 备注                   |  否   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;contractCode |     string     | 智能合约代码(字节码的Hex编码字符串) |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;alias        |     string     | 合约别名                 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;args         |    object[]    | 参数列表                 |  否   |

返回值
---
| 字段名             |  字段类型  | 参数描述        |
| --------------- |:------:| ----------- |
| txHash          | string | 发布合约的交易hash |
| contractAddress | string | 生成的合约地址     |
### Example request data: 

_**request path:**_


_**request form data:**_
```json
{ }
```

### Example response data: 
```json
{
  "success" : true,
  "data" : {
    "txHash" : "0e770f75b0daca9b5ad2132cbc81d12b622cb3827778088ed32f2485bcda651d",
    "contractAddress" : "tNULSeBaN77MT2aLDi45CYP6mbSbnEgBa7Mdpo"
  }
}
```

4.2 调用合约
========
Cmd: /api/contract/call
-----------------------
_**详细描述: 调用合约**_
### HttpMethod: POST

### Form json data: 
```json
{
  "sender" : null,
  "gasLimit" : 0,
  "price" : 0,
  "password" : null,
  "remark" : null,
  "contractAddress" : null,
  "value" : null,
  "methodName" : null,
  "methodDesc" : null,
  "args" : null
}
```

参数列表
----
| 参数名                                                             |     参数类型     | 参数描述                       | 是否必填 |
| --------------------------------------------------------------- |:------------:| -------------------------- |:----:|
| 调用合约                                                            | contractcall | 调用合约表单                     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sender          |    string    | 交易创建者                      |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;gasLimit        |     long     | 最大gas消耗                    |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;price           |     long     | 执行合约单价                     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password        |    string    | 交易创建者账户密码                  |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;remark          |    string    | 备注                         |  否   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;contractAddress |    string    | 智能合约地址                     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value           |  biginteger  | 调用者向合约地址转入的主网资产金额，没有此业务时填0 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;methodName      |    string    | 方法名                        |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;methodDesc      |    string    | 方法描述，若合约内方法没有重载，则此参数可以为空   |  否   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;args            |   object[]   | 参数列表                       |  否   |

返回值
---
| 字段名    |  字段类型  | 参数描述        |
| ------ |:------:| ----------- |
| txHash | string | 调用合约的交易hash |
### Example request data: 

_**request path:**_


_**request form data:**_
```json
{ }
```

### Example response data: 
```json
{
  "success" : true,
  "data" : {
    "txHash" : "f0a5fc5d20c39355e35f1fe8011b1a28e7c65d8566ae8d76b297a22d1110851d"
  }
}
```

4.3 删除合约
========
Cmd: /api/contract/delete
-------------------------
_**详细描述: 删除合约**_
### HttpMethod: POST

### Form json data: 
```json
{
  "sender" : null,
  "contractAddress" : null,
  "password" : null,
  "remark" : null
}
```

参数列表
----
| 参数名                                                             |      参数类型      | 参数描述      | 是否必填 |
| --------------------------------------------------------------- |:--------------:| --------- |:----:|
| 删除合约                                                            | contractdelete | 删除合约表单    |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sender          |     string     | 交易创建者     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;contractAddress |     string     | 智能合约地址    |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password        |     string     | 交易创建者账户密码 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;remark          |     string     | 备注        |  否   |

返回值
---
| 字段名    |  字段类型  | 参数描述        |
| ------ |:------:| ----------- |
| txHash | string | 删除合约的交易hash |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

4.4 合约token转账
=============
Cmd: /api/contract/tokentransfer
--------------------------------
_**详细描述: 合约token转账**_
### HttpMethod: POST

### Form json data: 
```json
{
  "fromAddress" : null,
  "password" : null,
  "toAddress" : null,
  "contractAddress" : null,
  "amount" : null,
  "remark" : null
}
```

参数列表
----
| 参数名                                                             |         参数类型          | 参数描述         | 是否必填 |
| --------------------------------------------------------------- |:---------------------:| ------------ |:----:|
| token转账                                                         | contracttokentransfer | token转账表单    |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;fromAddress     |        string         | 转出者账户地址      |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password        |        string         | 转出者账户地址密码    |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;toAddress       |        string         | 转入者账户地址      |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;contractAddress |        string         | 合约地址         |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount          |      biginteger       | 转出的token资产金额 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;remark          |        string         | 备注           |  否   |

返回值
---
| 字段名    |  字段类型  | 参数描述   |
| ------ |:------:| ------ |
| txHash | string | 交易hash |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

4.5 从账户地址向合约地址转账(主链资产)的合约交易
===========================
Cmd: /api/contract/transfer2contract
------------------------------------
_**详细描述: 从账户地址向合约地址转账(主链资产)的合约交易**_
### HttpMethod: POST

### Form json data: 
```json
{
  "fromAddress" : null,
  "password" : null,
  "toAddress" : null,
  "amount" : null,
  "remark" : null
}
```

参数列表
----
| 参数名                                                         |       参数类型       | 参数描述      | 是否必填 |
| ----------------------------------------------------------- |:----------------:| --------- |:----:|
| 向合约地址转账                                                     | contracttransfer | 向合约地址转账表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;fromAddress |      string      | 转出者账户地址   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password    |      string      | 转出者账户地址密码 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;toAddress   |      string      | 转入的合约地址   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount      |    biginteger    | 转出的主链资产金额 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;remark      |      string      | 备注        |  否   |

返回值
---
| 字段名    |  字段类型  | 参数描述   |
| ------ |:------:| ------ |
| txHash | string | 交易hash |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

4.6 获取账户地址的指定合约的token余额
=======================
Cmd: /api/contract/balance/token/{contractAddress}/{address}
------------------------------------------------------------
_**详细描述: 获取账户地址的指定合约的token余额**_
### HttpMethod: GET

参数列表
----
| 参数名             |  参数类型  | 参数描述 | 是否必填 |
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
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

4.7 获取智能合约详细信息
==============
Cmd: /api/contract/info/{address}
---------------------------------
_**详细描述: 获取智能合约详细信息**_
### HttpMethod: GET

参数列表
----
| 参数名     |  参数类型  | 参数描述 | 是否必填 |
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
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

4.8 获取智能合约执行结果
==============
Cmd: /api/contract/result/{hash}
--------------------------------
_**详细描述: 获取智能合约执行结果**_
### HttpMethod: GET

参数列表
----
| 参数名  |  参数类型  | 参数描述   | 是否必填 |
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
### Example request data: 

_**request path:**_
http://localhost:9898/api/contract/result/f0a5fc5d20c39355e35f1fe8011b1a28e7c65d8566ae8d76b297a22d1110851d

_**request form data:**_
```json
{ }
```

### Example response data: 
```json
{
  "success" : true,
  "data" : {
    "flag" : true,
    "data" : {
      "success" : true,
      "errorMessage" : null,
      "contractAddress" : "tNULSeBaN77MT2aLDi45CYP6mbSbnEgBa7Mdpo",
      "result" : "true",
      "gasLimit" : 20000,
      "gasUsed" : 9232,
      "price" : 25,
      "totalFee" : "600000",
      "txSizeFee" : "100000",
      "actualContractFee" : "230800",
      "refundFee" : "269200",
      "value" : "0",
      "stackTrace" : null,
      "transfers" : [ ],
      "events" : [ "{\"contractAddress\":\"tNULSeBaN77MT2aLDi45CYP6mbSbnEgBa7Mdpo\",\"blockNumber\":39,\"event\":\"TransferEvent\",\"payload\":{\"from\":\"tNULSeBaMvEtDfvZuukDf2mVyfGo3DdiN8KLRG\",\"to\":\"tNULSeBaMnrs6JKrCy6TQdzYJZkMZJDng7QAsD\",\"value\":\"990\"}}" ],
      "tokenTransfers" : [ {
        "contractAddress" : "tNULSeBaN77MT2aLDi45CYP6mbSbnEgBa7Mdpo",
        "from" : "tNULSeBaMvEtDfvZuukDf2mVyfGo3DdiN8KLRG",
        "to" : "tNULSeBaMnrs6JKrCy6TQdzYJZkMZJDng7QAsD",
        "value" : "990",
        "name" : "io",
        "symbol" : "IO",
        "decimals" : 1
      } ],
      "invokeRegisterCmds" : [ ],
      "contractTxList" : [ ],
      "remark" : "call"
    }
  }
}
```

4.9 获取合约代码构造函数
==============
Cmd: /api/contract/constructor
------------------------------
_**详细描述: 获取合约代码构造函数**_
### HttpMethod: POST

### Form json data: 
```json
{
  "contractCode" : null
}
```

参数列表
----
| 参数名                                                          |     参数类型     | 参数描述                 | 是否必填 |
| ------------------------------------------------------------ |:------------:| -------------------- |:----:|
| 获取合约代码构造函数                                                   | contractcode | 获取合约代码构造函数表单         |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;contractCode |    string    | 智能合约代码(字节码的Hex编码字符串) |  是   |

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
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

4.10 获取已发布合约指定函数的信息
===================
Cmd: /api/contract/method
-------------------------
_**详细描述: 获取已发布合约指定函数的信息**_
### HttpMethod: POST

### Form json data: 
```json
{
  "contractAddress" : null,
  "methodName" : null,
  "methodDesc" : null
}
```

参数列表
----
| 参数名                                                             |        参数类型        | 参数描述                     | 是否必填 |
| --------------------------------------------------------------- |:------------------:| ------------------------ |:----:|
| 获取已发布合约指定函数的信息                                                  | contractmethodform | 获取已发布合约指定函数的信息表单         |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;contractAddress |       string       | 智能合约地址                   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;methodName      |       string       | 方法名                      |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;methodDesc      |       string       | 方法描述，若合约内方法没有重载，则此参数可以为空 |  否   |

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
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

4.11 获取已发布合约指定函数的参数类型列表
=======================
Cmd: /api/contract/method/argstypes
-----------------------------------
_**详细描述: 获取已发布合约指定函数的参数类型列表**_
### HttpMethod: POST

### Form json data: 
```json
{
  "contractAddress" : null,
  "methodName" : null,
  "methodDesc" : null
}
```

参数列表
----
| 参数名                                                             |        参数类型        | 参数描述                     | 是否必填 |
| --------------------------------------------------------------- |:------------------:| ------------------------ |:----:|
| 获取已发布合约指定函数的参数类型列表                                              | contractmethodform | 获取已发布合约指定函数的参数类型表单       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;contractAddress |       string       | 智能合约地址                   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;methodName      |       string       | 方法名                      |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;methodDesc      |       string       | 方法描述，若合约内方法没有重载，则此参数可以为空 |  否   |

返回值
---
| 字段名 |      字段类型       | 参数描述 |
| --- |:---------------:| ---- |
| 返回值 | list&lt;string> |      |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

4.12 验证发布合约
===========
Cmd: /api/contract/validate/create
----------------------------------
_**详细描述: 验证发布合约**_
### HttpMethod: POST

### Form json data: 
```json
{
  "sender" : null,
  "gasLimit" : 0,
  "price" : 0,
  "contractCode" : null,
  "args" : null
}
```

参数列表
----
| 参数名                                                          |          参数类型          | 参数描述                 | 是否必填 |
| ------------------------------------------------------------ |:----------------------:| -------------------- |:----:|
| 验证发布合约                                                       | contractvalidatecreate | 验证发布合约表单             |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sender       |         string         | 交易创建者                |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;gasLimit     |          long          | 最大gas消耗              |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;price        |          long          | 执行合约单价               |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;contractCode |         string         | 智能合约代码(字节码的Hex编码字符串) |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;args         |        object[]        | 参数列表                 |  否   |

返回值
---
| 字段名     |  字段类型   | 参数描述      |
| ------- |:-------:| --------- |
| success | boolean | 验证成功与否    |
| code    | string  | 验证失败的错误码  |
| msg     | string  | 验证失败的错误信息 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

4.13 验证调用合约
===========
Cmd: /api/contract/validate/call
--------------------------------
_**详细描述: 验证调用合约**_
### HttpMethod: POST

### Form json data: 
```json
{
  "sender" : null,
  "value" : 0,
  "gasLimit" : 0,
  "price" : 0,
  "contractAddress" : null,
  "methodName" : null,
  "methodDesc" : null,
  "args" : null
}
```

参数列表
----
| 参数名                                                             |         参数类型         | 参数描述                       | 是否必填 |
| --------------------------------------------------------------- |:--------------------:| -------------------------- |:----:|
| 验证调用合约                                                          | contractvalidatecall | 验证调用合约表单                   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sender          |        string        | 交易创建者                      |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value           |         long         | 调用者向合约地址转入的主网资产金额，没有此业务时填0 |  否   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;gasLimit        |         long         | 最大gas消耗                    |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;price           |         long         | 执行合约单价                     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;contractAddress |        string        | 智能合约地址                     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;methodName      |        string        | 方法名称                       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;methodDesc      |        string        | 方法描述，若合约内方法没有重载，则此参数可以为空   |  否   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;args            |       object[]       | 参数列表                       |  否   |

返回值
---
| 字段名     |  字段类型   | 参数描述      |
| ------- |:-------:| --------- |
| success | boolean | 验证成功与否    |
| code    | string  | 验证失败的错误码  |
| msg     | string  | 验证失败的错误信息 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

4.14 验证删除合约
===========
Cmd: /api/contract/validate/delete
----------------------------------
_**详细描述: 验证删除合约**_
### HttpMethod: POST

### Form json data: 
```json
{
  "sender" : null,
  "contractAddress" : null
}
```

参数列表
----
| 参数名                                                             |          参数类型          | 参数描述     | 是否必填 |
| --------------------------------------------------------------- |:----------------------:| -------- |:----:|
| 验证删除合约                                                          | contractvalidatedelete | 验证删除合约表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sender          |         string         | 交易创建者    |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;contractAddress |         string         | 智能合约地址   |  是   |

返回值
---
| 字段名     |  字段类型   | 参数描述      |
| ------- |:-------:| --------- |
| success | boolean | 验证成功与否    |
| code    | string  | 验证失败的错误码  |
| msg     | string  | 验证失败的错误信息 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

4.15 估算发布合约交易的GAS
=================
Cmd: /api/contract/imputedgas/create
------------------------------------
_**详细描述: 估算发布合约交易的GAS**_
### HttpMethod: POST

### Form json data: 
```json
{
  "sender" : null,
  "contractCode" : null,
  "args" : null
}
```

参数列表
----
| 参数名                                                          |           参数类型           | 参数描述                 | 是否必填 |
| ------------------------------------------------------------ |:------------------------:| -------------------- |:----:|
| 估算发布合约交易的GAS                                                 | imputedgascontractcreate | 估算发布合约交易的GAS表单       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sender       |          string          | 交易创建者                |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;contractCode |          string          | 智能合约代码(字节码的Hex编码字符串) |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;args         |         object[]         | 参数列表                 |  否   |

返回值
---
| 字段名      | 字段类型 | 参数描述              |
| -------- |:----:| ----------------- |
| gasLimit | long | 消耗的gas值，执行失败返回数值1 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

4.16 估算调用合约交易的GAS
=================
Cmd: /api/contract/imputedgas/call
----------------------------------
_**详细描述: 估算调用合约交易的GAS**_
### HttpMethod: POST

### Form json data: 
```json
{
  "sender" : null,
  "value" : null,
  "contractAddress" : null,
  "methodName" : null,
  "methodDesc" : null,
  "args" : null
}
```

参数列表
----
| 参数名                                                             |          参数类型          | 参数描述                       | 是否必填 |
| --------------------------------------------------------------- |:----------------------:| -------------------------- |:----:|
| 估算调用合约交易的GAS                                                    | imputedgascontractcall | 估算调用合约交易的GAS表单             |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sender          |         string         | 交易创建者                      |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value           |       biginteger       | 调用者向合约地址转入的主网资产金额，没有此业务时填0 |  否   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;contractAddress |         string         | 智能合约地址                     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;methodName      |         string         | 方法名称                       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;methodDesc      |         string         | 方法描述，若合约内方法没有重载，则此参数可以为空   |  否   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;args            |        object[]        | 参数列表                       |  否   |

返回值
---
| 字段名      | 字段类型 | 参数描述              |
| -------- |:----:| ----------------- |
| gasLimit | long | 消耗的gas值，执行失败返回数值1 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

4.17 调用合约不上链方法
==============
Cmd: /api/contract/view
-----------------------
_**详细描述: 调用合约不上链方法**_
### HttpMethod: POST

### Form json data: 
```json
{
  "contractAddress" : null,
  "methodName" : null,
  "methodDesc" : null,
  "args" : null
}
```

参数列表
----
| 参数名                                                             |       参数类型       | 参数描述                     | 是否必填 |
| --------------------------------------------------------------- |:----------------:| ------------------------ |:----:|
| 调用合约不上链方法                                                       | contractviewcall | 调用合约不上链方法表单              |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;contractAddress |      string      | 智能合约地址                   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;methodName      |      string      | 方法名称                     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;methodDesc      |      string      | 方法描述，若合约内方法没有重载，则此参数可以为空 |  否   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;args            |     object[]     | 参数列表                     |  否   |

返回值
---
| 字段名    |  字段类型  | 参数描述      |
| ------ |:------:| --------- |
| result | string | 视图方法的调用结果 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

4.18 离线组装 - 发布合约的交易
===================
Cmd: /api/contract/create/offline
---------------------------------
_**详细描述: 离线组装 - 发布合约的交易**_
### HttpMethod: POST

### Form json data: 
```json
{
  "sender" : null,
  "alias" : null,
  "contractCode" : null,
  "args" : null,
  "remark" : null
}
```

参数列表
----
| 参数名                                                          |         参数类型          | 参数描述                 | 是否必填 |
| ------------------------------------------------------------ |:---------------------:| -------------------- |:----:|
| 发布合约离线交易                                                     | contractcreateoffline | 发布合约离线交易表单           |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sender       |        string         | 交易创建者                |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;alias        |        string         | 合约别名                 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;contractCode |        string         | 智能合约代码(字节码的Hex编码字符串) |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;args         |       object[]        | 参数列表                 |  否   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;remark       |        string         | 备注                   |  否   |

返回值
---
| 字段名             |  字段类型  | 参数描述     |
| --------------- |:------:| -------- |
| hash            | string | 交易hash   |
| txHex           | string | 交易序列化字符串 |
| contractAddress | string | 生成的合约地址  |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

4.19 离线组装 - 调用合约的交易
===================
Cmd: /api/contract/call/offline
-------------------------------
_**详细描述: 离线组装 - 调用合约的交易**_
### HttpMethod: POST

### Form json data: 
```json
{
  "sender" : null,
  "contractAddress" : null,
  "value" : null,
  "methodName" : null,
  "methodDesc" : null,
  "args" : null,
  "remark" : null
}
```

参数列表
----
| 参数名                                                             |        参数类型         | 参数描述                       | 是否必填 |
| --------------------------------------------------------------- |:-------------------:| -------------------------- |:----:|
| 调用合约离线交易                                                        | contractcalloffline | 调用合约离线交易表单                 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sender          |       string        | 交易创建者                      |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;contractAddress |       string        | 智能合约地址                     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value           |     biginteger      | 调用者向合约地址转入的主网资产金额，没有此业务时填0 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;methodName      |       string        | 方法名                        |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;methodDesc      |       string        | 方法描述，若合约内方法没有重载，则此参数可以为空   |  否   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;args            |      object[]       | 参数列表                       |  否   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;remark          |       string        | 备注                         |  否   |

返回值
---
| 字段名   |  字段类型  | 参数描述     |
| ----- |:------:| -------- |
| hash  | string | 交易hash   |
| txHex | string | 交易序列化字符串 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

4.20 离线组装 - 删除合约交易
==================
Cmd: /api/contract/delete/offline
---------------------------------
_**详细描述: 离线组装 - 删除合约交易**_
### HttpMethod: POST

### Form json data: 
```json
{
  "sender" : null,
  "contractAddress" : null,
  "remark" : null
}
```

参数列表
----
| 参数名                                                             |         参数类型          | 参数描述       | 是否必填 |
| --------------------------------------------------------------- |:---------------------:| ---------- |:----:|
| 删除合约离线交易                                                        | contractdeleteoffline | 删除合约离线交易表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sender          |        string         | 交易创建者      |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;contractAddress |        string         | 智能合约地址     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;remark          |        string         | 备注         |  否   |

返回值
---
| 字段名   |  字段类型  | 参数描述     |
| ----- |:------:| -------- |
| hash  | string | 交易hash   |
| txHex | string | 交易序列化字符串 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

4.21 离线组装 - 合约token转账交易
=======================
Cmd: /api/contract/tokentransfer/offline
----------------------------------------
_**详细描述: 离线组装 - 合约token转账交易**_
### HttpMethod: POST

### Form json data: 
```json
{
  "fromAddress" : null,
  "toAddress" : null,
  "contractAddress" : null,
  "amount" : null,
  "remark" : null
}
```

参数列表
----
| 参数名                                                             |             参数类型             | 参数描述          | 是否必填 |
| --------------------------------------------------------------- |:----------------------------:| ------------- |:----:|
| token转账离线交易                                                     | contracttokentransferoffline | token转账离线交易表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;fromAddress     |            string            | 转出者账户地址       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;toAddress       |            string            | 转入者账户地址       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;contractAddress |            string            | 合约地址          |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount          |          biginteger          | 转出的token资产金额  |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;remark          |            string            | 备注            |  否   |

返回值
---
| 字段名   |  字段类型  | 参数描述     |
| ----- |:------:| -------- |
| hash  | string | 交易hash   |
| txHex | string | 交易序列化字符串 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

4.22 离线组装 - 从账户地址向合约地址转账(主链资产)的合约交易
===================================
Cmd: /api/contract/transfer2contract/offline
--------------------------------------------
_**详细描述: 离线组装 - 从账户地址向合约地址转账(主链资产)的合约交易**_
### HttpMethod: POST

### Form json data: 
```json
{
  "fromAddress" : null,
  "toAddress" : null,
  "amount" : null,
  "remark" : null
}
```

参数列表
----
| 参数名                                                         |          参数类型           | 参数描述          | 是否必填 |
| ----------------------------------------------------------- |:-----------------------:| ------------- |:----:|
| 向合约地址转账离线交易                                                 | contracttransferoffline | 向合约地址转账离线交易表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;fromAddress |         string          | 账户地址          |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;toAddress   |         string          | 转入的合约地址       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount      |       biginteger        | 转出的主链资产金额     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;remark      |         string          | 备注            |  否   |

返回值
---
| 字段名   |  字段类型  | 参数描述     |
| ----- |:------:| -------- |
| hash  | string | 交易hash   |
| txHex | string | 交易序列化字符串 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

5.1 创建共识节点
==========
Cmd: /api/consensus/agent
-------------------------
_**详细描述: 创建共识节点**_
### HttpMethod: POST

### Form json data: 
```json
{
  "agentAddress" : null,
  "packingAddress" : null,
  "rewardAddress" : null,
  "commissionRate" : 0,
  "deposit" : null,
  "password" : null
}
```

参数列表
----
| 参数名                                                            |      参数类型       | 参数描述        | 是否必填 |
| -------------------------------------------------------------- |:---------------:| ----------- |:----:|
| 创建共识节点                                                         | createagentform | 创建共识节点表单    |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;agentAddress   |     string      | 节点地址        |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;packingAddress |     string      | 节点出块地址      |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;rewardAddress  |     string      | 奖励地址，默认节点地址 |  否   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;commissionRate |       int       | 佣金比例        |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;deposit        |     string      | 抵押金额        |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password       |     string      | 密码          |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述   |
| ----- |:------:| ------ |
| value | string | 交易hash |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

5.2 注销共识节点
==========
Cmd: /api/consensus/agent/stop
------------------------------
_**详细描述: 注销共识节点**_
### HttpMethod: POST

### Form json data: 
```json
{
  "address" : null,
  "password" : null
}
```

参数列表
----
| 参数名                                                      |     参数类型      | 参数描述     | 是否必填 |
| -------------------------------------------------------- |:-------------:| -------- |:----:|
| 注销共识节点                                                   | stopagentform | 注销共识节点表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address  |    string     | 共识节点地址   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password |    string     | 密码       |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述   |
| ----- |:------:| ------ |
| value | string | 交易hash |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

5.3 委托参与共识
==========
Cmd: /api/consensus/deposit
---------------------------
_**详细描述: 委托参与共识**_
### HttpMethod: POST

### Form json data: 
```json
{
  "address" : null,
  "agentHash" : null,
  "deposit" : null,
  "password" : null
}
```

参数列表
----
| 参数名                                                       |    参数类型     | 参数描述     | 是否必填 |
| --------------------------------------------------------- |:-----------:| -------- |:----:|
| 委托参与共识                                                    | depositform | 委托参与共识表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address   |   string    | 参与共识账户地址 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;agentHash |   string    | 共识节点hash |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;deposit   |   string    | 参与共识的金额  |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password  |   string    | 密码       |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述   |
| ----- |:------:| ------ |
| value | string | 交易hash |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

5.4 退出共识
========
Cmd: /api/consensus/withdraw
----------------------------
_**详细描述: 退出共识**_
### HttpMethod: POST

### Form json data: 
```json
{
  "address" : null,
  "txHash" : null,
  "password" : null
}
```

参数列表
----
| 参数名                                                      |     参数类型     | 参数描述         | 是否必填 |
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
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

5.5 查询节点的委托共识列表
===============
Cmd: /api/consensus/list/deposit/{agentHash}
--------------------------------------------
_**详细描述: 查询节点的委托共识列表**_
### HttpMethod: GET

参数列表
----
| 参数名       |  参数类型  | 参数描述          | 是否必填 |
| --------- |:------:| ------------- |:----:|
| agentHash | string | 创建共识节点的交易hash |  是   |

返回值
---
| 字段名         |  字段类型  | 参数描述      |
| ----------- |:------:| --------- |
| deposit     | string | 委托金额      |
| agentHash   | string | 节点hash    |
| address     | string | 账户地址      |
| time        |  long  | 委托时间      |
| txHash      | string | 委托交易hash  |
| blockHeight |  long  | 委托时的区块高度  |
| delHeight   |  long  | 退出委托的区块高度 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

5.6 离线组装 - 创建共识节点交易
===================
Cmd: /api/consensus/agent/offline
---------------------------------
_**详细描述: 参与共识所需资产可通过查询链信息接口获取(agentChainId和agentAssetId)**_
### HttpMethod: POST

### Form json data: 
```json
{
  "agentAddress" : null,
  "packingAddress" : null,
  "rewardAddress" : null,
  "commissionRate" : 0,
  "deposit" : null,
  "input" : {
    "address" : null,
    "assetChainId" : 0,
    "assetId" : 0,
    "amount" : null,
    "nonce" : null
  }
}
```

参数列表
----
| 参数名                                                                                                          |     参数类型     | 参数描述       | 是否必填 |
| ------------------------------------------------------------------------------------------------------------ |:------------:| ---------- |:----:|
| 离线创建共识节点                                                                                                     | consensusdto | 离线创建共识节点表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;agentAddress                                                 |    string    | 节点创建地址     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;packingAddress                                               |    string    | 节点出块地址     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;rewardAddress                                                |    string    | 获取共识奖励地址   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;commissionRate                                               |     int      | 节点佣金比例     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;deposit                                                      |  biginteger  | 创建节点保证金    |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;input                                                        |    object    | 交易输入信息     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address      |    string    | 账户地址       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetChainId |     int      | 资产的链id     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetId      |     int      | 资产id       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount       |  biginteger  | 资产金额       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;nonce        |    string    | 资产nonce值   |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述     |
| ----- |:------:| -------- |
| hash  | string | 交易hash   |
| txHex | string | 交易序列化字符串 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

5.7 离线组装 - 注销共识节点交易
===================
Cmd: /api/consensus/agent/stop/offline
--------------------------------------
_**详细描述: 组装交易的StopDepositDto信息，可通过查询节点的委托共识列表获取，input的nonce值可为空**_
### HttpMethod: POST

### Form json data: 
```json
{
  "agentHash" : null,
  "agentAddress" : null,
  "deposit" : null,
  "price" : null,
  "depositList" : [ {
    "depositHash" : null,
    "input" : {
      "address" : null,
      "assetChainId" : 0,
      "assetId" : 0,
      "amount" : null,
      "nonce" : null
    }
  } ]
}
```

参数列表
----
| 参数名                                                                                                                                                          |       参数类型       | 参数描述        | 是否必填 |
| ------------------------------------------------------------------------------------------------------------------------------------------------------------ |:----------------:| ----------- |:----:|
| 离线注销共识节点                                                                                                                                                     | stopconsensusdto | 离线注销共识节点表单  |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;agentHash                                                                                                    |      string      | 创建节点的交易hash |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;agentAddress                                                                                                 |      string      | 节点地址        |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;deposit                                                                                                      |    biginteger    | 创建节点的保证金    |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;price                                                                                                        |    biginteger    | 手续费单价       |  否   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;depositList                                                                                                  | list&lt;object>  | 停止委托列表      |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;depositHash                                                  |      string      | 委托共识的交易hash |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;input                                                        |      object      | 交易输入信息      |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address      |      string      | 账户地址        |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetChainId |       int        | 资产的链id      |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetId      |       int        | 资产id        |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount       |    biginteger    | 资产金额        |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;nonce        |      string      | 资产nonce值    |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述     |
| ----- |:------:| -------- |
| hash  | string | 交易hash   |
| txHex | string | 交易序列化字符串 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

5.8 离线组装 - 委托参与共识交易
===================
Cmd: /api/consensus/deposit/offline
-----------------------------------
_**详细描述: 参与共识所需资产可通过查询链信息接口获取(agentChainId和agentAssetId)**_
### HttpMethod: POST

### Form json data: 
```json
{
  "address" : null,
  "deposit" : null,
  "agentHash" : null,
  "input" : {
    "address" : null,
    "assetChainId" : 0,
    "assetId" : 0,
    "amount" : null,
    "nonce" : null
  }
}
```

参数列表
----
| 参数名                                                                                                          |    参数类型    | 参数描述       | 是否必填 |
| ------------------------------------------------------------------------------------------------------------ |:----------:| ---------- |:----:|
| 离线委托参与共识                                                                                                     | depositdto | 离线委托参与共识表单 |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address                                                      |   string   | 账户地址       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;deposit                                                      | biginteger | 委托金额       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;agentHash                                                    |   string   | 共识节点hash   |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;input                                                        |   object   | 交易输入信息     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address      |   string   | 账户地址       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetChainId |    int     | 资产的链id     |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetId      |    int     | 资产id       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount       | biginteger | 资产金额       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;nonce        |   string   | 资产nonce值   |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述     |
| ----- |:------:| -------- |
| hash  | string | 交易hash   |
| txHex | string | 交易序列化字符串 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

5.9 离线组装 - 退出共识交易
=================
Cmd: /api/consensus/withdraw/offline
------------------------------------
_**详细描述: 接口的input数据，则是委托共识交易的output数据，nonce值可为空**_
### HttpMethod: POST

### Form json data: 
```json
{
  "address" : null,
  "depositHash" : null,
  "price" : null,
  "input" : {
    "address" : null,
    "assetChainId" : 0,
    "assetId" : 0,
    "amount" : null,
    "nonce" : null
  }
}
```

参数列表
----
| 参数名                                                                                                          |    参数类型     | 参数描述        | 是否必填 |
| ------------------------------------------------------------------------------------------------------------ |:-----------:| ----------- |:----:|
| 离线退出共识                                                                                                       | withdrawdto | 离线退出共识表单    |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address                                                      |   string    | 地址          |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;depositHash                                                  |   string    | 委托共识交易的hash |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;price                                                        | biginteger  | 手续费单价       |  否   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;input                                                        |   object    | 交易输入信息      |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address      |   string    | 账户地址        |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetChainId |     int     | 资产的链id      |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assetId      |     int     | 资产id        |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;amount       | biginteger  | 资产金额        |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;nonce        |   string    | 资产nonce值    |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述     |
| ----- |:------:| -------- |
| hash  | string | 交易hash   |
| txHex | string | 交易序列化字符串 |
### Example request data: 

_**request path:**_
略

_**request form data:**_
略

### Example response data: 
略

