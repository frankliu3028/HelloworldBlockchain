package com.xingkaichun.blockchain.core;

import com.xingkaichun.blockchain.core.model.Block;
import com.xingkaichun.blockchain.core.model.transaction.Transaction;
import com.xingkaichun.blockchain.core.model.transaction.TransactionOutput;
import lombok.Data;


/**
 * 区块链数据库：该类用于区块链数据的持久化。
 */
@Data
public abstract class BlockChainDataBase {

    protected Incentive incentive ;
    protected Consensus consensus ;

    //region 区块增加与删除
    /**
     * 新增区块: 在不允许删除区块链上的区块的情况下，将一个新的区块添加到区块链上。
     * 这是一个有些复杂的操作，需要考虑如下几点:
     * 新增区块本身的数据的正确性;
     * 新增的区块是否能够正确衔接到区块链的尾部;
     */
    public abstract boolean addBlock(Block block) throws Exception ;

    /**
     * 删除区块链的尾巴区块(最后一个区块)
     */
    public abstract Block removeTailBlock() throws Exception ;
    //endregion

    //region 区块链提供的通用方法
    /**
     * 查找区块链上的最后一个区块
     */
    public abstract Block findTailBlock() throws Exception ;

    /**
     * 获取区块链的长度
     */
    public abstract int obtainBlockChainLength() throws Exception ;

    /**
     * 在区块链中根据 UTXO ID 查找UTXO
     * @param transactionOutputUUID UTXO ID
     */
    public abstract TransactionOutput findUtxoByUtxoUuid(String transactionOutputUUID) throws Exception ;

    /**
     * 在区块链中根据区块高度查找区块
     * @param blockHeight 区块高度
     */
    public abstract Block findBlockByBlockHeight(int blockHeight) throws Exception ;

    /**
     * 在区块链中根据交易ID查找交易
     * @param transactionUUID 交易ID
     */
    public abstract Transaction findTransactionByTransactionUuid(String transactionUUID) throws Exception ;
    //endregion



    /**
     * 检测区块是否可以被应用到区块链上
     * 只有一种情况，区块可以被应用到区块链，即: 区块是区块链上的下一个区块
     */
    public abstract boolean isBlockCanApplyToBlockChain(Block block) throws Exception ;

    /**
     * 校验交易是否可以被添加进下一个区块之中。
     * 如果校验的是奖励交易，则需要整个区块的信息，因此这个函数包含了两个参数：交易所在的区块、交易
     */
    public abstract boolean isTransactionCanAddToNextBlock(Block block, Transaction transaction) throws Exception ;
}