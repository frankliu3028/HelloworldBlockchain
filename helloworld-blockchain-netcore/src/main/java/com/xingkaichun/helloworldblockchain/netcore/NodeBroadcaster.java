package com.xingkaichun.helloworldblockchain.netcore;

import com.xingkaichun.helloworldblockchain.core.BlockchainCore;
import com.xingkaichun.helloworldblockchain.netcore.client.BlockchainNodeClientImpl;
import com.xingkaichun.helloworldblockchain.netcore.model.Node;
import com.xingkaichun.helloworldblockchain.netcore.service.NetCoreConfiguration;
import com.xingkaichun.helloworldblockchain.netcore.service.NodeService;
import com.xingkaichun.helloworldblockchain.netcore.transport.dto.PingRequest;
import com.xingkaichun.helloworldblockchain.util.LogUtil;
import com.xingkaichun.helloworldblockchain.util.SleepUtil;
import com.xingkaichun.helloworldblockchain.util.StringUtil;
import com.xingkaichun.helloworldblockchain.util.SystemUtil;

import java.util.List;


/**
 * 节点广播器
 * 在区块链网络中，广播自己。
 * 例如，系统启动后，广播自己，让区块链网络的其它节点知道自己已经上线了。
 * 再例如，由于未知原因，部分节点与自己中断了联系，自己已经不在它们的节点列表中了，
 * 而自己的列表中有它们，这时候可以广播一下自己，这些中断联系的节点将会恢复与自己联系。
 *
 * @author 邢开春 409060350@qq.com
 */
public class NodeBroadcaster {

    private NetCoreConfiguration netCoreConfiguration;
    private NodeService nodeService;
    private BlockchainCore blockchainCore;

    public NodeBroadcaster(NetCoreConfiguration netCoreConfiguration, NodeService nodeService, BlockchainCore blockchainCore) {
        this.netCoreConfiguration = netCoreConfiguration;
        this.nodeService = nodeService;
        this.blockchainCore = blockchainCore;
    }

    public void start() {
        new Thread(()->{
            //定时广播自己
            while (true){
                try {
                    broadcastNode();
                    SleepUtil.sleep(netCoreConfiguration.getNodeBroadcastTimeInterval());
                } catch (Exception e) {
                    SystemUtil.errorExit("在区块链网络中广播自己出现异常",e);
                }
            }
        }).start();
    }

    /**
     * 广播自己
     */
    private void broadcastNode() {
        List<Node> nodes = nodeService.queryAllNodeList();
        for(Node node:nodes){
            try {
                new BlockchainNodeClientImpl(node.getIp()).pingNode(new PingRequest());
            } catch (Exception e) {
                LogUtil.error(StringUtil.format("广播自己时出现异常，无法连接节点[%s]，删除节点[%s]",node.getIp(),node.getIp()),e);
            }
        }
    }
}
