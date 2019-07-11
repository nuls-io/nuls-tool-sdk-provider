# NULS2.0 SDK-Provider

**SDK模块，与核心模块连接，依赖离线SDK包，以HTTP接口作为服务，提供了JSON-RPC和RESTFUL的请求方式，
以此模块作为访问底层钱包数据的桥梁，用于在线和离线的交易组装、查询等功能**


JSON-RPC访问方式
    HttpMethod: POST
    ContentType: application/json;charset=UTF-8
    {
        "jsonrpc":"2.0",
        "method":"methodCMD",		//接口名称
        "params":[],				//所有接口的参数，都已数组方式传递，且参数顺序不能变
        "id":1234
    }
