package com.xingkaichun.helloworldblockchain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 区块
 */
@Data
public class BlockDTO implements Serializable {
    private long timestamp;
    private String previousHash;
    private Integer height;
    private List<TransactionDTO> transactions;
    private String merkleRoot;
    private Long nonce;
    private String hash;
}
