package io.neow3j.examples.jsonrpc;

import io.neow3j.protocol.Neow3j;
import io.neow3j.protocol.core.methods.response.Transaction;
import io.neow3j.protocol.http.HttpService;

import java.io.IOException;

public class GetTransaction {

    public static void main(String[] args) throws IOException {
        Neow3j neow3j = Neow3j.build(new HttpService("http://localhost:40332"));

        String txId = "0x57ef06710f1a96aea8742e4dcb78d4800ac21fd086a34bb6b53bb444653a4d31";
        Transaction tx = neow3j.getTransaction(txId)
                .send()
                .getTransaction();

        System.out.println("\n####################");
        if (tx == null) {
            System.out.println("No transaction found with tx id = " + txId + ".");
        } else {
            System.out.println("tx: " + tx);
        }
        System.out.println("####################");
    }
}
