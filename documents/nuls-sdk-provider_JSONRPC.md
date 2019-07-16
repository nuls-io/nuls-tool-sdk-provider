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



0.1 获取本链相关信息,其中共识资产为本链创建共识节点交易和创建委托共识交易时，需要用到的资产
================================================
Cmd: info
---------
_**详细描述: 获取本链相关信息,其中共识资产为本链创建共识节点交易和创建委托共识交易时，需要用到的资产**_

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
```json
{
  "jsonrpc" : "2.0",
  "method" : "info",
  "params" : [ ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : {
    "agentChainId" : 2,
    "inflationAmount" : 500000000000000,
    "agentAssetId" : 1,
    "chainId" : 2,
    "assetId" : 1
  }
}
```

1.1 批量创建账户
==========
Cmd: createAccount
------------------
_**详细描述: 创建的账户存在于本地钱包内**_

参数列表
----
| 参数名      |  参数类型  | 参数描述 | 是否必填 |
| -------- |:------:| ---- |:----:|
| chainId  |  int   | 链ID  |  是   |
| count    |  int   | 创建数量 |  是   |
| password | string | 密码   |  是   |

返回值
---
| 字段名 |      字段类型       | 参数描述     |
| --- |:---------------:| -------- |
| 返回值 | list&lt;string> | 返回账户地址集合 |
### Example request data: 
```json
{
  "jsonrpc" : "2.0",
  "method" : "createAccount",
  "params" : [ 2, 1, "abcd1234" ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : [ "tNULSeBaMhcccH1KeXhMpH5y3pvtRzatAiuMJk" ]
}
```

1.2 修改账户密码
==========
Cmd: updatePassword
-------------------
_**详细描述: 修改账户密码**_

参数列表
----
| 参数名         |  参数类型  | 参数描述 | 是否必填 |
| ----------- |:------:| ---- |:----:|
| chainId     |  int   | 链ID  |  是   |
| address     | string | 账户地址 |  是   |
| oldPassword | string | 原密码  |  是   |
| newPassword | string | 新密码  |  是   |

返回值
---
| 字段名   |  字段类型   | 参数描述   |
| ----- |:-------:| ------ |
| value | boolean | 是否修改成功 |
### Example request data: 
```json
{
  "jsonrpc" : "2.0",
  "method" : "updatePassword",
  "params" : [ 2, "tNULSeBaMhcccH1KeXhMpH5y3pvtRzatAiuMJk", "abcd1234", "abcd1111" ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : true
}
```

1.3 导出账户私钥
==========
Cmd: getPriKey
--------------
_**详细描述: 只能导出本地钱包已存在账户的私钥**_

参数列表
----
| 参数名      |  参数类型  | 参数描述 | 是否必填 |
| -------- |:------:| ---- |:----:|
| chainId  |  int   | 链ID  |  是   |
| address  | string | 账户地址 |  是   |
| password | string | 密码   |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述 |
| ----- |:------:| ---- |
| value | string | 私钥   |
### Example request data: 
```json
{
  "jsonrpc" : "2.0",
  "method" : "getPriKey",
  "params" : [ 2, "tNULSeBaMhcccH1KeXhMpH5y3pvtRzatAiuMJk", "abcd1111" ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : "53b02c91605451ea35175df894b4c47b7d1effbd05d6b269b3e7c785f3f6dc18"
}
```

1.4 根据私钥导入账户
============
Cmd: importPriKey
-----------------
_**详细描述: 导入私钥时，需要输入密码给明文私钥加密**_

参数列表
----
| 参数名      |  参数类型  | 参数描述   | 是否必填 |
| -------- |:------:| ------ |:----:|
| chainId  |  int   | 链ID    |  是   |
| priKey   | string | 账户明文私钥 |  是   |
| password | string | 新密码    |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述 |
| ----- |:------:| ---- |
| value | string | 账户地址 |
### Example request data: 
```json
{
  "jsonrpc" : "2.0",
  "method" : "importPriKey",
  "params" : [ 2, "53b02c91605451ea35175df894b4c47b7d1effbd05d6b269b3e7c785f3f6dc18", "abcd1234" ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : "tNULSeBaMhcccH1KeXhMpH5y3pvtRzatAiuMJk"
}
```

1.5 根据keystore导入账户
==================
Cmd: importKeystore
-------------------
_**详细描述: 根据keystore导入账户**_

参数列表
----
| 参数名          |  参数类型  | 参数描述         | 是否必填 |
| ------------ |:------:| ------------ |:----:|
| chainId      |  int   | 链ID          |  是   |
| keyStoreJson |  map   | keyStoreJson |  是   |
| password     | string | keystore密码   |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述 |
| ----- |:------:| ---- |
| value | string | 账户地址 |
### Example request data: 
```json
{
  "jsonrpc" : "2.0",
  "method" : "importKeystore",
  "params" : [ 2, {
    "address" : "tNULSeBaMhcccH1KeXhMpH5y3pvtRzatAiuMJk",
    "encryptedPrivateKey" : "8dbe5c1da7228c0a8b6a26c328231b8df2d4dbfd3f9b029557708d4560de9ecd53a353bb2d688d7c68bd11d741e5d3ed",
    "pubKey" : "024477033a4521efee5f90caf30f8eb3284e8d1bb7fef2923ae21617b24aacc8cb",
    "prikey" : null
  }, "abcd1234" ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : "tNULSeBaMhcccH1KeXhMpH5y3pvtRzatAiuMJk"
}
```

1.6 账户备份，导出账户keystore信息
=======================
Cmd: exportKeystore
-------------------
_**详细描述: 账户备份，导出账户keystore信息**_

参数列表
----
| 参数名      |  参数类型  | 参数描述 | 是否必填 |
| -------- |:------:| ---- |:----:|
| chainId  |  int   | 链ID  |  是   |
| address  | string | 账户地址 |  是   |
| password | string | 账户密码 |  是   |

返回值
---
| 字段名    |  字段类型  | 参数描述     |
| ------ |:------:| -------- |
| result | string | keystore |
### Example request data: 
```json
{
  "jsonrpc" : "2.0",
  "method" : "exportKeystore",
  "params" : [ 2, "tNULSeBaMhcccH1KeXhMpH5y3pvtRzatAiuMJk", "abcd1234" ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : "{\"address\":\"tNULSeBaMhcccH1KeXhMpH5y3pvtRzatAiuMJk\",\"encryptedPrivateKey\":\"8dbe5c1da7228c0a8b6a26c328231b8df2d4dbfd3f9b029557708d4560de9ecd53a353bb2d688d7c68bd11d741e5d3ed\",\"pubKey\":\"024477033a4521efee5f90caf30f8eb3284e8d1bb7fef2923ae21617b24aacc8cb\",\"prikey\":null}"
}
```

1.7 查询账户余额
==========
Cmd: getAccountBalance
----------------------
_**详细描述: 根据资产链ID和资产ID，查询本链账户对应资产的余额与nonce值**_

参数列表
----
| 参数名          |  参数类型  | 参数描述   | 是否必填 |
| ------------ |:------:| ------ |:----:|
| chainId      |  int   | 链ID    |  是   |
| assetChainId |  int   | 资产的链ID |  是   |
| assetId      |  int   | 资产ID   |  是   |
| address      | string | 账户地址   |  是   |

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
```json
{
  "jsonrpc" : "2.0",
  "method" : "getAccountBalance",
  "params" : [ 2, 2, 1, "tNULSeBaMhcccH1KeXhMpH5y3pvtRzatAiuMJk" ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : {
    "totalBalance" : "0",
    "balance" : "0",
    "timeLock" : "0",
    "consensusLock" : "0",
    "freeze" : "0",
    "nonce" : "0000000000000000",
    "nonceType" : 1
  }
}
```

1.8 离线 - 批量创建账户
===============
Cmd: createAccountOffline
-------------------------
_**详细描述: 创建的账户不会保存到钱包中,接口直接返回账户的keystore信息**_

参数列表
----
| 参数名      |  参数类型  | 参数描述 | 是否必填 |
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
### Example request data: 
```json
{
  "jsonrpc" : "2.0",
  "method" : "createAccountOffline",
  "params" : [ 2, 1, "abcd1234" ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : [ {
    "address" : "tNULSeBaMnS7et6FdFMMMLyK8Wvy1daVVeohfu",
    "pubKey" : "02756e0d0827df60f5806bc00c44f97a9f5c234f78502a314aa40bb0a0156cd9f0",
    "prikey" : "",
    "encryptedPrivateKey" : "720e9f7ac1ab2ee997bad249d1c42212a5c5c744358a7bc65f472a1fe61a87a8f0bc841fdc74c8313fe6c94f496f3676"
  } ]
}
```

1.9 离线获取账户明文私钥
==============
Cmd: getPriKeyOffline
---------------------
_**详细描述: 离线获取账户明文私钥**_

参数列表
----
| 参数名                 |  参数类型  | 参数描述   | 是否必填 |
| ------------------- |:------:| ------ |:----:|
| chainId             |  int   | 链ID    |  是   |
| address             | string | 账户地址   |  是   |
| encryptedPrivateKey | string | 账户密文私钥 |  是   |
| password            | string | 密码     |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述 |
| ----- |:------:| ---- |
| value | string | 明文私钥 |
### Example request data: 
```json
{
  "jsonrpc" : "2.0",
  "method" : "getPriKeyOffline",
  "params" : [ 2, "tNULSeBaMhcccH1KeXhMpH5y3pvtRzatAiuMJk", "8dbe5c1da7228c0a8b6a26c328231b8df2d4dbfd3f9b029557708d4560de9ecd53a353bb2d688d7c68bd11d741e5d3ed", "abcd1234" ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : {
    "priKey" : "53b02c91605451ea35175df894b4c47b7d1effbd05d6b269b3e7c785f3f6dc18"
  }
}
```

1.10 离线修改账户密码
=============
Cmd: resetPasswordOffline
-------------------------
_**详细描述: 离线修改账户密码**_

参数列表
----
| 参数名                 |  参数类型  | 参数描述   | 是否必填 |
| ------------------- |:------:| ------ |:----:|
| chainId             |  int   | 链ID    |  是   |
| address             | string | 账户地址   |  是   |
| encryptedPrivateKey | string | 账户密文私钥 |  是   |
| oldPassword         | string | 原密码    |  是   |
| newPassword         | string | 新密码    |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述       |
| ----- |:------:| ---------- |
| value | string | 重置密码后的加密私钥 |
### Example request data: 
```json
{
  "jsonrpc" : "2.0",
  "method" : "resetPasswordOffline",
  "params" : [ 2, "tNULSeBaMhcccH1KeXhMpH5y3pvtRzatAiuMJk", "8dbe5c1da7228c0a8b6a26c328231b8df2d4dbfd3f9b029557708d4560de9ecd53a353bb2d688d7c68bd11d741e5d3ed", "abcd1234", "abcd1111" ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : {
    "newEncryptedPriKey" : "8dbe5c1da7228c0a8b6a26c328231b8df2d4dbfd3f9b029557708d4560de9ecd53a353bb2d688d7c68bd11d741e5d3ed"
  }
}
```

1.11 多账户摘要签名
============
Cmd: multiSign
--------------
_**详细描述: 用于签名离线组装的多账户转账交易,调用接口时，参数可以传地址和私钥，或者传地址和加密私钥和加密密码**_

参数列表
----
| 参数名                                                                 |  参数类型  | 参数描述         | 是否必填 |
| ------------------------------------------------------------------- |:------:| ------------ |:----:|
| chainId                                                             |  int   | 链ID          |  是   |
| signDtoList                                                         |  list  | 摘要签名表单       |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;address             | string | 地址           |  是   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;priKey              | string | 明文私钥         |  否   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;encryptedPrivateKey | string | 加密私钥         |  否   |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password            | string | 密码           |  否   |
| txHex                                                               | string | 交易序列化16进制字符串 |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述          |
| ----- |:------:| ------------- |
| hash  | string | 交易hash        |
| txHex | string | 签名后的交易16进制字符串 |
### Example request data: 
```json
{
  "jsonrpc" : "2.0",
  "method" : "multiSign",
  "params" : [ 2, [ {
    "address" : "tNULSeBaMhcccH1KeXhMpH5y3pvtRzatAiuMJk",
    "priKey" : "53b02c91605451ea35175df894b4c47b7d1effbd05d6b269b3e7c785f3f6dc18"
  }, {
    "address" : "tNULSABFehEc2HgKhXFMtH3yGHpSStBthiuMfd",
    "encryptedPrivateKey" : "8dbe5c1da7228c0a8b6a26c328231b8df2d4dbfd3f9b029557708d4560de9ecd53a353bb2d688d7c68bd11d741e5d3ed",
    "password" : "abcd1234"
  } ], "0200b67f2d5d0672656d61726b008c01170200012a9af4ee49f4cb1ee84eafd42aec41bc04b28f7b02000100402a8648170000000000000000000000000000000000000000000000000000000800000000000000000001170200012a9af4ee49f4cb1ee84eafd42aec41bc04b28f7b0200010000e8764817000000000000000000000000000000000000000000000000000000000000000000000000" ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : {
    "txHex" : "0200b67f2d5d0672656d61726b008c01170200012a9af4ee49f4cb1ee84eafd42aec41bc04b28f7b02000100402a8648170000000000000000000000000000000000000000000000000000000800000000000000000001170200012a9af4ee49f4cb1ee84eafd42aec41bc04b28f7b0200010000e876481700000000000000000000000000000000000000000000000000000000000000000000006a21024477033a4521efee5f90caf30f8eb3284e8d1bb7fef2923ae21617b24aacc8cb473045022100a8b3d10dfdf4fb0c7c6ede1f5d216a631689fbbd0e9beb46cac1918a5e64ccbc02202a654c3d9a27a99e8458ac18a8b9bc460f520bff10e4592102ad04e22890b412",
    "hash" : "748184df91eda8d09be76e075d553313434c56bfeec3d449abc99ba6c430c00c"
  }
}
```

1.12 明文私钥摘要签名
=============
Cmd: priKeySign
---------------
_**详细描述: 明文私钥摘要签名**_

参数列表
----
| 参数名        |  参数类型  | 参数描述         | 是否必填 |
| ---------- |:------:| ------------ |:----:|
| chainId    |  int   | 链ID          |  是   |
| txHex      | string | 交易序列化16进制字符串 |  是   |
| address    | string | 账户地址         |  是   |
| privateKey | string | 账户明文私钥       |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述          |
| ----- |:------:| ------------- |
| hash  | string | 交易hash        |
| txHex | string | 签名后的交易16进制字符串 |
### Example request data: 
```json
{
  "jsonrpc" : "2.0",
  "method" : "priKeySign",
  "params" : [ 2, "0200b67f2d5d0672656d61726b008c01170200012a9af4ee49f4cb1ee84eafd42aec41bc04b28f7b02000100402a8648170000000000000000000000000000000000000000000000000000000800000000000000000001170200012a9af4ee49f4cb1ee84eafd42aec41bc04b28f7b0200010000e8764817000000000000000000000000000000000000000000000000000000000000000000000000", "tNULSeBaMhcccH1KeXhMpH5y3pvtRzatAiuMJk", "53b02c91605451ea35175df894b4c47b7d1effbd05d6b269b3e7c785f3f6dc18" ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : {
    "txHex" : "0200b67f2d5d0672656d61726b008c01170200012a9af4ee49f4cb1ee84eafd42aec41bc04b28f7b02000100402a8648170000000000000000000000000000000000000000000000000000000800000000000000000001170200012a9af4ee49f4cb1ee84eafd42aec41bc04b28f7b0200010000e876481700000000000000000000000000000000000000000000000000000000000000000000006a21024477033a4521efee5f90caf30f8eb3284e8d1bb7fef2923ae21617b24aacc8cb473045022100a8b3d10dfdf4fb0c7c6ede1f5d216a631689fbbd0e9beb46cac1918a5e64ccbc02202a654c3d9a27a99e8458ac18a8b9bc460f520bff10e4592102ad04e22890b412",
    "hash" : "748184df91eda8d09be76e075d553313434c56bfeec3d449abc99ba6c430c00c"
  }
}
```

1.13 密文私钥摘要签名
=============
Cmd: encryptedPriKeySign
------------------------
_**详细描述: 密文私钥摘要签名**_

参数列表
----
| 参数名                 |  参数类型  | 参数描述         | 是否必填 |
| ------------------- |:------:| ------------ |:----:|
| chainId             |  int   | 链ID          |  是   |
| txHex               | string | 交易序列化16进制字符串 |  是   |
| address             | string | 账户地址         |  是   |
| encryptedPrivateKey | string | 账户密文私钥       |  是   |
| password            | string | 密码           |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述          |
| ----- |:------:| ------------- |
| hash  | string | 交易hash        |
| txHex | string | 签名后的交易16进制字符串 |
### Example request data: 
```json
{
  "jsonrpc" : "2.0",
  "method" : "encryptedPriKeySign",
  "params" : [ 2, "0200b67f2d5d0672656d61726b008c01170200012a9af4ee49f4cb1ee84eafd42aec41bc04b28f7b02000100402a8648170000000000000000000000000000000000000000000000000000000800000000000000000001170200012a9af4ee49f4cb1ee84eafd42aec41bc04b28f7b0200010000e8764817000000000000000000000000000000000000000000000000000000000000000000000000", "tNULSeBaMhcccH1KeXhMpH5y3pvtRzatAiuMJk", "8dbe5c1da7228c0a8b6a26c328231b8df2d4dbfd3f9b029557708d4560de9ecd53a353bb2d688d7c68bd11d741e5d3ed", "abcd" ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : {
    "txHex" : "0200b67f2d5d0672656d61726b008c01170200012a9af4ee49f4cb1ee84eafd42aec41bc04b28f7b02000100402a8648170000000000000000000000000000000000000000000000000000000800000000000000000001170200012a9af4ee49f4cb1ee84eafd42aec41bc04b28f7b0200010000e876481700000000000000000000000000000000000000000000000000000000000000000000006a21024477033a4521efee5f90caf30f8eb3284e8d1bb7fef2923ae21617b24aacc8cb473045022100a8b3d10dfdf4fb0c7c6ede1f5d216a631689fbbd0e9beb46cac1918a5e64ccbc02202a654c3d9a27a99e8458ac18a8b9bc460f520bff10e4592102ad04e22890b412",
    "hash" : "748184df91eda8d09be76e075d553313434c56bfeec3d449abc99ba6c430c00c"
  }
}
```

2.1 根据区块高度查询区块头
===============
Cmd: getHeaderByHeight
----------------------
_**详细描述: 根据区块高度查询区块头**_

参数列表
----
| 参数名     | 参数类型 | 参数描述 | 是否必填 |
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
### Example request data: 
```json
{
  "jsonrpc" : "2.0",
  "method" : "getHeaderByHeight",
  "params" : [ 2, 1 ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : {
    "hash" : "0061dee8b289df58e3c820f38b31ce47d02993797f976eacd6020ced392a6b5b",
    "preHash" : "d8880f913c984e4dece5cfb3f5f1d96d6ee923ffb0b47be0079fe84472ddda83",
    "merkleHash" : "8930f7386e33eaf79c22025956820fa58f403b7dbdf3d39ca5f2be5776e8b8e5",
    "time" : "1970-01-19 10:14:08.008",
    "height" : 1,
    "txCount" : 1,
    "blockSignature" : "473045022100f2012721b3eef4bc052bcef76903cb4eab029020b09a300968f7dde6fb7c56be0220621774e67bc8b09440ab40273f64795d83394ec6ad3c9458801c36e9b0f29850",
    "size" : 247,
    "packingAddress" : "tNULSeBaMkrt4z9FYEkkR9D6choPVvQr94oYZp",
    "roundIndex" : 156324818,
    "consensusMemberCount" : 1,
    "roundStartTime" : "1970-01-19 10:14:08.008",
    "packingIndexOfRound" : 1,
    "mainVersion" : 1,
    "blockVersion" : 1,
    "stateRoot" : "56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421"
  }
}
```

2.2 根据区块hash查询区块头
=================
Cmd: getHeaderByHash
--------------------
_**详细描述: 根据区块hash查询区块头**_

参数列表
----
| 参数名     |  参数类型  | 参数描述   | 是否必填 |
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
### Example request data: 
```json
{
  "jsonrpc" : "2.0",
  "method" : "getHeaderByHash",
  "params" : [ 2, "0061dee8b289df58e3c820f38b31ce47d02993797f976eacd6020ced392a6b5b" ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : {
    "hash" : "0061dee8b289df58e3c820f38b31ce47d02993797f976eacd6020ced392a6b5b",
    "preHash" : "d8880f913c984e4dece5cfb3f5f1d96d6ee923ffb0b47be0079fe84472ddda83",
    "merkleHash" : "8930f7386e33eaf79c22025956820fa58f403b7dbdf3d39ca5f2be5776e8b8e5",
    "time" : "1970-01-19 10:14:08.008",
    "height" : 1,
    "txCount" : 1,
    "blockSignature" : "473045022100f2012721b3eef4bc052bcef76903cb4eab029020b09a300968f7dde6fb7c56be0220621774e67bc8b09440ab40273f64795d83394ec6ad3c9458801c36e9b0f29850",
    "size" : 247,
    "packingAddress" : "tNULSeBaMkrt4z9FYEkkR9D6choPVvQr94oYZp",
    "roundIndex" : 156324818,
    "consensusMemberCount" : 1,
    "roundStartTime" : "1970-01-19 10:14:08.008",
    "packingIndexOfRound" : 1,
    "mainVersion" : 1,
    "blockVersion" : 1,
    "stateRoot" : "56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421"
  }
}
```

2.3 查询最新区块头信息
=============
Cmd: getBestBlockHeader
-----------------------
_**详细描述: 查询最新区块头信息**_

参数列表
----
| 参数名     | 参数类型 | 参数描述 | 是否必填 |
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
### Example request data: 
```json
{
  "jsonrpc" : "2.0",
  "method" : "getBestBlockHeader",
  "params" : [ 2 ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : {
    "hash" : "f1003ee7c46ee33c5d6c518342c993cad7d202767cb4b7b5ddb69ce19d8899ea",
    "preHash" : "8edfb6610be130020c3815915e81eccaa4c3c426362d1239030119b3a2941923",
    "merkleHash" : "4b4564bff52373d698dbb4d95ea66d23b18a2ae09079a9e62b8f4d7ddf8bdb5c",
    "time" : "1970-01-19 10:14:18.018",
    "height" : 1000,
    "txCount" : 1,
    "blockSignature" : "4730450221009d13cd79b918fba44b4ca549a37dc715e368ac55fe80170f54f52c2742da0ed802207312ee6d38b95a28feaca40ed9c91fba4d47fe5efa1940ecd4fe63e7b9cb5533",
    "size" : 247,
    "packingAddress" : "tNULSeBaMkrt4z9FYEkkR9D6choPVvQr94oYZp",
    "roundIndex" : 156325817,
    "consensusMemberCount" : 1,
    "roundStartTime" : "1970-01-19 10:14:18.018",
    "packingIndexOfRound" : 1,
    "mainVersion" : 1,
    "blockVersion" : 1,
    "stateRoot" : "56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421"
  }
}
```

2.4 查询最新区块
==========
Cmd: getBestBlock
-----------------
_**详细描述: 包含区块打包的所有交易信息，此接口返回数据量较多，谨慎调用**_

参数列表
----
| 参数名     | 参数类型 | 参数描述 | 是否必填 |
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
### Example request data: 
```json
{
  "jsonrpc" : "2.0",
  "method" : "getBestBlock",
  "params" : [ 2 ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : {
    "header" : {
      "hash" : "55ff1491334a3e636e504f1bc12ba04fa0c582381a0b8e0c3f7aaa12a27fabb5",
      "preHash" : "97bb75f9d12e945396ffb386373941c05d9671770bd4639554e5ed948e775f8c",
      "merkleHash" : "0ecd099ee9c5955588516a6f619d9bef6406a7d2aa31eec592df2c6cb19e326d",
      "time" : "1970-01-19 10:14:21.021",
      "height" : 1348,
      "txCount" : 1,
      "blockSignature" : "463044022046aa28d324da4ec487829fcc8901e351eb13a0290bdd05c084d5e42a876ab6a1022024aa4386081787506771f5e8ddbe7a625d6f4aff67e5c10818fbd4f98ccf264e",
      "size" : 234,
      "packingAddress" : "tNULSeBaMkrt4z9FYEkkR9D6choPVvQr94oYZp",
      "roundIndex" : 156326165,
      "consensusMemberCount" : 1,
      "roundStartTime" : "1970-01-19 10:14:21.021",
      "packingIndexOfRound" : 1,
      "mainVersion" : 1,
      "blockVersion" : 1,
      "stateRoot" : "56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421"
    },
    "txs" : [ {
      "type" : 1,
      "coinData" : "AAA=",
      "txData" : null,
      "time" : 1563261651,
      "transactionSignature" : null,
      "remark" : null,
      "hash" : {
        "bytes" : "Ds0JnunFlVWIUWpvYZ2b72QGp9KqMe7Fkt8sbLGeMm0="
      },
      "blockHeight" : 1348,
      "status" : "UNCONFIRM",
      "size" : 12,
      "inBlockIndex" : 0,
      "coinDataInstance" : {
        "from" : [ ],
        "to" : [ ],
        "fromAddressCount" : 0
      },
      "fee" : 0,
      "multiSignTx" : false
    } ]
  }
}
```

2.5 根据区块高度查询区块
==============
Cmd: getBlockByHeight
---------------------
_**详细描述: 包含区块打包的所有交易信息，此接口返回数据量较多，谨慎调用**_

参数列表
----
| 参数名     | 参数类型 | 参数描述 | 是否必填 |
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
### Example request data: 
```json
{
  "jsonrpc" : "2.0",
  "method" : "getBlockByHeight",
  "params" : [ 2, 100 ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : {
    "header" : {
      "hash" : "0620b926b2f09921315cc251bd65fe803cfa9e2259275900f7f7509cd0dac6d3",
      "preHash" : "975c90cbc8dedc577ebf315be4d11b4153c2bbb1b9704484c45752215717aa1d",
      "merkleHash" : "c9144c126f64f2e79d11879af9f4c94839202c464bb854dae17d89800de30fc6",
      "time" : "1970-01-19 10:14:09.009",
      "height" : 100,
      "txCount" : 1,
      "blockSignature" : "463044022060286d182fb808bb24543730a0316688b2c02f8378f112bca15d0860288dc5340220566b867e1813ed57c79b5b6ed9baf1f07e29afa8b445a842120c5407557a7363",
      "size" : 234,
      "packingAddress" : "tNULSeBaMkrt4z9FYEkkR9D6choPVvQr94oYZp",
      "roundIndex" : 156324917,
      "consensusMemberCount" : 1,
      "roundStartTime" : "1970-01-19 10:14:09.009",
      "packingIndexOfRound" : 1,
      "mainVersion" : 1,
      "blockVersion" : 1,
      "stateRoot" : "56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421"
    },
    "txs" : [ {
      "type" : 1,
      "coinData" : "AAA=",
      "txData" : null,
      "time" : 1563249171,
      "transactionSignature" : null,
      "remark" : null,
      "hash" : {
        "bytes" : "yRRMEm9k8uedEYea+fTJSDkgLEZLuFTa4X2JgA3jD8Y="
      },
      "blockHeight" : 100,
      "status" : "UNCONFIRM",
      "size" : 12,
      "inBlockIndex" : 0,
      "coinDataInstance" : {
        "from" : [ ],
        "to" : [ ],
        "fromAddressCount" : 0
      },
      "fee" : 0,
      "multiSignTx" : false
    } ]
  }
}
```

2.6 根据区块hash查询区块
================
Cmd: getBlockByHash
-------------------
_**详细描述: 包含区块打包的所有交易信息，此接口返回数据量较多，谨慎调用**_

参数列表
----
| 参数名     |  参数类型  | 参数描述   | 是否必填 |
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
### Example request data: 
```json
{
  "jsonrpc" : "2.0",
  "method" : "getBlockByHash",
  "params" : [ 2, "0620b926b2f09921315cc251bd65fe803cfa9e2259275900f7f7509cd0dac6d3" ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : {
    "header" : {
      "hash" : "0620b926b2f09921315cc251bd65fe803cfa9e2259275900f7f7509cd0dac6d3",
      "preHash" : "975c90cbc8dedc577ebf315be4d11b4153c2bbb1b9704484c45752215717aa1d",
      "merkleHash" : "c9144c126f64f2e79d11879af9f4c94839202c464bb854dae17d89800de30fc6",
      "time" : "1970-01-19 10:14:09.009",
      "height" : 100,
      "txCount" : 1,
      "blockSignature" : "463044022060286d182fb808bb24543730a0316688b2c02f8378f112bca15d0860288dc5340220566b867e1813ed57c79b5b6ed9baf1f07e29afa8b445a842120c5407557a7363",
      "size" : 234,
      "packingAddress" : "tNULSeBaMkrt4z9FYEkkR9D6choPVvQr94oYZp",
      "roundIndex" : 156324917,
      "consensusMemberCount" : 1,
      "roundStartTime" : "1970-01-19 10:14:09.009",
      "packingIndexOfRound" : 1,
      "mainVersion" : 1,
      "blockVersion" : 1,
      "stateRoot" : "56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421"
    },
    "txs" : [ {
      "type" : 1,
      "coinData" : "AAA=",
      "txData" : null,
      "time" : 1563249171,
      "transactionSignature" : null,
      "remark" : null,
      "hash" : {
        "bytes" : "yRRMEm9k8uedEYea+fTJSDkgLEZLuFTa4X2JgA3jD8Y="
      },
      "blockHeight" : 100,
      "status" : "UNCONFIRM",
      "size" : 12,
      "inBlockIndex" : 0,
      "coinDataInstance" : {
        "from" : [ ],
        "to" : [ ],
        "fromAddressCount" : 0
      },
      "fee" : 0,
      "multiSignTx" : false
    } ]
  }
}
```

3.1 根据hash获取交易
==============
Cmd: getTx
----------
_**详细描述: 根据hash获取交易**_

参数列表
----
| 参数名     |  参数类型  | 参数描述   | 是否必填 |
| ------- |:------:| ------ |:----:|
| chainId |  int   | 链id    |  是   |
| hash    | string | 交易hash |  是   |

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
```json
{
  "jsonrpc" : "2.0",
  "method" : "getTx",
  "params" : [ 2, "40acabd7e7b7643aa545f2b74d09f8d65eecf885919d968d263a7a24255f8698" ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : {
    "hash" : "40acabd7e7b7643aa545f2b74d09f8d65eecf885919d968d263a7a24255f8698",
    "type" : 2,
    "time" : "2019-07-16 15:24:55.055",
    "blockHeight" : 1373,
    "remark" : null,
    "transactionSignature" : "2103958b790c331954ed367d37bac901de5c2f06ac8368b37d7bd6cd5ae143c1d7e3473045022100c2cdaec043c8e5f26cf2efcd63ce9a27461d0569fc4f5c13ee685c506329da4702204f3e0fc3aed450dbb8ac14b5745c1e694100092bad63a40247a534a82fcdab9d",
    "status" : 1,
    "size" : 256,
    "inBlockIndex" : 0,
    "form" : [ {
      "address" : "tNULSeBaMvEtDfvZuukDf2mVyfGo3DdiN8KLRG",
      "assetsChainId" : 2,
      "assetsId" : 1,
      "amount" : "10000000100000",
      "nonce" : "0000000000000000",
      "locked" : 0
    } ],
    "to" : [ {
      "address" : "tNULSeBaMhcccH1KeXhMpH5y3pvtRzatAiuMJk",
      "assetsChainId" : 2,
      "assetsId" : 1,
      "amount" : "10000000000000",
      "lockTime" : 0
    } ]
  }
}
```

3.2 验证交易
========
Cmd: validateTx
---------------
_**详细描述: 验证离线组装的交易,验证成功返回交易hash值,失败返回错误提示信息**_

参数列表
----
| 参数名     |  参数类型  | 参数描述     | 是否必填 |
| ------- |:------:| -------- |:----:|
| chainId |  int   | 链id      |  是   |
| tx      | string | 交易序列化字符串 |  是   |

返回值
---
| 字段名   |  字段类型  | 参数描述   |
| ----- |:------:| ------ |
| value | string | 交易hash |
### Example request data: 
略

### Example response data: 
略

3.3 广播交易
========
Cmd: broadcastTx
----------------
_**详细描述: 广播离线组装的交易,成功返回true,失败返回错误提示信息**_

参数列表
----
| 参数名     |  参数类型  | 参数描述         | 是否必填 |
| ------- |:------:| ------------ |:----:|
| chainId |  int   | 链id          |  是   |
| tx      | string | 交易序列化16进制字符串 |  是   |

返回值
---
| 字段名   |  字段类型   | 参数描述   |
| ----- |:-------:| ------ |
| value | boolean | 是否成功   |
| hash  | string  | 交易hash |
### Example request data: 
略

### Example response data: 
略

3.4 单笔转账
========
Cmd: transfer
-------------
_**详细描述: 发起单账户单资产的转账交易**_

参数列表
----
| 参数名       |  参数类型  | 参数描述   | 是否必填 |
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
### Example request data: 
```json
{
  "jsonrpc" : "2.0",
  "method" : "transfer",
  "params" : [ 2, 1, "tNULSeBaMvEtDfvZuukDf2mVyfGo3DdiN8KLRG", "tNULSeBaMhcccH1KeXhMpH5y3pvtRzatAiuMJk", "nuls123456", "10000000000000", "transfer tx" ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : {
    "hash" : "40acabd7e7b7643aa545f2b74d09f8d65eecf885919d968d263a7a24255f8698"
  }
}
```

3.5 离线组装转账交易
============
Cmd: createTransferTxOffline
----------------------------
_**详细描述: 根据inputs和outputs离线组装转账交易，用于单账户或多账户的转账交易。交易手续费为inputs里本链主资产金额总和，减去outputs里本链主资产总和**_

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
```json
{
  "jsonrpc" : "2.0",
  "method" : "createTransferTxOffline",
  "params" : [ [ {
    "address" : "tNULSeBaMhcccH1KeXhMpH5y3pvtRzatAiuMJk",
    "assetChainId" : 2,
    "assetId" : 1,
    "amount" : "100001000000",
    "nonce" : "0000000000000000"
  } ], [ {
    "address" : "tNULSeBaMhcccH1KeXhMpH5y3pvtRzatAiuMJk",
    "assetChainId" : 2,
    "assetId" : 1,
    "amount" : "100000000000",
    "lockTime" : 0
  } ], "remark" ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : {
    "txHex" : "0200b67f2d5d0672656d61726b008c01170200012a9af4ee49f4cb1ee84eafd42aec41bc04b28f7b02000100402a8648170000000000000000000000000000000000000000000000000000000800000000000000000001170200012a9af4ee49f4cb1ee84eafd42aec41bc04b28f7b0200010000e8764817000000000000000000000000000000000000000000000000000000000000000000000000",
    "hash" : "748184df91eda8d09be76e075d553313434c56bfeec3d449abc99ba6c430c00c"
  }
}
```

3.6 计算离线创建转账交易所需手续费
===================
Cmd: calcTransferTxFee
----------------------
_**详细描述: 计算离线创建转账交易所需手续费**_

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
```json
{
  "jsonrpc" : "2.0",
  "method" : "calcTransferTxFee",
  "params" : [ 6, 6, 2, "remark", "1000000" ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : {
    "value" : 2000000
  }
}
```

4.1 发布合约
========
Cmd: contractCreate
-------------------
_**详细描述: 发布合约**_

参数列表
----
| 参数名          |   参数类型   | 参数描述                 | 是否必填 |
| ------------ |:--------:| -------------------- |:----:|
| chainId      |   int    | 链id                  |  是   |
| sender       |  string  | 交易创建者账户地址            |  是   |
| password     |  string  | 账户密码                 |  是   |
| alias        |  string  | 合约别名                 |  是   |
| gasLimit     |   long   | GAS限制                |  是   |
| price        |   long   | GAS单价                |  是   |
| contractCode |  string  | 智能合约代码(字节码的Hex编码字符串) |  是   |
| args         | object[] | 参数列表                 |  否   |
| remark       |  string  | 交易备注                 |  否   |

返回值
---
| 字段名             |  字段类型  | 参数描述        |
| --------------- |:------:| ----------- |
| txHash          | string | 发布合约的交易hash |
| contractAddress | string | 生成的合约地址     |
### Example request data: 
略

### Example response data: 
略

4.2 调用合约
========
Cmd: contractCall
-----------------
_**详细描述: 调用合约**_

参数列表
----
| 参数名             |    参数类型    | 参数描述                                     | 是否必填 |
| --------------- |:----------:| ---------------------------------------- |:----:|
| chainId         |    int     | 链id                                      |  是   |
| sender          |   string   | 交易创建者账户地址                                |  是   |
| password        |   string   | 调用者账户密码                                  |  是   |
| value           | biginteger | 调用者向合约地址转入的主网资产金额，没有此业务时填BigInteger.ZERO |  是   |
| gasLimit        |    long    | GAS限制                                    |  是   |
| price           |    long    | GAS单价                                    |  是   |
| contractAddress |   string   | 合约地址                                     |  是   |
| methodName      |   string   | 合约方法                                     |  是   |
| methodDesc      |   string   | 合约方法描述，若合约内方法没有重载，则此参数可以为空               |  否   |
| args            |  object[]  | 参数列表                                     |  否   |
| remark          |   string   | 交易备注                                     |  否   |

返回值
---
| 字段名    |  字段类型  | 参数描述        |
| ------ |:------:| ----------- |
| txHash | string | 调用合约的交易hash |
### Example request data: 
略

### Example response data: 
略

4.3 删除合约
========
Cmd: contractDelete
-------------------
_**详细描述: 删除合约**_

参数列表
----
| 参数名             |  参数类型  | 参数描述      | 是否必填 |
| --------------- |:------:| --------- |:----:|
| chainId         |  int   | 链id       |  是   |
| sender          | string | 交易创建者账户地址 |  是   |
| password        | string | 交易账户密码    |  是   |
| contractAddress | string | 合约地址      |  是   |
| remark          | string | 交易备注      |  否   |

返回值
---
| 字段名    |  字段类型  | 参数描述        |
| ------ |:------:| ----------- |
| txHash | string | 删除合约的交易hash |
### Example request data: 
略

### Example response data: 
略

4.4 合约token转账
=============
Cmd: tokentransfer
------------------
_**详细描述: 合约token转账**_

参数列表
----
| 参数名             |    参数类型    | 参数描述                                     | 是否必填 |
| --------------- |:----------:| ---------------------------------------- |:----:|
| chainId         |    int     | 链id                                      |  是   |
| fromAddress     |   string   | 交易创建者账户地址                                |  是   |
| password        |   string   | 调用者账户密码                                  |  是   |
| toAddress       |   string   | 调用者向合约地址转入的主网资产金额，没有此业务时填BigInteger.ZERO |  是   |
| contractAddress |   string   | 合约地址                                     |  是   |
| amount          | biginteger | 合约方法                                     |  是   |
| remark          |   string   | 交易备注                                     |  否   |

返回值
---
| 字段名    |  字段类型  | 参数描述   |
| ------ |:------:| ------ |
| txHash | string | 交易hash |
### Example request data: 
略

### Example response data: 
略

4.5 从账户地址向合约地址转账(主链资产)的合约交易
===========================
Cmd: transfer2contract
----------------------
_**详细描述: 从账户地址向合约地址转账(主链资产)的合约交易**_

参数列表
----
| 参数名         |    参数类型    | 参数描述                                     | 是否必填 |
| ----------- |:----------:| ---------------------------------------- |:----:|
| chainId     |    int     | 链id                                      |  是   |
| fromAddress |   string   | 交易创建者账户地址                                |  是   |
| password    |   string   | 调用者账户密码                                  |  是   |
| toAddress   |   string   | 调用者向合约地址转入的主网资产金额，没有此业务时填BigInteger.ZERO |  是   |
| amount      | biginteger | 合约方法                                     |  是   |
| remark      |   string   | 交易备注                                     |  否   |

返回值
---
| 字段名    |  字段类型  | 参数描述   |
| ------ |:------:| ------ |
| txHash | string | 交易hash |
### Example request data: 
略

### Example response data: 
略

4.6 获取账户地址的指定合约的token余额
=======================
Cmd: getTokenBalance
--------------------
_**详细描述: 获取账户地址的指定合约的token余额**_

参数列表
----
| 参数名             |  参数类型  | 参数描述 | 是否必填 |
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
### Example request data: 
略

### Example response data: 
略

4.7 获取智能合约详细信息
==============
Cmd: getContract
----------------
_**详细描述: 获取智能合约详细信息**_

参数列表
----
| 参数名             |  参数类型  | 参数描述 | 是否必填 |
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
### Example request data: 
```json
{ }
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : {
    "createTxHash" : "ec11dfb9202090276fcde3d5c615caaa406ae6a5826a479878f560758db27bd9",
    "address" : "tNULSeBaNCP8xykvLm4CaVrGtkiybQwPx6LrSD",
    "creater" : "tNULSeBaMvEtDfvZuukDf2mVyfGo3DdiN8KLRG",
    "alias" : "kqb",
    "createTime" : 1563170044,
    "blockHeight" : 27048,
    "directPayable" : false,
    "nrc20" : true,
    "nrc20TokenName" : "KQB",
    "nrc20TokenSymbol" : "KongQiBi",
    "decimals" : 2,
    "totalSupply" : "1000000000000",
    "status" : "normal",
    "method" : [ {
      "name" : "name",
      "desc" : "() return String",
      "args" : [ ],
      "returnArg" : "String",
      "view" : true,
      "event" : false,
      "payable" : false
    }, {
      "name" : "symbol",
      "desc" : "() return String",
      "args" : [ ],
      "returnArg" : "String",
      "view" : true,
      "event" : false,
      "payable" : false
    }, {
      "name" : "decimals",
      "desc" : "() return int",
      "args" : [ ],
      "returnArg" : "int",
      "view" : true,
      "event" : false,
      "payable" : false
    }, {
      "name" : "totalSupply",
      "desc" : "() return BigInteger",
      "args" : [ ],
      "returnArg" : "BigInteger",
      "view" : true,
      "event" : false,
      "payable" : false
    }, {
      "name" : "<init>",
      "desc" : "(String name, String symbol, BigInteger initialAmount, int decimals) return void",
      "args" : [ {
        "type" : "String",
        "name" : "name",
        "required" : true
      }, {
        "type" : "String",
        "name" : "symbol",
        "required" : true
      }, {
        "type" : "BigInteger",
        "name" : "initialAmount",
        "required" : true
      }, {
        "type" : "int",
        "name" : "decimals",
        "required" : true
      } ],
      "returnArg" : "void",
      "view" : false,
      "event" : false,
      "payable" : false
    }, {
      "name" : "allowance",
      "desc" : "(Address owner, Address spender) return BigInteger",
      "args" : [ {
        "type" : "Address",
        "name" : "owner",
        "required" : true
      }, {
        "type" : "Address",
        "name" : "spender",
        "required" : true
      } ],
      "returnArg" : "BigInteger",
      "view" : true,
      "event" : false,
      "payable" : false
    }, {
      "name" : "transferFrom",
      "desc" : "(Address from, Address to, BigInteger value) return boolean",
      "args" : [ {
        "type" : "Address",
        "name" : "from",
        "required" : true
      }, {
        "type" : "Address",
        "name" : "to",
        "required" : true
      }, {
        "type" : "BigInteger",
        "name" : "value",
        "required" : true
      } ],
      "returnArg" : "boolean",
      "view" : false,
      "event" : false,
      "payable" : false
    }, {
      "name" : "balanceOf",
      "desc" : "(Address owner) return BigInteger",
      "args" : [ {
        "type" : "Address",
        "name" : "owner",
        "required" : true
      } ],
      "returnArg" : "BigInteger",
      "view" : true,
      "event" : false,
      "payable" : false
    }, {
      "name" : "transfer",
      "desc" : "(Address to, BigInteger value) return boolean",
      "args" : [ {
        "type" : "Address",
        "name" : "to",
        "required" : true
      }, {
        "type" : "BigInteger",
        "name" : "value",
        "required" : true
      } ],
      "returnArg" : "boolean",
      "view" : false,
      "event" : false,
      "payable" : false
    }, {
      "name" : "approve",
      "desc" : "(Address spender, BigInteger value) return boolean",
      "args" : [ {
        "type" : "Address",
        "name" : "spender",
        "required" : true
      }, {
        "type" : "BigInteger",
        "name" : "value",
        "required" : true
      } ],
      "returnArg" : "boolean",
      "view" : false,
      "event" : false,
      "payable" : false
    }, {
      "name" : "increaseApproval",
      "desc" : "(Address spender, BigInteger addedValue) return boolean",
      "args" : [ {
        "type" : "Address",
        "name" : "spender",
        "required" : true
      }, {
        "type" : "BigInteger",
        "name" : "addedValue",
        "required" : true
      } ],
      "returnArg" : "boolean",
      "view" : false,
      "event" : false,
      "payable" : false
    }, {
      "name" : "decreaseApproval",
      "desc" : "(Address spender, BigInteger subtractedValue) return boolean",
      "args" : [ {
        "type" : "Address",
        "name" : "spender",
        "required" : true
      }, {
        "type" : "BigInteger",
        "name" : "subtractedValue",
        "required" : true
      } ],
      "returnArg" : "boolean",
      "view" : false,
      "event" : false,
      "payable" : false
    }, {
      "name" : "TransferEvent",
      "desc" : "(Address from, Address to, BigInteger value) return void",
      "args" : [ {
        "type" : "Address",
        "name" : "from",
        "required" : false
      }, {
        "type" : "Address",
        "name" : "to",
        "required" : true
      }, {
        "type" : "BigInteger",
        "name" : "value",
        "required" : true
      } ],
      "returnArg" : "void",
      "view" : false,
      "event" : true,
      "payable" : false
    }, {
      "name" : "ApprovalEvent",
      "desc" : "(Address owner, Address spender, BigInteger value) return void",
      "args" : [ {
        "type" : "Address",
        "name" : "owner",
        "required" : true
      }, {
        "type" : "Address",
        "name" : "spender",
        "required" : true
      }, {
        "type" : "BigInteger",
        "name" : "value",
        "required" : true
      } ],
      "returnArg" : "void",
      "view" : false,
      "event" : true,
      "payable" : false
    } ]
  }
}
```

4.8 获取智能合约执行结果
==============
Cmd: getContractTxResult
------------------------
_**详细描述: 获取智能合约执行结果**_

参数列表
----
| 参数名     |  参数类型  | 参数描述   | 是否必填 |
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
### Example request data: 
```json
{ }
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : {
    "success" : true,
    "errorMessage" : null,
    "contractAddress" : "tNULSeBaNCP8xykvLm4CaVrGtkiybQwPx6LrSD",
    "result" : null,
    "gasLimit" : 200000,
    "gasUsed" : 15794,
    "price" : 25,
    "totalFee" : "5700000",
    "txSizeFee" : "700000",
    "actualContractFee" : "394850",
    "refundFee" : "4605150",
    "value" : "0",
    "stackTrace" : null,
    "transfers" : [ ],
    "events" : [ "{\"contractAddress\":\"tNULSeBaNCP8xykvLm4CaVrGtkiybQwPx6LrSD\",\"blockNumber\":27048,\"event\":\"TransferEvent\",\"payload\":{\"from\":null,\"to\":\"tNULSeBaMvEtDfvZuukDf2mVyfGo3DdiN8KLRG\",\"value\":\"1000000000000\"}}" ],
    "tokenTransfers" : [ {
      "contractAddress" : "tNULSeBaNCP8xykvLm4CaVrGtkiybQwPx6LrSD",
      "from" : null,
      "to" : "tNULSeBaMvEtDfvZuukDf2mVyfGo3DdiN8KLRG",
      "value" : "1000000000000",
      "name" : "KQB",
      "symbol" : "KongQiBi",
      "decimals" : 2
    } ],
    "invokeRegisterCmds" : [ ],
    "contractTxList" : [ ],
    "remark" : "create"
  }
}
```

4.9 获取合约代码构造函数
==============
Cmd: getContractConstructor
---------------------------
_**详细描述: 获取合约代码构造函数**_

参数列表
----
| 参数名          |  参数类型  | 参数描述                 | 是否必填 |
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
### Example request data: 
略

### Example response data: 
略

4.10 获取合约方法信息
=============
Cmd: getContractMethod
----------------------
_**详细描述: 获取合约方法信息**_

参数列表
----
| 参数名             |  参数类型  | 参数描述 | 是否必填 |
| --------------- |:------:| ---- |:----:|
| chainId         |  int   | 链ID  |  是   |
| contractAddress | string | 合约地址 |  是   |
| methodName      | string | 方法名称 |  是   |
| methodDesc      | string | 方法描述 |  否   |

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
略

### Example response data: 
略

4.11 获取合约方法参数类型
===============
Cmd: getContractMethodArgsTypes
-------------------------------
_**详细描述: 获取合约方法参数类型**_

参数列表
----
| 参数名             |  参数类型  | 参数描述 | 是否必填 |
| --------------- |:------:| ---- |:----:|
| chainId         |  int   | 链ID  |  是   |
| contractAddress | string | 合约地址 |  是   |
| methodName      | string | 方法名称 |  是   |
| methodDesc      | string | 方法描述 |  否   |

返回值
---
| 字段名 |      字段类型       | 参数描述 |
| --- |:---------------:| ---- |
| 返回值 | list&lt;string> |      |
### Example request data: 
略

### Example response data: 
略

4.12 验证发布合约
===========
Cmd: validateContractCreate
---------------------------
_**详细描述: 验证发布合约**_

参数列表
----
| 参数名          |   参数类型   | 参数描述                 | 是否必填 |
| ------------ |:--------:| -------------------- |:----:|
| chainId      |   int    | 链id                  |  是   |
| sender       |  string  | 交易创建者账户地址            |  是   |
| gasLimit     |   long   | GAS限制                |  是   |
| price        |   long   | GAS单价                |  是   |
| contractCode |  string  | 智能合约代码(字节码的Hex编码字符串) |  是   |
| args         | object[] | 参数列表                 |  否   |

返回值
---
| 字段名     |  字段类型   | 参数描述      |
| ------- |:-------:| --------- |
| success | boolean | 验证成功与否    |
| code    | string  | 验证失败的错误码  |
| msg     | string  | 验证失败的错误信息 |
### Example request data: 
略

### Example response data: 
略

4.13 验证调用合约
===========
Cmd: validateContractCall
-------------------------
_**详细描述: 验证调用合约**_

参数列表
----
| 参数名             |    参数类型    | 参数描述                                     | 是否必填 |
| --------------- |:----------:| ---------------------------------------- |:----:|
| chainId         |    int     | 链id                                      |  是   |
| sender          |   string   | 交易创建者账户地址                                |  是   |
| value           | biginteger | 调用者向合约地址转入的主网资产金额，没有此业务时填BigInteger.ZERO |  是   |
| gasLimit        |    long    | GAS限制                                    |  是   |
| price           |    long    | GAS单价                                    |  是   |
| contractAddress |   string   | 合约地址                                     |  是   |
| methodName      |   string   | 合约方法                                     |  是   |
| methodDesc      |   string   | 合约方法描述，若合约内方法没有重载，则此参数可以为空               |  否   |
| args            |  object[]  | 参数列表                                     |  否   |

返回值
---
| 字段名     |  字段类型   | 参数描述      |
| ------- |:-------:| --------- |
| success | boolean | 验证成功与否    |
| code    | string  | 验证失败的错误码  |
| msg     | string  | 验证失败的错误信息 |
### Example request data: 
略

### Example response data: 
略

4.14 验证删除合约
===========
Cmd: validateContractDelete
---------------------------
_**详细描述: 验证删除合约**_

参数列表
----
| 参数名             |  参数类型  | 参数描述      | 是否必填 |
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
### Example request data: 
略

### Example response data: 
略

4.15 估算发布合约交易的GAS
=================
Cmd: imputedContractCreateGas
-----------------------------
_**详细描述: 估算发布合约交易的GAS**_

参数列表
----
| 参数名          |   参数类型   | 参数描述                 | 是否必填 |
| ------------ |:--------:| -------------------- |:----:|
| chainId      |   int    | 链id                  |  是   |
| sender       |  string  | 交易创建者账户地址            |  是   |
| contractCode |  string  | 智能合约代码(字节码的Hex编码字符串) |  是   |
| args         | object[] | 参数列表                 |  否   |

返回值
---
| 字段名      | 字段类型 | 参数描述              |
| -------- |:----:| ----------------- |
| gasLimit | long | 消耗的gas值，执行失败返回数值1 |
### Example request data: 
略

### Example response data: 
略

4.16 估算调用合约交易的GAS
=================
Cmd: imputedContractCallGas
---------------------------
_**详细描述: 估算调用合约交易的GAS**_

参数列表
----
| 参数名             |    参数类型    | 参数描述                                     | 是否必填 |
| --------------- |:----------:| ---------------------------------------- |:----:|
| chainId         |    int     | 链id                                      |  是   |
| sender          |   string   | 交易创建者账户地址                                |  是   |
| value           | biginteger | 调用者向合约地址转入的主网资产金额，没有此业务时填BigInteger.ZERO |  是   |
| contractAddress |   string   | 合约地址                                     |  是   |
| methodName      |   string   | 合约方法                                     |  是   |
| methodDesc      |   string   | 合约方法描述，若合约内方法没有重载，则此参数可以为空               |  否   |
| args            |  object[]  | 参数列表                                     |  否   |

返回值
---
| 字段名      | 字段类型 | 参数描述              |
| -------- |:----:| ----------------- |
| gasLimit | long | 消耗的gas值，执行失败返回数值1 |
### Example request data: 
略

### Example response data: 
略

4.17 调用合约不上链方法
==============
Cmd: invokeView
---------------
_**详细描述: 调用合约不上链方法**_

参数列表
----
| 参数名             |   参数类型   | 参数描述                       | 是否必填 |
| --------------- |:--------:| -------------------------- |:----:|
| chainId         |   int    | 链id                        |  是   |
| contractAddress |  string  | 合约地址                       |  是   |
| methodName      |  string  | 合约方法                       |  是   |
| methodDesc      |  string  | 合约方法描述，若合约内方法没有重载，则此参数可以为空 |  否   |
| args            | object[] | 参数列表                       |  否   |

返回值
---
| 字段名    |  字段类型  | 参数描述      |
| ------ |:------:| --------- |
| result | string | 视图方法的调用结果 |
### Example request data: 
略

### Example response data: 
略

4.18 离线 - 发布合约交易
================
Cmd: contractCreateOffline
--------------------------
_**详细描述: 离线 - 发布合约交易**_

参数列表
----
| 参数名          |   参数类型   | 参数描述                 | 是否必填 |
| ------------ |:--------:| -------------------- |:----:|
| chainId      |   int    | 链id                  |  是   |
| sender       |  string  | 交易创建者账户地址            |  是   |
| alias        |  string  | 合约别名                 |  是   |
| contractCode |  string  | 智能合约代码(字节码的Hex编码字符串) |  是   |
| args         | object[] | 参数列表                 |  否   |
| remark       |  string  | 交易备注                 |  否   |

返回值
---
| 字段名             |  字段类型  | 参数描述     |
| --------------- |:------:| -------- |
| hash            | string | 交易hash   |
| txHex           | string | 交易序列化字符串 |
| contractAddress | string | 生成的合约地址  |
### Example request data: 
略

### Example response data: 
略

4.19 离线 - 调用合约
==============
Cmd: contractCallOffline
------------------------
_**详细描述: 离线 - 调用合约**_

参数列表
----
| 参数名             |    参数类型    | 参数描述                                     | 是否必填 |
| --------------- |:----------:| ---------------------------------------- |:----:|
| chainId         |    int     | 链id                                      |  是   |
| sender          |   string   | 交易创建者账户地址                                |  是   |
| value           | biginteger | 调用者向合约地址转入的主网资产金额，没有此业务时填BigInteger.ZERO |  是   |
| contractAddress |   string   | 合约地址                                     |  是   |
| methodName      |   string   | 合约方法                                     |  是   |
| methodDesc      |   string   | 合约方法描述，若合约内方法没有重载，则此参数可以为空               |  否   |
| args            |  object[]  | 参数列表                                     |  否   |
| remark          |   string   | 交易备注                                     |  否   |

返回值
---
| 字段名   |  字段类型  | 参数描述     |
| ----- |:------:| -------- |
| hash  | string | 交易hash   |
| txHex | string | 交易序列化字符串 |
### Example request data: 
略

### Example response data: 
略

4.20 离线 - 删除合约
==============
Cmd: contractDeleteOffline
--------------------------
_**详细描述: 离线 - 删除合约**_

参数列表
----
| 参数名             |  参数类型  | 参数描述      | 是否必填 |
| --------------- |:------:| --------- |:----:|
| chainId         |  int   | 链id       |  是   |
| sender          | string | 交易创建者账户地址 |  是   |
| contractAddress | string | 合约地址      |  是   |
| remark          | string | 交易备注      |  否   |

返回值
---
| 字段名   |  字段类型  | 参数描述     |
| ----- |:------:| -------- |
| hash  | string | 交易hash   |
| txHex | string | 交易序列化字符串 |
### Example request data: 
略

### Example response data: 
略

4.21 离线 - 合约token转账
===================
Cmd: tokentransferOffline
-------------------------
_**详细描述: 离线 - 合约token转账**_

参数列表
----
| 参数名             |    参数类型    | 参数描述                                     | 是否必填 |
| --------------- |:----------:| ---------------------------------------- |:----:|
| chainId         |    int     | 链id                                      |  是   |
| fromAddress     |   string   | 交易创建者账户地址                                |  是   |
| toAddress       |   string   | 调用者向合约地址转入的主网资产金额，没有此业务时填BigInteger.ZERO |  是   |
| contractAddress |   string   | 合约地址                                     |  是   |
| amount          | biginteger | 合约方法                                     |  是   |
| remark          |   string   | 交易备注                                     |  否   |

返回值
---
| 字段名   |  字段类型  | 参数描述     |
| ----- |:------:| -------- |
| hash  | string | 交易hash   |
| txHex | string | 交易序列化字符串 |
### Example request data: 
略

### Example response data: 
略

4.22 离线 - 从账户地址向合约地址转账(主链资产)的合约交易
=================================
Cmd: transfer2contractOffline
-----------------------------
_**详细描述: 离线 - 从账户地址向合约地址转账(主链资产)的合约交易**_

参数列表
----
| 参数名         |    参数类型    | 参数描述                                     | 是否必填 |
| ----------- |:----------:| ---------------------------------------- |:----:|
| chainId     |    int     | 链id                                      |  是   |
| fromAddress |   string   | 交易创建者账户地址                                |  是   |
| toAddress   |   string   | 调用者向合约地址转入的主网资产金额，没有此业务时填BigInteger.ZERO |  是   |
| amount      | biginteger | 合约方法                                     |  是   |
| remark      |   string   | 交易备注                                     |  否   |

返回值
---
| 字段名   |  字段类型  | 参数描述     |
| ----- |:------:| -------- |
| hash  | string | 交易hash   |
| txHex | string | 交易序列化字符串 |
### Example request data: 
略

### Example response data: 
略

5.1 创建共识节点
==========
Cmd: createAgent
----------------
_**详细描述: 创建共识节点**_

参数列表
----
| 参数名                                                            |      参数类型       | 参数描述        | 是否必填 |
| -------------------------------------------------------------- |:---------------:| ----------- |:----:|
| chainId                                                        |       int       | 链ID         |  是   |
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
```json
{
  "jsonrpc" : "2.0",
  "method" : "createAgent",
  "params" : [ 2, "tNULSeBaMhcccH1KeXhMpH5y3pvtRzatAiuMJk", "tNULSeBaMhbVDg6CpiWx2jzExLFarBr6vJ6aSF", "tNULSeBaMhcccH1KeXhMpH5y3pvtRzatAiuMJk", 10, "2000000000000", "abcd1234" ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : "157ad5e8061328c764090b85b60624d461d1815357c22f2910506a3cdcbbb6d5"
}
```

5.2 注销共识节点
==========
Cmd: stopAgent
--------------
_**详细描述: 注销共识节点**_

参数列表
----
| 参数名                                                      |     参数类型      | 参数描述     | 是否必填 |
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
### Example request data: 
略

### Example response data: 
略

5.3 委托参与共识
==========
Cmd: depositToAgent
-------------------
_**详细描述: 委托参与共识**_

参数列表
----
| 参数名                                                       |    参数类型     | 参数描述     | 是否必填 |
| --------------------------------------------------------- |:-----------:| -------- |:----:|
| chainId                                                   |     int     | 链ID      |  是   |
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
```json
{
  "jsonrpc" : "2.0",
  "method" : "depositToAgent",
  "params" : [ 2, "tNULSeBaMhcccH1KeXhMpH5y3pvtRzatAiuMJk", "157ad5e8061328c764090b85b60624d461d1815357c22f2910506a3cdcbbb6d5", "200000000000", "abcd1234" ],
  "id" : 1234
}
```

### Example response data: 
```json
{
  "jsonrpc" : "2.0",
  "id" : "1234",
  "result" : "4a1177e2a738f72ba5063a8667a81e10bd7523f91ea08b2aa3fb851ca8dc8b07"
}
```

5.4 退出共识
========
Cmd: withdraw
-------------
_**详细描述: 退出共识**_

参数列表
----
| 参数名                                                      |     参数类型     | 参数描述         | 是否必填 |
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
### Example request data: 
略

### Example response data: 
略

5.5 查询节点的委托共识列表
===============
Cmd: getDepositList
-------------------
_**详细描述: 查询节点的委托共识列表**_

参数列表
----
| 参数名       |  参数类型  | 参数描述          | 是否必填 |
| --------- |:------:| ------------- |:----:|
| chainId   |  int   | 链ID           |  是   |
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
略

### Example response data: 
略

5.6 离线组装 - 创建共识节点
=================
Cmd: createAgentOffline
-----------------------
_**详细描述: 参与共识所需资产可通过查询链信息接口获取(agentChainId和agentAssetId)**_

参数列表
----
| 参数名                                                                                                          |     参数类型     | 参数描述       | 是否必填 |
| ------------------------------------------------------------------------------------------------------------ |:------------:| ---------- |:----:|
| chainId                                                                                                      |     int      | 链ID        |  是   |
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
略

### Example response data: 
略

5.7 离线组装 - 注销共识节点
=================
Cmd: stopAgentOffline
---------------------
_**详细描述: 组装交易的StopDepositDto信息，可通过查询节点的委托共识列表获取，input的nonce值可为空**_

参数列表
----
| 参数名                                                                                                                                                          |       参数类型       | 参数描述        | 是否必填 |
| ------------------------------------------------------------------------------------------------------------------------------------------------------------ |:----------------:| ----------- |:----:|
| chainId                                                                                                                                                      |       int        | 链ID         |  是   |
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
略

### Example response data: 
略

5.8 离线组装 - 委托参与共识
=================
Cmd: depositToAgentOffline
--------------------------
_**详细描述: 参与共识所需资产可通过查询链信息接口获取(agentChainId和agentAssetId)**_

参数列表
----
| 参数名                                                                                                          |    参数类型    | 参数描述       | 是否必填 |
| ------------------------------------------------------------------------------------------------------------ |:----------:| ---------- |:----:|
| chainId                                                                                                      |    int     | 链ID        |  是   |
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
略

### Example response data: 
略

5.9 离线组装 - 退出共识
===============
Cmd: withdrawOffline
--------------------
_**详细描述: 离线组装 - 退出共识**_

参数列表
----
| 参数名                                                                                                          |    参数类型     | 参数描述        | 是否必填 |
| ------------------------------------------------------------------------------------------------------------ |:-----------:| ----------- |:----:|
| chainId                                                                                                      |     int     | 链ID         |  是   |
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
略

### Example response data: 
略

